package br.com.callink.bradesco.seguro.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.LoteMailing;

/**
 * 
 * @author swb_mateus
 *
 */
public interface ILoteMailingDAO extends IGenericDAO<LoteMailing>{

	/**
	 * retorna alista de loteMailing passando os parrametros da pesquisa.
	 * 
	 * @param pojo
	 * @param ateDataImportacao
	 * @param ateDataInicioMailing
	 * @param ateDataFimMailing
	 * @return
	 */
	List<LoteMailing> findLoteMailingByDateAndPojo(LoteMailing pojo, Date ateDataImportacao, Date ateDataInicioMailing, Date ateDataFimMailing) throws DataException;

	/**
	 * finaliza o mailing passado na pesquisa e retorna a quantidade de registros alterados.
	 * @param pojo
	 * @return
	 * @throws DataException
	 */
	Integer finalizaLotMailing(LoteMailing pojo) throws DataException;

	/**
	 * Retorna todos os mailings finalizados
	 * @return
	 * @throws DataException
	 */
	List<LoteMailing> findLoteMailingFinalizados() throws DataException;

	void insertNuvemTipoRegistroHeader(LoteMailing loteMailing) throws DataException;

	void insertNuvemTipoRegistroStatus(LoteMailing loteMailing) throws DataException;

//	BigInteger insertNuvemVendaRegistroHeader(Date dataGeracao) throws DataException;

	void insertNuvemVendaRegistroDocumento(BigInteger idHeader, Date dataEnvio) throws DataException;

	void insertNuvemVendaCadastroSegurados(BigInteger idHeader, Date dataEnvio) throws DataException;

	void insertNuvemVendaCobrancaSegurados(BigInteger idHeader, Date dataEnvio) throws DataException;
	
//	BigInteger insertNuvemVendaRegistroHeaderPPlus(Date dataGeracao) throws DataException;
	
	void insertNuvemVendaRegistroDocumentoPPlus(BigInteger idHeader, Date dataEnvio) throws DataException;
	
	void insertNuvemVendaCadastroSeguradosPPlus(BigInteger idHeader, Date dataEnvio) throws DataException;
	
	void insertNuvemVendaCobrancaSeguradosPPlus(BigInteger idHeader, Date dataEnvio) throws DataException;
	
	void insertNuvemVendaArquivoRetorno(Date dataEnvio) throws DataException;

	List<LoteMailing> findLoteMailingFinalizacaoMailing() throws DataException;
	
	List<LoteMailing> findLoteMailingFinalizadosVendas() throws DataException;

	List<LoteMailing> findLoteMailingFinalizadosArquivoRetorno() throws DataException;

	void insertNuvemVendaRegistroDocumentoDhi(BigInteger idHeader, Date dataEnvio) throws DataException;

	void insertNuvemVendaCadastroSeguradosDhi(BigInteger idHeader, Date dataEnvio) throws DataException;

	void insertNuvemVendaCobrancaSeguradosDhi(BigInteger idHeader, Date dataEnvio) throws DataException;


}
