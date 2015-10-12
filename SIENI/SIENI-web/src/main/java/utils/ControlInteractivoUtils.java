/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import sv.com.mined.sieni.model.SieniClaseSupComp;
import sv.com.mined.sieni.model.SieniCompInteraccion;
import sv.com.mined.sieni.model.SieniInteEntrComp;
import sv.com.mined.sieni.pojos.controller.CodigoComponente;
import sv.com.mined.sieni.pojos.controller.CodigoEvento;
import sv.com.mined.sieni.pojos.controller.ComponenteInteractivoPojo;
import sv.com.mined.sieni.pojos.controller.EventosPojo;
import sv.com.mined.sieni.pojos.controller.InteraccionesCompPojo;
import sv.com.mined.sieni.pojos.controller.PantallaPojo;
import sv.com.mined.sieni.pojos.controller.SeccionPlantillaPojo;
import sv.com.mined.sieni.pojos.controller.SuperCompPojo;

/**
 *
 * @author francisco_medina
 */
public class ControlInteractivoUtils {

    private List<SeccionPlantillaPojo> secciones;
    private List<SieniInteEntrComp> totalInteracc;

    public String getCodigoEventosEntreComp() {
        List<CodigoEvento> listEv;
        String funcion = "";
        for (SeccionPlantillaPojo sec : this.getSecciones()) {
            for (PantallaPojo pant : sec.getPantallas()) {
                Long idTipoElemPlantilla = sec.getIdElemPlantilla().getIdTipoElemPlantilla().getIdTipoElemPlantilla();
                Integer nPantalla = pant.getNumPantalla();
                List<SieniInteEntrComp> listInteEntreComp = getInterEntreCompByTipoElemPlantillaPantalla(idTipoElemPlantilla, nPantalla);
                HashMap<String, Integer> eventosDiferentes = getEventosNumerados(listInteEntreComp);
                eventosDiferentes = agregarEventosNoRelacionados(eventosDiferentes, idTipoElemPlantilla, nPantalla);
                //agregar eventos que no son de interaccion entre componentes
                HashMap<Long, Integer> componentesDiferentes = getComponentesNumerados(listInteEntreComp);
                componentesDiferentes = agregarComponentesNoRelacionados(componentesDiferentes, idTipoElemPlantilla, nPantalla);

                //agregar componentes que son parte de la pagina pero no estan en la interaccion entre componentes
//                HashMap<Long, SuperCompPojo> l = agruparPorComp(listInteEntreComp,idTipoElemPlantilla, nPantalla);
                HashMap<Long, SuperCompPojo> l = agruparPorCompNoRelacionados(new HashMap<Long, SuperCompPojo>(), idTipoElemPlantilla, nPantalla);
                //agregar componentes que son parte de la pagina pero no estan en la interaccion entre componentes
                CodigoEvento ev = new CodigoEvento();
                CodigoComponente comp = new CodigoComponente();
                List<CodigoComponente> listComp = new ArrayList<>();
                List<EventosPojo> evDif = getEventosDiferentes(listInteEntreComp);
                evDif = getEventosDiferentesNoRelacionados(evDif, idTipoElemPlantilla, nPantalla);
                //agregar eventos de componentes que son parte de la pagina pero no estan en la interaccion entre componentes
                Long idEventoActual;
                String codEvento;
                listEv = new ArrayList<>();
                //agrega interacciones de componentes relacionados
                for (EventosPojo actual : evDif) {
                    ev = new CodigoEvento();
                    listComp = new ArrayList<>();
                    idEventoActual = actual.getIdEvento();
                    codEvento = actual.getEvento();
                    List<Long> compEv = getComponByEvento(codEvento, listInteEntreComp);
                    compEv = getComponByEventoNoRelacionados(compEv, codEvento, idTipoElemPlantilla, nPantalla);
                    for (Long idCompActual : compEv) {
                        comp = new CodigoComponente();
                        comp.setIdSuperCompon(idCompActual);
                        comp.setVisible(l.get(idCompActual).isVisible());
                        comp.setInteracciones(l.get(idCompActual).getInteraccionesCompPojoByEvento(codEvento));
                        comp.setContEvento(eventosDiferentes.get(codEvento));
                        comp.setCont(componentesDiferentes.get(idCompActual));
                        comp.setEvento(codEvento);
                        comp.setComps2(getComps2(eventosDiferentes, componentesDiferentes, listInteEntreComp, idCompActual, codEvento));
                        listComp.add(comp);
                    }
                    ev.setComponentes(listComp);
                    ev.setCont(eventosDiferentes.get(codEvento));
                    listEv.add(ev);
                }
//                listEv.addAll(agregarEventosComponentesNoRelacionados(evDif, componentesDiferentes, l, listInteEntreComp.get(0).getIdTipoElemPlantilla().getIdTipoElemPlantilla(), listInteEntreComp.get(0).getIeNPantalla()));
                if (listEv != null && !listEv.isEmpty()) {
                    funcion += crearFuncion(listEv, idTipoElemPlantilla, nPantalla);
                }
            }
        }
        return funcion;
    }

