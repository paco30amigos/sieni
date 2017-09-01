package com.unicomer.opos.inhouse.gface.ejb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;

import com.unicomer.opos.inhouse.gface.entity.GfaceAssocDoc;

import java.util.HashMap;

@Remote
public interface GfaceAssocDocEjbLocal {

	GfaceAssocDoc create(GfaceAssocDoc GfaceAssocDoc);

    void edit(GfaceAssocDoc GfaceAssocDoc);

    void remove(GfaceAssocDoc GfaceAssocDoc);

    GfaceAssocDoc find(Serializable id);

    List<GfaceAssocDoc> findAll();

    List<GfaceAssocDoc> findRange(int[] range);

    int count();

    public boolean merge(HashMap<String, Object> params);
}
