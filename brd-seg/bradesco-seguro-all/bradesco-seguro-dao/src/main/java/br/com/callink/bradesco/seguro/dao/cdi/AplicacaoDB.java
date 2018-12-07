package br.com.callink.bradesco.seguro.dao.cdi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;
import javax.persistence.EntityManager;

/**
 * Representa o Banco de Dados ({@link EntityManager} ou Data Source) da Aplicação.
 * 
 * @author michael
 *
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE})
public @interface AplicacaoDB {
}
