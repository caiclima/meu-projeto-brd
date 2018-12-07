package br.com.callink.bradesco.seguro.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import br.com.callink.bradesco.seguro.commons.utils.PropertiesUtils;
import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.dao.IEmailDAO;
import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.Email;
import br.com.callink.bradesco.seguro.service.IEmailService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;
import br.com.callink.bradesco.seguro.service.utils.Constantes;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponseFactory;

@Stateless
@Local(IEmailService.class)
public class EmailService extends GenericCrudServiceImpl<Email> implements IEmailService<Email> {
    
	
	@Inject
	private IEmailDAO emailDAO;
	
	@Override
	protected IEmailDAO getDAO() {
		return emailDAO;
	}
	
	@Override
	protected void antesSalvar(Email email) throws ServiceException {
		
		email.setDataEnvio(new Date());
		email.setLogTransaction(BigInteger.valueOf(System.currentTimeMillis()));
		email.setFlagDesativado(Boolean.FALSE);
	}
	
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ServiceResponse save(Email object, String usuarioHost, String usuarioLogado) throws ServiceException {
    	antesSalvar(object);
        if (!StringUtils.isEmpty(object.getMensagemHtml().toString()) && object.getConteudo() == null) {
            object.setConteudo(object.getMensagemHtml().toString());
        } else if (!StringUtils.isEmpty(object.getMensagemTxt().toString()) && object.getConteudo() == null) {
            object.setConteudo(object.getMensagemTxt().toString());
        }
        object.setDataEnvio(new Date());
        return super.save(object);
    }
    
    
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ServiceResponse findEmailsEnvioPendente() throws ServiceException {
    	
    	List<Email> emailList = new ArrayList<Email>();
		ServiceResponse response = null;
		Email email = new Email();
		email.setFlagEnvioPendente(Boolean.TRUE);
		email.setFlagEnvio(Boolean.TRUE);
		email.setFlagErroEnvio(Boolean.FALSE);
		emailList = (List<Email>) getDAO().findByExample(email);
		response = ServiceResponseFactory.createWithData(emailList);
		
		return response;
		
    }
    
    @Override
    public Boolean existemEmailsNaoLidos(List<Email> emails) throws ServiceException {
        Boolean retorno = Boolean.FALSE;
        if (emails == null || emails.isEmpty()) {
            return retorno;
        }
        for (Email email : emails) {
            if (email.getFlagLido() != null && !email.getFlagLido()) {
                retorno = Boolean.TRUE;
            }
        }
        return retorno;
    }

    
    @Override
    public Properties enviaEmailProperties() {
        Properties props = PropertiesUtils.getPropertieByNomeArquivo(Constantes.CAMINHO_ARQUIVO_PROPERTIES_CONFIGURACAO_TELA + Constantes.NOME_ARQUIVO_PROPERTIE);
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", props.getProperty(Constantes.PARAMETRO_EMAIL_SMTPHOST));
        props.put("mail.smtp.auth", String.valueOf(Boolean.TRUE));
        return props;
    }
    
    @Override
    public Properties recebeEmailProperties() {
        return PropertiesUtils.getPropertieByNomeArquivo(Constantes.CAMINHO_ARQUIVO_PROPERTIES_CONFIGURACAO_TELA + Constantes.NOME_ARQUIVO_PROPERTIE);
    }
    
    @Override
    public ServiceResponse update(Email email) throws ServiceException {
        if (email == null || email.getId() == null) {
            throw new ServiceException("Erro ao atualizar email.");
        }
        return super.update(email);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateFlagLido(Email email) throws ServiceException {
        try {
            getDAO().updateFlagLido(email);
        } catch (DataException ex) {
            throw new ServiceException("Erro ao realizar o update no flag lido.", ex);
        }
    }

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateFlagDesativado(Email email) throws ServiceException {
		try {
			getDAO().updateFlagDesativado(email);
		} catch (Exception e) {
			throw new ServiceException("Erro ao realizar update no email.",e);
		}
	}

	@Override
	public void envia(Email email) throws ServiceException, ValidationException {
		try {
            email.setFlagEnvio(Boolean.TRUE);
            email.setFlagEnvioPendente(Boolean.TRUE);
            email.setDataEnvio(new Date());
            email.setFlagErroEnvio(Boolean.FALSE);
            email.setFlagLido(Boolean.TRUE);
            email.setConteudo(new String(email.getConteudo().getBytes("UTF-8"), "UTF-8"));
            this.save(email);
            
//            String descricaoGruposEmail = "";
//            
//            if (email.getListaDestinatarios() != null && !email.getListaDestinatarios().isEmpty()) {
//                descricaoGruposEmail = grupoEmailService.getDescricaoFromListaGrupoEmail(email.getListaDestinatarios());
//                emailGrupoEmailService.salvaDestinatariosEmail(email, email.getListaDestinatarios());
//            }
//
//            //se for email resposta
//            if (email.getPai() != null && email.getPai().getId() != null) {
//                email.setDescricaoLog("Email respondido de: " + email.getPai().getRemetente());
//                
//                if (!StringUtils.isEmpty(descricaoGruposEmail)) {
//                    email.setDescricaoLog(email.getDescricaoLog() + " - Grupos de email adicionados na resposta: " + descricaoGruposEmail);
//                }
//                
//            } else {
//                email.setDescricaoLog("Email enviado para departamento(s): " + descricaoGruposEmail);
//            }
            
        } catch (UnsupportedEncodingException ex) {
            throw new ServiceException("Erro ao realizar o encode do conteúdo.",ex);
        }
        
    }

	@Override
	public void salvaReceivedMails(List<Email> emails) throws ServiceException,
			ValidationException {
		
	}

}
