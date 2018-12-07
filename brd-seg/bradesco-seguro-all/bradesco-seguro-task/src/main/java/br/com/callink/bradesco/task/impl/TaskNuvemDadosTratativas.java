package br.com.callink.bradesco.task.impl;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.callink.bradesco.seguro.service.INuvemDadosTratativaService;

@Singleton
@Startup
public class TaskNuvemDadosTratativas {
	
	@Inject
	private INuvemDadosTratativaService nuvemDadosService;
	
	private static final Logger logger = Logger.getLogger(TaskNuvemDadosTratativas.class);
	
	@Schedule(minute="10", hour="*")
	public void execute() throws Exception {
		logger.info("Início task de tratativas..");
		
		try{
			
			nuvemDadosService.gerarNuvemDadosTratativas();
		
		}catch(Exception e){
			logger.error("Erro ao executar task de geração de "
					+ "nuvem de dados das tratativas. Motivo: " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		
		logger.info("Fim task de tratativas.");
	}

}
