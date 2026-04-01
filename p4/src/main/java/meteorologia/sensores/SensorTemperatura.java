package meteorologia.sensores;

import meteorologia.estrategias.EstrategiaGeneracionEnRango;
import meteorologia.estrategias.IEstrategia;
import meteorologia.procesamiento.IConversor;
import meteorologia.procesamiento.ConversorTemperatura;

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
  /** Rango de valores operativos permitidos en grados Celsius. */
  private static Rango rangoCelsius = new Rango(-273.15, 1000);

  /**
   * Construye un sensor de temperatura asignando una estrategia específica.
   *
   * @param estrategia La estrategia de simulación para generar lecturas.
   */
  public SensorTemperatura(IEstrategia estrategia) {
    super(String.format("TEMP-%04d", contador++), UnidadTemperatura.CELSIUS, rangoCelsius, estrategia);
  }

  /**
   * Construye un sensor de temperatura con una estrategia de generación en rango
   * por defecto.
   */
  public SensorTemperatura() {
    this(new EstrategiaGeneracionEnRango(rangoCelsius, 0.05));
  }

  public boolean cambiarConversor(IConversor conversor) {
    if (!ConversorTemperatura.class.isInstance(conversor)
        || conversor.getUnidadOrigen() != this.getUnidadDeLectura()) {
      return false;
    }
    return super.cambiarConversor(conversor);

  }

  /**
   * Devuelve una representación en cadena del sensor de temperatura.
   *
   * @return Cadena con el formato específico indicando el tipo de sensor y sus
   *         datos.
   */
  @Override
  public String toString() {
    return "Sensor Temperatura " + super.toString();
  }
}
