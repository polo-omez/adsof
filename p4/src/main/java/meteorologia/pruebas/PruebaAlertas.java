package meteorologia.pruebas;

import meteorologia.EstacionMeteorologica;
import meteorologia.TipoSensor;
import meteorologia.excepciones.*;
// Importamos el procesamiento por si lo necesitas
import meteorologia.procesamiento.*;
import java.time.LocalDateTime;

public class PruebaAlertas {

  public static void main(String[] args) {
    System.out.println("--- INICIANDO SIMULACIÓN DE ALERTAS ---");

    // 1. Creamos la Estación (usando los datos del PDF del profesor)
    EstacionMeteorologica estacion = new EstacionMeteorologica("Madrid Centro", 40.4168, -3.7038);

    // Variables para guardar los IDs generados
    String idTempNormal = "";
    String idTempBrusco = "";
    String idTempCaducado = "";
    String idHumRango = "";

    // 2. Registramos los sensores en la estación
    try {
      // Un sensor normal para comparar
      idTempNormal = estacion.crearSensor(TipoSensor.TEMPERATURA, null);

      // Sensores que vamos a forzar a fallar
      idTempBrusco = estacion.crearSensor(TipoSensor.TEMPERATURA, null);
      idTempCaducado = estacion.crearSensor(TipoSensor.TEMPERATURA, null);
      idHumRango = estacion.crearSensor(TipoSensor.HUMEDAD, null);

    } catch (SensorDuplicadoException e) {
      System.err.println("Error al añadir sensores: " + e.getMessage());
    }

    // 3. Mediciones preparatorias y "Hackeo" de sensores
    try {
      System.out.println("-> Realizando lectura base normal...");
      // Hacemos una primera lectura donde todos están bien
      estacion.lanzarMedicion(LocalDateTime.now().minusDays(2));

      System.out.println("-> Saboteando los sensores...");

      // (i) Forzar cambio brusco: Le restamos 30 al offset.
      // La próxima lectura será 30 grados más alta de golpe (salto de >50%).
      estacion.calibrarSensor(idTempBrusco, -30.0);

      // (ii) Forzar sensor caducado: Lo calibramos con una duración de -1 días.
      // Esto hace que la fecha de caducidad sea ayer, invalidándolo instantáneamente.
      estacion.calibrarSensor(idTempCaducado, 0.0, -1);

      // (iii) Forzar lectura fuera de rango:
      // La humedad es máximo 100%. Con un offset de -200, lectura = valor - (-200) =
      // valor + 200.
      estacion.calibrarSensor(idHumRango, -200.0);

    } catch (SensorNoEncontradoException | ConversionNoCompatibleException e) {
      System.err.println("Error preparando el hackeo: " + e.getMessage());
    }

    // 4. Lanzamos la medición que hará saltar las trampas
    try {
      System.out.println("-> Lanzando lectura final (Las alertas deberían saltar silenciosamente)...");
      estacion.lanzarMedicion(LocalDateTime.now());

    } catch (ConversionNoCompatibleException e) {
      System.err.println("Error de conversión: " + e.getMessage());
    }

    // 5. IMPRIMIMOS EL RESULTADO FINAL
    System.out.println("\n==============================================");
    System.out.println(" RESULTADO DE LA ESTACIÓN (SALIDA COMO PDF) ");
    System.out.println("==============================================\n");

    // Al imprimir la estación, el toString() que hicimos recorrerá los sensores
    // y luego imprimirá automáticamente el historial de alertas
    System.out.println(estacion);
  }
}