    public List<SieniInteEntrComp> getInterEntreCompByTipoElemPlantillaPantalla(Long idTipoElemPlantilla, Integer nPantalla) {
        List<SieniInteEntrComp> ret = new ArrayList<>();
        List<SieniInteEntrComp> listInteEntreComp = totalInteracc;
        for (SieniInteEntrComp el : listInteEntreComp) {
            if (el.getIdTipoElemPlantilla().getIdTipoElemPlantilla().equals(idTipoElemPlantilla) && el.getIeNPantalla().equals(nPantalla)) {
                ret.add(el);
            }
        }
        return ret;
    }

    public List<List<SieniInteEntrComp>> getInteracByTipoPlantilla(List<SieniInteEntrComp> listInteEntreComp) {
        List<List<SieniInteEntrComp>> ret = new ArrayList<>();
        HashMap<Long, List<SieniInteEntrComp>> dif = new HashMap<>();
        for (SieniInteEntrComp actual : listInteEntreComp) {
            if (!dif.containsKey(actual.getIdTipoElemPlantilla().getIdTipoElemPlantilla())) {
                dif.put(actual.getIdTipoElemPlantilla().getIdTipoElemPlantilla(), new ArrayList<SieniInteEntrComp>());
                dif.get(actual.getIdTipoElemPlantilla().getIdTipoElemPlantilla()).add(actual);
            } else {
                dif.get(actual.getIdTipoElemPlantilla().getIdTipoElemPlantilla()).add(actual);
            }
        }
        for (List<SieniInteEntrComp> actual : dif.values()) {
            ret.add(actual);
        }
        return ret;
    }

    public List<List<SieniInteEntrComp>> getInteracByPantalla(List<SieniInteEntrComp> listInteEntreComp) {
        List<List<SieniInteEntrComp>> ret = new ArrayList<>();
        HashMap<Integer, List<SieniInteEntrComp>> dif = new HashMap<>();
        for (SieniInteEntrComp actual : listInteEntreComp) {
            if (!dif.containsKey(actual.getIeNPantalla())) {
                dif.put(actual.getIeNPantalla(), new ArrayList<SieniInteEntrComp>());
                dif.get(actual.getIeNPantalla()).add(actual);
            } else {
                dif.get(actual.getIeNPantalla()).add(actual);
            }
        }
        for (List<SieniInteEntrComp> actual : dif.values()) {
            ret.add(actual);
        }
        return ret;
    }

    public HashMap<String, Integer> getEventosNumerados(List<SieniInteEntrComp> interac) {
        HashMap<String, Integer> ret = new HashMap<>();
        int cont = 1;
        for (SieniInteEntrComp comp : interac) {
            if (!ret.containsKey(comp.getIeEventoC1().getEvCodigo())) {
                ret.put(comp.getIeEventoC1().getEvCodigo(), cont);
                cont++;
            } else if (!ret.containsKey(comp.getIeEventoC2().getEvCodigo())) {
                ret.put(comp.getIeEventoC2().getEvCodigo(), cont);
                cont++;
            }
        }
        return ret;
    }

