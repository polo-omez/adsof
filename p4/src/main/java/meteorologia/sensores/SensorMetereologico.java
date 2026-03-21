package meteorologia.sensores;

import java.time.LocalDateTime;

import meteorologia.estrategias.IEstrategia;

public abstract class SensorMetereologico implements ISensor {
  private String id;
  private double offsetCalibracion;
  private double ultimaLectura;
  private LocalDateTime fechaUltimaLecutra;
  private LocalDateTime fechaInstalacion;
  private Rango rangoValores;
  private IUnidad unidadDeLectura;
  private IEstrategia estrategiaGeneracion;

  public SensorMetereologico(String id, IUnidad unidadDeLectura, Rango rangoValores, IEstrategia estrategiaGeneracion) {
    this.id = id;
    this.offsetCalibracion = 0.0;
    this.fechaUltimaLecutra = LocalDateTime.now();
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
  public LocalDateTime getFechaInstalacion() {
    return fechaInstalacion;
  }

  @Override
  public void calibrar(double offset) {
    this.offsetCalibracion = offset;

  }

  @Override
  public void setFechaInstalacion(LocalDateTime fecha) {
    this.fechaInstalacion = fecha;
  }

  @Override
  public boolean estaCalibrado() {
    return false;
  }

  @Override
  public void medir() {
    ultimaLectura = offsetCalibracion - this.estrategiaGeneracion.generarValor();
    this.fechaUltimaLecutra = LocalDateTime.now();

  }

}
