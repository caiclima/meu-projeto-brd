package br.com.callink.bradesco.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.com.callink.bradesco.dao.IAplicacaoDAO;
import br.com.callink.bradesco.seguro.commons.utils.ConnectionUtils;
import br.com.callink.bradesco.seguro.commons.utils.DateUtils;
import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.task.sql.SQLAtualizaLogin;
import br.com.callink.bradesco.task.sql.SQLAtualizaUsuario;
import br.com.callink.bradesco.task.sql.SQLBuscaCargos;
import br.com.callink.bradesco.task.sql.SQLBuscaIDsUsuarioComCPF;
import br.com.callink.bradesco.task.sql.SQLBuscaIDsUsuarioComMatricula;
import br.com.callink.bradesco.task.sql.SQLBuscaParametroSistema;
import br.com.callink.bradesco.task.sql.SQLDesativaLogin;
import br.com.callink.bradesco.task.sql.SQLInsereCargo;
import br.com.callink.bradesco.task.sql.SQLInsereLogin;
import br.com.callink.bradesco.task.sql.SQLInsereUsuario;

/**
 * 
 * @author michael
 * 
 */
public class AplicacaoDAO implements IAplicacaoDAO {
	private Connection conn;
	private String owner;

	public AplicacaoDAO(Connection conn, String owner) {
		this.conn = conn;
		this.owner = (owner == null ? "" : owner);
	}

	@Override
	public BigDecimal inserirCargo(String cargo) throws SQLException {
		String sql = new SQLInsereCargo().withOwner(owner).getSQL(cargo);
		return ConnectionUtils.executeUpdateGenerateKey(conn, sql);
	}

	@Override
	public Map<String, BigInteger> buscarCargos() throws SQLException {
		String sql = new SQLBuscaCargos().withOwner(owner).getSQL();
		ResultSet rs = ConnectionUtils.execute(conn, sql);
		Map<String, BigInteger> cargos = new HashMap<String, BigInteger>();
		
		while(rs.next()){
			cargos.put(rs.getString("nome"), BigInteger.valueOf(((Integer) rs.getInt("id")).longValue()));
		}

		ConnectionUtils.closeIgnore(rs);
		return cargos;
	}

	@Override
	public Long inserirUsuario(String nome, BigInteger matricula, String cpf, BigInteger codCentroCusto, String descricaoCentroCusto, Date dataCadastro, Date dataAtualizacao, Integer idCargo) throws SQLException {
		SQLInsereUsuario sqlInsereUsuario = new SQLInsereUsuario().withOwner(owner);
		String sql = sqlInsereUsuario.getSQL(nome, matricula, cpf, codCentroCusto, descricaoCentroCusto, DateUtils.format(dataCadastro, "yyyy-MM-dd HH:mm:ss"), DateUtils.format(dataAtualizacao, "yyyy-MM-dd HH:mm:ss"), idCargo);
		BigDecimal result = ConnectionUtils.executeUpdateGenerateKey(conn, sql);
		return result == null ? null : result.longValue();
	}

	@Override
	public int atualizarUsuario(Long idUsuario, String nome, BigInteger codCentroCusto, String descricaoCentroCusto, Date dataCadastro, Date dataAtualizacao, Integer idCargo) throws SQLException {
		SQLAtualizaUsuario sqlAtualizaUsuario = new SQLAtualizaUsuario().withOwner(owner);
		String sql = sqlAtualizaUsuario.getSQL(nome, codCentroCusto, descricaoCentroCusto, DateUtils.format(dataCadastro, "yyyy-MM-dd HH:mm:ss"), DateUtils.format(dataAtualizacao, "yyyy-MM-dd HH:mm:ss"), idCargo, idUsuario);
		return ConnectionUtils.executeUpdate(conn, sql);
	}
	
