package meteorologia.alertas;

import java.time.LocalDate;
import meteorologia.sensores.ISensor;

/**
 * Excepción crítica que salta cuando un sensor intenta medir con la calibración
 * expirada.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: CalibracionCaducadaException.java
 */
public class CalibracionCaducadaException extends SensorSinCalibrarException {
  /** Fecha en la que la calibración expiró. */
  private LocalDate fechaCaducidad;

  /**
   * Construye la excepción informando de la fecha de caducidad.
   *
   * @param sensor         El sensor caducado.
   * @param fechaCaducidad La fecha límite superada.
   */
  public CalibracionCaducadaException(ISensor sensor, LocalDate fechaCaducidad) {
    super(sensor, String.format("Sensor %s sin calibrar (calibración caducada desde %s)",
        sensor.getIdentificador(),
        fechaCaducidad.toString()));

    this.fechaCaducidad = fechaCaducidad;
  }

  /**
   * Obtiene la fecha en la que caducó la calibración del sensor.
   *
   * @return Fecha de caducidad en formato LocalDate.
   */
  public LocalDate getFechaCaducidad() {
    return fechaCaducidad;
  }
}
