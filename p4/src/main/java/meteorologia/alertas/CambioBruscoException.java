package meteorologia.alertas;

import meteorologia.sensores.ISensor;

public class CambioBruscoException extends AlertaMeteorologicaException {
  private double valorAnterior;
  private double valorNuevo;

  public CambioBruscoException(ISensor sensor, double valorAnterior, double valorNuevo) {
    super(sensor, String.format("Cambio brusco en %s: %.1f%s (anterior: %.1f%s)",
        sensor.getIdentificador(),
        valorNuevo, sensor.getUnidadDeLectura().getSimbolo(),
        valorAnterior, sensor.getUnidadDeLectura().getSimbolo()));

    this.valorAnterior = valorAnterior;
    this.valorNuevo = valorNuevo;
  }

  public double getValorAnterior() {
    return valorAnterior;
  }

  public double getValorNuevo() {
    return valorNuevo;
  }
}
