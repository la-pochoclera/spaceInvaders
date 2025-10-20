package vista;

import controlador.ControladorPrincipal;
import modelo.Partida;
import modelo.NaveJugador;
import modelo.NaveInvasora;
import modelo.Muro;
import modelo.SegmentoMuro;
import modelo.Proyectil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class VistaJuego extends JPanel {
    private VistaPrincipal padre;
    private ControladorPrincipal controlador;

    private Timer timer;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean spacePending = false; // one-shot fire

    // Sprites
    private Image bgImg;
    private Image playerImg;
    private Image invaderImg;
    private Image muroImg;
    private Image proyectilImg;

    public VistaJuego(VistaPrincipal padre) {
        this.padre = padre;
        this.controlador = padre.getControlador();
        setBackground(Color.BLACK);
        setFocusable(true);
        initInput();
        initTimer();
        loadSprites();
    }

    private void loadSprites() {
        try {
            File dir = new File("sprites");
            bgImg = loadImage(new File(dir, "background.png"));
            playerImg = loadImage(new File(dir, "nave.png"));
            invaderImg = loadImage(new File(dir, "invasor.png"));
            muroImg = loadImage(new File(dir, "muro.png"));
            proyectilImg = loadImage(new File(dir, "proyectil.png"));
        } catch (Exception ex) {
            // ignore, fallbacks will be used
            bgImg = playerImg = invaderImg = muroImg = proyectilImg = null;
        }
    }

    private Image loadImage(File f) {
        try {
            if (f.exists()) return ImageIO.read(f);
        } catch (IOException e) {
            // ignore
        }
        return null;
    }

    private void initInput() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_LEFT) leftPressed = true;
                if (code == KeyEvent.VK_RIGHT) rightPressed = true;
                if (code == KeyEvent.VK_SPACE) {
                    // mark a pending shot, consumed on next tick
                    spacePending = true;
                }
                // allow esc to return to menu (do not call controlador.finalizarPartida() here)
                if (code == KeyEvent.VK_ESCAPE) {
                    if (timer != null && timer.isRunning()) timer.stop();
                    padre.showMenu();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_LEFT) leftPressed = false;
                if (code == KeyEvent.VK_RIGHT) rightPressed = false;
                // do not clear space here to allow one-shot behavior
            }
        });

        // When the panel becomes visible, request focus so key events are received
        addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0 && isShowing()) {
                requestFocusInWindow();
            }
        });
    }

    private void initTimer() {
        int delay = 1000 / 60; // ~16 ms -> 60 FPS
        timer = new Timer(delay, e -> {
            // Determine direction
            int dir = 0;
            if (leftPressed && !rightPressed) dir = -1;
            if (rightPressed && !leftPressed) dir = 1;

            boolean disparar = false;
            if (spacePending) {
                disparar = true;
                spacePending = false; // consume
            }

            // Delegate input and update to controller
            controlador.procesarInput(dir, disparar);
            controlador.actualizar();

            // If partida ended, return to menu and update credits/ranking
            if (controlador.getPartidaActual() == null) {
                // partida finalizada por el controlador
                timer.stop();
                padre.showMenu();
            }

            repaint();
        });
    }

    @Override
    public void addNotify() {
        super.addNotify();
        // start timer when added to hierarchy
        if (timer != null && !timer.isRunning()) timer.start();
    }

    @Override
    public void removeNotify() {
        if (timer != null && timer.isRunning()) timer.stop();
        super.removeNotify();
    }

    // Public control to start the game loop when this view is shown
    public void iniciar() {
        // reset input state
        leftPressed = false;
        rightPressed = false;
        spacePending = false;
        if (timer != null && !timer.isRunning()) timer.start();
        requestFocusInWindow();
    }

    // Public control to stop the game loop when leaving this view
    public void detener() {
        if (timer != null && timer.isRunning()) timer.stop();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Partida p = controlador.getPartidaActual();
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            // Draw background image if available
            if (bgImg != null) {
                g2.drawImage(bgImg, 0, 0, getWidth(), getHeight(), null);
            } else {
                g2.setColor(getBackground());
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
            // Draw HUD background
            g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(14f));

            if (p == null) {
                g2.drawString("No hay partida en curso.", 20, 40);
                g2.dispose();
                return;
            }

            // Draw player
            NaveJugador nj = p.getNaveJugador();
            if (nj != null) {
                int shipW = 40;
                int shipH = 20;
                int x = nj.getPosX() - shipW / 2;
                int y = nj.getPosY() - shipH / 2;
                if (playerImg != null) {
                    g2.drawImage(playerImg, x, y, shipW, shipH, null);
                } else {
                    g2.setColor(Color.GREEN);
                    g2.fillRect(x, y, shipW, shipH);
                }
            }

            // Draw invaders
            if (p.getOleada() != null) {
                List<NaveInvasora> naves = p.getOleada().getNaves();
                for (NaveInvasora n : naves) {
                    if (!n.isViva()) continue;
                    int w = 30;
                    int h = 18;
                    int x = n.getPosX() - w / 2;
                    int y = n.getPosY() - h / 2;
                    if (invaderImg != null) {
                        g2.drawImage(invaderImg, x, y, w, h, null);
                    } else {
                        g2.setColor(Color.RED);
                        g2.fillRect(x, y, w, h);
                    }
                }
            }

            // Draw muros
            List<Muro> muros = p.getMuros();
            for (Muro m : muros) {
                for (SegmentoMuro s : m.getSegmentos()) {
                    if (s.estaDestruido()) continue;
                    int w = 10;
                    int h = 6;
                    int x = s.getPosX() - w / 2;
                    int y = s.getPosY() - h / 2;
                    if (muroImg != null) {
                        g2.drawImage(muroImg, x, y, w, h, null);
                    } else {
                        g2.setColor(Color.GRAY);
                        g2.fillRect(x, y, w, h);
                    }
                }
            }

            // Draw projectiles
            List<Proyectil> proyectiles = p.getProyectiles();
            for (Proyectil pr : proyectiles) {
                if (!pr.isActivo()) continue;
                int w = pr.isAliado() ? 4 : 4;
                int h = pr.isAliado() ? 10 : 10;
                int x = pr.getPosX() - w / 2;
                int y = pr.getPosY() - h / 2;
                if (proyectilImg != null) {
                    g2.drawImage(proyectilImg, x, y, w, h, null);
                } else {
                    g2.setColor(Color.YELLOW);
                    g2.fillRect(x, y, w, h);
                }
            }

            // HUD: puntos, vidas, creditos
            String hud = String.format("Puntos: %d   Vidas: %d   Créditos: %d",
                    p.getPuntuacion(), nj != null ? nj.getVidas() : 0,
                    controlador.getSistema().getCreditosDisponibles());
            g2.setColor(Color.WHITE);
            g2.drawString(hud, 10, 20);
        } finally {
            g2.dispose();
        }
    }
}