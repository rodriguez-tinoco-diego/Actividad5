import java.util.Comparator;

public class ComparadorLatitudAsc implements Comparator<Observacion> {
    @Override
    public int compare(Observacion o1, Observacion o2) {
        return Double.compare(o1.getLatitudConSigno(), o2.getLatitudConSigno());
    }
}