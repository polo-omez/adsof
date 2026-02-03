public class Libro {
  private String isbn;
  private String titulo;
  private String autor;
  private int año;
  private String genero;
  private int ejemplaresDisponibles;

  public Libro(String isbn, String titulo, String autor, int ejemplaresDisponibles, int año, String genero) {
    this.isbn = isbn;
    this.titulo = titulo;
    this.autor = autor;
    this.año = año;
    this.genero = genero;
    this.ejemplaresDisponibles = ejemplaresDisponibles;
  }

  public Libro(String isbn, String titulo, String autor, int ejemplaresDisponibles) {
    this.Libro(isbn, titulo, autor, ejemplaresDisponibles, null, null);
  }

  // Getter de Genero
  public String getGenero() {
    return this.genero;
  }

  // Getter de año
  public int getAño() {
    return this.año;
  }

  // Getter de titulo
  public String getTitulo() {
    return this.titulo;
  }

  // Método para verificar si el libro está disponible
  public boolean estaDisponible() {
    return this.ejemplaresDisponibles > 0;
  }

  // Método para prestar el libro
  public boolean prestar() {
    if (estaDisponible()) {
      this.ejemplaresDisponibles--;
      return true;
    }
    return false;
  }

  // Método para devolver el libro
  public void devolver() {
    this.ejemplaresDisponibles++;
  }

  // Método para obtener la descripción del libro
  private String descripcion() {
    String estado = this.estaDisponible() ? "Disponible" : "No disponible";
    return "'" + this.titulo + "' de " + this.autor + " [" + estado + "]";
  }

  @Override
  public String toString() {
    return "ISBN: " + this.isbn + ". " + this.descripcion() + "Año: " + this.año + "Género: " + this.genero + " ("
        + this.ejemplaresDisponibles +
        " ejemplares disponibles)";
  }
}
