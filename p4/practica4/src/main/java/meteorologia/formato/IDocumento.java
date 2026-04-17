package meteorologia.formato;

import java.util.List;
import java.util.Map;

/**
 * Interfaz que define la estructura de un documento exportable.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: IDocumento.java
 */
public interface IDocumento {
  /**
   * Obtiene el título principal del documento.
   *
   * @return El título en formato cadena.
   */
  public String getTituloDocumento();

  /**
   * Obtiene el nombre de la sección principal del documento.
   *
   * @return La sección principal en formato cadena.
   */
  public String getSeccionPricipalDocumento();

  /**
   * Obtiene una lista de los párrafos que componen el documento.
   *
   * @return Una lista de cadenas con los párrafos.
   */
  public List<String> getParrafosDocumento();

  /**
   * Obtiene un mapa con colecciones de datos del documento.
   *
   * @return Mapa asociando títulos de colecciones a listas de elementos.
   */
  public Map<String, List<String>> getColeccionesDocumento();
}
