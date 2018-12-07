package br.com.callink.bradesco.seguro.dao;

import javax.xml.rpc.ServiceException;

import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.Email;

public interface IEmailDAO extends IGenericDAO<Email>{
    

     /**
     * 
     * @param email
     * @throws ServiceException 
     */
    void updateFlagLido(Email email) throws DataException;
    

    /**
     * Destiva o e-mail do caso.
     * @param email
     * @throws DataException
     */
	void updateFlagDesativado(Email email) throws DataException;
	
}
