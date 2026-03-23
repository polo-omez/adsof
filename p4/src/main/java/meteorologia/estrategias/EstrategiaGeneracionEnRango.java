package meteorologia.estrategias;

import meteorologia.sensores.Rango;
import java.util.Random;

public class EstrategiaGeneracionEnRango implements IEstrategia {
  private Rango rango;
  private double probabilidadDesborde;

  public EstrategiaGeneracionEnRango(Rango rango, double probabilidadDesborde) {
    this.rango = rango;

    if (probabilidadDesborde > 1)
      this.probabilidadDesborde = 1;

    else if (probabilidadDesborde < 0)
      this.probabilidadDesborde = 0;

    else
      this.probabilidadDesborde = probabilidadDesborde;

  }

  @Override
  public double generarValor() {
    Random rand = new Random();
    double min = this.rango.getValorMinimo();
    double max = this.rango.getValorMaximo();

    if (rand.nextDouble() < this.probabilidadDesborde) {
      return rand.nextDouble(max - min + 1) + min + max + 1;
    }

    return rand.nextDouble(max - min + 1) + min;

  }
}
