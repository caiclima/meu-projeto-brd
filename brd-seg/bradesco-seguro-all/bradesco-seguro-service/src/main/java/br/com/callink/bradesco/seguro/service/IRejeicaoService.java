/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.callink.bradesco.seguro.service;

import java.math.BigInteger;
import java.util.List;

import br.com.callink.bradesco.seguro.entity.Rejeicao;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;

/**
 *
 * @author rogerio_moreira
 */
public interface IRejeicaoService<T> extends IGenericCrudService<T> {

    List<Rejeicao> findRejeicaoByVenda(BigInteger idVenda) throws ServiceException;
}
