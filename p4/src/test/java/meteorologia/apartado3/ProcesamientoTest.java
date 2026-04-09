package meteorologia.apartado3;

import meteorologia.procesamiento.*;
import meteorologia.sensores.*;
import meteorologia.estrategias.IEstrategia;
import meteorologia.excepciones.ConversionNoCompatibleException;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class ProcesamientoTest {

  @Test
  public void testConversorTemperaturaSimple() throws ConversionNoCompatibleException {
    IConversor conversor = new ConversorTemperatura(UnidadTemperatura.CELSIUS, UnidadTemperatura.KELVIN);

    assertEquals(273.15, conversor.convertir(0.0), 0.01);
    assertEquals(373.15, conversor.convertir(100.0), 0.01);
  }

  @Test
  public void testConversorConcatenado() throws ConversionNoCompatibleException {
    IConversor c1 = new ConversorTemperatura(UnidadTemperatura.CELSIUS, UnidadTemperatura.KELVIN);
    IConversor c2 = new ConversorTemperatura(UnidadTemperatura.KELVIN, UnidadTemperatura.FAHRENHEIT);

    IConversor concatenado = new ConversorConcatenado(c1, c2);

    assertEquals(32.0, concatenado.convertir(0.0), 0.01);
  }

  @Test
  public void testExcepcionConversorIncompatible() {
    IConversor c1 = new ConversorTemperatura(UnidadTemperatura.CELSIUS, UnidadTemperatura.KELVIN);
    IConversor c2 = new ConversorTemperatura(UnidadTemperatura.FAHRENHEIT, UnidadTemperatura.CELSIUS);

    // SIN LAMBDAS: Usamos el patrón clásico try-catch
    try {
      // Intentamos hacer algo que sabemos que debe fallar
      new ConversorConcatenado(c1, c2);

      // Si el código llega a esta línea, es que NO ha saltado la excepción, por tanto
      // el test falla
      fail("Debería haber lanzado ConversionNoCompatibleException por no encajar las unidades");

    } catch (ConversionNoCompatibleException e) {
      // Si el código entra en este catch, es que la excepción saltó correctamente. El
      // test pasa.
      assertNotNull(e.getMessage());
    }
  }

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

  @Test
  public void testIntegracionSensorConProcesador() throws Exception {
    // SIN LAMBDAS: Creamos una clase anónima que implementa la interfaz al vuelo
    IEstrategia estrategiaFija = new IEstrategia() {
      @Override
      public double generarValor() {
        return 20.0; // Siempre devuelve 20.0 para hacer el test predecible
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
