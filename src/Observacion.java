import java.util.Objects;

public class Observacion implements Comparable<Observacion> {
    private String idObservacion;
    private String periodo;
    private double latitud;
    private DireccionLat direccionLat;
    private double longitud;
    private DireccionLong direccionLong;

    public Observacion(String idObservacion, String periodo,
                       double latitud, DireccionLat direccionLat,
                       double longitud, DireccionLong direccionLong) {
        this.idObservacion = idObservacion;
        setPeriodo(periodo);
        setLatitud(latitud);
        setDireccionLat(direccionLat);
        setLongitud(longitud);
        setDireccionLong(direccionLong);
    }


    public String getIdObservacion() { return idObservacion; }
    public String getPeriodo() { return periodo; }
    public double getLatitud() { return latitud; }
    public DireccionLat getDireccionLat() { return direccionLat; }
    public double getLongitud() { return longitud; }
    public DireccionLong getDireccionLong() { return direccionLong; }


    public void setPeriodo(String periodo) {
        if (periodo == null || periodo.trim().isEmpty())
            throw new IllegalArgumentException("El periodo no puede estar vacío.");
        this.periodo = periodo;
    }

    public void setLatitud(double latitud) {
        if (latitud < 0 || latitud > 90)
            throw new IllegalArgumentException("Latitud debe estar entre 0 y 90 grados.");
        this.latitud = latitud;
    }

    public void setDireccionLat(DireccionLat direccionLat) {
        if (direccionLat == null)
            throw new IllegalArgumentException("Dirección de latitud no puede ser nula.");
        this.direccionLat = direccionLat;
    }

    public void setLongitud(double longitud) {
        if (longitud < 0 || longitud > 180)
            throw new IllegalArgumentException("Longitud debe estar entre 0 y 180 grados.");
        this.longitud = longitud;
    }

    public void setDireccionLong(DireccionLong direccionLong) {
        if (direccionLong == null)
            throw new IllegalArgumentException("Dirección de longitud no puede ser nula.");
        this.direccionLong = direccionLong;
    }


    public double getLatitudConSigno() {
        return (direccionLat == DireccionLat.NORTE) ? latitud : -latitud;
    }

    public double getLongitudConSigno() {
        return (direccionLong == DireccionLong.ESTE) ? longitud : -longitud;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Observacion that = (Observacion) o;
        return Objects.equals(idObservacion, that.idObservacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idObservacion);
    }


    @Override
    public int compareTo(Observacion otra) {
        return this.periodo.compareTo(otra.periodo);
    }

    @Override
    public String toString() {
        return String.format("Observacion{id='%s', periodo='%s', lat=%.2f° %s, lon=%.2f° %s}",
                idObservacion, periodo, latitud, direccionLat, longitud, direccionLong);
    }
}