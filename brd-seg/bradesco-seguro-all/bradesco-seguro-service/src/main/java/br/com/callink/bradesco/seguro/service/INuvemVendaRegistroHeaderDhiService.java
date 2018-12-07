package br.com.callink.bradesco.seguro.service;

import java.util.Date;

import br.com.callink.bradesco.seguro.entity.NuvemVendasRegistroHeaderDhi;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;

public interface INuvemVendaRegistroHeaderDhiService extends IGenericCrudService<NuvemVendasRegistroHeaderDhi> {

	NuvemVendasRegistroHeaderDhi geraNuvemVendaRegistroHeaderDhi(Date dataEnvio) throws ServiceException;

}
