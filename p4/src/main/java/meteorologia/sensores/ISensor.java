package meteorologia.sensores;

public interface ISensor {

  public String getIdentificador();

  public void medir();

  public void calibrar(double offset);

  public boolean estaCalibrado();

}
