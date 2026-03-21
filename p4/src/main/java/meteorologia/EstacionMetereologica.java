package meteorologia;

import java.util.*;

import java.time.LocalDateTime;

import meteorologia.sensores.ISensor;
import meteorologia.excepciones.*;

public class EstacionMetereologica {
  private String nombre;
  private Ubicacion ubicacionGeografica;
  private Map<String, ISensor> sensores;

  public EstacionMetereologica(String nombre, double latitud, double longitud) {
    this.nombre = nombre;
    this.ubicacionGeografica = new Ubicacion(latitud, longitud);
    this.sensores = new HashMap<>();
  }

  public String getNombre() {
    return nombre;
  }

  public void addSensor(ISensor sensor) throws SensorDuplicadoException {
    String sensorId = sensor.getIdentificador();

    if (sensores.containsKey(sensorId)) {

      throw new SensorDuplicadoException(sensores.get(sensorId), sensor);

    }
    sensores.put(sensor.getIdentificador(), sensor);
    sensor.setFechaInstalacion(LocalDateTime.now());

  }

  public ISensor getSensor(String sensorId) throws SensorNoEncontradoException {
    if (!sensores.containsKey(sensorId)) {
      throw new SensorNoEncontradoException(sensorId);
    }
    return sensores.get(sensorId);
  }

  public List<ISensor> getSensores(Class<?> tipo) {
    List<ISensor> sensoresTipo = new ArrayList<>();

    for (ISensor sensor : sensores.values()) {
      if (tipo.isInstance(sensor))
        sensoresTipo.add(sensor);
    }

    return sensoresTipo;

  }
}
