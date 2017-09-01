package com.unicomer.opos.inhouse.gface.controller;

import static com.unicomer.opos.inhouse.gface.pojo.GfaceConstants.ST_PEND_VALID;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.annotation.Resource;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.Visibility;

import com.unicomer.hr.utility.JsfUtil;
import com.unicomer.inhouse.gface.model.util.ExcelParser;
import com.unicomer.inhouse.gface.model.util.GfaceCopiaArchivos;
import com.unicomer.inhouse.gface.model.util.GfaceFileUtils;
import com.unicomer.inhouse.gface.model.util.GfaceFormatUtilities;
import com.unicomer.inhouse.gface.model.util.ZipUtilities;
import com.unicomer.opos.inhouse.gface.controller.form.GfaceDepuracionErroresForm;
import com.unicomer.opos.inhouse.gface.ejb.ControlErroresEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceAssocDocEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceBatchControlEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceBtchFilesEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceDocErrDtlEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceDtlDocEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceErrRegCtgEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceGuatefacturaFileReaderEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceHdrDocEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceOthersMigrationEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceSendDataToGuatefacturasEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceStatusControlEjbLocal;
import com.unicomer.opos.inhouse.gface.entity.GfaceAssocDoc;
import com.unicomer.opos.inhouse.gface.entity.GfaceDocErrDtl;
import com.unicomer.opos.inhouse.gface.entity.GfaceDtlDoc;
import com.unicomer.opos.inhouse.gface.entity.GfaceErrRegCtg;
import com.unicomer.opos.inhouse.gface.entity.GfaceHdrDoc;
import com.unicomer.opos.inhouse.gface.pojo.AssocDocExcelPojo;
import com.unicomer.opos.inhouse.gface.pojo.AssocDocPojo;
import com.unicomer.opos.inhouse.gface.pojo.ComboBoxPojo;
import com.unicomer.opos.inhouse.gface.pojo.DatosPojo;
import com.unicomer.opos.inhouse.gface.pojo.DtlDocPojo;
import com.unicomer.opos.inhouse.gface.pojo.DtlExcelPojo;
import com.unicomer.opos.inhouse.gface.pojo.GfaceConstants;
import com.unicomer.opos.inhouse.gface.pojo.GfaceDocPojo;
import com.unicomer.opos.inhouse.gface.pojo.HdrDocPojo;
import com.unicomer.opos.inhouse.gface.pojo.HdrExcelPojo;
import com.unicomer.opos.inhouse.gface.pojo.ValidationPojo;
import com.unicomer.opos.inhouse.gface.util.GfaceUtilities;
import com.unicomer.opos.inhouse.gface.util.validations.GfaceFormatValidationEjbLocal;
import com.unicomer.opos.inhouse.gface.util.validations.GfaceInternalValidationsEjbLocal;
import com.unicomer.opos.inhouse.security.ejbs.UnUserDaoEjbLocal;
import com.unicomer.opos.inhouse.security.entities.UnUser;
import com.unicomer.inhouse.jndi.JNDIUnicomerGface;
import com.unicomer.inhouse.jndi.JNDIUnicomerSecurity;

/**
 *
 * @author francisco_medina
 */
@ViewScoped
@ManagedBean(name = "GfaceDepuracionErroresController")
public class GfaceDepuracionErroresController extends GfaceDepuracionErroresForm implements Serializable {

    private static final long serialVersionUID = 1L;
    //Message Bundle
    ResourceBundle rbGface;

    @Resource(lookup = JNDIUnicomerSecurity.UnUserDaoEjbLocal)
    private UnUserDaoEjbLocal UnUserFacade;

