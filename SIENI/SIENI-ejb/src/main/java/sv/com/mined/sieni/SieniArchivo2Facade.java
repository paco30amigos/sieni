/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sv.com.mined.sieni.model.SieniArchivo2;

/**
 *
 * @author Laptop
 */
@Stateless
public class SieniArchivo2Facade extends AbstractFacade<SieniArchivo2> implements sv.com.mined.sieni.SieniArchivo2FacadeRemote {

    @PersistenceContext(unitName = "sieni_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SieniArchivo2Facade() {
        super(SieniArchivo2.class);
    }

    @Override
    public List<SieniArchivo2> merge(List<SieniArchivo2> lista, List<SieniArchivo2> eliminados) {
        for (SieniArchivo2 actual : lista) {
            if (actual.getIdArchivo() != null) {
                this.edit(actual);
            } else {
                this.create(actual);
            }
        }

        for (SieniArchivo2 actual : eliminados) {
            if (actual.getIdArchivo() != null) {
                actual.setArEstado("I");//eliminacion logica
                this.edit(actual);
            }
        }
        em.flush();

        return lista;
    }

    @Override
    public SieniArchivo2 merge(SieniArchivo2 dato) {

        if (dato.getIdArchivo() != null) {
            this.edit(dato);
        } else {
            this.create(dato);
        }
        return dato;
    }
}
