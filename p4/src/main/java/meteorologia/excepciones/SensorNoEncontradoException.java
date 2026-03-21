package meteorologia.excepciones;

public class SensorNoEncontradoException extends Exception {
  public SensorNoEncontradoException(String id) {
    super("Error: No se ha encontrado ningun sensor con ID " + id + " en el sistema");

  }
}
