package vista;

import java.awt.Dimension;

import javax.swing.JPanel;

public class PanelPrincipal extends JPanel{
	private int ancho;
	private int alto;
	private SpriteNave spriteNave;
	
	public PanelPrincipal() {
		ancho = 1024;
		alto = 720;
		setLayout(null);
		setPreferredSize(new Dimension(ancho, alto));
		SpriteNave spriteNave = new SpriteNave();
		add(spriteNave);
		spriteNave.mover(600,400);
	}
}
