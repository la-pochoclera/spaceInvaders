package gui;

import java.awt.Dimension;

import javax.swing.JPanel;

public class PanelPrincipal extends JPanel{
	private int ancho;
	private int alto;
	
	public PanelPrincipal() {
		ancho = 1024;
		alto = 720;
		setLayout(null);
		setPreferredSize(new Dimension(ancho, alto));
	}
}
