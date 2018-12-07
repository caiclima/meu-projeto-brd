package br.com.callink.bradesco.seguro.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.callink.bradesco.seguro.dao.IGenericDAO;
import br.com.callink.bradesco.seguro.dao.INuvemDadosTratativaDAO;
import br.com.callink.bradesco.seguro.entity.NuvemDadosTratativa;
import br.com.callink.bradesco.seguro.entity.ParametroSistema;
import br.com.callink.bradesco.seguro.service.INuvemDadosTratativaService;
import br.com.callink.bradesco.seguro.service.IParametroSistemaService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;

@Stateless
public class NuvemDadosTratativaService 
	extends GenericCrudServiceImpl<NuvemDadosTratativa> 
	implements INuvemDadosTratativaService{
	
	@Inject
	private INuvemDadosTratativaDAO dao;
	
	@SuppressWarnings("rawtypes")
	@Inject
	private IParametroSistemaService parametroSistemaService;
	
	private final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static final Logger logger = Logger.getLogger(NuvemDadosTratativaService.class);
	
	@Override
	public void gerarNuvemDadosTratativas() throws ServiceException {
		ParametroSistema param = 
				parametroSistemaService.buscarPorNome("DATA_REFERENCIA_NUVEM_DADOS_TRATATIVA");
		
		if(param == null){
			logger.error("Parâmetro de sistema 'DATA_REFERENCIA_NUVEM_DADOS_TRATATIVA' não encontrado.");
			return;
		}
		
		List<Object> params = new ArrayList<Object>();
		Date dataInicial = new Date();
		
		try {
			dataInicial = df.parse(param.getValorParametroSistema());
		} catch (ParseException e) {
			logger.error("Erro ao converter o valor da data.");
		} 
		
		params.add(dataInicial);
		params.add(new Date());
		
		dao.callStoredProcedure("uspExportacaoNuvemDadosTratativas", params);
		
		param.setValorParametroSistema(df.format(new Date()));
		parametroSistemaService.atualizar(param, "taskNuvemDados", "taskNuvemDados");
		
	}

	@Override
	protected IGenericDAO<NuvemDadosTratativa> getDAO() {
		return dao;
	}

}