	@Override
	public String buscarValorParametroSistema(String nomeParametro) throws SQLException {
		String sql = new SQLBuscaParametroSistema().withOwner(owner).getSQL(nomeParametro);
		ResultSet rs = ConnectionUtils.execute(conn, sql);
		if(rs.next()){
			return rs.getString("VALOR_PARAMETRO_SISTEMA");
		}

		ConnectionUtils.closeIgnore(rs);
		return null;
	}

	@Override
	public Map<String, Long> buscarIdUsuariosComCPF() throws SQLException {
		String sql = new SQLBuscaIDsUsuarioComCPF().withOwner(owner).getSQL();
		ResultSet rs = ConnectionUtils.execute(conn, sql);
		Map<String, Long> mapping = new HashMap<String, Long>();
		
		while(rs.next()){
			String cpf = rs.getString("cpf");
			Long id = rs.getLong("id");
			
			if(!StringUtils.isEmpty(cpf)){
				mapping.put(cpf, id);
			}
		}

		ConnectionUtils.closeIgnore(rs);
		return mapping;
	}

	@Override
	public Map<Integer, Long> buscarIdUsuariosComMatricula() throws SQLException {
		String sql = new SQLBuscaIDsUsuarioComMatricula().withOwner(owner).getSQL();
		ResultSet rs = ConnectionUtils.execute(conn, sql);
		Map<Integer, Long> mapping = new HashMap<Integer, Long>();
		
		while(rs.next()){
			Long id = rs.getLong("id");
			Integer matricula = rs.getInt("matricula");
			
			if(matricula != null){
				mapping.put(matricula, id);
			}
		}

		ConnectionUtils.closeIgnore(rs);
		return mapping;
	}

	@Override
	public int inserirLoginDeUsuario(Integer matricula, Date dataInicio, Short codigoDominio, Date dataFim, String login, Date dataCadastro, Date dataAtualizacao, Long idUsuario) throws SQLException {
		SQLInsereLogin sqlInsereLogin = new SQLInsereLogin().withOwner(owner);
		String sql = sqlInsereLogin.getSQL(matricula, DateUtils.format(dataInicio, "yyyy-MM-dd HH:mm:ss"), codigoDominio, DateUtils.format(dataFim, "yyyy-MM-dd HH:mm:ss"), login, DateUtils.format(dataCadastro, "yyyy-MM-dd HH:mm:ss"), DateUtils.format(dataAtualizacao, "yyyy-MM-dd HH:mm:ss"), idUsuario);
		return ConnectionUtils.executeUpdate(conn, sql);
	}

	@Override
	public int atualizaLoginDeUsuario(Integer matricula, Short codigoDominio, Date dataInicio, Date dataFim, String login, Date dataCadastro, Date dataAtualizacao) throws SQLException {
		SQLAtualizaLogin sqlAtualizaLogin = new SQLAtualizaLogin().withOwner(owner);
		
		String sdataInicio = DateUtils.format(dataInicio, "yyyy-MM-dd HH:mm:ss");
		String sDataFim = dataFim == null ? null : DateUtils.format(dataFim, "yyyy-MM-dd HH:mm:ss");
		String sDataCadastro = DateUtils.format(dataCadastro, "yyyy-MM-dd HH:mm:ss");
		String sdataAtualizacao = DateUtils.format(dataAtualizacao, "yyyy-MM-dd HH:mm:ss");
		
		String sql = sqlAtualizaLogin.getSQL(sdataInicio, sDataFim, matricula, sDataCadastro, sdataAtualizacao, codigoDominio, login);
		return ConnectionUtils.executeUpdate(conn, sql);
	}

	@Override
	public int desativaLoginUsuario(Short codigoDominio, String login) throws SQLException {
		SQLDesativaLogin sqlDesativaLogin = new SQLDesativaLogin().withOwner(owner);
		String sql = sqlDesativaLogin.getSQL(codigoDominio, login);
		return ConnectionUtils.executeUpdate(conn, sql);
	}

}