    public HashMap<String, Integer> agregarEventosNoRelacionados(HashMap<String, Integer> eventosDiferentes, Long idTipoElemPlantilla, Integer nPantalla) {
        HashMap<String, Integer> ret = new HashMap<>();
        ret.putAll(eventosDiferentes);
        Integer cont = ret.size() + 1;

        boolean encontrado = false;
        for (SeccionPlantillaPojo sec : this.getSecciones()) {
            //si es la seccion actual
            if (sec.getIdElemPlantilla().getIdTipoElemPlantilla().getIdTipoElemPlantilla().equals(idTipoElemPlantilla)) {
                for (PantallaPojo pant : sec.getPantallas()) {
                    if (pant.getNumPantalla().equals(nPantalla)) {
                        for (ComponenteInteractivoPojo comp : pant.getComponentes()) {
                            for (SieniCompInteraccion inte : comp.getInteracciones()) {
                                if (!ret.containsKey(inte.getIdEvento().getEvCodigo())) {
                                    ret.put(inte.getIdEvento().getEvCodigo(), cont++);
                                }
                            }
                        }
                        encontrado = true;
                        break;
                    }
                }
                //cierra el bucle para optimizar tiempo
                if (encontrado) {
                    break;
                }
            }
        }
        return ret;
    }

    public HashMap<Long, Integer> getComponentesNumerados(List<SieniInteEntrComp> interac) {
        HashMap<Long, Integer> ret = new HashMap<>();
        int cont = 1;
        for (SieniInteEntrComp comp : interac) {
            if (!ret.containsKey(comp.getIeSupC1().getIdSuperCompon())) {
                ret.put(comp.getIeSupC1().getIdSuperCompon(), cont);
                cont++;
            }
            if (!ret.containsKey(comp.getIeSupC2().getIdSuperCompon())) {
                ret.put(comp.getIeSupC2().getIdSuperCompon(), cont);
                cont++;
            }
        }
        return ret;
    }

    public HashMap<Long, Integer> agregarComponentesNoRelacionados(HashMap<Long, Integer> componentesDiferentes, Long idTipoElemPlantilla, Integer nPantalla) {
        HashMap<Long, Integer> ret = new HashMap<>();
        ret.putAll(componentesDiferentes);
        Integer cont = ret.size() + 1;

        boolean encontrado = false;
        for (SeccionPlantillaPojo sec : this.getSecciones()) {
            //si es la seccion actual
            if (sec.getIdElemPlantilla().getIdTipoElemPlantilla().getIdTipoElemPlantilla().equals(idTipoElemPlantilla)) {
                for (PantallaPojo pant : sec.getPantallas()) {
                    if (pant.getNumPantalla().equals(nPantalla)) {
                        for (ComponenteInteractivoPojo comp : pant.getComponentes()) {
                            if (!ret.containsKey(comp.getSuperComp().getIdSuperCompon())) {
                                ret.put(comp.getSuperComp().getIdSuperCompon(), cont++);
                            }
                        }
                        encontrado = true;
                        break;
                    }
                }
                //cierra el bucle para optimizar tiempo
                if (encontrado) {
                    break;
                }
            }
        }
        return ret;
    }

