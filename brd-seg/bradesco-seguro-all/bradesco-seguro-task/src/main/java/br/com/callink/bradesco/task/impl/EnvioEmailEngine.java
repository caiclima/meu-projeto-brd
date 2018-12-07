package br.com.callink.bradesco.task.impl;

import java.util.List;
import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.callink.bradesco.seguro.entity.Email;
import br.com.callink.bradesco.seguro.service.IEmailService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.task.IEnvioEmailEngine;
import br.com.callink.bradesco.task.exception.EmailException;
import br.com.callink.bradesco.task.utils.SendEmail;

@Stateless
public class EnvioEmailEngine implements IEnvioEmailEngine {

    
    @SuppressWarnings("rawtypes")
	@EJB
    private IEmailService emailService;
    
    public EnvioEmailEngine() {
    }

    @SuppressWarnings("unchecked")
	@Override
    public void run(Properties properties) {

        SendEmail sendEmail = null;
            try {
                sendEmail = new SendEmail(properties);

                ServiceResponse emailsEnvioPendente = emailService.findEmailsEnvioPendente();
                

                if (emailsEnvioPendente.getData() != null && !((List<Email>) emailsEnvioPendente.getData()).isEmpty()) {
                    for (Email email : (List<Email>) emailsEnvioPendente.getData()) {

                        Integer tentativasEnvio = 0;
                        //enqto a FlagEnvioPendente estiver ligada, tentar enviar
                        while (email.getFlagEnvioPendente()) {
                            try {
                                //se ja houveram mais de 5 tentativas de enviar o email..
                                if (tentativasEnvio > 5) {
                                    email.setFlagErroEnvio(Boolean.TRUE);
                                    emailService.update(email);
                                    //seta essa flag para sair do laço
                                    email.setFlagEnvioPendente(Boolean.FALSE);
                                    throw new ServiceException("Email excedeu tentativas de envio. ID Email: " + email.getId());
                                }
                                
                                sendEmail.send(email.getRemetente(), email.getDestinatario(), email.getAssunto(), email.getConteudo());
                                email.setFlagEnvioPendente(Boolean.FALSE);
                                email.setFlagErroEnvio(Boolean.FALSE);
                                emailService.update(email);
                            } catch (EmailException ex) {
                                //caso haja erro no envio de email, começa a incrementar tentativas de envio
//                                LOGGER.log(Level.SEVERE, "", ex);
                                tentativasEnvio++;
                            } 
                        }
                    }
                }
            } catch (ServiceException ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
    }
}
