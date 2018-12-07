package br.com.callink.bradesco.seguro.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.callink.bradesco.seguro.commons.utils.ClearCacheCommand;
import br.com.callink.bradesco.seguro.commons.utils.CollectionUtils;
import br.com.callink.bradesco.seguro.commons.utils.InvokeServletUtils;
import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.dao.ITipoPlanoDAO;
import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.ParametroSistema;
import br.com.callink.bradesco.seguro.entity.Plano;
import br.com.callink.bradesco.seguro.entity.TipoPlano;
import br.com.callink.bradesco.seguro.service.IParametroSistemaService;
import br.com.callink.bradesco.seguro.service.ITipoPlanoService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponseFactory;

/**
 * Representa a Camada de Serviço para o Domínio 'Tipo Plano'
 * 
 * @author swb.alisson
 * 
 */
@Stateless
@Local(ITipoPlanoService.class)
public class TipoPlanoService extends GenericCrudServiceImpl<TipoPlano> implements ITipoPlanoService<TipoPlano> {

	private final Logger logger = Logger.getLogger(TipoPlanoService.class);
	
	@Inject
	private ITipoPlanoDAO dao;
	
	@EJB
	private IParametroSistemaService<ParametroSistema> parametroSistemaService;

	@Override
	protected ITipoPlanoDAO getDAO() {
		return dao;
	}

	@Override
	public ServiceResponse buscarTodosTiposPlano() throws ServiceException {
		List<TipoPlano> all = getDAO().buscarTodosTiposPlano();
		ServiceResponse response = ServiceResponseFactory.createWithData(all);

		if (CollectionUtils.isEmpty(all)) {
			return response.addWarning("Nenhum registro encontrado!");
		}

		return response;
	}

	@Override
	public ServiceResponse buscaTipoPlanoPorId(BigInteger idTipoPlano) throws ServiceException {

		TipoPlano tipoPlano = getDAO().buscaTipoPlanoPorId(idTipoPlano);

		ServiceResponse response = ServiceResponseFactory.createWithData(tipoPlano);

		if (tipoPlano == null) {
			return response.addWarning("Nenhum registro encontrado!");
		}

		return response;
	}

	@Override
	public ServiceResponse findByExample(TipoPlano entity) throws ServiceException {
		List<TipoPlano> all = getDAO().findByExample(entity);
		ServiceResponse response = ServiceResponseFactory.createWithData(all);

		if (CollectionUtils.isEmpty(all)) {
			return response.addWarning("Nenhum registro encontrado!");
		}

		return response;
	}

	@Override
	protected void antesAtualizar(TipoPlano tipoPlano) throws ServiceException {
		_validar(tipoPlano);
		tipoPlano.setNomeTipoPlano(tipoPlano.getNomeTipoPlano().trim());

		if (tipoPlano.getId() != null) {
			TipoPlano previous = getDAO().getFromPersistenceContext(TipoPlano.class, tipoPlano.getId());
			if (previous != null) {
				String nomeTipoPlano = tipoPlano.getNomeTipoPlano().trim();
				if (!previous.getNomeTipoPlano().trim().equalsIgnoreCase(nomeTipoPlano)) {
					
					boolean jaExiste = (getDAO().existeTipoPlanoComNome(tipoPlano.getNomeTipoPlano()) > 0) ? true : false;
					if (jaExiste) {
						throw new ServiceException("Já existe Tipo Plano com o nome [" + nomeTipoPlano + "]. Outro nome deve ser informado.");
					}
				}
			}
		}
	}

	@Override
	protected void antesSalvar(TipoPlano tipoPlano) throws ServiceException {
		_validar(tipoPlano);
		String nome = tipoPlano.getNomeTipoPlano().trim();
		tipoPlano.setNomeTipoPlano(nome);

		boolean jaExiste = (getDAO().existeTipoPlanoComNome(tipoPlano.getNomeTipoPlano()) > 0) ? true : false;
		if (jaExiste) {
			throw new ServiceException("Já existe Tipo Plano com o nome [" + nome + "]. Outro nome deve ser informado.");
		}
		
	}
	
	@Override
	public ServiceResponse save(TipoPlano tipoPlano,String usuarioHost, String usuarioLogado) throws ServiceException {

		if (tipoPlano == null || StringUtils.isEmpty(tipoPlano.getNomeTipoPlano())) {
			throw new ServiceException("Digite o nome do tipo de plano!");
		}
		
		TipoPlano tipoPlanoExample = new TipoPlano();
		tipoPlanoExample.setNomeTipoPlano(tipoPlano.getNomeTipoPlano());
		List<TipoPlano> planos = getDAO().findByExampleExact(tipoPlanoExample);
		
		if (planos != null && planos.size() > 0) {
			throw new ServiceException("Já existe um tipo de plano com este nome!");
		}
		
		setLogPojo(tipoPlano, usuarioHost, usuarioLogado);
		tipoPlano.setLogTransaction(BigInteger.valueOf(System.currentTimeMillis()));
		
		ServiceResponse serviceResponse = new ServiceResponse();
		getDAO().save(tipoPlano);
		aposSalvar(tipoPlano);

		serviceResponse = ServiceResponseFactory.createWithData(tipoPlano);
		serviceResponse.addInfo("Registro salvo com sucesso.");
		
		return serviceResponse;
	}
	
