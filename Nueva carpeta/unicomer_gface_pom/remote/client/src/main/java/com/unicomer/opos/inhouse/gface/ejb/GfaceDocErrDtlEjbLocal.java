package com.unicomer.opos.inhouse.gface.ejb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;

import com.unicomer.opos.inhouse.gface.entity.GfaceDocErrDtl;

import java.util.HashMap;

@Remote
public interface GfaceDocErrDtlEjbLocal {

	GfaceDocErrDtl create(GfaceDocErrDtl GfaceDocErrDtl);

    void edit(GfaceDocErrDtl GfaceDocErrDtl);

    void remove(GfaceDocErrDtl GfaceDocErrDtl);

    GfaceDocErrDtl find(Serializable id);

    List<GfaceDocErrDtl> findAll();

    List<GfaceDocErrDtl> findRange(int[] range);

    int count();

    boolean merge(HashMap<String, Object> params);

}
