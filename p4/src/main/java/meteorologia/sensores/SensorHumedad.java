
package meteorologia.sensores;

import meteorologia.estrategias.EstrategiaGeneracionEnRango;
import meteorologia.estrategias.IEstrategia;

public class SensorHumedad extends SensorMetereologico {
  private static int contador = 1;

  public SensorHumedad(Rango rango, IEstrategia estrategia) {
    super(String.format("PRES-%04d", contador++), UnidadHumedad.PORCENTAJE, rango, estrategia);
  }

  public SensorHumedad(Rango rango) {
    this(rango, new EstrategiaGeneracionEnRango(rango, 2.5));
  }

}
