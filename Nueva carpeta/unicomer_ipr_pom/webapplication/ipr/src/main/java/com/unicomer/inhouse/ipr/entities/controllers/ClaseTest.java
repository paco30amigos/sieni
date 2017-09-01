//package com.unicomer.inhouse.ipr.entities.controllers;
//
//import javax.annotation.Resource;
//import javax.faces.bean.ManagedBean;
//
//import com.unicomer.ejb.opos.inhouse.ipr.classes.IprOthersMigrationEjbRemote;
//import com.unicomer.ejb.opos.inhouse.ipr.classes.IprTrxLineItmEjbRemote;
//import com.unicomer.inhouse.jndi.JNDIUnicomerIpr;
//import com.unicomer.mdb.service.IprSendDataToRiMDBEjbRemote;
//
//@ManagedBean(name = "claseTest")
//public class ClaseTest {
//
//    @Resource(lookup = JNDIUnicomerIpr.IprOthersMigrationEjbRemote)
//    private IprOthersMigrationEjbRemote iprOthersMigrationEjbRemote;
//
//    @Resource(lookup = "ejb.IprSendDataToRiMDBEjbLocalImpl#com.unicomer.mdb.service.IprSendDataToRiMDBEjbRemote")
//    private IprSendDataToRiMDBEjbRemote iprReceiveFromRiEjb;
//
//    @Resource(lookup = JNDIUnicomerIpr.IprTrxLineItmEjbRemote)
//    private IprTrxLineItmEjbRemote iprTrxLineItmEjbRemote;
//
//    public void useResource() {
//        iprOthersMigrationEjbRemote.getOtherTransaction("GT");
//        iprReceiveFromRiEjb.sendDataToRi();
//        iprTrxLineItmEjbRemote.finishReadyProducts();
//    }
//
//    public void receiveFromOthers() {
//        iprOthersMigrationEjbRemote.getOtherTransaction("GT");
//    }
//
//    public void sendDataToRI() {
//        iprReceiveFromRiEjb.sendDataToRi();
//    }
//
//}
