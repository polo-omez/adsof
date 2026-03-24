package meteorologia.sensores;

import meteorologia.estrategias.EstrategiaGeneracionEnRango;
import meteorologia.estrategias.EstrategiaGeneracionEnMedia;
import meteorologia.estrategias.IEstrategia;

public class SensorPresionAtmosferica extends SensorMeteorologico {
  private static int contador = 1;
  private static Rango rangoHpascales = new Rango(300, 1100);

  public SensorPresionAtmosferica(IEstrategia estrategia) {
    super(String.format("PRES-%04d", contador++), UnidadPresion.HECTOPASCALES, rangoHpascales, estrategia);
  }

  public SensorPresionAtmosferica() {
    this(new EstrategiaGeneracionEnMedia(40, 500));
  }

  public void cambiarUnidad(UnidadPresion unidad) {
    this.setUnidadDeLectura(unidad);
  }

  @Override
  public String toString() {
    return "Sensor Presión " + super.toString();
  }

}
