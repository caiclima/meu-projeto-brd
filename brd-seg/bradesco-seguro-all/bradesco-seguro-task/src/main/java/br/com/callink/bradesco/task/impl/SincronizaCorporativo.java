package br.com.callink.bradesco.task.impl;

import java.util.Date;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import javax.ejb.AccessTimeout;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.TransactionTimeout;

import br.com.callink.bradesco.dao.utils.CorporativoConnection;
import br.com.callink.bradesco.seguro.commons.utils.DateUtils;
import br.com.callink.bradesco.seguro.commons.utils.NumberUtils;
import br.com.callink.bradesco.seguro.entity.Cargo;
import br.com.callink.bradesco.seguro.entity.Dominio;
import br.com.callink.bradesco.seguro.entity.ParametroSistema;
import br.com.callink.bradesco.seguro.entity.Pessoa;
import br.com.callink.bradesco.seguro.entity.Usuario;
import br.com.callink.bradesco.seguro.service.IParametroSistemaService;
import br.com.callink.bradesco.task.ITask;
import br.com.callink.bradesco.task.sincronizadores.ISincronizadorCargos;
import br.com.callink.bradesco.task.sincronizadores.ISincronizadorDominios;
import br.com.callink.bradesco.task.sincronizadores.ISincronizadorPessoas;
import br.com.callink.bradesco.task.sincronizadores.ISincronizadorUsuarios;
import br.com.callink.bradesco.task.sincronizadores.utils.ParametrosUtils;

@Singleton
public class SincronizaCorporativo implements ITask {
	
	private final Logger LOGGER = Logger.getLogger(SincronizaCorporativo.class);
	protected final static String MSG_BUNDLE = "bundle";
	public static final Integer DEFAULT_NUMERO_EXECUCOES = 1;
	
	@SuppressWarnings("rawtypes")
	@EJB
	private IParametroSistemaService parametroSistemaService;
	
	@EJB
	private ISincronizadorCargos<Cargo> sincronizadorCargos;
	
	@EJB
	private ISincronizadorDominios<Dominio> sincronizadorDominios;
	
	@EJB
	private ISincronizadorPessoas<Pessoa> sincronizadorPessoas;
	
	@EJB
	private ISincronizadorUsuarios<Usuario> sincronizadorUsuarios;

	@Override
	@Schedule(minute = "*/60", hour = "*", info="Task para sincronizar os usuarios da base corporativa com a base do bradesco", persistent=false)
	@AccessTimeout(-1)
	@TransactionTimeout(unit=TimeUnit.HOURS, value=4)
	public void execute() throws Exception {
		
		if(!ParametrosUtils.getFlagExecutaSincronizador(parametroSistemaService)){
			LOGGER.info("Task de sincronização não executada. Para executá-la, "
					+ "altere o valor do Parametro de Sistema '" 
					+ ParametroSistema.PARAMETRO_EXECUTA_SINCRONIZADOR + "'.");
			return;
		}
		
		String parametroNumeroDePermicoes = ParametrosUtils.getNumeroPermissoes(parametroSistemaService);
		
		try {
			Integer numeroDePermicoes = NumberUtils.toInteger(parametroNumeroDePermicoes);
		    Semaphore semaphore = new Semaphore(((numeroDePermicoes != null) ? numeroDePermicoes : DEFAULT_NUMERO_EXECUCOES));
		    ExecutorTask task = new ExecutorTask(semaphore);
		    task.start();
		} catch (Exception e) {
			LOGGER.warn(e.getMessage());
		}
	}
	
	private class ExecutorTask extends Thread {
		
	    private Semaphore semaforo;
	 
	    public ExecutorTask(Semaphore semaphore) {
	        this.semaforo = semaphore;
	    }
	    
	    //TODO testar semaforo - verificar se funciona certinho no ejb, verificar uso da thread-safe
	    private void processar() {
	        try {
	        	LOGGER.info("Iniciando Sincronizacao com o Corporativo...");
	    		
	    		Date init = new Date();

	    		try {
	    			
	    			sincronizadorCargos.sincronizar();
	    			
	    			sincronizadorDominios.sincronizar();
	    			
	    			sincronizadorPessoas.sincronizar();
	    			
	    			sincronizadorUsuarios.sincronizar();

	    		} catch (Exception e) {
	    			LOGGER.error(e.getMessage(), e);
	    		} finally {
	    			ParametrosUtils.resetAll();
	    			CorporativoConnection.closeConnection();
	    		}

	    		LOGGER.info("Sincronizacao finalizada!");
	    		LOGGER.info("Init: " + DateUtils.format(init, "HH:mm:ss") + " End: " +  DateUtils.format(new Date(), "HH:mm:ss"));
	        } catch (Exception e) {
	        	LOGGER.error(e.getMessage());
	        }
	    }
	    
	    public void run() {
	        try {
	            semaforo.acquire();
	            processar();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        } finally {
	            semaforo.release();
	        }
	    }

	}

}