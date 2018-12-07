package br.com.callink.bradesco.seguro.dao.impl;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import org.hibernate.Cache;
import org.hibernate.Session;
import br.com.callink.bradesco.seguro.dao.cdi.AplicacaoDB;


public class CacheDAO {
	private EntityManager entityManager;
	
	protected EntityManager getEntityManager() {
		return entityManager;
	}
	
	@Inject
	public void setEntityManager(@AplicacaoDB EntityManager entityManager) {
		this.entityManager = entityManager;
		this.entityManager.setFlushMode(FlushModeType.COMMIT); // flush at commit time by default
	}
	
	private Session session(){
		return getEntityManager().unwrap(Session.class);
	}
	
	private Cache getSecondLevelCache(){
		return session().getSessionFactory().getCache();
	}

	public void clearCacheEntity(Class<?> clazz){
		getSecondLevelCache().evictEntityRegion(clazz);
	}
	
	public void clearCacheRegion(String region) {
		getSecondLevelCache().evictQueryRegion(region);
	}
}
