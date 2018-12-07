package br.com.callink.bradesco.seguro.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.callink.bradesco.seguro.commons.utils.CollectionUtils;
import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.dao.ILoteMailingDAO;
import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.Campanha;
import br.com.callink.bradesco.seguro.entity.LoteMailing;
import br.com.callink.bradesco.seguro.entity.TipoEvento;
import br.com.callink.bradesco.seguro.service.ICampanhaService;
import br.com.callink.bradesco.seguro.service.IClienteCampanhaService;
import br.com.callink.bradesco.seguro.service.ILoteMailingService;
import br.com.callink.bradesco.seguro.service.IParametroSistemaService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponseFactory;

@Stateless
public class LoteMailingService extends GenericCrudServiceImpl<LoteMailing> implements ILoteMailingService {
	
	private static final String ID_EVENTO_FINALIZADOR_LOTE_MAILING = "ID_EVENTO_FINALIZADOR_LOTE_MAILING";
	private static final Logger LOG = Logger.getLogger(LoteMailingService.class);
	
	@Inject
	private ILoteMailingDAO loteMailingDAO;
	
	@EJB
	private ICampanhaService campanhaService;
	
	@EJB
	private IParametroSistemaService<TipoEvento> parametroSistemaService;
	
	@EJB
	private IClienteCampanhaService clienteCampanhaService;
	
	@Override
	protected ILoteMailingDAO getDAO() {
		return loteMailingDAO;
	}
	
	@Override
	public List<LoteMailing> findLoteMailingByDateAndPojo(LoteMailing pojo, Date ateDataImportacao, Date ateDataInicioMailing, Date ateDataFimMailing) throws ValidationException, ServiceException {
		try {
			validaCampos(pojo, ateDataImportacao, ateDataInicioMailing, ateDataFimMailing);
			List<LoteMailing> listLotmailing = loteMailingDAO.findLoteMailingByDateAndPojo(pojo, ateDataImportacao, ateDataInicioMailing, ateDataFimMailing);
			for(LoteMailing loteMailing : listLotmailing) {
				loteMailing.setCampanha((Campanha) campanhaService.findById(loteMailing.getIdCampanha()).getData());
			}
			return listLotmailing;
		} catch (DataException ex) {
			throw new ServiceException(ex);
		}
	}
	
	private void validaCampos(LoteMailing object, Date ateDataImportacao, Date ateDataInicioMailing, Date ateDataFimMailing) throws ValidationException {
		if(object == null) {
			throw new ValidationException("Lote Mailing está nulo.");
		}
		else {
			List<String> erros = new ArrayList<String>();
			if(object.getDataImportacao() == null && ateDataImportacao != null) {
				erros.add("Selecine uma data inicial de importação de Mailing para o filtro.");
			}
			if(object.getDataInicioMailing() == null && ateDataInicioMailing != null) {
				erros.add("Selecine uma data inicial de Inicio de Lote Mailing para o filtro.");
			}
			if(object.getDataFimMailing() == null && ateDataFimMailing != null) {
				erros.add("Selecine uma data inicial de fim de Lote Mailing para o filtro.");
			}
			if(!erros.isEmpty()) {
				throw new ValidationException(erros);
			}
		}
	}
	
	@Override
	public Integer finalizaLotMailing(LoteMailing pojo) throws ServiceException {
		try {
			int quantidadeAlterada = getDAO().finalizaLotMailing(pojo);
			String idEventoFinalizadorMailing = parametroSistemaService.buscarPorNome(ID_EVENTO_FINALIZADOR_LOTE_MAILING).getValorParametroSistema();
			
			//se tiver algum status finalizador de mailing no parametro de sistemas todos os cliente Campanhas não trabalhados serão finalizados com o status.
			if(!StringUtils.isEmpty(idEventoFinalizadorMailing)) {
				try {
					clienteCampanhaService.finalizaClientesLoteMailing(Long.valueOf(idEventoFinalizadorMailing), pojo.getId(), pojo.getLogHost(), pojo.getLogObs(), pojo.getLogSystem(), pojo.getLogUid());
				} catch (NumberFormatException ex) {
					LOG.error(ex.getMessage(), ex);
				} catch (ValidationException ex) {
					LOG.error(ex.getMessage(), ex);
				}
			}
			return quantidadeAlterada;
		} catch (DataException ex) {
			throw new ServiceException(ex);
		}
	}
	
	protected void validarSave(LoteMailing object) throws ValidationException {
		
	}

	protected void validarUpdate(LoteMailing object) throws ValidationException {
		
	}

