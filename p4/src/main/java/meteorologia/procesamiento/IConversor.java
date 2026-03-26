package meteorologia.procesamiento;

import meteorologia.sensores.IUnidad;
import meteorologia.excepciones.*;

public interface IConversor {

  public IUnidad getUnidadOrigen();

  public IUnidad getUnidadDestino();

  public double convertir(double valor) throws ConversionNoCompatibleException;
}
