package br.com.callink.bradesco.seguro.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import br.com.callink.bradesco.seguro.dao.IEmailDAO;
import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.Email;


public class EmailDAO extends GenericHibernateDAOImpl<Email> implements IEmailDAO {
    

    @Override
    public void updateFlagLido(Email email) throws DataException {
        try {
            Query query = getEntityManager().createNativeQuery(" update tb_email set flag_lido = 1 where id_email = :idEmail ");
            query.setParameter("idEmail", email.getId());
            query.executeUpdate();
        } catch (Exception ex) {
            throw new DataException("Erro ao realizar update no flag lido.",ex);
        }
    }
    
    @Override
    public void updateFlagDesativado(Email email) throws DataException {
        try {
            Query query = getEntityManager().createNativeQuery(" update tb_email set FLAG_DESATIVADO = 1 where id_email = :idEmail ");
            query.setParameter("idEmail", email.getId());
            query.executeUpdate();
        } catch (Exception ex) {
            throw new DataException("Erro ao realizar update no flag desativado.",ex);
        }
    }

}
