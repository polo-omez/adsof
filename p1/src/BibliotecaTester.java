import java.util.*;

/**
 * Clase de prueba para la funcionalidad extendida de la Biblioteca (Apartado
 * 4).
 * Verifica la correcta adición de libros con género y año, y el funcionamiento
 * de los filtros de búsqueda.
 *
 * @author NombreAlumno1
 * @author NombreAlumno2
 * @version 1.0
 *          Nombre del fichero: BibliotecaTester.java
 */
public class BibliotecaTester {

  /**
   * Punto de entrada del programa de pruebas.
   * Crea una biblioteca, añade libros con metadatos extendidos y prueba los
   * métodos de filtrado.
   *
   * @param args Argumentos de línea de comandos.
   */
  public static void main(String[] args) {
    Biblioteca biblioteca = new Biblioteca("biblioteca1");
    List<Libro> libros = new ArrayList<>(List.of(
        new Libro("1", "El Quijote", "Miguel de Cervantes", 5, 1512, "aventuras"), // ISBN, titulo, autor, #ejemplares
        new Libro("2", "El murciélago", "Jo Nesbo", 1, 2008, "aventuras"),
        new Libro("3", "Learn Java", "David Hoffman", 6, 2001, "misterio")));

    for (Libro libro : libros) {
      biblioteca.añadirLibro(libro);
    }

    System.out.println(biblioteca);

    String genero = "aventuras";
    int año = 2000;

    System.out.println("Libros de " + genero + " : " + biblioteca.librosPorGenero(genero));
    System.out.println("Libros posteriores a " + año + " : " + biblioteca.librosPosterioresA(año));
  }
}
