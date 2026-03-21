package meteorologia.sensores;

public enum UnidadHumedad implements IUnidad {
  PORCENTAJE("%");

  private String simbolo;

  private UnidadHumedad(String simbolo) {
    this.simbolo = simbolo;
  }

  @Override
  public String getSimbolo() {
    return this.simbolo;

  }
}
