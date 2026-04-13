package meteorologia.procesamiento;

import meteorologia.sensores.IUnidad;
import meteorologia.excepciones.*;

/**
 * Interfaz que define las operaciones de un conversor de unidades
 * meteorológicas.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: IConversor.java
 */
public interface IConversor {
  /**
   * Obtiene la unidad de origen del conversor.
   * * @return Unidad de origen.
   */
  public IUnidad getUnidadOrigen();

  /**
   * Obtiene la unidad de destino del conversor.
   * * @return Unidad de destino.
   */
  public IUnidad getUnidadDestino();

  /**
   * Convierte un valor numérico desde la unidad origen hacia la unidad destino.
   * * @param valor El valor numérico a convertir.
   * 
   * @return El valor convertido.
   * @throws ConversionNoCompatibleException Si hay error al convertir.
   */
  public double convertir(double valor) throws ConversionNoCompatibleException;
}
