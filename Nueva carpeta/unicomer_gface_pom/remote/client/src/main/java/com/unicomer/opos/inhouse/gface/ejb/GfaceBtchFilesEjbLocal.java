package com.unicomer.opos.inhouse.gface.ejb;

import java.util.List;

import javax.ejb.Remote;

import com.unicomer.opos.inhouse.gface.entity.GfaceBtchFiles;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashMap;

@Remote
public interface GfaceBtchFilesEjbLocal {

	GfaceBtchFiles create(GfaceBtchFiles GfaceBtchFiles);

    void edit(GfaceBtchFiles GfaceBtchFiles);

    void remove(GfaceBtchFiles GfaceBtchFiles);

    GfaceBtchFiles find(Serializable id);

    List<GfaceBtchFiles> findAll();

    List<GfaceBtchFiles> findRange(int[] range);

    int count();

    BigInteger getSequenceNext(String sequenceName);

    public boolean merge(HashMap<String, Object> params);
    
    public List<String> getFilesProcessed();
}
