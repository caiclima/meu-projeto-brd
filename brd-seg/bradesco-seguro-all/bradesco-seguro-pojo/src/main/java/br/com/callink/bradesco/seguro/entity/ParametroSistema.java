package br.com.callink.bradesco.seguro.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.callink.bradesco.seguro.entity.metadata.IdentifiableEntity;

/**
 * 
 * @author swb.thiagohenrique
 */
@Entity
@Table(name = "TB_PARAMETRO_SISTEMA")
@Cacheable
public class ParametroSistema implements IdentifiableEntity<BigInteger>, ILog {
	public static final String PARAMETRO_URL_WSDL_WSPROPOSTA_TRIBANCO = "URL_WSDL_WSPROPOSTA_TRIBANCO";
	public static final String PARAMETRO_SENHA_WSPROPOSTA_TRIBANCO = "SENHA_WSPROPOSTA_TRIBANCO";
	public static final String PARAMETRO_CANAL_WSPROPOSTA_TRIBANCO = "CANAL_WSPROPOSTA_TRIBANCO";
	public static final String PARAMETRO_ULT_DATA_ENVIO_EVENTO_WSPROPOSTA_TRIBANCO = "ULTIMA_DATA_ENVIO_EVENTO_WSPROPOSTA_TRIBANCO";
	public static final String PARAMETRO_ULT_DATA_ENVIO_PROPOSTAS_EMITIDAS_TRIBANCO = "ULTIMA_DATA_ENVIO_PROPOSTAS_EMITIDAS_TRIBANCO";
	public static final String PARAMETRO_CODIGO_OPERACAO_SINCRONIZACAO_USUARIOS_COORPORATIVO = "CODIGO_OPERACAO_SINCRONIZACAO_USUARIOS_COORPORATIVO";
	public static final String PARAMETRO_CODIGO_DOMINIOS_SINCRONIZACAO_USUARIOS_COORPORATIVO = "CODIGO_DOMINIOS_SINCRONIZACAO_USUARIOS_COORPORATIVO";
	public static final String PARAMETRO_NUMERO_EXECUCOES_SIMULTANEAS_PERMITIDAS_SINCRONIZACAO_USUARIOS_COORPORATIVO = "NUMERO_EXECUCOES_SIMULTANEAS_PERMITIDAS_SINCRONIZACAO_USUARIOS_COORPORATIVO";
	
	public static final String PARAMETRO_APROVACAO_CONTESTACAO_EXTRATO = "APROVACAO_CONTESTACAO_EXTRATO";
	public static final String PARAMETRO_DIA_LIMITE_APROVACAO = "DIA_LIMITE_APROVACAO";
	public static final String PARAMETRO_ID_SERVICO_ASPECT = "ID_SERVICO_ASPECT";
	public static final String PARAMETRO_CALL_URL_ASPECT = "CALL_URL_ASPECT";
	public static final String PARAMETRO_RETORNO_LIGACAO_ENTRANTE = "RETORNO_LIGACAO_ENTRANTE";
	
	//parametros utilizados para migração de carteiras
	public static final String INDICADOR_GERAR_RAW_IMPORTACAO = "INDICADOR_GERAR_RAW_IMPORTACAO";
	public static final String INDICADOR_GERAR_RAW_MIGRACAO_CARTEIRA = "INDICADOR_GERAR_RAW_MIGRACAO_CARTEIRA";
	public static final String INDICADOR_GERAR_RAW_INDICACAO_CLIENTE = "INDICADOR_GERAR_RAW_INDICACAO_CLIENTE";
	public static final String FULL_URL_ARQUIVO_RAW_MIGRACAO = "FULL_URL_ARQUIVO_RAW_MIGRACAO";	
	public static final String FULL_URL_ARQUIVO_RAW_INDICACAO = "FULL_URL_ARQUIVO_RAW_INDICACAO";	
	public static final String ID_LOTE_MAILING_INDICACAO = "ID_LOTE_MAILING_INDICACAO";
	public static final String ID_CAMPANHA_INDICACAO = "ID_CAMPANHA_INDICACAO";
	public static final String ID_DATABASE_INDICACAO = "ID_DATABASE_INDICACAO";
	
	//MONTAR LINK CUC
	public static final String URL_CUC_TRIBANCO = "URL_CUC_TRIBANCO";	
	public static final String USUARIO_CALLINK_ACESSO_CUC = "USUARIO_CALLINK_ACESSO_CUC";
	public static final String SENHA_CALLINK_ACESSO_CUC = "SENHA_CALLINK_ACESSO_CUC";
	public static final String SENHA_CANAL_ACESSO_CUC_TRIBANCO = "SENHA_CANAL_ACESSO_CUC_TRIBANCO";
	public static final String USUARIO_CANAL_CALLINK_ACESSO_CUC = "USUARIO_CANAL_CALLINK_ACESSO_CUC";
	
