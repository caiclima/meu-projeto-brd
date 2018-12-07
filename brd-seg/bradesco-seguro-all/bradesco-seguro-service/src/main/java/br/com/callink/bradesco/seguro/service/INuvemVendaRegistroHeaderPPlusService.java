package br.com.callink.bradesco.seguro.service;

import java.util.Date;

import br.com.callink.bradesco.seguro.entity.NuvemVendasRegistroHeaderPplus;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;

public interface INuvemVendaRegistroHeaderPPlusService extends IGenericCrudService<NuvemVendasRegistroHeaderPplus> {

	NuvemVendasRegistroHeaderPplus geraNuvemVendaRegistroHeaderPPlus(Date dataEnvio) throws ServiceException;
	

}
