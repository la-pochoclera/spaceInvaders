package vista;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import controlador.JuegoControlador;

public class PanelPrincipal extends JPanel{
	private JuegoControlador juegoControlador;
	private int ancho;
	private int alto;
	private SpriteNave spriteNave;
	private Image fondo;
	
	public PanelPrincipal() {
		this.ancho = 800;
		this.alto = 600;
		setLayout(null);
		setPreferredSize(new Dimension(ancho, alto));
		spriteNave = new SpriteNave();
		add(spriteNave);
		spriteNave.mover(400, 300);
		int posicionNaveJugadorX = 400;
		int posicionNaveJugadorY = 300;
		juegoControlador = new JuegoControlador(ancho, alto, posicionNaveJugadorX, posicionNaveJugadorY, spriteNave);
		interceptarTecla();
		simularMovimiento();
		fondo = new ImageIcon("sprites/background.png").getImage();
		setLayout(null);
	}
	
	private void interceptarTecla() {
		setFocusable(true);
		addKeyListener(new KeyAdapter() {
			@Override 
			public void keyPressed(KeyEvent evento) {
				int tecla = evento.getKeyCode();
				switch(tecla) {
					case KeyEvent.VK_RIGHT: 
						juegoControlador.moverNaveJugadorDerecha();
						break;
					case KeyEvent.VK_LEFT:
						juegoControlador.moverNaveJugadorIzquierda();
						break;
					case KeyEvent.VK_D: 
					    juegoControlador.moverNaveJugadorDerecha();
					    break;
					case KeyEvent.VK_A:
					    juegoControlador.moverNaveJugadorIzquierda();
					    break;
					case KeyEvent.VK_SPACE:
						SpriteRayo imagenRayo = new SpriteRayo();
						add(imagenRayo);
						juegoControlador.disparar(imagenRayo);
						break;
				}
			}
		});
	}
	
	private void simularMovimiento() {
		Timer gameLoop = new Timer(20, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				juegoControlador.actualizarPosiciones();
			}
		});
		gameLoop.start();
	}

	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
    }
}
