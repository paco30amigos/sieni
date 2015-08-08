/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 *
 * @author Laptop
 */
public class CtrlImagenes {

//    public boolean guardarfoto(String id, String name, String ruta) {
//        FileInputStream fis = null;
//        try {
//            File file = new File(ruta);
//            fis = new FileInputStream(file);
//            PreparedStatement pstm = connection.prepareStatement("INSERT into "
//                    + " imagen(id, nombre, archivo) " + " VALUES(?,?,?)");
//            pstm.setString(1, id);
//            pstm.setString(2, name);
//            pstm.setBinaryStream(3, fis, (int) file.length());
//            pstm.execute();
//            pstm.close();
//            return true;
//        } catch (FileNotFoundException e) {
//            System.out.println(e.getMessage());
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        } finally {
//            try {
//                fis.close();
//            } catch (IOException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//        return false;
//    }

    /* Metodo que convierte una cadena de bytes en una imagen JPG
     * input:
     * bytes: array que contiene los binarios de la imagen
     * Output: la foto JPG en formato IMAGE
     */
    private Image ConvertirImagen(byte[] bytes) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Iterator readers = ImageIO.getImageReadersByFormatName("jpeg");
        ImageReader reader = (ImageReader) readers.next();
        Object source = bis;
        ImageInputStream iis = ImageIO.createImageInputStream(source);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        return reader.read(0, param);
    }
}
