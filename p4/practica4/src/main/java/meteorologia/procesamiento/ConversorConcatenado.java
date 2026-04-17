package meteorologia.procesamiento;

import meteorologia.excepciones.ConversionNoCompatibleException;
import meteorologia.sensores.IUnidad;

/**
 * Clase que permite encadenar dos conversores para realizar transformaciones
 * compuestas.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: ConversorConcatenado.java
 */
public class ConversorConcatenado implements IConversor {
  /** Primer conversor de la cadena. */
  private IConversor conversorOrigen;
  /** Segundo conversor de la cadena. */
  private IConversor conversorDestino;

  /**
   * Construye un conversor concatenado.
   * * @param origen El primer conversor.
   * 
   * @param destino El segundo conversor.
   * @throws ConversionNoCompatibleException Si los conversores no son
   *                                         encadenables.
   */
  public ConversorConcatenado(IConversor origen, IConversor destino) throws ConversionNoCompatibleException {
    if (origen.getUnidadDestino() != destino.getUnidadOrigen()) {
      throw new ConversionNoCompatibleException(origen.getUnidadOrigen(), destino.getUnidadDestino());
    }
    this.conversorOrigen = origen;
    this.conversorDestino = destino;
  }

  @Override
  public IUnidad getUnidadOrigen() {
    return this.conversorOrigen.getUnidadOrigen();
  }

  @Override
  public IUnidad getUnidadDestino() {
    return this.conversorDestino.getUnidadDestino();
  }

  @Override
  public double convertir(double valor) throws ConversionNoCompatibleException {
    double conversionIntermedia = this.conversorOrigen.convertir(valor);
    return this.conversorDestino.convertir(conversionIntermedia);
  }
}
