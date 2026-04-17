package meteorologia.apartado2;

import meteorologia.estrategias.*;
import meteorologia.sensores.Rango;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para verificar la correcta generación de valores
 * simulados según las distintas estrategias implementadas.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: EstrategiasTest.java
 */
public class EstrategiasTest {

  /**
   * Verifica que la estrategia basada en rango no genera valores fuera de los
   * límites cuando la probabilidad de desborde se establece a cero.
   */
  @Test
  public void testEstrategiaEnRangoSinDesborde() {
    Rango rango = new Rango(10, 20);
    IEstrategia estrategia = new EstrategiaGeneracionEnRango(rango, 0.0);

    for (int i = 0; i < 100; i++) {
      double valor = estrategia.generarValor();
      assertTrue(valor >= 10.0 && valor <= 20.0,
          "El valor " + valor + " debería estar entre 10 y 20");
    }
  }

  /**
   * Verifica que la estrategia que simula variaciones similares calcula
   * correctamente el margen de fluctuación pivotando sobre el valor anterior.
   */
  @Test
  public void testEstrategiaSimilar() {
    IEstrategia estrategia = new EstrategiaGeneracionSimilar(10, 100.0);

    double valor1 = estrategia.generarValor();
    assertTrue(valor1 >= 90.0 && valor1 <= 110.0, "El primer salto debe estar entre 90 y 110");

    double valor2 = estrategia.generarValor();
    double margen = valor1 * 0.10;
    assertTrue(valor2 >= (valor1 - margen) && valor2 <= (valor1 + margen),
        "El segundo salto debe pivotar sobre el valor anterior");
  }

  /**
   * Verifica que la estrategia basada en media histórica calcula adecuadamente
   * el rango de fluctuación inicial.
   */
  @Test
  public void testEstrategiaMedia() {
    IEstrategia estrategia = new EstrategiaGeneracionEnMedia(20, 50.0);

    double valor = estrategia.generarValor();
    assertTrue(valor >= 40.0 && valor <= 60.0);
  }
}
