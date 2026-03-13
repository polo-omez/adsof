package redsocial;

public class Mensaje {
  private String texto;
  private int alcanceDisponible;
  private Usuario usuarioActual;

  public Mensaje(String texto, int alcanceDisponible, Usuario usuarioActual) {
    this.texto = texto;
    this.alcanceDisponible = alcanceDisponible;
    this.usuarioActual = usuarioActual;

  }

  public String getTexto() {
    return texto;
  }

  public int getAlcanceDisponible() {
    return alcanceDisponible;
  }

  public Usuario getUsuarioActual() {
    return usuarioActual;
  }

  public boolean difunde(Enlace e) {
    if (e.getOrigen() != this.usuarioActual || puedeDifundirPor(e) == false) {
      return false;
    }
    Usuario destino = e.getDestino();
    this.usuarioActual = destino;
    this.alcanceDisponible += destino.getCapacidadAmplificacion() - e.getCoste();
    return true;
  }

  public boolean difunde(Usuario... usuarios) {
    Usuario actual = this.usuarioActual;
    boolean status = true;
    Enlace e;

    for (Usuario u : usuarios) {
      if ((e = actual.getEnlace(u)) != null) {
        if ((status = difunde(e)) != false) {
          actual = u;
        }
      } else {
        status = false;
      }
    }
    return status;

  }

  public boolean puedeDifundirPor(Enlace e) {
    if (this.alcanceDisponible < e.getCoste()) {

      return false;
    }
    return true;

  }

  public boolean aceptadoPor(Usuario u) {
    return true;

  }

  @Override
  public String toString() {
    return "Mensaje (" + this.texto + ":" + this.alcanceDisponible + ")"
        + " en @" + this.usuarioActual.getNombre();
  }
}
