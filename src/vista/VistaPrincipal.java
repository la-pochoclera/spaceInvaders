package vista;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import controlador.ControladorPrincipal;

public class VistaPrincipal extends JFrame {
    public static final String MENU = "MENU";
    public static final String JUEGO = "JUEGO";
    public static final String RANKING = "RANKING";
    public static final String GAMEOVER = "GAMEOVER";

    private ControladorPrincipal controlador;
    private CardLayout cardLayout;
    private JPanel cardsPanel;
    private VistaMenu vistaMenu;
    private VistaJuego vistaJuego;
    private VistaRanking vistaRanking;
    private VistaJuegoTerminado vistaJuegoTerminado;

    public VistaPrincipal() {
        super("Space Invaders");
        this.controlador = ControladorPrincipal.getInstance();
        initUI();
    }

    private void initUI() {
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);

        vistaMenu = new VistaMenu(this);
        vistaJuego = new VistaJuego(this);
        vistaRanking = new VistaRanking(this);
        vistaJuegoTerminado = new VistaJuegoTerminado(this);

        cardsPanel.add(vistaMenu, MENU);
        cardsPanel.add(vistaJuego, JUEGO);
        cardsPanel.add(vistaRanking, RANKING);
        cardsPanel.add(vistaJuegoTerminado, GAMEOVER);

        add(cardsPanel, BorderLayout.CENTER);

        showMenu();
        setVisible(true);
    }

    public ControladorPrincipal getControlador() {
        return controlador;
    }

    public void showMenu() {
        // parar juego si estaba en ejecución
        if (vistaJuego != null) {
			vistaJuego.detener();
		}
        // actualizar créditos antes de mostrar
        if (vistaMenu != null) {
			vistaMenu.actualizarCreditos();
		}
        cardLayout.show(cardsPanel, MENU);
    }

    public void showJuego() {
        cardLayout.show(cardsPanel, JUEGO);
        // iniciar loop/timer del panel de juego
        if (vistaJuego != null) {
			vistaJuego.iniciar();
		}
    }

    public void showRanking() {
        // parar juego si estaba en ejecución
        if (vistaJuego != null) {
			vistaJuego.detener();
		}
        // actualizar ranking antes de mostrar
        if (vistaRanking != null) {
			vistaRanking.actualizarRanking();
		}
        cardLayout.show(cardsPanel, RANKING);
    }

    public void showGameOver(){
        if(vistaJuego != null){
            vistaJuego.detener();
        }
        cardLayout.show(cardsPanel, GAMEOVER);
    }

    public void pasarNombre(String nombre){
            controlador.pasarNombre(nombre);
    }
}