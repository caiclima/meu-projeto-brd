package br.com.callink.bradesco.seguro.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import br.com.callink.bradesco.seguro.commons.utils.CollectionUtils;
import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.dao.IClienteCampanhaDAO;
import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.dto.ClienteCampanhaDTO;
import br.com.callink.bradesco.seguro.dto.ClienteIndicacaoDTO;
import br.com.callink.bradesco.seguro.entity.Agente;
import br.com.callink.bradesco.seguro.entity.Campanha;
import br.com.callink.bradesco.seguro.entity.ClienteCampanha;
import br.com.callink.bradesco.seguro.entity.ContatoMailing;
import br.com.callink.bradesco.seguro.entity.HistoricoContato;
import br.com.callink.bradesco.seguro.entity.LoteMailing;
import br.com.callink.bradesco.seguro.entity.ParametroSistema;
import br.com.callink.bradesco.seguro.entity.TelefoneCliente;
import br.com.callink.bradesco.seguro.enums.ParametroSistemaEnum;
import br.com.callink.bradesco.seguro.service.IAgenteService;
import br.com.callink.bradesco.seguro.service.IClienteCampanhaService;
import br.com.callink.bradesco.seguro.service.IContatoMailingService;
import br.com.callink.bradesco.seguro.service.IHistoricoContatoService;
import br.com.callink.bradesco.seguro.service.ILoteMailingService;
import br.com.callink.bradesco.seguro.service.IParametroSistemaService;
import br.com.callink.bradesco.seguro.service.ITelefoneClienteService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;
import br.com.callink.bradesco.seguro.service.utils.BundleHelper;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponseFactory;

@Stateless
public class ClienteCampanhaService extends GenericCrudServiceImpl<ClienteCampanha> implements IClienteCampanhaService {

	private static String ERRO_CONSULTA;

	@Inject
	private IClienteCampanhaDAO clienteCampanhaDAO;

	@EJB
	private IContatoMailingService contatoMailingService;

	@EJB
	private ITelefoneClienteService telefoneClienteService;

	@EJB
	private IHistoricoContatoService<HistoricoContato> historicoContatoService;

	@EJB
	private IParametroSistemaService<ParametroSistema> parametroSistemaService;

	@EJB
	private ILoteMailingService loteMailingService;
	
	@EJB
	private IAgenteService agenteService;

	@Override
	protected IClienteCampanhaDAO getDAO() {
		return clienteCampanhaDAO;
	}

	public ClienteCampanhaService() throws ServiceException {

		try {
			ClienteCampanhaService.ERRO_CONSULTA = BundleHelper
					.getMessageWithParam("gerror_problemasConsulta",
							MSG_BUNDLE, new Object[] { "Cliente Campanha" });
		} catch (Exception e) {

			ClienteCampanhaService.ERRO_CONSULTA = "Problemas ao carregar Cliente Campanha";
		}
	}

