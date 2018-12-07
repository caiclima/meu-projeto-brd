package br.com.callink.bradesco.seguro.remote.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.callink.bradesco.seguro.dao.impl.CacheDAO;
import br.com.callink.bradesco.seguro.service.ICacheService;

@Stateless
@Local(ICacheService.class)
public class CacheService implements ICacheService {

	@Inject
	private CacheDAO cacheDAO;
	
	@Override
	public void clearCacheParametroSistema() {
		cacheDAO.clearCacheRegion("parametroSistemaCacheRegion");
		
		final Logger logger = Logger.getLogger(CacheService.class);
		logger.info("SUCSSES: clear cache ParametroSistema");
	}

	@Override
	public void clearCacheEvento(){
		cacheDAO.clearCacheRegion("eventoCacheRegion");
		
		final Logger logger = Logger.getLogger(CacheService.class);
		logger.info("SUCSSES: clear cache Evento");
	}
	
	@Override
	public void clearCacheTipoEvento(){
		cacheDAO.clearCacheRegion("tipoEventoCacheRegion");
		
		final Logger logger = Logger.getLogger(CacheService.class);
		logger.info("SUCSSES: clear cache Tipo Evento");
	}

	@Override
	public void clearCacheGrauPrentesco() {
		cacheDAO.clearCacheRegion("grauParentescoCacheRegion");
		
		final Logger logger = Logger.getLogger(CacheService.class);
		logger.info("SUCSSES: clear cache GrauParentesco");
	}

	@Override
	public void clearCacheEstadoCivil() {
		cacheDAO.clearCacheRegion("estadoCivilCacheRegion");
		
		final Logger logger = Logger.getLogger(CacheService.class);
		logger.info("SUCSSES: clear cache EstadoCivil");
	}

	@Override
	public void clearCacheEsporte() {
		cacheDAO.clearCacheRegion("esporteCacheRegion");
		
		final Logger logger = Logger.getLogger(CacheService.class);
		logger.info("SUCSSES: clear cache Esporte");
	}

	@Override
	public void clearCacheProduto() {
		cacheDAO.clearCacheRegion("produtoCacheRegion");
		
		final Logger logger = Logger.getLogger(CacheService.class);
		logger.info("SUCSSES: clear cache Produto");
	}
	
	@Override
	public void clearCachePlano() {
		cacheDAO.clearCacheRegion("planoCacheRegion");
		
		final Logger logger = Logger.getLogger(CacheService.class);
		logger.info("SUCSSES: clear cache Plano");
	}
	
	@Override
	public void clearCacheProfissao() {
		cacheDAO.clearCacheRegion("profissaoCacheRegion");
		
		final Logger logger = Logger.getLogger(CacheService.class);
		logger.info("SUCSSES: clear cache Profissao");
	}
	
	@Override
	public void clearCacheTipoPlano() {
		cacheDAO.clearCacheRegion("tipoPlanoCacheRegion");
		final Logger logger = Logger.getLogger(CacheService.class);
		logger.info("SUCSSES: clear cache Tipo Plano");
	}
	
}
