package meteorologia.sensores;

public enum UnidadTemperatura implements IUnidad {
  CELSIUS("ºC"),
  FAHRENHEIT("ºF"),
  KELVIN("ºK");

  private String simbolo;

  private UnidadTemperatura(String simbolo) {
    this.simbolo = simbolo;
  }

  @Override
  public String getSimbolo() {
    return this.simbolo;

  }
}
