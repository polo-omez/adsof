package meteorologia.procesamiento;

import meteorologia.sensores.IUnidad;
import meteorologia.sensores.UnidadPresion;
import meteorologia.excepciones.*;

public class ConversorPresion implements IConversor {
  private UnidadPresion origen;
  private UnidadPresion destino;

  public ConversorPresion(UnidadPresion origen, UnidadPresion destino) {
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
      case HECTOPASCALES:
        if (this.destino == UnidadPresion.PASCALES)
          return valor * 100;
        else if (this.destino == UnidadPresion.MILIBARES)
          return valor;
        break;

      case PASCALES:
        if (this.destino == UnidadPresion.HECTOPASCALES)
          return valor / 100;

        break;
      case MILIBARES:
        if (this.destino == UnidadPresion.HECTOPASCALES)
          return valor;
        break;
      default:
        break;
    }

    throw new ConversionNoCompatibleException(this.origen, this.destino);
  }
}
