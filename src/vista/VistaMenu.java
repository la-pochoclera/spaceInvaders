package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingConstants;

import controlador.ControladorPrincipal;
import modelo.Dificultad;
import modelo.Sistema;

public class VistaMenu extends JPanel {
    private static final Color COLOR_FONDO = new Color(12, 16, 38);
    private static final Color COLOR_SECUNDARIO = new Color(88, 96, 178);
    private static final Color COLOR_TEXTO_PRINCIPAL = new Color(248, 248, 255);
    private static final Color COLOR_TEXTO_SECUNDARIO = new Color(205, 211, 240);
    private static final Color COLOR_BOTON = new Color(98, 112, 255);
    private static final Color COLOR_BOTON_HOVER = new Color(129, 140, 255);
    private static final Color COLOR_BOTON_TEXTO = Color.WHITE;
    private static final Color COLOR_BOTON_BORDE = new Color(46, 58, 143);
    private static final Color COLOR_TEXTO_CAMPO = new Color(240, 242, 255);
    private static final Color COLOR_CAMPO = new Color(33, 36, 72);
    private static final Font FUENTE_TITULO = new Font("Orbitron", Font.BOLD, 42);
    private static final Font FUENTE_TEXTO = new Font("Roboto", Font.PLAIN, 16);
    private static final Font FUENTE_BOTON = new Font("Roboto", Font.BOLD, 16);

    private VistaPrincipal padre;
    private ControladorPrincipal controlador;

    private JLabel lblCreditos;
    private JTextField txtCargar;
    private ButtonGroup grupoDificultad;
    private Dificultad dificultadSeleccionada;

    public VistaMenu(VistaPrincipal padre) {
        this.padre = padre;
        this.controlador = padre.getControlador();
    setBackground(COLOR_FONDO);
    setOpaque(true);
        initUI();
        actualizarCreditos();
    }

    private void initUI() {
        setLayout(new BorderLayout());

    JPanel center = new JPanel();
    center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("Space Invaders");
        title.setFont(FUENTE_TITULO);
        title.setForeground(COLOR_TEXTO_PRINCIPAL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnIniciar = new JButton("Iniciar Partida");
        estilizarBoton(btnIniciar);
        btnIniciar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnIniciar.addActionListener(e -> {
            // Validar créditos via el sistema
            Sistema s = controlador.getSistema();
            if (!s.tieneCreditos()) {
                JOptionPane.showMessageDialog(this, "No hay créditos disponibles. Cargue créditos.", "Créditos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            controlador.iniciarNuevaPartida(obtenerDificultadSeleccionada());
            padre.showJuego();
        });

        JButton btnRanking = new JButton("Ver Ranking");
        estilizarBoton(btnRanking);
        btnRanking.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRanking.addActionListener(e -> {
            padre.showRanking();
        });

        JButton btnSalir = new JButton("Salir");
        estilizarBoton(btnSalir);
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSalir.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(this).dispose();
        });

    JPanel creditPanel = new JPanel();
    creditPanel.setOpaque(false);
    creditPanel.setLayout(new BoxLayout(creditPanel, BoxLayout.Y_AXIS));
    creditPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    creditPanel.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));

    lblCreditos = new JLabel();
    lblCreditos.setFont(FUENTE_TEXTO.deriveFont(Font.BOLD, 18f));
    lblCreditos.setForeground(COLOR_TEXTO_PRINCIPAL);
        lblCreditos.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtCargar = new JTextField(5);
        txtCargar.setBackground(COLOR_CAMPO);
        txtCargar.setForeground(COLOR_TEXTO_CAMPO);
        txtCargar.setCaretColor(COLOR_TEXTO_CAMPO);
        txtCargar.setBorder(BorderFactory.createLineBorder(COLOR_SECUNDARIO, 1));
        txtCargar.setFont(FUENTE_TEXTO);
        txtCargar.setMaximumSize(txtCargar.getPreferredSize());

