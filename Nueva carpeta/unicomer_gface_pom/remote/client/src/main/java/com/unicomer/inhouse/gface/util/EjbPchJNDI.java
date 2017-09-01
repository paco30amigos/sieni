package com.unicomer.inhouse.gface.util;

public class EjbPchJNDI {
	
	public static final String JNDI_EJB_SEC = "java:global/UnicomerEnterpriseSecurity/Ejb-Security";
    public static final String JNDI_EJB_OTH = "java:global/UnicomerEnterpriseOthers/Ejb-AdminHoth";
    public static final String JNDI_EJB_SEC_HR = "java:global/UnicomerEnterpriseSecurity/Ejb-Hr";
    public static final String JNDI_EJB_SCHEDULER = "java:global/unicomer_scheduler-ear/unicomer_scheduler-ejb";
    
    public static final String EJB_BASE = "java:global/unicomer_gface-ear/unicomer_gface-ejb";
	public static final String EJB_GfaceOthersMigrationEjbLocal = EJB_BASE + "/GfaceOthersMigrationEjbLocalImpl!com.unicomer.opos.inhouse.gface.ejb.GfaceOthersMigrationEjbLocal";
	public static final String EJB_GfaceGuatefacturaFileReaderEjbLocal = EJB_BASE + "/GfaceGuatefacturaFileReaderEjbLocalImpl!com.unicomer.opos.inhouse.gface.ejb.GfaceGuatefacturaFileReaderEjbLocal";
	public static final String EJB_GfaceSendDataToGuatefacturasEjbLocal = EJB_BASE + "/GfaceSendDataToGuatefacturasEjbLocalImpl!com.unicomer.opos.inhouse.gface.ejb.GfaceSendDataToGuatefacturasEjbLocal";
	public static final String EJB_InHouseSchedulerEjbLocal = JNDI_EJB_SCHEDULER + "/InHouseSchedulerEjbLocalImpl!com.unicomer.opos.inhouse.scheduler.ejb.InHouseSchedulerEjbLocal";
}
