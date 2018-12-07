package br.com.callink.bradesco.seguro.service.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.logging.Level;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import br.com.callink.bradesco.seguro.commons.utils.CollectionUtils;
import br.com.callink.bradesco.seguro.dao.IProdutoEventoDAO;
import br.com.callink.bradesco.seguro.dao.exception.DataException;
import br.com.callink.bradesco.seguro.entity.Evento;
import br.com.callink.bradesco.seguro.entity.Produto;
import br.com.callink.bradesco.seguro.entity.ProdutoEvento;
import br.com.callink.bradesco.seguro.service.IProdutoEventoService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.exception.ValidationException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponseFactory;

@Stateless
@Local(IProdutoEventoService.class)
public class ProdutoEventoService extends GenericCrudServiceImpl<ProdutoEvento> implements IProdutoEventoService {
	
	@Inject
	private IProdutoEventoDAO produtoEventoDAO;
	
	@Override
	protected IProdutoEventoDAO getDAO() {
		return produtoEventoDAO;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteEventoProdutoByEvento(Evento evento) throws ServiceException, ValidationException {
		
		if(evento != null && evento.getId() != null){
			try {
				getDAO().deleteEventoProdutoByEvento(evento);
			} catch (DataException e) {
				throw new ServiceException(e);
			}
		} else {
			throw new ValidationException("Para esta ação não foi escolhido um produto válido.");
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void associarProdutosNoEvento(Evento evento) throws ServiceException {
		
		try {
			
			if (evento == null || evento.getId() == null) {
				throw new ServiceException("O evento selecionado é inválido");
			}
			
			getDAO().deleteEventoProdutoByEvento(evento);
			
			for(ProdutoEvento produtoEventoObject : evento.getProdutoEventoList()){
				if(produtoEventoObject != null){
					getDAO().saveOrUpdate(produtoEventoObject);
				}
			}
			
		} catch (DataException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public ServiceResponse associarEventoProduto(Evento evento, String usuarioHost, String usuarioLogado) throws ServiceException {
		
		ServiceResponse serviceResponse = null;
		if (evento != null && evento.getId() != null && evento.getId().compareTo(BigInteger.ZERO) != 0 ) {
			
			getDAO().deleteEventoProdutoByEvento(evento);
			
			if (!CollectionUtils.isEmpty(evento.getProdutoEventoList())){
				for (ProdutoEvento produtoEvento: evento.getProdutoEventoList()){
					setLogPojo(produtoEvento, usuarioHost, usuarioLogado);
					produtoEvento.setLogTransaction(BigInteger.valueOf(System.currentTimeMillis()));
					getDAO().save(produtoEvento);
				}
			}
		}
		serviceResponse = ServiceResponseFactory.createWithData(evento.getProdutoEventoList());
		serviceResponse.addInfo("Associação salva com sucesso.");
		return serviceResponse;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ServiceResponse desAssociarEventoProduto(Evento evento, Produto produto, String usuarioHost, String usuarioLogado) throws ServiceException {
		
		
		ProdutoEvento produtoEvento = new ProdutoEvento(produto, evento);
		ServiceResponse serviceResponse = this.findByExample(produtoEvento);
		List<ProdutoEvento> produtoEventoList = (List<ProdutoEvento>) serviceResponse.getData();
		if (!CollectionUtils.isEmpty(produtoEventoList)) {
			for (ProdutoEvento produtoEventoItem : produtoEventoList) {
				getDAO().delete(produtoEventoItem);
			}
		} else  {
			throw new ServiceException("Nenhum registro encontrado.");
		}
		serviceResponse.addInfo("Produto evento desassociado.");
		return serviceResponse;
	}

	@Override
	public ServiceResponse salvarProdutoEvento(List<ProdutoEvento> lst) throws ServiceException {
		if(lst != null && lst.size() > 0){
			for (ProdutoEvento produtoEvento : lst) {
				this.save(produtoEvento);
			}
		}
		ServiceResponse response = ServiceResponseFactory.createWithData(Level.INFO);
		response.addInfo("Registro salvo com sucesso.");
		return response;
	}

	@Override
	public ServiceResponse buscarPorEvento(Evento evento)
			throws ServiceException {
		
		ServiceResponse serviceResponse = null;
		if (evento != null && evento.getId() != null){
			serviceResponse = ServiceResponseFactory.createWithData(getDAO().buscarPorEvento(evento));
		} else {
			throw new ServiceException("Informe o Evento.");
		}
		return serviceResponse;
	}

}
