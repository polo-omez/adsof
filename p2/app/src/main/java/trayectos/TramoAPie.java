package trayectos;

public class TramoAPie extends TramoTrayecto {
  private Ritmo ritmo;
  private int numParadas;

  public TramoAPie(String origen, String destino, Ritmo ritmo, int numParadas) {
    super(origen, destino);
    this.linea = linea;
    this.numParadas = numParadas;
  }

  @Override
  public String toString() {
    return "En tren de la linea " + this.linea + " " + super.toString();
  }
}
