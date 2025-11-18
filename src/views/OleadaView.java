package views;
import java.util.List;

public class OleadaView {
    private List<NaveInvasoraView> naves;

    public OleadaView(List<NaveInvasoraView> naves) {
        this.naves = naves;
    }

    public OleadaView() {

    }

    public List<NaveInvasoraView> getNavesView() {
        return naves;
    }

    public void setNavesView(  List<NaveInvasoraView> naves) {
        this.naves = naves;
    }
}
