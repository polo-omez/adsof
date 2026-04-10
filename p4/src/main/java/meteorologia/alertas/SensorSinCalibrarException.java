package meteorologia.alertas;

import meteorologia.sensores.ISensor;

/**
 * Excepción base para los problemas críticos que invalidan un sensor
 * y obligan a detener su toma de datos.
 */
public abstract class SensorSinCalibrarException extends AlertaMeteorologicaException {

  public SensorSinCalibrarException(ISensor sensor, String mensaje) {
    super(sensor, mensaje);
  }
}
