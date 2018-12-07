package br.com.callink.bradesco.task.utils;

import java.text.MessageFormat;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Integração ao Servidor Jboss 7 utilizando o protocolo <i>ejb remoting</i> para
 * lookups remotos, por exemplo, de EJBs.
 * 
 * O protocolo utilizado é o protocolo 'remoting native-interface.'
 * 
 * Exemplo de configuração (properties):
 * 
 * <pre>
 * # remote ejb test
 * endpoint.name=bradesco-seguro
 * jboss.naming.client.ejb.context=true // sinaliza contexto ejb
 * java.naming.factory.url.pkgs=org.jboss.ejb.client.naming
 * java.naming.provider.url=remote://localhost:4447
 * java.naming.factory.initial=org.jboss.naming.remote.client.InitialContextFactory
 * java.naming.security.principal=testuser
 * java.naming.security.credentials=test123
 * 
 * remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED=false
 * remote.connections=default
 * remote.connection.default.host=localhost
 * remote.connection.default.port=4447
 * remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS=false
 * remote.connection.default.username=testuser
 * remote.connection.default.password=test123
 * </pre>
 * 
 * @author michael
 * 
 */
public class Jboss7RemotingHelper {
	private InitialContext context;
	private String module;

	public Jboss7RemotingHelper(Properties configuration) throws Exception {
		setup(configuration);
	}

	/**
	 * Efetua lookup remoto (utilizando protocolo ejb remoting, jboss 7)
	 * @param declaredType - Tipo declarado. Exemplo: Interface
	 * @param implementationType - Tipo da Implementacao - Exemplo: Classe que implementa a interface remota
	 * @return
	 * @throws NamingException
	 */
	@SuppressWarnings("unchecked")
	public <T> T lookup(Class<?> declaredType, Class<?> implementationType) throws NamingException {
		String jndi = MessageFormat.format("ejb:/{0}//{1}!{2}", module, implementationType.getSimpleName(), declaredType.getName());
		return (T) this.context.lookup(jndi);
	}

	private void setup(Properties configuration) throws Exception {
		assertConfiguration(configuration);
		this.context = new InitialContext(configuration);
		this.module = configuration.getProperty("endpoint.name");
	}
	
	public void assertConfiguration(Properties config){
		if (isEmpty(config.getProperty("endpoint.name"))) {
			throw new IllegalStateException("Nome da aplicacao nao informado para lookup remoto. Atributo [endpoint.name] deve ser informado.");
		}

		if (isEmpty(config.getProperty("java.naming.factory.url.pkgs"))) {
			throw new IllegalStateException("Packages nao informadas para lookup remoto. Atributo [java.naming.factory.url.pkgs] deve ser informado.");
		}

		if (isEmpty(config.getProperty("java.naming.provider.url"))) {
			throw new IllegalStateException("JNDI Provider nao informado para lookup remoto. Atributo [java.naming.provider.url] deve ser informado.");
		}

		if (isEmpty(config.getProperty("java.naming.factory.initial"))) {
			throw new IllegalStateException("Classe 'InitialFactory' nao informado para lookup remoto. Atributo [java.naming.factory.initial] deve ser informado.");
		}

		if (isEmpty(config.getProperty("java.naming.security.principal"))) {
			throw new IllegalStateException("Usuario para Autenticacao nao informado para lookup remoto. Atributo [java.naming.security.principal] deve ser informado.");
		}

		if (isEmpty(config.getProperty("java.naming.security.credentials"))) {
			throw new IllegalStateException("Senha para Autenticacao nao informada para lookup remoto. Atributo [java.naming.security.credentials] deve ser informado.");
		}
	}
	
	private boolean isEmpty(String value) {
		return value == null || value.trim().equals("");
	}
}