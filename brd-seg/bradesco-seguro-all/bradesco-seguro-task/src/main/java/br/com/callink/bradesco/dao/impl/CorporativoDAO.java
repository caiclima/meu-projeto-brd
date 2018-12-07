package br.com.callink.bradesco.dao.impl;

import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.callink.bradesco.dao.ICorporativoDAO;
import br.com.callink.bradesco.seguro.commons.utils.ConnectionUtils;
import br.com.callink.bradesco.seguro.commons.utils.ObjectUtils;
import br.com.callink.bradesco.seguro.entity.Cargo;
import br.com.callink.bradesco.seguro.entity.Dominio;
import br.com.callink.bradesco.seguro.entity.Pessoa;
import br.com.callink.bradesco.seguro.entity.PessoaColaborador;
import br.com.callink.bradesco.seguro.entity.Usuario;
import br.com.callink.bradesco.task.dto.LoginCoorporativoDTO;
import br.com.callink.bradesco.task.sql.SQLBuscaCargos;
import br.com.callink.bradesco.task.sql.SQLBuscaDominios;
import br.com.callink.bradesco.task.sql.SQLBuscaLogin;
import br.com.callink.bradesco.task.sql.SQLBuscaPessoas;
import br.com.callink.bradesco.task.sql.SQLBuscaUsuarios;

/**
 * 
 * @author michael
 * 
 */
public class CorporativoDAO implements ICorporativoDAO {
	private Connection conn;
	private String owner;

	public CorporativoDAO(Connection conn, String owner) {
		this.conn = conn;
		this.owner = (owner == null ? "" : owner);
	}
	
//	@Override
//	public List<UsuarioCoorporativoDTO> buscarUsuarios(int idOperacao) throws SQLException {
//		String sql = new SQLBuscaUsuarios().withOwner(owner).getSQL(idOperacao);
//		ResultSet rs = ConnectionUtils.execute(conn, sql);
//		List<UsuarioCoorporativoDTO> usuarios = new ArrayList<UsuarioCoorporativoDTO>();
//		
//		while(rs.next()){
//			String nome = rs.getString("nome");
//			Integer codMatricula = rs.getInt("codMatricula");
//			String numCpf = rs.getString("numCPF");
//			Integer codCentroCusto = rs.getInt("codCentroCusto");
//			String descricaoCentroCusto = rs.getString("descricaoCentroCusto");
//			Date dataCadastro = rs.getTimestamp("dataCadastro");
//			String cargo = rs.getString("cargo");
//			String equipe = rs.getString("equipe");
//
//			UsuarioCoorporativoDTO usuario = new UsuarioCoorporativoDTO();
//			usuario.setNome(nome);
//			usuario.setCodMatricula(codMatricula == null ? null : BigInteger.valueOf(codMatricula.longValue()));
//			usuario.setNumCpf(numCpf);
//			usuario.setCodCentroCusto(codCentroCusto == null ? null : BigInteger.valueOf(codCentroCusto.longValue()));
//			usuario.setDescricaoCentroCusto(descricaoCentroCusto);
//			usuario.setDataCadastro(dataCadastro);
//			usuario.setCargo(cargo);
//			usuario.setEquipe(equipe);
//			
//			usuarios.add(usuario);
//		}
//
//		ConnectionUtils.closeIgnore(rs);
//		return usuarios;
//	}
	
	@Override
	public Set<Usuario> buscarUsuarios(String idOperacao, String idCargos) throws SQLException {
		String sql = new SQLBuscaUsuarios().withOwner(owner).getSQL();
		CallableStatement cs = ConnectionUtils.executeProcedure(conn, sql);
		
		//TODO melhorar, criar de modo generico
		cs.setString(1, idOperacao);
		cs.setString(2, idCargos);
		cs.execute();
		ResultSet rs = cs.executeQuery();
		
		Set<Usuario> usuarios = new HashSet<Usuario>();
		
		while(rs.next()){
			Usuario usuario = new Usuario();
			Integer idUsuario = rs.getInt("ID_USUARIO");
			usuario.setIdUsuarioCorporativo(BigInteger.valueOf(idUsuario.longValue()));
			usuario.setId(BigInteger.valueOf(idUsuario.longValue()));
			usuario.setDatCadastro(rs.getDate("DAT_CADASTRO"));
			usuario.setDatFim(rs.getDate("DAT_FIM"));
			usuario.setDatInicio(rs.getDate("DAT_INICIO"));
			usuario.setIndAtivo(rs.getBoolean("IND_ATIVO"));
			usuario.setUsuario(rs.getString("USUARIO"));
			Integer idDominio = rs.getInt("ID_DOMINIO");
			usuario.setIdDominio(BigInteger.valueOf(idDominio.longValue()));
			Integer idPessoa = rs.getInt("ID_PESSOA");
			usuario.setIdPessoa(BigInteger.valueOf(idPessoa.longValue()));
			usuarios.add(usuario);
		}

		ConnectionUtils.closeIgnore(rs);
		return usuarios;
	}

