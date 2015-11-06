/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.siteUrls;

/**
 *
 * @author francisco_medina
 */
@WebFilter(filterName = "SecurityFilterController", urlPatterns = {"/*"})
public class SecurityFilterController implements Filter {

    private static final boolean debug = true;
    private FilterConfig filterConfig = null;
    siteUrls su = new siteUrls();

    public SecurityFilterController() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        // Obtengo el bean que representa el usuario desde el scope sesión
        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");

        //Proceso la URL que está requiriendo el cliente
        String urlStr = req.getRequestURL().toString();
        boolean noProteger = noProteger(urlStr, req);
        System.out.println(urlStr + " - desprotegido=[" + noProteger + "]");

        //Si no requiere protección continúo normalmente.
        if (noProteger) {
            chain.doFilter(request, response);
            return;
        }

        //El usuario no está logueado
        if (loginBean == null || !loginBean.isLogeado()) {
            res.sendRedirect(req.getContextPath() + "/faces/login.xhtml");
            return;
        }
        //El recurso requiere protección, pero el usuario ya está logueado.
        int tipoUsuario = Integer.parseInt(loginBean.getTipoRol());
        boolean accesoDenegado = false;
        switch (tipoUsuario) {
            case 0://alumno
                if (!validarAlumno(urlStr, req)) {
                    accesoDenegado = true;
                }
                break;
            case 1://docente
                if (!validarDocente(urlStr, req)) {
                    accesoDenegado = true;
                }
                break;
            case 2://administrador
                if (!validarAdministrador(urlStr, req)) {
                    accesoDenegado = true;
                }
                break;
            case 3://Subdirector
                if (!validarSubDirector(urlStr, req)) {
                    accesoDenegado = true;
                }
                break;
            case 4://Director
                if (!validarDirector(urlStr, req)) {
                    accesoDenegado = true;
                }
                break;
        }
        if (accesoDenegado) {
            res.sendRedirect(req.getContextPath() + "/faces/access-denied.xhtml");
        }
        chain.doFilter(request, response);
        return;
    }

    private boolean noProteger(String urlStr, HttpServletRequest req) {
        boolean ban = false;
        //url no protegidas
        String[] urls = {"login.xhtml", "access-denied.xhtml", "error.xhtml", "404.xhtml", "password.xhtml"};
        for (String actual : urls) {
            if (urlStr.endsWith("/faces/" + actual)) {
                ban = true;
                break;
            }
        }
        if (urlStr.contains("/javax.faces.resource/")) {
            ban = true;
        }
        return ban;
    }

    private boolean validarAlumno(String urlStr, HttpServletRequest req) {
        boolean ban = false;
        HashMap<String, String> subOpciones = new HashMap<>();
        subOpciones.put(su.getBaseprogramacionClases(), "index,ver");
        subOpciones.put(su.getBaseclaseOnline(), "index,ver");
        subOpciones.put(su.getBaseclaseVideoAlmacenada(), "index,ver");
        subOpciones.put(su.getBaseclaseInteractiva(), "index,ver");
        //TODO filtro de ejercicios
//        subOpciones.put(su.getEjerciciosResueltos(), "index,ver");
//        subOpciones.put(su.getGestionEvaluacion(), "index,ver");
        subOpciones.put(su.getBasegestionNota(), "index,ver");
        //modulos
        //url validas para alumno protegidas
        String[] urls = {"/faces/index.xhtml",
            su.getBaseprogramacionClases(),
            su.getBaseclaseOnline(),
            su.getBaseclaseVideoAlmacenada(),
            su.getBaseclaseInteractiva(),
            su.getBaseejerciciosResueltos(),
            su.getBasegestionEvaluacion(),
            su.getBasegestionNota(),
            su.getBasegestionNoticia(),
            su.getBasegestionConsulta(),
            su.getBasebuzonNotificacion()
        };
        for (String actual : urls) {
            if (urlStr.contains(actual)) {
                List<String> subModulos = getSubModulo(actual, subOpciones.get(actual));
                //filtro por submodulos
                if (subModulos != null && !subModulos.isEmpty()) {
                    for (String subM : subModulos) {
                        if (urlStr.contains(subM)) {
                            ban = true;
                            break;
                        }
                    }
                } else {
                    //acceso a todos los elementos de la url base
                    ban = true;
                }

                break;
            }
        }
        if (urlStr.endsWith(req.getContextPath() + "/")) {
            ban = true;
        }
        return ban;
    }

    List<String> getSubModulo(String base, String validos) {
        String[] subModulos = validos.split(",");
        List<String> ret = new ArrayList<>();
        for (String actual : subModulos) {
            ret.add(base + "/" + actual + ".xhtml");
        }
        return ret;
    }

    private boolean validarDocente(String urlStr, HttpServletRequest req) {
        boolean ban = false;
        HashMap<String, String> subOpciones = new HashMap<>();

        //url validas para alumno protegidas
        String[] urls = {"/faces/index.xhtml",
            su.getBaseexpedienteAlumnos(),
            su.getBasegestionarAnioEscolar(),
            su.getBasegestionAlumnos(),
            su.getBasegestionArchivosMultimedia(),
            su.getBasegestionarCursos(),
            su.getBaseprogramacionClases(),
            su.getBaseclaseOnline(),
            su.getBaseclaseVideoAlmacenada(),
            su.getBaseclaseInteractiva(),
            su.getBaseejerciciosResueltos(),
            su.getBasecomponenteInteractiva(),
            su.getBasegestionMateria(),
            su.getBasegestionNota(),
            su.getBasegestionNoticia(),
            su.getBasegestionConsulta(),
            su.getBasebuzonNotificacion(),
            su.getBasereporteMatricula(),
            su.getBasereporteRendimientoAlumno(),
            su.getBasereporteEstadisticoAvanceAlumno(),
            su.getBasereporteNotasAlumnoAnioEscolar(),
            su.getBasereporteEvaluaciones(),
            su.getBasereporteClases(),
            su.getBasereporteParticipacionClases(),
            su.getBasereporteCursos(),
            su.getBasereporteAlumnos()
        };
        for (String actual : urls) {
            if (urlStr.contains(actual)) {
                List<String> subModulos = getSubModulo(actual, subOpciones.get(actual));
                //filtro por submodulos
                if (subModulos != null && !subModulos.isEmpty()) {
                    for (String subM : subModulos) {
                        if (urlStr.contains(subM)) {
                            ban = true;
                            break;
                        }
                    }
                } else {
                    //acceso a todos los elementos de la url base
                    ban = true;
                }

                break;
            }
        }
        if (urlStr.endsWith(req.getContextPath() + "/")) {
            ban = true;
        }
        return ban;
    }

    private boolean validarAdministrador(String urlStr, HttpServletRequest req) {
        boolean ban = true;
        //da permiso a todas las url del sitio
        return ban;
    }

    private boolean validarSubDirector(String urlStr, HttpServletRequest req) {
        boolean ban = true;
        //da permiso a todas las url del sitio
        return ban;
    }

    private boolean validarDirector(String urlStr, HttpServletRequest req) {
        boolean ban = true;
        //da permiso a todas las url del sitio
        return ban;
    }

    /**
     * Return the filter configuration object for this filter.
     */
    /**
     * Destroy method for this filter
     */
    @Override
    public void destroy() {
    }

    /**
     * Init method for this filter
     *
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("SecurityFilterController:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("SecurityFilterController()");
        }
        StringBuilder sb = new StringBuilder("SecurityFilterController(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
