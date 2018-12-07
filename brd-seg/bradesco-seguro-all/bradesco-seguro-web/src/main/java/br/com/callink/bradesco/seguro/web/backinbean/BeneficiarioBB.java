package br.com.callink.bradesco.seguro.web.backinbean;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import org.richfaces.component.SortOrder;

import br.com.callink.bradesco.seguro.commons.utils.CollectionUtils;
import br.com.callink.bradesco.seguro.commons.utils.DateUtils;
import br.com.callink.bradesco.seguro.entity.Beneficiario;
import br.com.callink.bradesco.seguro.entity.BeneficiarioPlano;
import br.com.callink.bradesco.seguro.entity.Esporte;
import br.com.callink.bradesco.seguro.entity.GrauParentesco;
import br.com.callink.bradesco.seguro.entity.Profissao;
import br.com.callink.bradesco.seguro.enums.ProfissaoEnum;
import br.com.callink.bradesco.seguro.enums.Sexo;
import br.com.callink.bradesco.seguro.enums.TipoBeneficiario;
import br.com.callink.bradesco.seguro.service.IBeneficiarioPlanoService;
import br.com.callink.bradesco.seguro.service.IBeneficiarioService;
import br.com.callink.bradesco.seguro.service.IEsporteService;
import br.com.callink.bradesco.seguro.service.IGrauParentescoService;
import br.com.callink.bradesco.seguro.service.IProfissaoService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.web.selectitem.EsporteSelectItem;
import br.com.callink.bradesco.seguro.web.selectitem.GrauParentescoSelectItem;
import br.com.callink.bradesco.seguro.web.selectitem.ProfissaoSelectItem;

public class BeneficiarioBB extends GenericCrudBB<Beneficiario, IBeneficiarioService<Beneficiario>> {
	
	private static final long serialVersionUID = 8806132117733701960L;
	
	@EJB
	private IBeneficiarioService<Beneficiario> beneficiarioService;
	
	@EJB
	private IProfissaoService<Profissao> profissaoService;
	
	@EJB
	private IEsporteService<Esporte> esporteService;
	
	@EJB
	private IGrauParentescoService<GrauParentesco> grauParentescoService;
	
	@EJB
	private IBeneficiarioPlanoService<BeneficiarioPlano> beneficiarioPlanoService;

	private SortOrder nomeOrder = SortOrder.unsorted;
	private SortOrder grauParentescoOrder = SortOrder.unsorted;
	private SortOrder sexoOrder = SortOrder.unsorted;
	private SortOrder cpfOrder = SortOrder.unsorted;
	private SortOrder dataNascimentoOrder = SortOrder.unsorted;
	private SortOrder profissaoOrder = SortOrder.unsorted;
	private SortOrder esporteOrder = SortOrder.unsorted;
	
	private Collection<Object> selecao;
	private int currentBeneficiarioPage = 1;
	private Boolean sucesso;
	private Sexo[] sexoItens;
	private String idade;
	private String showHiv = "hidden";
	
	private ProfissaoSelectItem profissaoSelectItem;
	private EsporteSelectItem esporteSelectItem;
	private GrauParentescoSelectItem grauParentescoSelectItem;
	private Profissao profissaoAposentadoInvalidez;
	
	private VendaBB vendaBB;

	@PostConstruct
	public void init() throws Exception {
		
		sexoItens = Sexo.values();
		setEntity(new Beneficiario(Boolean.FALSE, Boolean.FALSE));
	}
	
	@Override
	protected IBeneficiarioService<Beneficiario> getService() {
		return this.beneficiarioService;
	}

	@Override
	public void novoRegistro() throws Exception {
		
		setEntity(new Beneficiario());
		setIdade(null);
		setSucesso(Boolean.FALSE);
	}
	
	public void teste(String produto) {
		System.out.println(produto);
	}
	
	@SuppressWarnings("unchecked")
	public void pesquisar() throws Exception {
		antesPesquisar();
		ServiceResponse serviceResponse = getService().findByExample(getEntity());
		addMsgs(serviceResponse);
		setDados((List<Beneficiario>) serviceResponse.getData());
		aposPesquisar();
	}
	
