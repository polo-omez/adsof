package meteorologia.sensores;

/**
 * Enumeración que representa las diferentes unidades de medida de presión
 * atmosférica.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: UnidadPresion.java
 */
public enum UnidadPresion implements IUnidad {
  /** Hectopascales. */
  HECTOPASCALES("hPa"),
  /** Pascales. */
  PASCALES("Pa"),
  /** Milibares. */
  MILIBARES("mbar");

  /** Símbolo representativo de la unidad. */
  private String simbolo;

  /**
   * Construye una unidad de presión con su símbolo asociado.
   *
   * @param simbolo La cadena de texto que representa el símbolo.
   */
  private UnidadPresion(String simbolo) {
    this.simbolo = simbolo;
  }

  /**
   * Obtiene el símbolo representativo de la unidad de presión.
   *
   * @return La cadena de texto con el símbolo correspondiente.
   */
  @Override
  public String getSimbolo() {
    return this.simbolo;
  }
}
