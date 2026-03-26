package meteorologia.procesamiento;

import meteorologia.sensores.IUnidad;
import meteorologia.sensores.UnidadTemperatura;
import meteorologia.excepciones.*;

public class ConversorTemperatura implements IConversor {
  private UnidadTemperatura origen;
  private UnidadTemperatura destino;

  public ConversorTemperatura(UnidadTemperatura origen, UnidadTemperatura destino) {
    this.origen = origen;
    this.destino = destino;
  }

  @Override
  public IUnidad getUnidadOrigen() {
    return this.origen;
  }

  @Override
  public IUnidad getUnidadDestino() {
    return this.destino;
  }

  @Override
  public double convertir(double valor) throws ConversionNoCompatibleException {
    switch (this.origen) {
      case FAHRENHEIT:
        if (this.destino == UnidadTemperatura.CELSIUS)
          return (valor - 32) / 1.8;
        break;

      case CELSIUS:
        if (this.destino == UnidadTemperatura.KELVIN)
          return valor + 273.15;

        break;
      case KELVIN:
        if (this.destino == UnidadTemperatura.FAHRENHEIT)
          return ((valor - 273.15) * 1.8) + 32;
        break;
      default:
        break;
    }

    throw new ConversionNoCompatibleException(this.origen, this.destino);
  }
}