    @Resource(lookup = JNDIUnicomerGface.GfaceOthersMigrationEjbLocal)
    private GfaceOthersMigrationEjbLocal gfaceOthersMigrationEjbLocal;
    @Resource(lookup = JNDIUnicomerGface.GfaceBtchFilesEjbLocal)
    private GfaceBtchFilesEjbLocal gfaceBtchFilesEjbLocal;
    @Resource(lookup = JNDIUnicomerGface.GfaceHdrDocEjbLocal)
    private GfaceHdrDocEjbLocal gfaceHdrDocEjbLocal;
    @Resource(lookup = JNDIUnicomerGface.GfaceDtlDocEjbLocal)
    private GfaceDtlDocEjbLocal gfaceDtlDocEjbLocal;
    @Resource(lookup = JNDIUnicomerGface.GfaceAssocDocEjbLocal)
    private GfaceAssocDocEjbLocal gfaceAssocDocEjbLocal;
    @Resource(lookup = JNDIUnicomerGface.GfaceErrRegCtgEjbLocal)
    private GfaceErrRegCtgEjbLocal gfaceErrRegCtgEjbLocal;
    @Resource(lookup = JNDIUnicomerGface.GfaceDocErrDtlEjbLocal)
    private GfaceDocErrDtlEjbLocal gfaceDocErrDtlEjbLocal;
    @Resource(lookup = JNDIUnicomerGface.GfaceSendDataToGuatefacturasEjbLocal)
    private GfaceSendDataToGuatefacturasEjbLocal gfaceSendDataToGuatefacturasEjbLocal;
    
    

    @ManagedProperty(value = "#{UnUserController.uniUser}")
    private UnUser userInSession;

    //ini objetos principales
//    GfaceReadDataFromOthers erfo = new GfaceReadDataFromOthers();
    @Resource(lookup = JNDIUnicomerGface.GfaceStatusControlEjbLocal)
    GfaceStatusControlEjbLocal sc;
    @Resource(lookup = JNDIUnicomerGface.GfaceBatchControlEjbLocal)
    GfaceBatchControlEjbLocal bc;
    @Resource(lookup = JNDIUnicomerGface.GfaceInternalValidationsEjbLocal)
    GfaceInternalValidationsEjbLocal iv;   
    GfaceFormatUtilities fu = new GfaceFormatUtilities();    
    @Resource(lookup = JNDIUnicomerGface.GfaceGuatefacturaFileReaderEjbLocal)
    GfaceGuatefacturaFileReaderEjbLocal gfr;
    GfaceFileUtils fut = new GfaceFileUtils();
    ZipUtilities zu = new ZipUtilities();
    @Resource(lookup = JNDIUnicomerGface.ControlErroresEjbLocal)
    ControlErroresEjbLocal ce;
    GfaceCopiaArchivos ca = new GfaceCopiaArchivos();
    @Resource(lookup = JNDIUnicomerGface.GfaceFormatValidationEjbLocal)
    GfaceFormatValidationEjbLocal fv;
    //fin objetos principales

    @PostConstruct
    public void init() {
        //Initialize the vars
        rbGface = ResourceBundle.getBundle("com.unicomer.opos.inhouse.gface.util.i18n.Bundlegface", FacesContext.getCurrentInstance().getViewRoot().getLocale()); 
        //inicializa los objetos que utilizan EJB
//        erfo.setGfaceOthersMigrationEjbLocal(gfaceOthersMigrationEjbLocal);
//        sc.setGfaceHdrDocEjbLocal(gfaceHdrDocEjbLocal);
//        sc.setUserInSession(userInSession);

//        bc.setGfaceHdrDocEjbLocal(gfaceHdrDocEjbLocal);
//        bc.setUserInSession(userInSession);
//        bc.setGfaceBtchFilesEjbLocal(gfaceBtchFilesEjbLocal);

//        ce.setUserInSession(userInSession);
//        ce.setGfaceErrRegCtgEjbLocal(gfaceErrRegCtgEjbLocal);
//        ce.setGfaceDocErrDtlEjbLocal(gfaceDocErrDtlEjbLocal);

        //agrega el elemento inicializado para control de errores
//        gfr.setCe(ce);
        //agrega objetos para la validacion
//        gv.setGfr(gfr);

        //validaciones internas
//        iv.setGfaceHdrDocEjbLocal(gfaceHdrDocEjbLocal);
//        iv.setCe(ce);
        //validacion de formato de excel
//        fv.setGfaceHdrDocEjbLocal(gfaceHdrDocEjbLocal);
//        fv.setGfaceDtlDocEjbLocal(gfaceDtlDocEjbLocal);
//        fv.setGfaceAssocDocEjbLocal(gfaceAssocDocEjbLocal);

        //iniciar variables de formulario
        this.setDocDateFin(new Date());
        this.setDocDateIni(new Date());
        this.setDocTypeList(new ArrayList<ComboBoxPojo>());
        this.setErrorsList(new ArrayList<ComboBoxPojo>());
        this.setWarningsList(new ArrayList<ComboBoxPojo>());
        this.setTabla(new ArrayList()); 
        this.setSeleccionados(new ArrayList<DatosPojo>());
        initd();
        this.setIndex(0);
    }

