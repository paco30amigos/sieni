/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils.gui_generator.controller;

import com.utils.gui_generator.controller.form.MainForm;
import com.utils.gui_generator.ejb.local.GgaGeneralDataEjbImplLocal;
import com.utils.gui_generator.model.GgaGeneralData;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author francisco_medina
 */
@ManagedBean(name = "MainClassController")
public class MainClassController extends MainForm {

    @EJB
    private GgaGeneralDataEjbImplLocal ggaGeneralDataEjbImplLocal;

    @PostConstruct
    public void init() {
        this.setGeneralDataList(ggaGeneralDataEjbImplLocal.getAllGeneralDataFetch());
    }
    public void createTreeStructure(List<GgaGeneralData> data){
        
    }
    
}
