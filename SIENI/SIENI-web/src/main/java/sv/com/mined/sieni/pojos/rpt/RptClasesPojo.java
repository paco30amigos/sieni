/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.pojos.rpt;

import java.io.Serializable;
import java.util.Date;
import sv.com.mined.sieni.model.SieniClase;
import sv.com.mined.sieni.model.SieniCurso;

/**
 *
 * @author INFORMATICA
 */
public class RptClasesPojo implements Serializable {
    private SieniCurso cursoEntity;
    private String curso;
    private String tema;
    private Character tipo;
    private String tipoClase;
    private Date hora;
    private String horario;
    private Character estado;
    private String EstadoClase;

    
    public RptClasesPojo() {
    }

    
    public RptClasesPojo(SieniCurso cursoEntity, String tema, Character tipo, Date hora, String horario, Character estado) {
        this.cursoEntity = cursoEntity;
        this.tema = tema;
        this.tipo = tipo;
        this.hora = hora;
        this.horario = horario;
        this.estado = estado;
    }

    

    public SieniCurso getCursoEntity() {
        return cursoEntity;
    }

    public String getCurso() {
        curso = "";
        if(this.cursoEntity != null){
            curso = this.cursoEntity.getCrNombre();
        }
        return curso;
    }
    

    public String getTema() {
        return tema;
    }

    public String getTipoClase() {
        switch (this.tipo) {
            case 'O':
                tipoClase = "Clase en vivo";
                break;
            case 'V':
                tipoClase = "Video clase";
                break;
            case 'I':
                tipoClase = "Clase interactiva";
                break;
        }
        return tipoClase;
    }

    public Date getHora() {
        return hora;
    }

    public String getHorario() {
        return horario;
    }

    public String getEstadoClase() {
        switch (this.estado) {
            case 'N':
                EstadoClase = "No Iniciada";
                break;
            case 'A':
                EstadoClase = "Iniciada";
                break;
            case 'T':
                EstadoClase = "Terminada";
                break;
            case 'I':
                EstadoClase = "Eliminada";
                break;
        }
        return EstadoClase;
    }
    
    
    
    
    
}
