package br.com.callink.bradesco.seguro.web.filter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Sinaliza para o browser que o mesmo deve fazer cache dos resources do
 * 
 * RichFaces (parâmetro de request ln=org.richfaces). O tempo em que o resource
 * será mantido em cache pelo browser está configurado pelo parâmetro de
 * contexto RichfacesResourceCacheTimeout (em segundos).
 * 
 * @author michael
 * 
 */
@WebFilter(filterName = "ResourceCacheFilter", urlPatterns = "/*")
public class ResourceCacheFilter implements Filter {
	private Integer seconds;
	private final DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy");

	@Override
	public void init(FilterConfig config) throws ServletException {
		String _seconds = config.getServletContext().getInitParameter("RichfacesResourceCacheTimeout");
		if (_seconds == null) {
			throw new ServletException("Timeout de cache de Resources [RichfacesResourceCacheTimeout] não configurado no web.xml");
		}
		this.seconds = Integer.valueOf(_seconds);

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try{
			if (!isAppResource(request) && request.getParameter("ln") != null) {
				Date now = addSeconds(this.seconds);
				String expires = this.df.format(now) + " 10:00:00 GMT";
				
				HttpServletResponse resp = (HttpServletResponse) response;
				resp.setHeader("Pragma", "public");
				resp.setHeader("Cache-Control", "public, max-age=" + this.seconds);
				resp.setHeader("Expires", expires);
			}
			chain.doFilter(request, response);
		}catch(Exception e){
			chain.doFilter(request, response); // ignore caching and doFilter()
		}
	}
	
	private boolean isAppResource(ServletRequest request){
		String path = ((HttpServletRequest) request).getPathInfo();
		return path != null && path.matches(".{0,1}(\\d+)_(\\d+)_(\\d+)\\..{3}");
	}

	@Override
	public void destroy() {
	}

	private Date addSeconds(int secs) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.SECOND, secs);
		return now.getTime();
	}

}
