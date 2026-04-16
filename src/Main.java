import java.util.*;

public class Main {
    public static void main(String[] args) {

        CuerpoCeleste alfa = new CuerpoCeleste("C001", "Alfa Centauri", 4.37, "años luz",
                Arrays.asList(TipoComposicion.GASES, TipoComposicion.SOLIDOS));
        CuerpoCeleste sirio = new CuerpoCeleste("C002", "Sirio", 8.6, "años luz",
                Arrays.asList(TipoComposicion.GASES));


        Observacion obs1 = new Observacion("O001", "Enero", 30.5, DireccionLat.NORTE, 120.3, DireccionLong.ESTE);
        Observacion obs2 = new Observacion("O002", "Marzo", 31.2, DireccionLat.NORTE, 121.0, DireccionLong.ESTE);
        Observacion obs3 = new Observacion("O003", "Junio", 15.0, DireccionLat.SUR, 45.0, DireccionLong.OESTE);


        alfa.agregarObservacion(obs1);
        alfa.agregarObservacion(obs2);
        alfa.agregarObservacion(obs3);


        CuerpoCeleste alfa2 = new CuerpoCeleste("C001", "Alfa Centauri", 4.37, "años luz");
        System.out.println("alfa.equals(alfa2): " + alfa.equals(alfa2)); // true
        System.out.println("Mismo hash? " + (alfa.hashCode() == alfa2.hashCode()));


        List<CuerpoCeleste> cuerpos = Arrays.asList(alfa, sirio);
        Collections.sort(cuerpos);
        System.out.println("\nOrden natural por nombre:");
        cuerpos.forEach(System.out::println);


        Collections.sort(cuerpos, new ComparadorDistanciaAsc());
        System.out.println("\nOrden por distancia ascendente:");
        cuerpos.forEach(System.out::println);


        Collections.sort(cuerpos, new ComparadorNombreDesc());
        System.out.println("\nOrden por nombre descendente:");
        cuerpos.forEach(System.out::println);


        System.out.println("\n" + alfa.calcularDesplazamiento("Enero", "Marzo"));
        System.out.println(alfa.calcularDesplazamiento("Enero", "Diciembre"));


        List<Observacion> listaObs = new ArrayList<>(alfa.getObservaciones());
        Collections.sort(listaObs);
        System.out.println("\nObservaciones ordenadas por periodo:");
        listaObs.forEach(System.out::println);


        listaObs.sort(new ComparadorLatitudAsc());
        System.out.println("\nObservaciones ordenadas por latitud:");
        listaObs.forEach(System.out::println);
    }
}
