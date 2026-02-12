
package trayectos;

public enum Ritmo {
  RAPIDO(8.0),
  MODERADO(10.0),
  SUAVE(15.0);

  private double ritmo;

  private Ritmo(double ritmo) {
    this.ritmo = ritmo;
  }

  public double getRitmo() {
    return this.ritmo;
  }

}
