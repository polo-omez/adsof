package meteorologia.apartado1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import meteorologia.EstacionMeteorologica;
import meteorologia.sensores.*;
import meteorologia.excepciones.*;

public class EstacionMeteorologicaTest {

  private EstacionMeteorologica estacion;

  @BeforeEach
  void setUp() {
    estacion = new EstacionMeteorologica("Estación Central Madrid", 40.41, -3.70);
  }

  // TEST 1: Comprobar que se añade un sensor y se puede recuperar
  @Test
  void testAñadirYRecuperarSensor() throws SensorDuplicadoException, SensorNoEncontradoException {
    SensorTemperatura t1 = new SensorTemperatura();

    estacion.addSensor(t1);

    ISensor recuperado = estacion.getSensor(t1.getIdentificador());
    assertNotNull(recuperado, "El sensor recuperado no debe ser nulo");
    assertEquals(t1, recuperado, "El sensor recuperado debe ser exactamente el mismo objeto");
  }

  // TEST 2: Comprobar la Excepción de Sensor Duplicado
  @Test
  void testLanzaExcepcionSiSensorDuplicado() {
    SensorTemperatura t1 = new SensorTemperatura();

    assertThrows(SensorDuplicadoException.class, () -> {
      estacion.addSensor(t1);
      estacion.addSensor(t1);
    }, "Debe lanzar SensorDuplicadoException");
  }

  // TEST 3: Comprobar el filtrado de sensores por tipo
  @Test
  void testObtenerSensoresPorTipo() throws SensorDuplicadoException {
    // Añadimos 2 de temperatura y 1 de humedad
    estacion.addSensor(new SensorTemperatura());
    estacion.addSensor(new SensorTemperatura());
    estacion.addSensor(new SensorHumedad());

    // Le pedimos a la estación solo los de temperatura
    List<ISensor> listaTemperaturas = estacion.getSensores(SensorTemperatura.class);
    List<ISensor> listaHumedad = estacion.getSensores(SensorHumedad.class);

    // Verificamos que los tamaños de las listas son correctos
    assertEquals(2, listaTemperaturas.size(), "Debe haber exactamente 2 sensores de temperatura");
    assertEquals(1, listaHumedad.size(), "Debe haber exactanebte 1 sensor de humedad");
  }

  // TEST 4: Comprobar la autogeneración de IDs estáticos
  @Test
  void testGeneracionIDsUnicos() {
    SensorPresionAtmosferica p1 = new SensorPresionAtmosferica();
    SensorPresionAtmosferica p2 = new SensorPresionAtmosferica();

    // Comprobamos que no son nulos
    assertNotNull(p1.getIdentificador());
    assertNotNull(p2.getIdentificador());

    // Comprobamos que empiezan por su prefijo correcto
    assertTrue(p1.getIdentificador().startsWith("PRES-"), "El ID debe empezar por PRES-");

    // Comprobamos que los IDs son diferentes entre sí gracias al contador estático
    assertNotEquals(p1.getIdentificador(), p2.getIdentificador(),
        "Los IDs deben ser distintos");
  }
}
