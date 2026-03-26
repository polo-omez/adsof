package meteorologia.procesamiento;

import meteorologia.excepciones.ConversionNoCompatibleException;
import meteorologia.sensores.IUnidad;

public class ConversorIdentidad implements IConversor {
  private IUnidad unidad;

  public ConversorIdentidad(IUnidad unidad) {
    this.unidad = unidad;
  }

  @Override
  public IUnidad getUnidadOrigen() {
    return this.unidad;
  }

  @Override
  public IUnidad getUnidadDestino() {
    return this.unidad;
  }

  @Override
  public double convertir(double valor) throws ConversionNoCompatibleException {
    return valor;
  }

}
