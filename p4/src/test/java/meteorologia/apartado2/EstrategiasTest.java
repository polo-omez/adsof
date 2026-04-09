package meteorologia.apartado2;

import meteorologia.estrategias.*;
import meteorologia.sensores.Rango;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EstrategiasTest {

  @Test
  public void testEstrategiaEnRangoSinDesborde() {
    // Rango de 10 a 20, con 0% de probabilidad de salirse del rango
    Rango rango = new Rango(10, 20);
    IEstrategia estrategia = new EstrategiaGeneracionEnRango(rango, 0.0);

    // Simulamos 100 lecturas para asegurar estadísticamente que nunca se sale
    for (int i = 0; i < 100; i++) {
      double valor = estrategia.generarValor();
      assertTrue(valor >= 10.0 && valor <= 20.0,
          "El valor " + valor + " debería estar entre 10 y 20");
    }
  }

  @Test
  public void testEstrategiaSimilar() {
    // Empieza en 100, y puede variar un 10% (es decir, entre 90 y 110 en el primer
    // salto)
    IEstrategia estrategia = new EstrategiaGeneracionSimilar(10, 100.0);

    double valor1 = estrategia.generarValor();
    assertTrue(valor1 >= 90.0 && valor1 <= 110.0, "El primer salto debe estar entre 90 y 110");

    // El segundo salto debe basarse en el valor1, no en 100.
    double valor2 = estrategia.generarValor();
    double margen = valor1 * 0.10;
    assertTrue(valor2 >= (valor1 - margen) && valor2 <= (valor1 + margen),
        "El segundo salto debe pivotar sobre el valor anterior");
  }

  @Test
  public void testEstrategiaMedia() {
    // Empieza en 50, con variación del 20%
    IEstrategia estrategia = new EstrategiaGeneracionEnMedia(20, 50.0);

    // La primera media es 50, así que debe generar entre 40 y 60
    double valor = estrategia.generarValor();
    assertTrue(valor >= 40.0 && valor <= 60.0);
  }
}
