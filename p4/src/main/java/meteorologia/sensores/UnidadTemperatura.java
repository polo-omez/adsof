package meteorologia.sensores;

/**
 * Enumeración que representa las diferentes unidades de medida de temperatura.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: UnidadTemperatura.java
 */
public enum UnidadTemperatura implements IUnidad {
  /** Grados Celsius. */
  CELSIUS("ºC"),
  /** Grados Fahrenheit. */
  FAHRENHEIT("ºF"),
  /** Grados Kelvin. */
  KELVIN("ºK");

  /** Símbolo representativo de la unidad. */
  private String simbolo;

  /**
   * Construye una unidad de temperatura con su símbolo asociado.
   *
   * @param simbolo La cadena de texto que representa el símbolo.
   */
  private UnidadTemperatura(String simbolo) {
    this.simbolo = simbolo;
  }

  /**
   * Obtiene el símbolo representativo de la unidad de temperatura.
   *
   * @return La cadena de texto con el símbolo correspondiente.
   */
  @Override
  public String getSimbolo() {
    return this.simbolo;
  }

}
