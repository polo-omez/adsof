package meteorologia;

import meteorologia.sensores.*;

public enum TipoSensor {
  TEMPERATURA,
  HUMEDAD,
  PRESION_ATMOSFERICA;

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
