package meteorologia;

/**
 * Clase que representa unas coordenadas geográficas.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: Ubicacion.java
 */
public class Ubicacion {
  /** Valor de la latitud geográfica. */
  private double latitud;
  /** Valor de la longitud geográfica. */
  private double longitud;

  /**
   * Construye una nueva ubicación especificando sus coordenadas.
   *
   * @param latitud  La latitud geográfica.
   * @param longitud La longitud geográfica.
   */
  public Ubicacion(double latitud, double longitud) {
    this.latitud = latitud;
    this.longitud = longitud;
  }

  /**
   * Obtiene la latitud de la ubicación.
   *
   * @return El valor de la latitud en formato decimal.
   */
  public double getLatitud() {
    return latitud;
  }

  /**
   * Obtiene la longitud de la ubicación.
   *
   * @return El valor de la longitud en formato decimal.
   */
  public double getLongitud() {
    return longitud;
  }

  /**
   * Devuelve una representación en cadena de la ubicación.
   *
   * @return Cadena formateada
   */
  @Override
  public String toString() {
    return this.latitud + " latitud, " + this.longitud + " longitud";
  }
}