	@Override
	public List<LoginCoorporativoDTO> buscarLoginsDeUsuario(int idOperacao, Set<Short> codigosDominios) throws SQLException {
		String sql = new SQLBuscaLogin().withOwner(owner).getSQL(idOperacao, ObjectUtils.asCommaSeparated(codigosDominios));
		ResultSet rs = ConnectionUtils.execute(conn, sql);
		List<LoginCoorporativoDTO> logins = new ArrayList<LoginCoorporativoDTO>();
		
		while(rs.next()){
			Integer matricula = rs.getInt("matricula");
			Date dataInicio = rs.getTimestamp("dataInicio");
			Short codigoDominio = rs.getShort("codigoDominio");
			Date dataFim = rs.getTimestamp("dataFim");
			String loginUsuario = rs.getString("login");
			Date dataCadastro = rs.getTimestamp("dataCadastro");
			
			LoginCoorporativoDTO login = new LoginCoorporativoDTO();
			login.setMatricula(matricula);
			login.setDataInicio(dataInicio);
			login.setCodigoDominio(codigoDominio);
			login.setDataFim(dataFim);
			login.setLogin(loginUsuario);
			login.setDataCadastro(dataCadastro);
			
			logins.add(login);
		}

		ConnectionUtils.closeIgnore(rs);
		return logins;
	}

	@Override
	public List<Cargo> buscarCargos() throws SQLException {
		String sql = new SQLBuscaCargos().withOwner(owner).getSQL();
		ResultSet rs = ConnectionUtils.execute(conn, sql);
		List<Cargo> cargos = new ArrayList<Cargo>();
		
		while(rs.next()){
			Integer idCorporativo = rs.getInt("id");
			Cargo cargo = new Cargo();
			cargo.setIdCargoCorporativo(BigInteger.valueOf(idCorporativo.longValue()));
			cargo.setNomeCargo(rs.getString("nomeCargo"));
			cargo.setAtivo(rs.getBoolean("ativo"));
			cargos.add(cargo);
		}

		ConnectionUtils.closeIgnore(rs);
		return cargos;
	}
	
	@Override
	public List<Dominio> buscarDominios() throws SQLException {
		String sql = new SQLBuscaDominios().withOwner(owner).getSQL();
		ResultSet rs = ConnectionUtils.execute(conn, sql);
		List<Dominio> dominios = new ArrayList<Dominio>();
		
		while(rs.next()){
			Integer idCorporativo = rs.getInt("id");
			Dominio dominio = new Dominio();
			dominio.setIdDominioCorporativo(BigInteger.valueOf(idCorporativo.longValue()));
			dominio.setDescricaoDominio(rs.getString("descricao"));
			dominio.setObsDominio(rs.getString("observacao"));
			dominio.setAtivo(rs.getBoolean("ativo"));
			dominio.setDataCadastro(rs.getDate("dataCadastro"));
			dominios.add(dominio);
		}

		ConnectionUtils.closeIgnore(rs);
		return dominios;
	}

	@Override
	public Set<PessoaColaborador> buscarPessoas(String idOperacao, String idCargos) throws SQLException {
		String sql = new SQLBuscaPessoas().withOwner(owner).getSQL();
		CallableStatement cs = ConnectionUtils.executeProcedure(conn, sql);
		
		//TODO melhorar, criar de modo generico
		cs.setString(1, idOperacao);
		cs.setString(2, idCargos);
		cs.execute();
		ResultSet rs = cs.executeQuery();
		
		Set<PessoaColaborador> pessoas = new HashSet<PessoaColaborador>();
		
		while(rs.next()){
			PessoaColaborador pessoaColaborador = new PessoaColaborador();
			Integer idPessoaColaborador = rs.getInt("idPessoaColaborador");
			pessoaColaborador.setIdPessoaColaboradorCorporativo(BigInteger.valueOf(idPessoaColaborador.longValue()));
			pessoaColaborador.setId(BigInteger.valueOf(idPessoaColaborador.longValue()));
			pessoaColaborador.setMatricula(rs.getString("matricula"));
			pessoaColaborador.setDataAdmissao(rs.getDate("dataAdmissao"));
			pessoaColaborador.setDataAfastamento(rs.getDate("dataAfastamento"));
			pessoaColaborador.setAtivo(rs.getBoolean("ativo"));
			pessoaColaborador.setExperiencia(rs.getBoolean("experiencia"));
			Integer idCargo = rs.getInt("idCargo");
			pessoaColaborador.setIdCargo(BigInteger.valueOf(idCargo.longValue()));
			
			Pessoa pessoa = new Pessoa();
			Integer idPessoa = rs.getInt("idPessoaPK");
			pessoa.setIdPessoaCorporativo(BigInteger.valueOf(idPessoa.longValue()));
			pessoa.setCpfCnpj(rs.getString("cpfCnpj"));
			pessoa.setEmail(rs.getString("email"));
			pessoa.setNomePessoa(rs.getString("nomePessoa"));
			pessoa.setTelefone(rs.getString("telefone"));
			
			pessoaColaborador.setPessoa(pessoa);
			pessoas.add(pessoaColaborador);
		}

		ConnectionUtils.closeIgnore(rs);
		return pessoas;
	}
}