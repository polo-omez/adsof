package meteorologia.pruebas;

import meteorologia.sensores.*;
import meteorologia.EstacionMeteorologica;
import meteorologia.TipoSensor;
import meteorologia.excepciones.*;
import meteorologia.procesamiento.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class pruebaSimulacionSensores {

  public static void main(String[] args) {
    System.out.println("--- INICIANDO SIMULACIÓN DE LA ESTACIÓN METEOROLÓGICA ---");

    // 1. Creamos la Estación
    EstacionMeteorologica estacion = new EstacionMeteorologica("Estación Central", 40.4165, -3.7026);

    // 2. Creamos los sensores (usarán sus estrategias por defecto internamente)

    // 4. Registramos los sensores en la estación
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

    } catch (SensorDuplicadoException e) {
      System.err.println("Error al añadir sensores: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("Error al añadir sensores: " + e.getMessage());
    }

    // 5. Lanzamos las mediciones
    try {
      estacion.lanzarMedicion(LocalDateTime.now());
      estacion.medicionPeriodica(0.5, 3);
    } catch (ConversionNoCompatibleException e) {
      System.err.println("Error al lanzar mediciones: " + e.getMessage());
    }

    // 6. IMPRIMIMOS EL RESULTADO FINAL
    System.out.println("\n--- ESTADO ACTUAL DE LOS SENSORES ---");

    // Obtener todos los sensores (suponiendo que tu mapa se pasa a Lista)
    // Imprimir la lista directamente invocará el toString() que acabamos de hacer
    System.out.println(estacion);
  }
}
