import java.util.Comparator;

public class ComparadorDistanciaAsc implements Comparator<CuerpoCeleste> {
    @Override
    public int compare(CuerpoCeleste c1, CuerpoCeleste c2) {
        return Double.compare(c1.getDistancia(), c2.getDistancia());
    }
}