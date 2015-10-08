/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.util.List;
import sv.com.mined.sieni.model.SieniInteEntrComp;

/**
 *
 * @author francisco_medina
 */
public class DiferentesPojo {

    private int count;
    private List<SieniInteEntrComp> interacc;
    private List<DiferentesPojo> comps2;
    private Long idSuperComp;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<SieniInteEntrComp> getInteracc() {
        return interacc;
    }

    public void setInteracc(List<SieniInteEntrComp> interacc) {
        this.interacc = interacc;
    }

    public List<DiferentesPojo> getComps2() {
        return comps2;
    }

    public void setComps2(List<DiferentesPojo> comps2) {
        this.comps2 = comps2;
    }

    public Long getIdSuperComp() {
        return idSuperComp;
    }

    public void setIdSuperComp(Long idSuperComp) {
        this.idSuperComp = idSuperComp;
    }

}
