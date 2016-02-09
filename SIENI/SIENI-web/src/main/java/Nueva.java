
import java.io.File;
import java.io.FileInputStream;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author francisco_medina
 */
@ManagedBean
public class Nueva {

    public StreamedContent getStreams() {
        StreamedContent ret = null;
        try {
            FileInputStream fis = new FileInputStream(new File("D:\\paco\\SIENI\\sieni\\SIENI\\SIENI-web\\src\\main\\webapp\\resources\\video\\20150828_113341s.mp4"));
            ret = new DefaultStreamedContent(fis, "video/mp4");
        } catch (Exception e) { 
            System.out.println(e.getMessage());
        }
        return ret;
    }
    public StreamedContent getStream() {
        StreamedContent ret = null;
        try {
            FileInputStream fis = new FileInputStream(new File("D:\\paco\\SIENI\\sieni\\SIENI\\SIENI-web\\src\\main\\webapp\\resources\\video\\20150828_113341.mp4"));
            ret = new DefaultStreamedContent(fis, "video/mp4");
        } catch (Exception e) { 
            System.out.println(e.getMessage());
        }
        return ret;
    }
}
