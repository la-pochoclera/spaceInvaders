package vista;

import controlador.ControladorPrincipal;
import modelo.Sistema;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaMenu extends JPanel {
    private VistaPrincipal padre;
    private ControladorPrincipal controlador;

    private JLabel lblCreditos;
    private JTextField txtCargar;

    public VistaMenu(VistaPrincipal padre) {
        this.padre = padre;
        this.controlador = padre.getControlador();
        initUI();
        actualizarCreditos();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("Space Invaders");
        title.setFont(title.getFont().deriveFont(32f));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnIniciar = new JButton("Iniciar Partida");
        btnIniciar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnIniciar.addActionListener(e -> {
            // Validar créditos via el sistema
            Sistema s = controlador.getSistema();
            if (!s.tieneCreditos()) {
                JOptionPane.showMessageDialog(this, "No hay créditos disponibles. Cargue créditos.", "Créditos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            controlador.iniciarNuevaPartida();
            padre.showJuego();
        });

        JButton btnRanking = new JButton("Ver Ranking");
        btnRanking.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRanking.addActionListener(e -> {
            padre.showRanking();
        });

        JButton btnSalir = new JButton("Salir");
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSalir.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(this).dispose();
        });

        JPanel creditPanel = new JPanel();
        creditPanel.setLayout(new FlowLayout());
        lblCreditos = new JLabel("Créditos disponibles: 0");
        txtCargar = new JTextField(5);
        JButton btnCargar = new JButton("Cargar Créditos");
        btnCargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int c = Integer.parseInt(txtCargar.getText());
                    if (c <= 0) {
                        JOptionPane.showMessageDialog(VistaMenu.this, "Ingrese un número positivo.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // through controller per UML
                    controlador.cargarCreditos(c);
                    actualizarCreditos();
                    txtCargar.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(VistaMenu.this, "Valor no válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        creditPanel.add(lblCreditos);
        creditPanel.add(new JLabel("Cantidad:"));
        creditPanel.add(txtCargar);
        creditPanel.add(btnCargar);

        center.add(Box.createVerticalStrut(20));
        center.add(title);
        center.add(Box.createVerticalStrut(20));
        center.add(btnIniciar);
        center.add(Box.createVerticalStrut(10));
        center.add(btnRanking);
        center.add(Box.createVerticalStrut(10));
        center.add(btnSalir);
        center.add(Box.createVerticalStrut(20));
        center.add(creditPanel);

        add(center, BorderLayout.CENTER);
    }

    public void actualizarCreditos() {
        int c = controlador.getSistema().getCreditosDisponibles();
        lblCreditos.setText("Créditos disponibles: " + c);
    }
}