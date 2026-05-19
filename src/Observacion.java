
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
        boolean ok = false;
        for (String m : mesesValidos)
            if (periodo.toLowerCase().contains(m)) { ok = true; break; }
        if (!ok) throw new PeriodoInvalidoException("Mes no reconocido: " + periodo);
        this.periodo = periodo;
    }
    public double getLatitud() { return latitud; }
    public void setLatitud(double latitud) throws LatitudInvalidaException {
        if (latitud < 0 || latitud > 90)
            throw new LatitudInvalidaException("Latitud entre 0 y 90 grados.");
        this.latitud = latitud;
    }
    public DireccionLat getDireccionLat() { return direccionLat; }
    public void setDireccionLat(DireccionLat direccionLat) { this.direccionLat = direccionLat; }
    public double getLongitud() { return longitud; }
    public void setLongitud(double longitud) throws LongitudInvalidaException {
        if (longitud < 0 || longitud > 180)
            throw new LongitudInvalidaException("Longitud entre 0 y 180 grados.");
        this.longitud = longitud;
    }
    public DireccionLong getDireccionLong() { return direccionLong; }
    public void setDireccionLong(DireccionLong direccionLong) { this.direccionLong = direccionLong; }

    public double getLatitudConSigno() {
        return (direccionLat == DireccionLat.NORTE) ? latitud : -latitud;
    }
    public double getLongitudConSigno() {
        return (direccionLong == DireccionLong.ESTE) ? longitud : -longitud;
    }


    public String toLinea() {
        return idObservacion + "|" +
                (periodo != null ? periodo : "") + "|" +
                latitud + "|" + direccionLat.name() + "|" +
                longitud + "|" + direccionLong.name();
    }


    public static Observacion fromLinea(String linea)
            throws LatitudInvalidaException, LongitudInvalidaException, PeriodoInvalidoException {
        String[] partes = linea.split("\\|");
        if (partes.length != 6) throw new IllegalArgumentException("Formato incorrecto en observación");
        String id = partes[0];
        String periodo = partes[1];
        double lat = Double.parseDouble(partes[2]);
        DireccionLat dirLat = DireccionLat.valueOf(partes[3]);
        double lon = Double.parseDouble(partes[4]);
        DireccionLong dirLon = DireccionLong.valueOf(partes[5]);
        return new Observacion(id, periodo, lat, dirLat, lon, dirLon);
    }

    @Override
    public String toString() {
        return String.format("Observacion{id='%s', periodo='%s', pos=(%.2f°%s, %.2f°%s)}",
                idObservacion, periodo, latitud, direccionLat, longitud, direccionLong);
    }
}