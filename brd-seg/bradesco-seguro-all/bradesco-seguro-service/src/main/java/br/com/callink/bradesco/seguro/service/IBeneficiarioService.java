package br.com.callink.bradesco.seguro.service;

import br.com.callink.bradesco.seguro.entity.Beneficiario;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

public interface IBeneficiarioService<T> extends IGenericCrudService<T> {

	ServiceResponse salvarOuAtualizar(Beneficiario beneficiario,
			String usuarioHost, String usuarioLogado) throws ServiceException;

	ServiceResponse remover(Beneficiario beneficiario) throws ServiceException;

}
