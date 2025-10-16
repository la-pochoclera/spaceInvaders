package vista;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import modelo.Observador;

public class SpriteNave extends SpriteObjetoJuego {

	public SpriteNave() {
		super(96, 96);
		Image imagen = new ImageIcon("sprites/bateria.png").getImage();
		Image imagenAEscala = imagen.getScaledInstance(getAncho(), getAlto(), Image.SCALE_SMOOTH);
		ImageIcon icono = new ImageIcon(imagenAEscala);
		setIcon(icono);
	}

}
