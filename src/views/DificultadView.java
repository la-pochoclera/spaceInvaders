package views;

public class DificultadView {
    private String etiqueta;
    private double incrementoVelocidad;  

    public DificultadView(String etiqueta, double incrementoVelocidad) {
        this.etiqueta = etiqueta;
        this.incrementoVelocidad = incrementoVelocidad;
    }

    public DificultadView(){
        
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public double getIncrementoVelocidad() {
        return incrementoVelocidad;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public void setIncrementoVelocidad(double incrementoVelocidad) {
        this.incrementoVelocidad = incrementoVelocidad;
    }

}
