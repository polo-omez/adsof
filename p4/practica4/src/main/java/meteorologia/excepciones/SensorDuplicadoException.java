package meteorologia.excepciones;

import meteorologia.sensores.ISensor;

/**
 * Excepción lanzada cuando se intenta registrar un sensor con un identificador
 * ya existente.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: SensorDuplicadoException.java
 */
public class SensorDuplicadoException extends Exception {
  /** Sensor que ya estaba registrado en el sistema. */
  private ISensor sensorExistente;
  /** Sensor que se intentaba añadir. */
  private ISensor sensorNuevo;

  /**
   * Construye la excepción indicando los sensores conflictivos.
   *
   * @param existente El sensor ya registrado.
   * @param nuevo     El sensor nuevo rechazado.
   */
  public SensorDuplicadoException(ISensor existente, ISensor nuevo) {
    super("Error: Ya existe un sensor con ID " + existente.getIdentificador() + " en el sistema");
    this.sensorExistente = existente;
    this.sensorNuevo = nuevo;
  }

  /**
   * Obtiene el sensor que causó el conflicto por estar ya registrado.
   *
   * @return El sensor existente.
   */
  public ISensor getSensorExistente() {
    return sensorExistente;
  }

  /**
   * Obtiene el sensor que intentó ser registrado de forma duplicada.
   *
   * @return El sensor nuevo.
   */
  public ISensor getSensorNuevo() {
    return sensorNuevo;
  }
}
