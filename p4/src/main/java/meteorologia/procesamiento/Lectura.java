package meteorologia.procesamiento;

import java.time.LocalDateTime;

/**
 * Clase que encapsula el valor de una lectura junto al momento en el que se
 * tomó.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: Lectura.java
 */
public class Lectura {
  /** Valor numérico de la lectura. */
  private double valor;
  /** Fecha y hora en la que se realizó la lectura. */
  private LocalDateTime fecha;

  /**
   * Construye una nueva lectura.
   * * @param valor Valor numérico registrado.
   * 
   * @param fecha Instante de tiempo del registro.
   */
  public Lectura(double valor, LocalDateTime fecha) {
    this.valor = valor;
    this.fecha = fecha;
  }

  /**
   * Obtiene el valor numérico.
   * * @return El valor leído.
   */
  public double getValor() {
    return valor;
  }

  /**
   * Obtiene la fecha de la lectura.
   * * @return El objeto LocalDateTime con la fecha.
   */
  public LocalDateTime getFecha() {
    return fecha;
  }
}
