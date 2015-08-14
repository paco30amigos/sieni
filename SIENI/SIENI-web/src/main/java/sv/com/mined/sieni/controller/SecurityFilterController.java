///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package sv.com.mined.sieni.controller;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.StringWriter;
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// *
// * @author francisco_medina
// */
//@WebFilter(filterName = "SecurityFilterController", urlPatterns = {"/*"})
//public class SecurityFilterController implements Filter {
//
//    private static final boolean debug = true;
//    private FilterConfig filterConfig = null;
//
//    public SecurityFilterController() {
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response,
//            FilterChain chain) throws IOException, ServletException {
////        HttpServletRequest req = (HttpServletRequest) request;
////        HttpServletResponse res = (HttpServletResponse) response;
////        // Obtengo el bean que representa el usuario desde el scope sesión
////        LoginController loginBean = (LoginController) req.getSession().getAttribute("loginController");
////
////        //Proceso la URL que está requiriendo el cliente
////        String urlStr = req.getRequestURL().toString();
////        boolean noProteger = noProteger(urlStr, req);
////        System.out.println(urlStr + " - desprotegido=[" + noProteger + "]");
////
////        //Si no requiere protección continúo normalmente.
////        if (noProteger) {
////            chain.doFilter(request, response);
////            return;
////        }
////
////        //El usuario no está logueado
////        if (loginBean == null || !loginBean.isLogeado()) {
////            res.sendRedirect("/faces/login.xhtml");
////            return;
////        }
////        //El recurso requiere protección, pero el usuario ya está logueado.
////        int tipoUsuario = 0;
////        boolean accesoDenegado = false;
////        switch (tipoUsuario) {
////            case 0://alumno
////                if (!validarAlumno(urlStr, req)) {
////                    accesoDenegado = true;
////                }
////                break;
////            case 1://docente
////                if (!validarDocente(urlStr, req)) {
////                    accesoDenegado = true;
////                }
////                break;
////            case 2://administrador
////                if (!validarAdministrador(urlStr, req)) {
////                    accesoDenegado = true;
////                }
////                break;
////        }
////        if (accesoDenegado) {
////            res.sendRedirect(req.getContextPath() + "/faces/access-denied.xhtml");
////        }
//        chain.doFilter(request, response);
//        return;
//    }
//
//    private boolean noProteger(String urlStr, HttpServletRequest req) {
//        boolean ban = false;
//        //url no protegidas
//        String[] urls = {"login.xhtml", "access-denied.xhtml", "error.xhtml", "404.xhtml"};
//        for (String actual : urls) {
//            if (urlStr.endsWith("/faces/" + actual)) {
//                ban = true;
//                break;
//            }
//        }
//        if (urlStr.contains("/javax.faces.resource/")) {
//            ban = true;
//        }
//        return ban;
//    }
//
//    private boolean validarAlumno(String urlStr, HttpServletRequest req) {
//        boolean ban = false;
//        //url validas para alumno protegidas
//        String[] urls = {"index.xhtml"};
//        for (String actual : urls) {
//            if (urlStr.endsWith("/faces/" + actual)) {
//                ban = true;
//                break;
//            }
//        }
//        if (urlStr.endsWith(req.getContextPath() + "/")) {
//            ban = true;
//        }
//        return ban;
//    }
//
//    private boolean validarDocente(String urlStr, HttpServletRequest req) {
//        boolean ban = false;
//        //url validas para alumno protegidas
//        String[] urls = {"index.xhtml"};
//        for (String actual : urls) {
//            if (urlStr.endsWith("/faces/" + actual)) {
//                ban = true;
//                break;
//            }
//        }
//        if (urlStr.endsWith(req.getContextPath() + "/")) {
//            ban = true;
//        }
//        return ban;
//    }
//
//    private boolean validarAdministrador(String urlStr, HttpServletRequest req) {
//        boolean ban = true;
//        //url validas para alumno protegidas
//        String[] urls = {"login.xhtml"};
//        for (String actual : urls) {
//            if (urlStr.endsWith("/faces/" + actual)) {
//                ban = false;
//                break;
//            }
//        }
//        if (urlStr.endsWith(req.getContextPath() + "/")) {
//            ban = true;
//        }
//        return ban;
//    }
//
//    /**
//     * Return the filter configuration object for this filter.
//     */
//    /**
//     * Destroy method for this filter
//     */
//    @Override
//    public void destroy() {
//    }
//
//    /**
//     * Init method for this filter
//     *
//     * @param filterConfig
//     */
//    @Override
//    public void init(FilterConfig filterConfig) {
//        this.filterConfig = filterConfig;
//        if (filterConfig != null) {
//            if (debug) {
//                log("SecurityFilterController:Initializing filter");
//            }
//        }
//    }
//
//    /**
//     * Return a String representation of this object.
//     */
//    @Override
//    public String toString() {
//        if (filterConfig == null) {
//            return ("SecurityFilterController()");
//        }
//        StringBuilder sb = new StringBuilder("SecurityFilterController(");
//        sb.append(filterConfig);
//        sb.append(")");
//        return (sb.toString());
//    }
//
//    public static String getStackTrace(Throwable t) {
//        String stackTrace = null;
//        try {
//            StringWriter sw = new StringWriter();
//            PrintWriter pw = new PrintWriter(sw);
//            t.printStackTrace(pw);
//            pw.close();
//            sw.close();
//            stackTrace = sw.getBuffer().toString();
//        } catch (Exception ex) {
//        }
//        return stackTrace;
//    }
//
//    public void log(String msg) {
//        filterConfig.getServletContext().log(msg);
//    }
//
//}
