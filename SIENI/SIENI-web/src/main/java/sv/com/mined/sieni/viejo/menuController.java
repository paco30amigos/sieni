/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.viejo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;


import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import org.primefaces.event.MenuActionEvent;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuItem;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author bugtraq
 */
@SessionScoped
@ManagedBean
public class menuController implements Serializable {

    private String caminoMigas;

    public String getCaminoMigas() {
        return caminoMigas;
    }

    public void setCaminoMigas(String caminoMigas) {
        this.caminoMigas = caminoMigas;
    }
    
    public void caminoMigasListener(ActionEvent e){
        MenuItem aa=(MenuItem) e.getComponent();
        
    }

    public List<DefaultMenuItem> getMenuList() {
        List<DefaultMenuItem> ret = new ArrayList<>();
        DefaultMenuItem actual;
        for (DefaultMenuItem ac2tual : ret) {

        }
        return ret;
    }
}

//    public List<elemMenu> getMenuRef() {
//        List<elemMenu> ret = new ArrayList<>();
//        elemMenu actual=new elemMenu();
//        elemMenu gestionarAlumnos = new elemMenu();
//        gestionarAlumnos.hijos=new ArrayList<>();
//        actual.setNombre("Expediente alumno");
//        actual.setUrl("/faces/views/expedienteAlumno/index.xhtml");
//        gestionarAlumnos.hijos.add(actual);
//        actual=new elemMenu();
//        actual.setNombre("Historial alumno");        
//        actual.setUrl("/faces/views/historialAlumno/index.xhtml");
//        gestionarAlumnos.hijos.add(actual);
//        ret.add(gestionarAlumnos);
//        //
//        elemMenu gestionarDocentes = new elemMenu();
//        ret.add(gestionarDocentes);
//        //
//        elemMenu gestionarAnioEscolar = new elemMenu();
//        ret.add(gestionarAnioEscolar);
//        //
//        elemMenu gestionarCurso = new elemMenu();
//        ret.add(gestionarCurso);
//        //
//        elemMenu portalNoticias = new elemMenu();
//        ret.add(portalNoticias);
//        //
//        elemMenu portalConsultas = new elemMenu();
//        ret.add(portalConsultas);
//        //
//        elemMenu buzonNotificaciones = new elemMenu();
//        ret.add(buzonNotificaciones);
//        //
//        elemMenu generarReportes = new elemMenu();
//        ret.add(generarReportes);
//        return ret;
//    }
//    
//
//}
//
//class elemMenu {
//
//    private String url;
//    private String nombre;
//    List<elemMenu> hijos;
//
//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
//
//    public String getNombre() {
//        return nombre;
//    }
//
//    public void setNombre(String nombre) {
//        this.nombre = nombre;
//    }
//
//    public List<elemMenu> getHijos() {
//        return hijos;
//    }
//
//    public void setHijos(List<elemMenu> hijos) {
//        this.hijos = hijos;
//    }
//
//}
