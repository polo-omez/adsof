package meteorologia.estrategias;

/**
 * Interfaz que define las estrategias de generación de valores aleatorios para
 * sensores.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: IEstrategia.java
 */
public interface IEstrategia {
  /**
   * Genera y devuelve un nuevo valor numérico simulado.
   * * @return Valor numérico generado.
   */
  public double generarValor();
}
