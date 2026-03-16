package redsocial.pruebas;

import redsocial.*;

/**
 * Programa de prueba para verificar el historial de mensajes de un usuario
 * y la actualización dinámica de su nivel de exposición pública.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: PruebaHistorial.java
 */
public class PruebaHistorial {

  public static void main(String[] args) {
    // Creamos un usuario origen
    Usuario emisor = new Usuario("Emisor", 0);

    // Creamos un usuario receptor que empezará por defecto en exposición ALTA
    Usuario receptor = new Usuario("Receptor", 0);

    emisor.addEnlace(receptor, 1);

    System.out.println("=== PRUEBA DE HISTORIAL Y EXPOSICIÓN ===");
    System.out.println("Exposición inicial del receptor: " + receptor.getExposicion()); // Debe ser ALTA

    // MENSAJE 1: Alcance alto (10)
    // Promedio previo: 0 -> Como 10 > 0, debe subir a VIRAL
    Mensaje m1 = new Mensaje("Alerta", 11, emisor); // 11 - 1(coste) = 10 de alcance al llegar
    m1.difunde(emisor.getEnlace(receptor));
    System.out.println("\nLlega Mensaje 1 con alcance " + m1.getAlcanceDisponible());
    System.out.println("Nueva exposición del receptor: " + receptor.getExposicion()); // Debe ser VIRAL

    // MENSAJE 2: Alcance bajo (5)
    // Promedio previo: 10 -> Como 5 <= 10, debe bajar a ALTA
    Mensaje m2 = new Mensaje("Hola", 6, emisor); // 6 - 1(coste) = 5
    m2.difunde(emisor.getEnlace(receptor));
    System.out.println("\nLlega Mensaje 2 con alcance " + m2.getAlcanceDisponible());
    System.out.println("Nueva exposición del receptor: " + receptor.getExposicion()); // Debe ser ALTA

    // MENSAJE 3: Alcance bajo (3)
    // Promedio previo: (10 + 5) / 2 = 7.5 -> Como 3 <= 7.5, debe bajar a MEDIA
    Mensaje m3 = new Mensaje("Spam", 4, emisor); // 4 - 1(coste) = 3
    m3.difunde(emisor.getEnlace(receptor));
    System.out.println("\nLlega Mensaje 3 con alcance " + m3.getAlcanceDisponible());
    System.out.println("Nueva exposición del receptor: " + receptor.getExposicion()); // Debe ser MEDIA

    // MENSAJE 4: Alcance muy alto (20)
    // Promedio previo: (10 + 5 + 3) / 3 = 6.0 -> Como 20 > 6.0, debe subir a ALTA
    Mensaje m4 = new Mensaje("Urgente", 21, emisor); // 21 - 1(coste) = 20
    m4.difunde(emisor.getEnlace(receptor));
    System.out.println("\nLlega Mensaje 4 con alcance " + m4.getAlcanceDisponible());
    System.out.println("Nueva exposición del receptor: " + receptor.getExposicion()); // Debe ser ALTA
  }
}
