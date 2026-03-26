package meteorologia.procesamiento;

import java.time.LocalDateTime;

public class Lectura {
  private double valor;
  private LocalDateTime fecha;

  public Lectura(double valor, LocalDateTime fecha) {
    this.valor = valor;
    this.fecha = fecha;
  }

  public double getValor() {
    return valor;
  }

  public LocalDateTime getFecha() {
    return fecha;
  }
}
