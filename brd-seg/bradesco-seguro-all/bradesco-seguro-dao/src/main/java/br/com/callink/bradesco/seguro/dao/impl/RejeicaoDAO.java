package br.com.callink.bradesco.seguro.dao.impl;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import br.com.callink.bradesco.seguro.dao.IRejeicaoDAO;
import br.com.callink.bradesco.seguro.entity.Rejeicao;

/**
 * Data Access Object (DAO) de Rejeicao.
 *
 * @author neppo.oldamar
 *
 */
public class RejeicaoDAO extends GenericHibernateDAOImpl<Rejeicao> implements IRejeicaoDAO {

    
    
    @SuppressWarnings("unchecked")
	@Override
    public List<Rejeicao> findRejeicaoByVenda(BigInteger idVenda) {
        Criteria criteria = createCriteria();
        criteria.add(Restrictions.eq("idVenda", idVenda));
        criteria.add(Restrictions.eq("flagTratado", Boolean.FALSE));
        return criteria.list();
    }

}
