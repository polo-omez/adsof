package meteorologia.sensores;

import java.time.LocalDateTime;

public abstract class SensorMetereologico implements ISensor {
  private String id;
  private double offsetCalibracion;
  private double ultimaLectura;
  private LocalDateTime fechaUltimaLecutra;
  private LocalDateTime fechaInstalacion;

  public SensorMetereologico(String id, double offsetCalibracion) {
    this.id = id;
    this.offsetCalibracion = offsetCalibracion;
    this.fechaUltimaLecutra = LocalDateTime.now();

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
  public void medir() {
    this.fechaUltimaLecutra = LocalDateTime.now();

  }

}
