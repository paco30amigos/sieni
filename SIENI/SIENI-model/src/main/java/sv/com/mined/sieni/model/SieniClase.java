/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Laptop
 */
@Entity
@Table(name = "sieni_clase")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniClase.findAll", query = "SELECT s FROM SieniClase s"),
    @NamedQuery(name = "SieniClase.findClaseByTipo", query = "SELECT s FROM SieniClase s where s.clEstado not in (:estado) and s.clTipo=:tipoClase ORDER BY s.idClase"),
    @NamedQuery(name = "SieniClase.findClaseByTipoAlumno", query = "SELECT s FROM SieniClase s,SieniAlumno al, SieniMatricula m where al.idAlumno=m.idAlumno and m.idGrado.idGrado=s.idCurso.idGrado.idGrado and m.idAlumno=al.idAlumno and s.clEstado not in (:estado) and s.clTipo=:tipoClase and al.idAlumno=:idAlumno  ORDER BY s.idClase"),
    @NamedQuery(name = "SieniClase.findClaseByAlumno", query = "SELECT s FROM SieniMatricula m,SieniClase s join fetch s.idCurso.sieniCursoAlumnoList al where m.idAlumno=al.idAlumno and m.idGrado.idGrado=s.idCurso.idGrado.idGrado and s.clEstado not in (:estado) and al.idAlumno=:idAlumno order by s.idClase"),
//    @NamedQuery(name = "SieniClase.findClaseByAlumno", query = "SELECT s FROM SieniClase s,SieniAlumno al join fetch al.sieniMatriculaList m where m.idGrado.idGrado=s.idCurso.idGrado.idGrado and m.idAlumno.idAlumno=al.idAlumno and s.clEstado not in (:estado) and al.idAlumno=:idAlumno"),
    @NamedQuery(name = "SieniClase.findAllNoInactivos", query = "SELECT s FROM SieniClase s where s.clEstado not in (:estado) order by s.idClase"),
    @NamedQuery(name = "SieniClase.findByIdClase", query = "SELECT s FROM SieniClase s WHERE s.idClase = :idClase"),
    @NamedQuery(name = "SieniClase.findByClHorario", query = "SELECT s FROM SieniClase s WHERE s.clHorario = :clHorario"),
    @NamedQuery(name = "SieniClase.findByClEstado", query = "SELECT s FROM SieniClase s WHERE s.clEstado = :clEstado"),
    @NamedQuery(name = "SieniClase.findByClTipo", query = "SELECT s FROM SieniClase s WHERE s.clTipo = :clTipo"),
    
    @NamedQuery(name = "SieniClase.findClasesRpt", query = "SELECT s FROM SieniClase s WHERE s.clEstado != 'I'"),
    @NamedQuery(name = "SieniClase.findClasesRptByTipoEstado", query = "SELECT s FROM SieniClase s WHERE s.clTipo = :clTipo AND s.clEstado = :clEstado"),
    @NamedQuery(name = "SieniClase.findClasesRptByEstado", query = "SELECT s FROM SieniClase s WHERE s.clEstado = :clEstado"),
    @NamedQuery(name = "SieniClase.findClasesRptByTipo", query = "SELECT s FROM SieniClase s WHERE s.clTipo = :clTipo AND s.clEstado != 'I'"),
    @NamedQuery(name = "SieniClase.findClasesRptByCurso", query = "SELECT s FROM SieniClase s WHERE s.clEstado != 'I' AND s.idCurso.idCurso = :idCurso"),
    @NamedQuery(name = "SieniClase.findClasesRptByCursoTipoEstado", query = "SELECT s FROM SieniClase s WHERE s.clTipo = :clTipo AND s.clEstado = :clEstado AND s.idCurso.idCurso = :idCurso"),
    @NamedQuery(name = "SieniClase.findClasesRptByCursoEstado", query = "SELECT s FROM SieniClase s WHERE s.clEstado = :clEstado AND s.idCurso.idCurso = :idCurso"),
    @NamedQuery(name = "SieniClase.findClasesRptByCursoTipo", query = "SELECT s FROM SieniClase s WHERE s.clTipo = :clTipo AND s.clEstado != 'I' AND s.idCurso.idCurso = :idCurso") ,

    @NamedQuery(name = "SieniClase.rptAvanceClases", query = "SELECT s FROM SieniClase s JOIN FETCH s.sieniCatPuntosList pt JOIN FETCH s.sieniPntosContrlList pa WHERE s.clEstado = 'A' ") })
public class SieniClase implements Serializable {

    @OneToMany(mappedBy = "idClase")
    private List<SieniClaseDocente> sieniClaseDocenteList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sec_sieni_clase")
    @SequenceGenerator(name = "sec_sieni_clase", initialValue = 1, allocationSize = 1, sequenceName = "sec_sieni_clase")
    @Basic(optional = false)
    @Column(name = "id_clase")
    private Long idClase;
    @Column(name = "cl_horario")
    private String clHorario;
    @Column(name = "cl_estado")
    private Character clEstado;
    @Column(name = "cl_tipo")
    private Character clTipo;
    @Column(name = "cl_tipo_publicacion")
    private Character clTipoPublicacion;
    @Column(name = "cl_ancho")
    private Integer clAncho;
    @Column(name = "cl_alto")
    private Integer clAlto;
    @Column(name = "cl_tema")
    private String clTema;
    @Column(name = "cl_hora")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date clHora;
    @OneToMany(mappedBy = "idClase")
    private List<SieniCatPuntos> sieniCatPuntosList;
    @OneToMany(mappedBy = "idClase")
    private List<SieniPntosContrl> sieniPntosContrlList;
    @OneToMany(mappedBy = "idClase")
    private List<SieniClaseSupComp> sieniClaseSupCompList;
    @JoinColumn(name = "id_curso", referencedColumnName = "id_curso")
    @ManyToOne
    private SieniCurso idCurso;
    @JoinColumn(name = "id_plantilla", referencedColumnName = "id_plantilla")
    @ManyToOne
    private SieniPlantilla idPlantilla;
    @Column(name = "id_video")
    private Long idArchivo;
    @Transient
    private String estado;

