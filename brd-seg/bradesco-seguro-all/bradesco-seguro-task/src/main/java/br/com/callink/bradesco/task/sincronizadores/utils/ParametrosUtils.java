package br.com.callink.bradesco.task.sincronizadores.utils;

import org.apache.log4j.Logger;

import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.seguro.entity.ParametroSistema;
import br.com.callink.bradesco.seguro.service.IParametroSistemaService;

public class ParametrosUtils {
	
	private static final Logger logger = Logger.getLogger(ParametrosUtils.class);
	
	private static String numeroDePermicoes;
	private static String idOperacao;
	private static String idCargos;
	
	@SuppressWarnings("rawtypes")
	public static String getIdOperacao(IParametroSistemaService parametroSistemaService) {
		if (idOperacao != null) {
			return idOperacao;
		}
		
		try {
			idOperacao = parametroSistemaService.buscarValorParametro(ParametroSistema.PARAMETRO_CODIGO_OPERACAO_SINCRONIZACAO_USUARIOS_COORPORATIVO);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao buscar parametro " 
					+ ParametroSistema.PARAMETRO_CODIGO_OPERACAO_SINCRONIZACAO_USUARIOS_COORPORATIVO + ". Erro: " + e.getMessage(), e);
		}
		
		if (StringUtils.isEmpty(idOperacao)) {
			throw new IllegalStateException("Parâmetro de Sistema '" + ParametroSistema.PARAMETRO_CODIGO_OPERACAO_SINCRONIZACAO_USUARIOS_COORPORATIVO + "' não encontrado!");
		}
		
		return idOperacao;
	}
	
	@SuppressWarnings("rawtypes")
	public static String getNumeroPermissoes(IParametroSistemaService parametroSistemaService) {
		if (numeroDePermicoes != null) {
			return numeroDePermicoes;
		}
		
		try {
			numeroDePermicoes = parametroSistemaService.buscarValorParametro(ParametroSistema.PARAMETRO_NUMERO_EXECUCOES_SIMULTANEAS_PERMITIDAS_SINCRONIZACAO_USUARIOS_COORPORATIVO);
		} catch (Exception e) {
			logger.error("Erro ao buscar parametro " 
					+ ParametroSistema.PARAMETRO_NUMERO_EXECUCOES_SIMULTANEAS_PERMITIDAS_SINCRONIZACAO_USUARIOS_COORPORATIVO
					+ ". Isso poderá causar sincronizações simultâneas e comprometer a memória do Sistema. Erro: " + e.getMessage(), e);
		}
		
		if (StringUtils.isEmpty(numeroDePermicoes)) {
			throw new IllegalStateException("Parâmetro de Sistema '" + ParametroSistema.PARAMETRO_NUMERO_EXECUCOES_SIMULTANEAS_PERMITIDAS_SINCRONIZACAO_USUARIOS_COORPORATIVO + "' não encontrado!");
		}
		
		return numeroDePermicoes;
	}
	
	@SuppressWarnings("rawtypes")
	public static String getIdCargos(IParametroSistemaService parametroSistemaService) {
		if (idCargos != null) {
			return idCargos;
		}
		
		String idCargos = null;
		
		try {
			idCargos = parametroSistemaService.buscarValorParametro(ParametroSistema.PARAMETRO_CODIGO_DOMINIOS_SINCRONIZACAO_USUARIOS_COORPORATIVO);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao buscar parametro " 
					+ ParametroSistema.PARAMETRO_CODIGO_DOMINIOS_SINCRONIZACAO_USUARIOS_COORPORATIVO + ". Erro: " + e.getMessage(), e);
		}
				
		if (StringUtils.isEmpty(idCargos)) {
			throw new IllegalStateException("Parâmetro de Sistema '" + ParametroSistema.PARAMETRO_CODIGO_CARGOS_SINCRONIZACAO_USUARIOS_COORPORATIVO + "' não encontrado!");
		}
		
		return idCargos;
	}
	
	public static void resetIdOperacao() {
		idOperacao = null;
	}
	
	public static void resetIdCargos() {
		idCargos = null;
	}
	
	public static void resetNumeroDePermissoes() {
		numeroDePermicoes = null;
	}
	
	public static void resetAll() {
		resetIdOperacao();
		resetIdCargos();
		resetNumeroDePermissoes();
	}
	
	@SuppressWarnings("rawtypes")
	public static Boolean getFlagExecutaSincronizador(IParametroSistemaService parametroSistemaService) {
		String executaSincronizador = null;
		try {
			executaSincronizador = parametroSistemaService.buscarValorParametro(ParametroSistema.PARAMETRO_EXECUTA_SINCRONIZADOR);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao buscar parametro " 
					+ ParametroSistema.PARAMETRO_EXECUTA_SINCRONIZADOR + ". Erro: " + e.getMessage(), e);
		}
		
		if (StringUtils.isEmpty(executaSincronizador)) {
			return true;
		}
		
		if("1".equals(executaSincronizador) ||
			"true".equals(executaSincronizador) ||
			"sim".equals(executaSincronizador) ||
			"s".equals(executaSincronizador)) {
			
			return true;
		}
		
		return false;
	}

}
