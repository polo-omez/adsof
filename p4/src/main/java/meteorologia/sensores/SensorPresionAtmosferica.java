package meteorologia.sensores;

import meteorologia.estrategias.EstrategiaGeneracionEnRango;
import meteorologia.estrategias.EstrategiaGeneracionEnMedia;
import meteorologia.estrategias.IEstrategia;

/**
 * Clase que representa un sensor específico para medir la presión atmosférica.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: SensorPresionAtmosferica.java
 */
public class SensorPresionAtmosferica extends SensorMeteorologico {
  /**
   * Contador estático para generar identificadores únicos de forma secuencial.
   */
  private static int contador = 1;

  /**
   * Construye un sensor de presión asignando una estrategia específica.
   *
   * @param estrategia La estrategia de simulación para generar lecturas.
   */
  public SensorPresionAtmosferica(IEstrategia estrategia) {
    super(String.format("PRES-%04d", contador++), UnidadPresion.HECTOPASCALES, new Rango(300, 1100), estrategia);
  }

  /**
   * Construye un sensor de presión con una estrategia de generación en media por
   * defecto.
   */
  public SensorPresionAtmosferica() {
    this(new EstrategiaGeneracionEnMedia(40, 500));
  }

  /**
   * Modifica la unidad de medida utilizada para las lecturas de presión.
   *
   * @param unidad La nueva unidad de presión a configurar.
   */
  public void cambiarUnidad(UnidadPresion unidad) {
    this.setUnidadDeLectura(unidad);
  }

  /**
   * Devuelve una representación en cadena del sensor de presión atmosférica.
   *
   * @return Cadena indicando el tipo de sensor y sus datos comunes.
   */
  @Override
  public String toString() {
    return "Sensor Presión " + super.toString();
  }
}