    public void initEdit() {
        if (this.getSeleccionados() != null && !this.getSeleccionados().isEmpty()) {
            this.setDatoActual(this.getSeleccionados().get(0));
            RequestContext context=RequestContext.getCurrentInstance();
            context.execute("PF('edit-dlg').show();");
        }else{
        	JsfUtil.addWarnMessage(rbGface.getString("gface_NoSelectedTransaction"));
        }
        this.setIndex(0);
    }

    public void initd() {        
    	List<GfaceHdrDoc> pv = sc.getInvalidas(this.getDocumentType(),this.getWarning(),this.getError(),this.getTienda());
//        List<GfaceHdrDoc> pv = sc.getInvalidas();
        List<GfaceDocPojo> files = GfaceDocPojo.getInfo(pv, true,rbGface);
//        initErrores(files);//TODO eliminar
//        this.setSeleccionados(getResumenDataHash(files));
        this.setTabla(getResumenDataHash(files));
        initErrorWarnings();
        initTipoDocumentos();
    }

    private void initErrorWarnings() {
    	//init listado errores
        this.setWarningsList(new ArrayList<ComboBoxPojo>());
        this.setErrorsList(new ArrayList<ComboBoxPojo>());
        HashMap<String,List<GfaceErrRegCtg>> errWar=gfaceErrRegCtgEjbLocal.getErroresAdvertencias();
        if(errWar.get("Y")!=null){
        	List<GfaceErrRegCtg> warning=errWar.get("Y");
        	for(GfaceErrRegCtg w:warning){
        		ComboBoxPojo c=new ComboBoxPojo();
        		c.setDescription(w.getErregDsc());
        		c.setId(w.getErregCode());
        		c.setValue(w);
        		this.getWarningsList().add(c);
        	}
        }
        if(errWar.get("N")!=null){
        	List<GfaceErrRegCtg> warning=errWar.get("N");
        	for(GfaceErrRegCtg w:warning){
        		ComboBoxPojo c=new ComboBoxPojo();
        		c.setDescription(w.getErregDsc());
        		c.setId(w.getErregCode());
        		c.setValue(w);
        		this.getErrorsList().add(c);
        	}
        }
	}
    
    public void getDatosGuatefacturas(){
    	gfr.readAllFiles();
    }
    
    private void initTipoDocumentos(){
    	this.setDocTypeList(new ArrayList<ComboBoxPojo>());
    	this.getDocTypeList().add(new ComboBoxPojo("CFACE",null,rbGface.getString("gface_rpt_docTypeCFACE")));
    	this.getDocTypeList().add(new ComboBoxPojo("CNC",null,rbGface.getString("gface_rpt_docTypeCNC")));
    	this.getDocTypeList().add(new ComboBoxPojo("CND",null,rbGface.getString("gface_rpt_docTypeCND")));
    }

