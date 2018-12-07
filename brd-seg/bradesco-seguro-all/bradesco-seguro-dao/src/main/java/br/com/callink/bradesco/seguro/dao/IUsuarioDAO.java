package br.com.callink.bradesco.seguro.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import br.com.callink.bradesco.seguro.dto.UsuarioDTO;
import br.com.callink.bradesco.seguro.entity.Usuario;

public interface IUsuarioDAO extends IGenericDAO<Usuario> {

	public List<Usuario> findByExampleExact(Usuario object);

	public <O> O getFromPersistenceContext(Class<O> clazz, Serializable id);
	
	public Usuario salvar(Usuario Usuario);
        
        Boolean isAuditor(String login) ;

	public List<UsuarioDTO> findAuditoresByExample(UsuarioDTO usuarioDTO);

	public Integer atualizarCargoUsuario(UsuarioDTO pojo, BigInteger idCargo);

}
