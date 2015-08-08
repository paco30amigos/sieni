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
import utils.Combo;
import utils.RandomNombre;

/**
 *
 * @author bugtraq
 */
@ManagedBean
public class ClasesForm {

    //listas
    private List<Combo> docentes;
    private List<Combo> plantillas;
    private List<Combo> estilosPasoPagina;
    //seleccionados
    private Combo docente;
    private Combo plantilla;
    private Combo estiloPasoPagina;

    @PostConstruct
    public void init() {
        RandomNombre rn = new RandomNombre();
        StringBuilder s = new StringBuilder();
        docentes=new ArrayList<>();
        docentes.add(new Combo("" + -1, "XX--100--XX", null));
        for (int i = 0; i < 10; i++) {
            docentes.add(new Combo("" + i, rn.getRandomNombre(), null));
        }
        plantillas=new ArrayList<>();
        plantillas.add(new Combo("0", "Clase Matemáticas", null));
        plantillas.add(new Combo("1", "Clase Sociales", null));
        
        
        estilosPasoPagina=new ArrayList<>();
        estilosPasoPagina.add(new Combo("0", "Deslizante", null));
        estilosPasoPagina.add(new Combo("1", "Cubo 3D", null));
    }

    public List<Combo> getDocentes() {
        return docentes;
    }

    public void setDocentes(List<Combo> docentes) {
        this.docentes = docentes;
    }

    public List<Combo> getPlantillas() {
        return plantillas;
    }

    public void setPlantillas(List<Combo> plantillas) {
        this.plantillas = plantillas;
    }

    public List<Combo> getEstilosPasoPagina() {
        return estilosPasoPagina;
    }

    public void setEstilosPasoPagina(List<Combo> estilosPasoPagina) {
        this.estilosPasoPagina = estilosPasoPagina;
    }

    public Combo getDocente() {
        return docente;
    }

    public void setDocente(Combo docente) {
        this.docente = docente;
    }

    public Combo getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(Combo plantilla) {
        this.plantilla = plantilla;
    }

    public Combo getEstiloPasoPagina() {
        return estiloPasoPagina;
    }

    public void setEstiloPasoPagina(Combo estiloPasoPagina) {
        this.estiloPasoPagina = estiloPasoPagina;
    }

}
