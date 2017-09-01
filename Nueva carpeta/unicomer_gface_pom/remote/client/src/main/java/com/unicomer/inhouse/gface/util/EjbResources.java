package com.unicomer.inhouse.gface.util;



public class EjbResources {
    
    public enum Application {SECURITY,OTHERS,gface} 
    
    /**
     * Base JNDI para el proyecto de gface
     * 
     */
    public static final String JNDI_EJB = EjbPchJNDI.EJB_BASE;
    public static final String JNDI_EJB_SEC = EjbPchJNDI.JNDI_EJB_SEC;
    public static final String JNDI_EJB_OTH = EjbPchJNDI.JNDI_EJB_OTH;
    public static final String JNDI_EJB_SEC_HR = EjbPchJNDI.JNDI_EJB_SEC_HR;
    
    /**
     * Retorna el nombre jndi utilizado para poder buscar un web resource
     * de manera local
     * @param impl
     * @param inter
     * @return
     */
    public static <T, K> String getRemoteEJB(Class<T> impl, Class<K> inter) {
    	String s = JNDI_EJB + "/" +impl.getSimpleName()+"!"+
    				inter.getCanonicalName();
    	return s;
    }
    
    /**
     * Retorna el nombre jndi utilizado para poder buscar un web resource
     * 
     * @param impl
     * @param inter
     * @return
     */
    public static <T, K> String getRemoteEJB(Class<T> impl, Class<K> inter,Application app) {
    	String baseJNDI = null;
    	switch(app){
    	case SECURITY:
    		baseJNDI = JNDI_EJB_SEC;
    		break;
    	case OTHERS:
    		baseJNDI = JNDI_EJB_OTH;
    		break;
    	case gface:
    		baseJNDI = JNDI_EJB;
    		break;
    	}
    	String s = baseJNDI + "/" +impl.getSimpleName()+"!"+
    				inter.getCanonicalName();
    	return s;
    }
    
    public static <T, K> String getRemoteEJB(String impl, String interf,Application app) {
        String baseJNDI = null;
        switch(app){
        case SECURITY:
            baseJNDI = JNDI_EJB_SEC;
            break;
        case OTHERS:
            baseJNDI = JNDI_EJB_OTH;
            break;
        case gface:
            baseJNDI = JNDI_EJB;
            break;
        }
        String s = baseJNDI + "/" +impl+"!"+interf;
        return s;
    }
    
    /**
     * Retorna el nombre jndi utilizado para poder buscar un web resource
     * 
     * @param alias
     * @param inter
     * @return
     */
    public static <T, K> String getRemoteEJB(String alias, Class<K> inter,Application app) {
    	String baseJNDI = null;
    	switch(app){
    	case SECURITY:
    		baseJNDI = JNDI_EJB_SEC;
    		break;
    	case OTHERS:
    		baseJNDI = JNDI_EJB_OTH;
    		break;
    	case gface:
    		baseJNDI = JNDI_EJB;
    		break;
    	}
    	String s = baseJNDI + "/" +inter.getSimpleName()+(alias==null?"":alias)+"!"+
    				inter.getCanonicalName();
    	return s;
    }
    
    /**
     * 
     * @param inter
     * @param app
     * @return
     */
    public static <T, K> String getRemoteEJB(Class<K> inter,Application app) {
    	String s = null;
    	return getRemoteEJB(s, inter, app);
    }
}
