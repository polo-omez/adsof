package trayectos;

public class TramoTren extends TramoTrayecto {
  private Linea linea;
  private int numParadas;

  public TramoTren(String origen, String destino, Linea linea, int numParadas) {
    super(origen, destino);
    this.linea = linea;
    this.numParadas = numParadas;
  }

  public double tiempo() {
    return this.numParadas * this.linea.tiempoEntreParadas();
  }

  @Override
  public String toString() {
    return "En tren de la linea " + this.linea + " " + super.toString();
  }
}
