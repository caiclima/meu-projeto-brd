package br.com.callink.bradesco.seguro.service;

import java.math.BigInteger;

import br.com.callink.bradesco.seguro.entity.Esporte;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

public interface IEsporteService<T> extends IGenericCrudService<T> {

	ServiceResponse buscarTodosEsportes() throws ServiceException;

	ServiceResponse buscarEsportePorId(BigInteger idEsporte)
			throws ServiceException;

	ServiceResponse buscarPorExemplo(Esporte esporte) throws ServiceException;

	ServiceResponse salvar(Esporte esporte, String usuarioHost,
			String usuarioLogado) throws ServiceException;

	ServiceResponse atualizar(Esporte esporte, String usuarioHost,
			String usuarioLogado) throws ServiceException;

	ServiceResponse remover(Esporte esporte, String usuarioHost,
			String usuarioLogado) throws ServiceException;

	ServiceResponse removerLogicamente(Esporte esporte, String usuarioHost,
			String usuarioLogado) throws ServiceException;

	ServiceResponse findByExampleOrderByNome(Esporte esporte) throws ServiceException;
}
