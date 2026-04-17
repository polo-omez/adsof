package meteorologia.estrategias;

import java.util.*;

/**
 * Estrategia de generación de valores basada en la media del historial con una
 * posible desviación.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: EstrategiaGeneracionEnMedia.java
 */
public class EstrategiaGeneracionEnMedia implements IEstrategia {
  /** Probabilidad de desviación sobre la media calculada. */
  private double probDesviacion;
  /** Historial de los valores generados anteriormente. */
  private List<Double> historialValores;
  /** Instancia de generador de números aleatorios. */
  private Random rand;

  /**
   * Construye la estrategia con una desviación máxima y un valor inicial de
   * semilla.
   *
   * @param porcentajeDesviacion Desviación permitida (de 0 a 100).
   * @param valorInicial         Semilla inicial de la media.
   */
  public EstrategiaGeneracionEnMedia(double porcentajeDesviacion, double valorInicial) {
    this.rand = new Random();
    if (porcentajeDesviacion > 100)
      this.probDesviacion = 1;
    else if (porcentajeDesviacion < 0)
      this.probDesviacion = 0;
    else
      this.probDesviacion = porcentajeDesviacion / 100;

    this.historialValores = new ArrayList<>();
    this.historialValores.add(valorInicial);
  }

  @Override
  public double generarValor() {
    double media = historialValores.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    double min = media - media * this.probDesviacion;
    double max = media + media * this.probDesviacion;
    double valorGenerado = rand.nextDouble(max - min + 1) + min;

    this.historialValores.add(valorGenerado);

    return valorGenerado;
  }
}
