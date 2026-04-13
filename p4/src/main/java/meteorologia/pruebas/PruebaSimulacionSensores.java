package meteorologia.pruebas;

import meteorologia.sensores.*;
import meteorologia.EstacionMeteorologica;
import meteorologia.TipoSensor;
import meteorologia.excepciones.*;
import meteorologia.procesamiento.*;
import meteorologia.formato.*;

import java.time.LocalDateTime;

/**
 * Clase de prueba para simular el comportamiento básico de los sensores.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: PruebaSimulacionSensores.java
 */
public class PruebaSimulacionSensores {

  /**
   * Método principal de ejecución de la simulación.
   *
   * @param args Argumentos pasados por línea de comandos.
   */
  public static void main(String[] args) {
    System.out.println("--- INICIANDO SIMULACIÓN DE LA ESTACIÓN METEOROLÓGICA ---");

    EstacionMeteorologica estacion = new EstacionMeteorologica("Estación Central", 40.4165, -3.7026);

    try {
      String sensor1 = estacion.crearSensor(TipoSensor.TEMPERATURA, null);
      estacion.asociarConversor(sensor1,
          new ConversorTemperatura((UnidadTemperatura) estacion.getSensor(sensor1).getUnidadDeLectura(),
              UnidadTemperatura.KELVIN));
      estacion.crearSensor(TipoSensor.TEMPERATURA, null);

      String sensor3 = estacion.crearSensor(TipoSensor.PRESION_ATMOSFERICA, null);
      estacion.asociarConversor(sensor3,
          new ConversorPresion((UnidadPresion) estacion.getSensor(sensor3).getUnidadDeLectura(),
              UnidadPresion.PASCALES));
      estacion.crearSensor(TipoSensor.PRESION_ATMOSFERICA, null);
      estacion.crearSensor(TipoSensor.HUMEDAD, null);

    } catch (SensorDuplicadoException | ConversionNoCompatibleException | SensorNoEncontradoException e) {
      System.err.println("Error al añadir sensores: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("Error al añadir sensores: " + e.getMessage());
    }

    try {
      estacion.lanzarMedicion(LocalDateTime.now());
      estacion.medicionPeriodica(0.5, 3);
    } catch (ConversionNoCompatibleException e) {
      System.err.println("Error al lanzar mediciones: " + e.getMessage());
    }

    System.out.println("\n--- ESTADO ACTUAL DE LOS SENSORES ---");
    System.out.println(estacion);
  }
}
