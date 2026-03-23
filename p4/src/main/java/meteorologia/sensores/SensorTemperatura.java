package meteorologia.sensores;

import meteorologia.estrategias.EstrategiaGeneracionEnRango;
import meteorologia.estrategias.IEstrategia;

public class SensorTemperatura extends SensorMeteorologico {
  private static int contador = 1;
  private static Rango rangoCelsius = new Rango(-273.15, 1000);

  public SensorTemperatura(IEstrategia estrategia) {
    super(String.format("TEMP-%04d", contador++), UnidadTemperatura.CELSIUS, rangoCelsius, estrategia);
  }

  public SensorTemperatura() {
    this(new EstrategiaGeneracionEnRango(rangoCelsius, 0.05));
  }

  public void cambiarUnidad(IUnidad unidad) {
    this.setUnidadDeLectura(unidad);
  }

  @Override
  public String toString() {
    return "Sensor Temperatura " + super.toString();
  }

}
