package meteorologia.sensores;

import meteorologia.estrategias.EstrategiaGeneracionEnRango;
import meteorologia.estrategias.IEstrategia;

public class SensorPresionAtmosferica extends SensorMetereologico {
  private static int contador = 1;

  public SensorPresionAtmosferica(Rango rango, IEstrategia estrategia) {
    super(String.format("PRES-%04d", contador++), UnidadPresion.HECTOPASCALES, rango, estrategia);
  }

  public SensorPresionAtmosferica(Rango rango) {
    this(rango, new EstrategiaGeneracionEnRango(rango, 2.5));
  }

  public void cambiarUnidad(UnidadPresion unidad) {
    this.setUnidadDeLectura(unidad);
  }

}
