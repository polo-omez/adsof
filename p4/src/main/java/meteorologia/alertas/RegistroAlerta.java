package meteorologia.alertas;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import meteorologia.sensores.ISensor;

/**
 * Clase que encapsula la información de una alerta generada para almacenarla
 * en el historial de la Estación Meteorológica.
 */
public class RegistroAlerta {
  private LocalDateTime fecha;
  private AlertaMeteorologicaException excepcion;

  public RegistroAlerta(LocalDateTime fecha, AlertaMeteorologicaException excepcion) {
    this.fecha = fecha;
    this.excepcion = excepcion;
  }

  public LocalDateTime getFechaHora() {
    return fecha;
  }

  public AlertaMeteorologicaException getExcepcion() {
    return excepcion;
  }

  public ISensor getSensorInvolucrado() {
    return excepcion.getSensor();
  }

  @Override
  public String toString() {
    String fechaAlerta = "[" + fecha.truncatedTo(ChronoUnit.SECONDS) + "] ";
    return fechaAlerta + excepcion.getMessage();
  }
}
