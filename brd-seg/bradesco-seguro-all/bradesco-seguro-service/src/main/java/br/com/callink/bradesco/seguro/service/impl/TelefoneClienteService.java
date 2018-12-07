package br.com.callink.bradesco.seguro.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.w3c.dom.Node;

import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.dao.ITelefoneClienteDAO;
import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.ParametroSistema;
import br.com.callink.bradesco.seguro.entity.TelefoneCliente;
import br.com.callink.bradesco.seguro.service.IParametroSistemaService;
import br.com.callink.bradesco.seguro.service.ITelefoneClienteService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

@Stateless
public class TelefoneClienteService extends GenericCrudServiceImpl<TelefoneCliente> implements ITelefoneClienteService<TelefoneCliente> {

	private static String ERRO_CONSULTA;
		
	public static final String TAG_TELEFONES = "/telefones";
	
	public static final String TAG_TELEFONE = "/telefone";
	
	public static final String TAG_NM_CONTATO = "nm_contato";
	
	public static final String TAG_DS_VALOR = "ds_valor";
	
	@EJB
	private IParametroSistemaService<ParametroSistema> parametroSistemaService;
	
	public TelefoneClienteService() throws ServiceException {
		try {
			//TODO bradesco.seguro
//			TelefoneClienteService.ERRO_CONSULTA = BundleHelper
//				.getMessageWithParam("gerror_problemasConsulta", MSG_BUNDLE, 
//						new Object[] {"Telefone Cliente"} );
			TelefoneClienteService.ERRO_CONSULTA = "MSG";
		} catch (Exception e) {
			
			TelefoneClienteService.ERRO_CONSULTA = "Erro consulta Telefone Cliente";
		}
	}
	
	@Inject
	private ITelefoneClienteDAO telefoneClienteDAO;
	
	@Override
	protected ITelefoneClienteDAO getDAO() {
		return telefoneClienteDAO;
	}
	
	@Override
	public List<TelefoneCliente> findTelefoneByClienteCampanha(
			BigInteger idClienteCampanha) throws ServiceException,
			ValidationException {
		try{
			if(idClienteCampanha == null) {
				throw new ValidationException(TelefoneClienteService.ERRO_CONSULTA);
			}
			return getDAO().findTelefonesCadastrados(idClienteCampanha);
		}catch(DataException e){
			throw new ServiceException(e);
		}
	}
	
	@Override
	public List<TelefoneCliente> findTelefoneByClienteCampanha(
			BigInteger idClienteCampanha, int limit) throws ServiceException,
			ValidationException {
		try{
			if(idClienteCampanha == null || limit == 0) {
				throw new ValidationException(TelefoneClienteService.ERRO_CONSULTA);
			}
			List<TelefoneCliente> lista = limitTelefones(getDAO().findTelefonesCadastrados(idClienteCampanha), limit);
			for(int i = limit - lista.size(); i > 0; i--) {
				lista.add(new TelefoneCliente());
			}
			return lista;
		}catch(DataException e){
			throw new ServiceException(e);
		}
	}
	

