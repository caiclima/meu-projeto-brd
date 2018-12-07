package br.com.callink.bradesco.seguro.service;

import br.com.callink.bradesco.seguro.entity.Rejeicao;
import br.com.callink.bradesco.seguro.entity.TelefoneCliente;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import br.com.callink.bradesco.seguro.entity.Venda;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;
import br.com.callink.bradesco.seguro.service.utils.ServiceResponse;

public interface IVendaService<T> extends IGenericCrudService<T> {

	ServiceResponse buscarTodasVendas() throws ServiceException;

	ServiceResponse buscarVendaPorId(BigInteger idVenda)
			throws ServiceException;

	ServiceResponse buscarPorExemplo(Venda venda) throws ServiceException;

	ServiceResponse salvarOuAtualizar(Venda venda, String usuarioHost, String usuarioLogado, String chaveAtendimento, String telefone, Rejeicao rejeicao, List<TelefoneCliente> telefones)
			throws ServiceException;
	
	ServiceResponse finalizarVenda(Venda venda, Rejeicao rejeicao, String usuarioHost, String usuarioLogado, String chaveAtendimento, String telefone,  List<TelefoneCliente> telefones)
			throws ServiceException;

	void updateDataEnvioNuvem(Date dataEnvio) throws ServiceException;

}
