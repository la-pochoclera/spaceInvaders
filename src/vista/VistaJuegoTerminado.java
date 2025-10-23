package vista;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class VistaJuegoTerminado extends JPanel {
    private Image fondo;
    private String mensaje = "Juego terminado";
    private JButton botonContinuar;

    public VistaJuegoTerminado(Runnable onContinuar) {
        setLayout(null);
        setBackground(Color.BLACK);

        try {
            fondo = ImageIO.read(new File("sprites/gameOver.png"));
        } catch (IOException e) {
            System.out.println("No se pudo cargar el fondo: " + e.getMessage());
            fondo = null;
        }

        botonContinuar = new JButton("Continuar");
        botonContinuar.setFont(new Font("Consolas", Font.BOLD, 24));
        botonContinuar.setBackground(Color.DARK_GRAY);
        botonContinuar.setForeground(Color.WHITE);
        botonContinuar.setFocusPainted(false);
        botonContinuar.setBounds(550, 450, 250, 60);
        add(botonContinuar);

        botonContinuar.addActionListener(e -> {
            if (onContinuar != null) {
                onContinuar.run();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (fondo != null) {
            g2.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        }

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Consolas", Font.BOLD, 48));
        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(mensaje)) / 2;
        int y = getHeight() / 2 - 100;
        g2.drawString(mensaje, x, y);
    }
}