    private List<TelefoneCliente> limitTelefones(List<TelefoneCliente> telefones, int limit) {
		for(int i = telefones.size(); i < limit; i++) {
			telefones.add(new TelefoneCliente());
		}
		return telefones;
	}
	
//	@Override
//	public ServiceResponse buscaTelefoneCliente(String cnpj) throws ServiceException {
//		
//		ServiceResponse serviceResponse = new ServiceResponse();
//
//		logger.info("Consultando Telefone Cliente...");
//		
//		ParametroSistema wsdl = parametroSistemaService.buscarPorNome(ParametroSistemaEnum.PARAMETRO_URL_WSDL_WSPARCELA_TRIBANCO.value());
//		
//		if (wsdl == null) {
//			throw new ServiceException("Parametro de Sistema [" + ParametroSistemaEnum.PARAMETRO_URL_WSDL_WSPARCELA_TRIBANCO.value() + "] representando URL do WebServide de Parcelas Tribanco nao existe. ");
//		}
//		
//		List<TelefoneCliente> telefoneClienteList = new ArrayList<TelefoneCliente>();
//
//		try {
//		
//			WsParcelaTNTSoap webservice = WebServiceClientFactory.create(WebService.WSParcela, wsdl.getValorParametroSistema());
//			
//			Holder<String> msgErroTelefone = new Holder<String>();
//			
//			Holder<String> telefoneResult = new Holder<String>();
//
//			NodeList nodeListChild;
//			String valor;
//			TelefoneCliente telefoneCliente = null;
//	
//			logger.info("Enviando requisicao para Web Service...");
//			
//			webservice.telefone(0l, cnpj, msgErroTelefone, telefoneResult);
//			
//			if(!StringUtils.isEmpty(msgErroTelefone.value)){
//				throw new ServiceException("Busca Telefone - cnpj: "+cnpj+"Error: " + msgErroTelefone.value);
//			}
//			
//			String buscaTelefone = XMLUtils.normalize(telefoneResult.value);
//			
//			XPathUtil xPathUtil = new XPathUtil(buscaTelefone);
//			NodeList nodeList = xPathUtil.getNodeList(TAG_TELEFONES+TAG_TELEFONE);
//			
//			if (nodeList != null) {
//				
//				for (int i = 0; i < nodeList.getLength(); i++) {
//					
//					nodeListChild = nodeList.item(i).getChildNodes();
//					telefoneCliente = null;
//					
//					for (int j = 0; j <nodeListChild.getLength(); j++) {
//						
//						valor = retornaDadoTag(TAG_DS_VALOR, nodeListChild.item(j));
//						
//						if (valor != null) {
//							telefoneCliente = validarTelefone(valor);
//							continue;
//						}
//						
//					}
//					
//					if (telefoneCliente != null) {
//						telefoneClienteList.add(telefoneCliente);
//					}
//				
//				}
//
//				for (TelefoneCliente telefoneClienteFor:telefoneClienteList) {
//					telefoneClienteFor.setFlagNovo(Boolean.TRUE);
//					telefoneClienteFor.setFlagEnabled(Boolean.TRUE);
//					telefoneClienteFor.setDataCadastro(new Date());
//					telefoneClienteFor.setFlagBloqueadoProcon(Boolean.FALSE);
//					telefoneClienteFor.setFlagPreferencial(Boolean.FALSE);
//					telefoneClienteFor.setFlagInvalido(Boolean.FALSE);
//					telefoneClienteFor.setSequencia(BigInteger.ZERO);
//				}
//				
//			}
//	
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			throw new ServiceException(e);
//		}
//	
//		logger.info("Consulta realizada!");
//		
//		if (telefoneClienteList.size() == 0) {
//			serviceResponse.addWarning("Nenhum telefone encontrado ou todos estão inválidos para o cnpj: "+cnpj, new Object[]{});
//		} else {
//			serviceResponse.setData(telefoneClienteList);
//		}
//
//		return serviceResponse;
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
    
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ServiceResponse atualizarTelefoneCliente(String cnpj,BigInteger idClienteCampanha,String usuarioHost,String usuarioLogado) throws ServiceException {

		try {
			if (StringUtils.isEmpty(cnpj)) {
				throw new ServiceException("Cnpj vazio!");
			}
			
			//TODO verificar implementacao
			//ServiceResponse serviceResponse = buscaTelefoneCliente(cnpj);
			ServiceResponse serviceResponse = null;
			
			if (serviceResponse.getMessages() != null && !serviceResponse.getMessages().isEmpty()) {
				return serviceResponse;
			}
			
			List<TelefoneCliente> telefoneClienteWebServiceList = (List<TelefoneCliente>) serviceResponse.getData();
			
			List<TelefoneCliente> telefoneClienteList = findTelefoneByClienteCampanha(idClienteCampanha);//getDAO().buscarTelefoneCliente(cnpj);
			
			List<TelefoneCliente> telefoneClienteNovoList = new ArrayList<TelefoneCliente>();
			
			if (telefoneClienteList.size() == 0) {
				
				for (TelefoneCliente telefoneCliente: telefoneClienteWebServiceList) {
					setLogPojo(telefoneCliente, usuarioHost, usuarioLogado);
					telefoneCliente.setIdClienteCampanha(idClienteCampanha);
					telefoneCliente.setLogTransaction(BigInteger.valueOf(System.currentTimeMillis()));
					getDAO().save(telefoneCliente);
					telefoneClienteNovoList.add(telefoneCliente);
				}
				
			} else {
				
				Boolean existeTelefone = Boolean.FALSE;
				
				for (TelefoneCliente telefoneClienteWebService: telefoneClienteWebServiceList) {
					
					existeTelefone = Boolean.FALSE;
					
					for (TelefoneCliente telefoneClienteBase: telefoneClienteList) {
						
						if (telefoneClienteBase.getDdd().concat(telefoneClienteBase.getTelefone()).equals(telefoneClienteWebService.getDdd().concat(telefoneClienteWebService.getTelefone()))) {
							existeTelefone = Boolean.TRUE;
						}
					}
					
					if (!existeTelefone) {
						setLogPojo(telefoneClienteWebService, usuarioHost, usuarioLogado);
						telefoneClienteWebService.setIdClienteCampanha(idClienteCampanha);
						telefoneClienteWebService.setLogTransaction(BigInteger.valueOf(System.currentTimeMillis()));
						getDAO().save(telefoneClienteWebService);
						telefoneClienteNovoList.add(telefoneClienteWebService);
					}
				}
			}
			
			if (telefoneClienteNovoList.size() > 0) {
				serviceResponse.setData(telefoneClienteNovoList);
				serviceResponse.addInfo("Novos Telefones Salvos com sucesso!", new Object[]{});
			} else {
				serviceResponse.addInfo("Nenhum Telefone novo para salvar!", new Object[]{});
			}
			
			return serviceResponse;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
		
		
    }


}
