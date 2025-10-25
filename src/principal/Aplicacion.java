package principal;

import javax.swing.SwingUtilities;

import vista.VistaPrincipal;

public class Aplicacion {
    public static void main(String[] args) {
    			SwingUtilities.invokeLater(() -> {
            VistaPrincipal vp = new VistaPrincipal();
            vp.setLocationRelativeTo(null);
            vp.setVisible(true);
        });
    }
}