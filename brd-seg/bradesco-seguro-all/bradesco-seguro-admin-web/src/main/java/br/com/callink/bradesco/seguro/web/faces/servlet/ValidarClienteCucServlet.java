package br.com.callink.bradesco.seguro.web.faces.servlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ValidarClienteCucServlet
 * @author Gabriel Arvelos
 */
@WebServlet("/validaCliente/*")
public class ValidarClienteCucServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ValidarClienteCucServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
//		JSONObject json = new JSONObject();
//        JSONArray jsonRetorno = new JSONArray();
//		try {
//			
//			logger.info("INICIANDO PROCESSO DE VALIDAÇÃO DE EXISTENCIA DE CLIENTES EM IMPORTAÇÃO");
//			List<ArquivoMailing> rejeitados = arquivoMailingService.validarClientesExistemNoCuc();
//			if(rejeitados != null
//					&& rejeitados.size() > 0){
//				for(ArquivoMailing arquivoMailing : rejeitados){
//					arquivoMailingService.saveOrUpdate(arquivoMailing);
//				}
//			}
//			logger.info("FINALIZADO PROCESSO DE VALIDAÇÃO DE EXISTENCIA DE CLIENTES EM IMPORTAÇÃO");
//			json.put("RESULTADO", "SUCESSO");
//		} catch (Exception e) {
//			logger.info("ERRO NO PROCESSO DE VALIDAÇÃO DE EXISTENCIA DE CLIENTES EM IMPORTAÇÃO["+e.getMessage()+"]");
//			json.put("RESULTADO", "ERRO:["+e.getMessage()+"]");
//		}
//		jsonRetorno.add(json);
//		response.getWriter().write(jsonRetorno.toString());
	}
}
