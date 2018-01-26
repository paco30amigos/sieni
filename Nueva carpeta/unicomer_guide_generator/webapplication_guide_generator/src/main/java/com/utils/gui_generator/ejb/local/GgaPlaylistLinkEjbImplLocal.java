/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils.gui_generator.ejb.local;

import com.utils.gui_generator.model.GgaPlaylistLink;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author francisco_medina
 */
@Local
public interface GgaPlaylistLinkEjbImplLocal {

    void create(GgaPlaylistLink ggaPlaylistLink);

    void edit(GgaPlaylistLink ggaPlaylistLink);

    void remove(GgaPlaylistLink ggaPlaylistLink);

    GgaPlaylistLink find(Object id);

    List<GgaPlaylistLink> findAll();

    List<GgaPlaylistLink> findRange(int[] range);

    int count();
    
}
