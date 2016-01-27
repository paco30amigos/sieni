/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.util.List;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import sv.com.mined.sieni.model.SieniArchivo;
import sv.com.mined.sieni.model.SieniClase;
import sv.com.mined.sieni.model.SieniCurso;
import sv.com.mined.sieni.model.SieniDocente;
import sv.com.mined.sieni.pojos.controller.Combo;
import utils.siteUrls;

/**
 *
 * @author francisco_medina
 */
public class ProgramacionClasesForm {

    private int indexMenu;
    private SieniClase eliminar;
    private SieniClase ver;

    // consulta de archivo
    private List<SieniClase> clasesList;
    private List<SieniClase> listDatosFiltered;
    //registro de archivo
    private SieniClase nuevo;
    private SieniDocente docente;
    private List<SieniDocente> docentesList;
    private List<SieniCurso> cursosList;
    private List<Combo> horarios;
    private List<String> horarioSelected;
    //modificacion de archivo
    private SieniClase modifica;
    private SieniDocente docenteModifica;
    private List<SieniDocente> docentesModificaList;
    private List<SieniCurso> cursosModificaList;
    private List<Combo> horariosModifica;
    private List<Combo> horarioModificaSelected;

    public int getIndexMenu() {
        return indexMenu;
    }

    public void setIndexMenu(int indexMenu) {
        siteUrls sU = new siteUrls();
        switch (indexMenu) {
            case 0:
                sU.redirect(sU.getBaseprogramacionClases() + "index.xhtml");
                break;
            case 1:
                sU.redirect(sU.getBaseprogramacionClases() + "crear.xhtml");
                break;
            case 2:
                sU.redirect(sU.getBaseprogramacionClases() + "editar.xhtml");
                break;
            case 3:
                sU.redirect(sU.getBaseprogramacionClases() + "ver.xhtml");
                break;
        }
        this.indexMenu = indexMenu;
    }

    public SieniClase getNuevo() {
        return nuevo;
    }

    public void setNuevo(SieniClase nuevo) {
        this.nuevo = nuevo;
    }

    public SieniDocente getDocente() {
        return docente;
    }

    public void setDocente(SieniDocente docente) {
        this.docente = docente;
    }

    public List<SieniDocente> getDocentesList() {
        return docentesList;
    }

    public void setDocentesList(List<SieniDocente> docentesList) {
        this.docentesList = docentesList;
    }

    public List<SieniCurso> getCursosList() {
        return cursosList;
    }

    public void setCursosList(List<SieniCurso> cursosList) {
        this.cursosList = cursosList;
    }

    public List<Combo> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Combo> horarios) {
        this.horarios = horarios;
    }

    public List<String> getHorarioSelected() {
        return horarioSelected;
    }

    public void setHorarioSelected(List<String> horarioSelected) {
        this.horarioSelected = horarioSelected;
    }

    public SieniClase getEliminar() {
        return eliminar;
    }

    public void setEliminar(SieniClase eliminar) {
        this.eliminar = eliminar;
    }

    public SieniClase getVer() {
        return ver;
    }

    public void setVer(SieniClase ver) {
        this.ver = ver;
    }

    public List<SieniClase> getClasesList() {
        return clasesList;
    }

    public void setClasesList(List<SieniClase> clasesList) {
        this.clasesList = clasesList;
    }

    public SieniClase getModifica() {
        return modifica;
    }

    public void setModifica(SieniClase modifica) {
        this.modifica = modifica;
    }

    public SieniDocente getDocenteModifica() {
        return docenteModifica;
    }

    public void setDocenteModifica(SieniDocente docenteModifica) {
        this.docenteModifica = docenteModifica;
    }

    public List<SieniDocente> getDocentesModificaList() {
        return docentesModificaList;
    }

    public void setDocentesModificaList(List<SieniDocente> docentesModificaList) {
        this.docentesModificaList = docentesModificaList;
    }

    public List<SieniCurso> getCursosModificaList() {
        return cursosModificaList;
    }

    public void setCursosModificaList(List<SieniCurso> cursosModificaList) {
        this.cursosModificaList = cursosModificaList;
    }

    public List<Combo> getHorariosModifica() {
        return horariosModifica;
    }

    public void setHorariosModifica(List<Combo> horariosModifica) {
        this.horariosModifica = horariosModifica;
    }

    public List<Combo> getHorarioModificaSelected() {
        return horarioModificaSelected;
    }

    public void setHorarioModificaSelected(List<Combo> horarioModificaSelected) {
        this.horarioModificaSelected = horarioModificaSelected;
    }

    public List<SieniClase> getListDatosFiltered() {
        return listDatosFiltered;
    }

    public void setListDatosFiltered(List<SieniClase> listDatosFiltered) {
        this.listDatosFiltered = listDatosFiltered;
    }

}
