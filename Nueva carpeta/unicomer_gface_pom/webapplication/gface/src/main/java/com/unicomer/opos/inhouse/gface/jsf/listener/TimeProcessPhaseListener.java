package com.unicomer.opos.inhouse.gface.jsf.listener;

import java.util.Map;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 * Clase encargada de medir tiempos en procesamiento de paginas
 * @author mauricio_jovel
 * @version 1.0
 * @since 2015-03-05 08:47
 */
public class TimeProcessPhaseListener implements PhaseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long initTime;
	private String methodExpression = "";
	
	@Override
	public void afterPhase(PhaseEvent phaseEvent) {
		long endTime = System.currentTimeMillis();
		double calTime = (endTime - initTime)/1000.0;
		FacesContext fc = phaseEvent.getFacesContext();
		if(phaseEvent.getPhaseId() == PhaseId.RENDER_RESPONSE) {
			UIComponent component = findInvokedCommandComponent(phaseEvent.getFacesContext());
			if (component != null) {
				methodExpression = component.getId(); 
	        } else {
	        	methodExpression = "";
	        }
			System.out.println(fc.getViewRoot().getViewId() + " " + methodExpression + " " +calTime + " S.");
			System.out.println("=============================================");
			System.out.println("Fin carga datos: "+fc.getViewRoot().getViewId()+" " + methodExpression);
			System.out.println("=============================================");
			initTime = 0;
		}
	}

	@Override
	public void beforePhase(PhaseEvent phaseEvent) {
		if(phaseEvent.getPhaseId() == PhaseId.RESTORE_VIEW
				|| (phaseEvent.getPhaseId() == PhaseId.RENDER_RESPONSE && initTime == 0)) {
			initTime = System.currentTimeMillis();
//			UIComponent component = findInvokedCommandComponent(phaseEvent.getFacesContext());
//			FacesContext fc = phaseEvent.getFacesContext();
//			if (component != null) {
//				methodExpression = component.getId(); 
//	        } else {
//	        	methodExpression = "";
//	        }
			
			System.out.println("=============================================");
			System.out.println("Inicio carga datos. ");
			System.out.println("=============================================");
			
		}
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}
	
	private UIComponent findInvokedCommandComponent(FacesContext context) {
	    UIViewRoot view = context.getViewRoot();
	    Map<String, String> params = context.getExternalContext().getRequestParameterMap();

	    if (context.getPartialViewContext().isAjaxRequest()) {
	    	UIComponent component = view.findComponent(params.get("javax.faces.source"));
//	    	if(component instanceof ClientBehaviorHolder) {
//	    		ClientBehaviorHolder holder = (ClientBehaviorHolder) component;
//	    		for (List<ClientBehavior> behavoirs : holder.getClientBehaviors().values()) {
//					for (ClientBehavior clientBehavior : behavoirs) {
//						if (clientBehavior instanceof AjaxBehavior) {
//							AjaxBehavior ajax = (AjaxBehavior) clientBehavior;
//							ajax.getUpdate();
//						}
//						clientBehavior.toString();
//					}
//				}
//	    	}
	        return component;
	    } else {
	        for (String clientId : params.keySet()) {
	            UIComponent component = view.findComponent(clientId);
	            if (component instanceof UICommand) {
	                return component;
	            }
	        }
	    }
	    return null;
	}

}