    public SieniClase() {
    }

    public SieniClase(Long idClase) {
        this.idClase = idClase;
    }

    public Long getIdClase() {
        return idClase;
    }

    public void setIdClase(Long idClase) {
        this.idClase = idClase;
    }

    public String getClHorario() {
        return clHorario;
    }

    public void setClHorario(String clHorario) {
        this.clHorario = clHorario;
    }

    public Character getClEstado() {
        return clEstado;
    }

    public void setClEstado(Character clEstado) {
        this.clEstado = clEstado;
    }

    public Character getClTipo() {
        return clTipo;
    }

    public void setClTipo(Character clTipo) {
        this.clTipo = clTipo;
    }

    @XmlTransient
    public List<SieniCatPuntos> getSieniCatPuntosList() {
        return sieniCatPuntosList;
    }

    public void setSieniCatPuntosList(List<SieniCatPuntos> sieniCatPuntosList) {
        this.sieniCatPuntosList = sieniCatPuntosList;
    }

    
    
    @XmlTransient
    public List<SieniPntosContrl> getSieniPntosContrlList() {
        return sieniPntosContrlList;
    }

    public void setSieniPntosContrlList(List<SieniPntosContrl> sieniPntosContrlList) {
        this.sieniPntosContrlList = sieniPntosContrlList;
    }

    @XmlTransient
    public List<SieniClaseSupComp> getSieniClaseSupCompList() {
        return sieniClaseSupCompList;
    }

    public void setSieniClaseSupCompList(List<SieniClaseSupComp> sieniClaseSupCompList) {
        this.sieniClaseSupCompList = sieniClaseSupCompList;
    }

    public SieniCurso getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(SieniCurso idCurso) {
        this.idCurso = idCurso;
    }

    public SieniPlantilla getIdPlantilla() {
        return idPlantilla;
    }

    public void setIdPlantilla(SieniPlantilla idPlantilla) {
        this.idPlantilla = idPlantilla;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idClase != null ? idClase.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniClase)) {
            return false;
        }
        SieniClase other = (SieniClase) object;
        if ((this.idClase == null && other.idClase != null) || (this.idClase != null && !this.idClase.equals(other.idClase))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniClase[ idClase=" + idClase + " ]";
    }

    public String getClTema() {
        return clTema;
    }

    public void setClTema(String clTema) {
        this.clTema = clTema;
    }

    public String getEstado() {
        switch (clEstado) {
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

    public String getTipo() {
        String tipo = "";
        switch (clTipo) {
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

    public String getTipoPublicacion() {
        String tipo = "";
        switch (clTipoPublicacion) {
            case 'A':
                tipo = "Automática";
                break;
            case 'M':
                tipo = "Manual";
                break;
        }
        return tipo;
    }

    public String getNombrePlantilla() {
        String ret = "Plantilla no seleccionada";
        if (idPlantilla != null && idPlantilla.getIdPlantilla() != null) {
            ret = idPlantilla.getPlNombre();
        }
        return ret;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<SieniClaseDocente> getSieniClaseDocenteList() {
        return sieniClaseDocenteList;
    }

    public void setSieniClaseDocenteList(List<SieniClaseDocente> sieniClaseDocenteList) {
        this.sieniClaseDocenteList = sieniClaseDocenteList;
    }

    public Date getClHora() {
        return clHora;
    }

    public void setClHora(Date clHora) {
        this.clHora = clHora;
    }

    public Character getClTipoPublicacion() {
        return clTipoPublicacion;
    }

    public void setClTipoPublicacion(Character clTipoPublicacion) {
        this.clTipoPublicacion = clTipoPublicacion;
    }

    public Integer getClAncho() {
        return clAncho;
    }

    public void setClAncho(Integer clAncho) {
        this.clAncho = clAncho;
    }

    public Integer getClAlto() {
        return clAlto;
    }

    public void setClAlto(Integer clAlto) {
        this.clAlto = clAlto;
    }

    public Long getIdArchivo() {
        return idArchivo;
    }

    public void setIdArchivo(Long idArchivo) {
        this.idArchivo = idArchivo;
    }

    public Integer getPtosTotales() {
         Integer puntos = 0;
         try{
            if(this.getSieniCatPuntosList()!= null){
                for(SieniCatPuntos actual : this.getSieniCatPuntosList()){
                    puntos = puntos + actual.getCpNumPuntos();
                }
            }
        }catch(Exception ex){}
        return puntos;
    }
    
    
    public Integer getPtosAcumulados() {
         Integer puntos = 0;
         try{
            if(this.getSieniPntosContrlList() != null){
                for(SieniPntosContrl actual : this.getSieniPntosContrlList()){
                    puntos = puntos + actual.getPcPantalla();
                }
            }
        }catch(Exception ex){}
        return puntos;
    }
     

}
