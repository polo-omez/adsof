package meteorologia.sensores;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ISensor {

  public String getIdentificador();

  public LocalDate getFechaInstalacion();

  public void setFechaInstalacion(LocalDate fecha);

  public void medir(LocalDateTime fechaMedicion);

  public void calibrar(double offset);

  public boolean estaCalibrado();

}
