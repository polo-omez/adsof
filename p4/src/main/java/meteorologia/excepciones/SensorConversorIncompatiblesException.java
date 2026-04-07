
package meteorologia.excepciones;

import meteorologia.sensores.ISensor;
import meteorologia.procesamiento.IConversor;

public class SensorConversorIncompatiblesException extends Exception {
  private IConversor conversor;
  private ISensor sensor;

  public SensorConversorIncompatiblesException(IConversor conversor, ISensor sensor) {
    super("Error: el conversor no es compatible con el sensor: " + sensor);
    this.conversor = conversor;
    this.sensor = sensor;
  }

  public IConversor getConversor() {
    return conversor;
  }

  public ISensor getSensor() {
    return sensor;
  }

}
