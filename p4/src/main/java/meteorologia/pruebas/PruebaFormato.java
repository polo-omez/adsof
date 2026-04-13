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
 * @version 1.0
 *          Nombre del fichero: PruebaFormato.java
 */
public class PruebaFormato {

  /**
   * Método principal de ejecución de la prueba de formato y exportación.
   *
   * @param args Argumentos pasados por línea de comandos.
   */
  public static void main(String[] args) {
    System.out.println("PRUEBA GENERACION DE ARCHIVOS HTML Y MARKDOWN\n");

    EstacionMeteorologica madrid = new EstacionMeteorologica("Madrid Centro", 40.4168, -3.7038);

    try {
      String t1 = madrid.crearSensor(TipoSensor.TEMPERATURA, null);
      String t2 = madrid.crearSensor(TipoSensor.TEMPERATURA, null);
      String p1 = madrid.crearSensor(TipoSensor.PRESION_ATMOSFERICA, null);
      String h1 = madrid.crearSensor(TipoSensor.HUMEDAD, null);

      madrid.asociarConversor(t1, new ConversorTemperatura(UnidadTemperatura.CELSIUS, UnidadTemperatura.KELVIN));
      madrid.asociarConversor(p1, new ConversorPresion(UnidadPresion.HECTOPASCALES, UnidadPresion.PASCALES));

      madrid.medicionPeriodica(1.0, 3);

      madrid.calibrarSensor(t2, -25.0);
      madrid.calibrarSensor(p1, 0.0, -10);
      madrid.calibrarSensor(h1, -500.0);

      madrid.lanzarMedicion(LocalDateTime.now());

      System.out.println("\n--- INFORME FINAL ---");
      System.out.println(madrid);

      System.out.println("\n--- GENERANDO ARCHIVOS EN informes/ ---");

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
