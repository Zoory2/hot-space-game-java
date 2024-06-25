package main.java.zoory07.project_conejo.scenes.evento;

import main.java.zoory07.project_conejo.scenes.level.level_00_desierto;
import main.java.zoory07.project_conejo.imagen.SpriteSheet;
import main.java.zoory07.project_conejo.game.teclado;
import main.java.zoory07.project_conejo.scenes.evento.tiempo;
import java.io.IOException;





public class reinicio_level {
    private level_00_desierto nivel;
    private SpriteSheet spriteSheet;
    private teclado teclado;
    private tiempo tiempo;

    public reinicio_level(SpriteSheet spriteSheet, teclado teclado, tiempo tiempo) {
        this.spriteSheet = spriteSheet;
        this.teclado = teclado;
        this.tiempo = tiempo;
    }

    public void setNivel(level_00_desierto nivel) {
        this.nivel = nivel;
    }

    public void reiniciar() {
        if (teclado.enter) {
            System.out.println("Reiniciando nivel..."); // Mensaje de depuración
            try {
                nivel.reiniciarNivel();
                tiempo.reiniciar();
                teclado.enter = false; 
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
