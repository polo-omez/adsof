package meteorologia.pruebas;

import meteorologia.EstacionMeteorologica;
import meteorologia.TipoSensor;
import meteorologia.sensores.*;
import meteorologia.procesamiento.*;
import meteorologia.formato.*;
import java.time.LocalDateTime;

/**
 * Probador final que integra todos los apartados de la práctica.
 * Genera la salida por consola y los archivos de informe.
 * * @author Pablo Gómez
 * 
 * @author Jose Antonio Gómez
 */
public class PruebaFormato {

  public static void main(String[] args) {
    System.out.println("=== SISTEMA METEOROLÓGICO: EJECUCIÓN INTEGRAL ===");

    // 1. Inicialización de la Estación
    EstacionMeteorologica madrid = new EstacionMeteorologica("Madrid Centro", 40.4168, -3.7038);

    try {
      // 2. Creación y configuración de sensores
      String t1 = madrid.crearSensor(TipoSensor.TEMPERATURA, null);
      String t2 = madrid.crearSensor(TipoSensor.TEMPERATURA, null);
      String p1 = madrid.crearSensor(TipoSensor.PRESION_ATMOSFERICA, null);
      String h1 = madrid.crearSensor(TipoSensor.HUMEDAD, null);

      // 3. Asociación de conversores (Apartado 3)
      // t1 medirá en Kelvin
      madrid.asociarConversor(t1, new ConversorTemperatura(UnidadTemperatura.CELSIUS, UnidadTemperatura.KELVIN));
      // p1 medirá en Pascales
      madrid.asociarConversor(p1, new ConversorPresion(UnidadPresion.HECTOPASCALES, UnidadPresion.PASCALES));

      // 4. Simulación de funcionamiento normal (Histórico)
      System.out.println("-> Generando historial de lecturas normales...");
      madrid.medicionPeriodica(1.0, 3); // 3 lecturas separadas por 1 hora

      // 5. Provocar Alertas (Apartado 4)
      System.out.println("-> Provocando anomalías para el sistema de alertas...");

      // Alerta 1: Cambio brusco en T2 (le bajamos mucho el offset)
      madrid.calibrarSensor(t2, -25.0);

      // Alerta 2: Caducidad en P1 (calibración de hace tiempo)
      madrid.calibrarSensor(p1, 0.0, -10);

      // Alerta 3: Fuera de rango en H1 (offset absurdo)
      madrid.calibrarSensor(h1, -500.0);

      // Ejecutamos la medición que captura los errores
      madrid.lanzarMedicion(LocalDateTime.now());

      // 6. Visualización y Exportación (Apartado 5)
      System.out.println("\n--- INFORME FINAL (CONSOLA) ---");
      System.out.println(madrid);

      System.out.println("\n--- GENERANDO ARCHIVOS EN meteorologia/informes/ ---");

      IFormateador formateadorHTML = new FormateadorHTML();
      formateadorHTML.exportar(madrid);

      IFormateador formateadorMD = new FormateadorMarkdown();
      formateadorMD.exportar(madrid);

    } catch (Exception e) {
      System.err.println("Error inesperado durante la prueba: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
