package br.com.callink.bradesco.seguro.commons.utils;

/**
 * 
 * @author michael
 *
 */
public final class XMLUtils {

	/**
	 * Aplica CDATA no XML informado em <i>source</i> para o Node informado em <i>nodeName</i>
	 * @param source
	 * @param nodeName
	 * @return
	 */
	public static String applyCDATA(String source, String nodeName){
		if(!StringUtils.isEmpty(source)){
			return source.replaceAll("<" + nodeName + ">(.*?)</" + nodeName + ">", "<" + nodeName + "><![CDATA[$1]]></" + nodeName + ">");
		}
		
		return null;
	}
	
	/**
	 * Remove line break
	 * @param source
	 * @return
	 */
	public static String normalize(String source){
		return StringUtils.isEmpty(source) ? null : source.replaceAll("\n", "");
	}
}