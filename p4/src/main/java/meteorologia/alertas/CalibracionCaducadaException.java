package meteorologia.alertas;

import java.time.LocalDate;
import meteorologia.sensores.ISensor;

public class CalibracionCaducadaException extends SensorSinCalibrarException {
  private LocalDate fechaCaducidad;

  public CalibracionCaducadaException(ISensor sensor, LocalDate fechaCaducidad) {
    super(sensor, String.format("Sensor %s sin calibrar (calibración caducada desde %s)",
        sensor.getIdentificador(),
        fechaCaducidad.toString()));

    this.fechaCaducidad = fechaCaducidad;
  }

  public LocalDate getFechaCaducidad() {
    return fechaCaducidad;
  }
}
