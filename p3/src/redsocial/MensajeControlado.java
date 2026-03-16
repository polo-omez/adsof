package redsocial;

/**
 * Clase que representa un mensaje con restricciones estrictas de difusión.
 * Un MensajeControlado evalúa la rigidez de su política frente a la exposición
 * del usuario destino y se niega categóricamente a viajar a través de enlaces
 * señuelo.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: MensajeControlado.java
 */
public class MensajeControlado extends Mensaje {

  /** Valor numérico que determina la rigidez de la política de difusión. */
  private int rigidez;

  /**
   * Construye un nuevo mensaje controlado especificando su texto, alcance, origen
   * y rigidez.
   *
   * @param texto             Contenido del mensaje.
   * @param alcanceDisponible Capacidad de difusión inicial.
   * @param usuarioActual     Usuario origen donde arranca el mensaje.
   * @param rigidez           Nivel de restricción del mensaje.
   */
  public MensajeControlado(String texto, int alcanceDisponible, Usuario usuarioActual, int rigidez) {
    super(texto, alcanceDisponible, usuarioActual);
    this.rigidez = rigidez;
  }

  /**
   * Obtiene la rigidez del mensaje controlado.
   *
   * @return Valor numérico de la rigidez.
   */
  public int getRigidez() {
    return rigidez;
  }

  /**
   * Comprueba si el mensaje puede propagarse por el enlace dado.
   * Además de comprobar el alcance (comportamiento base), rechaza cualquier
   * EnlaceSeñuelo.
   *
   * @param e El enlace a comprobar.
   * @return true si el enlace es válido, no es un señuelo y hay alcance
   *         suficiente.
   */
  @Override
  public boolean puedeDifundirPor(Enlace e) {
    // Si el enlace es de tipo EnlaceSeñuelo, cortamos la difusión instantáneamente
    if (e instanceof EnlaceSeñuelo) {
      return false;
    }

    // Si es un enlace normal, delegamos en la comprobación estándar de alcance
    return super.puedeDifundirPor(e);
  }

  /**
   * Determina si el usuario destino es válido para recibir este mensaje
   * comparando la exposición del usuario con la rigidez del mensaje.
   *
   * @param u El usuario destino que se va a evaluar.
   * @return true si el nivel de exposición cumple el requisito de rigidez.
   */
  @Override
  public boolean aceptadoPor(Usuario u) {
    Exposicion exp = u.getExposicion();

    switch (exp) {
      case OCULTA:
        return true;
      case BAJA:
        return this.rigidez >= 5;
      case MEDIA:
        return this.rigidez >= 10;
      case ALTA:
        return this.rigidez >= 20;
      case VIRAL:
        return this.rigidez >= 50;
      default:
        return false;
    }
  }

  /**
   * Devuelve la representación en cadena del mensaje mostrando su estado y
   * rigidez.
   *
   * @return Cadena con el formato de mensaje controlado.
   */
  @Override
  public String toString() {
    return super.toString() + " [Rigidez: " + this.rigidez + "]";
  }
}
