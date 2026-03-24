package meteorologia.pruebas;

import meteorologia.sensores.*;
import meteorologia.EstacionMeteorologica;
import meteorologia.excepciones.SensorDuplicadoException;
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
      estacion.crearSensor(SensorTemperatura.class, null);
      estacion.crearSensor(SensorPresionAtmosferica.class, null);
      estacion.crearSensor(SensorHumedad.class, null);

    } catch (SensorDuplicadoException e) {
      System.err.println("Error al añadir sensores: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("Error al añadir sensores: " + e.getMessage());
    }

    // 5. Lanzamos una medición puntual fijando la fecha que pide el ejemplo
    estacion.lanzarMedicion(LocalDateTime.now());
    estacion.medicionPeriodica(0.5, 5);

    // 6. IMPRIMIMOS EL RESULTADO FINAL
    System.out.println("\n--- ESTADO ACTUAL DE LOS SENSORES ---");

    // Obtener todos los sensores (suponiendo que tu mapa se pasa a Lista)
    // Imprimir la lista directamente invocará el toString() que acabamos de hacer
    System.out.println(estacion);
  }
}
