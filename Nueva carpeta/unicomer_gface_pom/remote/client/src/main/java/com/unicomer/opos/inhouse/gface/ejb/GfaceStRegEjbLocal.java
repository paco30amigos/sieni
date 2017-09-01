package com.unicomer.opos.inhouse.gface.ejb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;

import com.unicomer.opos.inhouse.gface.entity.GfaceStReg;

@Remote
public interface GfaceStRegEjbLocal {

	GfaceStReg create(GfaceStReg GfaceStReg);

    void edit(GfaceStReg GfaceStReg);

    void remove(GfaceStReg GfaceStReg);

    GfaceStReg find(Serializable id);

    List<GfaceStReg> findAll();

    List<GfaceStReg> findRange(int[] range);

    int count();

}
