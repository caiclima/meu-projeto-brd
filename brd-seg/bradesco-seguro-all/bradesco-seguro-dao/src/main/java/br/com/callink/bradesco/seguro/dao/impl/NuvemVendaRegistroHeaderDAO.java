package br.com.callink.bradesco.seguro.dao.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Query;

import br.com.callink.bradesco.seguro.dao.INuvemVendaRegistroHeaderDAO;
import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.NuvemVendasRegistroHeader;
import br.com.callink.bradesco.seguro.entity.ParametroSistema;

public class NuvemVendaRegistroHeaderDAO extends GenericHibernateDAOImpl<NuvemVendasRegistroHeader> implements INuvemVendaRegistroHeaderDAO {

	@Override
	public NuvemVendasRegistroHeader insertNuvemVendaRegistroHeader(Date dataGeracao)
			throws DataException {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
		String dataGeracaoFormat = dateFormat.format(dataGeracao);
		
		Query query = session().getNamedQuery("LoteMailing.insertNuvemVendaRegistroHeader");
		query.setParameter("nomeParametroRegistro", ParametroSistema.NUVEM_VENDAS_HEADER_REGISTRO);
		query.setParameter("nomeParametroLayout", ParametroSistema.NUVEM_VENDAS_HEADER_LAYOUT);
		query.setParameter("nomeParametroCliente", ParametroSistema.NUVEM_VENDAS_HEADER_CLIENTE);
		query.setParameter("dataEnvioFormat", dataGeracaoFormat);
		query.setParameter("dataEnvio", dataGeracao);
		query.setParameter("referenciaVendaTipoEvento", ParametroSistema.NUVEM_VENDAS_REFERENCIA_VENDA);
		query.setParameter("referenciaVendaTipoProduto", ParametroSistema.NUVEM_VENDAS_REFERENCIA_PRODUTO_PESSOAL);
		query.setParameter("identificacaoCampanha", ParametroSistema.IDENTIFICACAO_CAMPANHA);
		
		Object[] headerObject = (Object[]) query.uniqueResult();
		
		if(headerObject == null || headerObject.length <= 0) { return null; }
		NuvemVendasRegistroHeader nuvemVendasHeader = new NuvemVendasRegistroHeader();
		
		nuvemVendasHeader.setTipoRegistro((String) headerObject[0]);
		nuvemVendasHeader.setTipoLayout((String) headerObject[1]);
		nuvemVendasHeader.setCliente((String) headerObject[2]);
		nuvemVendasHeader.setProduto((String) headerObject[3]);
		nuvemVendasHeader.setIdentificacaoCampanha((String) headerObject[4]);
		nuvemVendasHeader.setDataGeracao((String) headerObject[5]);
		nuvemVendasHeader.setSequencialArquivo((String) headerObject[6]);
		nuvemVendasHeader.setFiller((String) headerObject[7]);
		nuvemVendasHeader.setProdutoSigla((String) headerObject[8]);
		nuvemVendasHeader.setIdLoteMailing((BigInteger) headerObject[9]);
		
		
		return save(nuvemVendasHeader);
	}
	
	@Override
	public void updateNuvemVendaRegistroHeader(NuvemVendasRegistroHeader nuvemVendasRegistroHeader)
			throws DataException {

		Query queryUpdate = session().getNamedQuery("LoteMailing.updateSequencialHeader");
		queryUpdate.setParameter("sequencial", nuvemVendasRegistroHeader.getId());
		queryUpdate.executeUpdate();
	}

}


