package meteorologia;

import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import meteorologia.sensores.ISensor;
import meteorologia.sensores.IUnidad;
import meteorologia.estrategias.IEstrategia;
import meteorologia.sensores.SensorMeteorologico;
import meteorologia.sensores.SensorTemperatura;
import meteorologia.procesamiento.IConversor;
import meteorologia.excepciones.*;

/**
 * Clase que representa y coordina una estación meteorológica y sus sensores.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: EstacionMeteorologica.java
 */
public class EstacionMeteorologica {
  /** Nombre identificativo de la estación. */
  private String nombre;
  /** Coordenadas geográficas donde se sitúa la estación. */
  private Ubicacion ubicacionGeografica;
  /** Mapa de sensores registrados en la estación indexados por su ID. */
  private Map<String, ISensor> sensores;

  /**
   * Construye una nueva estación meteorológica con su ubicación.
   *
   * @param nombre   El nombre identificativo de la estación.
   * @param latitud  La latitud geográfica de la estación.
   * @param longitud La longitud geográfica de la estación.
   */
  public EstacionMeteorologica(String nombre, double latitud, double longitud) {
    this.nombre = nombre;
    this.ubicacionGeografica = new Ubicacion(latitud, longitud);
    this.sensores = new HashMap<>();
  }

  /**
   * Obtiene el nombre de la estación.
   *
   * @return El nombre identificativo de la estación.
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * Obtiene el mapa completo de los sensores registrados.
   *
   * @return Un mapa con los identificadores como clave y los sensores como valor.
   */
  public Map<String, ISensor> getSensoresMap() {
    return this.sensores;
  }

  public void crearSensor(TipoSensor tipo, IEstrategia estrategiaGeneracion)
      throws SensorDuplicadoException {
    ISensor nuevoSensor;
    nuevoSensor = tipo.crearSensor();
    if (estrategiaGeneracion != null) {
      nuevoSensor.setEstrategiaGeneracion(estrategiaGeneracion);
    }
    this.addSensor(nuevoSensor);
  }

  public void asociarConversor(String sensorId, IUnidad unidadDestino)
      throws SensorNoEncontradoException, SensorConversorIncompatiblesException {
    return none;

  }

  /**
   * Registra un nuevo sensor en la estación asignándole la fecha actual de
   * instalación.
   *
   * @param sensor El objeto sensor a añadir.
   * @throws SensorDuplicadoException Si ya existe un sensor con el mismo
   *                                  identificador.
   */
  public void addSensor(ISensor sensor) throws SensorDuplicadoException {
    this.addSensor(sensor, LocalDate.now());
  }

  /**
   * Registra un nuevo sensor en la estación especificando su fecha de
   * instalación.
   *
   * @param sensor           El objeto sensor a añadir.
   * @param fechaInstalacion La fecha en la que se instaló el sensor.
   * @throws SensorDuplicadoException Si ya existe un sensor con el mismo
   *                                  identificador.
   */
  public void addSensor(ISensor sensor, LocalDate fechaInstalacion) throws SensorDuplicadoException {
    String sensorId = sensor.getIdentificador();

    if (sensores.containsKey(sensorId)) {
      throw new SensorDuplicadoException(sensores.get(sensorId), sensor);
    }
    sensores.put(sensor.getIdentificador(), sensor);
    sensor.setFechaInstalacion(fechaInstalacion);
  }

  /**
   * Recupera un sensor registrado mediante su identificador único.
   *
   * @param sensorId El identificador del sensor buscado.
   * @return El sensor correspondiente al identificador.
   * @throws SensorNoEncontradoException Si no existe ningún sensor con ese
   *                                     identificador.
   */
  public ISensor getSensor(String sensorId) throws SensorNoEncontradoException {
    if (!sensores.containsKey(sensorId)) {
      throw new SensorNoEncontradoException(sensorId);
    }
    return sensores.get(sensorId);
  }

  /**
   * Obtiene una lista con todos los sensores de un tipo específico.
   *
   * @param tipo La clase del tipo de sensor deseado.
   * @return Una lista de sensores que son instancia de la clase indicada.
   */
  public List<ISensor> getSensores(Class<?> tipo) {
    List<ISensor> sensoresTipo = new ArrayList<>();

    for (ISensor sensor : sensores.values()) {
      if (tipo.isInstance(sensor))
        sensoresTipo.add(sensor);
    }

    return sensoresTipo;
  }

  /**
   * Obliga a todos los sensores registrados a realizar una medición puntual.
   *
   * @param fechaMedicion La fecha y hora exacta de la medición.
   */
  public void lanzarMedicion(LocalDateTime fechaMedicion) {
    for (ISensor sensor : this.sensores.values()) {
      sensor.medir(fechaMedicion);
    }
  }

  /**
   * Simula una serie de mediciones periódicas a partir de la fecha actual.
   *
   * @param horasIntervalo  Las horas de separación entre cada medición simulada.
   * @param lecturasMaximas El número total de mediciones que se realizarán.
   */
  public void medicionPeriodica(double horasIntervalo, int lecturasMaximas) {
    LocalDateTime fechaMedicion = LocalDateTime.now();

    for (int i = 0; i < lecturasMaximas; i++) {
      this.lanzarMedicion(fechaMedicion);
      fechaMedicion = fechaMedicion.plusSeconds((int) (horasIntervalo * 3600));
    }
  }

  /**
   * Devuelve una representación en cadena de la estación y sus sensores.
   *
   * @return Cadena con el formato detallado de la estación y la lista de
   *         sensores.
   */
  @Override
  public String toString() {
    StringJoiner sensoresList = new StringJoiner(",\n", "[", "]");
    for (ISensor sensor : this.sensores.values()) {
      sensoresList
          .add(sensor.getIdentificador() + " (desde: " + sensor.getFechaInstalacion() + ") " + sensor.toString());
    }

    return "Estacion Meteorologica: " + this.nombre + "\nUbicacion: " + this.ubicacionGeografica.toString()
        + "\nSensores instalados:\n" + sensoresList.toString();
  }
}
