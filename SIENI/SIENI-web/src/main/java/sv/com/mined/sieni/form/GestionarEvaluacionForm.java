/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import sv.com.mined.sieni.model.SieniCurso;
import sv.com.mined.sieni.model.SieniEvalRespItem;
import sv.com.mined.sieni.model.SieniEvaluacion;
import sv.com.mined.sieni.model.SieniEvaluacionItem;
import utils.siteUrls;

/**
 *
 * @author Ever
 */
public class GestionarEvaluacionForm {

    private int indexMenu;
    private SieniEvaluacion eliminar;
    private List<SieniEvaluacionItem> evaluacionItemList;
    // consulta de alumnos
    private List<SieniEvaluacion> evaluacionList;
    
    private SieniEvaluacion evaluacionItemResp;
    //registro de alumnos
    private SieniEvaluacion evaluacionNuevo;
    private SieniEvaluacionItem evaluacionItemNuevo;
    private SieniEvaluacionItem evaluacionItemModifica;
    private SieniEvaluacionItem evaluacionItemElimina;
    
   private List<TipoP> tipoPregunta;
   private Double totalPonderacion;

    private List<SieniEvalRespItem> evalRespItemList;
    private SieniEvalRespItem evalRespItemNuevo;
    private SieniEvalRespItem evalRespItemModifica;
    private SieniEvalRespItem evalRespItemElimina;
    private UploadedFile foto;
    private StreamedContent fotoUsable;
    private byte[] fotoArchivo;
    //modificacion de alumnos
    private SieniEvaluacion evaluacionModifica;
    private UploadedFile fotoModifica;
    private StreamedContent fotoUsableModifica;
    private byte[] fotoArchivoModifica;
    private Long idCurso;
    private List<SieniCurso> cursoList;

    public UploadedFile getFoto() {
        return foto;
    }

    public void setFoto(UploadedFile foto) {
        this.foto = foto;
    }

    public StreamedContent getImage(byte[] foto) {
        StreamedContent ret = null;
        if (foto != null) {
            InputStream input = new ByteArrayInputStream(foto);
            ret = new DefaultStreamedContent(input);
        }
        return ret;
    }

