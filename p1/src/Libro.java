/**
 * Clase que representa un libro en el sistema de gestión de la biblioteca.
 * Mantiene información sobre el ISBN, título, autor, disponibilidad,
 * año de publicación y género literario.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: Libro.java
 */
public class Libro {
  private String isbn;
  private String titulo;
  private String autor;
  private Integer año;
  private String genero;
  private int ejemplaresDisponibles;

  /**
   * Constructor básico para crear un libro sin especificar año ni género.
   * Inicializa esos valores a null por defecto.
   *
   * @param isbn                  Identificador único del libro (ISBN).
   * @param titulo                Título de la obra.
   * @param autor                 Nombre del autor.
   * @param ejemplaresDisponibles Número inicial de copias físicas en la
   *                              biblioteca.
   */
  public Libro(String isbn, String titulo, String autor, int ejemplaresDisponibles) {
    this(isbn, titulo, autor, ejemplaresDisponibles, null, null);
  }

  /**
   * Constructor completo para crear un libro con toda su información detallada.
   *
   * @param isbn                  Identificador único del libro (ISBN).
   * @param titulo                Título de la obra.
   * @param autor                 Nombre del autor.
   * @param ejemplaresDisponibles Número inicial de copias físicas.
   * @param año                   Año de publicación de la obra.
   * @param genero                Género literario al que pertenece.
   */
  public Libro(String isbn, String titulo, String autor, int ejemplaresDisponibles, Integer año, String genero) {
    this.isbn = isbn;
    this.titulo = titulo;
    this.autor = autor;
    this.año = año;
    this.genero = genero;
    this.ejemplaresDisponibles = ejemplaresDisponibles;
  }

  /**
   * Obtiene el género literario del libro.
   *
   * @return El género del libro o null si no fue especificado.
   */
  public String getGenero() {
    return this.genero;
  }

  /**
   * Obtiene el año de publicación del libro.
   *
   * @return El año de publicación.
   */
  public int getAño() {
    return this.año;
  }

  /**
   * Obtiene el título del libro.
   *
   * @return El título de la obra.
   */
  public String getTitulo() {
    return this.titulo;
  }

  /**
   * Comprueba si existen ejemplares físicos disponibles para préstamo.
   *
   * @return true si hay al menos un ejemplar disponible, false en caso contrario.
   */
  public boolean estaDisponible() {
    return this.ejemplaresDisponibles > 0;
  }

  /**
   * Realiza el préstamo de un ejemplar del libro.
   * Decrementa el contador de ejemplares disponibles si es posible.
   *
   * @return true si el préstamo se realizó con éxito, false si no había
   *         ejemplares.
   */
  public boolean prestar() {
    if (estaDisponible()) {
      this.ejemplaresDisponibles--;
      return true;
    }
    return false;
  }

  /**
   * Registra la devolución de un ejemplar del libro.
   * Incrementa el contador de ejemplares disponibles.
   */
  public void devolver() {
    this.ejemplaresDisponibles++;
  }

  /**
   * Genera una descripción textual breve del estado del libro.
   * Método auxiliar interno.
   *
   * @return Cadena con título, autor y estado de disponibilidad.
   */
  private String descripcion() {
    String estado = this.estaDisponible() ? "Disponible" : "No disponible";
    return "'" + this.titulo + "' de " + this.autor + " [" + estado + "]";
  }

  /**
   * Devuelve una representación en cadena del objeto Libro.
   * Incluye ISBN, descripción, año, género y número de ejemplares.
   *
   * @return Cadena con la información completa del libro.
   */
  @Override
  public String toString() {
    return "ISBN: " + this.isbn + ". " + this.descripcion() + ", Año: " + this.año
        + ", Género: " + this.genero + " (" + this.ejemplaresDisponibles +
        " ejemplares disponibles)";
  }
}
