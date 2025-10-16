package vista;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



public class TeclaListener extends KeyAdapter {
	private SpriteNave spriteNave;
	
	public TeclaListener(SpriteNave spriteNave) {
		this.spriteNave = spriteNave;
	}
	
	@Override 
	public void keyPressed(KeyEvent evento) {
		int tecla = evento.getKeyCode();
		if(tecla == KeyEvent.VK_RIGHT) {
			System.out.println("tecla presionada");
			spriteNave.mover(600, 400);
		}
	}
}
