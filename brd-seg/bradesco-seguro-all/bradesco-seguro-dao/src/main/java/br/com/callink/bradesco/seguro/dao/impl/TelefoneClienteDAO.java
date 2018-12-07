package br.com.callink.bradesco.seguro.dao.impl;

import java.math.BigInteger;
import java.util.List;

import br.com.callink.bradesco.seguro.dao.ITelefoneClienteDAO;
import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.TelefoneCliente;

/**
 * Data Access Object (DAO) de Telefone Cliente.
 * 
 * @author swb.thiagohenrique
 *
 */
public class TelefoneClienteDAO extends GenericHibernateDAOImpl<TelefoneCliente> implements ITelefoneClienteDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<TelefoneCliente> findTelefonesCadastrados(BigInteger idClienteCampanha) throws DataException {

		try {

			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("SELECT telefoneCliente FROM TelefoneCliente AS telefoneCliente ");
			stringBuilder.append("INNER JOIN FETCH telefoneCliente.clienteCampanha AS clienteCampanha ");
			stringBuilder.append("WHERE ");
			//stringBuilder.append(" clienteCampanha.flagEnabled = :clienteCampanhaFlagEnabled AND ");
			stringBuilder.append("clienteCampanha.id = :idClienteCampanha");

			javax.persistence.Query query = getEntityManager().createQuery(stringBuilder.toString());
			//query.setParameter("clienteCampanhaFlagEnabled", Boolean.TRUE);
			query.setParameter("idClienteCampanha", idClienteCampanha);
			
			return (List<TelefoneCliente>) query.getResultList();
			
		} catch (Exception e) {
			throw new DataException(e);
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TelefoneCliente> buscarTelefoneCliente(String cnpj) throws DataException {

		try {

			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("SELECT telefoneCliente FROM TelefoneCliente AS telefoneCliente ");
			stringBuilder.append("INNER JOIN FETCH telefoneCliente.clienteCampanha AS clienteCampanha ");
			stringBuilder.append("INNER JOIN FETCH clienteCampanha.contatoMailing AS contatoMailing ");
			stringBuilder.append("WHERE clienteCampanha.flagEnabled = :clienteCampanhaFlagEnabled AND ");
			stringBuilder.append("contatoMailing.cnpj = :cnpj");

			javax.persistence.Query query = getEntityManager().createQuery(stringBuilder.toString());
			query.setParameter("clienteCampanhaFlagEnabled", Boolean.TRUE);
			query.setParameter("cnpj", cnpj);
			
			return (List<TelefoneCliente>) query.getResultList();
			
		} catch (Exception e) {
			throw new DataException(e);
		}
	}

}