    public StreamedContent getFotoUsable() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            if (fotoUsable != null) {
                return new DefaultStreamedContent();
            } else {
                return null;
            }
        } else {
            if (fotoUsable != null) {
                return fotoUsable;
            } else {
                return null;
            }

        }
    }

    public void setFotoUsable(StreamedContent fotoUsable) {
        this.fotoUsable = fotoUsable;
    }

    public byte[] getFotoArchivo() {
        return fotoArchivo;
    }

    public Double getTotalPonderacion() {
        return totalPonderacion;
    }

    public void setTotalPonderacion(Double totalPonderacion) {
        this.totalPonderacion = totalPonderacion;
    }

    public void setFotoArchivo(byte[] fotoArchivo) {
        this.fotoArchivo = fotoArchivo;
    }

    public int getIndexMenu() {
        return indexMenu;
    }

    public Long getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Long idCurso) {
        this.idCurso = idCurso;
    }

    public List<SieniCurso> getCursoList() {
        return cursoList;
    }

    public void setCursoList(List<SieniCurso> cursoList) {
        this.cursoList = cursoList;
    }

    public List<SieniEvaluacionItem> getEvaluacionItemList() {
        return evaluacionItemList;
    }

    public void setEvaluacionItemList(List<SieniEvaluacionItem> evaluacionItemList) {
        this.evaluacionItemList = evaluacionItemList;
    }

    public SieniEvaluacion getEvaluacionItemResp() {
        return evaluacionItemResp;
    }

    public void setEvaluacionItemResp(SieniEvaluacion evaluacionItemResp) {
        this.evaluacionItemResp = evaluacionItemResp;
    }

    public void setIndexMenu(int indexMenu) {
        siteUrls sU = new siteUrls();
        switch (indexMenu) {
            case 0:
                sU.redirect(sU.getBasegestionEvaluacion() + "index.xhtml");
                break;
            case 1:
                sU.redirect(sU.getBasegestionEvaluacion() + "crear.xhtml");
                break;
            case 2:
                sU.redirect(sU.getBasegestionEvaluacion() + "editar.xhtml");
                break;
            case 3:
                sU.redirect(sU.getBasegestionEvaluacion() + "ver.xhtml");
                break;
            case 4:
                sU.redirect(sU.getBasegestionEvaluacion() + "indexItems.xhtml");
                break;
            case 5:
                sU.redirect(sU.getBasegestionEvaluacion() + "crearItem.xhtml");
                break; 
             case 6:
                sU.redirect(sU.getBasegestionEvaluacion() + "editarItem.xhtml");
                break;  
            case 7:
                sU.redirect(sU.getBasegestionEvaluacion() + "indexRespuesta.xhtml");
                break;  
            case 8:
                sU.redirect(sU.getBasegestionEvaluacion() + "crearRespuesta.xhtml");
                break;  
            case 9:
                sU.redirect(sU.getBasegestionEvaluacion() + "editarRespuesta.xhtml");
                break;    
            case 10:
                sU.redirect(sU.getBasegestionEvaluacion() + "verEvaluacion.xhtml");
                break;    
                
                   
                
        }
        this.indexMenu = indexMenu;
    }

    public UploadedFile getFotoModifica() {
        return fotoModifica;
    }

    public void setFotoModifica(UploadedFile fotoModifica) {
        this.fotoModifica = fotoModifica;
    }

    public StreamedContent getFotoUsableModifica() {
        return fotoUsableModifica;
    }

    public void setFotoUsableModifica(StreamedContent fotoUsableModifica) {
        this.fotoUsableModifica = fotoUsableModifica;
    }

    public byte[] getFotoArchivoModifica() {
        return fotoArchivoModifica;
    }

    public void setFotoArchivoModifica(byte[] fotoArchivoModifica) {
        this.fotoArchivoModifica = fotoArchivoModifica;
    }

    public SieniEvaluacion getEliminar() {
        return eliminar;
    }

    public void setEliminar(SieniEvaluacion eliminar) {
        this.eliminar = eliminar;
    }

    public List<SieniEvaluacion> getEvaluacionList() {
        return evaluacionList;
    }

    public void setEvaluacionList(List<SieniEvaluacion> evaluacionList) {
        this.evaluacionList = evaluacionList;
    }

    public SieniEvaluacion getEvaluacionNuevo() {
        return evaluacionNuevo;
    }

    public void setEvaluacionNuevo(SieniEvaluacion evalucionNuevo) {
        this.evaluacionNuevo = evalucionNuevo;
    }

    public SieniEvaluacion getEvaluacionModifica() {
        return evaluacionModifica;
    }

    public void setEvaluacionModifica(SieniEvaluacion evalucionModifica) {
        this.evaluacionModifica = evalucionModifica;
    }

    public SieniEvaluacionItem getEvaluacionItemNuevo() {
        return evaluacionItemNuevo;
    }

    public void setEvaluacionItemNuevo(SieniEvaluacionItem evaluacionItemNuevo) {
        this.evaluacionItemNuevo = evaluacionItemNuevo;
    }

    public SieniEvaluacionItem getEvaluacionItemModifica() {
        return evaluacionItemModifica;
    }

    public void setEvaluacionItemModifica(SieniEvaluacionItem evaluacionItemModifica) {
        this.evaluacionItemModifica = evaluacionItemModifica;
    }

    public SieniEvaluacionItem getEvaluacionItemElimina() {
        return evaluacionItemElimina;
    }

    public void setEvaluacionItemElimina(SieniEvaluacionItem evaluacionItemElimina) {
        this.evaluacionItemElimina = evaluacionItemElimina;
    }

    public List<SieniEvalRespItem> getEvalRespItemList() {
        return evalRespItemList;
    }

    public void setEvalRespItemList(List<SieniEvalRespItem> evalRespItemList) {
        this.evalRespItemList = evalRespItemList;
    }

    public SieniEvalRespItem getEvalRespItemNuevo() {
        return evalRespItemNuevo;
    }

    public void setEvalRespItemNuevo(SieniEvalRespItem evalRespItemNuevo) {
        this.evalRespItemNuevo = evalRespItemNuevo;
    }

    public SieniEvalRespItem getEvalRespItemModifica() {
        return evalRespItemModifica;
    }

    public void setEvalRespItemModifica(SieniEvalRespItem evalRespItemModifica) {
        this.evalRespItemModifica = evalRespItemModifica;
    }

    public SieniEvalRespItem getEvalRespItemElimina() {
        return evalRespItemElimina;
    }

    public void setEvalRespItemElimina(SieniEvalRespItem evalRespItemElimina) {
        this.evalRespItemElimina = evalRespItemElimina;
    }

    public List<TipoP> getTipoPregunta() {
        return tipoPregunta;
    }

    public void setTipoPregunta(List<TipoP> tipoPregunta) {
        this.tipoPregunta = tipoPregunta;
    }

  
    
    public class TipoP{

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public TipoP(int id, String tipo) {
            this.id = id;
            this.tipo = tipo;
        }
    private int id;
    private String tipo;

}

}
