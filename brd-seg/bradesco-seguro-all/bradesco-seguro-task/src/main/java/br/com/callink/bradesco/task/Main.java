package br.com.callink.bradesco.task;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import br.com.callink.bradesco.task.impl.SincronizaCorporativo;
import br.com.callink.bradesco.task.utils.Tasks;


/**
 * Classe principal para execução de todas as Tasks
 * 
 * @author michael
 *
 */
public class Main {
	private static Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) throws Exception {
		
		/*
		 * Valida configuracao
		 */
		if(args.length == 0){
			logger.error("Exemplo: java -jar {nomeJar} {caminhoArquivoConfiguracao}");
			System.exit(1);
		}
		
		/*
		 * Cria arquivo de configuracao
		 */
		String configFile = args[0];
		Properties configuration = new Properties();
		configuration.load(new FileInputStream(new File(configFile)));
		String task = configuration.getProperty("task_name");
		
		if(task == null || task.trim().equals("")){
			logger.error("O arquivo de configuracao deve conter a key 'task_name', representando o nome da tarefa a executar.");
			logger.error("Tarefas disponiveis: " + Tasks.asList());
			System.exit(1);
		}

		/*
		 * Verifica tarefa a executar
		 */
		if(Tasks.byName(task) == Tasks.SINCRONIZA_PESSOAS_CARGOS_USUARIO){
			//new SincronizaPessoasCargosUsuarios().execute(configuration);
		}else{
			logger.error("Tarefa '" + task + "' nao suportada.");
			logger.error("Tarefas disponiveis: " + Tasks.asList());
			System.exit(1);
		}
	}
}