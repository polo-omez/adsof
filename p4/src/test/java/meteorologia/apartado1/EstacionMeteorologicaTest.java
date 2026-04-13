package meteorologia.apartado1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import meteorologia.EstacionMeteorologica;
import meteorologia.sensores.*;
import meteorologia.excepciones.*;

/**
 * Clase de pruebas unitarias para validar el comportamiento básico de la
 * Estación Meteorológica y la gestión de sus sensores.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: EstacionMeteorologicaTest.java
 */
public class EstacionMeteorologicaTest {

  private EstacionMeteorologica estacion;

  /**
   * Configuración inicial antes de cada test. Inicializa una estación base.
   */
  @BeforeEach
  void setUp() {
    estacion = new EstacionMeteorologica("Estación Central Madrid", 40.41, -3.70);
  }

  /**
   * Verifica que es posible añadir un sensor a la estación y posteriormente
   * recuperarlo correctamente utilizando su identificador.
   *
   * @throws SensorDuplicadoException    Si ocurre un error de duplicidad
   *                                     inesperado.
   * @throws SensorNoEncontradoException Si el sensor no se encuentra al intentar
   *                                     recuperarlo.
   */
  @Test
  void testAñadirYRecuperarSensor() throws SensorDuplicadoException, SensorNoEncontradoException {
    SensorTemperatura t1 = new SensorTemperatura();

    estacion.addSensor(t1);

    ISensor recuperado = estacion.getSensor(t1.getIdentificador());
    assertNotNull(recuperado, "El sensor recuperado no debe ser nulo");
    assertEquals(t1, recuperado, "El sensor recuperado debe ser exactamente el mismo objeto");
  }

  /**
   * Verifica que el sistema lanza correctamente la excepción correspondiente
   * cuando se intenta añadir un sensor que ya existe en la estación.
   */
  @Test
  void testLanzaExcepcionSiSensorDuplicado() {
    SensorTemperatura t1 = new SensorTemperatura();

    assertThrows(SensorDuplicadoException.class, () -> {
      estacion.addSensor(t1);
      estacion.addSensor(t1);
    }, "Debe lanzar SensorDuplicadoException");
  }

  /**
   * Verifica que el método de filtrado devuelve las colecciones correctas
   * según el tipo de clase solicitada.
   *
   * @throws SensorDuplicadoException Si ocurre un error al registrar los sensores
   *                                  de prueba.
   */
  @Test
  void testObtenerSensoresPorTipo() throws SensorDuplicadoException {
    estacion.addSensor(new SensorTemperatura());
    estacion.addSensor(new SensorTemperatura());
    estacion.addSensor(new SensorHumedad());

    List<ISensor> listaTemperaturas = estacion.getSensores(SensorTemperatura.class);
    List<ISensor> listaHumedad = estacion.getSensores(SensorHumedad.class);

    assertEquals(2, listaTemperaturas.size(), "Debe haber exactamente 2 sensores de temperatura");
    assertEquals(1, listaHumedad.size(), "Debe haber exactanebte 1 sensor de humedad");
  }

  /**
   * Verifica que la generación de identificadores estáticos de los sensores
   * funciona correctamente, asignando el prefijo adecuado y garantizando que
   * son únicos.
   */
  @Test
  void testGeneracionIDsUnicos() {
    SensorPresionAtmosferica p1 = new SensorPresionAtmosferica();
    SensorPresionAtmosferica p2 = new SensorPresionAtmosferica();

    assertNotNull(p1.getIdentificador());
    assertNotNull(p2.getIdentificador());

    assertTrue(p1.getIdentificador().startsWith("PRES-"), "El ID debe empezar por PRES-");

    assertNotEquals(p1.getIdentificador(), p2.getIdentificador(),
        "Los IDs deben ser distintos");
  }
}
