package br.com.callink.bradesco.seguro.service.impl;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.callink.bradesco.seguro.commons.utils.CollectionUtils;
import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.dao.IDominioDAO;
import br.com.callink.bradesco.seguro.entity.Dominio;
import br.com.callink.bradesco.seguro.service.IDominioService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponseFactory;

@Stateless
@Local(IDominioService.class)
public class DominioService extends GenericCrudServiceImpl<Dominio> implements IDominioService<Dominio> {

	@Inject
	private IDominioDAO dominioDAO;

	@Override
	protected IDominioDAO getDAO() {
		return dominioDAO;
	}

	@Override
	public ServiceResponse buscarTodosDominios() throws ServiceException {

		List<Dominio> dominios = getDAO().findAll();
		ServiceResponse serviceResponse = ServiceResponseFactory
				.createWithData(dominios);

		if (CollectionUtils.isEmpty(dominios)) {
			serviceResponse.addWarning("Nenhum registro encontrado.");
		}
		return serviceResponse;
	}

	@Override
	public ServiceResponse buscarDominioPorId(BigInteger idDominio)
			throws ServiceException {

		validarVazio(idDominio, "Informe o id do Dominio.");

		Dominio dominio = getDAO().findByPK(idDominio);
		ServiceResponse serviceResponse = ServiceResponseFactory
				.createWithData(dominio);

		if (dominio == null) {
			serviceResponse.addWarning("Nenhum registro encontrado.");
		}
		return serviceResponse;
	}

	@Override
	public ServiceResponse salvar(Dominio dominio) throws ServiceException {

		antesSalvar(dominio);

		getDAO().save(dominio);

		ServiceResponse serviceResponse = ServiceResponseFactory
				.createWithData(dominio);
		serviceResponse.addInfo("Registro salvo com sucesso.");

		return serviceResponse;
	}

	@Override
	protected void antesSalvar(Dominio dominio) throws ServiceException {

		validarVazio(dominio, "Informe o domínio.");
		//validarVazio(dominio.getId(), "Informe o id do domínio.");
		validarVazio(dominio.getDescricaoDominio(), "Informe a descrição do domínio.");
		validarVazio(dominio.getObsDominio(), "Informe a observação do domínio");
		validarVazio(dominio.getAtivo(), "Informe o IndAtivo do domínio");
	}

	@Override
	public ServiceResponse atualizar(Dominio dominio) throws ServiceException {

		antesAtualizar(dominio);

		getDAO().update(dominio);

		ServiceResponse serviceResponse = ServiceResponseFactory
				.createWithData(dominio);
		serviceResponse.addInfo("Registro salvo com sucesso.");

		return serviceResponse;
	}

	@Override
	protected void antesAtualizar(Dominio dominio) throws ServiceException {
		antesSalvar(dominio);
	}

	@Override
	public ServiceResponse remover(Dominio dominio) throws ServiceException {

		antesExcluir(dominio);

		getDAO().delete(dominio);

		ServiceResponse serviceResponse = ServiceResponseFactory
				.createWithData(dominio);
		serviceResponse.addInfo("Registro removido com sucesso.");

		return serviceResponse;
	}
	
	@Override
	public BigInteger buscarIdDominio(BigInteger idDominioCorporativo) throws ServiceException {
		return getDAO().findIdDominio(idDominioCorporativo);
	}

	@Override
	protected void antesExcluir(Dominio dominio) throws ServiceException {

		validarVazio(dominio, "Informe o domínio.");
		validarVazio(dominio.getId(), "Informe o id do domínio.");
	}

	private void validarVazio(Object obj, String mensagem)
			throws ServiceException {

		if (obj == null) {
			mensagemErro(mensagem);
		}
	}

	private void validarVazio(String obj, String mensagem)
			throws ServiceException {

		if (StringUtils.isEmpty(obj)) {
			mensagemErro(mensagem);
		}
	}

	private ServiceException mensagemErro(String mensagem)
			throws ServiceException {
		throw new ServiceException(mensagem);
	}

}
