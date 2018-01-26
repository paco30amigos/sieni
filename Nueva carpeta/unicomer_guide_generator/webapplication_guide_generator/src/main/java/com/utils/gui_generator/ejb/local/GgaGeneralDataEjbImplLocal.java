/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils.gui_generator.ejb.local;

import com.utils.gui_generator.model.GgaGeneralData;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author francisco_medina
 */
@Local
public interface GgaGeneralDataEjbImplLocal {

    void create(GgaGeneralData ggaGeneralData);

    void edit(GgaGeneralData ggaGeneralData);

    void remove(GgaGeneralData ggaGeneralData);

    GgaGeneralData find(Object id);

    List<GgaGeneralData> findAll();

    List<GgaGeneralData> findRange(int[] range);

    int count();

    List<GgaGeneralData> getAllGeneralDataFetch();
}
