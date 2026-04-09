package meteorologia.estrategias;

import java.util.*;

public class EstrategiaGeneracionEnMedia implements IEstrategia {
  private double probDesviacion;

  private List<Double> historialValores;
  private Random rand;

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
