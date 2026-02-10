package trayectos;

public class Main {
  public static void main(String[] args) {
    /*
     * * NOTA: Para que este código funcione, debes implementar:
     * 1. La clase TramoAPie
     * 2. El enumerado Ritmo (o constantes estáticas en TramoAPie, según diseñes)
     */

    TramoTrayecto[] trayecto = {
        new TramoAPie("Hotel Puerta del Sol", "Sol Renfe", 1),
        new TramoTren("Sol Renfe", "Cantoblanco Renfe", Linea.C4, 4),
        new TramoAPie("Cantoblanco Renfe", "EPS", 2.6, Ritmo.RAPIDO)
    };

    for (TramoTrayecto tramo : trayecto) {
      System.out.println(tramo);
    }
  }
}
