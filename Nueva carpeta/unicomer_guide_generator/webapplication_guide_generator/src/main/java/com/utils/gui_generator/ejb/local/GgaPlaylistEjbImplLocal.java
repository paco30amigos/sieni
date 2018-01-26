/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils.gui_generator.ejb.local;

import com.utils.gui_generator.model.GgaPlaylist;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author francisco_medina
 */
@Local
public interface GgaPlaylistEjbImplLocal {

    void create(GgaPlaylist ggaPlaylist);

    void edit(GgaPlaylist ggaPlaylist);

    void remove(GgaPlaylist ggaPlaylist);

    GgaPlaylist find(Object id);

    List<GgaPlaylist> findAll();

    List<GgaPlaylist> findRange(int[] range);

    int count();
    
}