    public List<DatosPojo> getResumenDataHash(List<GfaceDocPojo> files) {

        DatosPojo dp;
        GfaceFormatUtilities fu = new GfaceFormatUtilities();
        int count = 0;
        List<DatosPojo> ret = new ArrayList<DatosPojo>();
        for (GfaceDocPojo actual : files) {
            //clona el objeto despues de traerlo de la base de datos para evitar traerlo desde la base de datos al cambiar el estado
            setCopiasDeOriginales(actual);
            dp = new DatosPojo();
            dp.setDocType(actual.getEncabezado().docType);
            dp.setSerie(actual.getEncabezado().serieNum);
            dp.setNum(actual.getEncabezado().corrNum != null ? actual.getEncabezado().corrNum.toString() : "");
            dp.setDocDate(fu.getFormatedDate(actual.getEncabezado().docDate, "dd/MM/yyyy"));
            dp.setDatos(actual);
            if(actual.getErrores()!=null&&!actual.getErrores().isEmpty())
            	dp.setTieneError(rbGface.getString("gface_yes"));
            else{
            	dp.setTieneError(rbGface.getString("gface_no"));
            }
            
            if(actual.getAdvertencias()!=null&&!actual.getAdvertencias().isEmpty())
            	dp.setTieneAdvertencia(rbGface.getString("gface_yes"));
            else{
            	dp.setTieneAdvertencia(rbGface.getString("gface_no"));
            }
            ret.add(dp);
            count++;
        }
        
        this.setTransacErrors(gfaceHdrDocEjbLocal.countErrors());
        this.setTransacWarning(gfaceHdrDocEjbLocal.countWarnigns());
        this.setTransacPendingToSend(gfaceHdrDocEjbLocal.countReadyToSend());
        
        return ret;
    }

    private void setCopiasDeOriginales(GfaceDocPojo doc) {
        try {
            doc.getHdrFuente().setCopiado(doc.getHdrFuente().clone());
            for (GfaceDtlDoc actual : doc.getDetFuente()) {
                actual.setCopiado(actual.clone());
            }
            doc.getDocFuente().setCopiado(doc.getDocFuente().clone());
        } catch (Exception e) {
//            System.out.println("No se pudo clonar el objeto fuente");
        }
    }

    public void nextIndex() {
        if (this.getSeleccionados() != null && !this.getSeleccionados().isEmpty()) {
            if (getIndex() >= this.getSeleccionados().size() - 1) {
                setIndex(0);
            } else {
                setIndex(getIndex() + 1);
            }
            this.setDatoActual(this.getSeleccionados().get(getIndex()));
        }
    }

    public void prevIndex() {
        if (this.getSeleccionados() != null && !this.getSeleccionados().isEmpty()) {
            if (getIndex() <= 0) {
                setIndex(this.getSeleccionados().size() - 1);
            } else {
                setIndex(getIndex() - 1);
            }
            this.setDatoActual(this.getSeleccionados().get(getIndex()));
        }
    }
    /* proceso de guardar modificaciones*/

    //copia archivos sin guardarlos
    //crear pojos
    //pasar de pojo a entidad    
    //asignar los valores para relacionarlo al estado del registro
    //guardar registro con estado pendiente de validacion
    //validar
    //actualizar tabla de errores
    public void saveEdit() {
        copiarArchivos();
        List<GfaceHdrDoc> pv = sc.getPendientesValidar();
        iv.validacionInterna(pv,userInSession);
        
        List<GfaceHdrDoc> lpe = sc.getListasParaEnviar();
        gfaceSendDataToGuatefacturasEjbLocal.procesarArchivosValidados(lpe, userInSession);
        init();
    }

    public void cancelEdit() {
    	
    }

    public void enviarAGuatefacturas() {
        List<GfaceHdrDoc> lpe = sc.getListasParaEnviar();
        gfaceSendDataToGuatefacturasEjbLocal.sendDataToGuatefacturas();
        initd();        
    }

    