	public ClienteCampanha buscarClienteCampanha(BigInteger idClienteCampanha)
			throws ServiceException, ValidationException {

		try {

			if (idClienteCampanha == null) {
				throw new ValidationException(
						ClienteCampanhaService.ERRO_CONSULTA);
			}

			ClienteCampanha clienteCampanha = getDAO().buscarClienteCampanha(
					idClienteCampanha);
			return clienteCampanha;

		} catch (DataException e) {
			throw new ServiceException(e);
		} catch (ValidationException e) {
			throw new ValidationException(ClienteCampanhaService.ERRO_CONSULTA);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public ClienteCampanha findById(BigInteger idClienteCampanha)
			throws ServiceException, ValidationException {
		try {
			if (idClienteCampanha == null) {
				throw new ValidationException(
						ClienteCampanhaService.ERRO_CONSULTA);
			}

			ClienteCampanha clienteCampanha = getDAO().findByPK(
					idClienteCampanha);
			return clienteCampanha;
		} catch (DataException e) {
			throw new ServiceException(e);
		} catch (ValidationException e) {
			throw new ValidationException(ClienteCampanhaService.ERRO_CONSULTA);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public ClienteCampanha findById(ClienteCampanha cliente)
			throws ServiceException, ValidationException {
		List<String> erros = validaCamposFind(cliente);
		if (!erros.isEmpty()) {
			throw new ValidationException(erros);
		}

		return findById(cliente.getId());

	}

	protected void validarSave(ClienteCampanha object)
			throws ValidationException {
		List<String> erros = validaCamposFind(object);
		if (!erros.isEmpty()) {
			throw new ValidationException(erros);
		}
	}

	protected void validarUpdate(ClienteCampanha object)
			throws ValidationException {
		List<String> erros = validaCamposFind(object);
		// if(object.getIdUsuario() == null) {
		// erros.add("Usuário associado ao cliente não foi encontrado.");
		// }
		if (!erros.isEmpty()) {
			throw new ValidationException(erros);
		}
	}

	protected void validarDelete(ClienteCampanha object)
			throws ValidationException {
		List<String> erros = validaCamposFind(object);
		if (!erros.isEmpty()) {
			throw new ValidationException(erros);
		}
	}

	private List<String> validaCamposFind(ClienteCampanha object) {
		List<String> erros = new ArrayList<String>();
		if (object == null) {
			erros.add("Cliente inexistente!");
		} else {
			if (object.getId() == null) {
				erros.add("O campo ID é obrigatório!");
			}
		}
		return erros;
	}

	@Override
	public void finalizaClientesLoteMailing(Long idEvento,
			BigInteger idLoteMailing, String logHost, String logObs,
			String logSystem, String logUid) throws ServiceException,
			ValidationException {
		List<ClienteCampanha> list = findClienteCampVirgemByidLoteMailing(
				idLoteMailing, false);
		for (ClienteCampanha clienteCampanha : list) {
			for (TelefoneCliente telefone : clienteCampanha
					.getTelefoneClienteList()) {
				// historicoContatoService.salvaHistoricoContato(idEvento,
				// clienteCampanha.getId() != null ?
				// clienteCampanha.getId().longValue() : null,
				// clienteCampanha.getIdUsuario() != null ?
				// clienteCampanha.getIdUsuario().longValue() : null,
				// clienteCampanha.getDataContato(),
				// clienteCampanha.getDataFimContato(), telefone.getId(),
				// logHost, logObs, logSystem, logUid);
				historicoContatoService.salvaHistoricoContato(idEvento,
						clienteCampanha.getId() != null ? clienteCampanha
								.getId().longValue() : null, clienteCampanha
								.getDataContato(), clienteCampanha
								.getDataFimContato(), telefone.getId(),
						logHost, logObs, logSystem, logUid);
			}
		}
	}

	@Override
	public List<ClienteCampanha> findClienteCampByIdFinalizacaoAndidLoteMailing(
			Long idEvento, Integer idLoteMailing) throws ServiceException {
		try {
			return getDAO().findClienteCampByIdEventoAndidLoteMailing(idEvento,
					idLoteMailing);
		} catch (DataException ex) {
			throw new ServiceException(ex);
		}
	}

	@Override
	public List<ClienteCampanha> findClienteCampVirgemByidLoteMailing(
			BigInteger idLoteMailing, boolean flagGateway) throws ServiceException {
		try {
			return getDAO().findClienteCampVirgemByidLoteMailing(idLoteMailing,
					flagGateway);
		} catch (DataException ex) {
			throw new ServiceException(ex);
		}
	}

	@Override
	public ServiceResponse consultarClienteCampanha(String nome, String cnpj,
			String usuario) throws ServiceException {
		if (StringUtils.isEmpty(nome) && StringUtils.isEmpty(cnpj)) {
			throw new ServiceException(
					"Pelo menos o Nome ou CNPJ deve ser informado para efetuar a consulta!");
		}

		if (StringUtils.isEmpty(usuario)) {
			throw new ServiceException("Usuário é obrigatório.");
		}

		ParametroSistema param = parametroSistemaService
				.buscarPorNome(ParametroSistemaEnum.PARAMETRO_CODIGO_DOMINIOS_SINCRONIZACAO_USUARIOS_COORPORATIVO
						.value());

		List<ClienteCampanhaDTO> result = getDAO().consultarClienteCampanha(
				nome, cnpj, usuario,
				Short.valueOf(param.getValorParametroSistema()));
		ServiceResponse response = ServiceResponseFactory
				.createWithData(result);

		if (CollectionUtils.isEmpty(result)) {
			response.addWarning("Nenhum registro encontrado!");
		}

		return response;
	}

	@Override
	public ServiceResponse consultarClienteAtendimento(String nome,
			String cnpj, String usuario) throws ServiceException {
		if (StringUtils.isEmpty(nome) && StringUtils.isEmpty(cnpj)) {
			throw new ServiceException(
					"Pelo menos o Nome ou CNPJ deve ser informado para efetuar a consulta!");
		}

		if (StringUtils.isEmpty(usuario)) {
			throw new ServiceException("Usuário é obrigatório.");
		}

		if (cnpj != null) {
			cnpj = cnpj.replace(".", "").replace("-", "").replace("/", "");
		}

		ParametroSistema param = parametroSistemaService
				.buscarPorNome(ParametroSistemaEnum.PARAMETRO_CODIGO_DOMINIOS_SINCRONIZACAO_USUARIOS_COORPORATIVO
						.value());

		List<ClienteCampanhaDTO> result = getDAO().consultarClienteAtendimento(
				nome, cnpj, usuario,
				Short.valueOf(param.getValorParametroSistema()));
		ServiceResponse response = ServiceResponseFactory
				.createWithData(result);

		if (CollectionUtils.isEmpty(result)) {
			response.addWarning("Nenhum registro encontrado!");
		}

		return response;
	}

	@Override
	public ServiceResponse consultarClienteAtendimentoReceptivo(String nome,
			String cnpj, String usuario) throws ServiceException {
		if (StringUtils.isEmpty(nome) && StringUtils.isEmpty(cnpj)) {
			throw new ServiceException(
					"Pelo menos o Nome ou CNPJ deve ser informado para efetuar a consulta!");
		}

		if (StringUtils.isEmpty(usuario)) {
			throw new ServiceException("Usuário é obrigatório.");
		}

		if (cnpj != null) {
			cnpj = cnpj.replace(".", "").replace("-", "").replace("/", "");
		}

		ParametroSistema param = parametroSistemaService
				.buscarPorNome(ParametroSistemaEnum.PARAMETRO_CODIGO_DOMINIOS_SINCRONIZACAO_USUARIOS_COORPORATIVO
						.value());

		List<ClienteCampanhaDTO> result = getDAO()
				.consultarClienteAtendimentoReceptivo(nome, cnpj, usuario,
						Short.valueOf(param.getValorParametroSistema()));
		ServiceResponse response = ServiceResponseFactory
				.createWithData(result);

		if (CollectionUtils.isEmpty(result)) {
			response.addWarning("Nenhum registro encontrado!");
		}

		return response;
	}

	public ServiceResponse findContatoMailingByCnpjs(String cnpjs)
			throws ServiceException {

		ServiceResponse response;

		try {

			if (StringUtils.isEmpty(cnpjs)) {
				throw new ServiceException("Lista de CNPJs vazia!");
			}

			String[] cnpjArray = cnpjs.split("\\n");

			Boolean formatoCnpj = Boolean.TRUE;

			List<String> cnpjList = new ArrayList<String>();

			for (String cnpj : cnpjArray) {
				if (cnpj.length() != 14) {
					formatoCnpj = Boolean.FALSE;
					break;
				}
			}

			for (String cnpj : cnpjArray) {
				cnpjList.add(cnpj);
			}

			if (!formatoCnpj) {
				throw new ServiceException("Cnpj inválido.");
			}

			List<ClienteCampanha> clienteCampanhaList = getDAO()
					.findContatoMailingByCnpjs(cnpjList);

			response = ServiceResponseFactory
					.createWithData(clienteCampanhaList);

		} catch (DataException e) {
			throw new ServiceException(e);
		}

		return response;
	}

	private ServiceResponse findClienteCampanhasInCampanhaAtivaBycnpjs(
			String cnpjs) throws ServiceException {

		ServiceResponse response;

		try {

			if (StringUtils.isEmpty(cnpjs)) {
				throw new ServiceException("Lista de CNPJs vazia!");
			}

			String[] cnpjArray = cnpjs.split("\\n");

			Boolean formatoCnpj = Boolean.TRUE;

			List<String> cnpjList = new ArrayList<String>();

			for (String cnpj : cnpjArray) {
				if (cnpj.length() != 14) {
					formatoCnpj = Boolean.FALSE;
					break;
				}
			}

			for (String cnpj : cnpjArray) {
				cnpjList.add(cnpj);
			}

			if (!formatoCnpj) {
				throw new ServiceException("Cnpj inválido.");
			}

			// List<ClienteCampanha> clienteCampanhaList =
			// getDAO().findContatoMailingByCnpjs(cnpjList);

			List<ClienteCampanha> clienteCampanhaList = getDAO()
					.findClienteCampanhasInCampanhaAtivaBycnpjs(cnpjList);

			response = ServiceResponseFactory
					.createWithData(clienteCampanhaList);

		} catch (DataException e) {
			throw new ServiceException(e);
		}

		return response;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void salvarGrupoObjetos(ClienteCampanha clienteCampanha)
			throws ServiceException {

		ContatoMailing contatoMailing = clienteCampanha.getContatoMailing();
		LoteMailing loteMailing = contatoMailing.getLoteMailing();

		loteMailingService.save(loteMailing);

		contatoMailing.setIdLoteMailing(loteMailing.getId());

		contatoMailingService.save(contatoMailing);

		clienteCampanha.setIdContatoMailing(contatoMailing.getId());

		Set<TelefoneCliente> telefoneClienteList = clienteCampanha
				.getTelefoneClienteList();
		clienteCampanha.setTelefoneClienteList(null);

		save(clienteCampanha);

		for (TelefoneCliente telefoneCliente : telefoneClienteList) {
			telefoneCliente.setIdClienteCampanha(clienteCampanha.getId());
			telefoneClienteService.save(telefoneCliente);
		}
	}

	@SuppressWarnings("unchecked")
	public List<ClienteIndicacaoDTO> loadClientesParaIndicacao(String cnpjs)
			throws ServiceException {
		List<ClienteIndicacaoDTO> result = new ArrayList<ClienteIndicacaoDTO>();

		Map<String, ClienteCampanha> clientes = new HashMap<String, ClienteCampanha>();
		String[] aux = cnpjs.split("\n");
		StringBuilder nCnpjs = new StringBuilder();
		for (String c : aux) {
			if (c != null && c.length() == 14 && c.matches("[\\d]+")) {
				clientes.put(c, null);
				if (nCnpjs.length() > 0) {
					nCnpjs.append("\n");
				}
				nCnpjs.append(c);
			}
		}

		List<ClienteCampanha> clientesBase = (List<ClienteCampanha>) findContatoMailingByCnpjs(
				nCnpjs.toString()).getData();

		for (ClienteCampanha cliCam : clientesBase) {
			if (cliCam != null
					&& (clientes.get(cliCam.getContatoMailing().getCpf()) == null || clientes
							.get(cliCam.getContatoMailing().getCpf()).getId()
							.compareTo(cliCam.getId()) == -1)) {
				clientes.put(cliCam.getContatoMailing().getCpf(), cliCam);
			}

		}

		for (String cnpj : clientes.keySet()) {
			ClienteIndicacaoDTO cm = new ClienteIndicacaoDTO();
			cm.setCnpj(cnpj);
			cm.setClienteCampanha(clientes.get(cnpj));
			result.add(cm);
		}

		return result.size() > 0 ? result : null;
	}

	public List<ClienteIndicacaoDTO> buscarClientesParaIndicacao(String cnpjs,
			Campanha campanhaIndicacao) throws ServiceException {
		try {
			Map<String, ClienteIndicacaoDTO> clientes = new HashMap<String, ClienteIndicacaoDTO>();
			StringBuilder nCnpjs = new StringBuilder();
			if (cnpjs != null && cnpjs.length() >= 14) {
				String[] aux = cnpjs.split("\n");
				for (String c : aux) {
					if (c != null && c.length() == 14 && c.matches("[\\d]+")) {
						ClienteIndicacaoDTO cdto = new ClienteIndicacaoDTO(c);
						cdto.setCampanha(campanhaIndicacao);
						clientes.put(c, cdto);
						if (nCnpjs.length() > 0) {
							nCnpjs.append("\n");
						}
						nCnpjs.append(c);
					}
				}
			} else {
				throw new ServiceException("Nenhum CNPJ valido!");
			}

			List<ClienteCampanha> clientesNaBase = getDAO()
					.retornaClientesExistentes(
							new ArrayList<>(clientes.keySet()));
			for (ClienteCampanha cc : clientesNaBase) {
				// if(clientes.get(cc.getContatoMailing().getCnpj()) == null
				// ||
				// clientes.get(cc.getContatoMailing().getCnpj()).isIndicavel()){
				if (clientes.get(cc.getContatoMailing().getCpf()) == null) {

					ClienteIndicacaoDTO cdto = new ClienteIndicacaoDTO(cc
							.getContatoMailing().getCpf());
					cdto.setClienteCampanha(cc);
					cdto.setCampanha(campanhaIndicacao);
					clientes.put(cc.getContatoMailing().getCpf(), cdto);
				}

			}

			return new ArrayList<>(clientes.values());

		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public ClienteCampanha buscarClienteCampanhaPorIdEChaveAtendimento(
			BigInteger idClienteCampanha, String chaveAtendimento, String usuarioHost, String usuarioLogado, String telefone)
			throws ServiceException {

		if (idClienteCampanha == null || idClienteCampanha.compareTo(BigInteger.ZERO) == 0) {
			throw new ServiceException("Informe o id do Cliente.");
		}
		
		ClienteCampanha clienteCampanha = clienteCampanhaDAO.buscarClienteCampanhaPorId(idClienteCampanha);
		
		if (clienteCampanha == null){
			throw new ServiceException("Nenhum Cliente com o id " +  idClienteCampanha + " encontrado.");
		} else if (!CollectionUtils.isEmpty(clienteCampanha.getVendaList()) && clienteCampanha.getVendaList().size() > 1 ) {
			throw new ServiceException("Inconsistência na base de dados: Cliente campanha deve possui apenas uma Venda.");
		} else if (!StringUtils.isEmpty(chaveAtendimento)){
			ServiceResponse serviceResponseHistoricoContato = historicoContatoService.buscarPorChaveAtendimento(chaveAtendimento);
			
			Agente agente = findAgente(usuarioLogado);
			
			if(agente == null){
				agente = salvarAgente(usuarioLogado);
			}
			
			if (serviceResponseHistoricoContato.getData() == null) {
				
				HistoricoContato historicoContato = new HistoricoContato();
				historicoContato.setIdClienteCampanha(clienteCampanha.getId());
				historicoContato.setIdEvento(clienteCampanha.getIdEvento());
				historicoContato.setDataContato(new Date());
				historicoContato.setFlagEnabled(Boolean.TRUE);
				historicoContato.setChaveAtendimento(chaveAtendimento);
				
				for (TelefoneCliente telefoneCliente : clienteCampanha.getTelefoneClienteList()) {
					if ((telefoneCliente.getDdd()+telefoneCliente.getTelefone()).equalsIgnoreCase(telefone)) {
						historicoContato.setIdTelefoneCliente(telefoneCliente.getId());
						break;
					}
				}
				
				historicoContato.setAgente(agente);
				
				ServiceResponse serviceResponse  = historicoContatoService.salvar(historicoContato, usuarioHost, usuarioLogado);
				
				if (serviceResponse.getData() != null){
					List<HistoricoContato> historicoContatoList= new ArrayList<HistoricoContato>();
					historicoContatoList.add((HistoricoContato) serviceResponse.getData());
					clienteCampanha.setHistoricoContatoList(historicoContatoList);
				}
			} else {
				HistoricoContato historicoContato = (HistoricoContato) serviceResponseHistoricoContato.getData();
				historicoContato.setAgente(agente);
				List<HistoricoContato> historicoContatoList= new ArrayList<HistoricoContato>();
				historicoContatoList.add(historicoContato);
				clienteCampanha.setHistoricoContatoList(historicoContatoList);
			}
		}
		return clienteCampanha;
	}

	private Agente salvarAgente(String usuarioLogado) throws ServiceException{
		Agente agente = new Agente();
		agente.setNomeAgente(usuarioLogado);
		
		return agenteService.salvarAgente(agente);
	}

	private Agente findAgente(String usuarioLogado) throws ServiceException{
		return agenteService.buscarAgente(usuarioLogado);
	}
}
