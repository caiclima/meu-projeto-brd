package br.com.callink.bradesco.seguro.service;

import java.math.BigInteger;
import java.util.List;

import br.com.callink.bradesco.seguro.dto.UsuarioDTO;
import br.com.callink.bradesco.seguro.entity.Usuario;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

public interface IUsuarioService<T> extends IGenericCrudService<T> {

	ServiceResponse buscarTodosUsuarios() throws ServiceException;

	ServiceResponse buscarUsuarioPorId(BigInteger idUsuario)
			throws ServiceException;

	ServiceResponse salvar(Usuario usuario) throws ServiceException;

	ServiceResponse atualizar(Usuario usuario) throws ServiceException;

	ServiceResponse remover(Usuario usuario) throws ServiceException;

	void validaAuditor(String usuario, String senha) throws ServiceException;

	ServiceResponse findAuditoresByExample(UsuarioDTO pojo)
			throws ServiceException;

	ServiceResponse salvarUsuarioAuditor(UsuarioDTO pojo)
			throws ServiceException;

	ServiceResponse excluirUsuarioAuditor(UsuarioDTO pojo)
			throws ServiceException;
}
