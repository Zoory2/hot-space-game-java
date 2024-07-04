package main.java.zoory07.project_conejo.scenes.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.java.zoory07.project_conejo.game.teclado;
import main.java.zoory07.project_conejo.imagen.titulo;


public class Inicio_menu {
    private int x, y;
    private BufferedImage fondo;
    private String[] opciones = {"Jugar", "Salir"};
    private int seleccion = 0;
    private titulo titulo;
    private static final int INPUT_DELAY = 120; 
    private long lastInputTime; 
    
    public Inicio_menu(int x, int y) throws IOException {
        this.x = x;
        this.y = y;
        cargarImagen("resource/menu_inicio.png");
        titulo = new titulo("resource/titulo.png", x + 10, y + 5);
        lastInputTime = System.currentTimeMillis();
    }

    public void cargarImagen(String path) throws IOException {
        fondo = ImageIO.read(new File(path));
    }

    public void render(Graphics g) {
        if (fondo != null) {
            int newWidth = 900;  // Ajusta el ancho deseado
            int newHeight = 600; // Ajusta el alto deseado
            g.drawImage(fondo, x, y, newWidth, newHeight, null);
        }

        
        titulo.render(g);

        // Dibujar opciones del menú
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        int opcionesX = x + 100; // Ajusta la posición X de las opciones del menú
        int opcionesY = y + 300; // Ajusta la posición Y de las opciones del menú
        int espacioEntreOpciones = 40; // Ajusta el espacio entre opciones

        for (int i = 0; i < opciones.length; i++) {
            if (i == seleccion) {
                g.setColor(Color.RED);
                g.drawString("> " + opciones[i], opcionesX, opcionesY + i * espacioEntreOpciones);
            } else {
                g.setColor(Color.WHITE);
                g.drawString(opciones[i], opcionesX, opcionesY + i * espacioEntreOpciones);
            }
        }
    }

    public void update(teclado input) {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastInputTime >= INPUT_DELAY){
        if (input.arriba) {
            seleccion--;
            if (seleccion < 0) {
                seleccion = opciones.length - 1;
            }
            input.arriba = false;
        }
        if (input.abajo) {
            seleccion++;
            if (seleccion >= opciones.length) {
                seleccion = 0;
            }
            input.abajo = false; 
        }
        input.abajo = false;
        lastInputTime = currentTime; 
      
        }
    }

    public int getSeleccion() {
        return seleccion;
    }

    public void resetSeleccion() {
        seleccion = 0;
    }




}


