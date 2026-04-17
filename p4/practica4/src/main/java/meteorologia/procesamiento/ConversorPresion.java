package meteorologia.procesamiento;

import meteorologia.sensores.IUnidad;
import meteorologia.sensores.UnidadPresion;
import meteorologia.excepciones.*;

/**
 * Clase que permite la conversión entre distintas unidades de presión
 * atmosférica.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: ConversorPresion.java
 */
public class ConversorPresion implements IConversor {
  /** Unidad base desde la que se convertirá. */
  private UnidadPresion origen;
  /** Unidad final hacia la que se convertirá. */
  private UnidadPresion destino;

  /**
   * Construye un conversor de presión especificando origen y destino.
   *
   * @param origen  La unidad de presión de origen.
   * @param destino La unidad de presión de destino.
   */
  public ConversorPresion(UnidadPresion origen, UnidadPresion destino) {
    this.origen = origen;
    this.destino = destino;
  }

  @Override
  public IUnidad getUnidadOrigen() {
    return this.origen;
  }

  @Override
  public IUnidad getUnidadDestino() {
    return this.destino;
  }

  @Override
  public double convertir(double valor) throws ConversionNoCompatibleException {
    switch (this.origen) {
      case HECTOPASCALES:
        if (this.destino == UnidadPresion.PASCALES)
          return valor * 100;
        else if (this.destino == UnidadPresion.MILIBARES)
          return valor;
        break;

      case PASCALES:
        if (this.destino == UnidadPresion.HECTOPASCALES)
          return valor / 100;
        break;

      case MILIBARES:
        if (this.destino == UnidadPresion.HECTOPASCALES)
          return valor;
        break;
      default:
        break;
    }

    throw new ConversionNoCompatibleException(this.origen, this.destino);
  }
}