	@Override
	public ServiceResponse update(TipoPlano tipoPlano,String usuarioHost,String usuarioLogado) throws ServiceException {

		if (tipoPlano == null || StringUtils.isEmpty(tipoPlano.getNomeTipoPlano())) {
			throw new ServiceException("Digite o nome do tipo de plano!");
		}
			
		setLogPojo(tipoPlano,usuarioHost,usuarioLogado);
		tipoPlano.setLogTransaction(BigInteger.valueOf(System.currentTimeMillis()));
		
		ServiceResponse serviceResponse = new ServiceResponse();
		getDAO().update(tipoPlano);
		aposAtualizar(tipoPlano);
		
		serviceResponse = ServiceResponseFactory.createWithData(tipoPlano);
		serviceResponse.addInfo("Registro atualizado com sucesso.");
		
		return serviceResponse;
	}

	@Override
	protected void antesExcluir(TipoPlano tipoPlano) throws ServiceException {
		if (tipoPlano == null) {
			throw new ServiceException("[Tipo Plano] não informado para exclusão.");
		}

		if (tipoPlano.getId() == null) {
			throw new ServiceException("[Id do Tipo de Plano] não informado para exclusão.");
		}

		boolean planos = getDAO().existeTipoPlanoVinculadoPlano(tipoPlano.getId());
		if (planos) {
			throw new ServiceException("O Tipo de Plano não pode ser excluído pois existem planos vinculados ao mesmo.");
		}
		
	}
	
	@Override
	protected void aposSalvar(TipoPlano entity) throws ServiceException {
		clearCacheTipoPlano();
	}
	
	@Override
	protected void aposAtualizar(TipoPlano entity) throws ServiceException {
		clearCacheTipoPlano();
	}
	
	@Override
	protected void aposExcluir(TipoPlano entity) throws ServiceException {
		clearCacheTipoPlano();
	}

	private void _validar(TipoPlano tipoPlano) throws ServiceException {
		if (tipoPlano == null) {
			throw new ServiceException("[Tipo Plano] não informado.");
		}

		if (StringUtils.isEmpty(tipoPlano.getNomeTipoPlano().trim())) {
			throw new ServiceException("[Nome do Tipo de Plano] não informado.");
		}

		if (tipoPlano.getFlagEnabled() == null) {
			throw new ServiceException("Não informado se [flag Tipo de Plano] está ativo ou não.");
		}
	}
	
	@Override
	public ServiceResponse findByExampleOrderByNome(TipoPlano entity) throws ServiceException {
		List<TipoPlano> all = getDAO().findByExampleOrderByNome(entity);
		ServiceResponse response = ServiceResponseFactory.createWithData(all);

		if (CollectionUtils.isEmpty(all)) {
			return response.addWarning("Nenhum registro encontrado!");
		}

		return response;
	}
	
	@Override
	public List<TipoPlano> findByPlanoList(List<Plano> planoList) throws ServiceException, ValidationException {
		List<BigInteger> pkList = new ArrayList<BigInteger>();
		try {
			for(Plano plano: planoList){
				if(pkList.contains(plano.getTipoPlano().getId())){
					continue;
				} else {
					pkList.add(plano.getTipoPlano().getId());
				}
			}
			if(pkList.isEmpty()){
				throw new ValidationException("Não há planos disponíveis");
			}
			return getDAO().findAllFromPkList(pkList);
		} catch (DataException e) {
			throw new ServiceException(e);
		}
	}
	
	private void clearCacheTipoPlano() {
		try {
			final String urlCacheServ = parametroSistemaService.buscarPorNome(ParametroSistema.URL_CACHE_SERVLET).getValorParametroSistema();
			final String usuarioCacheServ = parametroSistemaService.buscarPorNome(ParametroSistema.USUARIO_ACESSO_CACHE_SERVLET).getValorParametroSistema();
			final String senhaCacheServ = parametroSistemaService.buscarPorNome(ParametroSistema.SENHA_ACESSO_CACHE_SERVLET).getValorParametroSistema();
			
			final String urlString = urlCacheServ + ClearCacheCommand.TIPO_PLANO.getQueryString();
			
			InvokeServletUtils.invokeServlet(urlString, usuarioCacheServ, senhaCacheServ);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}