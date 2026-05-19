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
        if (distancia <= 0) throw new DistanciaInvalidaException("Distancia positiva");
        this.distancia = distancia;
    }
    public String getUnidadDistancia() { return unidadDistancia; }
    public void setUnidadDistancia(String unidad) throws UnidadDistanciaInvalidaException {
        if (!"años luz".equals(unidad) && !"km/seg".equals(unidad))
            throw new UnidadDistanciaInvalidaException("Unidad inválida");
        this.unidadDistancia = unidad;
    }
    public List<TipoComposicion> getComposicion() { return Collections.unmodifiableList(composicion); }
    public void setComposicion(List<TipoComposicion> comp) { this.composicion = (comp!=null)?comp:new ArrayList<>(); }

    public List<Observacion> getObservaciones() { return Collections.unmodifiableList(observaciones); }
    public void agregarObservacion(Observacion obs) {
        if (obs == null) throw new IllegalArgumentException("Observación nula");
        observaciones.add(obs);
    }
    public boolean eliminarObservacion(String idObs) {
        return observaciones.removeIf(o -> o.getIdObservacion().equals(idObs));
    }


    public String toLinea() {
        StringBuilder sb = new StringBuilder();
        sb.append(idCuerpo).append("|")
                .append(nombre).append("|")
                .append(distancia).append("|")
                .append(unidadDistancia).append("|");

        for (int i = 0; i < composicion.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append(composicion.get(i).name());
        }
        return sb.toString();
    }


    public static CuerpoCeleste fromLinea(String linea)
            throws DistanciaInvalidaException, UnidadDistanciaInvalidaException {
        String[] partes = linea.split("\\|");
        if (partes.length != 5) throw new IllegalArgumentException("Formato incorrecto en cuerpo");
        String id = partes[0];
        String nombre = partes[1];
        double distancia = Double.parseDouble(partes[2]);
        String unidad = partes[3];
        List<TipoComposicion> comps = new ArrayList<>();
        if (!partes[4].isEmpty()) {
            String[] compsStr = partes[4].split(",");
            for (String c : compsStr) comps.add(TipoComposicion.valueOf(c));
        }
        return new CuerpoCeleste(id, nombre, distancia, unidad, comps);
    }

    @Override
    public String toString() {
        return String.format("CuerpoCeleste{id='%s', nombre='%s', distancia=%.2f %s}",
                idCuerpo, nombre, distancia, unidadDistancia);
    }
}