package br.com.callink.bradesco.seguro.service.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.w3c.dom.Node;

import br.com.callink.bradesco.seguro.commons.utils.CepUtils;
import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.dao.IContatoMailingDAO;
import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.ContatoMailing;
import br.com.callink.bradesco.seguro.entity.ParametroSistema;
import br.com.callink.bradesco.seguro.entity.TelefoneCliente;
import br.com.callink.bradesco.seguro.service.IClienteCampanhaService;
import br.com.callink.bradesco.seguro.service.IContatoMailingService;
import br.com.callink.bradesco.seguro.service.ILoteMailingService;
import br.com.callink.bradesco.seguro.service.IParametroSistemaService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;

@Stateless
public class ContatoMailingService extends GenericCrudServiceImpl<ContatoMailing> implements IContatoMailingService {
	
	private static final String ERRO_CONSULTA = "Erro Consulta";
	
	
	public static final String TAG_TELEFONES = "/telefones";
	
	public static final String TAG_TELEFONE = "/telefone";
	
	public static final String TAG_NM_CONTATO = "nm_contato";
	
	public static final String TAG_DS_VALOR = "ds_valor";
	
	
	@Inject
	private IContatoMailingDAO contatoMailingDAO;
	
	@EJB
	private IParametroSistemaService<ParametroSistema> parametroSistemaService;
	
	@EJB
	private IClienteCampanhaService clienteCampanhaService;
	
	@EJB
	private ILoteMailingService loteMailingService;
	
	@Override
	protected IContatoMailingDAO getDAO() {
		return contatoMailingDAO;
	}
	
