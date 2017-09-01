/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.ejb.opos.inhouse.ipr.classes;

import javax.ejb.Remote;

/**
 *
 * @author francisco_medina
 */
@Remote
public interface IprSendDataToRiEjbRemote {
	public void sendDataToRi();
}
