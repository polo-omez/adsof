package meteorologia.estrategias;

import java.util.Random;

public class EstrategiaGeneracionSimilar implements IEstrategia {
  private double probDesviacion;
  private double ultimoValor;

  public EstrategiaGeneracionSimilar(double porcentajeDesviacion, double valorInicial) {
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
    Random rand = new Random();
    double min = ultimoValor - ultimoValor * this.probDesviacion;
    double max = ultimoValor + ultimoValor * this.probDesviacion;

    return rand.nextDouble(max - min + 1) + min;

  }
}
