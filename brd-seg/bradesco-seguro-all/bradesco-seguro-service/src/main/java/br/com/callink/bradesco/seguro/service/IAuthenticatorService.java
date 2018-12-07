/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.callink.bradesco.seguro.service;

import br.com.callink.bradesco.seguro.service.exception.ServiceException;

/**
 *
 * @author rogerio_moreira
 */
public interface IAuthenticatorService {

    void authenticaAD(String usuario, String senha) throws ServiceException;
    
}
