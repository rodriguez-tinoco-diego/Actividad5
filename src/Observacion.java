
import java.util.Objects;

public class Observacion {
    private String idObservacion;
    private String periodo;
    private double latitud;
    private DireccionLat direccionLat;
    private double longitud;
    private DireccionLong direccionLong;

    public Observacion(String idObservacion, String periodo,
                       double latitud, DireccionLat direccionLat,
                       double longitud, DireccionLong direccionLong)
            throws LatitudInvalidaException, LongitudInvalidaException, PeriodoInvalidoException {
        this.idObservacion = idObservacion;
        setPeriodo(periodo);
        setLatitud(latitud);
        setDireccionLat(direccionLat);
        setLongitud(longitud);
        setDireccionLong(direccionLong);
    }


    public String getIdObservacion() { return idObservacion; }

    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) throws PeriodoInvalidoException {
        if (periodo == null || periodo.trim().isEmpty())
            throw new PeriodoInvalidoException("El periodo no puede estar vacío.");

        String[] mesesValidos = {"enero","febrero","marzo","abril","mayo","junio",
                "julio","agosto","septiembre","octubre","noviembre","diciembre"};
        String periodoLower = periodo.toLowerCase();
        boolean valido = false;
        for (String mes : mesesValidos) {
            if (periodoLower.contains(mes)) {
                valido = true;
                break;
            }
        }
        if (!valido)
            throw new PeriodoInvalidoException("El periodo debe contener un nombre de mes válido: " + periodo);
        this.periodo = periodo;
    }

    public double getLatitud() { return latitud; }
    public void setLatitud(double latitud) throws LatitudInvalidaException {
        if (latitud < 0 || latitud > 90)
            throw new LatitudInvalidaException("Latitud debe estar entre 0 y 90 grados (valor absoluto).");
        this.latitud = latitud;
    }

    public DireccionLat getDireccionLat() { return direccionLat; }
    public void setDireccionLat(DireccionLat direccionLat) {
        this.direccionLat = direccionLat;
    }

    public double getLongitud() { return longitud; }
    public void setLongitud(double longitud) throws LongitudInvalidaException {
        if (longitud < 0 || longitud > 180)
            throw new LongitudInvalidaException("Longitud debe estar entre 0 y 180 grados.");
        this.longitud = longitud;
    }

    public DireccionLong getDireccionLong() { return direccionLong; }
    public void setDireccionLong(DireccionLong direccionLong) {
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
    public String toString() {
        return String.format("Observacion{id='%s', periodo='%s', pos=(%.2f°%s, %.2f°%s)}",
                idObservacion, periodo, latitud, direccionLat, longitud, direccionLong);
    }
}