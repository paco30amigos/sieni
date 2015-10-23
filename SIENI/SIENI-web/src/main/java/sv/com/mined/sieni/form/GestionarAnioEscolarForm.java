/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.util.List;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import sv.com.mined.sieni.model.SieniAnioEscolar;
import sv.com.mined.sieni.model.SieniAnioEscolar;
import utils.siteUrls;

/**
 *
 * @author francisco_medina
 */
public class GestionarAnioEscolarForm {

    private int indexMenu;
    private SieniAnioEscolar eliminar;

    // consulta de anioEscolar
    private List<SieniAnioEscolar> anioEscolarList;
    //registro de anioEscolar
    private SieniAnioEscolar anioEscolarNuevo;
    //modificacion de anioEscolar
    private SieniAnioEscolar anioEscolarModifica;

    public int getIndexMenu() {
        return indexMenu;
    }

    public void setIndexMenu(int indexMenu) {
        siteUrls sU = new siteUrls();
        switch (indexMenu) {
            case 0:
                sU.redirect(sU.getBasegestionarAnioEscolar()+ "index.xhtml");
                break;
            case 1:
                sU.redirect(sU.getBasegestionarAnioEscolar() + "crear.xhtml");
                break;
            case 2:
                sU.redirect(sU.getBasegestionarAnioEscolar() + "editar.xhtml");
                break;
            case 3:
                sU.redirect(sU.getBasegestionarAnioEscolar() + "ver.xhtml");
                break;
        }
        this.indexMenu = indexMenu;
        this.indexMenu = indexMenu;
    }

    public SieniAnioEscolar getEliminar() {
        return eliminar;
    }

    public void setEliminar(SieniAnioEscolar eliminar) {
        this.eliminar = eliminar;
    }

    public List<SieniAnioEscolar> getAnioEscolarList() {
        return anioEscolarList;
    }

    public void setAnioEscolarList(List<SieniAnioEscolar> anioEscolarList) {
        this.anioEscolarList = anioEscolarList;
    }

    public SieniAnioEscolar getAnioEscolarNuevo() {
        return anioEscolarNuevo;
    }

    public void setAnioEscolarNuevo(SieniAnioEscolar anioEscolarNuevo) {
        this.anioEscolarNuevo = anioEscolarNuevo;
    }

    public SieniAnioEscolar getAnioEscolarModifica() {
        return anioEscolarModifica;
    }

    public void setAnioEscolarModifica(SieniAnioEscolar anioEscolarModifica) {
        this.anioEscolarModifica = anioEscolarModifica;
    }
}
