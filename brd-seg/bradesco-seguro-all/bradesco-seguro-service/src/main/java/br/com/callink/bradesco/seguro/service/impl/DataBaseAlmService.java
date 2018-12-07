package br.com.callink.bradesco.seguro.service.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.callink.bradesco.seguro.dao.IDataBaseAlmDAO;
import br.com.callink.bradesco.seguro.entity.DataBaseAlm;
import br.com.callink.bradesco.seguro.service.IDataBaseAlmService;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;

@Stateless
public class DataBaseAlmService extends GenericCrudServiceImpl<DataBaseAlm> implements IDataBaseAlmService {
	
	@Inject
	private IDataBaseAlmDAO dataBaseAlmDAO;
	
	@Override
	protected IDataBaseAlmDAO getDAO() {
		return dataBaseAlmDAO;
	}
	
	protected void validarSave(DataBaseAlm object)
			throws ValidationException {
		
	}

	protected void validarUpdate(DataBaseAlm object)
			throws ValidationException {
		
	}

	protected void validarDelete(DataBaseAlm object)
			throws ValidationException {
		
	}

}
