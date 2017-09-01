package com.unicomer.opos.inhouse.gface.ejb;

import java.util.List;

import javax.ejb.Remote;

import com.unicomer.opos.inhouse.gface.entity.GfaceHdrDoc;

import java.io.Serializable;
import java.util.HashMap;

@Remote
public interface GfaceHdrDocEjbLocal {

	GfaceHdrDoc create(GfaceHdrDoc GfaceHdrDoc);

    void edit(GfaceHdrDoc GfaceHdrDoc);

    void remove(GfaceHdrDoc GfaceHdrDoc);

    GfaceHdrDoc find(Serializable id);

    List<GfaceHdrDoc> findAll();

    List<GfaceHdrDoc> findRange(int[] range);

    int count();

    public List<GfaceHdrDoc> getHeaderByStatus(HashMap<String, Object> params);
    
    public List<GfaceHdrDoc> getHeaderByStatusDocTypeErrWrStr(HashMap<String, Object> params);

    public boolean merge(HashMap<String, Object> params,boolean saveStatus);
    
    public Long countErrors();
    public Long countWarnigns();
    public Long countReadyToSend();

}
