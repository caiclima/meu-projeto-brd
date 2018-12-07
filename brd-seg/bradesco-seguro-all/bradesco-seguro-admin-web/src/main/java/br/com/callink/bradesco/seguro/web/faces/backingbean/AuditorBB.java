package br.com.callink.bradesco.seguro.web.faces.backingbean;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.callink.bradesco.seguro.dto.UsuarioDTO;
import br.com.callink.bradesco.seguro.entity.Cargo;
import br.com.callink.bradesco.seguro.entity.ParametroSistema;
import br.com.callink.bradesco.seguro.entity.Usuario;
import br.com.callink.bradesco.seguro.service.ICargoService;
import br.com.callink.bradesco.seguro.service.IParametroSistemaService;
import br.com.callink.bradesco.seguro.service.IUsuarioService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

@ManagedBean
@ViewScoped
public class AuditorBB extends GenericBB{
	private static final long serialVersionUID = 1L;
	
	private UsuarioDTO pojo;
	private boolean modoAtualizacao;
	private boolean modoNovo;
	private List<Cargo> cargosList;
	private List<UsuarioDTO> auditoresList;
	
	@EJB
	private IParametroSistemaService<ParametroSistema> parametroSistemaService;
	
	@EJB
	private ICargoService<Cargo> cargoService;
	
	@EJB
	private IUsuarioService<Usuario> usuarioService;
	
	@PostConstruct
	public void init(){
		pojo = new UsuarioDTO();
		modoNovo = false;
		modoAtualizacao = false;
		cargosList = findCargosAuditor();
	}
	
	public void salvar() throws ServiceException{
		ServiceResponse response = usuarioService.salvarUsuarioAuditor(pojo);
		
		addMsgs(response);
		
		if((Integer) response.getData() != 0){
			cancelar();
			pesquisar();
		}
	}
	
	public void excluir(UsuarioDTO usuarioDTO) throws ServiceException{
		ServiceResponse response = usuarioService.excluirUsuarioAuditor(usuarioDTO);
		
		addMsgs(response);
		
		if((Integer) response.getData() != 0){
			cancelar();
			pesquisar();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void pesquisar() throws ServiceException{
		try{
			ServiceResponse response = usuarioService.findAuditoresByExample(pojo);
			auditoresList = (List<UsuarioDTO>) response.getData();
			
			addMsgs(response);
		}catch(ServiceException e){
			e.printStackTrace();
			throw e;
		}
	}
	
	public void editar(UsuarioDTO usuarioDTO){
		modoAtualizacao = false;
		modoNovo = true;
		
		pojo = usuarioDTO;
	}
	
	@SuppressWarnings("unchecked")
	private List<Cargo> findCargosAuditor() {
		List<Cargo> cargos = new ArrayList<Cargo>();
		
		try{
			ParametroSistema idsCargos = parametroSistemaService.buscarPorNome(ParametroSistema.PARAMETRO_IDS_CARGO_AUDITOR);
			
			if(idsCargos == null){
				return null;
			}
			
			List<BigInteger> ids = new ArrayList<BigInteger>();
			
			for (String id : idsCargos.getValorParametroSistema().split(",")) {
				ids.add(new BigInteger(id.replace(" ", "")));
			}
			
			cargos = (List<Cargo>) cargoService.findCargosByIds(ids).getData();
			
		}catch(Exception e){
			e.printStackTrace();
		}

		return cargos;
	}
	
	public void novo(){
		modoAtualizacao = false;
		modoNovo = true;
		pojo = new UsuarioDTO();
	}
	
	public void cancelar(){
		modoAtualizacao = false;
		modoNovo = false;
		pojo = new UsuarioDTO();
	}
	
	public UsuarioDTO getPojo() {
		return pojo;
	}

	public void setPojo(UsuarioDTO pojo) {
		this.pojo = pojo;
	}

	public boolean isModoAtualizacao() {
		return modoAtualizacao;
	}

	public void setModoAtualizacao(boolean modoAtualizacao) {
		this.modoAtualizacao = modoAtualizacao;
	}

	public boolean isModoNovo() {
		return modoNovo;
	}

	public void setModoNovo(boolean modoNovo) {
		this.modoNovo = modoNovo;
	}

	public List<Cargo> getCargosList() {
		return cargosList;
	}

	public void setCargosList(List<Cargo> cargosList) {
		this.cargosList = cargosList;
	}

	public List<UsuarioDTO> getAuditoresList() {
		return auditoresList;
	}

	public void setAuditoresList(List<UsuarioDTO> auditoresList) {
		this.auditoresList = auditoresList;
	}
	
}
