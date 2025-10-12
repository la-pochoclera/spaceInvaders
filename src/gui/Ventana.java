package gui;

import javax.swing.JFrame;

public class Ventana extends JFrame{
	private PanelPrincipal panelPrincipal;
	
	public Ventana() {
		panelPrincipal = new PanelPrincipal();
		setContentPane(panelPrincipal);
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
}
