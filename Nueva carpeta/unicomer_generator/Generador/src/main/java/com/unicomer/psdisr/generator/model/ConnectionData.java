/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.psdisr.generator.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author francisco_medina
 */
public class ConnectionData implements Serializable{

    private String name;
    private String conectionUrl;
    private String conectionUsr;
    private String conectionPass;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConectionUrl() {
        return conectionUrl;
    }

    public void setConectionUrl(String conectionUrl) {
        this.conectionUrl = conectionUrl;
    }

    public String getConectionUsr() {
        return conectionUsr;
    }

    public void setConectionUsr(String conectionUsr) {
        this.conectionUsr = conectionUsr;
    }

    public String getConectionPass() {
        return conectionPass;
    }

    public void setConectionPass(String conectionPass) {
        this.conectionPass = conectionPass;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.name);
        hash = 47 * hash + Objects.hashCode(this.conectionUrl);
        hash = 47 * hash + Objects.hashCode(this.conectionUsr);
        hash = 47 * hash + Objects.hashCode(this.conectionPass);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConnectionData other = (ConnectionData) obj;
        return Objects.equals(this.name, other.name);
    }

}
