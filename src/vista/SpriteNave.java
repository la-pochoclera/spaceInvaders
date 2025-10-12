package vista;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class SpriteNave extends JLabel {
	private int ancho;
	private int alto;
	
	public SpriteNave() {
		ancho = 96;
		alto = 96;
		Image imagen = new ImageIcon("sprites/bateria.png").getImage();
		Image imagenAEscala = imagen.getScaledInstance(ancho,  alto, Image.SCALE_SMOOTH);
		ImageIcon icono = new ImageIcon(imagenAEscala);
		setIcon(icono);
	}

	public void mover(int i, int j) {
		// TODO Auto-generated method stub
		setBounds(i, j, ancho, alto);
	}
}
