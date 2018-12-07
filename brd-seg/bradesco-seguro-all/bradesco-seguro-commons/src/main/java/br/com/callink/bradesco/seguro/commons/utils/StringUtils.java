package br.com.callink.bradesco.seguro.commons.utils;

import java.util.Locale;



/**
 * Classe utilitária para String
 * @author michael
 *
 */
public final class StringUtils {
	
	/**
	 * Indica se é null ou uma String 'vazia'
	 * @param c
	 * @return
	 */
	public static boolean isEmpty(String value){
		return value == null || value.trim().equals("");
	}
	
	public static String toString(Object o ){
		return o == null ? null : o.toString();
	}
	
	public static String format(String str, Object... args){
		return StringUtils.isEmpty(str) ? null : String.format(Locale.getDefault(), str, args);
	}
	
	public static String substring(String str, Integer start, Integer qtd) {
		if (str == null) {
			return null;
		}
		
		return str.substring(start, qtd);
	}
	
	public static String preencheCom(String linha_a_preencher, String letra, int tamanho, int direcao){

        //Checa se Linha a preencher é nula ou branco
        if (linha_a_preencher == null || linha_a_preencher.trim() == "" ) {
        	linha_a_preencher = "";
        }

        //Enquanto Linha a preencher possuir 2 espaços em branco seguidos, substitui por 1 espaço apenas
        while (linha_a_preencher.contains(" ")) {
        	linha_a_preencher = linha_a_preencher.replaceAll(" "," ").trim();
        }

        //Retira caracteres estranhos
        linha_a_preencher = linha_a_preencher.replaceAll("[./-]","");

        StringBuffer sb = new StringBuffer(linha_a_preencher);

        if (direcao==1) { //a Esquerda

            for (int i=sb.length() ; i<tamanho ; i++){
                sb.insert(0,letra);
            }

        } else if (direcao==2) {//a Direita

            for (int i=sb.length() ; i<tamanho ; i++){
                sb.append(letra);
            }
        }

        return sb.toString();
    }

}
