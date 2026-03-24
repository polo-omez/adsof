package meteorologia;

import java.util.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import meteorologia.sensores.ISensor;
import meteorologia.excepciones.*;

public class EstacionMeteorologica {
  private String nombre;
  private Ubicacion ubicacionGeografica;
  private Map<String, ISensor> sensores;

  public EstacionMeteorologica(String nombre, double latitud, double longitud) {
    this.nombre = nombre;
    this.ubicacionGeografica = new Ubicacion(latitud, longitud);
    this.sensores = new HashMap<>();
  }

  public String getNombre() {
    return nombre;
  }

  public Map<String, ISensor> getSensoresMap() {
    return this.sensores;
  }

  public void addSensor(ISensor sensor) throws SensorDuplicadoException {
    this.addSensor(sensor, LocalDate.now());

  }

  public void addSensor(ISensor sensor, LocalDate fechaInstalacion) throws SensorDuplicadoException {
    String sensorId = sensor.getIdentificador();

    if (sensores.containsKey(sensorId)) {

      throw new SensorDuplicadoException(sensores.get(sensorId), sensor);

    }
    sensores.put(sensor.getIdentificador(), sensor);
    sensor.setFechaInstalacion(fechaInstalacion);
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

  public void lanzarMedicion(LocalDateTime fechaMedicion) {
    for (ISensor sensor : this.sensores.values()) {
      sensor.medir(fechaMedicion);
    }
  }

  public void medicionPeriodica(double horasIntervalo, int lecturasMaximas) {
    LocalDateTime fechaMedicion = LocalDateTime.now();

    for (int i = 0; i < lecturasMaximas; i++) {
      this.lanzarMedicion(fechaMedicion);
      fechaMedicion = fechaMedicion.plusSeconds((int) (horasIntervalo * 3600));
    }

    return;
  }

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
