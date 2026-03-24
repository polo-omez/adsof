package meteorologia.sensores;

import meteorologia.estrategias.EstrategiaGeneracionEnRango;
import meteorologia.estrategias.EstrategiaGeneracionSimilar;
import meteorologia.estrategias.IEstrategia;

/**
 * Clase que representa un sensor específico para medir la humedad relativa.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: SensorHumedad.java
 */
public class SensorHumedad extends SensorMeteorologico {
  /**
   * Contador estático para generar identificadores únicos de forma secuencial.
   */
  private static int contador = 1;
  /** Rango de valores operativos permitidos en porcentaje. */
  private static Rango rangoPorcentaje = new Rango(0, 100);

  /**
   * Construye un sensor de humedad asignando una estrategia específica.
   *
   * @param estrategia La estrategia de simulación para generar lecturas.
   */
  public SensorHumedad(IEstrategia estrategia) {
    super(String.format("HUM-%04d", contador++), UnidadHumedad.PORCENTAJE, rangoPorcentaje, estrategia);
  }

  /**
   * Construye un sensor de humedad con una estrategia de generación similar por
   * defecto.
   */
  public SensorHumedad() {
    this(new EstrategiaGeneracionSimilar(20, 40));
  }

  /**
   * Devuelve una representación en cadena del sensor de humedad.
   *
   * @return Cadena indicando el tipo de sensor y sus datos comunes.
   */
  @Override
  public String toString() {
    return "Sensor Humedad " + super.toString();
  }
}
