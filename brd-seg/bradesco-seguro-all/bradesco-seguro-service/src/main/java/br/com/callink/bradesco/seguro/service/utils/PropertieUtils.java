package br.com.callink.bradesco.seguro.service.utils;

import java.io.IOException;
import java.util.Properties;

public final class PropertieUtils {

	private static Properties propertie;
	
	private PropertieUtils(){
		
	}

	public static Properties getPropertieByNomeArquivo(String nomePropertie) {
		try {
			propertie = new Properties();
			propertie.load(PropertieUtils.class.getResourceAsStream(nomePropertie));

		} catch (IOException e) {
		}
		return propertie;
	}

	public static String getPropertieByArquivoAndChave(String fileName, String key) {

		propertie = getPropertieByNomeArquivo(fileName);
		if(propertie == null){
			return null;
		}
		return propertie.getProperty(key);
	}
	public static String getPropertieByArquivoAndChave(String path, String fileName, String key) {

		String caminho = String.format(path + fileName);
		propertie = getPropertieByNomeArquivo(caminho);
		if(propertie == null){
			return null;
		}
		return propertie.getProperty(key);
	}

}
