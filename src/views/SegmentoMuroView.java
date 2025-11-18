package views;

public class SegmentoMuroView{
    private int posX;
    private int posY;
    private double salud;
    private int ancho;
    private int alto;

    public SegmentoMuroView(int posX, int posY, double salud, int ancho, int alto){
        this.posX = posX;
        this.posY = posY;
        this.salud = salud;
        this.ancho = ancho;
        this.alto = alto;
    }

    public SegmentoMuroView(){

    }

    public int getPosX(){
        return posX;
    }

    public int getPosY(){
        return posY;
    }

    public double getSalud(){
        return salud;
    }

    public int getAncho(){
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public boolean estaDestruido() {
		return salud <= 0.0;
	}

    public void setPosX(int posX){
        this.posX = posX;
    }

    public void setPosY(int posY){
        this.posY = posY;
    }

    public void setSalud(double salud) {
		this.salud = salud <= 0.0 ? 0.0 : 1.0;
	}

    public void setAncho(int ancho){
        this.ancho = ancho;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

}