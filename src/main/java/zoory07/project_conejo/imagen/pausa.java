package main.java.zoory07.project_conejo.imagen;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;




public class pausa {
    private int x, y;
    private BufferedImage pausa;

    public pausa(String path, int x, int y) {
        this.x = x;
        this.y = y;
        cargarImagen(path);
    }

    private void cargarImagen(String path) {
        try {
            pausa = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics g) {
        if (pausa != null) {
            g.drawImage(pausa, x, y, null);
        }
    }


}
