package br.com.callink.bradesco.seguro.dao;

import java.util.List;

import br.com.callink.bradesco.seguro.entity.NuvemDadosTratativa;


public interface INuvemDadosTratativaDAO  extends IGenericDAO<NuvemDadosTratativa>{
	
	public void callStoredProcedure(String spName, List<Object> params);

}
