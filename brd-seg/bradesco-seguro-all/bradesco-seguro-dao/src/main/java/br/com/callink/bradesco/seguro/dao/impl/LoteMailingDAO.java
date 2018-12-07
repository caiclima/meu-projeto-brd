package br.com.callink.bradesco.seguro.dao.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.dao.ILoteMailingDAO;
import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.LoteMailing;
import br.com.callink.bradesco.seguro.entity.ParametroSistema;

@SuppressWarnings("unchecked")
public class LoteMailingDAO extends GenericHibernateDAOImpl<LoteMailing> implements ILoteMailingDAO {

	@Override
	public List<LoteMailing> findLoteMailingByDateAndPojo(LoteMailing pojo, Date ateDataImportacao, Date ateDataInicioMailing, Date ateDataFimMailing) throws DataException {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT loteMailing FROM LoteMailing loteMailing where 1=1 ");
			
			if(pojo.getId() != null && pojo.getId() != BigInteger.ZERO) {
				sql.append("	and loteMailing.id = :ID_LOTE_MAILING ");
			}
			if(pojo.getIdCampanha() != null && !pojo.getIdCampanha().equals(Long.valueOf("0"))) {
				sql.append("	and loteMailing.idCampanha = :ID_CAMPANHA ");
			}
			if(!StringUtils.isEmpty(pojo.getDesignacao())) {
				sql.append("	and loteMailing.designacao like :DESIGNACAO_LOTE_MAILING ");
			}
			if(pojo.getQuantidadeImportada() != null && !pojo.getQuantidadeImportada().equals(Integer.valueOf("0"))) {
				sql.append("	and loteMailing.quantidadeImportada = :QUANTIDADE_IMPORTADA ");
			}
			if(pojo.getQuantidadeRejeitada() != null && !pojo.getQuantidadeRejeitada().equals(Integer.valueOf("0"))) {
				sql.append("	and loteMailing.quantidadeRejeitada = :QUANTIDADE_REJEITADA ");
			}
			
			//BETWEEN entre as datas.
			if(pojo.getDataImportacao() != null && ateDataImportacao != null) {
				sql.append("	and loteMailing.dataImportacao BETWEEN :DATA_INICIO_IMPORTACAO AND :DATA_FIM_IMPORTACAO ");
			}
			else if(pojo.getDataImportacao() != null) {
				sql.append("	and loteMailing.dataImportacao >= :DATA_IMPORTACAO ");
			}
			
			if(pojo.getDataInicioMailing() != null && ateDataInicioMailing != null) {
				sql.append("	and loteMailing.dataImportacao BETWEEN :DATA_INICIO_MAILING AND :DATA_ATE_INICIO_MAILING ");
			}
			else if(pojo.getDataInicioMailing() != null) {
				sql.append("	and loteMailing.dataImportacao >= :DATA_INICIO_MAILING ");
			}
			
			if(pojo.getDataFimMailing() != null && ateDataFimMailing != null) {
				sql.append("	and loteMailing.dataImportacao BETWEEN :DATA_FIM_MAILING AND :DATA_ATE_FIM_MAILING ");
			}
			else if(pojo.getDataFimMailing() != null) {
				sql.append("	and loteMailing.dataImportacao >= :DATA_FIM_MAILING ");
			}
			
			Query q = session().createQuery(sql.toString());
			 
				
			if(pojo.getId() != null && pojo.getId() != BigInteger.ZERO) {
				q.setParameter("ID_LOTE_MAILING", pojo.getId());
			}
			if(pojo.getIdCampanha() != null && !pojo.getIdCampanha().equals(Long.valueOf("0"))) {
				q.setParameter("ID_CAMPANHA", pojo.getIdCampanha());
			}
			if(!StringUtils.isEmpty(pojo.getDesignacao())) {
				q.setParameter("DESIGNACAO_LOTE_MAILING", new StringBuilder("%").append(pojo.getDesignacao()).append("%").toString());
			}
			if(pojo.getQuantidadeImportada() != null && !pojo.getQuantidadeImportada().equals(Integer.valueOf("0"))) {
				q.setParameter("QUANTIDADE_IMPORTADA", pojo.getQuantidadeImportada());
			}
			if(pojo.getQuantidadeRejeitada() != null && !pojo.getQuantidadeRejeitada().equals(Integer.valueOf("0"))) {
				q.setParameter("QUANTIDADE_REJEITADA", pojo.getQuantidadeRejeitada());
			}
			
			//BETWEEN entre as datas.
			if(pojo.getDataImportacao() != null && ateDataImportacao != null) {
				q.setParameter("DATA_INICIO_IMPORTACAO", pojo.getDataImportacao()).setParameter("DATA_FIM_IMPORTACAO", ateDataImportacao);
			}
			else if(pojo.getDataImportacao() != null) {
				q.setParameter("DATA_IMPORTACAO", pojo.getDataImportacao());
			}
			
			if(pojo.getDataInicioMailing() != null && ateDataInicioMailing != null) {
				q.setParameter("DATA_INICIO_MAILING", pojo.getDataInicioMailing()).setParameter("DATA_ATE_INICIO_MAILING", ateDataInicioMailing);
			}
			else if(pojo.getDataInicioMailing() != null) {
				q.setParameter("DATA_INICIO_MAILING", pojo.getDataInicioMailing());
			}
			
			if(pojo.getDataFimMailing() != null && ateDataFimMailing != null) {
				q.setParameter("DATA_FIM_MAILING", pojo.getDataFimMailing()).setParameter("DATA_ATE_FIM_MAILING", ateDataFimMailing);
			}
			else if(pojo.getDataFimMailing() != null) {
				q.setParameter("DATA_FIM_MAILING", pojo.getDataFimMailing());
			}
			
			//nolock no HQL
			q.setLockMode("loteMailing", LockMode.NONE);
			return q.list();
		} catch (Exception ex) {
			throw new DataException(ex);
		}
	}
	
	@Override
	public Integer finalizaLotMailing(LoteMailing pojo) throws DataException {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("update LoteMailing set dataFimMailing =:DATA_FIM, ");
			sql.append(" logUid = :LOG_UID, logHost = :LOG_HOST, ");
			sql.append(" logDate = :LOG_DATE, logSystem = :LOG_SYSTEM, ");
			sql.append(" logObs = :LOG_OBS, logTransaction = :LOG_TRANSACTION ");
			sql.append("	where id = :ID_LOTE_MAILING ");
			Query q = session().createQuery(sql.toString());
			
			
			q.setParameter("DATA_FIM", new Date());
			q.setParameter("LOG_UID", pojo.getLogUid());
			q.setParameter("LOG_HOST", pojo.getLogHost());
			q.setParameter("LOG_DATE", pojo.getLogDate());
			q.setParameter("LOG_SYSTEM", pojo.getLogSystem());
			q.setParameter("LOG_OBS", pojo.getLogObs());
			q.setParameter("LOG_TRANSACTION", pojo.getLogTransaction());
			q.setParameter("ID_LOTE_MAILING", pojo.getId());
			
			int rowCount = q.executeUpdate();
			session().beginTransaction().commit();
			
			return rowCount;
		} catch (Exception ex) {
			throw new DataException(ex);
		}
	}

	@Override
	public List<LoteMailing> findLoteMailingFinalizados() throws DataException {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("flagMailingFinalizado", Boolean.TRUE));
		criteria.add(
				Restrictions.disjunction()
					.add(Restrictions.eq("flagExportadoTipoRegistroHeader", Boolean.FALSE))
					.add(Restrictions.eq("flagExportadoTipoRegistroStatus", Boolean.FALSE))
				);
		criteria.addOrder(Order.asc("dataFimMailing"));
		return criteria.list();
	}
	
	@Override
	public List<LoteMailing> findLoteMailingFinalizadosVendas() throws DataException {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("flagMailingFinalizado", Boolean.TRUE));
		criteria.add(
				Restrictions.disjunction()
				.add(Restrictions.eq("flagExportadoVendaRegistroHeader", Boolean.FALSE))
				.add(Restrictions.eq("flagExportadoVendaRegistroDocumento", Boolean.FALSE))
				.add(Restrictions.eq("flagExportadoVendaRegistroCadastroSegurados", Boolean.FALSE))
				.add(Restrictions.eq("flagExportadoVendaRegistroCobrancaSegurados", Boolean.FALSE))
				.add(Restrictions.eq("flagExportadoVendaRegistroHeaderPPlus", Boolean.FALSE))
				.add(Restrictions.eq("flagExportadoVendaRegistroDocumentoPPlus", Boolean.FALSE))
				.add(Restrictions.eq("flagExportadoVendaRegistroCadastroSeguradosPPlus", Boolean.FALSE))
				.add(Restrictions.eq("flagExportadoVendaRegistroCobrancaSeguradosPPlus", Boolean.FALSE))
				);
		criteria.addOrder(Order.asc("dataFimMailing"));
		return criteria.list();
	}
	
	@Override
	public List<LoteMailing> findLoteMailingFinalizacaoMailing() throws DataException {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("flagMailingFinalizado", Boolean.TRUE));
		criteria.add(
				Restrictions.disjunction()
				.add(Restrictions.eq("flagExportadoTipoRegistroHeader", Boolean.FALSE))
				.add(Restrictions.eq("flagExportadoTipoRegistroStatus", Boolean.FALSE))
				);
		criteria.addOrder(Order.asc("dataFimMailing"));
		return criteria.list();
	}
	
	@Override
	public List<LoteMailing> findLoteMailingFinalizadosArquivoRetorno() throws DataException {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("flagMailingFinalizado", Boolean.TRUE));
		criteria.add(Restrictions.eq("flagExportadoArquivoRetorno", Boolean.FALSE));
		criteria.addOrder(Order.asc("dataFimMailing"));
		return criteria.list();
	}
	

	@Override
	public void insertNuvemTipoRegistroHeader(LoteMailing loteMailing)	throws DataException {
		Query query = session().getNamedQuery("LoteMailing.insertNuvemTipoRegistroHeader");
		query.setParameter("nomeParametroSistema", ParametroSistema.NUVEM_TIPO_REGISTRO_HEADER_REGISTRO);
		query.setParameter("idLoteMailing", loteMailing.getId());
		query.setParameter("referenciaVendaTipoEvento", ParametroSistema.NUVEM_VENDAS_REFERENCIA_VENDA);
		query.executeUpdate();
	}

	@Override
	public void insertNuvemTipoRegistroStatus(LoteMailing loteMailing)
			throws DataException {
		Query query = session().getNamedQuery("LoteMailing.insertNuvemTipoRegistroStatus");
		query.setParameter("nomeParametroSistema", ParametroSistema.NUVEM_FINALIZACAO_TIPO_REGISTRO_STATUS);
		query.setParameter("idLoteMailing", loteMailing.getId());
		query.setParameter("referenciaVendaTipoEvento", ParametroSistema.NUVEM_VENDAS_REFERENCIA_VENDA);
		query.executeUpdate();
	}

