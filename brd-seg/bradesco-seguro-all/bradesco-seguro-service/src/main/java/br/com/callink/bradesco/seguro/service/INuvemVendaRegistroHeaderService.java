package br.com.callink.bradesco.seguro.service;

import java.util.Date;

import br.com.callink.bradesco.seguro.entity.NuvemVendasRegistroHeader;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;

public interface INuvemVendaRegistroHeaderService extends IGenericCrudService<NuvemVendasRegistroHeader> {

	NuvemVendasRegistroHeader geraNuvemVendaRegistroHeader(Date dataEnvio) throws ServiceException;
	
//	BigInteger geraNuvemVendaRegistroHeaderPPlus(Date dataEnvio) throws ServiceException;

}
