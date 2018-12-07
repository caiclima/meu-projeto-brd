package br.com.callink.bradesco.task.sincronizadores.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import br.com.callink.bradesco.dao.ICorporativoDAO;
import br.com.callink.bradesco.dao.utils.CorporativoConnection;
import br.com.callink.bradesco.seguro.commons.utils.CollectionUtils;
import br.com.callink.bradesco.seguro.entity.Dominio;
import br.com.callink.bradesco.seguro.service.IDominioService;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.task.sincronizadores.ISincronizadorDominios;

@Stateless
@Local(ISincronizadorDominios.class)
public class SincronizadorDominio implements ISincronizadorDominios<Dominio> {
	
	private final Logger logger = Logger.getLogger(SincronizadorDominio.class);
	
	@EJB
	private IDominioService<Dominio> dominioService;
	
	private ICorporativoDAO corporativoDAO;

	@SuppressWarnings("unchecked")
	public void sincronizar()  {
		logger.info("Sincronizando dominios...");
		
		List<Dominio> dominios;
		
		try {
			corporativoDAO = CorporativoConnection.getConnection();
			dominios = corporativoDAO.buscarDominios();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao buscar dominios na base do Corporativo. Erro: " + e.getMessage(), e);
		}
	

		if (!CollectionUtils.isEmpty(dominios)) {

			ServiceResponse serviceResponse = null;
			
			try {
				serviceResponse = dominioService.findAll();
			} catch (Exception e) {
				throw new RuntimeException("Erro ao buscar dominios na base do Bradesco Seguros. Erro: " + e.getMessage(), e);
			}
			
			List<Dominio> dominiosExistentes = (List<Dominio>) serviceResponse.getData();
			Dominio dominioUpdate = null;

			for (Dominio dominio : dominios) {
				
				dominio.setDataAtualizacao( new Date());

				try {
					
					for (Dominio dominioExistente : dominiosExistentes) {
						if (dominioExistente.getIdDominioCorporativo().equals(dominio.getIdDominioCorporativo())) {
							dominioUpdate = dominioExistente;
							break;
						}
					}

					if (dominioUpdate != null) {
						dominiosExistentes.remove(dominioUpdate);
						dominioUpdate.setAtivo(dominio.getAtivo());
						dominioUpdate.setDescricaoDominio(dominio.getDescricaoDominio());
						dominioUpdate.setObsDominio(dominio.getObsDominio());
						dominioUpdate.setDataAtualizacao(dominio.getDataAtualizacao());
						dominioService.update(dominioUpdate);
						dominioUpdate = null;;
					} else {
						dominioService.save(dominio);
					}
					
				} catch (Exception e) {
					logger.error("Erro ao processar dominio atual: " + dominio + ". Erro: " + e.getMessage(), e);
				}
			}
		} else {
			logger.info("Nenhum dominio encontrado!");
		}
	}
}
