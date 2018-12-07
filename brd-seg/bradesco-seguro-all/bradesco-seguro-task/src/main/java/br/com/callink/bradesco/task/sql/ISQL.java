package br.com.callink.bradesco.task.sql;

/**
 * Representa um fragmento SQL (nativo)
 * 
 * @author michael
 * 
 */
public interface ISQL {
	String getSQL(Object... args);
}
