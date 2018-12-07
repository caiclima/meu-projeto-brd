package br.com.callink.bradesco.seguro.web.servlet;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import br.com.callink.bradesco.seguro.entity.ClienteCampanha;
import br.com.callink.bradesco.seguro.entity.TelefoneCliente;
import br.com.callink.bradesco.seguro.service.IClienteCampanhaService;
import br.com.callink.bradesco.seguro.service.ITelefoneClienteService;

/**
 * Servlet responsavel pela requisicao de busca dos numeros de telefone de um
 * cliente. Este servlet atende apenas uma única requisição e espera um único
 * parametro: idClienteCampanha. Encontrado o cliente com id informado no
 * parametro, o sistema retorna os numeros de telefone com DDD, todos separados
 * por ";".
 * 
 * @author denis
 * 
 */
@WebServlet(loadOnStartup = 1, name = "GetTelefonesClienteServlet", urlPatterns = { "/getTelefonesCliente" })
public class GetTelefonesClienteServlet extends HttpServlet {

	private static final long serialVersionUID = -3322521764450050698L;
	protected Logger logger = Logger.getLogger(getClass());
	
	@Inject
	private IClienteCampanhaService clienteCampanhaService;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

	/**
	 * Processa a requisição.
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// Recupera o idClienteCampanha da requisicao (parametro
			// OBRIGATORIO)
			BigInteger idClienteCampanha = BigInteger.valueOf(Long.parseLong(req.getParameter("idClienteCampanha")));

			// Instancia o pojo para a busca
			ClienteCampanha cliente = new ClienteCampanha(idClienteCampanha);

			// Instancia o service para a busca
			
			InitialContext initialContext = new InitialContext();
			
			
			
			IClienteCampanhaService clienteCampanhaService = (IClienteCampanhaService) initialContext.lookup("java:app/bradesco-seguro-web/ClienteCampanhaService!br.com.callink.bradesco.seguro.service.IClienteCampanhaService");

			cliente = clienteCampanhaService.findById(idClienteCampanha);
			
			// Procura o cliente pela chave
			cliente = clienteCampanhaService.findById(idClienteCampanha);

			if (cliente != null) {
				// DEBUG
				logger.debug("ClienteCampanha [" + idClienteCampanha + "] encontrado!");

				// Instancia o service para busca de telefones
				ITelefoneClienteService telefoneClienteService = (ITelefoneClienteService) initialContext.lookup("java:app/bradesco-seguro-web/TelefoneClienteService!br.com.callink.bradesco.seguro.service.ITelefoneClienteService");

				// Busca por todos os telefones do cliente
				List<TelefoneCliente> telefonesList = telefoneClienteService.findTelefoneByClienteCampanha(cliente.getId());

				if (telefonesList != null && !telefonesList.isEmpty()) {

					// Percorre a lista de telefones encontrados e escreve o
					// response
					writeTelefonesResponse(resp, telefonesList, cliente);

				} else {
					// DEBUG
					logger.debug("Não existem telefones para o cliente [" + cliente.getId() + "]");
				}

			} else {
				// DEBUG
				logger.debug("ClienteCampanha [" + idClienteCampanha + "] não encontrado!");
			}
		} catch (Exception ex) {
			// ERROR
			logger.error("Ocorreu um erro ao recuperar os telefones do cliente [" + req.getParameter("idClienteCampanha") + "]", ex);
		}
	}

	/**
	 * Percorre a lista de telefones encontrados e escreve o response
	 * 
	 * @param resp
	 * @param telefonesList
	 * @throws IOException
	 */
	private void writeTelefonesResponse(HttpServletResponse resp, List<TelefoneCliente> telefonesList, ClienteCampanha cliente) throws IOException {
		StringBuilder telsBuffer = new StringBuilder();
		for (TelefoneCliente tel : telefonesList) {
			telsBuffer.append(tel.getDdd() + tel.getTelefone() + ";");
		}

		// DEBUG
		logger.debug("Telefones do cliente [" + cliente.getId() + "] retornados: [" + telsBuffer.toString() + "]");

		resp.getWriter().write(telsBuffer.toString());
		resp.flushBuffer();
	}
}
