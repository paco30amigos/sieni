/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.DualListModel;
import utils.Combo;
import utils.RandomNombre;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author francisco_medina
 */
@ManagedBean
public class PlantillaClaseForm {

    //listas
    private List<Combo> docentes;
    private List<Combo> mostrarSecciones;
    private DualListModel<Combo> elementos;
    //valores seleccionados
    private Combo docente;
    private String titulo;
    private String mostrarSeccion;

    @PostConstruct
    private void init() {
//        org.primefaces.rio.convert a;
        docentes = new ArrayList<>();
        RandomNombre rn = new RandomNombre();
        StringBuilder s = new StringBuilder();
        docentes.add(new Combo("" + -1, "XX--100--XX", null));
        for (int i = 0; i < 10; i++) {
            docentes.add(new Combo("" + i, rn.getRandomNombre(), null));
        }
        elementos = new DualListModel<>();
        List<Combo> noSelec = new ArrayList<>();
        noSelec.add(new Combo("0", "Tema", null));
        noSelec.add(new Combo("1", "Descripción", null));
        noSelec.add(new Combo("2", "Objetivos", null));
        noSelec.add(new Combo("3", "Ejemplo", null));
        noSelec.add(new Combo("4", "Desarrollo de tema", null));
        noSelec.add(new Combo("5", "Ejercicio", null));
//        noSelec.add(new Combo("6", "Ejemplo 2", null));
//        noSelec.add(new Combo("7", "Ejercicio 2", null));
//        noSelec.add(new Combo("8", "Desarrollo de tema 2", null));
//        noSelec.add(new Combo("9", "Ejemplo 3", null));
//        noSelec.add(new Combo("10", "Ejercicio 3", null));
        noSelec.add(new Combo("11", "Concluciones", null));
        noSelec.add(new Combo("12", "Recomendaciones", null));
        noSelec.add(new Combo("13", "Tarea", null));
        noSelec.add(new Combo("14", "Examen", null));
        elementos.setSource(noSelec);

        mostrarSecciones = new ArrayList<>();
        mostrarSecciones.add(new Combo("1", "Si", null));
        mostrarSecciones.add(new Combo("0", "No", null));
    }

    public List<Combo> getDocentes() {
        return docentes;
    }

    public void setDocentes(List<Combo> docentes) {
        this.docentes = docentes;
    }

    public DualListModel<Combo> getElementos() {
        return elementos;
    }

    public void setElementos(DualListModel<Combo> elementos) {
        this.elementos = elementos;
    }

    public Combo getDocente() {
        return docente;
    }

    public void setDocente(Combo docente) {
        this.docente = docente;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Combo> getMostrarSecciones() {
        return mostrarSecciones;
    }

    public void setMostrarSecciones(List<Combo> mostrarSecciones) {
        this.mostrarSecciones = mostrarSecciones;
    }

    public String getMostrarSeccion() {
        return mostrarSeccion;
    }

    public void setMostrarSeccion(String mostrarSeccion) {
        this.mostrarSeccion = mostrarSeccion;
    }

}
