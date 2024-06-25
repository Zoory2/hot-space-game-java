package main.java.zoory07.project_conejo.imagen.fondo;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;








public class img_desierto {

 private int x, y;   
 private BufferedImage fondo;
    
    
 public img_desierto(int x, int y) throws IOException {
   this.x = x;
   this.y = y;
   cargarImagen("src/main/java/resources/fondo_decierto.png");
 
 }

 
 public void cargarImagen(String path) throws IOException{
     fondo = ImageIO.read(new File(path));
 }
 
 public void render(Graphics g){
   
    if(fondo != null){
      
       g.drawImage(fondo, x, y, null);
    }
   
   Ajusteimagen();
 
   
 
 
 }
 
 
 public void Ajusteimagen(){
    int Ancho = 900;
    int Alto = 600;
    
    Image imagenEscalada = fondo.getScaledInstance(Ancho, Alto, Image.SCALE_SMOOTH);

    BufferedImage imagenAjustada = new BufferedImage(Ancho, Alto, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = imagenAjustada.createGraphics();
    g2d.drawImage(imagenEscalada, 0, 0, null);
    g2d.dispose();
   
    //ajuste de imagen
    x = 1;
    y = 0;
    
    fondo = imagenAjustada; 
    
    float transparencia = 0.80f;
    AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparencia);
    g2d.setComposite(alpha);
    
    g2d.drawImage(fondo, y, x, null);  
    g2d.dispose();
}
 
 


}