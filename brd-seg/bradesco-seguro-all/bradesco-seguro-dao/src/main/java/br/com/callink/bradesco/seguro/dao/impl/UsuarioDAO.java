package br.com.callink.bradesco.seguro.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;

import br.com.callink.bradesco.seguro.dao.IParametroSistemaDAO;
import br.com.callink.bradesco.seguro.dao.IUsuarioDAO;
import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.dto.UsuarioDTO;
import br.com.callink.bradesco.seguro.entity.Cargo;
import br.com.callink.bradesco.seguro.entity.ParametroSistema;
import br.com.callink.bradesco.seguro.entity.Usuario;

public class UsuarioDAO extends GenericHibernateDAOImpl<Usuario> implements
        IUsuarioDAO {

	@Inject
	private IParametroSistemaDAO parametroSistemaDAO;
	
    @SuppressWarnings("unchecked")
    @Override
    public List<Usuario> findByExampleExact(Usuario usuario) {

        Criteria criteria = createCriteria();
        criteria.add(Example.create(usuario).enableLike(MatchMode.EXACT));
        return (List<Usuario>) criteria.list();
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        session().save(usuario);
        session().flush();
        session().clear();
        return usuario;
    }

    @Override
    public Boolean isAuditor(String login) {
    	ParametroSistema param = parametroSistemaDAO.buscarPorNome(ParametroSistema.PARAMETRO_IDS_CARGO_AUDITOR);
    	
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT count(usuario) from PessoaColaborador colab inner join colab.cargo cargo, Usuario usuario ");
        stringBuilder.append("  where colab.idPessoa = usuario.idPessoa and cargo.id in ("+ param.getValorParametroSistema() + ") and usuario.usuario = :USUARIO");
        Query query = getEntityManager().createQuery(stringBuilder.toString());
        query.setParameter("USUARIO", login);
        Long ret = (Long)query.getSingleResult();
       
        return ret == null ? Boolean.FALSE : ret != 0L;
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<UsuarioDTO> findAuditoresByExample(UsuarioDTO usuarioDTO) {
    	try{
    		StringBuilder select = new StringBuilder();
        	select.append("SELECT usuario.usuario ")
        		  .append("		\n,pessoa_colaborador.cod_matricula ")
        		  .append("		\n,cargo.id_cargo ")	
        		  .append("		\n,cargo.NOM_CARGO ")
        		  .append(" \n FROM tb_usuario usuario")
        		  .append("		\n inner join tb_pessoa_colaborador pessoa_colaborador on usuario.id_pessoa = pessoa_colaborador.id_pessoa ")
        		  .append("		\n inner join tb_cargo cargo on cargo.id_cargo = pessoa_colaborador.id_cargo ")
        		  .append(" \n WHERE 1 = 1 ");
        	
        	if(usuarioDTO.getCargo() != null && usuarioDTO.getCargo().getId() != null){
        		select.append(" AND pessoa_colaborador.id_cargo = :ID_CARGO ");
        	}else{
        		String inClauseCargos = findIdsCargosAuditores();
        		select.append(" AND pessoa_colaborador.id_cargo in (")
        			  .append(inClauseCargos).append(") ");
        	}
        	
        	if(usuarioDTO.getLoginUsuario() != null && 
        			!usuarioDTO.getLoginUsuario().trim().isEmpty()){
        		select.append(" AND usuario.USUARIO like :LOGIN_USUARIO ");
        	}
        	
        	if(usuarioDTO.getCodigoMatricula() != null &&
        			!usuarioDTO.getCodigoMatricula().trim().isEmpty()){
        		select.append(" AND pessoa_colaborador.cod_matricula = :COD_MATRICULA ");
        	}
        	
        	String inClauseDominios = findIdsDominiosUsuarios();
        	select.append(" AND usuario.id_dominio in (").append(inClauseDominios).append(") ");
        	
        	Query query = getEntityManager().createNativeQuery(select.toString());
        	
        	if(usuarioDTO.getCargo() != null && usuarioDTO.getCargo().getId() != null){
        		query.setParameter("ID_CARGO", usuarioDTO.getCargo().getId());
        	}
        	
        	if(usuarioDTO.getLoginUsuario() != null && 
        			!usuarioDTO.getLoginUsuario().trim().isEmpty()){
        		query.setParameter("LOGIN_USUARIO", "%" + usuarioDTO.getLoginUsuario() + "%");
        	}
        	
        	if(usuarioDTO.getCodigoMatricula() != null &&
        			!usuarioDTO.getCodigoMatricula().trim().isEmpty()){
        		query.setParameter("COD_MATRICULA", usuarioDTO.getCodigoMatricula());
        	}
        	
        	List<Object[]> list = query.getResultList();
        	
        	return buildUsuariosDTO(list);
    	}catch(Exception e){
    		e.printStackTrace();
    		throw new DataException("Erro ao buscar usuários auditores. Motivo: " + e.getMessage());
    	}
    	
    }

    private String findIdsDominiosUsuarios(){
    	ParametroSistema idsDominios = parametroSistemaDAO.buscarPorNome(ParametroSistema.PARAMETRO_IDS_DOMINIO);
		
		if(idsDominios == null || idsDominios.getValorParametroSistema().trim().isEmpty()){
			throw new DataException("O parâmetro de sistema "+ ParametroSistema.PARAMETRO_IDS_DOMINIO + " não está cadastrado. ");
		}
		
		return idsDominios.getValorParametroSistema();
    }
    
	private String findIdsCargosAuditores() {
		ParametroSistema idsCargos = parametroSistemaDAO.buscarPorNome(ParametroSistema.PARAMETRO_IDS_CARGO_AUDITOR);
		
		if(idsCargos == null || idsCargos.getValorParametroSistema().trim().isEmpty()){
			throw new DataException("O parâmetro de sistema "+ ParametroSistema.PARAMETRO_IDS_CARGO_AUDITOR + " não está cadastrado. ");
		}
		
		return idsCargos.getValorParametroSistema();
	}

	private List<UsuarioDTO> buildUsuariosDTO(List<Object[]> list) {
		List<UsuarioDTO> usuarios = new ArrayList<UsuarioDTO>();
		
		for (Object[] object : list) {
			UsuarioDTO usuario = new UsuarioDTO();
			usuario.setLoginUsuario((String)object[0]);
			usuario.setCodigoMatricula((String)object[1]);
			
			Cargo cargo = new Cargo();
			cargo.setId((BigInteger)object[2]);
			cargo.setNomeCargo((String)object[3]);
			
			usuario.setCargo(cargo);
			
			usuarios.add(usuario);
		}
		
		return usuarios;
	}
	
	@Override
	public Integer atualizarCargoUsuario(UsuarioDTO usuario, BigInteger idCargo) {
		StringBuilder update = new StringBuilder();
		
		update.append("update pessoa_colaborador ")
			.append("set id_cargo = :ID_CARGO ")
			.append("from tb_pessoa_colaborador pessoa_colaborador ")
			.append("inner join tb_usuario usuario on pessoa_colaborador.id_pessoa = usuario.id_pessoa ")
			.append("where 1 = 1 ");
		
		if(usuario.getLoginUsuario() != null 
				&& !usuario.getLoginUsuario().trim().isEmpty()){
			update.append(" and usuario.usuario = :NOME_USUARIO");
		}
		
		if(usuario.getCodigoMatricula() != null 
				&& !usuario.getCodigoMatricula().trim().isEmpty()){
			
			update.append(" and pessoa_colaborador.cod_matricula = :COD_MATRICULA");
		}
		
		String idDominios = findIdsDominiosUsuarios();
		update.append(" and usuario.id_dominio in (").append(idDominios).append(") ");

		Query query = getEntityManager().createNativeQuery(update.toString());
		
		query.setParameter("ID_CARGO", idCargo);
		
		if(usuario.getLoginUsuario() != null 
				&& !usuario.getLoginUsuario().trim().isEmpty()){
			
			query.setParameter("NOME_USUARIO", usuario.getLoginUsuario());
		}
		
		if(usuario.getCodigoMatricula() != null 
				&& !usuario.getCodigoMatricula().trim().isEmpty()){
			
			query.setParameter("COD_MATRICULA", usuario.getCodigoMatricula());
		}
		
		return query.executeUpdate();
	}

}
