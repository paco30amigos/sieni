/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.converter;

import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import sv.com.mined.sieni.pojos.controller.Combo;

/**
 *
 * @author Laptop
 */
@FacesConverter(value = "combo")
public class ComboConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
        Object ret = null;
        if (arg1 instanceof SelectOneMenu) {
            SelectOneMenu menu = (SelectOneMenu) arg1;
            List<UIComponent> menuItems = (List<UIComponent>) menu.getChildren();

            for (UIComponent item : (List<UIComponent>) menuItems) {
                UISelectItems select = (UISelectItems) item;
                for (Combo actual : (List<Combo>) select.getValue()) {
                    if (arg2.equals(actual.getValue())) {
                        ret = actual;
                    }
                }
            }
        }
        return ret;
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
        String str = "";
        if (arg2 instanceof Combo) {
            str = "" + ((Combo) arg2).getValue();
        }
        return str;
    }
}
