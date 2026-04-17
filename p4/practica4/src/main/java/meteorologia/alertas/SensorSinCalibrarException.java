package meteorologia.alertas;

import meteorologia.sensores.ISensor;

/**
 * Excepción base para los problemas críticos que invalidan un sensor
 * y obligan a detener su toma de datos.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: SensorSinCalibrarException.java
 */
public abstract class SensorSinCalibrarException extends AlertaMeteorologicaException {

  /**
   * Construye la excepción por falta de calibración o fallo operativo.
   *
   * @param sensor  Sensor inoperativo.
   * @param mensaje Texto explicativo del fallo.
   */
  public SensorSinCalibrarException(ISensor sensor, String mensaje) {
    super(sensor, mensaje);
  }
}
