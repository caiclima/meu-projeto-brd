package br.com.callink.bradesco.seguro.dao.impl;

import java.util.List;

import br.com.callink.bradesco.seguro.dao.INuvemDadosTratativaDAO;
import br.com.callink.bradesco.seguro.entity.NuvemDadosTratativa;

public class NuvemDadosTratativaDAO 
		extends GenericHibernateDAOImpl<NuvemDadosTratativa> 
		implements INuvemDadosTratativaDAO {

	@Override
	public void callStoredProcedure(String spName, List<Object> params) {
		super.callStoredProcedure(spName, params);
		
	}

}
