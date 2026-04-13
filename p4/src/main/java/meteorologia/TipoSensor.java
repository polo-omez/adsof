package meteorologia;

import meteorologia.sensores.*;

/**
 * Enumeración que define los tipos de sensores disponibles en el sistema.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: TipoSensor.java
 */
public enum TipoSensor {
  /** Representa un sensor de temperatura. */
  TEMPERATURA,
  /** Representa un sensor de humedad. */
  HUMEDAD,
  /** Representa un sensor de presión atmosférica. */
  PRESION_ATMOSFERICA;

  /**
   * Crea y devuelve una instancia de sensor correspondiente al tipo enumerado.
   *
   * @return Un nuevo objeto ISensor del tipo específico, o null si el tipo es
   *         desconocido.
   */
  public ISensor crearSensor() {
    ISensor sensor;
    switch (this) {
      case TEMPERATURA:
        sensor = new SensorTemperatura();
        break;
      case HUMEDAD:
        sensor = new SensorHumedad();
        break;
      case PRESION_ATMOSFERICA:
        sensor = new SensorPresionAtmosferica();
        break;
      default:
        return null;
    }
    return sensor;
  }
}
