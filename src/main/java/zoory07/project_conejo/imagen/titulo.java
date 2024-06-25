package main.java.zoory07.project_conejo.imagen;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;




public class titulo {
    private int x, y;
    private BufferedImage titulo;

    public titulo(String path, int x, int y) {
        this.x = x;
        this.y = y;
        cargarImagen(path);
    }

    private void cargarImagen(String path) {
        try {
            titulo = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics g) {
        if (titulo != null) {
            g.drawImage(titulo, x, y, null);
        }
    }
}
