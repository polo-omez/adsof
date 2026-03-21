package meteorologia.sensores;

import java.time.LocalDateTime;

public interface ISensor {

  public String getIdentificador();

  public LocalDateTime getFechaInstalacion();

  public void setFechaInstalacion(LocalDateTime fecha);

  public void medir();

  public void calibrar(double offset);

  public boolean estaCalibrado();

}
