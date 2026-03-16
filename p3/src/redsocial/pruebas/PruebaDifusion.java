package redsocial.pruebas;

import redsocial.*;

/**
 * Programa de prueba para el comportamiento avanzado de propagación de
 * mensajes.
 * Verifica la difusión mediante múltiples usuarios (varargs), asegurando que
 * el sistema ignora destinos inalcanzables pero registra el fallo global.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: PruebaDifusion.java
 */
public class PruebaDifusion {

  /**
   * Método principal que simula una ruta de difusión con obstáculos,
   * imprimiendo el estado del mensaje y el resultado booleano del método difunde.
   *
   * @param args Argumentos de la línea de comandos (no utilizados).
   */
  public static void main(String[] args) {
    Usuario uA = new Usuario("uA", 2);
    Usuario uB = new Usuario("uB", 2);
    Usuario uC = new Usuario("uC", 5);
    Usuario uD = new Usuario("uD", 2);

    uA.addEnlace(uB, 100);
    uA.addEnlace(uC, 10);
    uC.addEnlace(uD, 5);

    Mensaje m = new Mensaje("Alerta", 20, uA);
    System.out.println(m); // Estado inicial

    System.out.println(m.difunde(uB, uC, uD)); // false (porque falló el salto a uB)
    System.out.println(m); // Estado final en @uD con alcance 12
  }
}
