package br.com.callink.bradesco.task.sql;



/**
 * Busca dados de usuários
 * 
 * @author michael
 * 
 */
public class SQLBuscaUsuarios implements ISQL {
	private String owner = "";

	public SQLBuscaUsuarios withOwner(String owner) {
		if (owner != null) {
			this.owner = owner;
		}
		return this;
	}

	@Override
	public String getSQL(Object... args) {
		
		return " {CALL " + owner + "SP_RECUPERA_USUARIOS_POR_OPERACAO_AND_CARGO(?, ?)} ";
//		StringBuilder sql = new StringBuilder();
//
//		sql.append(" SELECT	DISTINCT p.NOME_PESSOA as nome, ");
//		sql.append(" 			p.COD_MATRICULA as codMatricula, ");
//		sql.append(" 			p.NUM_CPF as numCPF,");
//		sql.append(" 			p.COD_CENTRO_CUSTO as codCentroCusto, ");
//		sql.append(" 			p.DESCRICAO_CENTRO_CUSTO as descricaoCentroCusto, ");
//		sql.append(" 			p.DATA_CADASTRO as dataCadastro, ");
//		sql.append(" 			c.NOME_CARGO as cargo, ");
//		sql.append(" 			p.LOCAL_DESCRICAO as equipe ");
//		sql.append(" FROM		" + owner + " tb_pessoa p with (nolock) ");
//		sql.append(" INNER JOIN " + owner + " tb_login l with (nolock) on p.ID_PESSOA = l.ID_PESSOA ");
//		sql.append(" iNNER JOIN " + owner + " tb_cargo c with (nolock) on c.ID_CARGO = p.id_cargo ");
//		sql.append(" WHERE	p.COD_MATRICULA IS NOT NULL AND p.ID_OPERACAO = %s ");
//		sql.append(" ORDER BY p.NOME_PESSOA ");
//
//		return StringUtils.format(sql.toString(), args);
	}
}
