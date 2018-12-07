package br.com.callink.bradesco.seguro.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.Query;

import org.hibernate.Cache;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.callink.bradesco.seguro.dao.IGenericDAO;
import br.com.callink.bradesco.seguro.dao.cdi.AplicacaoDB;
import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.metadata.IRemovableEntity;
import br.com.callink.bradesco.seguro.entity.metadata.IdentifiableEntity;

/**
 * Implementacao generica de DAO baseada no Hibernate.
 * 
 * @author michael
 * 
 * @param <T> - entity type
 */
@SuppressWarnings("unchecked")
public class GenericHibernateDAOImpl<T extends IdentifiableEntity<?>> implements IGenericDAO<T> {
	private EntityManager entityManager;
	
	 private final Class<T> entityType;

     public GenericHibernateDAOImpl()  {
    	 entityType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
     }
	
	public Criteria createCriteria(){
		return session().createCriteria(entityType);
	}
	
	/**
	 * Verifica se o objeto é do tipo IRemovableEntity e aplica
	 * restrição flagRemoved = false
	 * @param o
	 * @return
	 */
	public Criteria createCriteria(T o){ 
		return o instanceof IRemovableEntity ?
				createCriteriaWithFlagRemoved(Boolean.FALSE) :
					createCriteria();
	}
	
	/**
	 * Cria objeto Criteria com restrição padrão para a propriedade flagRemoved
	 * @param removableEntity implements IRemovableEntity
	 * @param flagRemoved
	 * @return
	 */
	public Criteria createCriteriaWithFlagRemoved(Boolean flagRemoved){
		Criteria criteria = session().createCriteria(entityType);
		criteria.add(Restrictions.eq("flagRemoved", flagRemoved));
		return criteria;
	}
	
	/**
	 * Retorna o cache de segundo nivel
	 * 
	 * @return
	 */
	public Cache getSecondLevelCache(){
		return session().getSessionFactory().getCache();
	}

	@Override
	public void clearCacheEntity(){
		getSecondLevelCache().evictEntityRegion(entityType);
	}
	
	@Override
	public void clearCacheRegion(String region) {
		getSecondLevelCache().evictQueryRegion(region);
	}

	@Override
	public List<T> findAll(){
		return createCriteria().list();
	}
	
	@Override
	public List<T> findEnabledAndNotRemoved(){
		return createCriteria()
				.add(Restrictions.eq("flagEnabled",  Boolean.TRUE))
				.add(Restrictions.eq("flagRemoved",  Boolean.FALSE))
				.list();
	}
	
	/**
	 * Obtém objeto no cache de primeiro nível do Hibernate.
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	public <O> O getFromPersistenceContext(Class<O> clazz, Serializable id){
		O reference = (O) session().get(clazz, id);
		session().evict(reference);
		return reference;
	}

	@Override
	public T save(T object){
		session().save(object);
		return object;
	}

	@Override
	public void update(T object){
		session().update(object);
	}

	@Override
	public T saveOrUpdate(T object){
		session().saveOrUpdate(object);
		return object;
	}

	@Override
	public void delete(T object){
		session().delete(session().merge(object));
	}

	@Override
	public T findByPK(Object pk){
		Field[] fields = entityType.getDeclaredFields();
		String fieldName = null;
		for(Field f : fields){
			if(f.getAnnotation(javax.persistence.Id.class) != null){
				fieldName = f.getName();
				break;
			}else if(f.getAnnotation(javax.persistence.EmbeddedId.class) != null){
				fieldName = f.getName();
				break;
			}
		}
		
		if(fieldName == null){
			throw new DataException("A property annotated with @javax.persistence.Id or javax.persistence.EmbeddedId must exists.");
		}
		return (T) createCriteria().add(Restrictions.eq(fieldName, pk)).uniqueResult();
	}
	
	@Override
	public List<T> findByExample(T object){
		return createCriteria(object)
				.add(Example.create(object)
				.enableLike(MatchMode.ANYWHERE))		
				.list();
	}
	
	/**
	 * Retorna a sessão corrente {@link Session} vinculada ao {@link EntityManager}.
	 * @return {@link Session}
	 */
	protected Session session(){
		return getEntityManager().unwrap(Session.class);
	}

	protected EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Configura {@link EntityManager} utilizado na camada de persistencia.
	 * 
	 * Embora o entity manager default da aplicacao seja injetado, caso exista a necessidade de injetar
	 * outro EntityManager em alguma subclasse (DAO) especifico, isto pode ser feito de forma transparente
	 * apenas efetuando overriding deste metodo na subclasse e qualificando o novo EntityManager como parametro
	 * de injecao.
	 * 
	 * @param entityManager
	 */
	@Inject
	public void setEntityManager(@AplicacaoDB EntityManager entityManager) {
		this.entityManager = entityManager;
		this.entityManager.setFlushMode(FlushModeType.COMMIT); // flush at commit time by default
	}
	
	protected void callStoredProcedure(String spName, List<Object> params){
		StringBuilder paramsValues = new StringBuilder();
		
		for(int i=0; i<params.size(); i++){
			paramsValues.append(", ?");
		}
		
		String sql = "exec " + spName + paramsValues.toString().replaceFirst(",", "");
		
		Query q = getEntityManager().createNativeQuery(sql);
		
		for(int i=0; i<params.size(); i++){
			q.setParameter(i+1,params.get(i));
		}
		
		q.executeUpdate();
	}
}
