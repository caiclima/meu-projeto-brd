package br.com.callink.bradesco.seguro.dao.cdi;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Producer;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * {@link Producer} de todos os bancos de dados acessados pela aplicação.
 * 
 * @author michael
 * @since 12/06/2013
 * 
 */
public final class BancosDeDados {

	/**
	 * Representa o banco de dados da Aplicação.
	 */
	@Produces
	@AplicacaoDB
	@PersistenceContext(unitName = "bradesco-seguro-pu")
	EntityManager aplicacaoDB;
}