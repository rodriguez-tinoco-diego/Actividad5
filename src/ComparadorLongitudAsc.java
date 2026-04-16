import java.util.Comparator;

public class ComparadorLongitudAsc implements Comparator<Observacion> {
    @Override
    public int compare(Observacion o1, Observacion o2) {
        return Double.compare(o1.getLongitudConSigno(), o2.getLongitudConSigno());
    }
}
