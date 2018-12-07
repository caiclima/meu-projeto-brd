package br.com.callink.bradesco.task.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

import br.com.callink.bradesco.seguro.entity.LoteMailing;
import br.com.callink.bradesco.seguro.entity.NuvemVendasRegistroHeader;
import br.com.callink.bradesco.seguro.entity.NuvemVendasRegistroHeaderDhi;
import br.com.callink.bradesco.seguro.entity.NuvemVendasRegistroHeaderPplus;
import br.com.callink.bradesco.seguro.entity.Venda;
import br.com.callink.bradesco.seguro.service.ILoteMailingService;
import br.com.callink.bradesco.seguro.service.INuvemVendaRegistroHeaderDhiService;
import br.com.callink.bradesco.seguro.service.INuvemVendaRegistroHeaderPPlusService;
import br.com.callink.bradesco.seguro.service.INuvemVendaRegistroHeaderService;
import br.com.callink.bradesco.seguro.service.IVendaService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

@Singleton
public class FinalizaMailingTask {

	private static final Logger LOGGER = Logger.getLogger(FinalizaMailingTask.class.getName());
	
	@EJB
	private ILoteMailingService loteMailingService;
	
	@EJB
	private INuvemVendaRegistroHeaderService nuvemVendaRegistroHeaderService;
	
	@EJB
	private INuvemVendaRegistroHeaderPPlusService nuvemVendaRegistroHeaderPPlusService;
	
	@EJB
	private INuvemVendaRegistroHeaderDhiService nuvemVendaRegistroHeaderDhiService;
	
	@EJB
	private IVendaService<Venda> vendaService;
	
	//@Schedule(second = "0", minute = "0", hour = "22", info = "Task para gerar Numvem de Venda", persistent=false)
	@Schedule(minute = "*/3", hour = "*", info = "Task para gerar Numvem de Venda", persistent=false)
	public void executeVenda() throws Exception {

		LOGGER.info("Task GeranNuvemVendasTask iniciada");
		
		try {
			
			Date dataEnvio = new Date();
			
			try {
				NuvemVendasRegistroHeader headerPessoal = nuvemVendaRegistroHeaderService.geraNuvemVendaRegistroHeader(dataEnvio);
				
				if(headerPessoal != null && headerPessoal.getId() != null) { 
					
					BigInteger idHeaderPessoal = new BigInteger(headerPessoal.getId().toString());
					
					loteMailingService.geraNuvemVendaRegistroDocumento(idHeaderPessoal, dataEnvio);
					
					loteMailingService.geraNuvemVendaRegistroCadastroSegurados(idHeaderPessoal, dataEnvio);
					
					loteMailingService.geraNuvemVendaRegistroCobrancaSegurados(idHeaderPessoal, dataEnvio);
				}
			} catch (ServiceException e) {
				LOGGER.log(Level.SEVERE, e.getMessage());
			}

			
			try {
				
				NuvemVendasRegistroHeaderPplus headerPlus = nuvemVendaRegistroHeaderPPlusService.geraNuvemVendaRegistroHeaderPPlus(dataEnvio);
				
				if(headerPlus != null && headerPlus.getId() != null) {
					
					BigInteger idHeaderPlus = new BigInteger(headerPlus.getId().toString());
					
					loteMailingService.geraNuvemVendaRegistroDocumentoPPlus(idHeaderPlus, dataEnvio);
					
					loteMailingService.geraNuvemVendaRegistroCadastroSeguradosPPlus(idHeaderPlus, dataEnvio);
					
					loteMailingService.geraNuvemVendaRegistroCobrancaSeguradosPPlus(idHeaderPlus, dataEnvio);
				}
				
				
			} catch (ServiceException e) {
				LOGGER.log(Level.SEVERE, e.getMessage());
			}
			
			try {
				NuvemVendasRegistroHeaderDhi headerDhi = nuvemVendaRegistroHeaderDhiService.geraNuvemVendaRegistroHeaderDhi(dataEnvio);
				
				if(headerDhi != null && headerDhi.getId() != null) { 
					
					BigInteger idHeaderDhi = new BigInteger(headerDhi.getId().toString());
					
					loteMailingService.geraNuvemVendaRegistroDocumentoDhi(idHeaderDhi, dataEnvio);
					
					loteMailingService.geraNuvemVendaRegistroCadastroSeguradosDhi(idHeaderDhi, dataEnvio);
					
					loteMailingService.geraNuvemVendaRegistroCobrancaSeguradosDhi(idHeaderDhi, dataEnvio);
				}
			} catch (ServiceException e) {
				LOGGER.log(Level.SEVERE, e.getMessage());
			}
			
			loteMailingService.geraNuvemVendaArquivoRetorno(dataEnvio);
			
			vendaService.updateDataEnvioNuvem(dataEnvio);

		} catch (ServiceException ex) {
			throw new Exception(ex);
		}
		
		LOGGER.info("GeranNuvemVendasTask Encerrada");
		
	}
	
	@SuppressWarnings("unchecked")
	//@Schedule(second = "0", minute = "0", hour = "6", info = "Task para finalizar Mailing", persistent=false)
	@Schedule(minute = "*/2", hour = "*", info = "Task para gerar Numvem de Venda", persistent=false)
	public void executeFinalizacaoMailing() throws Exception {
		LOGGER.info("Preparar inicio FinalizaMailingTask");
		
		List<LoteMailing> listMailingFinalizados = new ArrayList<LoteMailing>();
			
		LOGGER.info("Task GeraNuvemFinalizaMailingTask iniciada");
		try {
			ServiceResponse serviceResponse = loteMailingService.findLoteMailingFinalizacaoMailing();
			listMailingFinalizados.addAll((List<LoteMailing>) serviceResponse.getData());
			for(LoteMailing loteMailing : listMailingFinalizados) {
				if(loteMailing.getFlagExportadoTipoRegistroHeader() == Boolean.FALSE){
					
					loteMailingService.geraNuvemTipoRegistroHeader(loteMailing);
				}
				if(loteMailing.getFlagExportadoTipoRegistroStatus() == Boolean.FALSE){
					
					loteMailingService.geraNuvemTipoRegistroStatus(loteMailing);
				}
        	}
		} catch (ServiceException ex) {
			throw new Exception(ex);
		}
		
		LOGGER.info("GeraNuvemFinalizaMailingTask Encerrada");
	}
}
