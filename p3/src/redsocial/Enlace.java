package redsocial;

/**
 * Clase que representa un enlace unidireccional entre dos usuarios.
 * Define el coste que supone para un mensaje viajar de un origen a un destino.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: Enlace.java
 */
public class Enlace {
  /** Usuario desde el que parte el enlace. */
  private Usuario origen;
  /** Usuario al que apunta el enlace. */
  private Usuario destino;
  /** Coste numérico que resta al alcance del mensaje al propagarse. */
  private int coste = 0;

  /** Variable estática para acumular el coste de todos los enlaces creados. */
  private static int sumaCoste;

  /**
   * Construye un enlace con un coste por defecto de 1.
   *
   * @param origen  Usuario origen.
   * @param destino Usuario destino.
   */
  public Enlace(Usuario origen, Usuario destino) {
    this(origen, destino, 1);
  }

  /**
   * Construye un enlace con un coste específico.
   * Si el coste es menor o igual a cero, se le asigna 1.
   *
   * @param origen  Usuario origen.
   * @param destino Usuario destino.
   * @param coste   Coste de propagación del enlace.
   */
  public Enlace(Usuario origen, Usuario destino, int coste) {
    this.origen = origen;
    this.destino = destino;
    this.coste = this.actualizarCoste(coste);
  }

  /**
   * Obtiene el coste básico del enlace.
   *
   * @return Coste numérico del enlace.
   */
  public int getCoste() {
    return coste;
  }

  /**
   * Obtiene el usuario origen del enlace.
   *
   * @return Objeto Usuario de origen.
   */
  public Usuario getOrigen() {
    return origen;
  }

  /**
   * Obtiene el usuario destino del enlace.
   *
   * @return Objeto Usuario de destino.
   */
  public Usuario getDestino() {
    return destino;
  }

  /**
   * Obtiene la suma acumulada del coste de todos los enlaces que han sido
   * creados.
   *
   * @return El sumatorio total de costes.
   */
  public static int getSumaCoste() {
    return sumaCoste;
  }

  /**
   * Modifica el usuario destino y el coste del enlace, manteniendo el origen
   * intacto.
   * Actualiza el registro de costes globales restando el antiguo y sumando el
   * nuevo.
   *
   * @param destino El nuevo usuario destino.
   * @param coste   El nuevo coste del enlace.
   */
  public void cambiarDestino(Usuario destino, int coste) {
    this.destino = destino;
    sumaCoste -= this.coste;
    this.coste = this.actualizarCoste(coste);
  }

  /**
   * Devuelve un valor de coste especial
   *
   * @return 0 por defecto para los enlaces estándar.
   */
  public int costeEspecial() {
    return 0;
  }

  /**
   * Calcula el coste real del enlace sumando el coste base más su coste especial.
   *
   * @return El coste real de propagación.
   */
  public int costeReal() {
    return this.coste + this.costeEspecial();
  }

  /**
   * Método auxiliar para validar el coste y sumarlo al total global acumulado.
   *
   * @param coste El coste a validar.
   * @return El coste validado (mínimo 1).
   */
  private int actualizarCoste(int coste) {
    int valor = coste;

    if (coste < 1) {
      valor = 1;
    }

    sumaCoste += valor;
    return valor;
  }

  /**
   * Devuelve la representación en cadena del enlace indicando origen, coste y
   * destino.
   *
   * @return Cadena con el formato "(@origen--coste-->@destino)".
   */
  @Override
  public String toString() {
    return "(@" + this.origen.getNombre() + "--" + this.costeReal() + "-->" + "@"
        + this.destino.getNombre() + ")";
  }
}
