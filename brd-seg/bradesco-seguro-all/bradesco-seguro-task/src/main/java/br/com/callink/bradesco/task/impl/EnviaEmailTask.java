package br.com.callink.bradesco.task.impl;

import java.util.Properties;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import br.com.callink.bradesco.seguro.entity.ParametroSistema;
import br.com.callink.bradesco.seguro.service.IEmailService;
import br.com.callink.bradesco.seguro.service.IParametroSistemaService;
import br.com.callink.bradesco.task.IEnvioEmailEngine;
import br.com.callink.bradesco.task.ITask;

@Startup
@Singleton
public class EnviaEmailTask implements ITask {

	private static final Logger LOGGER = Logger.getLogger(EnviaEmailTask.class.getName());
	
	@EJB
	private IEnvioEmailEngine envioEmailEngine;
	
	@EJB
	private IParametroSistemaService<ParametroSistema> parametroSistemaService;
	
	@SuppressWarnings("rawtypes")
	@EJB
    private IEmailService emailService;
	
	private Properties properties;
	
	@Override
	@Schedule(minute = "*/10", hour = "*", info = "Task para envio de email", persistent=false)
	public void execute() throws Exception {
		LOGGER.info("Preparar inicio EnviaEmailTask");
		
			
		if(properties==null){
			properties = emailService.enviaEmailProperties();
		}
		LOGGER.info("Task iniciada");
		envioEmailEngine.run(properties);
		
		LOGGER.info("EnviaEmailTask Encerrada");
	}
}
