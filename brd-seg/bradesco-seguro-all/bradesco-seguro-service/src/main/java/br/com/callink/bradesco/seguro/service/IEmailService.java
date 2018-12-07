package br.com.callink.bradesco.seguro.service;

import java.util.List;
import java.util.Properties;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import br.com.callink.bradesco.seguro.entity.Email;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

public interface IEmailService<T> extends IGenericCrudService<T> {

	/**
	 * Envia um e-mail
	 * @param email
	 * @throws ServiceException
	 * @throws ValidationException 
	 */
    void envia(Email email) throws ServiceException, ValidationException;

    /**
     * Busca todos os e-mails com envio pendente
     * @return
     * @throws ServiceException
     */
    ServiceResponse findEmailsEnvioPendente() throws ServiceException;

    /**
     * 
     * @param emails
     * @throws ServiceException
     * @throws ValidationException 
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    void salvaReceivedMails(List<Email> emails) throws ServiceException, ValidationException;

    /**
     * 
     * @return 
     */
    Properties enviaEmailProperties();

    /**
     * 
     * @return 
     */
    Properties recebeEmailProperties();


    /**
     * 
     * @param email
     * @throws ServiceException 
     */
    void updateFlagLido(Email email) throws ServiceException;
    
    /**
     * Retira o caso da lista.
     * @param email
     * @throws ServiceException
     */
    void updateFlagDesativado(Email email) throws ServiceException;

	Boolean existemEmailsNaoLidos(List<Email> emails) throws ServiceException;


}
