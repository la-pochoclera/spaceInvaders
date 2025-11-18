package views;

public class NaveInvasoraView{
    private int posX;
    private int posY;
	private int velocidad;
	private boolean viva;

    public NaveInvasoraView(int posX, int posY, int velocidad, boolean viva){
        this.posX = posX;
        this.posY = posY;
		this.velocidad = velocidad;
        this.viva = viva;
    }

    public NaveInvasoraView(){

    }

    public int getPosX(){
        return posX;
    }

    public int getPosY(){
        return posY;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public boolean getViva() {
        return viva;
    }

    public boolean isViva() {
        return viva;
    }

    public void setPosX(int posX){
        this.posX = posX;
    }

    public void setPosY(int posY){
        this.posY = posY;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public void setViva(boolean viva) {
        this.viva = viva;
    }
}