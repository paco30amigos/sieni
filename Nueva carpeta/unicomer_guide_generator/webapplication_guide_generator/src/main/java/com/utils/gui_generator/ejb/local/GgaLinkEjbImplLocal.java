/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils.gui_generator.ejb.local;

import com.utils.gui_generator.model.GgaLink;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author francisco_medina
 */
@Local
public interface GgaLinkEjbImplLocal {

    void create(GgaLink ggaLink);

    void edit(GgaLink ggaLink);

    void remove(GgaLink ggaLink);

    GgaLink find(Object id);

    List<GgaLink> findAll();

    List<GgaLink> findRange(int[] range);

    int count();
    
}
