package redsocial;

public class Enlace {
  private Usuario origen;
  private Usuario destino;
  private int coste = 0;

  private static int sumaCoste;

  public Enlace(Usuario origen, Usuario destino) {
    this(origen, destino, 1);
  }

  public Enlace(Usuario origen, Usuario destino, int coste) {
    this.origen = origen;
    this.destino = destino;
    this.coste = this.actualizarCoste(coste);
  }

  public int getCoste() {
    return coste;
  }

  public Usuario getOrigen() {
    return origen;
  }

  public Usuario getDestino() {
    return destino;
  }

  public static int getSumaCoste() {
    return sumaCoste;
  }

  public void cambiarDestino(Usuario destino, int coste) {
    this.destino = destino;
    sumaCoste -= this.coste;
    this.coste = this.actualizarCoste(coste);

  }

  public int costeEspecial() {
    return 0;
  }

  public int costeReal() {
    return this.coste + this.costeEspecial();
  }

  private int actualizarCoste(int coste) {
    int valor = coste;

    if (coste < 1) {
      valor = 1;
    }

    sumaCoste += valor;
    return valor;
  }

  @Override
  public String toString() {
    return "(@" + this.origen.getNombre() + "--" + this.coste + "-->" + "@)"
        + this.destino.getNombre();
  }

}
