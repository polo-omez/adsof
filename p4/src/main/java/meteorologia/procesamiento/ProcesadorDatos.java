package meteorologia.procesamiento;

import java.time.LocalDateTime;
import java.util.*;

public class ProcesadorDatos {
  private List<Lectura> historial;
  private IConversor conversor;
  private double minimoLeido;
  private double maximoLeido;

  public ProcesadorDatos(IConversor conversor) {
    this.historial = new ArrayList<>();
    this.conversor = conversor;
  }

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

  public IConversor getConversor() {
    return this.conversor;
  }

  public double getMaximoLeido() {
    return this.maximoLeido;
  }

  public double getMinimoLeido() {
    return this.minimoLeido;
  }

  public double getMedia() {
    if (historial.isEmpty())
      return 0;

    double sum = 0;
    for (Lectura lectura : historial) {
      sum += lectura.getValor();
    }

    return sum / historial.size();
  }

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
