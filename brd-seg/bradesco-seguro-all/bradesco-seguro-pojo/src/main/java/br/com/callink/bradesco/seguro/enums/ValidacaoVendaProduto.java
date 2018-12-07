package br.com.callink.bradesco.seguro.enums;

public enum ValidacaoVendaProduto {
	
	VALIDACAO_1("Validação 01", "Valida idade do titular entre 18 e 64 anos. Os filhos e enteados (as) devem ter até 24 anos de idade. "
			+ "Validação se titular e dependente(s) faz prática de esporte de risco. Validação se titular e dependente(s) exerce profissão de risco."
			+ "Não permite inclusão no plano familiar sem cônjuge ou companheiro (a) (ou seja, a situação de titular mais filhos não é permitida)."
			+ "O cônjuge ou (a) companheiro (a) tenha entre 18 e 64 anos de idade. não deve permitir vendas para clientes sem cadastro de CPF, RG (número, órgão expedidor, data de expedição."
			+ "A aplicação não deve permitir venda para titulares aposentados por invalidez."
			+ "Caso o plano escolhido seja familiar, deverá ser preenchido a tabela dependentes", "VALIDACAO_01"),
			
	VALIDACAO_2("Validação 02", "Valida idade do titular entre 21 e 64 anos. O cônjuge ou (a) companheiro (a) tenha entre 18 e 64 anos de idade."
			+ "Permite a venda para o titular e dependentes, sem necessariamente com a presença de um cônjuge ou companheiro (a)."
			+ "Não deve permitir vendas para clientes sem cadastro de CPF, RG (número, órgão expedidor, data de expedição."
			+ "A aplicação não deve permitir venda para titulares aposentados por invalidez."
			+ "Caso o plano escolhido seja familiar, deverá ser preenchido a tabela dependentes", "VALIDACAO_02"),
	
	VALIDACAO_3("Validação 03", "A aplicação deve garantir que o titular do cartão tenha entre 21 e 64 anos de idade."
			+ "O cônjuge ou (a) companheiro (a) tenha entre 18 e 64 anos de idade. Os filhos e enteados (as) devem ter até 24 anos de idade."
			+ "Permite a venda para o titular e dependentes, sem necessariamente com a presença de um cônjuge ou companheiro (a)."
			+ "Não permite a venda para clientes e dependentes que exerçam alguma atividade profissional considerada de risco. "
			+ "Caso o titular não exerça atividade profissional de risco, mas o seus dependentes (cônjuge, parceiro (a), filhos (as) e/ou enteados (as)) exerçam, a venda pode ser efetuada com o plano individual."
			+ "Não deve permitir venda para titulares aposentados por invalidez. Caso o plano escolhido seja familiar, deverá ser preenchida a aba de dependentes.", "VALIDACAO_03");

    private final String nome;
    private final String validacoes;
    private final String codigo;

    ValidacaoVendaProduto(String nome, String validacoes, String codigo) {
        this.nome = nome;
        this.validacoes = validacoes;
        this.codigo = codigo;
    }

    public String getNome() {
        return this.nome;
    }

    public String getValidacoes() {
        return this.validacoes;
    }
    
    public String getCodigo() {
    	return this.codigo;
    }
    
    @Override
    public String toString() {
		return this.nome;
    	
    }


}

