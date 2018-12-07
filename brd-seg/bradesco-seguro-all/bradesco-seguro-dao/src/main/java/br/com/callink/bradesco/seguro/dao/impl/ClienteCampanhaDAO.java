package br.com.callink.bradesco.seguro.dao.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.dao.IClienteCampanhaDAO;
import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.dto.ClienteCampanhaDTO;
import br.com.callink.bradesco.seguro.entity.ClienteCampanha;

/**
 * Data Access Object (DAO) de Cliente Campanha.
 * 
 * @author swb.thiagohenrique
 * 
 */
public class ClienteCampanhaDAO extends
		GenericHibernateDAOImpl<ClienteCampanha> implements IClienteCampanhaDAO {

	public ClienteCampanha buscarClienteCampanha(BigInteger idClienteCampanha)
			throws DataException {
		try {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder
					.append("SELECT clienteCampanha FROM ClienteCampanha AS clienteCampanha ");
			stringBuilder
					.append("INNER JOIN FETCH clienteCampanha.contatoMailing AS contatoMailing ");
			stringBuilder
					.append("INNER JOIN FETCH clienteCampanha.campanha AS campanha ");
			// stringBuilder.append("INNER JOIN campanha.eventoCampanhaList AS eventoCampanhaList ");
			// stringBuilder.append("INNER JOIN eventoCampanhaList.evento AS evento ");
			// stringBuilder.append("INNER JOIN evento.tipoEvento AS tipoEvento ");
			stringBuilder.append("WHERE ");
			// stringBuilder.append(" clienteCampanha.flagEnabled = :clienteCampanhaFlagEnabled AND ");
			// stringBuilder.append("evento.flagEnabled = :eventoFlagEnabled AND ");
			stringBuilder.append("clienteCampanha.id = :clienteCampanhaId ");

			Query query = getEntityManager().createQuery(
					stringBuilder.toString());
			// query.setParameter("clienteCampanhaFlagEnabled", Boolean.TRUE);
			// query.setParameter("eventoFlagEnabled", Boolean.TRUE);
			query.setParameter("clienteCampanhaId", idClienteCampanha);

			return (ClienteCampanha) query.getSingleResult();

		} catch (NoResultException e) {
			throw new DataException("Cliente campanha não foi encontrado! ", e);

		} catch (Exception e) {
			throw new DataException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClienteCampanha> findClienteCampVirgemByidLoteMailing(
			BigInteger idLoteMailing, boolean flagGateway) throws DataException {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append(" select distinct cliente_campanha from HistoricoContato as historico_contato ");
		stringBuilder
				.append(" \n RIGHT JOIN  historico_contato.clienteCampanha as cliente_campanha ");
		stringBuilder
				.append(" \n INNER JOIN cliente_campanha.contatoMailing as contato_mailing ");
		stringBuilder
				.append(" \n INNER JOIN contato_mailing.loteMailing as lote_mailing ");
		stringBuilder
				.append(" \n LEFT JOIN historico_contato.evento as evento with evento.flagGateway = :FLAG_GATWAY ");
		stringBuilder.append(" \n WHERE ");
		stringBuilder.append(" \n lote_mailing.id = :ID_LOTE_MAILING AND ");
		stringBuilder.append(" \n evento.id is null ");

		org.hibernate.Query query = session().createQuery(
				stringBuilder.toString());

		query.setParameter("ID_LOTE_MAILING", idLoteMailing);
		query.setParameter("FLAG_GATWAY", flagGateway);

		query.setLockMode("cliente_campanha", LockMode.NONE);
		query.setLockMode("contato_mailing", LockMode.NONE);
		query.setLockMode("lote_mailing", LockMode.NONE);
		query.setLockMode("historico_contato", LockMode.NONE);
		query.setLockMode("evento", LockMode.NONE);

		return (List<ClienteCampanha>) query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClienteCampanha> findClienteCampByIdEventoAndidLoteMailing(
			Long idEvento, Integer idLoteMailing) throws DataException {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select clienteCamp ");
		stringBuilder.append(" from ClienteCampanha as clienteCamp ");
		stringBuilder.append("	 ,ContatoMailing as contato ");
		stringBuilder.append("	 ,Evento as evento ");
		stringBuilder
				.append(" where clienteCamp.contatoMailing.id = contato.id ");
		stringBuilder.append("   and evento.id = clienteCamp.idEvento ");
		stringBuilder
				.append("   and contato.idLoteMailing = :ID_LOTE_MAILING ");
		stringBuilder.append("   and clienteCamp.idEvento = :ID_EVENTO ");
		stringBuilder
				.append("	 and (evento.flagGateway = 1 or evento.flagGateway is null) ");

		org.hibernate.Query query = session().createQuery(
				stringBuilder.toString());

		query.setParameter("ID_LOTE_MAILING", idLoteMailing);
		query.setParameter("ID_EVENTO",
				BigInteger.valueOf(idEvento.longValue()));

		query.setLockMode("clienteCamp", LockMode.NONE);
		query.setLockMode("contato", LockMode.NONE);
		query.setLockMode("evento", LockMode.NONE);

		return (List<ClienteCampanha>) query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClienteCampanhaDTO> consultarClienteCampanha(String nome,
			String cnpj, String nomeUsuario, Short codigoDominio) {
		StringBuilder hql = new StringBuilder();
		Map<String, Object> param = new Hashtable<>();

		hql.append(" SELECT	cliente.id as idClienteCampanha, ");
		hql.append(" 			telefone.ddd as ddd, ");
		hql.append(" 			telefone.telefone as telefone, ");
		hql.append(" 			contato.cnpj as cnpj, ");
		hql.append(" 			contato.razaoSocial as nome ");
		hql.append(" FROM		ClienteCampanha cliente, ");
		hql.append(" 			ContatoMailing contato,");
		hql.append(" 			TelefoneCliente telefone, ");
		// hql.add(" 			Usuario usuario ");
		hql.append(" 			Login credencial ");
		hql.append(" WHERE		cliente.idContatoMailing = contato.id ");
		hql.append(" AND		cliente.id = telefone.idClienteCampanha ");

		// hql.add(" AND		usuario.id = cliente.idUsuario ");
		hql.append(" AND		credencial.idUsuario = cliente.idUsuario ");
		// hql.add(" AND		usuario.nomeUsuario = ':nomeUsuario' ");
		hql.append(" AND credencial.login = :nomeUsuario AND credencial.ativo = :ATIVO_INATIVO AND credencial.codigoDominio = :COD_DOMINIO");
		param.put("nomeUsuario", nomeUsuario);
		param.put("COD_DOMINIO", codigoDominio);
		param.put("ATIVO_INATIVO", Boolean.TRUE);

		if (!StringUtils.isEmpty(nome)) {
			hql.append(" AND	LOWER(contato.razaoSocial) like :nome ");
			param.put("nome", nome.concat("%"));
		}

		if (!StringUtils.isEmpty(cnpj)) {
			hql.append(" AND	contato.cnpj = :NUM_CNPJ ");
			param.put("NUM_CNPJ", cnpj);
		}
		hql.append(" ORDER BY contato.razaoSocial ASC, cliente.id DESC  ");

		org.hibernate.Query query = session().createQuery(hql.toString());

		for (String key : param.keySet()) {
			query.setParameter(key, param.get(key));
		}

		query.setResultTransformer(Transformers
				.aliasToBean(ClienteCampanhaDTO.class));
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClienteCampanhaDTO> consultarClienteAtendimento(String nome,
			String cnpj, String nomeUsuario, Short codigoDominio)
			throws DataException {
		StringBuilder hql = new StringBuilder();
		Map<String, Object> param = new Hashtable<>();

		hql.append(" SELECT	cliente.id as idClienteCampanha, ");
		hql.append(" 			contato.cnpj as cnpj, ");
		hql.append(" 			contato.razaoSocial as nome ");
		hql.append(" FROM		ClienteCampanha cliente, ");
		hql.append(" 			ContatoMailing contato,");
		// hql.add(" 			Usuario usuario ");
		hql.append(" 			Login credencial ");
		hql.append(" WHERE		cliente.idContatoMailing = contato.id ");
		// hql.add(" AND		((usuario.id = cliente.idUsuario AND usuario.nomeUsuario = ':nomeUsuario') OR");
		hql.append(" AND		((credencial.idUsuario = cliente.idUsuario AND credencial.login = :nomeUsuario AND credencial.ativo = :ATIVO_INATIVO AND credencial.codigoDominio = :COD_DOMINIO) OR");
		hql.append(" cliente.idUsuario IS NULL)");
		param.put("nomeUsuario", nomeUsuario);
		param.put("COD_DOMINIO", codigoDominio);
		param.put("ATIVO_INATIVO", Boolean.TRUE);

		if (!StringUtils.isEmpty(nome)) {
			hql.append(" AND	LOWER(contato.razaoSocial) like :nome ");
			param.put("nome", nome.toLowerCase().concat("%"));
		}

		if (!StringUtils.isEmpty(cnpj)) {
			hql.append(" AND	contato.cnpj = :NUM_CNPJ ");
			param.put("NUM_CNPJ", cnpj);
		}

		hql.append(" GROUP BY cliente.id, contato.cnpj,contato.razaoSocial");
		hql.append(" ORDER BY contato.razaoSocial ASC, cliente.id DESC ");

		org.hibernate.Query query = session().createQuery(hql.toString());

		for (String key : param.keySet()) {
			query.setParameter(key, param.get(key));
		}

		query.setResultTransformer(Transformers
				.aliasToBean(ClienteCampanhaDTO.class));
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClienteCampanhaDTO> consultarClienteAtendimentoReceptivo(
			String nome, String cnpj, String nomeUsuario, Short codigoDominio)
			throws DataException {
		StringBuilder hql = new StringBuilder();
		Map<String, Object> param = new Hashtable<>();

		hql.append(" SELECT	cliente.id as idClienteCampanha, ");
		hql.append(" 			contato.cnpj as cnpj, ");
		hql.append(" 			contato.razaoSocial as nome ");
		hql.append(" FROM		ClienteCampanha cliente, ");
		hql.append(" 			ContatoMailing contato,");
		hql.append(" 			Login credencial ");
		hql.append(" WHERE		cliente.idContatoMailing = contato.id	 ");

		if (!StringUtils.isEmpty(nome)) {
			hql.append(" AND	LOWER(contato.razaoSocial) like :nome ");
			param.put("nome", nome.toLowerCase().concat("%"));
		}

		if (!StringUtils.isEmpty(cnpj)) {
			hql.append(" AND	contato.cnpj = :NUM_CNPJ ");
			param.put("NUM_CNPJ", cnpj);
		}

		hql.append(" GROUP BY cliente.id, contato.cnpj,contato.razaoSocial");
		hql.append(" ORDER BY contato.razaoSocial ASC, cliente.id DESC");

		org.hibernate.Query query = session().createQuery(hql.toString());

		for (String key : param.keySet()) {
			query.setParameter(key, param.get(key));
		}

		query.setResultTransformer(Transformers
				.aliasToBean(ClienteCampanhaDTO.class));
		return query.list();
	}

	public Integer contaRegistroComMesmoNome(BigInteger id, String nome) {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("flagEnabled", Boolean.TRUE));
		criteria.add(Restrictions.eq("nomeCampanha", nome));

		if (id != null) {
			criteria.add(Restrictions.ne("id", id));
		}
		Long valor = (Long) criteria.setProjection(Projections.rowCount())
				.uniqueResult();
		return valor.intValue();
	}

	@SuppressWarnings("unchecked")
	public List<ClienteCampanha> findContatoMailingByCnpjs(List<String> cnpjs)
			throws DataException {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("SELECT clienteCampanha FROM ClienteCampanha AS clienteCampanha ");
		stringBuilder
				.append("INNER JOIN FETCH clienteCampanha.contatoMailing AS contatoMailing ");
		stringBuilder.append(" WHERE contatoMailing.cnpj IN(:cnpjList) ");
		stringBuilder.append(" ORDER BY contatoMailing.razaoSocial ");

		Query query = getEntityManager().createQuery(stringBuilder.toString());
		query.setParameter("cnpjList", cnpjs);

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClienteCampanha> findClienteCampanhasInCampanhaAtivaBycnpjs(
			List<String> cnpjs) throws DataException {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("SELECT clienteCampanha FROM ClienteCampanha AS clienteCampanha ");
		stringBuilder
				.append("INNER JOIN FETCH clienteCampanha.contatoMailing AS contatoMailing ");
		stringBuilder
				.append("INNER JOIN FETCH clienteCampanha.campanha AS campanha ");
		stringBuilder.append(" WHERE contatoMailing.cnpj IN(:cnpjList) ");
		stringBuilder.append(" AND ( campanha.dataFim IS NULL    ");
		stringBuilder.append(" OR campanha.dataFim >= :DATA_FIM    )");
		stringBuilder.append(" AND campanha.flagEnabled = 1    ");
		stringBuilder.append(" ORDER BY contatoMailing.razaoSocial ");

		Query query = getEntityManager().createQuery(stringBuilder.toString());
		query.setParameter("cnpjList", cnpjs);
		query.setParameter("DATA_FIM", new Date());

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<ClienteCampanha> retornaClientesExistentes(List<String> cnpjs)
			throws DataException {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" SELECT	cliente ");
		stringBuilder.append(" FROM		ClienteCampanha cliente ");
		stringBuilder
				.append("  INNER JOIN FETCH cliente.contatoMailing AS contato ");
		stringBuilder
				.append("  INNER JOIN FETCH cliente.campanha AS campanha ");
		stringBuilder.append(" WHERE contato.cnpj in ( :cnpjs ) ");
		stringBuilder.append("  ORDER BY cliente.id ");

		Query query = getEntityManager().createQuery(stringBuilder.toString());
		query.setParameter("cnpjs", cnpjs);

		return query.getResultList();
	}

	@Override
	public ClienteCampanha buscarClienteCampanhaPorId(
			BigInteger idClienteCampanha) throws DataException {
		
		org.hibernate.Query query = session().getNamedQuery(
				"ClienteCampanha.buscarClienteCampanhaPorId");
		query.setParameter("idClienteCampanha", idClienteCampanha);
		return (ClienteCampanha) query.uniqueResult();
	}
}