    public HashMap<Long, SuperCompPojo> agruparPorComp(List<SieniInteEntrComp> intEntComp, Long idTipoElemPlantilla, Integer nPantalla) {
        List<SieniCompInteraccion> interActual;
        HashMap<Long, SuperCompPojo> interaccionesBySuperComp = new HashMap<>();
        SuperCompPojo nuevo;
        int count = 1;
        for (SieniInteEntrComp comp : intEntComp) {
            if (!interaccionesBySuperComp.containsKey(comp.getIeSupC1().getIdSuperCompon())) {
                interActual = getInteractBySuperCompon(comp.getIeSupC1().getIdSuperCompon(), idTipoElemPlantilla, nPantalla);
                SieniClaseSupComp aux = getCalseSuperComp(comp.getIeSupC1().getIdSuperCompon(), idTipoElemPlantilla, nPantalla);
                nuevo = new SuperCompPojo();
                nuevo.setVisible(!(aux.getScVisible() == null || aux.getScVisible().equals(new Character('N'))));
                nuevo.setCount(count);
                nuevo.setEventos(agruparEvento(interActual));
                nuevo.setSupComp(comp.getIeSupC1());
                interaccionesBySuperComp.put(comp.getIeSupC1().getIdSuperCompon(), nuevo);
                count++;
            }
            if (!interaccionesBySuperComp.containsKey(comp.getIeSupC2().getIdSuperCompon())) {
                interActual = getInteractBySuperCompon(comp.getIeSupC2().getIdSuperCompon(), idTipoElemPlantilla, nPantalla);
                SieniClaseSupComp aux = getCalseSuperComp(comp.getIeSupC2().getIdSuperCompon(), idTipoElemPlantilla, nPantalla);
                nuevo = new SuperCompPojo();
                nuevo.setVisible(!(aux.getScVisible() == null || aux.getScVisible().equals(new Character('N'))));
                nuevo.setCount(count);
                nuevo.setEventos(agruparEvento(interActual));
                nuevo.setSupComp(comp.getIeSupC2());
                interaccionesBySuperComp.put(comp.getIeSupC2().getIdSuperCompon(), nuevo);
                count++;
            }
        }
        return interaccionesBySuperComp;
    }

    public List<SieniCompInteraccion> getInteractBySuperCompon(Long idClaseSuperComp, Long idTipoElemPlantilla, Integer nPantalla) {
        List<SieniCompInteraccion> ret = new ArrayList<>();
        for (SeccionPlantillaPojo sec : this.getSecciones()) {
            //si es la seccion actual
            if (sec.getIdElemPlantilla().getIdTipoElemPlantilla().getIdTipoElemPlantilla().equals(idTipoElemPlantilla)) {
                for (PantallaPojo pant : sec.getPantallas()) {
                    if (pant.getNumPantalla().equals(nPantalla)) {
                        for (ComponenteInteractivoPojo comp : pant.getComponentes()) {
                            if (idClaseSuperComp.equals(comp.getSuperComp().getIdSuperCompon())) {
                                ret = comp.getInteracciones();
                                break;
                            }
                        }
                    }
                }
            }
        }
        return ret;
    }

    public SieniClaseSupComp getCalseSuperComp(Long idClaseSuperComp, Long idTipoElemPlantilla, Integer nPantalla) {
        SieniClaseSupComp ret = new SieniClaseSupComp();
        for (SeccionPlantillaPojo sec : this.getSecciones()) {
            //si es la seccion actual
            if (sec.getIdElemPlantilla().getIdTipoElemPlantilla().getIdTipoElemPlantilla().equals(idTipoElemPlantilla)) {
                for (PantallaPojo pant : sec.getPantallas()) {
                    if (pant.getNumPantalla().equals(nPantalla)) {
                        for (ComponenteInteractivoPojo comp : pant.getComponentes()) {
                            if (idClaseSuperComp.equals(comp.getSuperComp().getIdSuperCompon())) {
                                ret = comp.getClaseSuperComp();
                                break;
                            }
                        }
                    }
                }
            }
        }
        return ret;
    }

