package vista;

import javax.swing.SwingUtilities;

public class Aplicacion {
    public static void main(String[] args) {
        // Use user-provided sprites in the 'sprites' folder; do not auto-create images.
        SwingUtilities.invokeLater(() -> {
            VistaPrincipal vp = new VistaPrincipal();
            vp.setLocationRelativeTo(null);
            vp.setVisible(true);
        });
    }
}