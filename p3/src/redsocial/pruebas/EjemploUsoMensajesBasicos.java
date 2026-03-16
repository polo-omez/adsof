package redsocial.pruebas;

import redsocial.*;

/**
 * Programa de prueba que ejemplifica el uso básico de las clases
 * Usuario, Enlace y Mensaje.
 * Simula la creación de usuarios y enlaces, y comprueba la difusión
 * de un mensaje a través de la red siguiendo las reglas de alcance
 * y amplificación establecidas.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: EjemploUsoMensajesBasicos.java
 */
public class EjemploUsoMensajesBasicos {

  /**
   * Método principal que ejecuta la simulación básica.
   * Crea una pequeña red de tres usuarios, establece conexiones entre ellos
   * y lanza un mensaje comprobando su estado y alcance tras cada salto.
   *
   * @param args Argumentos de la línea de comandos (no utilizados).
   */
  public static void main(String[] args) {
    // Se crean los usuarios con sus respectivas capacidades de amplificación
    Usuario ana = new Usuario("ana", 1); // capacidad de amplificación 1
    Usuario luis = new Usuario("luis", 5);
    Usuario carmen = new Usuario("carmen"); // por defecto capacidad 2

    // Se crea el mensaje inicial en el usuario @ana
    Mensaje m = new Mensaje("Hi!", 50, ana); // texto (Hi!), 50 unid. alcance inicial

    // Se añaden enlaces salientes desde @ana
    ana.addEnlace(new Enlace(ana, luis, 68));
    ana.addEnlace(carmen, 33);

    // Estado inicial del mensaje
    System.out.println(m);

    // Intento de difusión hacia @luis y luego hacia @carmen
    m.difunde(luis, carmen); // irá directamente a @carmen porque a @luis no le llega el alcance
    System.out.println(m); // alcance 19 = 50 - 33 + 2

    // Se añade un enlace desde @carmen hacia @luis
    carmen.addEnlace(new Enlace(carmen, luis, 11));

    // Difusión directa a través del enlace recién creado
    m.difunde(carmen.getEnlace(luis));
    System.out.println(m); // en @luis con alcance 13 = 19 - 11 + 5
  }
}
