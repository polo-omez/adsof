package meteorologia.alertas;

import meteorologia.sensores.ISensor;

/**
 * Excepción base para todas las alertas generadas por los sensores.
 * Contiene el objeto sensor que causó la alerta para su posterior tratamiento.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: AlertaMeteorologicaException.java
 */
public abstract class AlertaMeteorologicaException extends Exception {
  /** Sensor que provocó la generación de la alerta. */
  private ISensor sensorAsociado;

  /**
   * Construye la excepción asociando el sensor y el mensaje de alerta.
   *
   * @param sensor  El sensor implicado en la alerta.
   * @param mensaje El texto explicativo.
   */
  public AlertaMeteorologicaException(ISensor sensor, String mensaje) {
    super(mensaje);
    this.sensorAsociado = sensor;
  }

  /**
   * Obtiene el sensor que disparó la alerta.
   *
   * @return Instancia del sensor involucrado.
   */
  public ISensor getSensor() {
    return sensorAsociado;
  }
}
