
import java.util.*;

public class CatalogoCuerpos {
    private Map<String, CuerpoCeleste> cuerpos;

    public CatalogoCuerpos() {
        cuerpos = new HashMap<>();
    }

    public void agregarCuerpo(CuerpoCeleste cuerpo) throws CuerpoDuplicadoException {
        if (cuerpos.containsKey(cuerpo.getIdCuerpo()))
            throw new CuerpoDuplicadoException("Ya existe un cuerpo con ID: " + cuerpo.getIdCuerpo());
        cuerpos.put(cuerpo.getIdCuerpo(), cuerpo);
    }

    public CuerpoCeleste buscarPorId(String id) throws CuerpoNoEncontradoException {
        CuerpoCeleste c = cuerpos.get(id);
        if (c == null)
            throw new CuerpoNoEncontradoException("No se encontró cuerpo con ID: " + id);
        return c;
    }

    public void eliminarCuerpo(String id) throws CuerpoNoEncontradoException {
        if (cuerpos.remove(id) == null)
            throw new CuerpoNoEncontradoException("No se encontró cuerpo para eliminar: " + id);
    }

    public List<CuerpoCeleste> listarTodos() {
        return new ArrayList<>(cuerpos.values());
    }

    public int tamaño() {
        return cuerpos.size();
    }
}