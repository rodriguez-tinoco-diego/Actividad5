import java.util.*;

public class CuerpoCeleste implements Comparable<CuerpoCeleste> {
    private String idCuerpo;
    private String nombre;
    private double distancia;
    private String unidadDistancia;
    private List<TipoComposicion> composicion;
    private List<Observacion> observaciones;


    public CuerpoCeleste(String idCuerpo, String nombre, double distancia, String unidadDistancia) {
        this(idCuerpo, nombre, distancia, unidadDistancia, new ArrayList<>());
    }

    public CuerpoCeleste(String idCuerpo, String nombre, double distancia, String unidadDistancia,
                         List<TipoComposicion> composicion) {
        this.idCuerpo = idCuerpo;
        this.nombre = nombre;
        setDistancia(distancia);
        setUnidadDistancia(unidadDistancia);
        this.composicion = (composicion != null) ? new ArrayList<>(composicion) : new ArrayList<>();
        this.observaciones = new ArrayList<>();
    }


    public String getIdCuerpo() { return idCuerpo; }
    public String getNombre() { return nombre; }
    public double getDistancia() { return distancia; }
    public String getUnidadDistancia() { return unidadDistancia; }
    public List<TipoComposicion> getComposicion() { return Collections.unmodifiableList(composicion); }
    public List<Observacion> getObservaciones() { return Collections.unmodifiableList(observaciones); }


    public void setNombre(String nombre) { this.nombre = nombre; }

    public void setDistancia(double distancia) {
        if (distancia <= 0) throw new IllegalArgumentException("Distancia debe ser positiva.");
        this.distancia = distancia;
    }

    public void setUnidadDistancia(String unidadDistancia) {
        if (!"años luz".equals(unidadDistancia) && !"km/seg".equals(unidadDistancia))
            throw new IllegalArgumentException("Unidad debe ser 'años luz' o 'km/seg'.");
        this.unidadDistancia = unidadDistancia;
    }

    public void setComposicion(List<TipoComposicion> composicion) {
        this.composicion = (composicion != null) ? new ArrayList<>(composicion) : new ArrayList<>();
    }


    public void agregarObservacion(Observacion obs) {
        if (obs == null) throw new IllegalArgumentException("Observación nula.");
        for (Observacion o : observaciones) {
            if (o.getIdObservacion().equals(obs.getIdObservacion()))
                throw new IllegalArgumentException("Ya existe observación con ID " + obs.getIdObservacion());
        }
        observaciones.add(obs);
    }

    public boolean eliminarObservacion(String idObservacion) {
        return observaciones.removeIf(o -> o.getIdObservacion().equals(idObservacion));
    }


    public String calcularDesplazamiento(String periodo1, String periodo2) {
        Observacion obs1 = null, obs2 = null;
        for (Observacion o : observaciones) {
            if (o.getPeriodo().equalsIgnoreCase(periodo1)) obs1 = o;
            if (o.getPeriodo().equalsIgnoreCase(periodo2)) obs2 = o;
        }
        if (obs1 == null || obs2 == null)
            return "No se encontraron observaciones para uno o ambos periodos.";
        double difLat = obs2.getLatitudConSigno() - obs1.getLatitudConSigno();
        double difLon = obs2.getLongitudConSigno() - obs1.getLongitudConSigno();
        return String.format("Desplazamiento de %s a %s: ΔLat = %.2f°, ΔLon = %.2f°",
                periodo1, periodo2, difLat, difLon);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CuerpoCeleste that = (CuerpoCeleste) o;
        return Objects.equals(idCuerpo, that.idCuerpo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCuerpo);
    }


    @Override
    public int compareTo(CuerpoCeleste otro) {
        return this.nombre.compareTo(otro.nombre);
    }

    @Override
    public String toString() {
        return String.format("CuerpoCeleste{id='%s', nombre='%s', distancia=%.2f %s, composición=%s, obs=%d}",
                idCuerpo, nombre, distancia, unidadDistancia, composicion, observaciones.size());
    }
}