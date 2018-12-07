package br.com.callink.bradesco.seguro.dao;

import java.util.Date;

import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.NuvemVendasRegistroHeader;

public interface INuvemVendaRegistroHeaderDAO extends IGenericDAO<NuvemVendasRegistroHeader>  {

	public NuvemVendasRegistroHeader insertNuvemVendaRegistroHeader(Date dataEnvio) throws DataException;
	
	public void updateNuvemVendaRegistroHeader(NuvemVendasRegistroHeader nuvemVendasRegistroHeader) throws DataException;

}