        JButton btnCargar = new JButton("Cargar Créditos");
        estilizarBoton(btnCargar);
        btnCargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int c = Integer.parseInt(txtCargar.getText());
                    if (c <= 0) {
                        JOptionPane.showMessageDialog(VistaMenu.this, "Ingrese un número positivo.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    controlador.getSistema().cargarCreditos(c);
                    actualizarCreditos();
                    txtCargar.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(VistaMenu.this, "Valor no válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    JPanel creditInputs = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
    creditInputs.setOpaque(false);
    JLabel lblCantidad = new JLabel("Cantidad:");
    lblCantidad.setFont(FUENTE_TEXTO);
    lblCantidad.setForeground(COLOR_TEXTO_SECUNDARIO);
    creditInputs.add(lblCantidad);
    creditInputs.add(txtCargar);
    creditInputs.add(btnCargar);

    creditPanel.add(lblCreditos);
    creditPanel.add(Box.createVerticalStrut(10));
    creditPanel.add(creditInputs);

        center.add(Box.createVerticalGlue());
        center.add(Box.createVerticalStrut(20));
        center.add(title);
        center.add(Box.createVerticalStrut(20));
        center.add(btnIniciar);
    center.add(Box.createVerticalStrut(10));
    JPanel dificultadPanel = new JPanel();
    dificultadPanel.setOpaque(false);
    dificultadPanel.setLayout(new BoxLayout(dificultadPanel, BoxLayout.Y_AXIS));
    dificultadPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    JLabel lblDificultad = new JLabel("Dificultad:");
    lblDificultad.setFont(FUENTE_TEXTO);
    lblDificultad.setForeground(COLOR_TEXTO_SECUNDARIO);
    lblDificultad.setAlignmentX(Component.CENTER_ALIGNMENT);
    dificultadPanel.add(lblDificultad);
    dificultadPanel.add(Box.createVerticalStrut(8));
    grupoDificultad = new ButtonGroup();
    dificultadSeleccionada = Dificultad.CADETE;
    JPanel radiosColumn = new JPanel();
    radiosColumn.setOpaque(false);
    radiosColumn.setLayout(new BoxLayout(radiosColumn, BoxLayout.Y_AXIS));
    radiosColumn.setAlignmentX(Component.CENTER_ALIGNMENT);
    for (Dificultad dificultad : Dificultad.obtenerPredefinidas()) {
        JRadioButton radio = crearRadioDificultad(dificultad);
        if (dificultad == dificultadSeleccionada) {
            radio.setSelected(true);
        }
        grupoDificultad.add(radio);
        radiosColumn.add(radio);
        radiosColumn.add(Box.createVerticalStrut(6));
    }
    dificultadPanel.add(radiosColumn);
    center.add(dificultadPanel);
        center.add(Box.createVerticalStrut(10));
        center.add(btnRanking);
        center.add(Box.createVerticalStrut(10));
        center.add(btnSalir);
        center.add(Box.createVerticalStrut(20));
        center.add(creditPanel);
        center.add(Box.createVerticalGlue());

        add(center, BorderLayout.CENTER);
    }

    public void actualizarCreditos() {
        int c = controlador.getSistema().getCreditosDisponibles();
        lblCreditos.setText("Créditos disponibles: " + c);
    }

    private JRadioButton crearRadioDificultad(Dificultad dificultad) {
        JRadioButton radio = new JRadioButton(dificultad.getEtiqueta());
        radio.setOpaque(false);
        radio.setFont(FUENTE_TEXTO);
        radio.setForeground(COLOR_TEXTO_CAMPO);
        radio.setFocusPainted(false);
        radio.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        radio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    radio.setAlignmentX(Component.CENTER_ALIGNMENT);
    radio.setHorizontalAlignment(SwingConstants.CENTER);
        radio.addActionListener(e -> dificultadSeleccionada = dificultad);

        radio.setIcon(crearCircularIcono(false));
        radio.setSelectedIcon(crearCircularIcono(true));
        radio.setIconTextGap(10);

        return radio;
    }

    private javax.swing.Icon crearCircularIcono(boolean seleccionado) {
        int diametro = 16;
        return new javax.swing.Icon() {
            @Override
            public void paintIcon(java.awt.Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(seleccionado ? COLOR_BOTON : COLOR_TEXTO_SECUNDARIO);
                g2.drawOval(x, y, diametro, diametro);
                if (seleccionado) {
                    g2.fillOval(x + 4, y + 4, diametro - 8, diametro - 8);
                }
                g2.dispose();
            }

            @Override
            public int getIconWidth() {
                return diametro;
            }

            @Override
            public int getIconHeight() {
                return diametro;
            }
        };
    }

    private Dificultad obtenerDificultadSeleccionada() {
        return dificultadSeleccionada != null ? dificultadSeleccionada : Dificultad.CADETE;
    }

    private void estilizarBoton(JButton boton) {
        boton.setFont(FUENTE_BOTON);
        boton.setForeground(COLOR_BOTON_TEXTO);
        boton.setFocusPainted(false);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setContentAreaFilled(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 26, 10, 26));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                boton.putClientProperty("hover", Boolean.TRUE);
                boton.repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                boton.putClientProperty("hover", Boolean.FALSE);
                boton.repaint();
            }
        });

    boton.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, javax.swing.JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

                boolean hover = Boolean.TRUE.equals(c.getClientProperty("hover"));
                Color fondo = hover ? COLOR_BOTON_HOVER : COLOR_BOTON;
        GradientPaint grad = new GradientPaint(0, 0, fondo.brighter(), 0, c.getHeight(), fondo);
        g2.setPaint(grad);
        g2.fillRoundRect(2, 2, c.getWidth() - 4, c.getHeight() - 4, 20, 20);

        g2.setColor(COLOR_BOTON_BORDE);
        g2.drawRoundRect(2, 2, c.getWidth() - 5, c.getHeight() - 5, 20, 20);

                g2.setColor(COLOR_BOTON_TEXTO);
                java.awt.FontMetrics fm = g2.getFontMetrics(boton.getFont());
                int textX = (c.getWidth() - fm.stringWidth(boton.getText())) / 2;
                int textY = (c.getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.setFont(boton.getFont());
                g2.drawString(boton.getText(), textX, textY);
                g2.dispose();
            }
        });
    }
}
