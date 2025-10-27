package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import controlador.ControladorPrincipal;
import modelo.EntradaRanking;

public class VistaRanking extends JPanel {
    private final VistaPrincipal padre;
    private final ControladorPrincipal controlador;
    private JTable tabla;
    private DefaultTableModel modelo;

    public VistaRanking(VistaPrincipal padre) {
        this.padre = padre;
        this.controlador = padre.getControlador();
        initUI();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        GradientPaint gp = new GradientPaint(0, 0, new Color(10, 10, 30), 0, h, new Color(25, 25, 60));
        g2.setPaint(gp);
        g2.fillRect(0, 0, w, h);
        g2.dispose();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // Título
        JLabel titulo = new JLabel("Ranking", JLabel.CENTER);
        titulo.setFont(new Font("Consolas", Font.BOLD, 40));
        titulo.setForeground(new Color(230, 230, 255));
        titulo.setBorder(new EmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        // Modelo y tabla
        modelo = new DefaultTableModel(new Object[]{"Nombre", "Puntuación"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabla = new JTable(modelo);
        tabla.setRowHeight(26);
        tabla.setFillsViewportHeight(true);
        tabla.setShowGrid(false);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        Color tableBg = new Color(30, 33, 45);
        Color tableFg = new Color(230, 232, 240);
        Color tableSelBg = new Color(70, 90, 140);
        tabla.setBackground(tableBg);
        tabla.setForeground(tableFg);
        tabla.setSelectionBackground(tableSelBg);
        tabla.setSelectionForeground(Color.WHITE);
        tabla.setFont(new Font("Consolas", Font.PLAIN, 16));
        // Renderer de celdas con padding y fondo sólido oscuro
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                                    boolean hasFocus, int row, int column) {
                java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setForeground(isSelected ? Color.WHITE : tableFg);
                c.setBackground(isSelected ? tableSelBg : tableBg);
                if (c instanceof javax.swing.JComponent jc) {
                    jc.setOpaque(true);
                    jc.setBorder(new EmptyBorder(8, 12, 8, 12)); // padding en contenido
                }
                return c;
            }
        };
        tabla.setDefaultRenderer(Object.class, cellRenderer);

        JTableHeader header = tabla.getTableHeader();
        header.setReorderingAllowed(false); 
        header.setResizingAllowed(false);
        header.setOpaque(true);
        Color headerBg = new Color(45, 50, 80);
        Color headerFg = Color.WHITE;
        Font headerFont = new Font("Consolas", Font.BOLD, 16);
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                                    boolean hasFocus, int row, int column) {
                java.awt.Component c = super.getTableCellRendererComponent(table, value, false, false, row, column);
                if (c instanceof JLabel l) {
                    l.setHorizontalAlignment(JLabel.LEFT);
                    l.setFont(headerFont);
                    l.setForeground(headerFg);
                    l.setBackground(headerBg);
                    l.setOpaque(true);
                    l.setBorder(new EmptyBorder(8, 12, 8, 12));
                }
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setBackground(tableBg);
        scroll.getViewport().setBackground(tableBg);
        scroll.setOpaque(true);
        scroll.getViewport().setOpaque(true);
        
        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        container.setBorder(new EmptyBorder(0, 24, 0, 24));
        container.add(scroll, BorderLayout.CENTER);
        add(container, BorderLayout.CENTER);

        JButton btnVolver = new JButton("Volver al menú");
        btnVolver.setFocusPainted(false);
        btnVolver.setFont(new Font("Consolas", Font.BOLD, 16));
        btnVolver.setForeground(new Color(240, 242, 255));
        btnVolver.setBackground(new Color(80, 90, 140));
        btnVolver.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnVolver.addActionListener((ActionEvent e) -> padre.showMenu());

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.setBorder(new EmptyBorder(0, 0, 20, 0));
        bottom.add(btnVolver);
        add(bottom, BorderLayout.SOUTH);
    
    }

    public void actualizarRanking() {
        modelo.setRowCount(0);
        List<EntradaRanking> entradas = controlador.getSistema().getRanking().getEntradas();
        for (EntradaRanking er : entradas) {
            modelo.addRow(new Object[]{er.getNombre(), er.getPuntuacion()});
        }
    }
}
