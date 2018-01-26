/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils.gui_generator.ejb.local;

import com.utils.gui_generator.model.GgaLinkExtraData;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author francisco_medina
 */
@Local
public interface GgaLinkExtraDataEjbImplLocal {

    void create(GgaLinkExtraData ggaLinkExtraData);

    void edit(GgaLinkExtraData ggaLinkExtraData);

    void remove(GgaLinkExtraData ggaLinkExtraData);

    GgaLinkExtraData find(Object id);

    List<GgaLinkExtraData> findAll();

    List<GgaLinkExtraData> findRange(int[] range);

    int count();
    
}
