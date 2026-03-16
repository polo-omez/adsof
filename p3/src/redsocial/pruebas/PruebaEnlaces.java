package redsocial.pruebas;

import redsocial.*;

/**
 * Programa de prueba enfocado en la validación de la clase Enlace.
 * Comprueba la corrección de costes nulos o negativos y el correcto
 * funcionamiento de la variable estática global de suma de costes.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: PruebaEnlaces.java
 */
public class PruebaEnlaces {

  /**
   * Método principal que ejecuta las validaciones de creación y modificación
   * de los enlaces, imprimiendo los costes resultantes y el acumulado.
   *
   * @param args Argumentos de la línea de comandos (no utilizados).
   */
  public static void main(String[] args) {
    Usuario u1 = new Usuario("u1");
    Usuario u2 = new Usuario("u2");
    Usuario u3 = new Usuario("u3");

    Enlace e1 = new Enlace(u1, u2, -5);
    System.out.println(e1.getCoste()); // 1

    Enlace e2 = new Enlace(u2, u3, 0);
    System.out.println(e2.getCoste()); // 1

    System.out.println(Enlace.getSumaCoste()); // 2

    e1.cambiarDestino(u3, 10);
    System.out.println(e1.getCoste()); // 10
    System.out.println(Enlace.getSumaCoste()); // 11
  }
}
