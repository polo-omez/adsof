package meteorologia.estrategias;

import meteorologia.sensores.Rango;
import java.util.Random;

/**
 * Estrategia de generación de valores dentro de un rango específico,
 * con posibilidad de forzar desbordes deliberados.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: EstrategiaGeneracionEnRango.java
 */
public class EstrategiaGeneracionEnRango implements IEstrategia {
  /** Rango dentro del cual se generan los valores. */
  private Rango rango;
  /** Probabilidad de que se genere un valor fuera de rango. */
  private double probabilidadDesborde;
  /** Instancia de generador de números aleatorios. */
  private Random rand;

  /**
   * Construye la estrategia asociándola a un rango y fijando una probabilidad de
   * fallo.
   *
   * @param rango                El rango permitido.
   * @param probabilidadDesborde Probabilidad de salir del rango (entre 0 y 1).
   */
  public EstrategiaGeneracionEnRango(Rango rango, double probabilidadDesborde) {
    this.rango = rango;
    this.rand = new Random();

    if (probabilidadDesborde > 1)
      this.probabilidadDesborde = 1;
    else if (probabilidadDesborde < 0)
      this.probabilidadDesborde = 0;
    else
      this.probabilidadDesborde = probabilidadDesborde;
  }

  @Override
  public double generarValor() {
    double min = this.rango.getValorMinimo();
    double max = this.rango.getValorMaximo();

    if (rand.nextDouble() < this.probabilidadDesborde) {
      if (rand.nextBoolean()) {
        return max + 1.0 + (rand.nextDouble() * 10);
      } else {
        return min - 1.0 - (rand.nextDouble() * 10);
      }
    }

    return min + (max - min) * rand.nextDouble();
  }
}
