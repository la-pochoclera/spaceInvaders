package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controlador.ControladorPrincipal;
import modelo.EntradaRanking;

public class VistaRanking extends JPanel {
    private VistaPrincipal padre;
    private ControladorPrincipal controlador;
    private JTable tabla;
    private DefaultTableModel modelo;

    public VistaRanking(VistaPrincipal padre) {
        this.padre = padre;
        this.controlador = padre.getControlador();
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(new Object[]{"Nombre", "Puntuación"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnVolver = new JButton("Volver al Menú");
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                padre.showMenu();
            }
        });
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