    public HashMap<Long, SuperCompPojo> agruparPorCompNoRelacionados(HashMap<Long, SuperCompPojo> intEntComp, Long idTipoElemPlantilla, Integer nPantalla) {
        HashMap<Long, SuperCompPojo> ret = new HashMap<>();
        ret.putAll(intEntComp);
        SuperCompPojo nuevo;
        int count = ret.size() + 1;
        boolean encontrado = false;
        for (SeccionPlantillaPojo sec : this.getSecciones()) {
            //si es la seccion actual
            if (sec.getIdElemPlantilla().getIdTipoElemPlantilla().getIdTipoElemPlantilla().equals(idTipoElemPlantilla)) {
                for (PantallaPojo pant : sec.getPantallas()) {
                    if (pant.getNumPantalla().equals(nPantalla)) {
                        for (ComponenteInteractivoPojo comp : pant.getComponentes()) {
                            if (!ret.containsKey(comp.getSuperComp().getIdSuperCompon())) {
                                nuevo = new SuperCompPojo();
                                Character v = comp.getClaseSuperComp().getScVisible();
                                nuevo.setVisible(!(v == null || v.equals(new Character('N'))));
                                nuevo.setCount(count);
                                nuevo.setEventos(agruparEvento(comp.getInteracciones()));
                                nuevo.setEventosEv(agruparEventoCod(comp.getInteracciones()));
                                nuevo.setSupComp(comp.getSuperComp());
                                ret.put(comp.getSuperComp().getIdSuperCompon(), nuevo);
                                count++;
                            }
                        }
                        encontrado = true;
                        break;
                    }
                }
                //cierra el bucle para optimizar tiempo
                if (encontrado) {
                    break;
                }
            }
        }
        return ret;
    }

    public HashMap<Long, List<InteraccionesCompPojo>> agruparEvento(List<SieniCompInteraccion> intEntComp) {
        HashMap<Long, List<InteraccionesCompPojo>> ret = new HashMap<>();
        InteraccionesCompPojo nuevo;
        int cont = 1;
        for (SieniCompInteraccion comp : intEntComp) {
            nuevo = new InteraccionesCompPojo();
            nuevo.setCont(cont);
            nuevo.setInteraccion(comp);
            if (!ret.containsKey(comp.getIdEvento().getIdEvento())) {
                ret.put(comp.getIdEvento().getIdEvento(), new ArrayList<InteraccionesCompPojo>());
                ret.get(comp.getIdEvento().getIdEvento()).add(nuevo);
            } else {
                ret.get(comp.getIdEvento().getIdEvento()).add(nuevo);
            }
            cont++;
        }
        return ret;
    }

    public HashMap<String, List<InteraccionesCompPojo>> agruparEventoCod(List<SieniCompInteraccion> intEntComp) {
        HashMap<String, List<InteraccionesCompPojo>> ret = new HashMap<>();
        InteraccionesCompPojo nuevo;
        int cont = 1;
        for (SieniCompInteraccion comp : intEntComp) {
            nuevo = new InteraccionesCompPojo();
            nuevo.setCont(cont);
            nuevo.setInteraccion(comp);
            if (!ret.containsKey(comp.getIdEvento().getEvCodigo())) {
                ret.put(comp.getIdEvento().getEvCodigo(), new ArrayList<InteraccionesCompPojo>());
                ret.get(comp.getIdEvento().getEvCodigo()).add(nuevo);
            } else {
                ret.get(comp.getIdEvento().getEvCodigo()).add(nuevo);
            }
            cont++;
        }
        return ret;
    }

    public List<EventosPojo> getEventosDiferentes(List<SieniInteEntrComp> interac) {
        List<String> res = new ArrayList<>();
        List<EventosPojo> ret = new ArrayList<>();
        EventosPojo nuevo;
        //TODO falta meter eventos que no son interacciones entre componentes
        for (SieniInteEntrComp comp : interac) {
            if (!res.contains(comp.getIeEventoC1().getEvCodigo())) {
                nuevo = new EventosPojo();
                nuevo.setEvento(comp.getIeEventoC1().getEvCodigo());
                nuevo.setIdEvento(comp.getIeEventoC1().getIdEvento());
                nuevo.setInterac(comp);
                res.add(comp.getIeEventoC1().getEvCodigo());
                ret.add(nuevo);
            }
            if (!res.contains(comp.getIeEventoC2().getEvCodigo())) {
                nuevo = new EventosPojo();
                nuevo.setEvento(comp.getIeEventoC2().getEvCodigo());
                nuevo.setIdEvento(comp.getIeEventoC2().getIdEvento());
                nuevo.setInterac(comp);
                res.add(comp.getIeEventoC2().getEvCodigo());
                ret.add(nuevo);
            }
        }
        return ret;
    }

