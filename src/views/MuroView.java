package views;

import java.util.List;

public class MuroView{
    private int posX;
    private int posY;
    List<SegmentoMuroView> segmentos;

    public MuroView(int posX, int posY, List<SegmentoMuroView> segmentos){
        this.posX = posX;
        this.posY = posY;
        this.segmentos = segmentos;
    }

    public MuroView(){

    }

    public int getPosX(){
        return posX;
    }

    public int getPosY(){
        return posY;
    }

    public List<SegmentoMuroView> getSegmentos(){
        return segmentos;
    }

    public void setPosX(int posX){
        this.posX = posX;
    }

    public void setPosY(int posY){
        this.posY = posY;
    }

    public void setSegmentos(List<SegmentoMuroView> segmentos){
        this.segmentos = segmentos;
    }
}