package meteorologia.apartado4;

import meteorologia.alertas.*;
import meteorologia.sensores.*;
import meteorologia.estrategias.IEstrategia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias enfocada en el sistema de alertas meteorológicas,
 * comprobando comportamientos ante fallos operativos, de rango y variaciones
 * extremas.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: AlertasTest.java
 */
public class AlertasTest {

  private SensorTemperatura sensor;
  private LocalDateTime ahora;

  /**
   * Configuración previa a cada test. Inicializa un sensor con una estrategia
   * controlada para garantizar un comportamiento determinista.
   */
  @BeforeEach
  public void setUp() {
    IEstrategia estrategiaFija = new IEstrategia() {
      @Override
      public double generarValor() {
        return 25.0;
      }
    };
    sensor = new SensorTemperatura(estrategiaFija);
    ahora = LocalDateTime.now();
  }

  /**
   * Comprueba que un sensor rechaza cualquier lectura y emite la alerta correcta
   * si su periodo de validez por calibración ha expirado.
   */
  @Test
  public void testCalibracionCaducadaBloqueaSensor() {
    sensor.calibrar(0.0, -1);

    assertFalse(sensor.estaCalibrado(), "El sensor debería detectar que está caducado");

    try {
      sensor.medir(ahora);
      fail("Debería haber lanzado CalibracionCaducadaException");
    } catch (CalibracionCaducadaException e) {
      assertNotNull(e.getFechaCaducidad());
      assertEquals(sensor, e.getSensor());
    } catch (AlertaMeteorologicaException e) {
      fail("Lanzó una alerta incorrecta: " + e.getClass().getSimpleName());
    }
  }

  /**
   * Verifica que una lectura generada completamente fuera de los límites
   * de hardware definidos en el rango del sensor dispara una alerta crítica
   * y procede a detener su operatividad.
   */
  @Test
  public void testLecturaFueraDeRango() {
    sensor.calibrar(-1500.0);

    try {
      sensor.medir(ahora);
      fail("Debería haber lanzado LecturaFueraDeRangoException");
    } catch (LecturaFueraDeRangoException e) {
      assertTrue(e.getLecturaFallida() > 1000.0);
      assertFalse(sensor.estaCalibrado(), "El fallo fatal debe bloquear el sensor");
    } catch (AlertaMeteorologicaException e) {
      fail("Lanzó una alerta incorrecta");
    }
  }

  /**
   * Confirma que un pico severo de medición dispara una alerta de advertencia,
   * pero siendo válido, permite al sensor guardar el dato y continuar operando.
   */
  @Test
  public void testCambioBruscoNoBloqueaSensor() {
    try {
      sensor.medir(ahora);

      sensor.calibrar(-20.0);

      try {
        sensor.medir(ahora.plusHours(1));
        fail("Debería haber lanzado CambioBruscoException");
      } catch (CambioBruscoException e) {
        assertEquals(25.0, e.getValorAnterior(), 0.01);
        assertEquals(45.0, e.getValorNuevo(), 0.01);

        assertEquals(45.0, sensor.getUltimaLectura(), 0.01);
        assertTrue(sensor.estaCalibrado(), "El cambio brusco NO debe bloquear el sensor");
      }

    } catch (AlertaMeteorologicaException e) {
      fail("Error inesperado en la preparación del test: " + e.getMessage());
    }
  }
}
