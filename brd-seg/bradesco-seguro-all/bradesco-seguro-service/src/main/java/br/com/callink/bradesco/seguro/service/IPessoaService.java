package br.com.callink.bradesco.seguro.service;

import java.math.BigInteger;

import br.com.callink.bradesco.seguro.entity.Pessoa;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

public interface IPessoaService<T> extends IGenericCrudService<T> {

	ServiceResponse buscarTodasPessoas() throws ServiceException;

	ServiceResponse buscarPessoaPorId(BigInteger idPessoa)
			throws ServiceException;

	ServiceResponse salvar(Pessoa pessoa) throws ServiceException;

	ServiceResponse atualizar(Pessoa pessoa) throws ServiceException;

	ServiceResponse remover(Pessoa pessoa) throws ServiceException;
	
	ServiceResponse buscarPessoasComPessoaColaborador() throws ServiceException;
	
	BigInteger buscarIdPessoa(BigInteger idPessoaCorporativo) throws ServiceException;

}
