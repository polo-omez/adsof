package meteorologia.procesamiento;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Clase que almacena el historial de lecturas y provee estadísticas básicas.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: ProcesadorDatos.java
 */
public class ProcesadorDatos {
  /** Lista con el historial de lecturas almacenadas. */
  private List<Lectura> historial;
  /** Conversor activo para procesar las lecturas a almacenar. */
  private IConversor conversor;
  /** Valor mínimo registrado históricamente. */
  private double minimoLeido;
  /** Valor máximo registrado históricamente. */
  private double maximoLeido;

  /**
   * Construye un nuevo procesador de datos asignándole un conversor.
   * * @param conversor Conversor a emplear.
   */
  public ProcesadorDatos(IConversor conversor) {
    this.historial = new ArrayList<>();
    this.conversor = conversor;
  }

  /**
   * Registra una nueva lectura en el historial y actualiza las estadísticas.
   * * @param valorLectura Valor numérico de la nueva lectura.
   * 
   * @param fechaLectura Fecha y hora del registro.
   */
  public void addLectura(double valorLectura, LocalDateTime fechaLectura) {
    if (this.historial.isEmpty() == true) {
      this.minimoLeido = valorLectura;
      this.maximoLeido = valorLectura;
    } else if (valorLectura > this.maximoLeido)
      this.maximoLeido = valorLectura;
    else if (valorLectura < this.minimoLeido)
      this.minimoLeido = valorLectura;

    this.historial.add(new Lectura(valorLectura, fechaLectura));
  }

  /**
   * Obtiene el conversor en uso.
   * * @return Objeto IConversor.
   */
  public IConversor getConversor() {
    return this.conversor;
  }

  /**
   * Obtiene el valor máximo registrado.
   * * @return El valor numérico más alto del historial.
   */
  public double getMaximoLeido() {
    return this.maximoLeido;
  }

  /**
   * Obtiene el valor mínimo registrado.
   * * @return El valor numérico más bajo del historial.
   */
  public double getMinimoLeido() {
    return this.minimoLeido;
  }

  /**
   * Calcula la media aritmética de todas las lecturas registradas.
   * * @return La media calculada.
   */
  public double getMedia() {
    if (historial.isEmpty())
      return 0;

    double sum = 0;
    for (Lectura lectura : historial) {
      sum += lectura.getValor();
    }

    return sum / historial.size();
  }

  /**
   * Asigna un nuevo conversor al procesador.
   * * @param conversor El conversor a asignar.
   */
  public void setConversor(IConversor conversor) {
    this.conversor = conversor;
  }

  @Override
  public String toString() {
    StringJoiner lecturas = new StringJoiner(", ", "[", "]");
    for (Lectura lectura : historial) {
      lecturas.add(String.format("%.2f", lectura.getValor()));
    }

    return lecturas
        + String.format(" MIN: %.2f, MAX: %.2f, AVG: %.2f", this.minimoLeido, this.maximoLeido, this.getMedia());
  }
}
