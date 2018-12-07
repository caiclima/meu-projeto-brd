package br.com.callink.bradesco.seguro.service.impl;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.callink.bradesco.seguro.commons.utils.DateUtils;
import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.dao.INuvemVendaRegistroHeaderPPlusDAO;
import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.NuvemVendasRegistroHeaderPplus;
import br.com.callink.bradesco.seguro.entity.ParametroSistema;
import br.com.callink.bradesco.seguro.entity.TipoEvento;
import br.com.callink.bradesco.seguro.service.INuvemVendaRegistroHeaderPPlusService;
import br.com.callink.bradesco.seguro.service.IParametroSistemaService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;

@Stateless
public class NuvemVendaRegistroHeaderPPlusService extends GenericCrudServiceImpl<NuvemVendasRegistroHeaderPplus> implements INuvemVendaRegistroHeaderPPlusService {
	
	@Inject
	private INuvemVendaRegistroHeaderPPlusDAO nuvemVendaRegistroHeaderPPLusDAO;
	
	
	@EJB
	private IParametroSistemaService<TipoEvento> parametroSistemaService;
	
	
	@Override
	protected INuvemVendaRegistroHeaderPPlusDAO getDAO() {
		return nuvemVendaRegistroHeaderPPLusDAO;
	}
	
	private void preencherNomeArquivo(NuvemVendasRegistroHeaderPplus nuvem) {
		String codigoBradescoProtecao = null;
		
		try {
			codigoBradescoProtecao = parametroSistemaService.buscarValorParametro(ParametroSistema.CODIGO_BRADESCO_CARTOES);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao buscar parametro " 
					+ ParametroSistema.CODIGO_BRADESCO_CARTOES + ". Erro: " + e.getMessage(), e);
		}
		
		if (StringUtils.isEmpty(codigoBradescoProtecao)) {
			throw new IllegalStateException("Parâmetro de Sistema '" + ParametroSistema.CODIGO_BRADESCO_CARTOES 
					+ "' não encontrado! É necessário do mesmo para criar o código da Apólice.");
		}
		
		String siglaCallink = null;
		
		try {
			siglaCallink = parametroSistemaService.buscarValorParametro(ParametroSistema.SIGLA_CALLINK);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao buscar parametro " 
					+ ParametroSistema.SIGLA_CALLINK + ". Erro: " + e.getMessage(), e);
		}
		
		if (StringUtils.isEmpty(siglaCallink)) {
			throw new IllegalStateException("Parâmetro de Sistema '" + ParametroSistema.SIGLA_CALLINK 
					+ "' não encontrado! É necessário do mesmo para criar o código da Apólice.");
		}
		
		Integer mes = DateUtils.getMesData(new Date());
		String sequencial = null;
		
		if (nuvem.getId().toString().length() == 1) {
			sequencial = "00" + nuvem.getId(); 
		} else if (nuvem.getId().toString().length() == 2)
			sequencial = "0" + nuvem.getId();
		else {
			sequencial = nuvem.getId().toString();
		}
		
		StringBuilder nomeArquivo = new StringBuilder(); 
		nomeArquivo.append(codigoBradescoProtecao);
		nomeArquivo.append(nuvem.getProduto());
		nomeArquivo.append(siglaCallink);
		nomeArquivo.append(nuvem.getIdLoteMailing());
		nomeArquivo.append(nuvem.getProdutoSigla());
		nomeArquivo.append(((StringUtils.toString(mes).length() == 2) ? mes : "0" + mes));
		nomeArquivo.append(sequencial);		
		
		nuvem.setNomeArquivo(nomeArquivo.toString());
	}


	@Override
	public NuvemVendasRegistroHeaderPplus geraNuvemVendaRegistroHeaderPPlus(Date dataEnvio) throws ServiceException {
		try {
			NuvemVendasRegistroHeaderPplus nuvem = getDAO().insertNuvemVendaRegistroHeaderPPlus(dataEnvio);
			if (nuvem != null && nuvem.getId() != null) {
				preencherNomeArquivo(nuvem);
				nuvem.setSequencialArquivo(StringUtils.preencheCom(nuvem.getId().toString(), "0", 7, 1));
				getDAO().update(nuvem);
			}
			return nuvem;
		} catch(DataException ex) {
			throw new ServiceException(ex);
		}
	}


}