//	@Override
//	public BigInteger insertNuvemVendaRegistroHeader(Date dataGeracao)
//			throws DataException {
//		
//		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
//		String dataGeracaoFormat = dateFormat.format(dataGeracao);
//		
//		Query queryId = session().getNamedQuery("LoteMailing.selectIdentCurrentHeader");
//		BigInteger id = ((BigDecimal) queryId.uniqueResult()).toBigInteger().add(new BigInteger("1"));
//		
//		Query query = session().getNamedQuery("LoteMailing.insertNuvemVendaRegistroHeader");
//		query.setParameter("nomeParametroRegistro", ParametroSistema.NUVEM_VENDAS_HEADER_REGISTRO);
//		query.setParameter("nomeParametroLayout", ParametroSistema.NUVEM_VENDAS_HEADER_LAYOUT);
//		query.setParameter("nomeParametroCliente", ParametroSistema.NUVEM_VENDAS_HEADER_CLIENTE);
//		query.setParameter("sequencialArquivo", id);
//		query.setParameter("dataEnvioFormat", dataGeracaoFormat);
//		query.setParameter("dataEnvio", dataGeracao);
//		query.setParameter("referenciaVendaTipoEvento", ParametroSistema.NUVEM_VENDAS_REFERENCIA_VENDA);
//		query.setParameter("referenciaVendaTipoProduto", ParametroSistema.NUVEM_VENDAS_REFERENCIA_PRODUTO_PESSOAL);
//		
//		
//		NuvemVendasRegistroHeader nuvemVendasHeader = (NuvemVendasRegistroHeader) query.uniqueResult();
//		
//		BigInteger id = new BigInteger(((BigInteger) getEntityManager().getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(getClass())).toString());
//		
//		Query updateSequencialCommand = session().getNamedQuery("LoteMailing.updateSequencialCampanha");
//		updateSequencialCommand.setParameter("idCampanha", loteMailing.getCampanha().getId());
//		updateSequencialCommand.setParameter("sequencial", incremental);
//		updateSequencialCommand.executeUpdate();
//		
//		
//		return id;
//	}

	@Override
	public void insertNuvemVendaRegistroDocumento(BigInteger idHeader, Date dataEnvio)
			throws DataException {
		Query query = session().getNamedQuery("LoteMailing.insertNuvemVendaRegistroDocumento");
		query.setParameter("nomeParametroRegistro", ParametroSistema.NUVEM_VENDAS_DOCUMENTO_REGISTRO);
		query.setParameter("nomeParametroTransacao", ParametroSistema.NUVEM_VENDAS_DOCUMENTO_TRANSACAO);
		query.setParameter("nomeParametroParcelamento", ParametroSistema.NUVEM_VENDAS_DOCUMENTO_PARCELAMENTO);
		query.setParameter("nomeParametroEstipulante", ParametroSistema.NUVEM_VENDAS_DOCUMENTO_ESTIPULANTE);
		query.setParameter("nomeParametroDesconto", ParametroSistema.NUVEM_VENDAS_DOCUMENTO_DESCONTO);
		query.setParameter("idHeader", idHeader);
		query.setParameter("dataEnvio", dataEnvio);
		query.setParameter("referenciaVendaTipoEvento", ParametroSistema.NUVEM_VENDAS_REFERENCIA_VENDA);
		query.setParameter("referenciaVendaTipoProduto", ParametroSistema.NUVEM_VENDAS_REFERENCIA_PRODUTO_PESSOAL);
		query.executeUpdate();
	}

	@Override
	public void insertNuvemVendaCadastroSegurados(BigInteger idHeader, Date dataEnvio)
			throws DataException {
		Query query = session().getNamedQuery("LoteMailing.insertNuvemVendaCadastroSegurados");
		query.setParameter("nomeParametroRegistro", ParametroSistema.NUVEM_VENDAS_CADASTRO_REGISTRO);
		query.setParameter("nomeParametroTransacao", ParametroSistema.NUVEM_VENDAS_CADASTRO_TRANSACAO);
		query.setParameter("idHeader", idHeader);
		query.setParameter("dataEnvio", dataEnvio);
		query.setParameter("referenciaVendaTipoEvento", ParametroSistema.NUVEM_VENDAS_REFERENCIA_VENDA);
		query.setParameter("referenciaVendaTipoProduto", ParametroSistema.NUVEM_VENDAS_REFERENCIA_PRODUTO_PESSOAL);
		query.executeUpdate();
	}

	@Override
	public void insertNuvemVendaCobrancaSegurados(BigInteger idHeader, Date dataEnvio) throws DataException {
		Query query = session().getNamedQuery("LoteMailing.insertNuvemVendaCobrancaSegurados");
		query.setParameter("nomeParametroRegistro", ParametroSistema.NUVEM_VENDAS_COBRANCA_REGISTRO);
		query.setParameter("nomeParametroTransacao", ParametroSistema.NUVEM_VENDAS_COBRANCA_TRANSACAO);		
		query.setParameter("idHeader", idHeader);
		query.setParameter("dataEnvio", dataEnvio);
		query.setParameter("referenciaVendaTipoEvento", ParametroSistema.NUVEM_VENDAS_REFERENCIA_VENDA);
		query.setParameter("referenciaVendaTipoProduto", ParametroSistema.NUVEM_VENDAS_REFERENCIA_PRODUTO_PESSOAL);
		query.setParameter("meioCobranca", ParametroSistema.MEIO_COBRANCA);
		query.setParameter("adminCobranca", ParametroSistema.ADMINISTRACAO_COBRANCA);
		query.executeUpdate();
	}
	
