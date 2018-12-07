package br.com.callink.bradesco.seguro.service.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.callink.bradesco.seguro.dao.ICoberturaDAO;
import br.com.callink.bradesco.seguro.entity.Cobertura;
import br.com.callink.bradesco.seguro.service.ICoberturaService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

@Stateless
public class CoberturaService extends GenericCrudServiceImpl<Cobertura>
		implements ICoberturaService<Cobertura> {

	@Inject
	private ICoberturaDAO dao;

	@Override
	protected ICoberturaDAO getDAO() {
		return dao;
	}

	@Override
	public ServiceResponse salvar(Cobertura cobertura, String usuarioHost,
			String usuarioLogado) throws ServiceException {
		
		setLogPojo(cobertura, usuarioHost, usuarioLogado);
		return super.save(cobertura);
	}
	
	@Override
	protected void antesSalvar(Cobertura cobertura) throws ServiceException {
		
		cobertura.setFlagRemoved(Boolean.FALSE);
		validarVazio(cobertura.getGrauParentesco(),
				"Informe o Grau Parentesco para a Cobertura.");
		validarVazio(cobertura.getGrauParentesco().getId(),
				"Informe o Grau Parentesco para a Cobertura.");
		cobertura.setIdGrauParentesco(cobertura.getGrauParentesco().getId());
		validarVazio(cobertura.getIdPlano(), "Informe o Plano");
		validarVazio(cobertura.getValorDemaisAcidentes(),
				"Informe o Valor demais Acidentes para a Cobertura.");
		validarVazio(cobertura.getValorDIHMotivoAcidentes(),
				"Informe o Valor DIH para a Cobertura.");
		validarVazio(cobertura.getValorSorteio(),
				"Informe o Valor Sorteio para a Cobertura.");
		validarVazio(cobertura.getValorTransporteColetivo(),
				"Informe o Valor Transporte Coletivo para a Cobertura.");
		validarVazio(cobertura.getValorVeiculoParticularTaxisPedestres(),
				"Informe o Valor Veic. Part. Taxis e Pedestres para a Cobertura.");
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
