package meteorologia.pruebas;

import meteorologia.EstacionMeteorologica;
import meteorologia.TipoSensor;
import meteorologia.excepciones.*;
import meteorologia.procesamiento.*;
import java.time.LocalDateTime;

/**
 * Clase de prueba para verificar el correcto funcionamiento del sistema de
 * alertas.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: PruebaAlertas.java
 */
public class PruebaAlertas {

  /**
   * Método principal de ejecución de la prueba.
   * * @param args Argumentos pasados por línea de comandos.
   */
  public static void main(String[] args) {
    System.out.println("--- INICIANDO SIMULACIÓN DE ALERTAS ---");
    EstacionMeteorologica estacion = new EstacionMeteorologica("Madrid Centro", 40.4168, -3.7038);

    String idTempNormal = "";
    String idTempBrusco = "";
    String idTempCaducado = "";
    String idHumRango = "";

    try {
      idTempNormal = estacion.crearSensor(TipoSensor.TEMPERATURA, null);
      idTempBrusco = estacion.crearSensor(TipoSensor.TEMPERATURA, null);
      idTempCaducado = estacion.crearSensor(TipoSensor.TEMPERATURA, null);
      idHumRango = estacion.crearSensor(TipoSensor.HUMEDAD, null);
    } catch (SensorDuplicadoException e) {
      System.err.println("Error al añadir sensores: " + e.getMessage());
    }

    try {
      estacion.lanzarMedicion(LocalDateTime.now().minusDays(2));
      estacion.calibrarSensor(idTempBrusco, -30.0);
      estacion.calibrarSensor(idTempCaducado, 0.0, -1);
      estacion.calibrarSensor(idHumRango, -200.0);
    } catch (SensorNoEncontradoException | ConversionNoCompatibleException e) {
      System.err.println("Error preparando el hackeo: " + e.getMessage());
    }

    try {
      estacion.lanzarMedicion(LocalDateTime.now());
    } catch (ConversionNoCompatibleException e) {
      System.err.println("Error de conversión: " + e.getMessage());
    }

    System.out.println(" RESULTADO DE LA ESTACIÓN ");
    System.out.println(estacion);
  }
}
