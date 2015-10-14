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
  public static  final String ABSOLUTO="\\left |{x}\\right |";
  public static  final String FACTORIAL="{x}!";
  public static  final String RAIZ="\\sqrt[]{x}";
  public static  final String RAIZN="\\sqrt[{n}]{x}";
  public static  final String FUNCIONE="e^{x}";
  public static  final String POTENCIA="{x}^{n}";
  public static  final String LN="ln{x}";
  public static  final String LOG="log{x}";
  public static  final String SENO="\\sen{x}";
  public static  final String COSENO="\\cos{x}";
  public static  final String TAN="\\tan{x}";
  public static  final String COT="\\cot{x}";
    public static  final String SENOH="\\senh{x}";
  public static  final String COSENOH="\\cosh{x}";
  public static  final String TANH="\\tanh{x}";
  public static  final String COTH="\\coth{x}";
  public static  final String ARCSENO="\\arcsen{x}";
  public static  final String ARCCOSENO="\\arccos{x}";
  public static  final String ARCTAN="\\arctan{x}";
  public static  final String ARCCOT="\\arccot{x}";
   public static  final String ARCSENOH="\\arcsenh{x}";
  public static  final String ARCCOSENOH="\\arccosh{x}";
  public static  final String ARCTANH="\\arctanh{x}";
  public static  final String ARCCOTH="\\arccoth{x}";
 
  
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
