package meteorologia.formato;

import java.util.List;
import java.util.Map;

public class FormateadorHTML implements IFormateador {

  @Override
  public String formatear(IDocumento doc) {
    StringBuilder html = new StringBuilder();
    html.append("<!DOCTYPE html>\n<html lang=\"es\">\n<head>\n<title>")
        .append(doc.getTituloDocumento())
        .append("</title>\n</head>\n<body>\n");

    html.append("<h1>").append(doc.getSeccionPricipalDocumento()).append("</h1>\n");

    for (String parrafo : doc.getParrafosDocumento()) {
      html.append("<p>").append(parrafo).append("</p>\n");
    }

    for (Map.Entry<String, List<String>> entrada : doc.getColeccionesDocumento().entrySet()) {
      html.append("<h3>").append(entrada.getKey()).append("</h3>\n");
      html.append("<ul>\n");
      for (String item : entrada.getValue()) {
        html.append("<li>").append(item).append("</li>\n");
      }
      html.append("</ul>\n");
    }

    html.append("</body>\n</html>");
    return html.toString();
  }

  @Override
  public String getExtension() {
    return ".html";
  }

}
