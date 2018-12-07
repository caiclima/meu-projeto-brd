package br.com.callink.bradesco.seguro.service;

import java.math.BigInteger;

import br.com.callink.bradesco.seguro.entity.PessoaColaborador;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

public interface IPessoaColaboradorService<T> extends IGenericCrudService<T> {

	ServiceResponse buscarTodasPessoasColaborador() throws ServiceException;

	ServiceResponse buscarPessoaColaboradorPorId(BigInteger idPessoaColaborador)
			throws ServiceException;

	ServiceResponse salvar(PessoaColaborador pessoaColaborador)
			throws ServiceException;

	ServiceResponse atualizar(PessoaColaborador pessoaColaborador)
			throws ServiceException;

	ServiceResponse remover(PessoaColaborador pessoaColaborador)
			throws ServiceException;
}
