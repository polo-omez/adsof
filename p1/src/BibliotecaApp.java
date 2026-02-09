import java.util.*;

/**
 * Clase principal de la aplicación para el Apartado 3.
 * Demuestra el funcionamiento básico de la clase Libro (creación, préstamo y
 * devolución).
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: BibliotecaApp.java
 */
public class BibliotecaApp {

  /**
   * Punto de entrada del programa.
   * Realiza una demostración de la gestión básica de libros en una lista.
   *
   * @param args Argumentos de línea de comandos (no utilizados).
   */
  public static void main(String[] args) {
    List<Libro> libros = new ArrayList<>(List.of(
        new Libro("1", "El Quijote", "Miguel de Cervantes", 5), // ISBN, titulo, autor, #ejemplares
        new Libro("2", "El murciélago", "Jo Nesbo", 1),
        new Libro("3", "Learn Java", "David Hoffman", 6)));

    libros.get(1).prestar();
    for (Libro l : libros)
      System.out.println(l);

    libros.get(1).devolver();
    System.out.println(libros);

    libros.add(new Libro("4", "Con viento solano", "Ignacio Aldecoa", 1));
    System.out.println(libros);
  }
}
