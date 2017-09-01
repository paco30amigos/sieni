package com.unicomer.opos.inhouse.gface.ejb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;

import com.unicomer.opos.inhouse.gface.entity.GfaceErrRegCtg;

import java.util.HashMap;

@Remote
public interface GfaceErrRegCtgEjbLocal {

	GfaceErrRegCtg create(GfaceErrRegCtg GfaceErrRegCtg);

    void edit(GfaceErrRegCtg GfaceErrRegCtg);

    void remove(GfaceErrRegCtg GfaceErrRegCtg);

    GfaceErrRegCtg find(Serializable id);

    List<GfaceErrRegCtg> findAll();

    List<GfaceErrRegCtg> findRange(int[] range);

    int count();

    public boolean merge(HashMap<String, Object> params);
    
    public HashMap<String,List<GfaceErrRegCtg>> getErroresAdvertencias();

}
