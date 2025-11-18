package views;

public class NaveJugadorView{
    private int puntuacion;
    private int vidas;
    private int posX;
    private int posY;
    private int anchoMax;
    private int altoMax;
   

    public NaveJugadorView(int puntuacion, int vidas, int posX, int posY, int ancho, int alto){
        this.puntuacion = puntuacion;
        this.vidas = vidas;
        this.posX = posX;
        this.posY = posY;
        this.anchoMax = ancho;
        this.altoMax = alto;
    }

    public NaveJugadorView(){

    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public int getVidas() {
        return vidas;
    }

    public int getPosX(){
        return posX;
    }

    public int getPosY(){
        return posY;
    }

    public int getAnchoMax() {
        return anchoMax;
    }

    public int getAltoMax() {
        return altoMax;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public void setPosX(int posX){
        this.posX = posX;
    }

    public void setPosY(int posY){
        this.posY = posY;
    }

    public void setAnchoMax(int anchoMax) {
        this.anchoMax = anchoMax;
    }

    public void setAltoMax(int altoMax) {
        this.altoMax = altoMax;
    }

   
}