/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.callink.bradesco.seguro.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rogerio_moreira
 */
public class ScriptAuditoriaDTO {

    private String clienteNome;
    private String clientePlano;
    private String clienteProduto;
    private String clienteValorCobertura;
    private String planoDIH;
    private String planoValorSorteio;
    private String clienteValor;
    private String clienteCodigoValidacaoProduto;
    private String valorSorteio;
    private String valorDIH;
    
    private boolean esporteRisco;
    private boolean profissaoRisco;
    private boolean aposentadoInvalidez;
    
    private final List<DependenteDTO> dependentes = new ArrayList<>();

    public void addDependente(String dependenteNome, String dependenteGrauParentesco, String dependenteValorCobertura, String dependentePlanoDIH
    		, String dependenteValorSorteio) {
        dependentes.add(new DependenteDTO(dependenteNome, dependenteGrauParentesco, dependenteValorCobertura, dependentePlanoDIH, dependenteValorSorteio));
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public String getClientePlano() {
        return clientePlano;
    }

    public void setClientePlano(String clientePlano) {
        this.clientePlano = clientePlano;
    }

    public String getClienteProduto() {
        return clienteProduto;
    }

    public void setClienteProduto(String clienteProduto) {
        this.clienteProduto = clienteProduto;
    }

    public String getClienteValorCobertura() {
        return clienteValorCobertura;
    }

    public void setClienteValorCobertura(String clienteValorCobertura) {
        this.clienteValorCobertura = clienteValorCobertura;
    }

    public String getPlanoDIH() {
        return planoDIH;
    }

    public void setPlanoDIH(String planoDIH) {
        this.planoDIH = planoDIH;
    }

    public String getPlanoValorSorteio() {
        return planoValorSorteio;
    }

    public void setPlanoValorSorteio(String planoValorSorteio) {
        this.planoValorSorteio = planoValorSorteio;
    }

    public String getClienteValor() {
        return clienteValor;
    }

    public void setClienteValor(String clienteValor) {
        this.clienteValor = clienteValor;
    }

    public List<DependenteDTO> getDependentes() {
        return dependentes;
    }

    public boolean getEsporteRisco() {
        return esporteRisco;
    }

    public void setEsporteRisco(boolean esporteRisco) {
        this.esporteRisco = esporteRisco;
    }

    public boolean getProfissaoRisco() {
        return profissaoRisco;
    }

    public void setProfissaoRisco(boolean profissaoRisco) {
        this.profissaoRisco = profissaoRisco;
    }

    public boolean getAposentadoInvalidez() {
        return aposentadoInvalidez;
    }

    public void setAposentadoInvalidez(boolean aposentadoInvalidez) {
        this.aposentadoInvalidez = aposentadoInvalidez;
    }

    public String getClienteCodigoValidacaoProduto() {
		return clienteCodigoValidacaoProduto;
	}

	public void setClienteCodigoValidacaoProduto(String clienteCodigoValidacaoProduto) {
		this.clienteCodigoValidacaoProduto = clienteCodigoValidacaoProduto;
	}

	public String getValorSorteio() {
		return valorSorteio;
	}

	public void setValorSorteio(String valorSorteio) {
		this.valorSorteio = valorSorteio;
	}

	public String getValorDIH() {
		return valorDIH;
	}

	public void setValorDIH(String valorDIH) {
		this.valorDIH = valorDIH;
	}



	public class DependenteDTO {

        private DependenteDTO(String dependenteNome, String dependenteGrauParentesco, String dependenteValorCobertura, String dependentePlanoDIH, String dependenteValorSorteio) {
            this.dependenteNome = dependenteNome;
            this.dependenteGrauParentesco = dependenteGrauParentesco;
            this.dependenteValorCobertura = dependenteValorCobertura;
            this.dependentePlanoDIH = dependentePlanoDIH;
            this.dependenteValorSorteio = dependenteValorSorteio;
        }
        public final String dependenteNome;
        public final String dependenteGrauParentesco;
        public final String dependenteValorCobertura;
        public final String dependentePlanoDIH;
        public final String dependenteValorSorteio;

    }

}
