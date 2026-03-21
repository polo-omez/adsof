package meteorologia.estrategias;

import meteorologia.sensores.Rango;

public class EstrategiaGeneracionEnRango implements IEstrategia {
  private Rango rango;
  private double probabilidadDesborde;

  public EstrategiaGeneracionEnRango(Rango rango, double probabilidadDesborde) {
    this.rango = rango;
    this.probabilidadDesborde = probabilidadDesborde;
  }

  @Override
  public double generarValor() {
    return 0;
  }
}
