package br.com.callink.bradesco.seguro.service.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import br.com.callink.bradesco.seguro.commons.utils.CollectionUtils;
import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.dao.IHistoricoContatoDAO;
import br.com.callink.bradesco.seguro.dto.EventoContatoDTO;
import br.com.callink.bradesco.seguro.entity.HistoricoContato;
import br.com.callink.bradesco.seguro.entity.LoteMailing;
import br.com.callink.bradesco.seguro.service.IHistoricoContatoService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponseFactory;

@Stateless
@Local(IHistoricoContatoService.class)
public class HistoricoContatoService extends GenericCrudServiceImpl<HistoricoContato> implements IHistoricoContatoService<HistoricoContato> {

	@Inject
	private IHistoricoContatoDAO historicoContatoDAO;
	
	@Override
	protected IHistoricoContatoDAO getDAO() {
		return historicoContatoDAO;
	}
	

	@Override
	public List<EventoContatoDTO> buscarHistoricoPorPeriodoDataContato(Date dataInicial, Date dataFinal) throws ServiceException {
		if (dataInicial == null) {
			throw new ServiceException("Data Inicial deve ser informada para consulta de Eventos de Contato ao Cliente.");
		}

		if (dataFinal == null) {
			throw new ServiceException("Data Final deve ser informada para consulta de Eventos de Contato ao Cliente.");
		}

		return getDAO().buscarHistoricoPorPeriodoDataContato(dataInicial, dataFinal);
	}
	

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public int atualizarFlagContatoParaEnviado(Set<BigInteger> idsHistoricoContato) throws ServiceException {
		if(CollectionUtils.isEmpty(idsHistoricoContato)){
			throw new ServiceException("IDs de Históricos de Contato não informados!");
		}
		
		return getDAO().atualizarFlagContatoEnviado(true, idsHistoricoContato);
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public int atualizarFlagContatoParaEnviado(BigInteger idHistoricoContato) throws ServiceException {
		if(idHistoricoContato == null ){
			throw new ServiceException("IDs de Históricos de Contato não informados!");
		}
		
		return getDAO().atualizarFlagContatoEnviado(true, idHistoricoContato);
	}
	
	@Override
	public List<HistoricoContato> buscaHistoricoByIdClienteCampanha(BigInteger idClienteCampanha) throws ServiceException {
		
		return getDAO().buscaHistoricoByIdClienteCampanha(idClienteCampanha);
	}
	
	@Override
	public ServiceResponse salvar(HistoricoContato historicoContato, String usuarioHost, String usuarioLogado)
			throws ServiceException {

		antesSalvar(historicoContato);
		setLogPojo(historicoContato, usuarioHost, usuarioLogado);
		historicoContato.setLogTransaction(BigInteger.valueOf(System.currentTimeMillis()));
		return this.save(historicoContato);
	}
	
	@Override
	public ServiceResponse atualizar(HistoricoContato historicoContato, String usuarioHost, String usuarioLogado)
			throws ServiceException {

		antesAtualizar(historicoContato);
		setLogPojo(historicoContato, usuarioHost, usuarioLogado);
		historicoContato.setLogTransaction(BigInteger.valueOf(System.currentTimeMillis()));
		return this.update(historicoContato);
	}
	
	@Override
	protected void antesSalvar(HistoricoContato historicoContato) throws ServiceException {
		validar(historicoContato);
	}
	
	@Override
	protected void antesAtualizar(HistoricoContato historicoContato) throws ServiceException {
		if(historicoContato.getDataContato() != null && historicoContato.getDataFimContato() != null) {
			historicoContato.setDuracaoSegundosContato(BigInteger.valueOf(historicoContato.getDataFimContato().getTime() - historicoContato.getDataContato().getTime()));
		} 
		else {
			historicoContato.setDuracaoSegundosContato(BigInteger.ZERO);
		}
		
		validar(historicoContato);
	}
	
	private void validar(HistoricoContato historicoContato) throws ServiceException {

		if (StringUtils.isEmpty(historicoContato.getChaveAtendimento())){
			throw new ServiceException("Informe a chave de atendimento.");
		}
	}


	//	@Override
//	public void salvaHistoricoContato(Long idEvento, Long IdClienteCampanha, Long IdUsuario, Date dataContato, Date dataFimContato, BigInteger idTelefone, String logHost, String logObs, String logSystem, String logUid) throws ServiceException {
//		HistoricoContato historicoContato = new HistoricoContato();
	@Override
	public void salvaHistoricoContato(Long idEvento, Long IdClienteCampanha, Date dataContato, Date dataFimContato, BigInteger idTelefone, String logHost, String logObs, String logSystem, String logUid) throws ServiceException {
		HistoricoContato historicoContato = new HistoricoContato();
		
		historicoContato.setIdClienteCampanha(BigInteger.valueOf(IdClienteCampanha.longValue()));
		historicoContato.setIdEvento(BigInteger.valueOf(idEvento.longValue()));
		historicoContato.setDataContato(dataContato != null ? dataContato : new Date());
		historicoContato.setFlagEnabled(Boolean.TRUE);
		historicoContato.setDataFimContato(dataFimContato != null ? dataFimContato : null);
		if(dataContato != null && dataFimContato != null) {
			historicoContato.setDuracaoSegundosContato(BigInteger.valueOf(dataFimContato.getTime() - dataContato.getTime()));
		} 
		else {
			historicoContato.setDuracaoSegundosContato(BigInteger.ZERO);
		}
		
		setLogPojo(historicoContato, logHost, logUid);
		historicoContato.setLogTransaction(BigInteger.valueOf(System.currentTimeMillis()));
		
		super.save(historicoContato);
	}

	
	protected void validarSave(LoteMailing object) throws ValidationException {
		
	}

	protected void validarUpdate(LoteMailing object) throws ValidationException {
		
	}

	protected void validarDelete(LoteMailing object) throws ValidationException {
		
	}


	@Override
	public int atualizarFlagContatoParaNaoEnviado() {
		return getDAO().atualizarFlagContatoParaNaoEnviado();
	}


	@Override
	public HistoricoContato findByCallId(BigInteger callId, BigInteger idClienteCampanha, String logUid) throws ServiceException {
		return getDAO().findByCallId(callId, idClienteCampanha, logUid);
	}
	
	@Override
	public List<HistoricoContato> buscaPorCnpj(String cnpj) throws ServiceException {
		return getDAO().buscaPorCnpj(cnpj);
	}


	@Override
	public ServiceResponse buscarPorChaveAtendimento(String chaveAtendimento)
			throws ServiceException {
		
		HistoricoContato historicoContato = getDAO().buscarPorChaveAtendimento(chaveAtendimento);
		ServiceResponse serviceResponse = ServiceResponseFactory.createWithData(historicoContato);
		return serviceResponse;
	}



}
