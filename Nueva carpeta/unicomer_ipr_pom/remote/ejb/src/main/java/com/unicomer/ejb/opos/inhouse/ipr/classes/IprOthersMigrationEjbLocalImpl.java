/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.ejb.opos.inhouse.ipr.classes;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.unicomer.inhouse.jndi.JNDIUnicomerIpr;
import com.unicomer.inhouse.jndi.JNDIUnicomerOthers;
import com.unicomer.opos.inhouse.admiHoTh.ejbs.OthAuditoryEjbRemote;

/**
 *
 * @author francisco_medina
 */
@Stateless(name = "IprOthersMigrationEjbLocalImpl", mappedName = "ejb/IprOthersMigrationEjbLocalImpl")
@Remote(IprOthersMigrationEjbRemote.class)
public class IprOthersMigrationEjbLocalImpl implements
        IprOthersMigrationEjbRemote {

    @PersistenceContext(unitName = "IhIprPersistence")
    private EntityManager em;

    // obtener las transacciones de others y su informacion extra
    @Override
    public boolean getOtherTransaction(String countryCode) {
		// crear punto de auditoria
        // crear ipr transaction

		// crear linea de producto
		// crear punto de auditoria
        // crear historico de ubicacion para la tienda
        boolean process = false;
        try {
            HashMap<String, Object> params = new HashMap<String, Object>();

            String vAmbiente = "Development";
            String query = "{CALL ADMIHIPR.IPR_CONTROL.MIGRAR_DATOS_IPR(P_AMBIENTE,P_COUNTRY_CODE,?)}";
            query = query.replace("P_AMBIENTE", vAmbiente != null ? "'" + vAmbiente.toString() + "'" : "NULL");
            query = query.replace("P_COUNTRY_CODE", countryCode != null ? "'" + countryCode.toString() + "'" : "'GT'");

            String ret = new String();
            em.createNativeQuery(query)
                    .setParameter(1, ret)
                    .executeUpdate();
            process = true;
        } catch (Exception e) {
        }

        return process;
    }

	//
}
