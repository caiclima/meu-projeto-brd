/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.callink.bradesco.seguro.service.impl;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.callink.bradesco.seguro.dao.IGenericDAO;
import br.com.callink.bradesco.seguro.dao.IRejeicaoDAO;
import br.com.callink.bradesco.seguro.entity.Rejeicao;
import br.com.callink.bradesco.seguro.service.IRejeicaoService;
import br.com.callink.bradesco.seguro.service.exception.ServiceException;

/**
 *
 * @author rogerio_moreira
 */
@Stateless
@Local
public class RejeicaoService extends GenericCrudServiceImpl<Rejeicao> implements IRejeicaoService<Rejeicao>{

    @Inject
    private IRejeicaoDAO rejeicaoDAO;

    @Override
    protected IGenericDAO<Rejeicao> getDAO() {
        return rejeicaoDAO;
    }

    @Override
    public List<Rejeicao> findRejeicaoByVenda(BigInteger idVenda) throws ServiceException {
        return rejeicaoDAO.findRejeicaoByVenda(idVenda);
    }
}
