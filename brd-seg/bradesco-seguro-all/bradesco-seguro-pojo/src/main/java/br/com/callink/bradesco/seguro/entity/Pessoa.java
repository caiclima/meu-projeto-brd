/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.callink.bradesco.seguro.entity;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import br.com.callink.bradesco.seguro.entity.metadata.IdentifiableEntity;

/**
 *
 * @author neppo_oldamar
 */
@Entity
@Table(name = "TB_PESSOA")
@XmlRootElement
public class Pessoa implements IdentifiableEntity<BigInteger> {
    
	private static final long serialVersionUID = 1L;
    
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PESSOA")
    private BigInteger id;
	
	@Basic(optional = false)
    @Column(name = "ID_PESSOA_CORPORATIVO")
    private BigInteger idPessoaCorporativo;
    
	@Basic(optional = false)
    @Column(name = "NOM_PESSOA")
    private String nomePessoa;
    
	@Basic(optional = false)
    @Column(name = "NUM_CPF_CNPJ")
    private String cpfCnpj;
    
	@Column(name = "EMAIL")
    private String email;
    
	@Column(name = "NUM_TELEFONE")
    private String telefone;
    
	@Column(name = "DAT_ATUALIZACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;
    
	@OneToMany(mappedBy = "id", fetch=FetchType.LAZY)
    private List<PessoaColaborador> pessoaColaboradorList;
    
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "id")
    private List<Usuario> usuarioList;

    public Pessoa() {
    }

    public Pessoa(BigInteger id) {
        this.id = id;
    }
    
    public Pessoa(BigInteger id, BigInteger idPessoaCorporativo,
			String nomePessoa, String cpfCnpj, String email, String telefone,
			Date dataAtualizacao,
			List<PessoaColaborador> pessoaColaboradorList,
			List<Usuario> usuarioList) {
		super();
		this.id = id;
		this.idPessoaCorporativo = idPessoaCorporativo;
		this.nomePessoa = nomePessoa;
		this.cpfCnpj = cpfCnpj;
		this.email = email;
		this.telefone = telefone;
		this.dataAtualizacao = dataAtualizacao;
		this.pessoaColaboradorList = pessoaColaboradorList;
		this.usuarioList = usuarioList;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getIdPessoaCorporativo() {
		return idPessoaCorporativo;
	}

	public void setIdPessoaCorporativo(BigInteger idPessoaCorporativo) {
		this.idPessoaCorporativo = idPessoaCorporativo;
	}

	public String getNomePessoa() {
		return nomePessoa;
	}

	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public List<PessoaColaborador> getPessoaColaboradorList() {
		return pessoaColaboradorList;
	}

	public void setPessoaColaboradorList(
			List<PessoaColaborador> pessoaColaboradorList) {
		this.pessoaColaboradorList = pessoaColaboradorList;
	}

	@XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pessoa)) {
            return false;
        }
        Pessoa other = (Pessoa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.callink.bradesco.seguro.entity.Pessoa[ id=" + id + " ]";
    }
    
}
