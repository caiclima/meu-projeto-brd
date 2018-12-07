package br.com.callink.bradesco.seguro.service.impl;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.callink.bradesco.seguro.commons.utils.CollectionUtils;
import br.com.callink.bradesco.seguro.dao.IGenericDAO;
import br.com.callink.bradesco.seguro.dao.IPessoaColaboradorDAO;
import br.com.callink.bradesco.seguro.entity.PessoaColaborador;
import br.com.callink.bradesco.seguro.service.IPessoaColaboradorService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponseFactory;

@Stateless
@Local(IPessoaColaboradorService.class)
public class PessoaColaboradorService extends
		GenericCrudServiceImpl<PessoaColaborador> implements
		IPessoaColaboradorService<PessoaColaborador> {

	@Inject
	private IPessoaColaboradorDAO dao;

	@Override
	protected IGenericDAO<PessoaColaborador> getDAO() {
		return dao;
	}

	@Override
	public ServiceResponse buscarTodasPessoasColaborador()
			throws ServiceException {

		List<PessoaColaborador> pessoasColaborador = getDAO().findAll();
		ServiceResponse serviceResponse = ServiceResponseFactory
				.createWithData(pessoasColaborador);

		if (CollectionUtils.isEmpty(pessoasColaborador)) {
			serviceResponse.addWarning("Nenhum registro encontrado.");
		}
		return serviceResponse;
	}

	@Override
	public ServiceResponse buscarPessoaColaboradorPorId(
			BigInteger idPessoaColaborador) throws ServiceException {

		validarVazio(idPessoaColaborador, "Informe o id da Pessoa Colaborador.");

		PessoaColaborador pessoaColaborador = getDAO().findByPK(
				idPessoaColaborador);
		ServiceResponse serviceResponse = ServiceResponseFactory
				.createWithData(pessoaColaborador);

		if (pessoaColaborador == null) {
			serviceResponse.addWarning("Nenhum registro encontrado.");
		}
		return serviceResponse;
	}

	@Override
	public ServiceResponse salvar(PessoaColaborador pessoaColaborador)
			throws ServiceException {

		antesSalvar(pessoaColaborador);

		getDAO().save(pessoaColaborador);

		ServiceResponse serviceResponse = ServiceResponseFactory
				.createWithData(pessoaColaborador);
		serviceResponse.addInfo("Registro salvo com sucesso.");

		return serviceResponse;
	}

	@Override
	protected void antesSalvar(PessoaColaborador pessoaColaborador)
			throws ServiceException {

		validarVazio(pessoaColaborador, "Informe a Pessoa Colaborador");
	}

	@Override
	public ServiceResponse atualizar(PessoaColaborador pessoaColaborador)
			throws ServiceException {

		antesAtualizar(pessoaColaborador);

		getDAO().update(pessoaColaborador);

		ServiceResponse serviceResponse = ServiceResponseFactory
				.createWithData(pessoaColaborador);
		serviceResponse.addInfo("Registro salvo com sucesso.");

		return serviceResponse;
	}

	@Override
	protected void antesAtualizar(PessoaColaborador pessoaColaborador)
			throws ServiceException {
		antesSalvar(pessoaColaborador);
	}

	@Override
	public ServiceResponse remover(PessoaColaborador pessoaColaborador)
			throws ServiceException {

		antesExcluir(pessoaColaborador);

		getDAO().delete(pessoaColaborador);

		ServiceResponse serviceResponse = ServiceResponseFactory
				.createWithData(pessoaColaborador);
		serviceResponse.addInfo("Registro removido com sucesso.");

		return serviceResponse;
	}

	@Override
	protected void antesExcluir(PessoaColaborador pessoaColaborador)
			throws ServiceException {
		antesSalvar(pessoaColaborador);
	}

	private void validarVazio(Object obj, String mensagem)
			throws ServiceException {

		if (obj == null) {
			mensagemErro(mensagem);
		}
	}

	private ServiceException mensagemErro(String mensagem)
			throws ServiceException {
		throw new ServiceException(mensagem);
	}

}
