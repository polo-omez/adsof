package trayectos;

public enum Linea {
  C1("azul claro", 5),
  C4("azul oscuro", 10),
  C5("amarilla", 30);

  private String color;
  private double tiempo;

  private Linea(String color, double tiempo) {
    this.color = color;
    this.tiempo = tiempo;
  }

  public double tiempoEntreParadas() {
    return this.tiempo;
  }

  @Override
  public String toString() {
    return this.name() + " (" + this.color + ")";
  }
}
