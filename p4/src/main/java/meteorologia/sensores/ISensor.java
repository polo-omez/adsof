package meteorologia.sensores;

import java.time.LocalDate;

public interface ISensor {

  public String getIdentificador();

  public LocalDate getFechaInstalacion();

  public void setFechaInstalacion(LocalDate fecha);

  public void medir();

  public void calibrar(double offset);

  public boolean estaCalibrado();

}
