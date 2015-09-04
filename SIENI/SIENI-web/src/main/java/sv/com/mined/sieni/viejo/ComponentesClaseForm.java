/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.viejo;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.DualListModel;
import sv.com.mined.sieni.pojos.controller.Combo;

/**
 *
 * @author bugtraq
 */
@ManagedBean
public class ComponentesClaseForm {

    //listas
    private List<Combo> seccionesPlantillas;
    private DualListModel<Combo> componentes;
    //seleccionados
    private Combo seccionPlantilla;

    @PostConstruct
    public void init(){
        componentes=new DualListModel<>();
        componentes.setSource(new ArrayList<Combo>());
        componentes.getSource().add(new Combo("0", "Documental Animales", null));
        componentes.getSource().add(new Combo("0", "Video Clase matemáticas", null));
        
        seccionesPlantillas=new ArrayList<>();
        seccionesPlantillas.add(new Combo("0", "Tema", null));
        seccionesPlantillas.add(new Combo("1", "Descripción del Tema", null));
    }
    public List<Combo> getSeccionesPlantillas() {
        return seccionesPlantillas;
    }

    public void setSeccionesPlantillas(List<Combo> seccionesPlantillas) {
        this.seccionesPlantillas = seccionesPlantillas;
    }

    public DualListModel<Combo> getComponentes() {
        return componentes;
    }

    public void setComponentes(DualListModel<Combo> componentes) {
        this.componentes = componentes;
    }

    public Combo getSeccionPlantilla() {
        return seccionPlantilla;
    }

    public void setSeccionPlantilla(Combo seccionPlantilla) {
        this.seccionPlantilla = seccionPlantilla;
    }

}
