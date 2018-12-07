package br.com.callink.bradesco.seguro.dao.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Query;

import br.com.callink.bradesco.seguro.dao.INuvemVendaRegistroHeaderDhiDAO;
import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.NuvemVendasRegistroHeaderDhi;
import br.com.callink.bradesco.seguro.entity.ParametroSistema;

public class NuvemVendaRegistroHeaderDhiDAO extends GenericHibernateDAOImpl<NuvemVendasRegistroHeaderDhi> implements INuvemVendaRegistroHeaderDhiDAO {

	@Override
	public NuvemVendasRegistroHeaderDhi insertNuvemVendaRegistroHeaderDhi(Date dataGeracao) throws DataException {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
		String dataGeracaoFormat = dateFormat.format(dataGeracao);
		
		Query query = session().getNamedQuery("LoteMailing.insertNuvemVendaRegistroHeaderDhi");
		query.setParameter("nomeParametroRegistro", ParametroSistema.NUVEM_VENDAS_HEADER_REGISTRO);
		query.setParameter("nomeParametroCliente", ParametroSistema.NUVEM_VENDAS_HEADER_CLIENTE);
		query.setParameter("dataEnvioFormat", dataGeracaoFormat);
		query.setParameter("dataEnvio", dataGeracao);
		query.setParameter("referenciaVendaTipoEvento", ParametroSistema.NUVEM_VENDAS_REFERENCIA_VENDA);
		query.setParameter("referenciaVendaTipoProduto", ParametroSistema.NUVEM_VENDAS_REFERENCIA_PRODUTO_DHI);
		query.setParameter("identificacaoCampanha", ParametroSistema.IDENTIFICACAO_CAMPANHA);
		
		Object[] headerObject = (Object[]) query.uniqueResult();
		
		if(headerObject == null || headerObject.length <= 0) { return null; }
		NuvemVendasRegistroHeaderDhi nuvemVendasHeader = new NuvemVendasRegistroHeaderDhi();
		
		nuvemVendasHeader.setTipoRegistro((String) headerObject[0]);
		nuvemVendasHeader.setCliente((String) headerObject[1]);
		nuvemVendasHeader.setProduto((String) headerObject[2]);
		nuvemVendasHeader.setIdentificacaoCampanha((String) headerObject[3]);
		nuvemVendasHeader.setDataGeracao((String) headerObject[4]);
		nuvemVendasHeader.setSequencialArquivo((String) headerObject[5]);
		nuvemVendasHeader.setFiller((String) headerObject[6]);
		nuvemVendasHeader.setSiglaProduto((String) headerObject[7]);
		nuvemVendasHeader.setIdLoteMailing((BigInteger) headerObject[8]);
		
		
		return save(nuvemVendasHeader);
	}
	
	@Override
	public void updateNuvemVendaRegistroHeaderDhi(NuvemVendasRegistroHeaderDhi nuvemVendasRegistroHeaderDhi) throws DataException {

		Query queryUpdate = session().getNamedQuery("LoteMailing.updateSequencialHeaderDhi");
		queryUpdate.setParameter("sequencial", nuvemVendasRegistroHeaderDhi.getId());
		queryUpdate.executeUpdate();
	}

}

