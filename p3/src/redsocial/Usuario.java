package redsocial;

import java.util.*;

/**
 * Clase que representa a un usuario dentro de la red social.
 * Un usuario tiene una capacidad de amplificación de mensajes y puede
 * conectarse
 * con otros usuarios a través de enlaces dirigidos.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: Usuario.java
 */
public class Usuario {
  /** Nombre o identificador único del usuario. */
  private String nombre;
  /** Cantidad de alcance que el usuario añade a un mensaje al difundirlo. */
  private int capacidadAmplificacion;
  /** Lista ordenada de los enlaces salientes desde este usuario hacia otros. */
  private List<Enlace> enlaces;
  /** Nivel de visibilidad pública del usuario en la red. */
  private Exposicion exposicion;
  /** Historial de mensajes que el usuario ha recibido. */
  private List<Mensaje> historial;

  /**
   * Construye un usuario con un nombre específico, capacidad de amplificación por
   * defecto (2)
   * y una exposición pública por defecto (ALTA).
   *
   * @param nombre El nombre identificativo del usuario.
   */
  public Usuario(String nombre) {
    this(nombre, 2, Exposicion.ALTA);
  }

  /**
   * Construye un usuario con un nombre, capacidad de amplificación específica
   * y exposición pública por defecto (ALTA).
   *
   * @param nombre                 El nombre identificativo del usuario.
   * @param capacidadAmplificacion El valor de alcance extra que aportará a los
   *                               mensajes.
   */
  public Usuario(String nombre, int capacidadAmplificacion) {
    this(nombre, capacidadAmplificacion, Exposicion.ALTA);
  }

  /**
   * Construye un usuario especificando todos sus atributos, incluyendo su nivel
   * de exposición.
   * Inicializa la lista de enlaces vacía.
   *
   * @param nombre                 El nombre identificativo del usuario.
   * @param capacidadAmplificacion El valor de alcance extra que aportará a los
   *                               mensajes.
   * @param exposicion             El nivel de visibilidad pública del usuario.
   */
  public Usuario(String nombre, int capacidadAmplificacion, Exposicion exposicion) {
    this.nombre = nombre;
    this.capacidadAmplificacion = capacidadAmplificacion;
    this.enlaces = new ArrayList<>();
    this.exposicion = exposicion;
    this.historial = new ArrayList<>();
  }

  /**
   * Obtiene el nombre del usuario.
   *
   * @return El nombre identificativo del usuario.
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * Obtiene el nivel de exposición pública actual del usuario.
   *
   * @return El valor del enumerado Exposicion asociado al usuario.
   */
  public Exposicion getExposicion() {
    return this.exposicion;
  }

  /**
   * Modifica el nivel de exposición pública del usuario durante la ejecución.
   *
   * @param e El nuevo nivel de exposición que se asignará.
   */
  public void cambiarExposicion(Exposicion e) {
    this.exposicion = e;
  }

  /**
   * Obtiene la capacidad de amplificación del usuario.
   *
   * @return El valor numérico de la capacidad de amplificación.
   */
  public int getCapacidadAmplificacion() {
    return capacidadAmplificacion;
  }

  /**
   * Obtiene el i-ésimo enlace saliente del usuario según su orden de creación.
   *
   * @param i El índice del enlace que se desea obtener.
   * @return El objeto Enlace correspondiente a ese índice.
   */
  public Enlace getEnlace(int i) {
    return enlaces.get(i);
  }

  /**
   * Obtiene la cantidad total de enlaces salientes de este usuario.
   *
   * @return El número de conexiones salientes.
   */
  public int getNumEnlaces() {
    return enlaces.size();
  }

  /**
   * Busca y obtiene el enlace directo desde este usuario hacia el usuario destino
   * especificado.
   *
   * @param destino El usuario destino de la conexión.
   * @return El objeto Enlace si existe la conexión, o null en caso contrario.
   */
  public Enlace getEnlace(Usuario destino) {
    for (Enlace enlace : this.enlaces) {
      if (enlace.getDestino() == destino) {
        return enlace;
      }
    }
    return null;
  }

  /**
   * Añade un nuevo enlace a la colección de enlaces salientes del usuario.
   * Solo se añadirá si el origen del enlace es este usuario, no es una
   * autorreferencia
   * y no existe ya un enlace hacia ese mismo destino.
   *
   * @param e El objeto Enlace que se desea añadir.
   * @return true si el enlace se añadió correctamente, false si no cumplía las
   *         condiciones.
   */
  public boolean addEnlace(Enlace e) {
    Usuario destino = e.getDestino();

    if (e.getOrigen() != this || destino == this) {
      return false;
    }

    if (getEnlace(destino) != null) {
      return false;
    }

    this.enlaces.add(e);
    return true;
  }

  /**
   * Sobrecarga del método para crear y añadir un enlace directamente recibiendo
   * el destino y el coste.
   *
   * @param destino El usuario destino del nuevo enlace.
   * @param coste   El coste de propagación del nuevo enlace.
   * @return true si se creó y añadió correctamente, false en caso contrario.
   */
  public boolean addEnlace(Usuario destino, int coste) {
    Enlace e = new Enlace(this, destino, coste);
    return this.addEnlace(e);
  }

  /**
   * Recibe un mensaje, evalúa su alcance frente al promedio histórico,
   * ajusta la exposición pública del usuario y guarda el mensaje en el historial.
   *
   * @param m El mensaje que acaba de llegar al usuario.
   */
  public void recibirMensaje(Mensaje m) {
    double promedio = 0.0;

    // Calcular el promedio si ya hay mensajes en el historial
    if (!this.historial.isEmpty()) {
      double suma = 0;
      for (Mensaje msg : this.historial) {
        suma += msg.getAlcanceDisponible();
      }
      promedio = suma / this.historial.size();
    }

    // Ajustar exposición comparando el alcance actual con el promedio
    if (m.getAlcanceDisponible() > promedio) {
      this.subirExposicion();
    } else {
      this.bajarExposicion();
    }

    // Guardar el mensaje en el historial
    this.historial.add(m);
  }

  /**
   * Aumenta la exposición pública en un nivel (con límite superior en VIRAL).
   */
  private void subirExposicion() {
    int nivelActual = this.exposicion.ordinal();
    if (nivelActual < Exposicion.values().length - 1) {
      this.exposicion = Exposicion.values()[nivelActual + 1];
    }
  }

  /**
   * Disminuye la exposición pública en un nivel (con límite inferior en OCULTA).
   */
  private void bajarExposicion() {
    int nivelActual = this.exposicion.ordinal();
    if (nivelActual > 0) {
      this.exposicion = Exposicion.values()[nivelActual - 1];
    }
  }

  /**
   * Devuelve una representación en cadena del usuario, incluyendo su capacidad y
   * la lista de sus enlaces salientes.
   *
   * @return Cadena con el formato "@nombre (amplificacion) [(enlaces)]".
   */
  @Override
  public String toString() {
    StringJoiner sj = new StringJoiner(", ", "[", "]");
    for (Enlace enlace : this.enlaces) {
      sj.add(enlace.toString());
    }

    return "@" + this.nombre + " (" + this.capacidadAmplificacion + ") " + sj.toString();
  }
}
