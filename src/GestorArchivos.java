import java.io.File;
import java.io.IOException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GestorArchivos {

    private static final String ARCHIVO_CUERPOS = "cuerpos.txt";
    private static final String ARCHIVO_OBSERVACIONES = "observaciones.txt";


    public static void guardarCatalogo(CatalogoCuerpos catalogo) throws IOException {
        // Guardar cuerpos
        try (Formatter fmt = new Formatter(new File(ARCHIVO_CUERPOS))) {
            for (CuerpoCeleste c : catalogo.listarTodos()) {
                fmt.format("%s%n", c.toLinea());
            }
        }
        // Guardar observaciones (asociadas a cada cuerpo)
        try (Formatter fmt = new Formatter(new File(ARCHIVO_OBSERVACIONES))) {
            for (CuerpoCeleste c : catalogo.listarTodos()) {
                for (Observacion obs : c.getObservaciones()) {
                    fmt.format("%s|%s%n", c.getIdCuerpo(), obs.toLinea());
                }
            }
        }
    }


    public static CatalogoCuerpos cargarCatalogo()
            throws IOException, DistanciaInvalidaException, UnidadDistanciaInvalidaException,
            LatitudInvalidaException, LongitudInvalidaException, PeriodoInvalidoException,
            CuerpoDuplicadoException {
        CatalogoCuerpos catalogo = new CatalogoCuerpos();


        Map<String, CuerpoCeleste> cuerposMap = new HashMap<>();
        File fCuerpos = new File(ARCHIVO_CUERPOS);
        if (fCuerpos.exists()) {
            try (Scanner sc = new Scanner(fCuerpos)) {
                while (sc.hasNextLine()) {
                    String linea = sc.nextLine().trim();
                    if (linea.isEmpty()) continue;
                    CuerpoCeleste c = CuerpoCeleste.fromLinea(linea);
                    cuerposMap.put(c.getIdCuerpo(), c);
                }
            }
        }


        File fObs = new File(ARCHIVO_OBSERVACIONES);
        if (fObs.exists()) {
            try (Scanner sc = new Scanner(fObs)) {
                while (sc.hasNextLine()) {
                    String linea = sc.nextLine().trim();
                    if (linea.isEmpty()) continue;
                    String[] partes = linea.split("\\|", 2);
                    if (partes.length != 2) continue;
                    String idCuerpo = partes[0];
                    String restoObs = partes[1];
                    Observacion obs = Observacion.fromLinea(restoObs);
                    CuerpoCeleste cuerpo = cuerposMap.get(idCuerpo);
                    if (cuerpo != null) {
                        cuerpo.agregarObservacion(obs);
                    }
                }
            }
        }


        for (CuerpoCeleste c : cuerposMap.values()) {
            catalogo.agregarCuerpo(c);
        }
        return catalogo;
    }
}