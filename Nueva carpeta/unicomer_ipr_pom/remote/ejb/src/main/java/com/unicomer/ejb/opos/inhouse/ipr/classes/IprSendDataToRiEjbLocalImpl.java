///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.unicomer.ejb.opos.inhouse.ipr.classes;
//
//import java.math.BigInteger;
//import java.util.List;
//
//import javax.annotation.Resource;
//import javax.ejb.Remote;
//import javax.ejb.Stateless;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//import org.apache.log4j.Logger;
//
//import com.unicomer.batch.inhouse.IprSendRi;
//import com.unicomer.batch.inhouse.IprSendRi.IprProductLineList;
//import com.unicomer.inhouse.jndi.JNDIUnicomerOthers;
//import com.unicomer.opos.inhouse.admiHoTh.service.SendToRiService;
//import com.unicomer.opos.inhouse.ipr.entities.IprTrxLineItm;
//import com.unicomer.utility.JaxbHelper;
//
///**
// *
// * @author francisco_medina
// */
//@Stateless(name = "IprSendDataToRiEjbLocalImpl", mappedName = "ejb/IprSendDataToRiEjbLocalImpl")
//@Remote(IprSendDataToRiEjbRemote.class)
//public class IprSendDataToRiEjbLocalImpl implements IprSendDataToRiEjbRemote {
//
//	private static Logger logger = Logger.getLogger(IprSendDataToRiEjbLocalImpl.class);
//	
//	@Resource(lookup = JNDIUnicomerOthers.SendToRiService)
//	SendToRiService sendToRiService;
//	
//    @PersistenceContext(unitName = "IhIprPersistence")
//    private EntityManager em;
//
//    //obtener informacion a enviar
//    public void sendDataToRi(String countryCode){
//    	List<IprTrxLineItm> productos;
//    	IprSendRi ro=new IprSendRi();//datos a enviar
//    	try {
//    		IprProductLineList pl=new IprProductLineList();
//    		pl.setCountryCode("GT");
//    		pl.setDocumentNumber("101");
//    		pl.setUPC("1010");
//    		ro.getIprProductLineLists().add(pl);
//    		String xml=JaxbHelper.marshal(IprSendRi.class, ro);
//        	BigInteger company=new BigInteger("4");
//        	BigInteger country=new BigInteger("3");
//    		sendToRiService.execute(xml, company, country, "IPR", "", "", "1", "");
//		} catch (Exception e) {
//			logger.error("Error al enviar productos hacia RI:"+e.getMessage());
//		}
//    
////    	OthSendA
//    }
//    
//    //parametro de configuracion por pais
//    //enviar utilizando conector cliente hacia cola JMS
//}
