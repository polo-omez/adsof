package meteorologia.alertas;

import meteorologia.sensores.ISensor;

/**
 * Alerta disparada cuando una lectura difiere drásticamente de la anterior.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: CambioBruscoException.java
 */
public class CambioBruscoException extends AlertaMeteorologicaException {
  /** Valor de la medición anterior. */
  private double valorAnterior;
  /** Valor de la medición nueva causante del salto. */
  private double valorNuevo;

  /**
   * Construye la excepción informando del salto entre mediciones.
   *
   * @param sensor        Sensor implicado en el salto.
   * @param valorAnterior Valor de referencia histórico.
   * @param valorNuevo    Valor atípico registrado.
   */
  public CambioBruscoException(ISensor sensor, double valorAnterior, double valorNuevo) {
    super(sensor, String.format("Cambio brusco en %s: %.1f%s (anterior: %.1f%s)",
        sensor.getIdentificador(),
        valorNuevo, sensor.getUnidadDeLectura().getSimbolo(),
        valorAnterior, sensor.getUnidadDeLectura().getSimbolo()));

    this.valorAnterior = valorAnterior;
    this.valorNuevo = valorNuevo;
  }

  /**
   * Obtiene la lectura tomada antes de producirse el cambio.
   *
   * @return El valor anterior numérico.
   */
  public double getValorAnterior() {
    return valorAnterior;
  }

  /**
   * Obtiene la lectura atípica que provocó la alerta.
   *
   * @return El valor nuevo numérico.
   */
  public double getValorNuevo() {
    return valorNuevo;
  }
}