//	@Override
//	public BigInteger insertNuvemVendaRegistroHeaderPPlus(Date dataGeracao)
//			throws DataException {
//		
//		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
//		String dataGeracaoFormat = dateFormat.format(dataGeracao);
//		
//		Query queryId = session().getNamedQuery("LoteMailing.selectIdentCurrentHeaderPlus");
//		BigInteger id = ((BigDecimal) queryId.uniqueResult()).toBigInteger().add(new BigInteger("1"));
//		
//		Query query = session().getNamedQuery("LoteMailing.insertNuvemVendaRegistroHeaderPPlus");
//		query.setParameter("nomeParametroRegistro", ParametroSistema.NUVEM_VENDAS_HEADER_REGISTRO);
//		query.setParameter("nomeParametroLayout", ParametroSistema.NUVEM_VENDAS_HEADER_LAYOUT);
//		query.setParameter("nomeParametroCliente", ParametroSistema.NUVEM_VENDAS_HEADER_CLIENTE);
//		query.setParameter("sequencialArquivo", id);
//		query.setParameter("dataEnvioFormat", dataGeracaoFormat);
//		query.setParameter("dataEnvio", dataGeracao);
//		query.setParameter("referenciaVendaTipoEvento", ParametroSistema.NUVEM_VENDAS_REFERENCIA_VENDA);
//		query.setParameter("referenciaVendaTipoProduto", ParametroSistema.NUVEM_VENDAS_REFERENCIA_PRODUTO_PLUS);
//		query.executeUpdate();
//		
//		return id;
//	}
	
	@Override
	public void insertNuvemVendaRegistroDocumentoPPlus(BigInteger idHeader, Date dataEnvio)
			throws DataException {
		Query query = session().getNamedQuery("LoteMailing.insertNuvemVendaRegistroDocumentoPPlus");
		query.setParameter("nomeParametroRegistro", ParametroSistema.NUVEM_VENDAS_DOCUMENTO_REGISTRO);
		query.setParameter("nomeParametroTransacao", ParametroSistema.NUVEM_VENDAS_DOCUMENTO_TRANSACAO);
		query.setParameter("nomeParametroParcelamento", ParametroSistema.NUVEM_VENDAS_DOCUMENTO_PARCELAMENTO);
		query.setParameter("nomeParametroEstipulante", ParametroSistema.NUVEM_VENDAS_DOCUMENTO_ESTIPULANTE);
		query.setParameter("nomeParametroDesconto", ParametroSistema.NUVEM_VENDAS_DOCUMENTO_DESCONTO);
		query.setParameter("idHeader", idHeader);
		query.setParameter("dataEnvio", dataEnvio);
		query.setParameter("referenciaVendaTipoEvento", ParametroSistema.NUVEM_VENDAS_REFERENCIA_VENDA);
		query.setParameter("referenciaVendaTipoProduto", ParametroSistema.NUVEM_VENDAS_REFERENCIA_PRODUTO_PLUS);
		query.executeUpdate();
	}
	
	@Override
	public void insertNuvemVendaCadastroSeguradosPPlus(BigInteger idHeader, Date dataEnvio)
			throws DataException {
		Query query = session().getNamedQuery("LoteMailing.insertNuvemVendaCadastroSeguradosPPlus");
		query.setParameter("nomeParametroRegistro", ParametroSistema.NUVEM_VENDAS_CADASTRO_REGISTRO);
		query.setParameter("nomeParametroTransacao", ParametroSistema.NUVEM_VENDAS_CADASTRO_TRANSACAO);
		query.setParameter("idHeader", idHeader);
		query.setParameter("dataEnvio", dataEnvio);
		query.setParameter("referenciaVendaTipoEvento", ParametroSistema.NUVEM_VENDAS_REFERENCIA_VENDA);
		query.setParameter("referenciaVendaTipoProduto", ParametroSistema.NUVEM_VENDAS_REFERENCIA_PRODUTO_PLUS);
		query.executeUpdate();
	}
	
	@Override
	public void insertNuvemVendaCobrancaSeguradosPPlus(BigInteger idHeader, Date dataEnvio) throws DataException {
		Query query = session().getNamedQuery("LoteMailing.insertNuvemVendaCobrancaSeguradosPPlus");
		query.setParameter("nomeParametroRegistro", ParametroSistema.NUVEM_VENDAS_COBRANCA_REGISTRO);
		query.setParameter("nomeParametroTransacao", ParametroSistema.NUVEM_VENDAS_COBRANCA_TRANSACAO);
		query.setParameter("idHeader", idHeader);
		query.setParameter("dataEnvio", dataEnvio);
		query.setParameter("referenciaVendaTipoEvento", ParametroSistema.NUVEM_VENDAS_REFERENCIA_VENDA);
		query.setParameter("referenciaVendaTipoProduto", ParametroSistema.NUVEM_VENDAS_REFERENCIA_PRODUTO_PLUS);
		query.setParameter("meioCobranca", ParametroSistema.MEIO_COBRANCA);
		query.setParameter("adminCobranca", ParametroSistema.ADMINISTRACAO_COBRANCA);
		query.executeUpdate();
		
	}
	
	@Override
	public void insertNuvemVendaArquivoRetorno(Date dataEnvio) throws DataException {
		Query queryCommand = session().getNamedQuery("LoteMailing.insertNuvemVendaArquivoRetorno");
		queryCommand.setParameter("referenciaVendaTipoEvento", ParametroSistema.NUVEM_VENDAS_REFERENCIA_VENDA);
		queryCommand.setParameter("dataEnvio", dataEnvio);
		queryCommand.executeUpdate();
	}
	
	@Override
	public void insertNuvemVendaRegistroDocumentoDhi(BigInteger idHeader, Date dataEnvio) throws DataException {
		Query query = session().getNamedQuery("LoteMailing.insertNuvemVendaRegistroDocumentoDhi");
		query.setParameter("nomeParametroRegistro", ParametroSistema.NUVEM_VENDAS_DOCUMENTO_REGISTRO);
		query.setParameter("nomeParametroTransacao", ParametroSistema.NUVEM_VENDAS_DOCUMENTO_TRANSACAO);
		query.setParameter("nomeParametroParcelamento", ParametroSistema.NUVEM_VENDAS_DOCUMENTO_PARCELAMENTO);
		query.setParameter("nomeParametroEstipulante", ParametroSistema.NUVEM_VENDAS_DOCUMENTO_ESTIPULANTE);
		query.setParameter("nomeParametroDesconto", ParametroSistema.NUVEM_VENDAS_DOCUMENTO_DESCONTO);
		query.setParameter("idHeader", idHeader);
		query.setParameter("dataEnvio", dataEnvio);
		query.setParameter("referenciaVendaTipoEvento", ParametroSistema.NUVEM_VENDAS_REFERENCIA_VENDA);
		query.setParameter("referenciaVendaTipoProduto", ParametroSistema.NUVEM_VENDAS_REFERENCIA_PRODUTO_DHI);
		query.executeUpdate();
	}
	
	@Override
	public void insertNuvemVendaCadastroSeguradosDhi(BigInteger idHeader, Date dataEnvio) throws DataException {
		Query query = session().getNamedQuery("LoteMailing.insertNuvemVendaCadastroSeguradosDhi");
		query.setParameter("nomeParametroRegistro", ParametroSistema.NUVEM_VENDAS_CADASTRO_REGISTRO);
		query.setParameter("nomeParametroTransacao", ParametroSistema.NUVEM_VENDAS_CADASTRO_TRANSACAO);
		query.setParameter("idHeader", idHeader);
		query.setParameter("dataEnvio", dataEnvio);
		query.setParameter("referenciaVendaTipoEvento", ParametroSistema.NUVEM_VENDAS_REFERENCIA_VENDA);
		query.setParameter("referenciaVendaTipoProduto", ParametroSistema.NUVEM_VENDAS_REFERENCIA_PRODUTO_DHI);
		query.executeUpdate();
	}
	
	@Override
	public void insertNuvemVendaCobrancaSeguradosDhi(BigInteger idHeader, Date dataEnvio) throws DataException {
		Query query = session().getNamedQuery("LoteMailing.insertNuvemVendaCobrancaSeguradosDhi");
		query.setParameter("nomeParametroRegistro", ParametroSistema.NUVEM_VENDAS_COBRANCA_REGISTRO);
		query.setParameter("nomeParametroTransacao", ParametroSistema.NUVEM_VENDAS_COBRANCA_TRANSACAO);		
		query.setParameter("idHeader", idHeader);
		query.setParameter("dataEnvio", dataEnvio);
		query.setParameter("referenciaVendaTipoEvento", ParametroSistema.NUVEM_VENDAS_REFERENCIA_VENDA);
		query.setParameter("referenciaVendaTipoProduto", ParametroSistema.NUVEM_VENDAS_REFERENCIA_PRODUTO_DHI);
		query.setParameter("meioCobranca", ParametroSistema.MEIO_COBRANCA);
		query.setParameter("adminCobranca", ParametroSistema.ADMINISTRACAO_COBRANCA);
		query.executeUpdate();
	}


}
