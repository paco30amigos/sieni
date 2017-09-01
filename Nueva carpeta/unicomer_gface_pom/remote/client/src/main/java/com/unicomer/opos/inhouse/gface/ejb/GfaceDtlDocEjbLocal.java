package com.unicomer.opos.inhouse.gface.ejb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;

import com.unicomer.opos.inhouse.gface.entity.GfaceDtlDoc;

import java.util.HashMap;

@Remote
public interface GfaceDtlDocEjbLocal {

	GfaceDtlDoc create(GfaceDtlDoc GfaceDtlDoc);

    void edit(GfaceDtlDoc GfaceDtlDoc);

    void remove(GfaceDtlDoc GfaceDtlDoc);

    GfaceDtlDoc find(Serializable id);

    List<GfaceDtlDoc> findAll();

    List<GfaceDtlDoc> findRange(int[] range);

    int count();
    
    public boolean merge(HashMap<String, Object> params);

}
