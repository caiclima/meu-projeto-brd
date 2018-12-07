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
import br.com.callink.bradesco.seguro.entity.Cargo;
import br.com.callink.bradesco.seguro.service.ICargoService;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.task.sincronizadores.ISincronizadorCargos;

@Stateless
@Local(ISincronizadorCargos.class)
public class SincronizadorCargo implements ISincronizadorCargos<Cargo> {
	
	private final Logger logger = Logger.getLogger(SincronizadorCargo.class);
	
	@EJB
	private ICargoService<Cargo> cargoService;
	
	private ICorporativoDAO corporativoDAO;

	@SuppressWarnings("unchecked")
	public void sincronizar()  {
		
		logger.info("Sincronizando cargos...");
		List<Cargo> cargos = null;
		
		try {
			corporativoDAO = CorporativoConnection.getConnection();
			cargos = corporativoDAO.buscarCargos();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao buscar cargos na base do Corporativo. Erro: " + e.getMessage(), e);
		}
	

		if (!CollectionUtils.isEmpty(cargos)) {

			ServiceResponse serviceResponse = null;
			
			try {
				serviceResponse = cargoService.findAll();
			} catch (Exception e) {
				throw new RuntimeException("Erro ao buscar cargos na base do Bradesco Seguros. Erro: " + e.getMessage(), e);
			}
			
			List<Cargo> cargosExistentes = (List<Cargo>) serviceResponse.getData();
			Cargo cargoUpdate = null;

			for (Cargo cargo : cargos) {
				
				cargo.setDatAtualizacao( new Date());

				try {
					
					for (Cargo cargoExistente : cargosExistentes) {
						if (cargoExistente.getIdCargoCorporativo().equals(cargo.getIdCargoCorporativo())) {
							cargoUpdate = cargoExistente;
							break;
						}
					}

					if (cargoUpdate != null) {
						cargosExistentes.remove(cargoUpdate);
						cargoUpdate.setAtivo(cargo.getAtivo());
						cargoUpdate.setNomeCargo(cargo.getNomeCargo());
						cargoUpdate.setDatAtualizacao(cargo.getDatAtualizacao());
						cargoService.update(cargoUpdate);
						cargoUpdate = null;
					} else {
						cargoService.save(cargo);
					}
					
				} catch (Exception e) {
					logger.error("Erro ao processar cargo atual: " + cargo + ". Erro: " + e.getMessage(), e);
				}
			}
		} else {
			logger.info("Nenhum cargo encontrado!");
		}
	}
}
