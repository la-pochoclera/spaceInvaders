package views;

import java.util.List;

public class PartidaView{
    private NaveJugadorView naveJugador;
    private OleadaView oleada;
    private List<MuroView> muros;
    private List<ProyectilView> proyectiles;
    private DificultadView dificultad;
    

    public PartidaView(DificultadView dificultad, 
                      NaveJugadorView naveJugador,
                      OleadaView oleada,
                      List<MuroView> muros,
                      List<ProyectilView> proyectiles) {
        this.naveJugador = naveJugador;
        this.oleada = oleada;
        this.muros = muros;
        this.proyectiles = proyectiles;
        this.dificultad = dificultad;
    }

    public PartidaView(){

    }

    public NaveJugadorView getNaveJugador(){
        return naveJugador;
    }

    public OleadaView getOleada(){
        return oleada;
    }

    public List<MuroView> getMuros(){
        return muros;
    }

    public List<ProyectilView> getProyectiles(){
        return proyectiles;
    }

    public DificultadView getDificultad(){
        return dificultad;
    }

    public void setNaveJugador(NaveJugadorView naveJugador){
        this.naveJugador = naveJugador;
    }
   

    public void setMuros(List<MuroView> muros){
        this.muros = muros;
    }

    public void setProyectiles(List<ProyectilView> proyectiles){
        this.proyectiles = proyectiles;
    }

    public void setDificultad(DificultadView dificultad){
        this.dificultad = dificultad;
    }

    public int getPuntuacion(){
        if (naveJugador != null) {
			return naveJugador.getPuntuacion();
		} else {
			return 0;
		}
    }
}