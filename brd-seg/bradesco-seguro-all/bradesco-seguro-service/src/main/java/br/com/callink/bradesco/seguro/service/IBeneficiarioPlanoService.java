package br.com.callink.bradesco.seguro.service;

import br.com.callink.bradesco.seguro.entity.BeneficiarioPlano;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

public interface IBeneficiarioPlanoService<T> extends IGenericCrudService<T> {

	ServiceResponse salvarOuAtualizar(BeneficiarioPlano beneficiarioPlano) throws ServiceException;
}