	public static final String QTD_DIAS_QUARENTENA_EMISSAO_PROPOSTA = "QTD_DIAS_QUARENTENA_EMISSAO_PROPOSTA";
	public static final String CARGOS_LIBERACAO_DE_TAXA_REEMISSAO = "CARGOS_LIBERACAO_DE_TAXA_REEMISSAO";
	
	public static final String URL_CACHE_SERVLET = "URL_CACHE_SERVLET";
	public static final String USUARIO_ACESSO_CACHE_SERVLET = "USUARIO_ACESSO_CACHE_SERVLET";
	public static final String SENHA_ACESSO_CACHE_SERVLET = "SENHA_ACESSO_CACHE_SERVLET";
	
	public static final String FLAG_ENVIA_SUCESSO_CHAMADA_MANUAL = "FLAG_ENVIA_SUCESSO_CHAMADA_MANUAL";
	
	public static final String ID_CLIENTE_CAMPANHA_NAO_IDENTIFICADO = "ID_CLIENTE_CAMPANHA_NAO_IDENTIFICADO";
	public static final String ID_EVENTO_FINALIZACAO_PADRAO = "ID_EVENTO_FINALIZACAO_PADRAO";
	public static final String PARAMETRO_CODIGO_CARGOS_SINCRONIZACAO_USUARIOS_COORPORATIVO = "CODIGO_CARGOS_SINCRONIZACAO_USUARIOS_COORPORATIVO";
	
	public static final String NUVEM_TIPO_REGISTRO_HEADER_REGISTRO = "NUVEM_FINALIZACAO_01_TIPO_REGISTRO";
	public static final String NUVEM_FINALIZACAO_TIPO_REGISTRO_STATUS = "NUVEM_FINALIZACAO_TIPO_REGISTRO_STATUS";
	public static final String NUVEM_VENDAS_HEADER_REGISTRO = "NUVEM_VENDAS_HEADER_REGISTRO";
	public static final String NUVEM_VENDAS_HEADER_LAYOUT = "NUVEM_VENDAS_HEADER_LAYOUT";
	public static final String NUVEM_VENDAS_HEADER_CLIENTE = "NUVEM_VENDAS_HEADER_CLIENTE";

	public static final String CODIGO_BRADESCO_CARTOES = "CODIGO_BRADESCO_CARTOES";
	public static final String SIGLA_CALLINK = "SIGLA_CALLINK"; 
	
	//Parametro para setar tabulação implicita em caso de confirmação de venda sem finalização
		public static final String CONFIRMAÇAO_VENDA_FAMILIAR_SEM_DEPENDENTES = "CONFIRMAÇAO_VENDA_FAMILIAR_SEM_DEPENDENTES";
	
	public static final String NUVEM_VENDAS_DOCUMENTO_REGISTRO = "NUVEM_VENDAS_DOCUMENTO_REGISTRO";
	public static final String NUVEM_VENDAS_DOCUMENTO_TRANSACAO = "NUVEM_VENDAS_DOCUMENTO_TRANSACAO";
	public static final String NUVEM_VENDAS_DOCUMENTO_PARCELAMENTO = "NUVEM_VENDAS_DOCUMENTO_PARCELAMENTO";
	public static final String NUVEM_VENDAS_DOCUMENTO_ESTIPULANTE = "NUVEM_VENDAS_DOCUMENTO_ESTIPULANTE";
	public static final String NUVEM_VENDAS_DOCUMENTO_DESCONTO = "NUVEM_VENDAS_DOCUMENTO_DESCONTO";
	

	public static final String NUVEM_VENDAS_CADASTRO_REGISTRO = "NUVEM_VENDAS_CAD_SE_REGISTRO";
	public static final String NUVEM_VENDAS_CADASTRO_TRANSACAO = "NUVEM_VENDAS_CAD_SE_TRANSACAO";
	
	public static final String NUVEM_VENDAS_COBRANCA_REGISTRO = "NUVEM_VENDAS_COBRANCA_REGISTRO";
	public static final String NUVEM_VENDAS_COBRANCA_TRANSACAO = "NUVEM_VENDAS_COBRANCA_TRANSACAO";
	public static final String NUVEM_VENDAS_COBRANCA_MEIO= "NUVEM_VENDAS_COBRANCA_MEIO";
	
//	public static final String NUVEM_VENDAS_TRAILLER_REGISTRO = "NUVEM_VENDAS_TRAILLER_REGISTRO";
	
