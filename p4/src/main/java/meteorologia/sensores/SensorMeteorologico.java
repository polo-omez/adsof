package meteorologia.sensores;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;

import meteorologia.estrategias.IEstrategia;

public abstract class SensorMeteorologico implements ISensor {
  private String id;
  private double offsetCalibracion;
  private double ultimaLectura;
  private LocalDateTime fechaUltimaLecutra;
  private LocalDate fechaInstalacion;
  private LocalDate fechaUltimaCalibracion;
  private Rango rangoValores;
  private IUnidad unidadDeLectura;
  private IEstrategia estrategiaGeneracion;

  public SensorMeteorologico(String id, IUnidad unidadDeLectura, Rango rangoValores, IEstrategia estrategiaGeneracion) {
    this.id = id;
    this.offsetCalibracion = 0.0;
    this.fechaUltimaLecutra = LocalDateTime.now();
    this.fechaUltimaCalibracion = LocalDate.now();
    this.rangoValores = rangoValores;
    this.unidadDeLectura = unidadDeLectura;
    this.estrategiaGeneracion = estrategiaGeneracion;
  }

  public double getUltimaLectura() {
    return ultimaLectura;
  }

  public double getOffsetCalibracion() {
    return offsetCalibracion;
  }

  public LocalDateTime getFechaUltimaLecutra() {
    return fechaUltimaLecutra;
  }

  public IUnidad getUnidadDeLectura() {
    return unidadDeLectura;
  }

  public Rango getRangoValores() {
    return rangoValores;
  }

  public void setUnidadDeLectura(IUnidad unidadDeLectura) {
    this.unidadDeLectura = unidadDeLectura;
  }

  @Override
  public String getIdentificador() {
    return this.id;
  }

  @Override
  public LocalDate getFechaInstalacion() {
    return fechaInstalacion;
  }

  @Override
  public void calibrar(double offset) {
    this.offsetCalibracion = offset;
    this.fechaUltimaCalibracion = LocalDate.now();

  }

  @Override
  public void setFechaInstalacion(LocalDate fecha) {
    this.fechaInstalacion = fecha;
  }

  @Override
  public boolean estaCalibrado() {
    if (rangoValores.enRango(this.ultimaLectura))
      return LocalDate.now().isBefore(this.fechaUltimaCalibracion);

    return false;
  }

  @Override
  public void medir() {
    ultimaLectura = this.estrategiaGeneracion.generarValor() - offsetCalibracion;
    this.fechaUltimaLecutra = LocalDateTime.now();
  }

  @Override
  public String toString() {
    return String.format("(%.1f%s) última lectura: %s", this.ultimaLectura, this.unidadDeLectura.getSimbolo(),
        this.fechaUltimaLecutra.truncatedTo(ChronoUnit.SECONDS));

  }

}
