package br.com.callink.bradesco.seguro.commons.utils;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;


public final class PropertiesUtils {
	private static Logger logger = Logger.getLogger(PropertiesUtils.class);
	private static Properties props = new Properties();

	private PropertiesUtils(String fileName) {
		try {
			props.load(this.getClass().getClassLoader().getResourceAsStream(fileName));
		} catch (Exception ex) {
			logger.error(ex);
		}
	}
	
	public static Properties getPropertieByNomeArquivo(String nomeProperty) {
		try {
			props = new Properties();
			props.load(PropertiesUtils.class.getResourceAsStream(nomeProperty));

		} catch (IOException e) {
		}
		return props;
	}

	public static String getPropertieByArquivoAndChave(String fileName, String key) {

		props = getPropertieByNomeArquivo(fileName);
		if(props == null){
			return null;
		}
		return props.getProperty(key);
	}
	public static String getPropertieByArquivoAndChave(String path, String fileName, String key) {

		String caminho = String.format(path + fileName);
		props = getPropertieByNomeArquivo(caminho);
		if(props == null){
			return null;
		}
		return props.getProperty(key);
	}

	public static void load(String fileName) {
		new PropertiesUtils(fileName);
	}

	public static String getProperty(String key) {
		return (String) props.getProperty(key);
	}
}