	@Override
	protected void antesPesquisar() throws Exception {
		getEntity().setTipoBeneficiario(TipoBeneficiario.DEPENDENTE.getValor());
		if (getEntity() != null){
			if (getEntity().getCpf() != null) {
				getEntity().setCpf(getEntity().getCpf().replaceAll("[\\.-]+", ""));
			}
			
			if (getEntity().getProfissao() != null && getEntity().getProfissao().getId() != null) {
				getEntity().setIdProfissao(getEntity().getProfissao().getId());
			} else {
				getEntity().setIdProfissao(null);
			}
			
			if (getEntity().getEsporte() != null && getEntity().getEsporte().getId() != null) {
				getEntity().setIdEsporte(getEntity().getEsporte().getId());
			} else {
				getEntity().setIdEsporte(null);
			}
			
			if (getEntity().getGrauParentesco() != null && getEntity().getGrauParentesco().getId() != null) {
				getEntity().setIdGrauParentesco(getEntity().getGrauParentesco().getId());
			} else {
				getEntity().setIdGrauParentesco(null);
			}
			
			if (vendaBB.getEntity().getId() != null) {
				getEntity().setIdVenda(vendaBB.getEntity().getId());
			}
		}
	}
	
	public void chamarPopup(Beneficiario beneficiario) throws Exception {
		if (beneficiario.getId() == null) {
			novoRegistro();
		}
		setEntity(beneficiario);
		setSucesso(Boolean.FALSE);
		
		calcularIdade();
	}

	@Override
	public void excluir(Beneficiario beneficiario) throws Exception {
		ServiceResponse response = beneficiarioService.remover(beneficiario);
    	
		getDados().remove(beneficiario);
		
		vendaBB.getEntity().getBeneficiarioList().remove(beneficiario);
		vendaBB.calcularPlanoSelecionadoAndSetarTipoPlano();
		
		addMsgs(response);
		novoRegistro();
	}

	public void salvar() throws ServiceException {
		antesSalvar();
		boolean isNovoRegistro = Boolean.FALSE;
		if (getEntity().getId() == null || getEntity().getId().compareTo(BigInteger.ZERO) == 0) {
			isNovoRegistro = Boolean.TRUE;
		}
		
		getEntity().setVenda(vendaBB.getEntity());
		
		Set<BeneficiarioPlano> beneficiarioPlanoList = null;
		BeneficiarioPlano beneficiarioPlano = null;
		
		//TODO - melhorar codigo
		if (isNovoRegistro) {
			beneficiarioPlanoList = new HashSet<BeneficiarioPlano>();
			beneficiarioPlano = new BeneficiarioPlano();
			beneficiarioPlano.setPlano(vendaBB.getPlanoSelecionado());
			beneficiarioPlanoList.add(beneficiarioPlano);
			getEntity().setBeneficiarioPlanoList(beneficiarioPlanoList);
		} else {
			for (BeneficiarioPlano temp : getEntity().getBeneficiarioPlanoList()) {
				if (temp.getBeneficiario().equals(getEntity())) {
					temp.setBeneficiario(getEntity());
					temp.setPlano(vendaBB.getPlanoSelecionado());
				}
			}
		}
		
		ServiceResponse serviceResponse = getService().salvarOuAtualizar(getEntity(), "Atendimento", vendaBB.getLyricallUrlParameters().getLoginUsuario());
		
		if (isNovoRegistro) {
			getDados().add((Beneficiario) serviceResponse.getData());
			vendaBB.getEntity().getBeneficiarioList().add((Beneficiario) serviceResponse.getData());
		} else {
			for (Beneficiario beneficiario : vendaBB.getEntity().getBeneficiarioList()) {
				if (beneficiario.equals((Beneficiario) serviceResponse.getData())) {
					beneficiario = (Beneficiario) serviceResponse.getData();
				}
			}
		}
		
		vendaBB.calcularPlanoSelecionadoAndSetarTipoPlano();
		
		setEntity(new Beneficiario());
		setSucesso(Boolean.TRUE);
		addMsgs(serviceResponse);
	}
	
	@Override
	protected void antesSalvar() {
		if (getEntity() != null){
			getEntity().setTipoBeneficiario(TipoBeneficiario.DEPENDENTE.getValor());
		}
	}
	
	public int getCurrentBeneficiarioPage() {
		return currentBeneficiarioPage;
	}

	public void setCurrentBeneficiarioPage(int currentBeneficiarioPage) {
		this.currentBeneficiarioPage = currentBeneficiarioPage;
	}

	public void sortByNome() {
		grauParentescoOrder = SortOrder.unsorted;
		sexoOrder = SortOrder.unsorted;
		cpfOrder = SortOrder.unsorted;
		dataNascimentoOrder = SortOrder.unsorted;
		profissaoOrder = SortOrder.unsorted;
		esporteOrder = SortOrder.unsorted;

		setNomeOrder(nomeOrder.equals(SortOrder.ascending) ? SortOrder.descending : SortOrder.ascending);
	}

