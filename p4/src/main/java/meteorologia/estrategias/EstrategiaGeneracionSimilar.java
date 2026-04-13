package meteorologia.estrategias;

import java.util.Random;

/**
 * Estrategia de generación de valores basada en aplicar una desviación sobre el
 * valor anterior.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: EstrategiaGeneracionSimilar.java
 */
public class EstrategiaGeneracionSimilar implements IEstrategia {
  /** Probabilidad de desviación sobre el último valor generado. */
  private double probDesviacion;
  /** Último valor que se generó para usar de referencia. */
  private double ultimoValor;
  /** Instancia de generador de números aleatorios. */
  private Random rand;

  /**
   * Construye la estrategia con una desviación máxima y un valor inicial de
   * semilla.
   *
   * @param porcentajeDesviacion Desviación permitida (de 0 a 100).
   * @param valorInicial         Valor inicial sobre el que arrancar.
   */
  public EstrategiaGeneracionSimilar(double porcentajeDesviacion, double valorInicial) {
    this.rand = new Random();
    if (porcentajeDesviacion > 100)
      this.probDesviacion = 1;
    else if (porcentajeDesviacion < 0)
      this.probDesviacion = 0;
    else
      this.probDesviacion = porcentajeDesviacion / 100;

    this.ultimoValor = valorInicial;
  }

  @Override
  public double generarValor() {
    double min = ultimoValor - ultimoValor * this.probDesviacion;
    double max = ultimoValor + ultimoValor * this.probDesviacion;

    double nuevoValor = rand.nextDouble(max - min + 1) + min;
    this.ultimoValor = nuevoValor;
    return nuevoValor;
  }
}