	protected void validarDelete(LoteMailing object) throws ValidationException {
		
	}

	public LoteMailing findByPk(Integer pk) throws ServiceException{
		try {
			return getDAO().findByPK(pk);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}
	
	@Override
	public void antesSalvar(LoteMailing loteMailing) {
	}

	@Override
	public void finalizaMailing(LoteMailing loteMailing, String usuarioHost, String usuarioLogado) throws ServiceException {
		try {
			antesSalvar(loteMailing);
			loteMailing.setLogTransaction(BigInteger.valueOf(System.currentTimeMillis()));
			setLogPojo(loteMailing, usuarioHost, usuarioLogado);
			loteMailing.setFlagMailingFinalizado(Boolean.TRUE);
			super.update(loteMailing);
		} catch (DataException ex) {
			throw new ServiceException(ex);
		}
	}

	@Override
	public ServiceResponse findLoteMailingFinalizados() throws ServiceException {
		try {
			List<LoteMailing> mailings = getDAO().findLoteMailingFinalizados();
			
			ServiceResponse serviceReponse = ServiceResponseFactory
					.createWithData(mailings);

			if (CollectionUtils.isEmpty(mailings)) {
				serviceReponse.addWarning("Nenhum registro encontrado.");
			}
			return serviceReponse;
		} catch(DataException ex) {
			throw new ServiceException(ex);
		}
	}
	
	@Override
	public ServiceResponse findLoteMailingFinalizacaoMailing()
			throws ServiceException {
		try {
			List<LoteMailing> mailings = getDAO().findLoteMailingFinalizacaoMailing();
			
			ServiceResponse serviceReponse = ServiceResponseFactory
					.createWithData(mailings);

			if (CollectionUtils.isEmpty(mailings)) {
				serviceReponse.addWarning("Nenhum registro encontrado.");
			}
			return serviceReponse;
		} catch(DataException ex) {
			throw new ServiceException(ex);
		}
	}

	@Override
	public ServiceResponse findLoteMailingFinalizadosVendas()
			throws ServiceException {
		try {
			List<LoteMailing> mailings = getDAO().findLoteMailingFinalizadosVendas();
			
			ServiceResponse serviceReponse = ServiceResponseFactory
					.createWithData(mailings);

			if (CollectionUtils.isEmpty(mailings)) {
				serviceReponse.addWarning("Nenhum registro encontrado.");
			}
			return serviceReponse;
		} catch(DataException ex) {
			throw new ServiceException(ex);
		}
	}

	@Override
	public ServiceResponse findLoteMailingFinalizadosArquivoRetorno()
			throws ServiceException {
		try {
			List<LoteMailing> mailings = getDAO().findLoteMailingFinalizadosArquivoRetorno();
			
			ServiceResponse serviceReponse = ServiceResponseFactory
					.createWithData(mailings);

			if (CollectionUtils.isEmpty(mailings)) {
				serviceReponse.addWarning("Nenhum registro encontrado.");
			}
			return serviceReponse;
		} catch(DataException ex) {
			throw new ServiceException(ex);
		}
	}

	@Override
	public void geraNuvemTipoRegistroHeader(LoteMailing loteMailing) throws ServiceException {
		try {
			if(loteMailing.getFlagMailingFinalizado() == null || loteMailing.getFlagMailingFinalizado() == Boolean.FALSE) {
				throw new ServiceException("Não é possível gerar Nuvem de Dados para mailing que não foi finalizado");
			}
			getDAO().insertNuvemTipoRegistroHeader(loteMailing);
			finalizaNuvemTipoRegistroHeader(loteMailing);

		} catch(DataException ex) {
			throw new ServiceException(ex);
		}
	}
	
	@Override
	public void finalizaNuvemTipoRegistroHeader(LoteMailing loteMailing)
			throws ServiceException {
		
		try {
			loteMailing.setFlagExportadoTipoRegistroHeader(Boolean.TRUE);
			super.update(loteMailing);
			
		} catch(DataException ex) {
			throw new ServiceException(ex);
		}
	}

	@Override
	public void geraNuvemTipoRegistroStatus(LoteMailing loteMailing)
			throws ServiceException {
		try {
			if(loteMailing.getFlagMailingFinalizado() == null || loteMailing.getFlagMailingFinalizado() == Boolean.FALSE) {
				throw new ServiceException("Não é possível gerar Nuvem de Dados para mailing que não foi finalizado");
			}
			getDAO().insertNuvemTipoRegistroStatus(loteMailing);
			finalizaNuvemTipoRegistroStatus(loteMailing);

		} catch(DataException ex) {
			throw new ServiceException(ex);
		}
	}
	
	@Override
	public void finalizaNuvemTipoRegistroStatus(LoteMailing loteMailing) throws ServiceException {
		
		try {
			loteMailing.setFlagExportadoTipoRegistroStatus(Boolean.TRUE);
			super.update(loteMailing);
			
		} catch(DataException ex) {
			throw new ServiceException(ex);
		}
	}

//	@Override
//	public BigInteger geraNuvemVendaRegistroHeader(Date dataEnvio) throws ServiceException {
//		try {
//			return getDAO().insertNuvemVendaRegistroHeader(dataEnvio);
//		} catch(DataException ex) {
//			throw new ServiceException(ex);
//		}
//	}

	@Override
	public void geraNuvemVendaRegistroDocumento(BigInteger idHeader, Date dataEnvio) throws ServiceException {
		try {
			getDAO().insertNuvemVendaRegistroDocumento(idHeader, dataEnvio);
		} catch(DataException ex) {
			throw new ServiceException(ex);
		}
	}
	

	@Override
	public void geraNuvemVendaRegistroCadastroSegurados(BigInteger idHeader, Date dataEnvio) throws ServiceException {
		try {
			getDAO().insertNuvemVendaCadastroSegurados(idHeader, dataEnvio);
		} catch(DataException ex) {
			throw new ServiceException(ex);
		}
	}
	

	@Override
	public void geraNuvemVendaRegistroCobrancaSegurados(BigInteger idHeader, Date dataEnvio) throws ServiceException {
		try {
			getDAO().insertNuvemVendaCobrancaSegurados(idHeader, dataEnvio);
		} catch(DataException ex) {
			throw new ServiceException(ex);
		}
		
	}
	
//	@Override
//	public BigInteger geraNuvemVendaRegistroHeaderPPlus(Date dataEnvio) throws ServiceException {
//		try {
//			return getDAO().insertNuvemVendaRegistroHeaderPPlus(dataEnvio);
//		} catch(DataException ex) {
//			throw new ServiceException(ex);
//		}
//	}
	
	
	@Override
	public void geraNuvemVendaRegistroDocumentoPPlus(BigInteger idHeader, Date dataEnvio)
			throws ServiceException {
		try {
			getDAO().insertNuvemVendaRegistroDocumentoPPlus(idHeader, dataEnvio);
		} catch(DataException ex) {
			throw new ServiceException(ex);
		}
	}
	
	
	@Override
	public void geraNuvemVendaRegistroCadastroSeguradosPPlus(BigInteger idHeader, Date dataEnvio) throws ServiceException {
		try {
			getDAO().insertNuvemVendaCadastroSeguradosPPlus(idHeader, dataEnvio);
		} catch(DataException ex) {
			throw new ServiceException(ex);
		}
	}
	
	@Override
	public void geraNuvemVendaRegistroCobrancaSeguradosPPlus(BigInteger idHeader, Date dataEnvio) throws ServiceException {
		try {
			getDAO().insertNuvemVendaCobrancaSeguradosPPlus(idHeader, dataEnvio);
		} catch(DataException ex) {
			throw new ServiceException(ex);
		}
		
	}

	@Override
	public void geraNuvemVendaArquivoRetorno(Date dataEnvio) throws ServiceException{
		
		try {
			getDAO().insertNuvemVendaArquivoRetorno(dataEnvio);
		} catch(DataException ex) {
			throw new ServiceException(ex);
		}
	}
	
	@Override
	public void geraNuvemVendaRegistroDocumentoDhi(BigInteger idHeader, Date dataEnvio) throws ServiceException {
		try {
			getDAO().insertNuvemVendaRegistroDocumentoDhi(idHeader, dataEnvio);
		} catch(DataException ex) {
			throw new ServiceException(ex);
		}
	}
	
	@Override
	public void geraNuvemVendaRegistroCadastroSeguradosDhi(BigInteger idHeader, Date dataEnvio) throws ServiceException {
		try {
			getDAO().insertNuvemVendaCadastroSeguradosDhi(idHeader, dataEnvio);
		} catch(DataException ex) {
			throw new ServiceException(ex);
		}
	}
	
	@Override
	public void geraNuvemVendaRegistroCobrancaSeguradosDhi(BigInteger idHeader, Date dataEnvio) throws ServiceException {
		try {
			getDAO().insertNuvemVendaCobrancaSeguradosDhi(idHeader, dataEnvio);
		} catch(DataException ex) {
			throw new ServiceException(ex);
		}
		
	}


}
