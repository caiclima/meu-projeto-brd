package br.com.callink.bradesco.seguro.dao;

import java.math.BigInteger;
import java.util.List;

import br.com.callink.bradesco.seguro.entity.Rejeicao;

/**
 * Data Access Object (DAO) de Rejeicao
 * 
 * @author neppo.oldamar
 *
 */
public interface IRejeicaoDAO extends IGenericDAO<Rejeicao> {

    List<Rejeicao> findRejeicaoByVenda(BigInteger idVenda);
    
}
