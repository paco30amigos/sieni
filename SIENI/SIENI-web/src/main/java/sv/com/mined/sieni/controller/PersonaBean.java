package sv.com.mined.sieni.controller;

import sv.com.mined.sieni.model.Persona;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

@SessionScoped
@ManagedBean(name = "personaBean")
public class PersonaBean implements Serializable  {

    private Persona persona = new Persona();
    private static List<Persona> lista = new ArrayList();

    public List<Persona> getLista() {
        return lista;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public void setLista(List<Persona> lista) {
        this.lista = lista;
    }

    public void agregar() {
        this.lista.add(persona);
        notificarPUSH();
        persona = new Persona();
    }

    public void notificarPUSH() {

        String summary = "Nuevo Elemento";
        String detail = "Se agrego a la lista";
        String CHANNEL = "/notify";

        EventBus eventBus = EventBusFactory.getDefault().eventBus();
        eventBus.publish(CHANNEL, new FacesMessage(summary, detail));
        
    }
}
