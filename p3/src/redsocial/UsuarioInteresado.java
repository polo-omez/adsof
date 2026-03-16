package redsocial;

/**
 * Clase que representa a un usuario con un interés especial en la difusión
 * viral.
 * Al buscar un enlace para propagar un mensaje, prioriza siempre el primer
 * enlace
 * cuyo destino tenga una exposición ALTA o VIRAL, ignorando el destino original
 * solicitado.
 * Si no encuentra ninguno con esa exposición, aplica el comportamiento
 * estándar.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: UsuarioInteresado.java
 */
public class UsuarioInteresado extends Usuario {

  /**
   * Construye un UsuarioInteresado con nombre, amplificación y exposición por
   * defecto.
   *
   * @param nombre Nombre identificativo del usuario.
   */
  public UsuarioInteresado(String nombre) {
    super(nombre);
  }

  /**
   * Construye un UsuarioInteresado con nombre y capacidad de amplificación
   * específicos.
   *
   * @param nombre                 Nombre identificativo del usuario.
   * @param capacidadAmplificacion Capacidad de amplificación de mensajes.
   */
  public UsuarioInteresado(String nombre, int capacidadAmplificacion) {
    super(nombre, capacidadAmplificacion);
  }

  /**
   * Construye un UsuarioInteresado especificando todos sus atributos base.
   *
   * @param nombre                 Nombre identificativo del usuario.
   * @param capacidadAmplificacion Capacidad de amplificación de mensajes.
   * @param exposicion             Nivel de visibilidad pública del usuario.
   */
  public UsuarioInteresado(String nombre, int capacidadAmplificacion, Exposicion exposicion) {
    super(nombre, capacidadAmplificacion, exposicion);
  }

  /**
   * Sobrescribe la búsqueda de enlaces.
   * Primero busca si tiene algún enlace saliente hacia un usuario con exposición
   * ALTA o VIRAL. Si lo encuentra, devuelve ese enlace (ignorando el destino
   * original).
   * Si no encuentra ninguno, llama al comportamiento estándar de la clase padre.
   *
   * @param destino El usuario destino original al que se pretendía enviar el
   *                mensaje.
   * @return El enlace seleccionado para la difusión, o null si no hay conexión
   *         válida.
   */
  @Override
  public Enlace getEnlace(Usuario destino) {
    for (int i = 0; i < this.getNumEnlaces(); i++) {
      Enlace e = this.getEnlace(i);
      Exposicion exp = e.getDestino().getExposicion();

      if (exp == Exposicion.ALTA || exp == Exposicion.VIRAL) {
        return e;
      }
    }

    return super.getEnlace(destino);
  }
}
