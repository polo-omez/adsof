package meteorologia.sensores;

/**
 * Enumeración que representa las diferentes unidades de medida de humedad.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: UnidadHumedad.java
 */
public enum UnidadHumedad implements IUnidad {
  /** Porcentaje de humedad. */
  PORCENTAJE("%");

  /** Símbolo representativo de la unidad. */
  private String simbolo;

  /**
   * Construye una unidad de humedad con su símbolo asociado.
   *
   * @param simbolo La cadena de texto que representa el símbolo.
   */
  private UnidadHumedad(String simbolo) {
    this.simbolo = simbolo;
  }

  /**
   * Obtiene el símbolo representativo de la unidad de humedad.
   *
   * @return La cadena de texto con el símbolo correspondiente.
   */
  @Override
  public String getSimbolo() {
    return this.simbolo;
  }
}