	public static final String NUVEM_VENDAS_REFERENCIA_VENDA = "NUVEM_VENDAS_REFERENCIA_VENDA";
	public static final String NUVEM_VENDAS_REFERENCIA_PRODUTO_PESSOAL = "NUVEM_VENDAS_PLANO_PESSOAL_EFERENCIA";
	public static final String NUVEM_VENDAS_REFERENCIA_PRODUTO_PLUS = "NUVEM_VENDAS_PLANO_PLUS_EFERENCIA";
	public static final String NUVEM_VENDAS_REFERENCIA_PRODUTO_DHI = "NUVEM_VENDAS_PLANO_DHI_EFERENCIA";
	
	public static final String NUVEM_VENDAS_TIPO_PLANO_FAMILIA = "NUVEM_VENDAS_TIPO_PLANO_FAMILIA";
	
	public static final String NOME_TIPO_EVENTO_SEM_RESULTADO = "NOME_TIPO_EVENTO_SEM_RESULTADO";
	
	public static final String IDENTIFICACAO_CAMPANHA = "IDENTIFICACAO_CAMPANHA";
	
	public static final String MEIO_COBRANCA = "MEIO_COBRANCA";
	public static final String ADMINISTRACAO_COBRANCA = "ADMINISTRACAO_COBRANCA";
	
	private static final long serialVersionUID = 1L;
	public static final String PARAMETRO_EXECUTA_SINCRONIZADOR = "EXECUTA_SINCRONIZADOR";
	
	public static final String PARAMETRO_IDS_CARGO_AUDITOR = "IDS_CARGOS_AUDITOR";
	public static final String PARAMETRO_ID_CARGO_ATENDENTE = "ID_CARGO_ATENDENTE";
	public static final String PARAMETRO_IDS_DOMINIO = "IDS_DOMINIOS_USUARIOS";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ID_PARAMETRO_SISTEMA")
	private BigInteger id;

	@Basic(optional = false)
	@Column(name = "NOME_PARAMETRO_SISTEMA")
	private String nomeParametroSistema;

	@Basic(optional = false)
	@Column(name = "VALOR_PARAMETRO_SISTEMA")
	private String valorParametroSistema;
	
	@Column(name = "DESCRICAO")
	private String descricao;
	
	@Basic(optional = false)
    @Column(name = "FLAG_ADMIN_ROLE")
	private Boolean flagAdminRole;
    
    @Basic(optional = false)
    @Column(name = "LOG_UID")
    private String logUid;
    
    @Basic(optional = false)
    @Column(name = "LOG_HOST")
    private String logHost;
    
    @Basic(optional = false)
    @Column(name = "LOG_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date logDate;
    
    @Basic(optional = false)
    @Column(name = "LOG_SYSTEM")
    private String logSystem;
    
    @Basic(optional = false)
    @Column(name = "LOG_OBS")
    private String logObs;
    
    @Basic(optional = false)
    @Column(name = "LOG_TRANSACTION")
    private BigInteger logTransaction;
    
    public ParametroSistema() {
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getNomeParametroSistema() {
		return nomeParametroSistema;
	}

	public void setNomeParametroSistema(String nomeParametroSistema) {
		this.nomeParametroSistema = nomeParametroSistema;
	}

	public String getValorParametroSistema() {
		return valorParametroSistema;
	}

	public void setValorParametroSistema(String valorParametroSistema) {
		this.valorParametroSistema = valorParametroSistema;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getFlagAdminRole() {
		return flagAdminRole;
	}

	public void setFlagAdminRole(Boolean flagAdminRole) {
		this.flagAdminRole = flagAdminRole;
	}

	public String getLogUid() {
		return logUid;
	}

	public void setLogUid(String logUid) {
		this.logUid = logUid;
	}

	public String getLogHost() {
		return logHost;
	}

	public void setLogHost(String logHost) {
		this.logHost = logHost;
	}

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public String getLogSystem() {
		return logSystem;
	}

	public void setLogSystem(String logSystem) {
		this.logSystem = logSystem;
	}

	public String getLogObs() {
		return logObs;
	}

	public void setLogObs(String logObs) {
		this.logObs = logObs;
	}

	public BigInteger getLogTransaction() {
		return logTransaction;
	}

	public void setLogTransaction(BigInteger logTransaction) {
		this.logTransaction = logTransaction;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParametroSistema other = (ParametroSistema) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}