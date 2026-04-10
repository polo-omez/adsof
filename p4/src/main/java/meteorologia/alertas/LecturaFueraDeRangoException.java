package meteorologia.alertas;

import meteorologia.sensores.ISensor;

public class LecturaFueraDeRangoException extends SensorSinCalibrarException {
  private double lecturaFallida;

  public LecturaFueraDeRangoException(ISensor sensor, double lecturaFallida) {
    super(sensor, String.format("Lectura fuera de rango en %s: %.1f%s",
        sensor.getIdentificador(),
        lecturaFallida, sensor.getUnidadDeLectura().getSimbolo()));

    this.lecturaFallida = lecturaFallida;
  }

  public double getLecturaFallida() {
    return lecturaFallida;
  }
}
