package br.com.callink.bradesco.seguro.service.impl;

import java.math.BigInteger;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.callink.bradesco.seguro.commons.utils.CollectionUtils;
import br.com.callink.bradesco.seguro.dao.IBeneficiarioDAO;
import br.com.callink.bradesco.seguro.entity.Beneficiario;
import br.com.callink.bradesco.seguro.entity.BeneficiarioPlano;
import br.com.callink.bradesco.seguro.service.IBeneficiarioPlanoService;
import br.com.callink.bradesco.seguro.service.IBeneficiarioService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.service.utils.ValidacaoVendaUtils;

@Stateless
public class BeneficiarioService extends GenericCrudServiceImpl<Beneficiario>
		implements IBeneficiarioService<Beneficiario> {

	@Inject
	private IBeneficiarioDAO dao;
	
	@EJB
	private IBeneficiarioPlanoService<BeneficiarioPlano> beneficiarioPlanoService;
	
	@EJB
	private ValidacaoVendaUtils validacaoVendaUtils;

	@Override
	protected IBeneficiarioDAO getDAO() {
		return dao;
	}

	@Override
	public ServiceResponse salvarOuAtualizar(Beneficiario beneficiario,
			String usuarioHost, String usuarioLogado) throws ServiceException {

		setLogPojo(beneficiario, usuarioHost, usuarioLogado);
		beneficiario.setLogTransaction(BigInteger.valueOf(System.currentTimeMillis()));
		
		Set<BeneficiarioPlano> beneficiarioPlanoList = beneficiario.getBeneficiarioPlanoList();
		ServiceResponse serviceResponse = super.saveOrUpdate(beneficiario);
		beneficiario = (Beneficiario) serviceResponse.getData();
		
		beneficiario.setBeneficiarioPlanoList(beneficiarioPlanoList);
		//Plano plano = null;
		//Produto produto = null;
		
		for(BeneficiarioPlano beneficiarioPlano: beneficiario.getBeneficiarioPlanoList()){
			//plano = beneficiarioPlano.getPlano();
			beneficiarioPlano.setBeneficiario(beneficiario);
			beneficiarioPlanoService.saveOrUpdate(beneficiarioPlano);
			//beneficiarioPlano.setPlano(plano);
		}
		return serviceResponse;
	}

	@Override
	protected void antesSalvar(Beneficiario beneficiario)
			throws ServiceException {
		validacaoVendaUtils.validarDependente(beneficiario.getVenda(), beneficiario);
	}

	@Override
	protected void antesExcluir(Beneficiario beneficiario)
			throws ServiceException {

		validarVazio(beneficiario, "Informe o Beneficiario.");
		validarVazio(beneficiario.getId(), "Informe o Id do Beneficiario.");
	}
	
	@Override
	public ServiceResponse remover(Beneficiario beneficiario)
			throws ServiceException {
		
		if(CollectionUtils.isEmpty(beneficiario.getBeneficiarioPlanoList())){
			mensagemErro("Informe o Plano.");
		}
		for (BeneficiarioPlano beneficiarioPlano : beneficiario.getBeneficiarioPlanoList()) {
			beneficiarioPlanoService.delete(beneficiarioPlano);
		}
		beneficiario.setBeneficiarioPlanoList(null);
		return super.delete(beneficiario);
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
