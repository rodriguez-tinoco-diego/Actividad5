import java.util.*;

public class TestActividad6 {
    public static void main(String[] args) {
        CatalogoCuerpos catalogo = new CatalogoCuerpos();

        //Creación de cuerpos válidos (sin excepciones)
        try {
            CuerpoCeleste alfa = new CuerpoCeleste("C001", "Alfa Centauri", 4.37, "años luz");
            CuerpoCeleste betelgeuse = new CuerpoCeleste("C002", "Betelgeuse", 642.5, "años luz");

            catalogo.agregarCuerpo(alfa);
            catalogo.agregarCuerpo(betelgeuse);
            System.out.println("✓ Cuerpos agregados correctamente.");
        } catch (DistanciaInvalidaException | UnidadDistanciaInvalidaException | CuerpoDuplicadoException e) {
            System.err.println("Error al crear/agregar: " + e.getMessage());
        }
        // .

        try {
            CuerpoCeleste alfaDuplicado = new CuerpoCeleste("C001", "Alfa Duplicado", 4.37, "años luz");
            catalogo.agregarCuerpo(alfaDuplicado);
        } catch (CuerpoDuplicadoException e) {
            System.err.println("✓ Excepción capturada (esperada): " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Otra excepción: " + e.getMessage());
        }


        System.out.println("\n--- Prueba de validación de observaciones ---");
        try {
            Observacion obs1 = new Observacion("O001", "Enero", 95.0, DireccionLat.NORTE, 120.0, DireccionLong.ESTE);
        } catch (LatitudInvalidaException e) {
            System.err.println("✓ Latitud inválida: " + e.getMessage());
        } catch (LongitudInvalidaException | PeriodoInvalidoException e) {
            System.err.println("Error: " + e.getMessage());
        }

        try {
            Observacion obs2 = new Observacion("O002", "Enero", 30.0, DireccionLat.NORTE, 190.0, DireccionLong.ESTE);
        } catch (LongitudInvalidaException e) {
            System.err.println("✓ Longitud inválida: " + e.getMessage());
        } catch (LatitudInvalidaException | PeriodoInvalidoException e) {
            System.err.println("Error: " + e.getMessage());
        }

        try {
            Observacion obs3 = new Observacion("O003", "MesInventado", 30.0, DireccionLat.NORTE, 120.0, DireccionLong.ESTE);
        } catch (PeriodoInvalidoException e) {
            System.err.println("✓ Periodo inválido: " + e.getMessage());
        } catch (LatitudInvalidaException | LongitudInvalidaException e) {
            System.err.println("Error: " + e.getMessage());
        }

        try {
            Observacion obsValida = new Observacion("O004", "Marzo", 45.0, DireccionLat.SUR, 80.0, DireccionLong.OESTE);
            CuerpoCeleste alfa = catalogo.buscarPorId("C001");
            alfa.agregarObservacion(obsValida);
            System.out.println("\n✓ Observación agregada correctamente a " + alfa.getNombre());
            System.out.println(alfa.calcularDesplazamiento("Enero", "Marzo"));
        } catch (CuerpoNoEncontradoException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (LatitudInvalidaException | LongitudInvalidaException | PeriodoInvalidoException e) {
            System.err.println("Error en datos de observación: " + e.getMessage());
        }


        try {
            catalogo.buscarPorId("X999");
        } catch (CuerpoNoEncontradoException e) {
            System.err.println("\n✓ Cuerpo no encontrado: " + e.getMessage());
        }


        System.out.println("\n--- Catálogo final ---");
        for (CuerpoCeleste c : catalogo.listarTodos()) {
            System.out.println(c);
        }
    }
}