package br.com.callink.bradesco.seguro.service;

import java.math.BigInteger;

import br.com.callink.bradesco.seguro.entity.Plano;
import br.com.callink.bradesco.seguro.entity.Produto;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

public interface IPlanoService<T> extends IGenericCrudService<T> {

	ServiceResponse buscarTodosPlanos() throws ServiceException;

	ServiceResponse buscarPlanoPorId(BigInteger idPlano)
			throws ServiceException;

	ServiceResponse buscarPorExemplo(Plano plano) throws ServiceException;

	ServiceResponse salvar(Plano plano, String usuarioHost, String usuarioLogado)
			throws ServiceException;

	ServiceResponse remover(Plano plano) throws ServiceException;

	ServiceResponse buscarPlanoPorProdutoAndIdade(Produto produto, String idade)
			throws ServiceException;
}
