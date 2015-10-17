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
import utils.FormatUtils;

/**
 *
 * @author INFORMATICA
 */
public class RptClasesPojo implements Serializable {
    private SieniCurso cursoEntity;
    private String curso;
    private String tema;
    private Character tipoClase;
    private String tipo;
    private Date hora;
    private String horatext;
    private String horario;
    private Character estadoClase;
    private String estado;

    
    public RptClasesPojo() {
    }

    
    public RptClasesPojo(SieniCurso cursoEntity, String tema, Character tipo, Date hora, String horario, Character estado) {
        this.cursoEntity = cursoEntity;
        this.tema = tema;
        this.tipoClase = tipo;
        this.hora = hora;
        this.horario = horario;
        this.estadoClase = estado;
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

    public String getTipo() {
        switch (this.tipoClase) {
            case 'O':
                tipo = "Clase en vivo";
                break;
            case 'V':
                tipo = "Video clase";
                break;
            case 'I':
                tipo = "Clase interactiva";
                break;
        }
        return tipo;
    }

    public Date getHora() {
        return hora;
    }

    public String getHorario() {
        return horario;
    }

    public String getEstado() {
        switch (this.estadoClase) {
            case 'N':
                estado = "No Iniciada";
                break;
            case 'A':
                estado = "Iniciada";
                break;
            case 'T':
                estado = "Terminada";
                break;
            case 'I':
                estado = "Eliminada";
                break;
        }
        return estado;
    }

    public String getHoratext() {
        horatext = new FormatUtils().getFormatedTime(this.hora);
        return horatext;
    }
    
    
    
    
    
    
}
