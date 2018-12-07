package br.com.callink.bradesco.seguro.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.callink.bradesco.seguro.dao.IBeneficiarioDAO;
import br.com.callink.bradesco.seguro.entity.Beneficiario;

public class BeneficiarioDAO extends GenericHibernateDAOImpl<Beneficiario>
		implements IBeneficiarioDAO {

	@Override
	public List<Beneficiario> findByExample(Beneficiario object) {
		//TODO - melhorar codigo
		Set<Beneficiario> setResponse= new HashSet<Beneficiario>(super.findByExample(object));
		return new ArrayList<Beneficiario>(setResponse);
	}
}