	public void sortByGrauParentesco() {
		nomeOrder = SortOrder.unsorted;
		sexoOrder = SortOrder.unsorted;
		cpfOrder = SortOrder.unsorted;
		dataNascimentoOrder = SortOrder.unsorted;
		profissaoOrder = SortOrder.unsorted;
		esporteOrder = SortOrder.unsorted;

		setGrauParentescoOrder(grauParentescoOrder.equals(SortOrder.ascending) ? SortOrder.descending : SortOrder.ascending);
	}

	public void sortBySexo() {
		nomeOrder = SortOrder.unsorted;
		grauParentescoOrder = SortOrder.unsorted;
		cpfOrder = SortOrder.unsorted;
		dataNascimentoOrder = SortOrder.unsorted;
		profissaoOrder = SortOrder.unsorted;
		esporteOrder = SortOrder.unsorted;

		setSexoOrder(sexoOrder.equals(SortOrder.ascending) ? SortOrder.descending : SortOrder.ascending);
	}

	public void sortByCpf() {
		nomeOrder = SortOrder.unsorted;
		grauParentescoOrder = SortOrder.unsorted;
		sexoOrder = SortOrder.unsorted;
		dataNascimentoOrder = SortOrder.unsorted;
		profissaoOrder = SortOrder.unsorted;
		esporteOrder = SortOrder.unsorted;

		setCpfOrder(cpfOrder.equals(SortOrder.ascending) ? SortOrder.descending : SortOrder.ascending);
	}

	public void sortByDataNascimento() {
		nomeOrder = SortOrder.unsorted;
		grauParentescoOrder = SortOrder.unsorted;
		sexoOrder = SortOrder.unsorted;
		cpfOrder = SortOrder.unsorted;
		profissaoOrder = SortOrder.unsorted;
		esporteOrder = SortOrder.unsorted;

		setDataNascimentoOrder(dataNascimentoOrder.equals(SortOrder.ascending) ? SortOrder.descending : SortOrder.ascending);
	}
	
	public void sortByProfissao() {
		nomeOrder = SortOrder.unsorted;
		grauParentescoOrder = SortOrder.unsorted;
		sexoOrder = SortOrder.unsorted;
		cpfOrder = SortOrder.unsorted;
		dataNascimentoOrder = SortOrder.unsorted;
		esporteOrder = SortOrder.unsorted;

		setProfissaoOrder(profissaoOrder.equals(SortOrder.ascending) ? SortOrder.descending : SortOrder.ascending);
	}
	
	public void sortByEsporte() {	
		nomeOrder = SortOrder.unsorted;
		grauParentescoOrder = SortOrder.unsorted;
		sexoOrder = SortOrder.unsorted;
		cpfOrder = SortOrder.unsorted;
		dataNascimentoOrder = SortOrder.unsorted;
		profissaoOrder = SortOrder.unsorted;

		setEsporteOrder(esporteOrder.equals(SortOrder.ascending) ? SortOrder.descending : SortOrder.ascending);
	}
	
