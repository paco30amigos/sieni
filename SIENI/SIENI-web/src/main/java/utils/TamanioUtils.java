/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javax.faces.bean.ManagedBean;

/**
 *
 * @author francisco_medina
 */
@ManagedBean(name = "tamanioUtils")
public class TamanioUtils {

    private final int direccionMax = 200;
    private final int nombreMax = 50;
    private final int apellidoMax = 50;
    private final int emailMax = 200;
    private final Integer imagenMax = (25 * 1024 * 1024);
    private final Integer audioMax = (50 * 1024 * 1024);
    private final Integer videoMax = (500 * 1024 * 1024);
    private final String nombreArchivoMax = "100";
    private final int nombreSuperCompMax = 100;
    private final int descSuperCompMax = 200;
    private final int nombrePlantillaMax = 100;

    public int getDireccionMax() {
        return direccionMax;
    }

    public int getNombreMax() {
        return nombreMax;
    }

    public int getApellidoMax() {
        return apellidoMax;
    }

    public int getEmailMax() {
        return emailMax;
    }

    public Integer getImagenMax() {
        return imagenMax;
    }

    public Integer getAudioMax() {
        return audioMax;
    }

    public Integer getVideoMax() {
        return videoMax;
    }

    public String getNombreArchivoMax() {
        return nombreArchivoMax;
    }

    public int getNombreSuperCompMax() {
        return nombreSuperCompMax;
    }

    public int getDescSuperCompMax() {
        return descSuperCompMax;
    }

    public int getNombrePlantillaMax() {
        return nombrePlantillaMax;
    }

}
