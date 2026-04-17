package meteorologia;

import java.util.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.LocalDateTime;

import meteorologia.sensores.ISensor;
import meteorologia.alertas.*;
import meteorologia.estrategias.IEstrategia;
import meteorologia.procesamiento.IConversor;
import meteorologia.excepciones.*;
import meteorologia.formato.IDocumento;

/**
 * Clase que representa y coordina una estación meteorológica y sus sensores.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: EstacionMeteorologica.java
 */
public class EstacionMeteorologica implements IDocumento {
  /** Nombre identificativo de la estación. */
  private String nombre;
  /** Coordenadas geográficas donde se sitúa la estación. */
  private Ubicacion ubicacionGeografica;
  /** Mapa de sensores registrados en la estación indexados por su ID. */
  private Map<String, ISensor> sensores;
  /** Historial de alertas registradas por los sensores. */
  private List<RegistroAlerta> historialAlertas;
  /** Fecha y hora de la última lectura global realizada. */
  private LocalDateTime ultimaLectura;

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
    this.sensores = new LinkedHashMap<>();
    this.ultimaLectura = LocalDateTime.now();
    this.historialAlertas = new ArrayList<>();
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

  /**
   * Crea un nuevo sensor del tipo especificado y lo asocia a la estación.
   *
   * @param tipo                 El tipo de sensor a crear.
   * @param estrategiaGeneracion La estrategia de generación de datos a utilizar
   *                             (puede ser null).
   * @return El identificador único del sensor creado.
   * @throws SensorDuplicadoException Si ocurre un error de duplicidad al
   *                                  registrar el sensor.
   */
  public String crearSensor(TipoSensor tipo, IEstrategia estrategiaGeneracion)
      throws SensorDuplicadoException {
    ISensor nuevoSensor;
    nuevoSensor = tipo.crearSensor();
    if (estrategiaGeneracion != null) {
      nuevoSensor.setEstrategiaGeneracion(estrategiaGeneracion);
    }
    this.addSensor(nuevoSensor);
    return nuevoSensor.getIdentificador();
  }

  /**
   * Asocia un nuevo conversor de unidades a un sensor existente.
   *
   * @param sensorId       El identificador del sensor.
   * @param nuevoConversor El conversor a asociar.
   * @throws SensorNoEncontradoException     Si el sensor no existe en la
   *                                         estación.
   * @throws ConversionNoCompatibleException Si el conversor no es compatible con
   *                                         la unidad del sensor.
   */
  public void asociarConversor(String sensorId, IConversor nuevoConversor)
      throws SensorNoEncontradoException, ConversionNoCompatibleException {
    this.getSensor(sensorId).cambiarConversor(nuevoConversor);
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
   * Calibra un sensor específico y limpia su historial de alertas.
   *
   * @param sensorId     El identificador del sensor a calibrar.
   * @param offset       El valor de ajuste para la calibración.
   * @param diasDuracion La duración en días de la calibración.
   * @throws SensorNoEncontradoException Si el sensor indicado no existe.
   */
  public void calibrarSensor(String sensorId, double offset, int diasDuracion) throws SensorNoEncontradoException {
    ISensor sensor = this.getSensor(sensorId);
    sensor.calibrar(offset, diasDuracion);
    this.historialAlertas.removeIf(alerta -> alerta.getSensorInvolucrado().getIdentificador().equals(sensorId));
  }

  /**
   * Calibra un sensor específico con una duración por defecto de un año.
   *
   * @param sensorId El identificador del sensor a calibrar.
   * @param offset   El valor de ajuste para la calibración.
   * @throws SensorNoEncontradoException Si el sensor indicado no existe.
   */
  public void calibrarSensor(String sensorId, double offset) throws SensorNoEncontradoException {
    this.calibrarSensor(sensorId, offset, 365);
  }

  /**
   * Obliga a todos los sensores registrados a realizar una medición puntual.
   *
   * @param fechaMedicion La fecha y hora exacta de la medición.
   * @throws ConversionNoCompatibleException Si hay problemas de conversión de
   *                                         unidades durante la medición.
   */
  public void lanzarMedicion(LocalDateTime fechaMedicion) throws ConversionNoCompatibleException {
    for (ISensor sensor : this.sensores.values()) {
      try {
        sensor.medir(fechaMedicion);
      } catch (SensorSinCalibrarException | CambioBruscoException e) {
        this.historialAlertas.add(new RegistroAlerta(fechaMedicion, e));
      } catch (AlertaMeteorologicaException e) {
        this.historialAlertas.add(new RegistroAlerta(fechaMedicion, e));
      }
    }
  }

  /**
   * Simula una serie de mediciones periódicas a partir de la fecha actual.
   *
   * @param horasIntervalo  Las horas de separación entre cada medición simulada.
   * @param lecturasMaximas El número total de mediciones que se realizarán.
   * @throws ConversionNoCompatibleException Si hay un fallo de compatibilidad de
   *                                         conversiones.
   */
  public void medicionPeriodica(double horasIntervalo, int lecturasMaximas) throws ConversionNoCompatibleException {
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
    StringBuilder salida = new StringBuilder();
    salida.append("Estación Meteorológica: ").append(this.nombre).append("\n");
    salida.append("Ubicación: ").append(this.ubicacionGeografica.toString()).append("\n");
    salida.append("Sensores instalados: ").append(this.sensores.size()).append("\n");

    for (ISensor sensor : this.sensores.values()) {
      salida.append(sensor + "\n");
    }

    if (!this.historialAlertas.isEmpty()) {
      salida.append("\nAlertas activas: ").append(this.historialAlertas.size()).append("\n");
      for (RegistroAlerta alerta : this.historialAlertas) {
        salida.append(alerta.toString()).append("\n");
      }
    }

    return salida.toString();
  }

  @Override
  public String getTituloDocumento() {
    return this.nombre;
  }

  @Override
  public String getSeccionPricipalDocumento() {
    return this.nombre;
  }

  @Override
  public List<String> getParrafosDocumento() {
    return List.of(
        "Ubicación: " + this.ubicacionGeografica,
        "Sensores instalados: " + this.sensores.size(),
        "Última lectura: : " + this.ultimaLectura.truncatedTo(ChronoUnit.SECONDS));
  }

  @Override
  public Map<String, List<String>> getColeccionesDocumento() {
    Map<String, List<String>> colecciones = new LinkedHashMap<>();
    List<String> listaSensores = new ArrayList<>();

    for (ISensor sensor : this.sensores.values()) {
      listaSensores.add(sensor.toString());
    }

    colecciones.put("Sensores Activos", listaSensores);

    if (!this.historialAlertas.isEmpty()) {
      List<String> listaAlertas = new ArrayList<>();
      for (RegistroAlerta alerta : this.historialAlertas) {
        listaAlertas.add(alerta.toString());
      }
      colecciones.put("Alertas activas", listaAlertas);
    }

    return colecciones;
  }
}
