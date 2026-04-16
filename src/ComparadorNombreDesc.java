import java.util.Comparator;

public class ComparadorNombreDesc implements Comparator<CuerpoCeleste> {
    @Override
    public int compare(CuerpoCeleste c1, CuerpoCeleste c2) {
        return c2.getNombre().compareTo(c1.getNombre()); // orden descendente
    }
}