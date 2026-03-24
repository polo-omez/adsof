
package meteorologia.sensores;

import meteorologia.estrategias.EstrategiaGeneracionEnRango;
import meteorologia.estrategias.EstrategiaGeneracionSimilar;
import meteorologia.estrategias.IEstrategia;

public class SensorHumedad extends SensorMeteorologico {
  private static int contador = 1;
  private static Rango rangoPorcentaje = new Rango(0, 100);

  public SensorHumedad(IEstrategia estrategia) {
    super(String.format("HUM-%04d", contador++), UnidadHumedad.PORCENTAJE, rangoPorcentaje, estrategia);
  }

  public SensorHumedad() {
    this(new EstrategiaGeneracionSimilar(20, 40));
  }

  @Override
  public String toString() {
    return "Sensor Humedad " + super.toString();
  }
}
