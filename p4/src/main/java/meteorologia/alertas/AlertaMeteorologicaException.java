package meteorologia.alertas;

import meteorologia.sensores.ISensor;

/**
 * Excepción base para todas las alertas generadas por los sensores.
 * Contiene el objeto sensor que causó la alerta para su posterior tratamiento.
 */
public abstract class AlertaMeteorologicaException extends Exception {
  private ISensor sensorAsociado;

  public AlertaMeteorologicaException(ISensor sensor, String mensaje) {
    super(mensaje);
    this.sensorAsociado = sensor;
  }

  public ISensor getSensor() {
    return sensorAsociado;
  }
}
