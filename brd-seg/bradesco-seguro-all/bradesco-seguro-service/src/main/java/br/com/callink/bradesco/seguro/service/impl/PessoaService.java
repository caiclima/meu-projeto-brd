package br.com.callink.bradesco.seguro.service.impl;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.callink.bradesco.seguro.commons.utils.CollectionUtils;
import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.dao.IPessoaDAO;
import br.com.callink.bradesco.seguro.entity.Pessoa;
import br.com.callink.bradesco.seguro.service.IPessoaService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponseFactory;

@Stateless
@Local(IPessoaService.class)
public class PessoaService extends GenericCrudServiceImpl<Pessoa> implements
		IPessoaService<Pessoa> {

	@Inject
	private IPessoaDAO pessoaDAO;

	@Override
	protected IPessoaDAO getDAO() {
		return pessoaDAO;
	}

	@Override
	public ServiceResponse buscarTodasPessoas() throws ServiceException {

		List<Pessoa> pessoas = getDAO().findAll();
		ServiceResponse serviceResponse = ServiceResponseFactory
				.createWithData(pessoas);

		if (CollectionUtils.isEmpty(pessoas)) {
			serviceResponse.addWarning("Nenhum registro encontrado.");
		}
		return serviceResponse;
	}

	@Override
	public ServiceResponse buscarPessoaPorId(BigInteger idPessoa)
			throws ServiceException {

		validarVazio(idPessoa, "Informe o id da Pessoa.");

		Pessoa pessoa = getDAO().findByPK(idPessoa);
		ServiceResponse serviceResponse = ServiceResponseFactory
				.createWithData(pessoa);

		if (pessoa == null) {
			serviceResponse.addWarning("Nenhum registro encontrado.");
		}
		return serviceResponse;
	}

	@Override
	public ServiceResponse salvar(Pessoa pessoa) throws ServiceException {

		antesSalvar(pessoa);

		getDAO().save(pessoa);

		ServiceResponse serviceResponse = ServiceResponseFactory
				.createWithData(pessoa);
		serviceResponse.addInfo("Registro salvo com sucesso.");

		return serviceResponse;
	}

	@Override
	protected void antesSalvar(Pessoa pessoa) throws ServiceException {

		validarVazio(pessoa, "Informe a Pessoa.");
		validarVazio(pessoa.getNomePessoa(), "Informe o nome da Pessoa.");
		validarVazio(pessoa.getCpfCnpj(), "Informe o cpf/cnpj da Pessoa.");
	}

	@Override
	public ServiceResponse atualizar(Pessoa pessoa) throws ServiceException {

		antesAtualizar(pessoa);

		getDAO().update(pessoa);

		ServiceResponse serviceResponse = ServiceResponseFactory
				.createWithData(pessoa);
		serviceResponse.addInfo("Registro salvo com sucesso.");

		return serviceResponse;
	}

	@Override
	protected void antesAtualizar(Pessoa pessoa) throws ServiceException {
		antesSalvar(pessoa);
	}

	@Override
	public ServiceResponse remover(Pessoa pessoa) throws ServiceException {

		antesExcluir(pessoa);

		getDAO().delete(pessoa);

		ServiceResponse serviceResponse = ServiceResponseFactory
				.createWithData(pessoa);
		serviceResponse.addInfo("Registro removido com sucesso.");

		return serviceResponse;
	}
	
	@Override
	public ServiceResponse buscarPessoasComPessoaColaborador() {
		ServiceResponse serviceResponse = ServiceResponseFactory.createWithData(getDAO().findPessoasWithPessoaColaborador());
		return serviceResponse;
	}
	
	@Override
	public BigInteger buscarIdPessoa(BigInteger idPessoaCorporativo) throws ServiceException {
		return getDAO().findIdPessoa(idPessoaCorporativo);
	}

	@Override
	protected void antesExcluir(Pessoa pessoa) throws ServiceException {

		validarVazio(pessoa, "Informe a Pessoa.");
		validarVazio(pessoa.getId(), "Informe o id da Pessoa.");
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
