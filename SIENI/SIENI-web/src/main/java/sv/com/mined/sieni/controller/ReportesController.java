/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import sv.com.mined.sieni.pojos.rpt.RptAlumnosPojo;

/**
 *
 * @author Laptop
 */
public class ReportesController {

//    @Resource(name = "bd_sieni")
//    private javax.sql.DataSource origenDatos;
    public static final int PDF_REPORT = 0;
    public static final int DOCX_REPORT = 1;
    public static final int XLSX_REPORT = 2;
    public int tipo = 0;

    public static void generateReport(String path, String fileName, Connection connection, Map parameters, int format) throws JRException, IOException {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String realPath = (String) servletContext.getRealPath(path);
        JasperPrint jasperPrint = JasperFillManager.fillReport(realPath, parameters, connection);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.setContentType("application/pdf");
        httpServletResponse.setHeader("Cache-Control", "no-cache");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        ServletOutputStream servletOutputStream;
        switch (format) {
            case PDF_REPORT:
                servletOutputStream = httpServletResponse.getOutputStream();
                JRExporter exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
                exporter.exportReport();
                //JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
                break;
            case XLSX_REPORT:
                httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
                servletOutputStream = httpServletResponse.getOutputStream();
                JRXlsxExporter xlsxExporter = new JRXlsxExporter();
                xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
                xlsxExporter.exportReport();
                break;
            case DOCX_REPORT:
                httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + fileName + ".docx");
                servletOutputStream = httpServletResponse.getOutputStream();
                JRDocxExporter docxExporter = new JRDocxExporter();
                docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
                docxExporter.setParameter(JRDocxExporterParameter.OUTPUT_STREAM, servletOutputStream);
                docxExporter.exportReport();
                break;
            default:
                httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + fileName + ".pdf");
                servletOutputStream = httpServletResponse.getOutputStream();
                JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

                break;
        }
    }

    public static void generateReport(String path, String fileName, List collection, Map parameters, int format) throws JRException, IOException {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String realPath = (String) servletContext.getRealPath(path);
        JasperPrint jasperPrint = JasperFillManager.fillReport(realPath, parameters, new JRBeanCollectionDataSource(collection));
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.setContentType("application/pdf");
        httpServletResponse.setHeader("Cache-Control", "no-cache");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        ServletOutputStream servletOutputStream;
        switch (format) {
            case PDF_REPORT:
                servletOutputStream = httpServletResponse.getOutputStream();
                JRExporter exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
                exporter.exportReport();
                //JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
                break;
            case XLSX_REPORT:
                httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
                servletOutputStream = httpServletResponse.getOutputStream();
                JRXlsxExporter xlsxExporter = new JRXlsxExporter();
                xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
                xlsxExporter.exportReport();
                break;
            case DOCX_REPORT:
                httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + fileName + ".docx");
                servletOutputStream = httpServletResponse.getOutputStream();
                JRDocxExporter docxExporter = new JRDocxExporter();
                docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
                docxExporter.setParameter(JRDocxExporterParameter.OUTPUT_STREAM, servletOutputStream);
                docxExporter.exportReport();
                break;
            default:
                httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + fileName + ".pdf");
                servletOutputStream = httpServletResponse.getOutputStream();
                JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

                break;
        }
    }
}
