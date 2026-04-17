package meteorologia.apartado5;

import meteorologia.EstacionMeteorologica;
import meteorologia.TipoSensor;
import meteorologia.formato.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias diseñada para verificar la correcta conversión
 * de los datos de la estación a diferentes formatos de marcado y la posterior
 * generación de sus archivos físicos.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: FormatoTest.java
 */
public class FormatoTest {

  private EstacionMeteorologica estacion;
  private String idSensorReal;

  /**
   * Prepara la estación con un sensor básico y recupera dinámicamente su
   * identificador para validar correctamente las aserciones de formato.
   */
  @BeforeEach
  public void setUp() {
    estacion = new EstacionMeteorologica("Estacion Test", 40.0, -3.0);
    try {
      estacion.crearSensor(TipoSensor.TEMPERATURA, null);

      idSensorReal = estacion.getSensoresMap().keySet().iterator().next();

    } catch (Exception e) {
      fail("Fallo al preparar el entorno de pruebas");
    }
  }

  /**
   * Comprueba que la exportación hacia HTML construye la sintaxis base correcta,
   * incluyendo etiquetas principales y el identificador dinámico.
   */
  @Test
  public void testGeneracionHTML() {
    IFormateador html = new FormateadorHTML();
    String resultado = html.formatear(estacion);

    assertTrue(resultado.contains("<!DOCTYPE html>"), "Falta el DOCTYPE");
    assertTrue(resultado.contains("<h1>Estacion Test</h1>"), "Falta el H1 con el nombre");

    assertTrue(resultado.contains("<li>" + idSensorReal), "No se encontró el ID " + idSensorReal + " en el HTML");
  }

  /**
   * Comprueba que la estructura generada para Markdown respeta los caracteres
   * de jerarquía propios del lenguaje y los datos del documento.
   */
  @Test
  public void testGeneracionMarkdown() {
    IFormateador md = new FormateadorMarkdown();
    String resultado = md.formatear(estacion);

    assertTrue(resultado.contains("Estacion Test"), "Falta el nombre de la estación");
    assertTrue(resultado.contains("## Estacion Test"), "Falta el subtítulo H2");

    assertTrue(resultado.contains("- " + idSensorReal), "No se encontró el ID " + idSensorReal + " en el Markdown");
  }

  /**
   * Verifica la capacidad del formateador base para crear efectivamente
   * el fichero físico en el sistema de archivos del sistema operativo,
   * procediendo después a su correspondiente limpieza para no alterar el entorno.
   */
  @Test
  public void testExportarCreaFicheroFisico() {
    IFormateador html = new FormateadorHTML();

    String nombreFichero = "Estacion_Test.html";
    File ficheroEsperado = new File("informes", nombreFichero);

    try {
      if (ficheroEsperado.exists()) {
        ficheroEsperado.delete();
      }

      html.exportar(estacion);

      System.out.println("DEBUG RUTA TEST: Buscando el fichero en -> " + ficheroEsperado.getAbsolutePath());

      assertTrue(ficheroEsperado.exists(),
          "ERROR: El fichero no se creó en la ruta esperada: " + ficheroEsperado.getAbsolutePath());

      assertTrue(ficheroEsperado.length() > 0, "El fichero se creó pero está vacío");

    } catch (Exception e) {
      fail("La exportación lanzó una excepción: " + e.getMessage());
    } finally {
      if (ficheroEsperado.exists()) {
        ficheroEsperado.delete();
      }
    }
  }
}
