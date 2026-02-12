package trayectos;

public class TramoAPie extends TramoTrayecto {
  private Ritmo ritmo;
  private double distancia;

  public TramoAPie(String origen, String destino, double distancia) {
    this(origen, destino, distancia, Ritmo.MODERADO);
  }

  public TramoAPie(String origen, String destino, double distancia, Ritmo ritmo) {
    super(origen, destino);
    this.ritmo = ritmo;
    this.distancia = distancia;
  }

  public double tiempo() {
    return this.distancia * this.ritmo.getRitmo();
  }

  @Override
  public String toString() {
    return "A pie " + super.toString() + " (ritmo " + this.ritmo + ")";
  }
}
