package br.com.callink.bradesco.seguro.web.faces.webobjects;

import java.io.Serializable;

import br.com.callink.bradesco.seguro.entity.Produto;

public class ProdutoDTO implements Serializable {

	private static final long serialVersionUID = -7628141856344077635L;

	private Produto produto;

	private boolean selecionado = Boolean.FALSE;

	public ProdutoDTO() {
	}

	public ProdutoDTO(Produto produto) {
		this.produto = produto;
	}

	public ProdutoDTO(Produto produto, boolean selecionado) {
		this.produto = produto;
		this.selecionado = selecionado;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public boolean getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ProdutoDTO)) {
			return false;
		}
		ProdutoDTO other = (ProdutoDTO) obj;
		if (produto == null) {
			if (other.produto != null) {
				return false;
			}
		} else if (!produto.equals(other.produto)) {
			return false;
		}
		return true;
	}
	
}
