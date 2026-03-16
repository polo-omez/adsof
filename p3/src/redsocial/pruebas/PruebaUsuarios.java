package redsocial.pruebas;

import redsocial.*;

/**
 * Programa de prueba enfocado en las restricciones de la clase Usuario.
 * Verifica que se eviten las autorreferencias y los enlaces duplicados
 * hacia un mismo destino.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: PruebaUsuarios.java
 */
public class PruebaUsuarios {

  /**
   * Método principal que ejecuta inserciones de enlaces válidas e inválidas,
   * imprimiendo el resultado booleano de la operación y el conteo final.
   *
   * @param args Argumentos de la línea de comandos (no utilizados).
   */
  public static void main(String[] args) {
    Usuario origen = new Usuario("origen", 5);
    Usuario destino = new Usuario("destino", 3);

    System.out.println(origen.addEnlace(origen, 10)); // false
    System.out.println(origen.addEnlace(destino, 5)); // true
    System.out.println(origen.addEnlace(destino, 20)); // false
    System.out.println(origen.getNumEnlaces()); // 1
  }
}
