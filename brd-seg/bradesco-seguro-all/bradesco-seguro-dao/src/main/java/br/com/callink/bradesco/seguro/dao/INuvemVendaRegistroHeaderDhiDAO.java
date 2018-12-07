package br.com.callink.bradesco.seguro.dao;

import java.util.Date;

import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.NuvemVendasRegistroHeaderDhi;

public interface INuvemVendaRegistroHeaderDhiDAO extends IGenericDAO<NuvemVendasRegistroHeaderDhi>  {

	public NuvemVendasRegistroHeaderDhi insertNuvemVendaRegistroHeaderDhi(Date dataEnvio) throws DataException;
	
	public void updateNuvemVendaRegistroHeaderDhi(NuvemVendasRegistroHeaderDhi nuvemVendasRegistroHeaderDhi) throws DataException;

}