    //flujo manual
    public void copiarArchivos() {
        Date fecha = new Date();
        BigInteger usuario = new BigInteger(userInSession.getUserId().toString());
        HashMap<String, Object> paramsCopia = new HashMap<String, Object>();
        paramsCopia.put("fecha", fecha);
        paramsCopia.put("usuario", usuario);

        prepararCopias();

        //obtiene las copias de los encabezados
        paramsCopia.put("original", getHdrModificados());
        List<GfaceHdrDoc> hdrParaModificar = ca.copiarLimpiarHeader(paramsCopia);
        List<GfaceHdrDoc> copiaHeader=getHdrModificados();
        //envia los parametros para hacer la actualizacion
        copiaHeader.addAll(hdrParaModificar);
        paramsCopia.put("registros", copiaHeader);
        gfaceHdrDocEjbLocal.merge(paramsCopia,false); 
        //establece el valor de los header para utilizarlos de referencia en la copia de detalle y documentos asociados
        paramsCopia.put("headerModificados", getHashHeader(hdrParaModificar));

        if(getDetModificados()!=null&&!getDetModificados().isEmpty()){
	        //obtiene las copias de los detalles
	        paramsCopia.put("original", getDetModificados());
	        List<GfaceDtlDoc> detParaModificar = ca.copiarLimpiarDetail(paramsCopia);
	        //envia los parametros para hacer la actualizacion y creacion de los registros nuevos
	        detParaModificar.addAll(getDetModificados());
	        paramsCopia.put("registros", detParaModificar);
	        gfaceDtlDocEjbLocal.merge(paramsCopia);
        }

        if(getDocModificados()!=null&&!getDocModificados().isEmpty()
        		&&getDocModificados().get(0).getAsdocId()!=null// valida que el documento exista y q no este en blanco
        		){
	        //obtiene las copias de los documentos asociados
	        paramsCopia.put("original", getDocModificados());
 	        List<GfaceAssocDoc> docParaModificar = ca.copiarLimpiarDocs(paramsCopia);
	        //envia los parametros para hacer la actualizacion y creacion de los registros nuevos
	        docParaModificar.addAll(getDocModificados());
	        paramsCopia.put("registros", docParaModificar);
	        gfaceAssocDocEjbLocal.merge(paramsCopia);
        }

        //cambia el estatus de las facturas copiadas para que sean validadas
        sc.cambiarValidInterInvalPorPenValid(copiaHeader,userInSession);
    }

    public HashMap<BigInteger, GfaceHdrDoc> getHashHeader(List<GfaceHdrDoc> headers) {
        HashMap<BigInteger, GfaceHdrDoc> ret = new HashMap<BigInteger, GfaceHdrDoc>();
        for (GfaceHdrDoc actual : headers) {
        	//actual.getCopiado().setCopiado(null);
            ret.put(actual.getCopiado().getHdrId(), actual);
        }
        return ret;
    }

    //obtiene todos los documentos seleccionados y crea un paquete de header, detalle y documentos asosciados
    public void prepararCopias() {
        setDetModificados(new ArrayList<GfaceDtlDoc>());
        setDocModificados(new ArrayList<GfaceAssocDoc>());
        setHdrModificados(new ArrayList<GfaceHdrDoc>());
        for (DatosPojo doc : this.getSeleccionados()) {
            GfaceHdrDoc pv = doc.getDatos().getHdrFuente();
            getDetModificados().addAll(doc.getDatos().getDetFuente());
            getDocModificados().add(doc.getDatos().getDocFuente());
            getHdrModificados().add(pv);
        }
    }

