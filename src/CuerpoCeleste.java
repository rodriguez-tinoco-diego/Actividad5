import java.util.*;

public class CuerpoCeleste {
    private String idCuerpo;
    private String nombre;
    private double distancia;
    private String unidadDistancia;
    private List<TipoComposicion> composicion;
    private List<Observacion> observaciones;

    public CuerpoCeleste(String idCuerpo, String nombre, double distancia, String unidadDistancia)
            throws DistanciaInvalidaException, UnidadDistanciaInvalidaException {
        this(idCuerpo, nombre, distancia, unidadDistancia, new ArrayList<>());
    }

    public CuerpoCeleste(String idCuerpo, String nombre, double distancia,
                         String unidadDistancia, List<TipoComposicion> composicion)
            throws DistanciaInvalidaException, UnidadDistanciaInvalidaException {
        this.idCuerpo = idCuerpo;
        this.nombre = nombre;
        setDistancia(distancia);
        setUnidadDistancia(unidadDistancia);
        this.composicion = (composicion != null) ? composicion : new ArrayList<>();
        this.observaciones = new ArrayList<>();
    }


    public String getIdCuerpo() { return idCuerpo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getDistancia() { return distancia; }
    public void setDistancia(double distancia) throws DistanciaInvalidaException {
        if (distancia <= 0)
            throw new DistanciaInvalidaException("La distancia debe ser positiva.");
        this.distancia = distancia;
    }

    public String getUnidadDistancia() { return unidadDistancia; }
    public void setUnidadDistancia(String unidadDistancia) throws UnidadDistanciaInvalidaException {
        if (!"años luz".equals(unidadDistancia) && !"km/seg".equals(unidadDistancia))
            throw new UnidadDistanciaInvalidaException("Unidad debe ser 'años luz' o 'km/seg'.");
        this.unidadDistancia = unidadDistancia;
    }

    public List<TipoComposicion> getComposicion() {
        return Collections.unmodifiableList(composicion);
    }
    public void setComposicion(List<TipoComposicion> composicion) {
        this.composicion = (composicion != null) ? composicion : new ArrayList<>();
    }


    public void agregarObservacion(Observacion observacion) {
        if (observacion == null)
            throw new IllegalArgumentException("Observación no puede ser nula.");
        for (Observacion o : observaciones) {
            if (o.getIdObservacion().equals(observacion.getIdObservacion()))
                throw new IllegalArgumentException("Ya existe observación con ese ID.");
        }
        observaciones.add(observacion);
    }

    public boolean eliminarObservacion(String idObservacion) {
        return observaciones.removeIf(o -> o.getIdObservacion().equals(idObservacion));
    }

    public List<Observacion> getObservaciones() {
        return Collections.unmodifiableList(observaciones);
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
    public String toString() {
        return String.format("CuerpoCeleste{id='%s', nombre='%s', distancia=%.2f %s, obs=%d}",
                idCuerpo, nombre, distancia, unidadDistancia, observaciones.size());
    }
}