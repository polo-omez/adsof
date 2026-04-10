package meteorologia.formato;

import java.util.List;
import java.util.Map;

public interface IDocumento {
  public String getTituloDocumento();

  public String getSeccionPricipalDocumento();

  public List<String> getParrafosDocumento();

  public Map<String, List<String>> getColeccionesDocumento();
}
