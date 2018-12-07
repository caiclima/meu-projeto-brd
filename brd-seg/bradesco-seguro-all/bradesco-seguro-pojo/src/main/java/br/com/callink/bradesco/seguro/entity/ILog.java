/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.callink.bradesco.seguro.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 *
 * @author neppo.oldamar
 */
public interface ILog extends Serializable{

    String getLogUid();

    void setLogUid(String logUid);

    String getLogHost();

    void setLogHost(String logHost);

    Date getLogDate();

    void setLogDate(Date logDate);

    String getLogSystem();

    void setLogSystem(String logSystem);

    String getLogObs();

    void setLogObs(String logObs);
    		
	BigInteger getLogTransaction();

	void setLogTransaction(BigInteger logTransaction);
}
