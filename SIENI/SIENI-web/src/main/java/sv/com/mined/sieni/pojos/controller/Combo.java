/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.pojos.controller;

/**
 *
 * @author francisco_medina
 */
public class Combo {

    public Combo() {
    }

    public Combo(String value, String description, Object elem) {
        this.value = value;
        this.description = description;
        this.elem = elem;
    }
    private String value;
    private String description;
    private Object elem;
    private Combo self;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getElem() {
        return elem;
    }

    public void setElem(Object elem) {
        this.elem = elem;
    }

    public Combo getSelf() {
        return this;
    }
}