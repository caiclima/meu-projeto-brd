package br.com.callink.bradesco.seguro.service.impl;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.callink.bradesco.seguro.commons.utils.CollectionUtils;
import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.dao.ICargoDAO;
import br.com.callink.bradesco.seguro.entity.Cargo;
import br.com.callink.bradesco.seguro.service.ICargoService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponseFactory;

/**
 * Representa a Camada de Serviço para o Domínio 'Cargo'
 * 
 * @author neppo.oldamar
 * 
 */
@Stateless
@Local(ICargoService.class)
public class CargoService extends GenericCrudServiceImpl<Cargo> implements
		ICargoService<Cargo> {

	@Inject
	private ICargoDAO dao;

	@Override
	protected ICargoDAO getDAO() {
		return dao;
	}

	@Override
	public ServiceResponse buscarTodosCargos() throws ServiceException {
		List<Cargo> all = getDAO().findAll();
		ServiceResponse response = ServiceResponseFactory.createWithData(all);

		if (CollectionUtils.isEmpty(all)) {
			return response.addWarning("Nenhum registro encontrado!");
		}

		return response;
	}

	@Override
	public ServiceResponse buscaCargoPorId(BigInteger idCargo)
			throws ServiceException {

		Cargo cargo = getDAO().findByPK(idCargo);

		ServiceResponse response = ServiceResponseFactory.createWithData(cargo);

		if (cargo == null) {
			return response.addWarning("Nenhum registro encontrado!");
		}

		return response;
	}

	@Override
	public ServiceResponse findByExample(Cargo entity) throws ServiceException {
		List<Cargo> all = getDAO().findByExample(entity);
		ServiceResponse response = ServiceResponseFactory.createWithData(all);

		if (CollectionUtils.isEmpty(all)) {
			return response.addWarning("Nenhum registro encontrado!");
		}

		return response;
	}

	@Override
	protected void antesAtualizar(Cargo cargo) throws ServiceException {
		_validar(cargo);
	}

	@Override
	protected void antesSalvar(Cargo cargo) throws ServiceException {
		_validar(cargo);
	}

	@Override
	public ServiceResponse save(Cargo cargo, String usuarioHost,
			String usuarioLogado) throws ServiceException {

		antesSalvar(cargo);

		// setLogPojo(cargo, usuarioHost, usuarioLogado);

		ServiceResponse serviceResponse = new ServiceResponse();
		getDAO().save(cargo);
		aposSalvar(cargo);

		serviceResponse = ServiceResponseFactory.createWithData(cargo);
		serviceResponse.addInfo("Registro salvo com sucesso.");

		return serviceResponse;
	}

	@Override
	public ServiceResponse update(Cargo cargo, String usuarioHost,
			String usuarioLogado) throws ServiceException {

		antesAtualizar(cargo);

		// setLogPojo(cargo,usuarioHost,usuarioLogado);

		ServiceResponse serviceResponse = new ServiceResponse();
		getDAO().update(cargo);
		aposAtualizar(cargo);

		serviceResponse = ServiceResponseFactory.createWithData(cargo);
		serviceResponse.addInfo("Registro atualizado com sucesso.");

		return serviceResponse;
	}

	@Override
	public ServiceResponse delete(Cargo cargo) throws ServiceException {

		antesExcluir(cargo);

		getDAO().delete(cargo);

		ServiceResponse serviceResponse = ServiceResponseFactory
				.createWithData(cargo);
		serviceResponse.addInfo("Registro removido com sucesso.");

		return serviceResponse;
	}

	@Override
	protected void antesExcluir(Cargo cargo) throws ServiceException {
		if (cargo == null) {
			throw new ServiceException("[Cargo] não informado para exclusão.");
		}

		if (cargo.getId() == null) {
			throw new ServiceException(
					"[Id do Cargo] não informado para exclusão.");
		}
	}

	private void _validar(Cargo cargo) throws ServiceException {
		if (cargo == null) {
			throw new ServiceException("[Cargo] não informado.");
		}

		if (StringUtils.isEmpty(cargo.getNomeCargo().trim())) {
			throw new ServiceException("[Nome do Cargo] não informado.");
		}

	}

	@Override
	public BigInteger buscarIdCargo(BigInteger idCargoCorporativo) throws ServiceException {
		return getDAO().findIdCargo(idCargoCorporativo);
	}
	
	@Override
	public ServiceResponse findCargosByIds(List<BigInteger> ids) throws ServiceException{
		List<Cargo> cargos = getDAO().findCargosByIds(ids);
		
		ServiceResponse serviceResponse = ServiceResponseFactory
				.createWithData(cargos);
		
		return serviceResponse;
	}

}