	@Override
	public ContatoMailing findById(BigInteger idContatoMailing)
			throws ServiceException, ValidationException {
		try {
			if(idContatoMailing == null){
				throw new ValidationException(ERRO_CONSULTA);
			}
			return contatoMailingDAO.findByPK(idContatoMailing);
		} catch (DataException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public ContatoMailing findByIdClienteCampanha(BigInteger idClienteCampanha)
			throws ServiceException, ValidationException {
		try {
			if(idClienteCampanha == null) {
				throw new ValidationException(ERRO_CONSULTA);
			}
			return validarSingleObject(contatoMailingDAO.findByIdClienteCampanha(idClienteCampanha));
		} catch (DataException e) {
			throw new ServiceException(e);
		}
	}

	protected void validarSave(ContatoMailing object)
			throws ValidationException {
		
	}

	protected void validarUpdate(ContatoMailing object)
			throws ValidationException {
		
	}

	protected void validarDelete(ContatoMailing object)
			throws ValidationException {
		
	}

	@Override
	public ContatoMailing findContatoMailingByIdClienteCampanha(BigInteger idClienteCampanha) throws ServiceException {
		return contatoMailingDAO.findContatoMailingByIdClienteCampanha(idClienteCampanha);
	}
	
//	public List<ClienteCampanha> buscarContatoMailing(List<String> cnpjs) throws ServiceException {
//
//		logger.info("Consultando Pessoa Relacionada...");
//		
//		ParametroSistema wsdl = parametroSistemaService.buscarPorNome(ParametroSistemaEnum.PARAMETRO_URL_WSDL_WSPARCELA_TRIBANCO.value());
//		
//		if (wsdl == null) {
//			throw new ServiceException("Parametro de Sistema [" + ParametroSistemaEnum.PARAMETRO_URL_WSDL_WSPARCELA_TRIBANCO.value() + "] representando URL do WebServide de Parcelas Tribanco nao existe. ");
//		}
//		
//		ParametroSistema nomeLoteMailingDefaultParametroSistema = parametroSistemaService.buscarPorNome(ParametroSistemaEnum.PARAMETRO_NOME_LOTE_MAILING_DEFAULT.value());
//		
//		if (nomeLoteMailingDefaultParametroSistema == null) {
//			throw new ServiceException("Parametro de Sistema [" + ParametroSistemaEnum.PARAMETRO_NOME_LOTE_MAILING_DEFAULT.value() + "] representando o id default de lote Mailing Tribanco nao existe. ");
//		}
//		
//		String nomeLoteMailingDefault = nomeLoteMailingDefaultParametroSistema.getValorParametroSistema();
//		
//		LoteMailing loteMailing = new LoteMailing(nomeLoteMailingDefault);
//		
//		List<ClienteCampanha> clienteCampanhaList = new ArrayList<ClienteCampanha>();
//		
//		try {
//		
//			WsParcelaTNTSoap webservice = WebServiceClientFactory.create(WebService.WSParcela, wsdl.getValorParametroSistema());
//			
//			Holder<String> msgErro = new Holder<String>();
//			Holder<String> msgErroTelefone = new Holder<String>();
//			
//			Holder<String> razaoSocialResult = new Holder<String>();
//			Holder<String> telefoneResult = new Holder<String>();
//			XPathUtil xPathUtil;
//			List<TelefoneCliente> telefoneClienteList;
//			ClienteCampanha clienteCampanha = null;
//			TelefoneCliente telefoneCliente = null;
//			ContatoMailing contatoMailing = null;
//			String buscaTelefone;
//			Integer index;
//
//			NodeList nodeListChild;
//			String valor;
//			
//			NodeList nodeList; 
//	
//			logger.info("Enviando requisicao para Web Service...");
//			
//			for (String cnpj:cnpjs) {
//			
//				webservice.razaoSocial(0l, cnpj, msgErro, razaoSocialResult);
//				
//				if(!StringUtils.isEmpty(msgErro.value)){
//					throw new ServiceException("Busca Razão Social - cnpj: "+cnpj+"Error: " + msgErro.value);
//				}
//			
//				webservice.telefone(0l, cnpj, msgErroTelefone, telefoneResult);
//				
//				if(!StringUtils.isEmpty(msgErroTelefone.value)){
//					throw new ServiceException("Busca Telefone - cnpj: "+cnpj+"Error: " + msgErroTelefone.value);
//				}
//				
//				String razaoSocial = XMLUtils.normalize(razaoSocialResult.value);
//				
//				if (StringUtils.isEmpty(razaoSocial)) {
//					throw new ServiceException("cnpj: "+cnpj+"Razao Social vazia... "+razaoSocial);
//				}
//				
//				// passando o telefone.
//				telefoneClienteList = new ArrayList<TelefoneCliente>();
//				
//				buscaTelefone = XMLUtils.normalize(telefoneResult.value);
//				
//				xPathUtil = new XPathUtil(buscaTelefone);
//				
//				nodeList = xPathUtil.getNodeList(TAG_TELEFONES+TAG_TELEFONE);
//				
//				if (nodeList != null) {
//					
//					for (int i = 0; i < nodeList.getLength(); i++) {
//						
//						nodeListChild = nodeList.item(i).getChildNodes();
//						telefoneCliente = null;
//						
//						for (int j = 0; j <nodeListChild.getLength(); j++) {
//							
////							valor = retornaDadoTag(TAG_NM_CONTATO, nodeListChild.item(j));
////							não precisa pegar o nome do contato.
//							
//							valor = retornaDadoTag(TAG_DS_VALOR, nodeListChild.item(j));
//							
//							if (valor != null) {
//								telefoneCliente = validarTelefone(valor);
//								continue;
//							}
//							
//						}
//						
//						if (telefoneCliente != null) {
//							telefoneClienteList.add(telefoneCliente);
//						}
//					
//					}
//				}
//				
//				clienteCampanha = new ClienteCampanha();
//				clienteCampanha.setFlagEnabled(Boolean.TRUE);
//				clienteCampanha.setCampanha(new Campanha());
//				clienteCampanha.setUsuario(new Usuario());
//				
//				loteMailing.setQuantidadeImportada(0);
//				loteMailing.setQuantidadeRejeitada(0);
//				loteMailing.setFlagEnabled(Boolean.TRUE);
//				
//				contatoMailing = new ContatoMailing();
//				contatoMailing.setRazaoSocial(razaoSocial);
//				contatoMailing.setCnpj(cnpj);
//				contatoMailing.setLoteMailing(loteMailing);
//				contatoMailing.setFlagEnabled(Boolean.TRUE);
//				
//				if (telefoneClienteList.size() == 0) {
//					throw new ServiceException("Nenhum Telefone é válido.");
//				}
//				
//				clienteCampanha.setTelefoneClienteList(telefoneClienteList);
//				
//				for (TelefoneCliente telefoneClienteFor:telefoneClienteList) {
//					
//					index = telefoneClienteList.indexOf(telefoneClienteFor);
//					
//					telefoneClienteFor.setFlagNovo(Boolean.TRUE);
//					telefoneClienteFor.setFlagEnabled(Boolean.TRUE);
//					telefoneClienteFor.setDataCadastro(new Date());
//					telefoneClienteFor.setFlagBloqueadoProcon(Boolean.FALSE);
//					telefoneClienteFor.setFlagPreferencial(Boolean.FALSE);
//					telefoneClienteFor.setFlagInvalido(Boolean.FALSE);
//					telefoneClienteFor.setSequencia(BigInteger.ZERO);
//					
//					switch (index) {
//						case 0:
//							contatoMailing.setTelefoneUm(telefoneClienteFor.getTelefone());
//							break;
//						case 1:
//							contatoMailing.setTelefoneDois(telefoneClienteFor.getTelefone());
//							break;
//						case 2:
//							contatoMailing.setTelefoneTres(telefoneClienteFor.getTelefone());
//							break;
//						case 3:
//							contatoMailing.setTelefoneQuatro(telefoneClienteFor.getTelefone());
//							break;
//						case 4:
//							contatoMailing.setTelefoneCinco(telefoneClienteFor.getTelefone());
//							break;
//						case 5:
//							contatoMailing.setTelefoneSeis(telefoneClienteFor.getTelefone());
//							break;
//						case 6:
//							contatoMailing.setTelefoneSete(telefoneClienteFor.getTelefone());
//							break;
//						case 7:
//							contatoMailing.setTelefoneOito(telefoneClienteFor.getTelefone());
//							break;
//						default: continue;
//							
//					}
//				}
//				
//				clienteCampanha.setContatoMailing(contatoMailing);
//				
//				clienteCampanhaList.add(clienteCampanha);
//				
//			}
//			
//			
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			throw new ServiceException(e);
//		}
//	
//		logger.info("Consulta realizada!");
//	
//		return clienteCampanhaList;
//	}
	
	private TelefoneCliente validarTelefone(String telefone) {
		
		TelefoneCliente telefoneCliente = new TelefoneCliente();
		
		/**
         * Valida se o telefone está em um dos formatos: xxxxxxxxxx(10digitos), xxxxxxxxxxx(11digitos) ou xxxxxxxxxxxx(12digitos)
         * o ddd está representado nos 2 primeiros digitos
         * os demais digitos representam o número do telefone
         */

        //telefone = "(011) 12345-67890";

        String expressaoTelefone = "([1-9][0-9])([0-9]{8}|[0-9]{9}|[0-9]{10})";

        String expressaoTelefoneDDD34 = "([0-9]{8})";


	     //remove da string todos os cqracteres nao numericos
	     String auxTelefone = telefone.replaceAll("[^0-9]", "");
	
	        //caso o primeiro digito seja zero ele é removido
	     auxTelefone = auxTelefone.replaceAll("^[0]", "");
	
	     //caso o telefone tenha mais que 12 digitos recorta apenas os 12 primeiros
	        auxTelefone = auxTelefone.length() > 12 ? auxTelefone.substring(0, 12) : auxTelefone;
	
	     String ddd = "";
	     String numero = "";
	     if (Pattern.matches(expressaoTelefone, auxTelefone)) { // telefone nos formatos: xxxxxxxxxx(10digitos), xxxxxxxxxxx(11digitos) ou xxxxxxxxxxxx(12digitos)
	           
	            //os 2 primeiros digitos são o codigo de area(DDD)
	            ddd = auxTelefone.substring(0, 2);
	            //o numero do telefone
	            numero = auxTelefone.substring(2);
	            
	            telefoneCliente.setDdd(ddd);
	            telefoneCliente.setTelefone(numero);
	            
	            return telefoneCliente;
	
        } else if (Pattern.matches(expressaoTelefoneDDD34, auxTelefone)) { // telefone nos formatos: xxxxxxxx(8digitos) - apenas local (34)
            //havia uma regra previamente implementada que considera o ddd 34 quando recebe apena o numero do telefone(8 digitos)
            ddd = "34";
            numero = auxTelefone;
            
            telefoneCliente.setDdd(ddd);
            telefoneCliente.setTelefone(numero);
            
            return telefoneCliente;
        }
	     
	     return null;
	}
	
	private String retornaDadoTag(String tagName,Node node) {
		
		if (node.getNodeName().equals(tagName)) {
			return node.getFirstChild().getNodeValue();
		} else {
			return null;
		}
	}
	
	@Override
	public Boolean compararContatosMailing(ContatoMailing contatoVenda,
			ContatoMailing contatoMailing, List<TelefoneCliente> telefones) {
		
		if ((isEmpty(contatoVenda.getCidade())  || !contatoVenda.getCidade().equals(contatoMailing.getCidade())) 
			|| (isEmpty(contatoVenda.getUf())       || !contatoVenda.getUf().equals(contatoMailing.getUf()))
			|| (isEmpty(contatoVenda.getBairro())   || !contatoVenda.getBairro().equals(contatoMailing.getBairro()))
			|| (isEmpty(contatoVenda.getCep())      || !contatoVenda.getCep().equals(contatoMailing.getCep()))
			|| (isEmpty(contatoVenda.getEndereco()) || !contatoVenda.getEndereco().equals(contatoMailing.getEndereco()))
			|| (isEmpty(contatoVenda.getCidade())   || !contatoVenda.getCidade().equals(contatoMailing.getCidade()))
			|| (isEmpty(contatoVenda.getNome())     || !contatoVenda.getNome().equals(contatoMailing.getNome()))
			|| (isNull(contatoVenda.getDataNascimento())        || !contatoVenda.getDataNascimento().equals(contatoMailing.getDataNascimento()))
			|| (isEmpty(contatoVenda.getSexo())     || !contatoVenda.getSexo().equals(contatoMailing.getSexo()))
			|| (isEmpty(contatoVenda.getNumeroEndereco()) || !contatoVenda.getNumeroEndereco().equals(contatoMailing.getNumeroEndereco()))
			|| (isEmpty(contatoVenda.getRg()) || !contatoVenda.getRg().equals(contatoMailing.getRg()))
			|| (isEmpty(contatoVenda.getComplementoEndereco()) || !contatoVenda.getComplementoEndereco().equals(contatoMailing.getComplementoEndereco()))
			|| (isEmpty(contatoVenda.getEmail()) || !contatoVenda.getEmail().equals(contatoMailing.getEmail()))
			|| (isEmpty(contatoVenda.getOrgaoExpedidor()) || !contatoVenda.getOrgaoExpedidor().equals(contatoMailing.getOrgaoExpedidor()))
			|| (isNull(contatoVenda.getDataExpedicao()) || !contatoVenda.getDataExpedicao().equals(contatoMailing.getDataExpedicao()))
			|| (isNull(contatoVenda.getFlagRecebeOfertasEmail()) || !contatoVenda.getFlagRecebeOfertasEmail().equals(contatoMailing.getFlagRecebeOfertasEmail()))
			|| (isNull(contatoVenda.getFlagRecebeCertificadoEmail()) || !contatoVenda.getFlagRecebeCertificadoEmail().equals(contatoMailing.getFlagRecebeCertificadoEmail()))
			|| (isEmpty(contatoVenda.getEmpresa())     || !contatoVenda.getEmpresa().equals(contatoMailing.getEmpresa()))
			|| (isEmpty(contatoVenda.getCnpj())     || !contatoVenda.getCnpj().equals(contatoMailing.getCnpj()))) {
		
			return true;
		}
		
		return false;
	}
	
	private boolean isEmpty(String value){
		return StringUtils.isEmpty(value);
	}
	
	private boolean isNull(Object value){
		return value == null;
	}
	
	@Override
	public void atualizarContatoMailing(ContatoMailing contatoVenda,	ContatoMailing contatoMailing, 
			String usuarioHost,	String usuarioLogado,  List<TelefoneCliente> telefones) throws ServiceException {

		//Boolean houveModificacao = compararContatosMailing(contatoVenda, contatoMailing, telefones);
		
		contatoMailing.setCidade(contatoVenda.getCidade());
		contatoMailing.setUf(contatoVenda.getUf());
		contatoMailing.setEndereco(contatoVenda.getEndereco());
		contatoMailing.setBairro(contatoVenda.getBairro());
		contatoMailing.setCep(CepUtils.unmask(contatoVenda.getCep()));
		contatoMailing.setCidade(contatoVenda.getCidade());
		contatoMailing.setNome(contatoVenda.getNome());
		contatoMailing.setDataNascimento(contatoVenda.getDataNascimento());
		contatoMailing.setSexo(contatoVenda.getSexo());
		contatoMailing.setNumeroEndereco(contatoVenda.getNumeroEndereco());
		contatoMailing.setRg(contatoVenda.getRg());
		contatoMailing.setComplementoEndereco(contatoVenda.getComplementoEndereco());
		contatoMailing.setDataExpedicao(contatoVenda.getDataExpedicao());
		contatoMailing.setEmail(contatoVenda.getEmail());
		contatoMailing.setOrgaoExpedidor(contatoVenda.getOrgaoExpedidor());
		contatoMailing.setFlagRecebeCertificadoEmail(contatoVenda.getFlagRecebeCertificadoEmail());
		contatoMailing.setFlagRecebeOfertasEmail(contatoVenda.getFlagRecebeOfertasEmail());
		contatoMailing.setEmpresa(contatoVenda.getEmpresa());
		contatoMailing.setCnpj(contatoVenda.getCnpj());
		
		if(telefones != null){
			for (TelefoneCliente telefone : telefones) {
				if((telefone.getDdd() + telefone.getTelefone()).equals(contatoMailing.getFoneCelular())){
					contatoMailing.setFlagRecebeOfertasSmsCel(telefone.getFlagRecebeSms());
					continue;
				}
				if((telefone.getDdd() + telefone.getTelefone()).equals(contatoMailing.getFoneResidencial())){
					contatoMailing.setFlagRecebeOfertasSmsRes(telefone.getFlagRecebeSms());
					continue;
				}
				if((telefone.getDdd() + telefone.getTelefone()).equals(contatoMailing.getFoneComercial())){
					contatoMailing.setFlagRecebeOfertasSmsCom(telefone.getFlagRecebeSms());
					continue;
				}
			}
		}
		
		update(contatoMailing, usuarioHost, usuarioLogado);
		
	}

}
