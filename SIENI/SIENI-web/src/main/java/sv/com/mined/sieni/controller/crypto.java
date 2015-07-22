/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

/**
 *
 * @author francisco_medina
 */
import java.io.Console;
import javax.faces.bean.ManagedBean;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

@ManagedBean
public class crypto {

    public static void main(String[] args) {
        Console console = System.console();
        if (console == null) {
            System.out.println("Couldn't get Console instance");
            System.exit(0);
        }

        console.printf("Encriptando clave%n");
        char passwordArray[] = console.readPassword("Ingrese su clave: ");
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("com.orpos.commons.config.ReadConfig");

        console.printf("%s%n", "!!"
                + encryptor.encrypt(new String(passwordArray)));
    }
    
    public String getCryp(){
//        Console console = System.console();
//        if (console == null) {
//            System.out.println("Couldn't get Console instance");
//            System.exit(0);
//        }
//
//        console.printf("Encriptando clave%n");
        String C="tallerni";
        
        char passwordArray[] = C.toCharArray();
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("com.orpos.commons.config.ReadConfig");

        return "!!"+ encryptor.encrypt(new String(passwordArray));
    }

}
