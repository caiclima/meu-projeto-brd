package br.com.callink.bradesco.seguro.dao.impl;

import java.util.Date;

import org.hibernate.Query;

import br.com.callink.bradesco.seguro.dao.IVendaDAO;
import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.ParametroSistema;
import br.com.callink.bradesco.seguro.entity.Venda;

/**
 * Data Access Object (DAO) de Venda.
 * 
 * @author neppo.oldamar
 *
 */
public class VendaDAO extends GenericHibernateDAOImpl<Venda> implements IVendaDAO {
	
	@Override
	public void updateDataEnvioNuvem(Date dataEnvio) throws DataException {
		Query queryCommand = session().getNamedQuery("Venda.updateDataEnvioNuvem");
		queryCommand.setParameter("dataEnvio", dataEnvio);
		queryCommand.setParameter("referenciaVendaTipoEvento", ParametroSistema.NUVEM_VENDAS_REFERENCIA_VENDA);
		queryCommand.executeUpdate();
	}

}
