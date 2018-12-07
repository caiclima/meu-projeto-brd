/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.callink.bradesco.seguro.service.impl;

import br.com.callink.bradesco.seguro.service.IAuthenticatorService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.PropertieUtils;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

/**
 *
 * @author Rogério Moreira de Andrade. rogeriom@swb.com.br
 */
public class AuthenticatorService implements IAuthenticatorService {

    private static final String CAMINHO_ARQUIVO_PROPERTIES_CONFIGURACAO_TELA = "";
    private static final String NOME_ARQUIVO_PROPERTIE = "/ldap-config.properties";

    @Override
    public void authenticaAD(String usuario, String senha) throws ServiceException {

        String ldapServerName = PropertieUtils.getPropertieByArquivoAndChave(CAMINHO_ARQUIVO_PROPERTIES_CONFIGURACAO_TELA, NOME_ARQUIVO_PROPERTIE, "parametro.ldap.name");
        String rootdn = PropertieUtils.getPropertieByArquivoAndChave(CAMINHO_ARQUIVO_PROPERTIES_CONFIGURACAO_TELA, NOME_ARQUIVO_PROPERTIE, "parametro.rootdn");

        try {

            Hashtable env = new Hashtable();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, ldapServerName);
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, usuario + "@" + rootdn);
            env.put(Context.SECURITY_CREDENTIALS, senha);

            LdapContext ctxGC = new InitialLdapContext(env, null);

        } catch (NamingException ex) {
            throw new ServiceException("Usuário ou senha inválidos.");
        }

    }

}
