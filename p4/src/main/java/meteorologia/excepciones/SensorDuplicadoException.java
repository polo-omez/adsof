package meteorologia.excepciones;

import meteorologia.sensores.ISensor;

public class SensorDuplicadoException extends Exception {
  private ISensor sensorExistente;
  private ISensor sensorNuevo;

  public SensorDuplicadoException(ISensor existente, ISensor nuevo) {
    super("Error: Ya existe un sensor con ID " + existente.getIdentificador() + " en el sistema");
    this.sensorExistente = existente;
    this.sensorNuevo = nuevo;
  }

  public ISensor getSensorExistente() {
    return sensorExistente;
  }

  public ISensor getSensorNuevo() {
    return sensorNuevo;
  }
}
