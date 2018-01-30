/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils.gui_generator.ejb.local;

import com.utils.gui_generator.model.GgaGeneraldataLink;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author francisco_medina
 */
@Local
public interface GgaGeneraldataLinkEjbImplLocal {

    void create(GgaGeneraldataLink ggaGeneraldataLink);

    void edit(GgaGeneraldataLink ggaGeneraldataLink);

    void remove(GgaGeneraldataLink ggaGeneraldataLink);

    GgaGeneraldataLink find(Object id);

    List<GgaGeneraldataLink> findAll();

    List<GgaGeneraldataLink> findRange(int[] range);

    int count();

    List<GgaGeneraldataLink> findByParentId(BigDecimal parentId);

}
