/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javax.faces.bean.ManagedBean;

/**
 *
 * @author Laptop
 */
@ManagedBean
public class nombreUtils {

    private final String nombreMax = "50";
    private final String apellidoMax = "50";

    public String getNombreMax() {
        return nombreMax;
    }

    public String getApellidoMax() {
        return apellidoMax;
    }

}
