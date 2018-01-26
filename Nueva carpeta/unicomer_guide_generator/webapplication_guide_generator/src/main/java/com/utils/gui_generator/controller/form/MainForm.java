/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils.gui_generator.controller.form;

import com.utils.gui_generator.model.GgaGeneralData;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author francisco_medina
 */
public class MainForm implements Serializable{
    private List<GgaGeneralData> generalDataList;

    public List<GgaGeneralData> getGeneralDataList() {
        return generalDataList;
    }

    public void setGeneralDataList(List<GgaGeneralData> generalDataList) {
        this.generalDataList = generalDataList;
    }
    
}
