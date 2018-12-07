package br.com.callink.bradesco.seguro.dao;

import java.util.Date;

import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.NuvemVendasRegistroHeaderPplus;

public interface INuvemVendaRegistroHeaderPPlusDAO extends IGenericDAO<NuvemVendasRegistroHeaderPplus>  {

	public NuvemVendasRegistroHeaderPplus insertNuvemVendaRegistroHeaderPPlus(Date dataEnvio) throws DataException;
	
	public void updateNuvemVendaRegistroHeaderPPlus(NuvemVendasRegistroHeaderPplus nuvemVendasRegistroHeaderPplus) throws DataException;


}
