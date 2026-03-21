package meteorologia.sensores;

public enum UnidadPresion implements IUnidad {
  HECTOPASCALES("hPa"),
  PASCALES("Pa"),
  MILIBARES("mbar");

  private String simbolo;

  private UnidadPresion(String simbolo) {
    this.simbolo = simbolo;
  }

  @Override
  public String getSimbolo() {
    return this.simbolo;

  }
}
