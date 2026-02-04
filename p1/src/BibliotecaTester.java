import java.util.*;

public class BibliotecaTester {
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
