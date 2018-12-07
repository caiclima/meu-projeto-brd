package br.com.callink.bradesco.seguro.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import br.com.callink.bradesco.seguro.entity.LoteMailing;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

public interface ILoteMailingService extends IGenericCrudService<LoteMailing> {

	/**
	 * retorna alista de loteMailing passando os parrametros da pesquisa
	 * @param pojo
	 * @param ateDataImportacao
	 * @param ateDataInicioMailing
	 * @param ateDataFimMailing
	 * @return
	 * @throws ServiceException
	 */
	List<LoteMailing> findLoteMailingByDateAndPojo(LoteMailing pojo, Date ateDataImportacao, Date ateDataInicioMailing, Date ateDataFimMailing) throws ValidationException, ServiceException;

	/**
	 * finaliza o LoteMailing.
	 * @param pojo
	 * @return
	 * @throws ServiceException
	 */
	Integer finalizaLotMailing(LoteMailing pojo) throws ServiceException;
	
	/**
	 * Busca o loteMailing tendo como parametro a PK
	 * @param pk
	 * @return
	 * @throws ServiceException
	 */
	LoteMailing findByPk(Integer pk) throws ServiceException;

	/**
	 * Finaliza mailing definindo flagFinalizado = true | Há uma task observa este campo para gerar nuvem de dados
	 * @param loteMailing
	 * @param usuarioHost
	 * @param usuarioLogado
	 * @throws ServiceException
	 */
	void finalizaMailing(LoteMailing loteMailing, String usuarioHost, String usuarioLogado) throws ServiceException;
	
	/**
	 * Busca todos os mailings finalizados
	 * @return
	 * @throws ServiceException
	 */
	ServiceResponse findLoteMailingFinalizados() throws ServiceException;
	
	ServiceResponse findLoteMailingFinalizacaoMailing() throws ServiceException;
	
	ServiceResponse findLoteMailingFinalizadosVendas() throws ServiceException;
	
	ServiceResponse findLoteMailingFinalizadosArquivoRetorno() throws ServiceException;
	
	void geraNuvemTipoRegistroHeader(LoteMailing loteMailing) throws ServiceException;

	void geraNuvemTipoRegistroStatus(LoteMailing loteMailing)  throws ServiceException;

//	BigInteger geraNuvemVendaRegistroHeader(Date dataEnvio) throws ServiceException;

	void geraNuvemVendaRegistroDocumento(BigInteger idHeader, Date dataEnvio) throws ServiceException;

	void geraNuvemVendaRegistroCadastroSegurados(BigInteger idHeader, Date dataEnvio) throws ServiceException;

	void geraNuvemVendaRegistroCobrancaSegurados(BigInteger idHeader, Date dataEnvio) throws ServiceException;
	
//	BigInteger geraNuvemVendaRegistroHeaderPPlus(Date dataEnvio) throws ServiceException;
	
	void geraNuvemVendaRegistroDocumentoPPlus(BigInteger idHeader, Date dataEnvio) throws ServiceException;
	
	void geraNuvemVendaRegistroCadastroSeguradosPPlus(BigInteger idHeader, Date dataEnvio) throws ServiceException;
	
	void geraNuvemVendaRegistroCobrancaSeguradosPPlus(BigInteger idHeader, Date dataEnvio) throws ServiceException;

	void geraNuvemVendaArquivoRetorno(Date dataEnvio) throws ServiceException;

	void finalizaNuvemTipoRegistroHeader(LoteMailing loteMailing)
			throws ServiceException;
	
	void finalizaNuvemTipoRegistroStatus(LoteMailing loteMailing)
			throws ServiceException;
	
	void geraNuvemVendaRegistroDocumentoDhi(BigInteger idHeader, Date dataEnvio) throws ServiceException;

	void geraNuvemVendaRegistroCadastroSeguradosDhi(BigInteger idHeaderDhi, Date dataEnvio) throws ServiceException;

	void geraNuvemVendaRegistroCobrancaSeguradosDhi(BigInteger idHeaderDhi, Date dataEnvio) throws ServiceException;

}