	public void calcularIdade() {
		if (getEntity().getDataNascimento() != null) {
			idade = DateUtils.getIdadeByNascimento(getEntity().getDataNascimento());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void fillProfissoesSelectItem() throws ServiceException {
		ServiceResponse serviceResponse = profissaoService.findEnabledAndNotRemoved();
		List<Profissao> profissoes = (List<Profissao>) serviceResponse.getData();
        if (this.profissaoAposentadoInvalidez == null
        		&& !CollectionUtils.isEmpty(profissoes)){
        	for(Profissao profissao: profissoes){
        		if (profissao.getNome().equalsIgnoreCase(ProfissaoEnum.APOSENTADO_INVALIDEZ.getValor())
        				&& profissao.getCodigo().equals(ProfissaoEnum.APOSENTADO_INVALIDEZ.getCodigo())){
        			this.profissaoAposentadoInvalidez = profissao;
        		}
        	}
        }
		profissaoSelectItem.setListAndOrder((List<Profissao>)serviceResponse.getData());
	}
	
	@SuppressWarnings("unchecked")
	private void fillEsportesSelectItem() throws ServiceException {
		ServiceResponse serviceResponse = esporteService.findEnabledAndNotRemoved();
		esporteSelectItem.setListAndOrder((List<Esporte>)serviceResponse.getData());
	}
	
	@SuppressWarnings("unchecked")
	private void fillGrauParentescoSelectItem() throws ServiceException {
		ServiceResponse serviceResponse = grauParentescoService.findWithoutTitular();
		grauParentescoSelectItem.setListAndOrder((List<GrauParentesco>)serviceResponse.getData());
	}
	
	public ProfissaoSelectItem getProfissaoSelectItem() throws ServiceException {
		if (profissaoSelectItem == null) {
			profissaoSelectItem = new ProfissaoSelectItem();
		}
		fillProfissoesSelectItem();

		return profissaoSelectItem;
	}

	public void setProfissaoSelectItem(ProfissaoSelectItem profissaoSelectItem) {
		this.profissaoSelectItem = profissaoSelectItem;
	}
	
	public EsporteSelectItem getEsporteSelectItem() throws ServiceException {
		if (esporteSelectItem == null) {
			esporteSelectItem = new EsporteSelectItem();
		}
		fillEsportesSelectItem();

		return esporteSelectItem;
	}

	public void setEsporteSelectItem(EsporteSelectItem esporteSelectItem) {
		this.esporteSelectItem = esporteSelectItem;
	}
	
	public GrauParentescoSelectItem getGrauParentescoSelectItem() throws ServiceException {
		if (grauParentescoSelectItem == null) {
			grauParentescoSelectItem = new GrauParentescoSelectItem();
		}
		fillGrauParentescoSelectItem();

		return grauParentescoSelectItem;
	}
	
	public void atualizarInvalidez() throws ServiceException {

		if (getEntity().getFlagAposentadoInvalidez()) {
			getEntity().setProfissao(this.profissaoAposentadoInvalidez);
		} else {
			getEntity().setProfissao(null);
		} 
	}
	
	public void atualizarInvalidezCombo() throws ServiceException {

		if (getEntity().getProfissao().equals(this.profissaoAposentadoInvalidez)) {
			getEntity().setFlagAposentadoInvalidez(Boolean.TRUE);
		} else {
			getEntity().setFlagAposentadoInvalidez(Boolean.FALSE);
		} 
	}

	public void setGrauParentescoSelectItem(GrauParentescoSelectItem grauParentescoSelectItem) {
		this.grauParentescoSelectItem = grauParentescoSelectItem;
	}
	
	public SortOrder getNomeOrder() {
		return nomeOrder;
	}

	public void setNomeOrder(SortOrder nomeOrder) {
		this.nomeOrder = nomeOrder;
	}

	public SortOrder getGrauParentescoOrder() {
		return grauParentescoOrder;
	}

	public void setGrauParentescoOrder(SortOrder grauParentescoOrder) {
		this.grauParentescoOrder = grauParentescoOrder;
	}

	public SortOrder getSexoOrder() {
		return sexoOrder;
	}

	public void setSexoOrder(SortOrder sexoOrder) {
		this.sexoOrder = sexoOrder;
	}

	public SortOrder getCpfOrder() {
		return cpfOrder;
	}

	public void setCpfOrder(SortOrder cpfOrder) {
		this.cpfOrder = cpfOrder;
	}

	public SortOrder getDataNascimentoOrder() {
		return dataNascimentoOrder;
	}

	public void setDataNascimentoOrder(SortOrder dataNascimentoOrder) {
		this.dataNascimentoOrder = dataNascimentoOrder;
	}

	public SortOrder getProfissaoOrder() {
		return profissaoOrder;
	}

	public void setProfissaoOrder(SortOrder profissaoOrder) {
		this.profissaoOrder = profissaoOrder;
	}

	public SortOrder getEsporteOrder() {
		return esporteOrder;
	}

	public void setEsporteOrder(SortOrder esporteOrder) {
		this.esporteOrder = esporteOrder;
	}

	public Boolean getSucesso() {
		return sucesso;
	}

	public void setSucesso(Boolean sucesso) {
		this.sucesso = sucesso;
	}
	
	@Override
	protected boolean isPesquisarTodos() {
		return false;
	}

	public VendaBB getVendaBB() {
		return vendaBB;
	}

	public void setVendaBB(VendaBB vendaBB) {
		this.vendaBB = vendaBB;
	}

	public Collection<Object> getSelecao() {
		return selecao;
	}

	public void setSelecao(Collection<Object> selecao) {
		this.selecao = selecao;
	}

	public String getIdade() {
		return idade;
	}

	public void setIdade(String idade) {
		this.idade = idade;
	}
	
	public String getShowHiv() {
		return showHiv;
	}

	public void setShowHiv(String showHiv) {
		this.showHiv = showHiv;
	}

	public Sexo[] getSexoItens() {
		return sexoItens;
	}

	public void setSexoItens(Sexo[] sexoItens) {
		this.sexoItens = sexoItens;
	}
}