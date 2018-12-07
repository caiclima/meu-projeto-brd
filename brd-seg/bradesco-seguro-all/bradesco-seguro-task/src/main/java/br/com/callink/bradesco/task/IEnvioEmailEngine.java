package br.com.callink.bradesco.task;

import java.util.Properties;

public interface IEnvioEmailEngine {

	/*
	 * Envia todos os emails que estão na lista e que ainda não foram enviados.
	 */
	void run(Properties properties);

}
