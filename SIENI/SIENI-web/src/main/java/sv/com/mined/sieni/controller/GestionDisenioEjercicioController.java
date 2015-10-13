/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import org.primefaces.model.ByteArrayContent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;
import sv.com.mined.sieni.SieniBitacoraFacadeRemote;
import sv.com.mined.sieni.SieniCursoFacadeRemote;
import sv.com.mined.sieni.SieniDocenteFacadeRemote;
import sv.com.mined.sieni.SieniGradoFacadeRemote;
import sv.com.mined.sieni.SieniMateriaFacadeRemote;
import sv.com.mined.sieni.SieniSeccionFacadeRemote;
import sv.com.mined.sieni.form.GestionEjercicioForm;
import sv.com.mined.sieni.model.SieniCurso;

/**
 *
 * @author francisco_medina
 */
@SessionScoped
@ManagedBean(name = "gestionDisenioEjercicioController")
public class GestionDisenioEjercicioController extends GestionEjercicioForm {
      
  private StreamedContent graphicText;
    @EJB
    private SieniCursoFacadeRemote sieniCursoFacadeRemote;

    @EJB
    private SieniBitacoraFacadeRemote sieniBitacoraFacadeRemote;

    @EJB
    private SieniDocenteFacadeRemote sieniDocenteFacadeRemote;
    @EJB
    private SieniGradoFacadeRemote sieniGradoFacadeRemote;
    @EJB
    private SieniSeccionFacadeRemote sieniSeccionFacadeRemote;
    @EJB
    private SieniMateriaFacadeRemote sieniMateriaFacadeRemote;

    @PostConstruct
    public void init() {
//        this.setCursoNuevo(new SieniCurso());
//        this.setCursoModifica(new SieniCurso());
//        this.setCursoList(new ArrayList<SieniCurso>());
      
       
        
//        fill();
    }

    private void fill() {
  
    }

   

       public void refresh() {
//        fill();
//        String latex = "\\begin{array}{l}";
//	latex += "\\forall\\varepsilon\\in\\mathbb{R}_+^*\\ \\exists\\eta>0\\ |x-x_0|\\leq\\eta\\Longrightarrow|f(x)-f(x_0)|\\leq\\varepsilon\\\\";
//	latex += "\\det\\begin{bmatrix}a_{11}&a_{12}&\\cdots&a_{1n}\\\\a_{21}&\\ddots&&\\vdots\\\\\\vdots&&\\ddots&\\vdots\\\\a_{n1}&\\cdots&\\cdots&a_{nn}\\end{bmatrix}\\overset{\\mathrm{def}}{=}\\sum_{\\sigma\\in\\mathfrak{S}_n}\\varepsilon(\\sigma)\\prod_{k=1}^n a_{k\\sigma(k)}\\\\";
//	latex += "\\sideset{_\\alpha^\\beta}{_\\gamma^\\delta}{\\begin{pmatrix}a&b\\\\c&d\\end{pmatrix}}\\\\";
//	latex += "\\int_0^\\infty{x^{2n} e^{-a x^2}\\,dx} = \\frac{2n-1}{2a} \\int_0^\\infty{x^{2(n-1)} e^{-a x^2}\\,dx} = \\frac{(2n-1)!!}{2^{n+1}} \\sqrt{\\frac{\\pi}{a^{2n+1}}}\\\\";
//	latex += "\\int_a^b{f(x)\\,dx} = (b - a) \\sum\\limits_{n = 1}^\\infty  {\\sum\\limits_{m = 1}^{2^n  - 1} {\\left( { - 1} \\right)^{m + 1} } } 2^{ - n} f(a + m\\left( {b - a} \\right)2^{-n} )\\\\";
//	latex += "\\int_{-\\pi}^{\\pi} \\sin(\\alpha x) \\sin^n(\\beta x) dx = \\textstyle{\\left \\{ \\begin{array}{cc} (-1)^{(n+1)/2} (-1)^m \\frac{2 \\pi}{2^n} \\binom{n}{m} & n \\mbox{ odd},\\ \\alpha = \\beta (2m-n) \\\\ 0 & \\mbox{otherwise} \\\\ \\end{array} \\right .}\\\\";
//	latex += "L = \\int_a^b \\sqrt{ \\left|\\sum_{i,j=1}^ng_{ij}(\\gamma(t))\\left(\\frac{d}{dt}x^i\\circ\\gamma(t)\\right)\\left(\\frac{d}{dt}x^j\\circ\\gamma(t)\\right)\\right|}\\,dt\\\\";
//	latex += "\\begin{array}{rl} s &= \\int_a^b\\left\\|\\frac{d}{dt}\\vec{r}\\,(u(t),v(t))\\right\\|\\,dt \\\\ &= \\int_a^b \\sqrt{u'(t)^2\\,\\vec{r}_u\\cdot\\vec{r}_u + 2u'(t)v'(t)\\, \\vec{r}_u\\cdot\\vec{r}_v+ v'(t)^2\\,\\vec{r}_v\\cdot\\vec{r}_v}\\,\\,\\, dt. \\end{array}\\\\";
//	latex += "\\end{array}";

	TeXFormula formula = new TeXFormula(this.getFormula());
	TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);
	icon.setInsets(new Insets(5, 5, 5, 5));

	BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
	Graphics2D g2 = image.createGraphics();
	g2.setColor(Color.white);
	g2.fillRect(0,0,icon.getIconWidth(),icon.getIconHeight());
	JLabel jl = new JLabel();
	jl.setForeground(new Color(0, 0, 0));
	icon.paintIcon(jl, g2, 0, 0);
//	File file = new File("Example3.png"); 
	try {
//            ImageIO.write(image, "png",new File("/home/ever/imagenesSIENI/Example3.jpg"));
//            BufferedImage originalImage = ImageIO.read(new File("/home/ever/imagenesSIENI/Example3.png"));
            ByteArrayOutputStream os = new ByteArrayOutputStream();            
            ImageIO.write(image, "png",os);
            os.flush();
            graphicText=new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "image/png");
//           graphicText=new ByteArrayContent(os.toByteArray(), "image/jpg");
         os.close();            
	    
	} catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public StreamedContent getGraphicText() {
        return graphicText;
    }

    public void setGraphicText(StreamedContent graphicText) {
        this.graphicText = graphicText;
    }

    
public void  formulaConstruct(int f){
    String valor="";
    switch(f){
        case 1:
            valor = this.ABSOLUTO;
            break;
        case 2:
            valor = this.FACTORIAL;
            break;
        case 3:
            valor = this.RAIZ;
            break;
        case 4:
            valor = this.RAIZN;
            break;
        case 5:
            valor = this.FUNCIONE;
            break;
        case 6:
            valor = this.POTENCIA;
            break;
        case 7:
            valor = this.LN;
            break;
        case 8:
            valor = this.LOG;
            break;
        case 9:
            valor = this.SENO;
            break;
        case 10:
            valor = this.COSENO;
            break;
        case 11:
            valor = this.TAN;
            break;
        case 12:
            valor = this.COT;
            break;
        case 13:
            valor = this.SENOH;
            break;
        case 14:
            valor = this.COSENOH;
            break;
        case 15:
            valor = this.TANH;
            break;
        case 16:
            valor = this.COTH;
            break;
        case 17:
            valor = this.ARCSENO;
            break;
        case 18:
            valor = this.ARCCOSENO;
            break;
        case 19:
            valor = this.ARCTAN;
            break;
        case 20:
            valor = this.ARCCOT;
            break;
        case 21:
            valor = this.ARCSENOH;
            break;
        case 22:
            valor = this.ARCCOSENOH;
            break;
        case 23:
            valor = this.ARCTANH;
            break;
        case 24:
            valor = this.ARCCOTH;    
            break;
    }
    
this.setFormula(this.getFormula()+valor);
}
    

}