    public List<EventosPojo> getEventosDiferentesNoRelacionados(List<EventosPojo> eventos, Long idTipoElemPlantilla, Integer nPantalla) {
        List<String> res = new ArrayList<>();
        List<EventosPojo> ret = new ArrayList<>();
        EventosPojo nuevo;
        boolean encontrado = false;
        //agrega los eventos ya existentes
        for (EventosPojo ev : eventos) {
            res.add(ev.getEvento());
            ret.add(ev);
        }

        for (SeccionPlantillaPojo sec : this.getSecciones()) {
            //si es la seccion actual
            if (sec.getIdElemPlantilla().getIdTipoElemPlantilla().getIdTipoElemPlantilla().equals(idTipoElemPlantilla)) {
                for (PantallaPojo pant : sec.getPantallas()) {
                    if (pant.getNumPantalla().equals(nPantalla)) {
                        for (ComponenteInteractivoPojo comp : pant.getComponentes()) {
                            for (SieniCompInteraccion inte : comp.getInteracciones()) {
                                if (!res.contains(inte.getIdEvento().getEvCodigo())) {
                                    nuevo = new EventosPojo();
                                    nuevo.setEvento(inte.getIdEvento().getEvCodigo());
                                    nuevo.setIdEvento(inte.getIdEvento().getIdEvento());
//                                nuevo.setInterac(inte);
                                    res.add(inte.getIdEvento().getEvCodigo());
                                    ret.add(nuevo);
                                }
                            }
                        }
                        encontrado = true;
                        break;
                    }
                }
                //cierra el bucle para optimizar tiempo
                if (encontrado) {
                    break;
                }
            }
        }
        return ret;
    }

    public List<Long> getComponByEvento(String idEvento, List<SieniInteEntrComp> listInteEntreComp) {
        List<Long> ret = new ArrayList<>();
        for (SieniInteEntrComp actual : listInteEntreComp) {
            if (actual.getIeEventoC1().getEvCodigo().equals(idEvento) && !ret.contains(actual.getIeSupC1().getIdSuperCompon())) {
                ret.add(actual.getIeSupC1().getIdSuperCompon());
            }
            if (actual.getIeEventoC2().getEvCodigo().equals(idEvento) && !ret.contains(actual.getIeSupC2().getIdSuperCompon())) {
                ret.add(actual.getIeSupC2().getIdSuperCompon());
            }
        }

        return ret;
    }

    public List<Long> getComponByEventoNoRelacionados(List<Long> compon, String idEvento, Long idTipoElemPlantilla, Integer nPantalla) {
        List<Long> ret = new ArrayList<>();
        ret.addAll(compon);
        boolean encontrado = false;
        for (SeccionPlantillaPojo sec : this.getSecciones()) {
            //si es la seccion actual
            if (sec.getIdElemPlantilla().getIdTipoElemPlantilla().getIdTipoElemPlantilla().equals(idTipoElemPlantilla)) {
                for (PantallaPojo pant : sec.getPantallas()) {
                    if (pant.getNumPantalla().equals(nPantalla)) {
                        for (ComponenteInteractivoPojo comp : pant.getComponentes()) {
                            for (SieniCompInteraccion inte : comp.getInteracciones()) {
                                if (inte.getIdEvento().getEvCodigo().equals(idEvento) && !ret.contains(comp.getSuperComp().getIdSuperCompon())) {
                                    ret.add(comp.getSuperComp().getIdSuperCompon());
                                }
                            }
                        }
                        encontrado = true;
                        break;
                    }
                }
                //cierra el bucle para optimizar tiempo
                if (encontrado) {
                    break;
                }
            }
        }
        return ret;
    }

