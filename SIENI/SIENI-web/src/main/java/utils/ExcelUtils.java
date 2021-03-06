/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sv.com.mined.sieni.SieniAlumnoFacadeRemote;
import sv.com.mined.sieni.model.SieniAlumno;
import sv.com.mined.sieni.model.SieniNota;

/**
 *
 * @author francisco_medina
 */
public class ExcelUtils {

    String fileName;
    Workbook workbook;
    private SieniAlumnoFacadeRemote sieniAlumnoFacadeRemote;

    public ExcelUtils() {
    }

    public ExcelUtils(String fileName) {
        try {
            loadWorkbook(fileName);
        } catch (IOException ex) {
            Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ExcelUtils(InputStream stream) {
        loadWorkbookInputStream(stream);
    }

    public void loadWorkbook(String fileName) throws FileNotFoundException,
            IOException {
        try (FileInputStream workbookFileInputStream = new FileInputStream(fileName)) {
            this.fileName = fileName;
            workbook = new XSSFWorkbook(workbookFileInputStream);
            workbookFileInputStream.close();
        }
    }

    public void loadWorkbookInputStream(InputStream stream) {
        try {
            workbook = new XSSFWorkbook(stream);
            stream.close();
        } catch (IOException ex) {
            Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Obtiene la referencia de una celda de un libro de excel
     *
     * @param worksheetIndex indice de numero de pagina
     * @param cellAddress nombre de la celda a actualizar
     * @return
     */
    public Cell getReferencedCell(int worksheetIndex, String cellAddress) {
        Sheet sheet = workbook.getSheetAt(worksheetIndex);
        CellReference reference = new CellReference(cellAddress);
        Row row = sheet.getRow(reference.getRow());
        Cell cell = row.getCell(reference.getCol());
        return cell;
    }

    public List<SieniNota> readNotasExcel(int worksheetIndex) {
        List<SieniNota> notas = new ArrayList<>();
        Sheet sheet = workbook.getSheetAt(worksheetIndex);
        Iterator rows = sheet.rowIterator();
        SieniNota notaActual;
        int rowIndex = 0;
        while (rows.hasNext()) {
            XSSFRow row = (XSSFRow) rows.next();
            if(rowIndex > 2){
                notaActual = new SieniNota();
                notaActual.setErrores(new ArrayList<String>());
                int celdaCarnet = 0;
                int celdaNombre = 1;
                int celdaNota = 2;
                Long idCarnet = null, idAlumno = null;
                String carnet = null;

                if (row.getCell(celdaCarnet) == null&&row.getCell(celdaNombre) == null&&row.getCell(celdaNota) == null) {
                    break;
                }

                if (row.getCell(celdaCarnet) != null && row.getCell(celdaCarnet).getCellType() == Cell.CELL_TYPE_STRING) {
                    carnet = row.getCell(celdaCarnet) != null ? row.getCell(celdaCarnet).getStringCellValue() : null;
                }

                if (row.getCell(celdaNombre) != null && row.getCell(celdaNombre).getCellType() == Cell.CELL_TYPE_STRING) {
                    notaActual.setNombreCompleto(row.getCell(celdaNombre) != null ? row.getCell(celdaNombre).getStringCellValue() : null);//nombre
                }

                if (row.getCell(celdaNota) != null && row.getCell(celdaNota).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    notaActual.setNtCalificacion(row.getCell(celdaNota) != null ? row.getCell(celdaNota).getNumericCellValue() : null);//nota
                } else if (row.getCell(celdaNota) != null && row.getCell(celdaNota).getCellType() == Cell.CELL_TYPE_STRING) {
                    String val = row.getCell(celdaNota).getStringCellValue();
                    try {
                        val = val.replaceAll(",", ".");
                        notaActual.setNtCalificacion(Double.parseDouble(val));
                    } catch (Exception e) {
                        notaActual.setNtCalificacion(null);
                        notaActual.getErrores().add("Nota no valida");
                    }
                } else {
                    notaActual.getErrores().add("Nota no valida");
                }

                if (carnet != null) {
                    List<SieniAlumno> alumnos = sieniAlumnoFacadeRemote.findByCarnet(carnet);
                    if (alumnos == null || alumnos.isEmpty()) {
                        notaActual.setCarnet("");
                        notaActual.getErrores().add("Carnet no se encontró");
                    } else {
                        idCarnet = alumnos.get(0).getIdAlumno();
                        notaActual.setCarnet(carnet);
                    }
                }

                if (notaActual.getNtCalificacion() != null && (notaActual.getNtCalificacion() < 0.0 || notaActual.getNtCalificacion() > 10.0)) {
                    notaActual.getErrores().add("Nota no valida");
                }
                if (notaActual.getNombreCompleto() != null || notaActual.getNtCalificacion() != null) {
                    SieniAlumno alumno = sieniAlumnoFacadeRemote.findByNombreCompleto(notaActual.getNombreCompleto());
                    if (alumno == null) {
                        notaActual.getErrores().add("Nombre de alumno no se encontró");
                    } else {
                        notaActual.setIdAlumno(alumno.getIdAlumno());
                        idAlumno = alumno.getIdAlumno();
                    }
                }
                if (notaActual.getNombreCompleto() == null) {
                    notaActual.getErrores().add("No se ingresó un nombre para el alumno");
                } else if (notaActual.getNtCalificacion() == null) {
                    notaActual.getErrores().add("No se ingresó una nota para el alumno");
                }

                if (idCarnet != null && idAlumno != null && !idCarnet.equals(idAlumno)) {
                    notaActual.getErrores().add("Carnet y Nombre de alumno no coinciden");
                } else if (idCarnet == null || idAlumno == null) {
                    notaActual.getErrores().add("Carnet y Nombre de alumno no coinciden");
                }
                notas.add(notaActual);
            }
            rowIndex++;
        }
        return notas;
    }

    /**
     * Realiza el cierre del documento de excel
     *
     * @throws java.io.IOException
     */
    public void closeWorkbook() throws IOException {
        workbook.close();
    }

    /**
     * Obtiene un valor Double de una celda en un libro de excel
     *
     * @param worksheetIndex indice de numero de pagina
     * @param cellAddress nombre de la celda a actualizar
     * @return
     */
    public Double getDoubleValue(int worksheetIndex, String cellAddress) {
        Double ret = 0.0;
        Cell cell = getReferencedCell(worksheetIndex, cellAddress);
        FormulaEvaluator evaluator = workbook.getCreationHelper()
                .createFormulaEvaluator();
        evaluator.clearAllCachedResultValues();
        CellValue cellValue = evaluator.evaluate(cell);
        if (cellValue != null) {
            if (cellValue.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                ret = cellValue.getNumberValue();
            } else {
                throw new RuntimeException("La celda " + cellAddress
                        + " no tiene un valor numerico");
            }
        } else {
            throw new RuntimeException("La celda " + cellAddress
                    + " no tiene un valor numerico");
        }
        return ret;
    }

    /**
     * Obtiene un valor Double de una celda en un libro de excel
     *
     * @param worksheetIndex indice de numero de pagina
     * @param cellAddress nombre de la celda a actualizar
     * @return
     */
    public String getStringValue(int worksheetIndex, String cellAddress) {
        String ret = "";
        Cell cell = getReferencedCell(worksheetIndex, cellAddress);
        if (cell != null) {
            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                ret = cell.getStringCellValue();
            } else {
                throw new RuntimeException("La celda " + cellAddress + " no tiene una cadena de texto");
            }
        } else {
            throw new RuntimeException("La celda " + cellAddress + " no tiene una cadena de texto");
        }
        return ret;
    }

    public void setSieniAlumnoFacadeRemote(SieniAlumnoFacadeRemote sieniAlumnoFacadeRemote) {
        this.sieniAlumnoFacadeRemote = sieniAlumnoFacadeRemote;
    }
}