    public StreamedContent getArchivoErrores(){
    	StreamedContent ret=null;
    	try {
    		if(getTabla()!=null&&!getTabla().isEmpty()){
	    		ExcelParser eu = new ExcelParser();
	        	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        	byte[] bytes;
	        	
	        	// Se crea el libro
	            XSSFWorkbook libro = new XSSFWorkbook();
	            // Se crea una hoja dentro del libro
	            XSSFSheet sheetD = libro.createSheet();
	            	            
	            CellStyle style = libro.createCellStyle();//Create style
	            Font font = libro.createFont();//Create font
	            font.setBoldweight(Font.BOLDWEIGHT_BOLD);//Make font bold
	            font.setColor(HSSFColor.BLUE_GREY.index);
	            style.setFont(font);//set it to bold
	            
	            
	            //indice inicial=3, ya que del cero al 3 es el encabezado y tiene el ejemplo de los 4 encabezados 
	            int initIndex=3;
	            //crea el enzabezado del documento
	            setEnzabezadoDocumento(sheetD,0,style);
	            
	            for(DatosPojo actual:getTabla()){
	            	//agregar el header al reporte de excell
	            	HdrExcelPojo h=new HdrExcelPojo();
	            	h.setHdrValues(actual.getDatos().getEncabezado());
	            	h.descDocType="Encabezado";
	            	h.docId=actual.getDatos().getHdrFuente().getHdrId();
	            	h.errores=new ArrayList<String>();
	            	h.advertencias=new ArrayList<String>();
	            	if(actual.getDatos().getErrores()!=null){
		            	for(GfaceDocErrDtl error:actual.getDatos().getErrores()){
		            		h.errores.add(error.getErregId().getErregDsc());
		            	}
	            	}
	            	if(actual.getDatos().getAdvertencias()!=null){
		            	for(GfaceDocErrDtl advertencias:actual.getDatos().getAdvertencias()){
		            		h.errores.add(advertencias.getErregId().getErregDsc());
		            	}
	            	}
	            	//agrega los objetos de un tipo de dato especifico a una hoja de excel
	                eu.parseObjectToExcelSheet(h, HdrExcelPojo.class, sheetD,initIndex);
	                initIndex++;
	                int idxDet=0;
	                for(DtlDocPojo dt:actual.getDatos().getDetalle()){
	                	DtlExcelPojo dtl=new DtlExcelPojo();
	                	dtl.setDtlPojo(dt);
	                	dtl.descDocType="Detalle";
	                	dtl.docId=actual.getDatos().getDetFuente().get(idxDet).getDtldcId();
	                	eu.parseObjectToExcelSheet(dtl, DtlExcelPojo.class, sheetD,initIndex);
	                	idxDet++;
	                	initIndex++;
	                }
	                
	                AssocDocExcelPojo docAssoc=new AssocDocExcelPojo();
	                AssocDocPojo doc=actual.getDatos().getDocumentosAsociados();
	                if(doc!=null&&doc.getDocTy()!=null){
		                docAssoc.setAssocDocPojo(doc);
		                docAssoc.descDocType="Documento";
		                docAssoc.docId=actual.getDatos().getDocFuente().getAsdocId();
		                eu.parseObjectToExcelSheet(docAssoc, AssocDocExcelPojo.class, sheetD,initIndex);
		                initIndex++;
	                }
	            }
	            
	            libro.write(outputStream);
	            outputStream.flush();
	            outputStream.close();
	
	            bytes = outputStream.toByteArray();
	            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
	            ret= new DefaultStreamedContent(inputStream, "application/xlsx", "data.xlsx");
    		}else{
    			JsfUtil.addSuccessMessage(rbGface.getString("gface_NoErrorToDownload"));    			
    		}
		} catch (Exception e) {
			JsfUtil.addSuccessMessage(rbGface.getString("gface_errorGeneratingErrorsFile"));
		}
    	return ret;    	
    }
    
    public void setEnzabezadoDocumento(XSSFSheet sheetD,int inicioEncabezado,CellStyle negrita){
    	
    	int i=0;
    	List<List<String>> partesDocs=new ArrayList<List<String>>();
    	partesDocs.add(getColumnasEncabezado());
    	partesDocs.add(getColumnasDetalle());
    	partesDocs.add(getColumnasDocumentosAsociados());
//    	partesDocs.add(getColumnasTotales());
    	int fila=inicioEncabezado;
    	for(List<String> secciones:partesDocs){
    		XSSFRow enzabezado=sheetD.createRow(fila);
    		i=0;
    		for(String columna:secciones){
        		XSSFCell celda=enzabezado.createCell(i);
        		celda.setCellValue(columna);
        		i++;
        	}
    		for(int j=0;j<enzabezado.getLastCellNum();j++){
    			enzabezado.getCell(j).setCellStyle(negrita);
    		}
    		fila++;
    	}
    	//establece que el encabezado va a ser un freeze panel
    	sheetD.createFreezePane(inicioEncabezado, fila);
    }
    
