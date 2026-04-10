package meteorologia.formato;

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public interface IFormateador {
  public String formatear(IDocumento doc);

  public String getExtension();

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
