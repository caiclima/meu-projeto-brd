package br.com.callink.bradesco.seguro.service.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.callink.bradesco.seguro.dao.IBeneficiarioPlanoDAO;
import br.com.callink.bradesco.seguro.entity.BeneficiarioPlano;
import br.com.callink.bradesco.seguro.service.IBeneficiarioPlanoService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

@Stateless
public class BeneficiarioPlanoService extends
		GenericCrudServiceImpl<BeneficiarioPlano> implements
		IBeneficiarioPlanoService<BeneficiarioPlano> {
	
	@Inject
	private IBeneficiarioPlanoDAO dao;
	
	@Override
	protected IBeneficiarioPlanoDAO getDAO() {
		return dao;
	}
	
	@Override
	public ServiceResponse salvarOuAtualizar(BeneficiarioPlano beneficiarioPlano) throws ServiceException {

		antesSalvar(beneficiarioPlano);
		return super.saveOrUpdate(beneficiarioPlano);
	}

	@Override
	protected void antesSalvar(BeneficiarioPlano beneficiarioPlano)
			throws ServiceException {

		validarVazio(beneficiarioPlano, "Informe o Beneficiario");
		validarVazio(beneficiarioPlano.getBeneficiario(), "Informe o Benenficiario.");
		validarVazio(beneficiarioPlano.getBeneficiario().getId(), "Informe o Id do Benenficiario.");
		beneficiarioPlano.setIdBeneficiario(beneficiarioPlano.getBeneficiario().getId());
		validarVazio(beneficiarioPlano.getPlano(), "Informe o Plano.");
		validarVazio(beneficiarioPlano.getPlano().getId(), "Informe o Id do Plano.");
		beneficiarioPlano.setIdPlano(beneficiarioPlano.getPlano().getId());
	}
	
	private void validarVazio(Object obj, String mensagem)
			throws ServiceException {

		if (obj == null) {
			mensagemErro(mensagem);
		}
	}

	private ServiceException mensagemErro(String mensagem)
			throws ServiceException {
		throw new ServiceException(mensagem);
	}

}
