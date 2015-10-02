/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.util.List;
import org.primefaces.model.StreamedContent;
import sv.com.mined.sieni.model.SieniCurso;
import sv.com.mined.sieni.model.SieniDocente;
import sv.com.mined.sieni.model.SieniGrado;
import sv.com.mined.sieni.model.SieniMateria;
import sv.com.mined.sieni.model.SieniSeccion;
import utils.siteUrls;

/**
 *
 * @author francisco_medina
 */
public class GestionEjercicioForm {

    private int indexMenu;
  
  String formula;

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    

    public int getIndexMenu() {
        return indexMenu;
    }

    
    public void setIndexMenu(int indexMenu) {
        siteUrls sU = new siteUrls();
        switch (indexMenu) {
            case 0:
                sU.redirect(sU.getBasegestionarCursos() + "index.xhtml");
                break;
            case 1:
                sU.redirect(sU.getBasegestionarCursos() + "crear.xhtml");
                break;
            case 2:
                sU.redirect(sU.getBasegestionarCursos() + "editar.xhtml");
                break;
            case 3:
                sU.redirect(sU.getBasegestionarCursos() + "ver.xhtml");
                break;
        }
        this.indexMenu = indexMenu;
    }
    
   
}
