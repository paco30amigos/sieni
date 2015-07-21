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
public class crearClaseForm {

    //listas
    private List<Combo> docentes;
    private List<Combo> tipoClases;
    private List<Combo> cursos;
    private List<Combo> estadoClases;
    private List<Combo> horarios;
    private List<Combo> tipoPublicaciones;
    private List<Combo> amPms;
    //valores seleccionados
    private Combo docente;
    private Combo tipoClase;
    private Combo curso;
    private Combo estadoClase;
    private String temaClase;
    private String hora;
    private List<Combo> horariosSelected;
    private Combo tipoPublicacion;
    private Combo amPm;

    @PostConstruct
    private void init() {
        docentes = new ArrayList<>();
        RandomNombre rn = new RandomNombre();
        StringBuilder s = new StringBuilder();
        docentes.add(new Combo("" + -1, "XX--100--XX", null));
        for (int i = 0; i < 10; i++) {
            docentes.add(new Combo("" + i, rn.getRandomNombre(), null));
        }

        horarios = new ArrayList<>();
        horarios.add(new Combo("0", "Lunes", null));
        horarios.add(new Combo("1", "Martes", null));
        horarios.add(new Combo("2", "Miercoles", null));
        horarios.add(new Combo("3", "Jueves", null));
        horarios.add(new Combo("4", "Viernes", null));

        amPms = new ArrayList<>();
        amPms.add(new Combo("-1", "Xm", null));
        amPms.add(new Combo("0", "am", null));
        amPms.add(new Combo("1", "pm", null));

        tipoClases = new ArrayList<>();
        tipoClases.add(new Combo("0", "Video-clase", null));
        tipoClases.add(new Combo("1", "Clase en linea", null));
        tipoClases.add(new Combo("2", "Clase interactiva", null));

        estadoClases = new ArrayList<>();
        estadoClases.add(new Combo("0", "Publicada", null));
        estadoClases.add(new Combo("1", "No Publicada", null));

        cursos = new ArrayList<>();
        cursos.add(new Combo("0", "Matemáticas", null));
        cursos.add(new Combo("1", "Ciencias Naturales", null));
        cursos.add(new Combo("2", "Sociales", null));
        cursos.add(new Combo("3", "Lenguaje", null));
        cursos.add(new Combo("4", "Ingles", null));
        cursos.add(new Combo("5", "Computacion", null));
        
        tipoPublicaciones = new ArrayList<>();
        tipoPublicaciones.add(new Combo("0", "Manual", null));
        tipoPublicaciones.add(new Combo("1", "Automático", null));

    }

    public List<Combo> getDocentes() {
        return docentes;
    }

    public void setDocentes(List<Combo> docentes) {
        this.docentes = docentes;
    }

    public List<Combo> getTipoClases() {
        return tipoClases;
    }

    public void setTipoClases(List<Combo> tipoClases) {
        this.tipoClases = tipoClases;
    }

    public List<Combo> getEstadoClases() {
        return estadoClases;
    }

    public void setEstadoClases(List<Combo> estadoClases) {
        this.estadoClases = estadoClases;
    }

    public String getTemaClase() {
        return temaClase;
    }

    public void setTemaClase(String temaClase) {
        this.temaClase = temaClase;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public List<Combo> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Combo> horarios) {
        this.horarios = horarios;
    }

    public List<Combo> getTipoPublicaciones() {
        return tipoPublicaciones;
    }

    public void setTipoPublicaciones(List<Combo> tipoPublicaciones) {
        this.tipoPublicaciones = tipoPublicaciones;
    }

    public Combo getDocente() {
        return docente;
    }

    public void setDocente(Combo docente) {
        this.docente = docente;
    }

    public Combo getTipoClase() {
        return tipoClase;
    }

    public void setTipoClase(Combo tipoClase) {
        this.tipoClase = tipoClase;
    }

    public List<Combo> getHorariosSelected() {
        return horariosSelected;
    }

    public void setHorariosSelected(List<Combo> horariosSelected) {
        this.horariosSelected = horariosSelected;
    }

    public Combo getTipoPublicacion() {
        return tipoPublicacion;
    }

    public void setTipoPublicacion(Combo tipoPublicacion) {
        this.tipoPublicacion = tipoPublicacion;
    }

    public Combo getEstadoClase() {
        return estadoClase;
    }

    public void setEstadoClase(Combo estadoClase) {
        this.estadoClase = estadoClase;
    }

    public Combo getAmPm() {
        return amPm;
    }

    public void setAmPm(Combo amPm) {
        this.amPm = amPm;
    }

    public List<Combo> getAmPms() {
        return amPms;
    }

    public void setAmPms(List<Combo> amPms) {
        this.amPms = amPms;
    }

    public List<Combo> getCursos() {
        return cursos;
    }

    public void setCursos(List<Combo> cursos) {
        this.cursos = cursos;
    }

    public Combo getCurso() {
        return curso;
    }

    public void setCurso(Combo curso) {
        this.curso = curso;
    }
}
