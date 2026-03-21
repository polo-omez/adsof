package meteorologia.sensores;

public class Rango {
  private double valorMinimo;
  private double valorMaximo;

  public Rango(double valorMinimo, double valorMaximo) {
    this.valorMinimo = valorMinimo;
    this.valorMaximo = valorMaximo;
  }

  public double getValorMinimo() {
    return valorMinimo;
  }

  public double getValorMaximo() {
    return valorMaximo;
  }

  public boolean enRango(double valor) {
    if (valor < this.valorMinimo || valor > this.valorMaximo) {
      return false;
    }
    return true;
  }

}
