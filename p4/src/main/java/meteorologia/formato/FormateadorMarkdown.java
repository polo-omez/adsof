package meteorologia.formato;

import java.util.List;
import java.util.Map;

public class FormateadorMarkdown implements IFormateador {

  @Override
  public String getExtension() {
    return ".md";
  }

  @Override
  public String formatear(IDocumento doc) {
    StringBuilder md = new StringBuilder();

    md.append("# ").append(doc.getTituloDocumento()).append("\n\n");
    md.append("## ").append(doc.getSeccionPricipalDocumento()).append("\n\n");

    for (String parrafo : doc.getParrafosDocumento()) {
      md.append(parrafo).append("\n\n");
    }

    for (Map.Entry<String, List<String>> entrada : doc.getColeccionesDocumento().entrySet()) {
      md.append("### ").append(entrada.getKey()).append("\n");
      for (String item : entrada.getValue()) {
        md.append("- ").append(item).append("\n");
      }
      md.append("\n");
    }

    return md.toString();
  }
}
