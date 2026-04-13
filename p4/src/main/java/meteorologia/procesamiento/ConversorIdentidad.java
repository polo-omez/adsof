package meteorologia.procesamiento;

import meteorologia.excepciones.ConversionNoCompatibleException;
import meteorologia.sensores.IUnidad;

/**
 * Clase que representa un conversor nulo que devuelve el mismo valor de
 * entrada.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: ConversorIdentidad.java
 */
public class ConversorIdentidad implements IConversor {
  /** Unidad base del conversor. */
  private IUnidad unidad;

  /**
   * Construye un conversor de identidad para una unidad concreta.
   * * @param unidad Unidad base.
   */
  public ConversorIdentidad(IUnidad unidad) {
    this.unidad = unidad;
  }

  @Override
  public IUnidad getUnidadOrigen() {
    return this.unidad;
  }

  @Override
  public IUnidad getUnidadDestino() {
    return this.unidad;
  }

  @Override
  public double convertir(double valor) throws ConversionNoCompatibleException {
    return valor;
  }
}
