package br.com.callink.bradesco.seguro.service.impl;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.callink.bradesco.seguro.commons.utils.CollectionUtils;
import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.dao.IParametroSistemaDAO;
import br.com.callink.bradesco.seguro.dao.IUsuarioDAO;
import br.com.callink.bradesco.seguro.dto.UsuarioDTO;
import br.com.callink.bradesco.seguro.entity.ParametroSistema;
import br.com.callink.bradesco.seguro.entity.Usuario;
import br.com.callink.bradesco.seguro.service.IAuthenticatorService;
import br.com.callink.bradesco.seguro.service.IUsuarioService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponseFactory;

@Stateless
@Local(IUsuarioService.class)
public class UsuarioService extends GenericCrudServiceImpl<Usuario> implements
		IUsuarioService<Usuario> {

	@Inject
	private IUsuarioDAO usuarioDao;
    @Inject
    private IAuthenticatorService authenticatorService;
    @Inject
    private IParametroSistemaDAO parametroSistemaDAO;

	@Override
	protected IUsuarioDAO getDAO() {
		return usuarioDao;
	}

	@Override
	public ServiceResponse buscarTodosUsuarios() throws ServiceException {

		List<Usuario> usuarios = getDAO().findAll();
		ServiceResponse serviceResponse = ServiceResponseFactory
				.createWithData(usuarios);

		if (CollectionUtils.isEmpty(usuarios)) {
			serviceResponse.addWarning("Nenhum registro encontrado.");
		}
		return serviceResponse;
	}

	@Override
	public ServiceResponse buscarUsuarioPorId(BigInteger idUsuario)
			throws ServiceException {

		validarVazio(idUsuario, "Informe o id do Usuário.");

		Usuario usuario = getDAO().findByPK(idUsuario);
		ServiceResponse serviceResponse = ServiceResponseFactory
				.createWithData(usuario);

		if (usuario == null) {
			serviceResponse.addWarning("Nenhum registro encontrado.");
		}
		return serviceResponse;

	}

	@Override
	public ServiceResponse salvar(Usuario usuario) throws ServiceException {

		antesSalvar(usuario);

		getDAO().salvar(usuario);

		ServiceResponse serviceResponse = ServiceResponseFactory
				.createWithData(usuario);
		serviceResponse.addInfo("Registro salvo com sucesso.");

		return serviceResponse;
	}

	@Override
	protected void antesSalvar(Usuario usuario) throws ServiceException {

		validarVazio(usuario, "Informe o Usuário.");
		validarVazio(usuario.getUsuario(), "Informe o nome do Usuário");
	}

	@Override
	public ServiceResponse atualizar(Usuario usuario) throws ServiceException {

		antesAtualizar(usuario);

		getDAO().update(usuario);

		ServiceResponse serviceResponse = ServiceResponseFactory
				.createWithData(usuario);
		serviceResponse.addInfo("Registro salvo com sucesso.");

		return serviceResponse;
	}

	@Override
	protected void antesAtualizar(Usuario usuario) throws ServiceException {
		validarVazio(usuario, "Informe o Usuário.");
		validarVazio(usuario.getUsuario(), "Informe o nome do Usuário");
	}

	@Override
	public ServiceResponse remover(Usuario usuario) throws ServiceException {

		antesExcluir(usuario);

		getDAO().delete(usuario);

		ServiceResponse serviceResponse = ServiceResponseFactory
				.createWithData(usuario);
		serviceResponse.addInfo("Registro removido com sucesso.");

		return serviceResponse;
	}

	@Override
	protected void antesExcluir(Usuario usuario) throws ServiceException {

		validarVazio(usuario, "Informe o Usuário.");
		validarVazio(usuario.getId(), "Informe o id do Usuário.");
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

    @Override
    public void validaAuditor(String usuario, String senha) throws ServiceException{
    	
    	validarVazio(usuario, "Informe o usuário");
    	validarVazio(senha, "Informe a senha");
    	
        if(!getDAO().isAuditor(usuario)){
           throw new ServiceException("Usuario não cadastrado como Auditor!");
        }
        
        authenticatorService.authenticaAD(usuario, senha);
    }
    
    @Override
    public ServiceResponse findAuditoresByExample(UsuarioDTO usuarioDTO) throws ServiceException {
    	List<UsuarioDTO> usuariosDTO = getDAO().findAuditoresByExample(usuarioDTO);
    	
    	ServiceResponse serviceResponse = ServiceResponseFactory
				.createWithData(usuariosDTO);
    	
    	if(usuariosDTO == null || usuariosDTO.isEmpty()){
    		serviceResponse.addInfo("Nenhum registro encontrado!");
    	}
    		
    	return serviceResponse;
    }
    
    @Override
    public ServiceResponse salvarUsuarioAuditor(UsuarioDTO usuario) throws ServiceException {
    	validarUsuarioAuditor(usuario);
    	
    	Integer qtdAtualizado = usuarioDao.atualizarCargoUsuario(usuario, usuario.getCargo().getId());
    	
    	ServiceResponse serviceResponse = ServiceResponseFactory.createWithData(qtdAtualizado);
    	
    	if(qtdAtualizado == 0){
    		serviceResponse.addError("Não existe usuário com esse login e/ou matrícula.");
    	}else{
    		serviceResponse.addInfo("Registro salvo com sucesso!");
    	}
    	
    	return serviceResponse;
    }
    
    @Override
    public ServiceResponse excluirUsuarioAuditor(UsuarioDTO usuario) throws ServiceException {
    	ParametroSistema param = parametroSistemaDAO.buscarPorNome(ParametroSistema.PARAMETRO_ID_CARGO_ATENDENTE);
    	
    	if(param == null){
    		throw new ServiceException("Parâmetro de sistema " + ParametroSistema.PARAMETRO_ID_CARGO_ATENDENTE + " não castrado.");
    	}
    	
    	BigInteger idCargo = new BigInteger(param.getValorParametroSistema());
    	
    	Integer qtdAtualizado = usuarioDao.atualizarCargoUsuario(usuario, idCargo);
    	
    	ServiceResponse serviceResponse = ServiceResponseFactory.createWithData(qtdAtualizado);
    	
    	if(qtdAtualizado != 0){
    		serviceResponse.addInfo("Registro excluído com sucesso!");
    	}
    	
    	return serviceResponse;
    }

	private void validarUsuarioAuditor(UsuarioDTO usuarioDTO) throws ServiceException{
		if(usuarioDTO.getCargo() == null || usuarioDTO.getCargo().getId() == null){
			throw new ServiceException("Informe o cargo do usuário!");
		}
		
		if((usuarioDTO.getCodigoMatricula() == null || usuarioDTO.getCodigoMatricula().trim().isEmpty()) &&
			(usuarioDTO.getLoginUsuario() == null || usuarioDTO.getLoginUsuario().trim().isEmpty())){
			
			throw new ServiceException("É obrigatório informar Login do Usuário ou Código de Matrícula!");
		}
		
	}
}
