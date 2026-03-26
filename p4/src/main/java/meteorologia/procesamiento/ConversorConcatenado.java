package meteorologia.procesamiento;

import meteorologia.excepciones.ConversionNoCompatibleException;
import meteorologia.sensores.IUnidad;

public class ConversorConcatenado implements IConversor {
  private IConversor conversorOrigen;
  private IConversor conversorDestino;

  public ConversorConcatenado(IConversor origen, IConversor destino) throws ConversionNoCompatibleException {
    if (origen.getUnidadDestino() != destino.getUnidadOrigen()) {
      throw new ConversionNoCompatibleException(origen.getUnidadOrigen(), destino.getUnidadDestino());
    }
    this.conversorOrigen = origen;
    this.conversorDestino = destino;
  }

  @Override
  public IUnidad getUnidadOrigen() {
    return this.conversorOrigen.getUnidadOrigen();
  }

  @Override
  public IUnidad getUnidadDestino() {
    return this.conversorDestino.getUnidadDestino();
  }

  @Override
  public double convertir(double valor) throws ConversionNoCompatibleException {
    double conversionIntermedia = this.conversorOrigen.convertir(valor);
    return this.conversorDestino.convertir(conversionIntermedia);
  }
}
