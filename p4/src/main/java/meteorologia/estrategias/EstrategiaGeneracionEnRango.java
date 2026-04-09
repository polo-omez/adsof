package meteorologia.estrategias;

import meteorologia.sensores.Rango;
import java.util.Random;

public class EstrategiaGeneracionEnRango implements IEstrategia {
  private Rango rango;
  private double probabilidadDesborde;
  private Random rand;

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