    public List<String> getColumnasEncabezado(){
    	List<String> ret=new ArrayList<String>();
    	ret.add("Encabezado");
    	ret.add("Identificador");
    	ret.add("Tipo de Registro");
    	ret.add("Fecha Documento");
    	ret.add("Tipo Documento");
    	ret.add("Serie");
    	ret.add("Numero");
    	ret.add("Nit Comprador");
    	ret.add("Nombre Comprador");
    	ret.add("Direccion");
    	ret.add("Telefono");
    	ret.add("Codigo Moneda");
    	ret.add("Tasa Cambio");
    	ret.add("Estado Documento");
    	ret.add("Fecha anulacion");
    	ret.add("Orden Externo");
    	ret.add("Tipo Venta");
    	ret.add("Destino Venta");
    	ret.add("Motivo Anulacion");
    	ret.add("Enviar Correo");
    	ret.add("ERRORES");
    	ret.add("ADVERTENCIAS");
    	return ret;
    }
    public List<String> getColumnasDetalle(){
    	List<String> ret=new ArrayList<String>();
    	ret.add("Detalle");
    	ret.add("Identificador");
    	ret.add("Tipo de Registro");
    	ret.add("Cantidad");
    	ret.add("Unidad Medida");
    	ret.add("Precio");
    	ret.add("Porcentaje Descuento");
    	ret.add("Importe Descuento");
    	ret.add("Importe Bruto");
    	ret.add("Importe Neto");
    	ret.add("Importe Iva");
    	ret.add("Importe Otros");
    	ret.add("Importe Total");
    	ret.add("Producto");
    	ret.add("Descripcion");
    	return ret;
    }
    public List<String> getColumnasDocumentosAsociados(){
    	List<String> ret=new ArrayList<String>();
    	ret.add("Documentos Asociados");
    	ret.add("Identificador");
    	ret.add("Tipo de Registro");
    	ret.add("Tipo Documento");
    	ret.add("Serie");
    	ret.add("Numero");
    	ret.add("Fecha Documento");
    	return ret;
    }
    public List<String> getColumnasTotales(){
    	List<String> ret=new ArrayList<String>();
    	ret.add("Totales");
    	ret.add("Identificador");
    	ret.add("Tipo de Registro");
    	ret.add("Importe Bruto");
    	ret.add("Importe Descuento");
    	ret.add("Importe Exento");
    	ret.add("Importe Neto");
    	ret.add("Importe IVA");
    	ret.add("Importe Otros");
    	ret.add("Importe Total");
    	ret.add("Porcentaje ISR");
    	ret.add("Importe ISR");
    	ret.add("Registros Detalle");
    	ret.add("Documentos Asociados");
    	return ret;
    }

