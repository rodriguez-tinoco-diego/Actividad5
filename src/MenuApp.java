import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.time.LocalDate;
import java.util.*;

public class MenuApp {
    private static CatalogoCuerpos catalogo;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {

            catalogo = GestorArchivos.cargarCatalogo();
            System.out.println("Datos cargados desde archivos.");
        } catch (Exception e) {
            System.out.println("No se pudieron cargar archivos previos: " + e.getMessage());
            catalogo = new CatalogoCuerpos();
        }

        int opcion;
        do {
            System.out.println("\n=== CATÁLOGO DE CUERPOS CELESTES ===");
            System.out.println("1. Agregar cuerpo");
            System.out.println("2. Listar cuerpos");
            System.out.println("3. Buscar cuerpo por ID");
            System.out.println("4. Eliminar cuerpo");
            System.out.println("5. Agregar observación a un cuerpo");
            System.out.println("6. Listar observaciones de un cuerpo");
            System.out.println("7. Guardar y salir");
            System.out.print("Opción: ");
            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                opcion = 0;
            }

            switch (opcion) {
                case 1 -> agregarCuerpo();
                case 2 -> listarCuerpos();
                case 3 -> buscarCuerpo();
                case 4 -> eliminarCuerpo();
                case 5 -> agregarObservacion();
                case 6 -> listarObservaciones();
                case 7 -> {
                    try {
                        GestorArchivos.guardarCatalogo(catalogo);
                        System.out.println("Datos guardados. ¡Hasta luego!");
                    } catch (IOException e) {
                        System.err.println("Error al guardar: " + e.getMessage());
                    }
                }
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 7);
    }

    private static void agregarCuerpo() {
        try {
            System.out.print("ID único: ");
            String id = sc.nextLine();
            System.out.print("Nombre: ");
            String nombre = sc.nextLine();
            System.out.print("Distancia: ");
            double dist = Double.parseDouble(sc.nextLine());
            System.out.print("Unidad (años luz / km/seg): ");
            String unidad = sc.nextLine();
            System.out.print("Composición (ej: GASES,SOLIDOS) <ENTER si ninguna>: ");
            String compLinea = sc.nextLine();
            List<TipoComposicion> comps = new ArrayList<>();
            if (!compLinea.isEmpty()) {
                for (String c : compLinea.split(",")) {
                    comps.add(TipoComposicion.valueOf(c.trim().toUpperCase()));
                }
            }
            CuerpoCeleste nuevo = new CuerpoCeleste(id, nombre, dist, unidad, comps);
            catalogo.agregarCuerpo(nuevo);
            System.out.println("Cuerpo agregado exitosamente.");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void listarCuerpos() {
        var lista = catalogo.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("No hay cuerpos registrados.");
        } else {
            for (CuerpoCeleste c : lista) {
                System.out.println(c.getIdCuerpo() + " - " + c.getNombre() +
                        " (" + c.getDistancia() + " " + c.getUnidadDistancia() + ")");
            }
        }
    }

    private static void buscarCuerpo() {
        System.out.print("ID del cuerpo: ");
        String id = sc.nextLine();
        try {
            CuerpoCeleste c = catalogo.buscarPorId(id);
            System.out.println(c);
            System.out.println("Composición: " + c.getComposicion());
        } catch (CuerpoNoEncontradoException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void eliminarCuerpo() {
        System.out.print("ID del cuerpo a eliminar: ");
        String id = sc.nextLine();
        try {
            catalogo.eliminarCuerpo(id);
            System.out.println("Cuerpo eliminado (y sus observaciones ya no se guardarán).");
        } catch (CuerpoNoEncontradoException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void agregarObservacion() {
        System.out.print("ID del cuerpo: ");
        String idCuerpo = sc.nextLine();
        try {
            CuerpoCeleste cuerpo = catalogo.buscarPorId(idCuerpo);
            System.out.print("ID de observación: ");
            String idObs = sc.nextLine();
            System.out.print("Periodo (mes, ej. Enero): ");
            String periodo = sc.nextLine();
            System.out.print("Latitud (0-90): ");
            double lat = Double.parseDouble(sc.nextLine());
            System.out.print("Dirección latitud (NORTE/SUR): ");
            DireccionLat dirLat = DireccionLat.valueOf(sc.nextLine().toUpperCase());
            System.out.print("Longitud (0-180): ");
            double lon = Double.parseDouble(sc.nextLine());
            System.out.print("Dirección longitud (ESTE/OESTE): ");
            DireccionLong dirLon = DireccionLong.valueOf(sc.nextLine().toUpperCase());
            Observacion obs = new Observacion(idObs, periodo, lat, dirLat, lon, dirLon);
            cuerpo.agregarObservacion(obs);
            System.out.println("Observación agregada.");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void listarObservaciones() {
        System.out.print("ID del cuerpo: ");
        String idCuerpo = sc.nextLine();
        try {
            CuerpoCeleste cuerpo = catalogo.buscarPorId(idCuerpo);
            var obsList = cuerpo.getObservaciones();
            if (obsList.isEmpty()) {
                System.out.println("No hay observaciones para este cuerpo.");
            } else {
                for (Observacion o : obsList) {
                    System.out.println(o);
                }
            }
        } catch (CuerpoNoEncontradoException e) {
            System.err.println(e.getMessage());
        }
    }
}