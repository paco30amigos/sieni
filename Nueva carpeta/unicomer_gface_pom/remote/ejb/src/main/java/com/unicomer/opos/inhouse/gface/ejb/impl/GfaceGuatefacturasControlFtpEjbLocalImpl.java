/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.opos.inhouse.gface.ejb.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.unicomer.inhouse.gface.model.util.GfaceFileUtils;
import com.unicomer.opos.inhouse.gface.ejb.GfaceGuatefacturaFileReaderEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceGuatefacturasControlFtpEjbLocal;

/**
 *
 * @author francisco_medina
 */
@Transactional(value = TxType.REQUIRED)
@Stateless(name = "GfaceGuatefacturasControlFtpEjbLocalImpl", mappedName = "ejb/GfaceGuatefacturasControlFtpEjbLocalImpl")
@Remote(GfaceGuatefacturasControlFtpEjbLocal.class)
public class GfaceGuatefacturasControlFtpEjbLocalImpl implements GfaceGuatefacturasControlFtpEjbLocal {

    private HashMap<String, String> ftpParams;
    String REMOTE_SEPARATOR = "/";
    String remoteStoreFiles = REMOTE_SEPARATOR + "home" + REMOTE_SEPARATOR + "jinhouse" + REMOTE_SEPARATOR + "gface_test" + REMOTE_SEPARATOR + "Archivos_CAE";
    String remoteRetreiveFiles = REMOTE_SEPARATOR + "home" + REMOTE_SEPARATOR + "jinhouse" + REMOTE_SEPARATOR + "gface_test" + REMOTE_SEPARATOR + "Archivos_XML_CAE";

    public void send(File[] archivos) {
        try {
            HashMap<String, String> params = getFtpParams();
            FTPClient ftpClient = getFtpClient(params);
            BufferedInputStream buffIn = null;
            for (File actual : archivos) {
//                String fileName = "PROCESOS_PARA_EL_IVA_VENTAS_UNICOMER.xlsx";
                buffIn = new BufferedInputStream(new FileInputStream(actual.getAbsolutePath()));//Ruta del archivo para enviar
                ftpClient.enterLocalPassiveMode();
                ftpClient.storeFile(params.get("remoteStoreFiles") + REMOTE_SEPARATOR + actual.getName(), buffIn);//Ruta completa de alojamiento en el FTP
                if (ftpClient.getReplyCode() == 226) {
                    System.out.println("Archivo guardado exitosamente");
                } else {
                    System.out.println("No se pudo guardar el archivo: " + actual.getName() + ", codigo:" + ftpClient.getReplyCode());
                }
            }

            buffIn.close(); //Cerrar envio de archivos al FTP
            ftpClient.logout(); //Cerrar sesión
            ftpClient.disconnect();//Desconectarse del servidor
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void receive(List<String> fileNames) {
        try {
            HashMap<String, String> params = getFtpParams();
            FTPClient ftpClient = getFtpClient(params);
            BufferedInputStream buffIn;
            ftpClient.enterLocalPassiveMode();
            for (String nombre : fileNames) {
                buffIn = new BufferedInputStream(ftpClient.retrieveFileStream(params.get("remoteRetreiveFiles") + REMOTE_SEPARATOR + nombre));//Ruta completa de alojamiento en el FTP
                if (ftpClient.getReplyCode() == 150) {
                    System.out.println("Archivo obtenido exitosamente");
                    // write the inputStream to a FileOutputStream
                    OutputStream outputStream = new FileOutputStream(new File(params.get("localStoreFiles") + File.separator + nombre));
                    int read = 0;
                    byte[] bytes = new byte[1024];

                    while ((read = buffIn.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, read);
                    }
                    buffIn.close(); //Cerrar envio de arcivos al FTP
                    outputStream.close();
                } else {
                    System.out.println("No se pudo obtener el archivo: " + nombre + ", codigo:" + ftpClient.getReplyCode());
                }
            }

            ftpClient.logout(); //Cerrar sesión
            ftpClient.disconnect();//Desconectarse del servidor
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public List<String> getFilesExists(List<String> fileNames) {
        List<String> ret = new ArrayList<String>();
        try {
            HashMap<String, String> params = getFtpParams();
            FTPClient ftpClient = getFtpClient(params);
            BufferedInputStream buffIn;
            ftpClient.enterLocalPassiveMode();
            for (String nombre : fileNames) {
                buffIn = new BufferedInputStream(ftpClient.retrieveFileStream(params.get("remoteRetreiveFiles") + REMOTE_SEPARATOR + nombre));//Ruta completa de alojamiento en el FTP
                if (ftpClient.getReplyCode() == 150) {
                    System.out.println("Archivo obtenido exitosamente");
                    buffIn.close();
                    ret.add(nombre);
                } else {
                    System.out.println("No se pudo obtener el archivo: " + nombre + ", codigo:" + ftpClient.getReplyCode());
                }
            }

            ftpClient.logout(); //Cerrar sesión
            ftpClient.disconnect();//Desconectarse del servidor
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return ret;
    }

    public FTPClient getFtpClient(HashMap<String, String> params) {
        String urlServer = params.get("urlServer");
        String usuario = params.get("user");
        String pass = params.get("pass");
//        String workingDir = params.get("workingDir");

        FTPClient ret = null;
        try {
            FTPClient ftpClient = new FTPClient();
            ftpClient.connect(InetAddress.getByName(urlServer));
            ftpClient.login(usuario, pass);
            //Verificar conexión con el servidor.
            int reply = ftpClient.getReplyCode();
            System.out.println("Estatus de conexion FTP:" + reply);
            if (FTPReply.isPositiveCompletion(reply)) {
                System.out.println("Conectado exitosamente");
            } else {
                System.out.println("No se pudo conectar con el servidor");
            }
            //directorio de trabajo
//            ftpClient.changeWorkingDirectory(workingDir);
            System.out.println("Establecer carpeta de trabajo");
            //Activar que se envie cualquier tipo de archivo
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ret = ftpClient;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return ret;
    }

    public HashMap<String, String> getFtpParamsDefault() {
        HashMap<String, String> paramsDefault = new HashMap<String, String>();
        paramsDefault.put("urlServer", "192.168.130.134");
        paramsDefault.put("user", "jinhouse");
        paramsDefault.put("pass", "jinhouse");
        paramsDefault.put("localFilesToSend", GfaceFileUtils.localFilesToSend);
        paramsDefault.put("localStoreFiles", GfaceFileUtils.localStoreFiles);
        paramsDefault.put("remoteStoreFiles", remoteStoreFiles);
        paramsDefault.put("remoteRetreiveFiles", remoteRetreiveFiles);
        return paramsDefault;
    }

    public HashMap<String, String> getFtpParams() {
        return ftpParams;
    }

    public void setFtpParams(HashMap<String, String> ftpParams) {
        this.ftpParams = ftpParams;
    }

}
