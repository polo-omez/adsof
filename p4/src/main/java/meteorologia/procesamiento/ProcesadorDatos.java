package meteorologia.procesamiento;

import java.time.LocalDateTime;
import java.util.*;

public class ProcesadorDatos {
  private List<Lectura> historial;
  private IConversor conversor;

  public ProcesadorDatos(IConversor conversor) {
    this.historial = new ArrayList<>();
    this.conversor = conversor;
  }

  public void addLectura(double valorLectura, LocalDateTime fechaLectura) {
    this.historial.add(new Lectura(valorLectura, fechaLectura));
  }

  public IConversor getConversor() {
    return this.conversor;
  }

  public boolean cambiarConversor(IConversor conversor) {
    this.conversor = conversor;
    return true;
  }
}
