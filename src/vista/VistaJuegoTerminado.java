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
    private JTextField txtFnombreRanking;
    private VistaPrincipal padre;
    private String nombre;

    public VistaJuegoTerminado(VistaPrincipal padre) {
        this.padre = padre;
        setLayout(null);
        setBackground(Color.BLACK);

        try {
            fondo = ImageIO.read(new File("sprites/gameOver.png"));
        } catch (IOException e) {
            System.out.println("No se pudo cargar el fondo: " + e.getMessage());
            fondo = null;
        }

        txtFnombreRanking = new JTextField();
        txtFnombreRanking.setFont(new Font("Consolas", Font.BOLD, 24));
        txtFnombreRanking.setBackground(Color.DARK_GRAY);
        txtFnombreRanking.setForeground(Color.WHITE);
        txtFnombreRanking.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        txtFnombreRanking.setBounds(500, 380, 350, 50);
        txtFnombreRanking.setHorizontalAlignment(JTextField.CENTER);
        add(txtFnombreRanking);

        botonContinuar = new JButton("Continuar");
        botonContinuar.setFont(new Font("Consolas", Font.BOLD, 24));
        botonContinuar.setBackground(Color.DARK_GRAY);
        botonContinuar.setForeground(Color.WHITE);
        botonContinuar.setFocusPainted(false);
        botonContinuar.setBounds(550, 460, 250, 60);
        add(botonContinuar);

        botonContinuar.addActionListener(e -> ejecutarContinuar());
        txtFnombreRanking.addActionListener(e -> ejecutarContinuar());
    }

    private void ejecutarContinuar() {
        nombre = txtFnombreRanking.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingresá tu nombre.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        System.out.println("Jugador: " + nombre);

        terminarPartida();
    }

    private void terminarPartida() {
        if (padre != null) {
            nombre = txtFnombreRanking.getText().trim();
            padre.pasarNombre(nombre);
            padre.showRanking();
        }
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
        int y = getHeight() / 2 - 150;
        g2.drawString(mensaje, x, y);
    }

    public String getNombreJugador() {
        return txtFnombreRanking.getText().trim();
    }
}
