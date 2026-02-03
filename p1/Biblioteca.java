import java.util.*;

public class Biblioteca {
  private String nombre;
  private List<Libro> libros;
  private HashMap<String, List<Libro>> librosPorIndice;

  public Biblioteca(String nombre) {
    this.nombre = nombre;
    this.libros = new ArrayList<>();
    this.librosPorIndice = new HashMap<>();
  }

  public void añadirLibro(Libro libro) {
    String genero = libro.getGenero();

    this.libros.add(libro);
    // Comprobamos si el libro tiene genero, y lo añadimos a la lista de libros y al
    // mapa de íncices
    if (genero != null) {
      this.librosPorIndice.putIfAbsent(genero, new ArrayList<>());
      this.librosPorIndice.get(genero).add(libro);
    }

  }

  public List<Libro> librosPorGenero(String genero) {

    // Devolvemos la lista de libros si ese genero exixte como clave en nuestro mapa
    if (this.librosPorIndice.containsKey(genero)) {
      return this.librosPorIndice.get(genero);
    }

    return null;
  }

  public List<Libro> librosPosterioresA(int año) {
    List<Libro> librosPosteriores = new ArrayList<>();
    for (Libro libro : this.libros) {
      if (libro.getAño() < año) {
        librosPosteriores.add(libro);
      }
    }

    return librosPosteriores;

  }

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
