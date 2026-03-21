package meteorologia.sensores;

import meteorologia.estrategias.EstrategiaGeneracionEnRango;
import meteorologia.estrategias.IEstrategia;

public class SensorTemperatura extends SensorMetereologico {
  private static int contador = 1;

  public SensorTemperatura(Rango rango, IEstrategia estrategia) {
    super(String.format("TEMP-%04d", contador++), UnidadTemperatura.CELSIUS, rango, estrategia);
  }

  public SensorTemperatura(Rango rango) {
    this(rango, new EstrategiaGeneracionEnRango(rango, 2.5));
  }

  public void cambiarUnidad(IUnidad unidad) {
    this.setUnidadDeLectura(unidad);
  }

}
