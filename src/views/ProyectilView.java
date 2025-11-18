package views;

public class ProyectilView{
    private int posX;
    private int posY;
    private int velocidad;
    private boolean esAliado;
    private boolean activo;

    public ProyectilView(int posX, int posY, int velocidad, boolean esAliado,  boolean activo){
        this.posX = posX;
        this.posY = posY;
        this.velocidad = velocidad;
        this.esAliado = esAliado;
        this.activo = activo;
    }

    public ProyectilView(){
        
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

    public boolean isAliado() {
		return esAliado;
	}

	public boolean isActivo() {
		return activo;
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

    public void setEsAliado(boolean esAliado){
        this.esAliado = esAliado;
    }

    public void setActivo(boolean activo){
        this.activo = activo;
    }
}