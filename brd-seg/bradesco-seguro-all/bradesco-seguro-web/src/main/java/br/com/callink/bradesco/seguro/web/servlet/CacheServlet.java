package br.com.callink.bradesco.seguro.web.servlet;


import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.callink.bradesco.seguro.commons.utils.ClearCacheCommand;
import br.com.callink.bradesco.seguro.service.ICacheService;

/**
 * Servlet implementation class CacheServlet
 * @author swb_samuel
 */
@WebServlet("/cache/*")
public class CacheServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ICacheService cacheService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CacheServlet() {
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
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) {
		String comm = (String) request.getParameter("command");
		
		if(comm == null){
			throw new IllegalArgumentException("É obrigatório informar o comando! ");
		}
		
		switch(ClearCacheCommand.getInstance(comm)) {
			case PARAMETRO_SISTEMA:
				cacheService.clearCacheParametroSistema();
				break;
				
			case EVENTO: 
				cacheService.clearCacheEvento();
				break;
				
			case TIPO_EVENTO: 
				cacheService.clearCacheTipoEvento();
				break;
				
			case PRODUTO: 
				cacheService.clearCacheProduto();
				break;
				
			case PLANO: 
				cacheService.clearCachePlano();
				break;
				
			case PROFISSAO: 
				cacheService.clearCacheProfissao();
				break;
				
			case ESPORTE: 
				cacheService.clearCacheEsporte();
				break;
				
			case GRAU_PARENTESCO: 
				cacheService.clearCacheGrauPrentesco();
				break;
				
			case ESTADO_CIVIL: 
				cacheService.clearCacheEstadoCivil();
				break;
				
			case TIPO_PLANO: 
				cacheService.clearCacheTipoPlano();
				break;
				
			default:
				throw new IllegalArgumentException("Comando não conhecido! ");
		}
	}
}
