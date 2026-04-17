package meteorologia.apartado3;

import meteorologia.procesamiento.*;
import meteorologia.sensores.*;
import meteorologia.estrategias.IEstrategia;
import meteorologia.excepciones.ConversionNoCompatibleException;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para validar los conversores de unidades
 * y el procesamiento del historial de datos.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: ProcesamientoTest.java
 */
public class ProcesamientoTest {

  /**
   * Verifica que un conversor simple realiza correctamente la transformación
   * matemática entre dos unidades conocidas.
   *
   * @throws ConversionNoCompatibleException Si hay error al configurar la
   *                                         conversión.
   */
  @Test
  public void testConversorTemperaturaSimple() throws ConversionNoCompatibleException {
    IConversor conversor = new ConversorTemperatura(UnidadTemperatura.CELSIUS, UnidadTemperatura.KELVIN);

    assertEquals(273.15, conversor.convertir(0.0), 0.01);
    assertEquals(373.15, conversor.convertir(100.0), 0.01);
  }

  /**
   * Verifica que la unión de múltiples conversores en cadena traslada el dato
   * final correctamente aplicando todas las operaciones secuenciales.
   *
   * @throws ConversionNoCompatibleException Si las unidades intermedias no son
   *                                         compatibles.
   */
  @Test
  public void testConversorConcatenado() throws ConversionNoCompatibleException {
    IConversor c1 = new ConversorTemperatura(UnidadTemperatura.CELSIUS, UnidadTemperatura.KELVIN);
    IConversor c2 = new ConversorTemperatura(UnidadTemperatura.KELVIN, UnidadTemperatura.FAHRENHEIT);

    IConversor concatenado = new ConversorConcatenado(c1, c2);

    assertEquals(32.0, concatenado.convertir(0.0), 0.01);
  }

  /**
   * Verifica que el sistema impide la creación de un conversor encadenado
   * si la unidad de destino del primero no coincide con el origen del segundo.
   */
  @Test
  public void testExcepcionConversorIncompatible() {
    IConversor c1 = new ConversorTemperatura(UnidadTemperatura.CELSIUS, UnidadTemperatura.KELVIN);
    IConversor c2 = new ConversorTemperatura(UnidadTemperatura.FAHRENHEIT, UnidadTemperatura.CELSIUS);

    try {
      new ConversorConcatenado(c1, c2);

      fail("Debería haber lanzado ConversionNoCompatibleException por no encajar las unidades");

    } catch (ConversionNoCompatibleException e) {
      assertNotNull(e.getMessage());
    }
  }

  /**
   * Verifica que el procesador de datos consolida adecuadamente una serie
   * de lecturas, calculando bien los mínimos, máximos y medias.
   */
  @Test
  public void testProcesadorDatosEstadisticas() {
    ProcesadorDatos procesador = new ProcesadorDatos(new ConversorIdentidad(UnidadHumedad.PORCENTAJE));
    LocalDateTime ahora = LocalDateTime.now();

    procesador.addLectura(40.0, ahora);
    procesador.addLectura(60.0, ahora.plusHours(1));
    procesador.addLectura(20.0, ahora.plusHours(2));

    assertEquals(20.0, procesador.getMinimoLeido(), 0.01);
    assertEquals(60.0, procesador.getMaximoLeido(), 0.01);
    assertEquals(40.0, procesador.getMedia(), 0.01);
  }

  /**
   * Verifica la integración completa entre un sensor, su estrategia de
   * generación y el conversor asociado al procesador de datos.
   *
   * @throws Exception Si ocurre un fallo no capturado durante el test.
   */
  @Test
  public void testIntegracionSensorConProcesador() throws Exception {
    IEstrategia estrategiaFija = new IEstrategia() {
      @Override
      public double generarValor() {
        return 20.0;
      }
    };

    SensorTemperatura sensor = new SensorTemperatura(estrategiaFija);

    IConversor conversor = new ConversorTemperatura(UnidadTemperatura.CELSIUS, UnidadTemperatura.KELVIN);
    sensor.cambiarConversor(conversor);

    sensor.medir(LocalDateTime.now());

    double mediaGuardada = sensor.getProcesadorDatos().getMedia();
    assertEquals(293.15, mediaGuardada, 0.01);
  }
}
