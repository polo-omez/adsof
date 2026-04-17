package meteorologia.formato;

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Interfaz que define las operaciones para dar formato y exportar documentos.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: IFormateador.java
 */
public interface IFormateador {
  /**
   * Aplica un formato específico a un documento dado.
   *
   * @param doc El documento a formatear.
   * @return El texto del documento ya formateado.
   */
  public String formatear(IDocumento doc);

  /**
   * Obtiene la extensión del archivo asociada al formato.
   *
   * @return La extensión (por ejemplo, ".html").
   */
  public String getExtension();

  /**
   * Exporta el documento formateado a un archivo en disco.
   *
   * @param doc El documento a exportar.
   * @throws IOException Si ocurre un error de escritura.
   */
  default void exportar(IDocumento doc) throws IOException {
    File directorio = new File("informes");
    if (!directorio.exists()) {
      directorio.mkdirs();
    }

    String nombreFichero = doc.getTituloDocumento().replaceAll("\\s+", "_") + this.getExtension();
    File fichero = new File(directorio, nombreFichero);

    try (PrintWriter writer = new PrintWriter(new FileWriter(fichero))) {
      writer.print(this.formatear(doc));
    }
  }
}
