package main.java.zoory07.project_conejo.game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import main.java.zoory07.project_conejo.imagen.SpriteSheet;
import main.java.zoory07.project_conejo.entity.player;
import main.java.zoory07.project_conejo.scenes.level.level_00_desierto;
import main.java.zoory07.project_conejo.game.teclado;
import main.java.zoory07.project_conejo.scenes.evento.tiempo;
import main.java.zoory07.project_conejo.scenes.menus.Inicio_menu;




public class Main extends Canvas {
    public static final int WIDTH = 300;
    public static final int HEIGHT = 200;
    public static final int SCALE = 3;
    public static String NAME = "HotSpace 1.0.1b";

    private JFrame Ventana;
    public boolean running = false;
    public int Loop = 0;

    public SpriteSheet spriteSheet;
    public teclado teclado;
    private player player;
    private boolean mostrarSprite = false;

    // niveles
    private level_00_desierto lvl_desierto;
    private tiempo tiempo;
    private Inicio_menu inicio_menu;

    private boolean enMenu = true;

    
    public void Game() throws IOException {
        setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

        Ventana = new JFrame(NAME);
        Ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Ventana.setLayout(new BorderLayout());
        Ventana.add(this, BorderLayout.CENTER);
        Ventana.pack();
        Ventana.setVisible(true);
        Ventana.setLocationRelativeTo(null);
        Ventana.setResizable(false);
        Ventana.revalidate();

        // Inicializar teclado antes de añadirlo como KeyListener
        this.teclado = new teclado();
        Ventana.addKeyListener(teclado);
        Ventana.setFocusable(true);
        Ventana.requestFocusInWindow();

        tiempo = new tiempo();
        tiempo.iniciar();

        // Inicializar inicio_menu
        inicio_menu = new Inicio_menu(0, 0);

        Sprite();
        Ecenas();
        CentrarPantallaPlayer();
    }

    public void Sprite() throws IOException {
        try {
            File archivoImagen = new File("src/main/java/resources/SpriteSheet.png");
            BufferedImage hojaSprites = ImageIO.read(archivoImagen);
            spriteSheet = new SpriteSheet(hojaSprites);

            int frameWidth = 30;
            int frameHeight = 30;
            int startX = 0;
            int startY = 0;
            int numFrames = 4; // Número de frames en la fila

            List<BufferedImage> correrFrames = spriteSheet.getAnimationFrames(startX, startY, frameWidth, frameHeight, numFrames);

            long frameDuracion = 100;
            this.player = new player(0, 0, correrFrames, teclado, frameDuracion);
            addKeyListener(this.teclado);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public void setMostrarSprite(boolean mostrar) {
        this.mostrarSprite = mostrar;
    }

    public void CentrarPantallaPlayer() {
        int ventanaAncho = getWidth();
        int ventanaAlto = getHeight();
        int playerAncho = player.getWidth();
        int playerAlto = player.getHeight();

        int x = (ventanaAncho - playerAncho) / 2;
        int y = (ventanaAlto - playerAlto) / 2;

        player.setX(x);
        player.setY(y);
    }

    public void Run() {
        long ultimoTiempo = System.nanoTime();
        double cantidadDeTicks = 60.0;
        double ns = 1000000000 / cantidadDeTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frame = 0;

        running = true;

        while (running) {
            long ahora = System.nanoTime();
            delta += (ahora - ultimoTiempo) / ns;
            ultimoTiempo = ahora;

            while (delta >= 1) {
                Ticks();
                updates++;
                delta--;
            }
            Render();
            frame++;

            if (System.currentTimeMillis() - timer > 100) {
                timer += 1000;
                System.out.println("FPS: " + frame + ", Ticks: " + updates);
                frame = 0;
                updates = 0;
            }

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void Ticks() {
        teclado.update();
        if (enMenu) {
            inicio_menu.update(teclado);
            if (teclado.enter) {
                if (inicio_menu.getSeleccion() == 0) {
                    Ventana.setVisible(false);
                    enMenu = false;
                    try {
                        Game();
                        Start();
                    Ventana.setVisible(true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (inicio_menu.getSeleccion() == 1) {
                    System.exit(0);
                }
            }
        } else {
            Update();
        }
    }

    public void Start() {
        running = true;
        new Thread(this::Run).start();
    }

    public void Ecenas() throws IOException {
        lvl_desierto = new level_00_desierto(spriteSheet, teclado, tiempo);
    }

    public void Update() {
        if (!enMenu) {
            lvl_desierto.update();
            teclado.update();
        }
    }

    public void Stop() {
        running = false;
    }

    public void Render() {
        if (!this.isDisplayable()) {
            return;
        }

        BufferStrategy b = getBufferStrategy();
        if (b == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = b.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (enMenu) {
            inicio_menu.render(g);
        } else {
            lvl_desierto.render(g);
        }

        g.dispose();
        b.show();
    }

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.Game();
        main.Start();
    }






}
