package meteorologia.excepciones;

/**
 * Excepción lanzada cuando se busca un sensor por identificador y este no
 * existe.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: SensorNoEncontradoException.java
 */
public class SensorNoEncontradoException extends Exception {
  /**
   * Construye la excepción indicando el identificador no encontrado.
   *
   * @param id Identificador buscado.
   */
  public SensorNoEncontradoException(String id) {
    super("Error: No se ha encontrado ningun sensor con ID " + id + " en el sistema");
  }
}
