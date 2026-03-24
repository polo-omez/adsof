package meteorologia.sensores;

/**
 * Clase auxiliar que define los límites numéricos permitidos para las lecturas
 * de un sensor.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: Rango.java
 */
public class Rango {
  /** Límite inferior operativo. */
  private double valorMinimo;
  /** Límite superior operativo. */
  private double valorMaximo;

  /**
   * Construye un rango especificando sus límites.
   *
   * @param valorMinimo El valor más bajo permitido.
   * @param valorMaximo El valor más alto permitido.
   */
  public Rango(double valorMinimo, double valorMaximo) {
    this.valorMinimo = valorMinimo;
    this.valorMaximo = valorMaximo;
  }

  /**
   * Obtiene el límite inferior del rango.
   *
   * @return El valor mínimo permitido.
   */
  public double getValorMinimo() {
    return valorMinimo;
  }

  /**
   * Obtiene el límite superior del rango.
   *
   * @return El valor máximo permitido.
   */
  public double getValorMaximo() {
    return valorMaximo;
  }

  /**
   * Comprueba si un valor dado se encuentra dentro de los límites operativos.
   *
   * @param valor El número a comprobar.
   * @return true si el valor está entre el mínimo y el máximo (ambos inclusive),
   *         false si se sale.
   */
  public boolean enRango(double valor) {
    if (valor < this.valorMinimo || valor > this.valorMaximo) {
      return false;
    }
    return true;
  }
}
