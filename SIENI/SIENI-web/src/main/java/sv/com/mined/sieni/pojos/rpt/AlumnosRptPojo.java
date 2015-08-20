/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.pojos.rpt;

import java.io.Serializable;

/**
 *
 * @author Laptop
 */
public class AlumnosRptPojo implements Serializable {

    private String al_prim_nombre;
    private String al_segu_nombre;
    private String al_terc_nombre;
    private String al_prim_ape;
    private String al_segu_ape;
    private String al_terc_ape;

    public String getAl_prim_nombre() {
        return al_prim_nombre;
    }

    public void setAl_prim_nombre(String al_prim_nombre) {
        this.al_prim_nombre = al_prim_nombre;
    }

    public String getAl_segu_nombre() {
        return al_segu_nombre;
    }

    public void setAl_segu_nombre(String al_segu_nombre) {
        this.al_segu_nombre = al_segu_nombre;
    }

    public String getAl_terc_nombre() {
        return al_terc_nombre;
    }

    public void setAl_terc_nombre(String al_terc_nombre) {
        this.al_terc_nombre = al_terc_nombre;
    }

    public String getAl_prim_ape() {
        return al_prim_ape;
    }

    public void setAl_prim_ape(String al_prim_ape) {
        this.al_prim_ape = al_prim_ape;
    }

    public String getAl_segu_ape() {
        return al_segu_ape;
    }

    public void setAl_segu_ape(String al_segu_ape) {
        this.al_segu_ape = al_segu_ape;
    }

    public String getAl_terc_ape() {
        return al_terc_ape;
    }

    public void setAl_terc_ape(String al_terc_ape) {
        this.al_terc_ape = al_terc_ape;
    }

}
