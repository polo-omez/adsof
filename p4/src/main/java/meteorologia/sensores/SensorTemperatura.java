package meteorologia.sensores;

import meteorologia.estrategias.EstrategiaGeneracionEnRango;
import meteorologia.estrategias.IEstrategia;

/**
 * Clase que representa un sensor específico para medir la temperatura.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: SensorTemperatura.java
 */
public class SensorTemperatura extends SensorMeteorologico {
  /**
   * Contador estático para generar identificadores únicos de forma secuencial.
   */
  private static int contador = 1;

  /**
   * Construye un sensor de temperatura asignando una estrategia específica.
   *
   * @param estrategia La estrategia de simulación para generar lecturas.
   */
  public SensorTemperatura(IEstrategia estrategia) {
    super(String.format("TEMP-%04d", contador++), UnidadTemperatura.CELSIUS, new Rango(-273.15, 1000), estrategia);
  }

  /**
   * Construye un sensor de temperatura con una estrategia de generación en rango
   * por defecto.
   */
  public SensorTemperatura() {
    this(new EstrategiaGeneracionEnRango(new Rango(-10, 50), 0.05));
  }
}
