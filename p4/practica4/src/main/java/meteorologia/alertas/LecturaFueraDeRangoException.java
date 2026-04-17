package meteorologia.alertas;

import meteorologia.sensores.ISensor;

/**
 * Excepción crítica que salta cuando un sensor genera una lectura que excede su
 * rango operativo.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: LecturaFueraDeRangoException.java
 */
public class LecturaFueraDeRangoException extends SensorSinCalibrarException {
  /** Valor numérico que provocó el desborde. */
  private double lecturaFallida;

  /**
   * Construye la excepción registrando el valor que salió de los límites.
   *
   * @param sensor         El sensor afectado.
   * @param lecturaFallida El valor numérico registrado.
   */
  public LecturaFueraDeRangoException(ISensor sensor, double lecturaFallida) {
    super(sensor, String.format("Lectura fuera de rango en %s: %.1f%s",
        sensor.getIdentificador(),
        lecturaFallida, sensor.getUnidadDeLectura().getSimbolo()));

    this.lecturaFallida = lecturaFallida;
  }

  /**
   * Obtiene el valor exacto de la medición que fue considerada inválida.
   *
   * @return El valor fallido.
   */
  public double getLecturaFallida() {
    return lecturaFallida;
  }
}
