package br.com.callink.bradesco.seguro.service;

import br.com.callink.bradesco.seguro.entity.NuvemDadosTratativa;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;

public interface INuvemDadosTratativaService extends IGenericCrudService<NuvemDadosTratativa> {
	
	public void gerarNuvemDadosTratativas() throws ServiceException;

}
