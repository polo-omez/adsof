import java.util.*;

/**
 * Clase que representa una Biblioteca.
 * Gestiona una colección de libros y permite realizar búsquedas filtradas
 * por género y año de publicación de manera eficiente.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: Biblioteca.java
 */
public class Biblioteca {
  private String nombre;
  /** Lista maestra con todos los libros registrados */
  private List<Libro> libros;
  /** Mapa auxiliar para búsquedas eficientes por género (Índice invertido) */
  private HashMap<String, List<Libro>> librosPorIndice;

  /**
   * Crea una nueva biblioteca vacía.
   *
   * @param nombre Nombre identificativo de la biblioteca.
   */
  public Biblioteca(String nombre) {
    this.nombre = nombre;
    this.libros = new ArrayList<>();
    this.librosPorIndice = new HashMap<>();
  }

  /**
   * Añade un libro al catálogo de la biblioteca.
   * Actualiza tanto la lista general como el índice de géneros para mantener
   * la eficiencia en las búsquedas.
   *
   * @param libro El objeto Libro que se desea añadir.
   */
  public void añadirLibro(Libro libro) {
    String genero = libro.getGenero();

    this.libros.add(libro);
    // Comprobamos si el libro tiene genero, y lo añadimos a la lista de libros y al
    // mapa de índices
    if (genero != null) {
      this.librosPorIndice.putIfAbsent(genero, new ArrayList<>());
      this.librosPorIndice.get(genero).add(libro);
    }
  }

  /**
   * Obtiene la lista de libros pertenecientes a un género específico.
   * Utiliza un mapa hash para obtener los resultados con eficiencia O(1).
   *
   * @param genero El género literario a buscar.
   * @return Lista de libros de ese género, o null si el género no existe en el
   *         índice.
   */
  public List<Libro> librosPorGenero(String genero) {
    // Devolvemos la lista de libros si ese genero existe como clave en nuestro mapa
    if (this.librosPorIndice.containsKey(genero)) {
      return this.librosPorIndice.get(genero);
    }
    return null;
  }

  /**
   * Filtra los libros publicados después de un año determinado.
   *
   * @param año Año de corte para el filtro.
   * @return Lista de libros cuyo año de publicación es posterior al indicado.
   */
  public List<Libro> librosPosterioresA(int año) {
    List<Libro> librosPosteriores = new ArrayList<>();
    for (Libro libro : this.libros) {
      // OJO: En tu código original tienes '<', debería ser '>' para "Posteriores"
      if (libro.getAño() < año) {
        librosPosteriores.add(libro);
      }
    }
    return librosPosteriores;
  }

  /**
   * Devuelve una representación en cadena de la biblioteca y su contenido.
   * Muestra los libros agrupados por género según el índice interno.
   *
   * @return Cadena con el nombre de la biblioteca y el listado de libros.
   */
  @Override
  public String toString() {
    String string = "Nombre: " + this.nombre;

    for (String genero : this.librosPorIndice.keySet()) {
      string = string + "\nLibros de género " + genero + ": ";
      for (Libro libro : this.librosPorIndice.get(genero)) {
        string = string + "\n-" + libro.getTitulo();
      }
    }
    return string;
  }
}