    //get comp2 de comp1 ev1
    public List<CodigoComponente> getComps2(HashMap<String, Integer> eventosDiferentes,
            HashMap<Long, Integer> componentesDiferentes,
            List<SieniInteEntrComp> listInteEntreComp,
            Long idComp1, String idEvnt1) {
        List<CodigoComponente> listComp = new ArrayList<>();
        CodigoComponente comp;
        List<SieniInteEntrComp> ret = new ArrayList<>();
        for (SieniInteEntrComp actual : listInteEntreComp) {
            if (actual.getIeSupC1().getIdSuperCompon().equals(idComp1)
                    && actual.getIeEventoC1().getEvCodigo().equals(idEvnt1)) {
                ret.add(actual);
            }
        }
        for (SieniInteEntrComp actual : ret) {
            comp = new CodigoComponente();
            comp.setIdSuperCompon(actual.getIeSupC2().getIdSuperCompon());
            comp.setContEvento(eventosDiferentes.get(actual.getIeEventoC2().getEvCodigo()));
            comp.setDelay(actual.getIeRetraso());
            comp.setCont(componentesDiferentes.get(actual.getIeSupC2().getIdSuperCompon()));
            listComp.add(comp);
        }
        return listComp;

    }

    public String crearFuncion(List<CodigoEvento> cods, Long idTipoElemPlantilla, Integer nPantalla) {
        String funcion = "";
        String intermedia = "";
        String eventos = "";
        String showHide = "";
        boolean ultimoAccionCompActual, tieneMultimedia = false, tieneShowHide = false;
        //codigoEvento->lista componentes
        //lista componentes->lista acciones
        int contAcciones = 1;
        //click,dblclick,contextmenu
        for (CodigoEvento evnto : cods) {
            //2
            for (CodigoComponente comp : evnto.getComponentes()) {
                String ubica = nPantalla + "_" + idTipoElemPlantilla + "_";
                String compon = ubica + comp.getIdSuperCompon();

                contAcciones = 1;
                if (comp.getInteracciones() != null && !comp.getInteracciones().isEmpty()) {
                    Integer inteCont = 1;
                    showHide = "";
                    tieneShowHide = false;
                    for (InteraccionesCompPojo inte : comp.getInteracciones()) {
                        //2 interacc
                        switch (inte.getInteraccion().getIdEvento().getEvCodigo()) {
                            case "show":
                                if (!tieneShowHide) {
                                    funcion += " function func_" + evnto.getCont() + "_" + compon + "_" + inteCont + "(){\n"
                                            + "     jQuery(\".compon" + compon + "\").show(\"" + inte.getInteraccion().getIdAccion().getEvCodigo() + "\"," + inte.getInteraccion().getInDuracion() + ", function(){\n"
                                            + "         func_" + evnto.getCont() + "_" + compon + "_" + inteCont + "_intermedia();\n"
                                            + "     });"
                                            + "  };\n";
                                } else {
                                    //corrige bug de multiples acciones en show
                                    funcion += " function func_" + evnto.getCont() + "_" + compon + "_" + inteCont + "(){\n"
                                            + "     jQuery(\".compon" + compon + "\").effect(\"" + inte.getInteraccion().getIdAccion().getEvCodigo() + "\"," + inte.getInteraccion().getInDuracion() + ", function(){\n"
                                            + "         func_" + evnto.getCont() + "_" + compon + "_" + inteCont + "_intermedia();\n"
                                            + "     });"
                                            + "  };\n";
                                    break;
                                }
                                tieneShowHide = true;
                                break;
                            case "hide":
                                if (!tieneShowHide) {
                                    funcion += " function func_" + evnto.getCont() + "_" + compon + "_" + inteCont + "(){\n"
                                            + "     jQuery(\".compon" + compon + "\").hide(\"" + inte.getInteraccion().getIdAccion().getEvCodigo() + "\"," + inte.getInteraccion().getInDuracion() + ", function(){\n"
                                            + "         func_" + evnto.getCont() + "_" + compon + "_" + inteCont + "_intermedia();\n"
                                            + "     });"
                                            + "  };\n";
                                } else {
                                    //corrige bug de multiples acciones en hide
                                    funcion += " function func_" + evnto.getCont() + "_" + compon + "_" + inteCont + "(){\n"
                                            + "     jQuery(\".compon" + compon + "\").effect(\"" + inte.getInteraccion().getIdAccion().getEvCodigo() + "\"," + inte.getInteraccion().getInDuracion() + ", function(){\n"
                                            + "         func_" + evnto.getCont() + "_" + compon + "_" + inteCont + "_intermedia();\n"
                                            + "     });"
                                            + "  };\n";
                                    break;
                                }
                                tieneShowHide = true;
                                break;
                            default:
                                funcion += " function func_" + evnto.getCont() + "_" + compon + "_" + inteCont + "(){\n"
                                        + "     jQuery(\".compon" + compon + "\").effect(\"" + inte.getInteraccion().getIdAccion().getEvCodigo() + "\"," + inte.getInteraccion().getInDuracion() + ", function(){\n"
                                        + "         func_" + evnto.getCont() + "_" + compon + "_" + inteCont + "_intermedia();\n"
                                        + "     });"
                                        + "  };\n";
                                break;
                        }
                        intermedia = "";
                        ultimoAccionCompActual = contAcciones >= comp.getInteracciones().size();
                        if (ultimoAccionCompActual) {
                            if (comp.getComps2() != null && !comp.getComps2().isEmpty()) {
                                //componentes vinculados al componente 1
                                for (CodigoComponente comp2 : comp.getComps2()) {
                                    intermedia += " setTimeout(\n"
                                            + "         function(){\n"
                                            + "             func_" + comp2.getContEvento() + "_" + ubica + comp2.getIdSuperCompon() + "_1();\n"
                                            + "         },\n"
                                            + "         " + (comp2.getDelay() != null ? comp2.getDelay() + "" : "0") + "\n"
                                            + "     );\n";
                                }
                            }
                        } else {//siguiente evento del componente actual
                            intermedia += "func_" + evnto.getCont() + "_" + compon + "_" + (inteCont + 1) + "();\n";
                        }
                        funcion += "function func_" + evnto.getCont() + "_" + compon + "_" + inteCont + "_intermedia(){\n"
                                + "     " + intermedia + "\n"
                                + "}\n";
                        contAcciones++;
                        inteCont++;
                    }
                    //si tiene show y esta oculto 
                    if (!comp.isVisible() && tieneShowHide) {
                        showHide += "setTimeout(function(){jQuery(\".compon" + compon + "\").hide(); },300);\n";
                    }
                    //si no tiene eventos para mostrar u ocultar
                    if (!tieneShowHide) {
                        eventos += "jQuery(\".compon" + compon + "\").on(\"" + comp.getEvento() + "\", function () {\n"
                                + "                        func_" + evnto.getCont() + "_" + compon + "_1();\n"
                                + "                    });\n";
                    }
                    eventos += showHide;

                } else {//funcion eliminada
                    funcion += " function func_" + evnto.getCont() + "_" + compon + "_1(){}\n";
                }
            }
        }
        if (tieneMultimedia) {
            eventos += " configurarMultimedia();\n";
        }
        funcion += "jQuery(document).ready(function () {\n"
                + eventos
                + "});\n";

        return funcion;
    }

    public List<SeccionPlantillaPojo> getSecciones() {
        return secciones;
    }

    public void setSecciones(List<SeccionPlantillaPojo> secciones) {
        this.secciones = secciones;
    }

    public List<SieniInteEntrComp> getTotalInteracc() {
        return totalInteracc;
    }

    public void setTotalInteracc(List<SieniInteEntrComp> totalInteracc) {
        this.totalInteracc = totalInteracc;
    }
}
