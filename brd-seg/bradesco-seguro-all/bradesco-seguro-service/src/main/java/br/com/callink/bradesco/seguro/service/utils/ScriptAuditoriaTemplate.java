/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.callink.bradesco.seguro.service.utils;

import br.com.callink.bradesco.seguro.dto.ScriptAuditoriaDTO;
import br.com.callink.bradesco.seguro.enums.ValidacaoVendaProduto;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rogerio_moreira
 */
public class ScriptAuditoriaTemplate {

    private static final String SCRIPT_1;
    private static final String SCRIPT_2;
    private static final String SCRIPT_3;
    private static final String SCRIPT_3_DEPENDENTE;

    static {
        SCRIPT_1 = getFile("templateScriptsAuditoria/script1.html");
        SCRIPT_2 = getFile("templateScriptsAuditoria/script2.html");
        SCRIPT_3 = getFile("templateScriptsAuditoria/script3.html");
        SCRIPT_3_DEPENDENTE = getFile("templateScriptsAuditoria/script3_dependente.html");
    }

    private static String getFile(String fileName) {
        try {
            byte[] dd;
            try (InputStream is = ScriptAuditoriaTemplate.class.getClassLoader().getResourceAsStream(fileName)) {
                dd = new byte[is.available()];
                is.read(dd);
            }
            return new String(dd, "UTF-8");

        } catch (IOException ex) {
            Logger.getLogger(ScriptAuditoriaTemplate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    private static String script1(ScriptAuditoriaDTO auditoriaDTO) {
        String script = SCRIPT_1;
        script = script.replaceFirst("@@Cliente.nome", auditoriaDTO.getClienteNome());
        return script;
    }

    private static String script2(ScriptAuditoriaDTO auditoriaDTO) {
        String script = SCRIPT_2;
        script = script.replaceFirst("@@Cliente.nome", auditoriaDTO.getClienteNome());
        script = script.replaceFirst("@@Cliente.Plano", auditoriaDTO.getClientePlano());
        script = script.replaceFirst("@@Cliente.Produto", auditoriaDTO.getClienteProduto());
        script = script.replaceFirst("@@Cliente.ValorCobertura", auditoriaDTO.getClienteValorCobertura());
        script = script.replaceFirst("@@Plano.DIH", auditoriaDTO.getValorDIH());
        script = script.replaceFirst("@@Plano.ValorSorteio", auditoriaDTO.getValorSorteio());
        script = script.replaceFirst("@@Cliente.Valor", auditoriaDTO.getClienteValor());
        return script;
    }

    private static String script3(ScriptAuditoriaDTO auditoriaDTO) {
        String script = SCRIPT_3;
        script = script.replaceFirst("@@Cliente.nome", auditoriaDTO.getClienteNome());
        script = script.replaceFirst("@@Cliente.Plano", auditoriaDTO.getClientePlano());
        script = script.replaceFirst("@@Cliente.Produto", auditoriaDTO.getClienteProduto());
        script = script.replaceFirst("@@Cliente.ValorCobertura", auditoriaDTO.getClienteValorCobertura());
        script = script.replaceFirst("@@Plano.DIH", auditoriaDTO.getValorDIH());
        script = script.replaceFirst("@@Plano.ValorSorteio", auditoriaDTO.getValorSorteio());
        script = script.replaceFirst("@@Cliente.Valor", auditoriaDTO.getClienteValor());

        StringBuilder sbr = new StringBuilder(script);

        for (ScriptAuditoriaDTO.DependenteDTO dependente : auditoriaDTO.getDependentes()) {
            String scriptDependentes = SCRIPT_3_DEPENDENTE;
            scriptDependentes = scriptDependentes.replaceFirst("@@Cliente.nome", auditoriaDTO.getClienteNome());
            scriptDependentes = scriptDependentes.replaceFirst("@@Cliente.Plano", auditoriaDTO.getClientePlano());
            scriptDependentes = scriptDependentes.replaceFirst("@@Cliente.Produto", auditoriaDTO.getClienteProduto());
            scriptDependentes = scriptDependentes.replaceFirst("@@Dependente.GrauParentesco", dependente.dependenteGrauParentesco);

            scriptDependentes = scriptDependentes.replaceFirst("@@Dependente.nome", dependente.dependenteNome);
            scriptDependentes = scriptDependentes.replaceFirst("@@Dependente.ValorCobertura", dependente.dependenteValorCobertura);
            scriptDependentes = scriptDependentes.replaceFirst("@@Dependente.DHI", dependente.dependentePlanoDIH);
            scriptDependentes = scriptDependentes.replaceFirst("@@Dependente.ValorSorteio", dependente.dependenteValorSorteio);
            sbr.append(scriptDependentes);
        }

        return sbr.toString();
    }


    public static String getScript(ScriptAuditoriaDTO auditoriaDTO) {

    	if (auditoriaDTO != null) {
	        if ((!auditoriaDTO.getClienteCodigoValidacaoProduto().equalsIgnoreCase(ValidacaoVendaProduto.VALIDACAO_2.getCodigo())) 
	        			&& (auditoriaDTO.getEsporteRisco() || auditoriaDTO.getProfissaoRisco())) {
	            return script1(auditoriaDTO);
	        }
	
	        String produto = auditoriaDTO.getClienteProduto();
	        String plano = auditoriaDTO.getClientePlano();
	
	        if (!auditoriaDTO.getAposentadoInvalidez() && produto.startsWith("Protecao Pessoal") && plano.toUpperCase().contains("INDIVIDUAL")) {
	            return script2(auditoriaDTO);
	        }
	
	        if (!auditoriaDTO.getAposentadoInvalidez() && produto.startsWith("Protecao Pessoal") && plano.toUpperCase().contains("FAMILIAR")) {
	            return script3(auditoriaDTO);
	        }
    	}
        return "Não existe Script cadastrado para esse Produto!";
    }

//    public static void main(String[] args){
//      ScriptAuditoriaService ss = new ScriptAuditoriaService();
//        ScriptAuditoriaDTO a = new ScriptAuditoriaDTO();
//        a.setAposentadoInvalidez(true);
//        a.setClienteNome("Andre Golçalves de Moura");
//        a.setEsporteRisco(false);
//        a.setProfissaoRisco(false);
//        a.setAposentadoInvalidez(false);
//        a.setClienteProduto("Proteção Pessoal");
//        a.setClientePlano("FAMILIAR");
//        a.setClienteValor("25.90");
//        a.setClienteValorCobertura("200.0000,00");
//        a.setPlanoValorSorteio("50.000");
//        a.setPlanoDIH("50");
//        
//        a.addDependente("Patricia Caixeta", "Conjuje", "150.000", "100", "30.000");
//        a.addDependente("Eloisa Caixeta", "Filha", "150.000", "100", "30.000");
//        a.addDependente("Naiara Caixeta", "Filha", "150.000", "100", "30.000");
//        a.addDependente("Julio Caixeta", "Sobrinho", "150.000", "100", "30.000");
//        
//      System.out.println(ss.getScript(a));
//    }
}
