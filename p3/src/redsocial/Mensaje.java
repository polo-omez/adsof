package redsocial;

/**
 * Clase que representa un mensaje propagándose por la red social.
 * El mensaje tiene un texto, una capacidad de alcance restante y guarda una
 * referencia al usuario en el que se encuentra actualmente.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: Mensaje.java
 */
public class Mensaje {
  /** El contenido textual del mensaje. */
  private String texto;
  /** Capacidad actual restante del mensaje para seguir difundiéndose. */
  private int alcanceDisponible;
  /** El usuario en el que reside el mensaje en este momento. */
  private Usuario usuarioActual;

  /**
   * Construye un nuevo mensaje especificando su texto, alcance y origen.
   *
   * @param texto             Contenido del mensaje.
   * @param alcanceDisponible Capacidad de difusión inicial.
   * @param usuarioActual     Usuario origen donde arranca el mensaje.
   */
  public Mensaje(String texto, int alcanceDisponible, Usuario usuarioActual) {
    this.texto = texto;
    this.alcanceDisponible = alcanceDisponible;
    this.usuarioActual = usuarioActual;
  }

  /**
   * Obtiene el texto del mensaje.
   *
   * @return Contenido del mensaje.
   */
  public String getTexto() {
    return texto;
  }

  /**
   * Obtiene el alcance restante que le queda al mensaje.
   *
   * @return El alcance numérico disponible.
   */
  public int getAlcanceDisponible() {
    return alcanceDisponible;
  }

  /**
   * Obtiene el usuario donde se encuentra posicionado el mensaje actualmente.
   *
   * @return Objeto Usuario actual.
   */
  public Usuario getUsuarioActual() {
    return usuarioActual;
  }

  /**
   * Intenta difundir el mensaje a través de un enlace específico.
   * Resta el coste del enlace y suma la amplificación del usuario destino.
   *
   * @param e El enlace a través del cual se quiere propagar.
   * @return true si la difusión tuvo éxito, false si el origen no coincide o no
   *         hay alcance suficiente.
   */
  public boolean difunde(Enlace e) {
    if (e.getOrigen() != this.usuarioActual || puedeDifundirPor(e) == false) {
      return false;
    }
    Usuario destino = e.getDestino();

    if (this.aceptadoPor(destino) == false) {
      return false;
    }

    this.usuarioActual = destino;
    this.alcanceDisponible += destino.getCapacidadAmplificacion() - e.costeReal();

    destino.recibirMensaje(this);

    return true;
  }

  /**
   * Intenta difundir el mensaje a través de una secuencia de usuarios previstos.
   * Continúa el recorrido aunque falle un salto, pero devuelve false si hubo
   * algún error durante el proceso completo.
   *
   * @param usuarios Secuencia variable de usuarios (varargs) a visitar.
   * @return true si todos los saltos fueron exitosos, false si al menos uno
   *         falló.
   */
  public boolean difunde(Usuario... usuarios) {
    Usuario actual = this.usuarioActual;
    boolean status = true;

    for (Usuario u : usuarios) {
      Enlace e = actual.getEnlace(u);
      if (e != null && this.difunde(e) != false) {
        actual = u;
      } else {
        status = false;
      }
    }
    return status;
  }

  /**
   * Comprueba si el mensaje dispone de alcance suficiente para cruzar un enlace.
   *
   * @param e El enlace a comprobar.
   * @return true si el alcance es mayor o igual al coste del enlace.
   */
  public boolean puedeDifundirPor(Enlace e) {
    if (this.alcanceDisponible < e.costeReal()) {
      return false;
    }
    return true;
  }

  /**
   * Determina si el usuario destino acepta recibir el mensaje.
   *
   * @param u El usuario destino.
   * @return true siempre, por defecto en esta versión básica.
   */
  public boolean aceptadoPor(Usuario u) {
    return true;
  }

  /**
   * Devuelve la representación en cadena del mensaje mostrando su estado.
   *
   * @return Cadena con el formato "Mensaje (texto:alcance) en @usuario".
   */
  @Override
  public String toString() {
    return "Mensaje (" + this.texto + ":" + this.alcanceDisponible + ")"
        + " en @" + this.usuarioActual.getNombre();
  }
}
