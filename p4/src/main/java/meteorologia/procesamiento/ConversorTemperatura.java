package meteorologia.procesamiento;

import meteorologia.sensores.IUnidad;
import meteorologia.sensores.UnidadTemperatura;
import meteorologia.excepciones.*;

/**
 * Clase que permite la conversión entre distintas unidades de temperatura.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: ConversorTemperatura.java
 */
public class ConversorTemperatura implements IConversor {
  /** Unidad base desde la que se convertirá. */
  private UnidadTemperatura origen;
  /** Unidad final hacia la que se convertirá. */
  private UnidadTemperatura destino;

  /**
   * Construye un conversor de temperatura especificando origen y destino.
   *
   * @param origen  La unidad de temperatura de origen.
   * @param destino La unidad de temperatura de destino.
   */
  public ConversorTemperatura(UnidadTemperatura origen, UnidadTemperatura destino) {
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
      case FAHRENHEIT:
        if (this.destino == UnidadTemperatura.CELSIUS)
          return (valor - 32) / 1.8;
        break;

      case CELSIUS:
        if (this.destino == UnidadTemperatura.KELVIN)
          return valor + 273.15;
        break;

      case KELVIN:
        if (this.destino == UnidadTemperatura.FAHRENHEIT)
          return ((valor - 273.15) * 1.8) + 32;
        break;
      default:
        break;
    }

    throw new ConversionNoCompatibleException(this.origen, this.destino);
  }
}
