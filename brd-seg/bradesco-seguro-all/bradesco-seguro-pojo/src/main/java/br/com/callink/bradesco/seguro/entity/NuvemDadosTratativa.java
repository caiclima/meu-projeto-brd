package br.com.callink.bradesco.seguro.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.callink.bradesco.seguro.entity.metadata.IRemovableEntity;
import br.com.callink.bradesco.seguro.entity.metadata.IdentifiableEntity;

@Entity
@Table(name = "TB_NUVEM_DADOS_TRATATIVAS")
@XmlRootElement
public class NuvemDadosTratativa implements IdentifiableEntity<BigInteger>, IRemovableEntity, Comparable<Esporte>, ILog  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getLogUid() {
		return null;
	}

	@Override
	public void setLogUid(String logUid) {
		
	}

	@Override
	public String getLogHost() {
		return null;
	}

	@Override
	public void setLogHost(String logHost) {
		
	}

	@Override
	public Date getLogDate() {
		return null;
	}

	@Override
	public void setLogDate(Date logDate) {
		
	}

	@Override
	public String getLogSystem() {
		return null;
	}

	@Override
	public void setLogSystem(String logSystem) {
	}

	@Override
	public String getLogObs() {
		return null;
	}

	@Override
	public void setLogObs(String logObs) {
		
	}

	@Override
	public BigInteger getLogTransaction() {
		return null;
	}

	@Override
	public void setLogTransaction(BigInteger logTransaction) {
		
	}

	@Override
	public int compareTo(Esporte arg0) {
		return 0;
	}

	@Override
	public Boolean getFlagRemoved() {
		return null;
	}

	@Override
	public void setFlagRemoved(Boolean removed) {
		
	}

	@Override
	public BigInteger getId() {
		return null;
	}

	@Override
	public void setId(BigInteger id) {
		
	}
	
}