    //subir archivos
    //validar formato excel
    //validar datos excel
    //crear objetos
    //guardar objetos
    //validar datos
    //refrescar tabla
    public void getCargarArchivos(FileUploadEvent event) {
        try {
            Date fecha = new Date();
            BigInteger usuario = new BigInteger(userInSession.getUserId().toString());
            HashMap<String, Object> paramsCopia = new HashMap<String, Object>();
            paramsCopia.put("fecha", fecha);
            paramsCopia.put("usuario", usuario);

            InputStream stream = event.getFile().getInputstream();
            ExcelParser eu = new ExcelParser();
            XSSFWorkbook hssfWorkbook = new XSSFWorkbook(stream);
            XSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
            HashMap<Object, Class<?>> clases = new HashMap<Object, Class<?>>();
            //
            clases.put("Encabezado", HdrDocPojo.class);
            clases.put("Detalle", DtlDocPojo.class);
            clases.put("Documento", AssocDocPojo.class);

            List<Integer> indexGuardar = new ArrayList<Integer>();
            indexGuardar.add(1);

            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("mapObjetos", clases);
            params.put("indexGuardar", indexGuardar);
            params.put("rowIni", 3);
            List<ValidationPojo> listErrores=fv.validateCargaExcel(params, hssfSheet,FacesContext.getCurrentInstance());
            if (ValidationPojo.isValid(listErrores)) {
                List<Object> res = eu.getExcelSheetToObject(hssfSheet, params);
                GfaceHdrDoc headerActual = null;
                for (Object key : res) {
                    Object[] data = (Object[]) key;
                    //en el espacio 0 se devuelve la informacion del excel
                    Object o = (Object) data[0];
                    //en el espacio 1 se devuelven los indexGuardar que en este caso son los id de los campos a relacionar
                    List<Object> id = (List<Object>) data[1];
                    //se considera que las lineas del documento se van a ingresar en el orden
                    // encabezado
                    // detalle
                    // documentos asociados 
                    if (o instanceof HdrDocPojo) {
                    	List<ValidationPojo> vp=fv.validateHeader(key);
                        if (ValidationPojo.isValid(vp)) {
                            HdrDocPojo nuevoHeader = (HdrDocPojo) o;
                            GfaceHdrDoc ingresado = nuevoHeader.transformToEntity(nuevoHeader);
                            String stringValue = id.get(0).toString();
                            Double doubleValue = Double.parseDouble(stringValue);
                            Integer intValue = new Integer(doubleValue.intValue());
                            BigInteger idCampo = new BigInteger(intValue.toString());
                            GfaceHdrDoc almacenado = gfaceHdrDocEjbLocal.find(idCampo);
                            List<GfaceHdrDoc> hdrParaModificar = new ArrayList<GfaceHdrDoc>();
                            //el id existe en la BD
                            if (almacenado != null) {
                                ingresado.setSttRgsId(almacenado.getSttRgsId());
                                //cambia el estado del registro anterior
                                almacenado.setFlgSt(GfaceConstants.REG_INACTIVE);
                                hdrParaModificar.add(almacenado);
                            }
                            ingresado.setFlgSt(GfaceConstants.REG_ACTIVE);
                            ingresado.getSttRgsId().setRgsStCode(ST_PEND_VALID);
                            hdrParaModificar.add(ingresado);

                            paramsCopia.put("registros", hdrParaModificar);
                            gfaceHdrDocEjbLocal.merge(paramsCopia,true);
                            //header ya con el ID
                            headerActual = ingresado;

	                        //get header desde BD por el id
                            //get estado del header para asignarlo al nuevo header
                            //cambiar estado del header anterior
                            //agregar a la lista de datos a actualizar
                        }else{
                        	GfaceUtilities.printErrores(vp);
                        }
                        
                    }
                    if (o instanceof DtlDocPojo) {
                    	List<ValidationPojo> vp=fv.validateDetail(key);
                        if (ValidationPojo.isValid(vp)) {
                            DtlDocPojo nuevoDet = (DtlDocPojo) o;
                            GfaceDtlDoc ingresado = nuevoDet.transformToEntity(nuevoDet);
                            ingresado.setHdrId(headerActual);
                            List<GfaceDtlDoc> dtlParaModificar = new ArrayList<GfaceDtlDoc>();
                            dtlParaModificar.add(ingresado);
                            paramsCopia.put("registros", dtlParaModificar);
                            gfaceDtlDocEjbLocal.merge(paramsCopia);
                        }else{
                        	GfaceUtilities.printErrores(vp);
                        }
                    }
                    if (o instanceof AssocDocPojo) {
                    	List<ValidationPojo> vp=fv.validateDocuments();
                        if (ValidationPojo.isValid(vp)) {
                            AssocDocPojo nuevoDoc = (AssocDocPojo) o;
                            GfaceAssocDoc ingresado = nuevoDoc.transformToEntity(nuevoDoc);
                            ingresado.setHdrId(headerActual);
                            List<GfaceAssocDoc> docParaModificar = new ArrayList<GfaceAssocDoc>();
                            docParaModificar.add(ingresado);
                            paramsCopia.put("registros", docParaModificar);
                            gfaceAssocDocEjbLocal.merge(paramsCopia);
                        }else{
                        	GfaceUtilities.printErrores(vp);
                        }
                    }
                }
                
                List<GfaceHdrDoc> pv = sc.getPendientesValidar();
                iv.validacionInterna(pv,userInSession);
                
                List<GfaceHdrDoc> lpe = sc.getListasParaEnviar();
                gfaceSendDataToGuatefacturasEjbLocal.procesarArchivosValidados(lpe, userInSession);
                initd();
                JsfUtil.addSuccessMessage(rbGface.getString("gface_ExcelUploadCompleted"));
            }else{
            	GfaceUtilities.printErrores(listErrores);
            }

        } catch (Exception e) {
        	JsfUtil.addErrorMessage(rbGface.getString("gface_exceptionExcelUpload"));
        }
    }

    public void onToggleColumn(ToggleEvent e) {
        this.getSeleccionados().get((Integer) e.getData()).setVisible(e.getVisibility() == Visibility.VISIBLE);
    }

    public UnUser getUserInSession() {
        return userInSession;
    }

    public void setUserInSession(UnUser userInSession) {
        this.userInSession = userInSession;
    }
    
    public Date getFechaActual(){
    	return new Date();
    }

}
