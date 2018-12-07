package br.com.callink.bradesco.seguro.service.impl;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.callink.bradesco.seguro.commons.utils.DateUtils;
import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.dao.INuvemVendaRegistroHeaderDhiDAO;
import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.NuvemVendasRegistroHeaderDhi;
import br.com.callink.bradesco.seguro.entity.ParametroSistema;
import br.com.callink.bradesco.seguro.entity.TipoEvento;
import br.com.callink.bradesco.seguro.service.INuvemVendaRegistroHeaderDhiService;
import br.com.callink.bradesco.seguro.service.IParametroSistemaService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;

@Stateless
public class NuvemVendaRegistroHeaderDhiService extends GenericCrudServiceImpl<NuvemVendasRegistroHeaderDhi> implements INuvemVendaRegistroHeaderDhiService {
	
	@Inject
	private INuvemVendaRegistroHeaderDhiDAO nuvemVendaRegistroHeaderDhiDAO;
	
	
	@EJB
	private IParametroSistemaService<TipoEvento> parametroSistemaService;
	
	
	@Override
	protected INuvemVendaRegistroHeaderDhiDAO getDAO() {
		return nuvemVendaRegistroHeaderDhiDAO;
	}
	
	private void preencherNomeArquivo(NuvemVendasRegistroHeaderDhi nuvem) {
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
		nomeArquivo.append(nuvem.getSiglaProduto());
		nomeArquivo.append(((StringUtils.toString(mes).length() == 2) ? mes : "0" + mes));
		nomeArquivo.append(sequencial);		
		
		nuvem.setNomeArquivo(nomeArquivo.toString());
	}

	@Override
	public NuvemVendasRegistroHeaderDhi geraNuvemVendaRegistroHeaderDhi(Date dataEnvio) throws ServiceException {
		try {
			NuvemVendasRegistroHeaderDhi nuvem = getDAO().insertNuvemVendaRegistroHeaderDhi(dataEnvio);
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
