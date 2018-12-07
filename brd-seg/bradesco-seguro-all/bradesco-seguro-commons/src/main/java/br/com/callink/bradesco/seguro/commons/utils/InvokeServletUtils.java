package br.com.callink.bradesco.seguro.commons.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import org.apache.commons.codec.binary.Base64;

/**
 * @author swb_samuel
 *
 */
public class InvokeServletUtils {

	/**
	 * @param urlString - obrigatório
	 * @param username - opcional
	 * @param password - opcional
	 * 
	 * <br />
	 * Implementa uma basic autetication, caso seja informado usuário e senha.
	 * Neste caso deve estar devidamente configurado o security-domain no standalone.xml ou domain.xml 
	 * 
	 * <br />
	 * Exemplo de configuração:
	 * <br />
	 * 
	 * <pre>
	 * {@code 
	 * <security-domain name="webapp-realm" cache-type="default">
     *   <authentication>
     *       <login-module code="UsersRoles" flag="required">
     *           <module-option name="usersProperties" value="${jboss.server.config.dir}/my-users.properties"></module-option>
     *           <module-option name="rolesProperties" value="${jboss.server.config.dir}/my-roles.properties"></module-option>
     *       </login-module>
     *   </authentication>
	 * </security-domain>
	 * }
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public static String invokeServlet(String urlString, String username, String password) throws Exception {
		OutputStreamWriter writer = null;
		BufferedReader reader = null;

		try {

			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);

			if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
				String encoded = Base64.encodeBase64String((username + ":" + password).getBytes());
				conn.setRequestProperty("Authorization", "Basic " + encoded);
			}

			/*
			 * force
			 */
			writer = new OutputStreamWriter(conn.getOutputStream());
			writer.write("");
			writer.flush();

			/*
			 * response
			 */
			String line;
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder output = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				output.append(line);
			}
			return output.toString();

		} catch (Exception e) {
			throw e;
		} finally {
			if (writer != null)
				writer.close();
			if (reader != null)
				reader.close();
		}
	}
}
