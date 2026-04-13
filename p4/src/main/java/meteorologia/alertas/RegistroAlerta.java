package meteorologia.alertas;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import meteorologia.sensores.ISensor;

/**
 * Clase que encapsula la información de una alerta generada para almacenarla
 * en el historial de la Estación Meteorológica.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: RegistroAlerta.java
 */
public class RegistroAlerta {
  /** Fecha y hora exactas en la que saltó la alerta. */
  private LocalDateTime fecha;
  /** Excepción encapsulada causante del registro. */
  private AlertaMeteorologicaException excepcion;

  /**
   * Crea un nuevo asiento en el historial asociando un momento de tiempo y su
   * error.
   *
   * @param fecha     Instante de registro.
   * @param excepcion Error a almacenar.
   */
  public RegistroAlerta(LocalDateTime fecha, AlertaMeteorologicaException excepcion) {
    this.fecha = fecha;
    this.excepcion = excepcion;
  }

  /**
   * Obtiene la fecha y hora de la incidencia.
   *
   * @return LocalDateTime con el instante del suceso.
   */
  public LocalDateTime getFechaHora() {
    return fecha;
  }

  /**
   * Obtiene el tipo de alerta que fue guardada.
   *
   * @return La excepción correspondiente a la alerta.
   */
  public AlertaMeteorologicaException getExcepcion() {
    return excepcion;
  }

  /**
   * Extrae de forma rápida el sensor que estuvo implicado en la alerta de este
   * registro.
   *
   * @return Interfaz del sensor que falló.
   */
  public ISensor getSensorInvolucrado() {
    return excepcion.getSensor();
  }

  /**
   * Devuelve una representación en formato texto del suceso ocurrido en el
   * registro.
   *
   * @return Cadena que incluye fecha y el mensaje de alerta.
   */
  @Override
  public String toString() {
    String fechaAlerta = "[" + fecha.truncatedTo(ChronoUnit.SECONDS) + "] ";
    return fechaAlerta + excepcion.getMessage();
  }
}
