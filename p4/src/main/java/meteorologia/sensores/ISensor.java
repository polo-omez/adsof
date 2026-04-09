package meteorologia.sensores;

import java.time.LocalDate;
import java.time.LocalDateTime;

import meteorologia.estrategias.IEstrategia;
import meteorologia.excepciones.*;
import meteorologia.procesamiento.IConversor;

/**
 * Interfaz que define el contrato básico que debe cumplir cualquier sensor
 * meteorológico.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: ISensor.java
 */
public interface ISensor {
  /**
   * Obtiene el identificador único del sensor.
   *
   * @return Cadena con el ID.
   */
  public String getIdentificador();

  /**
   * Obtiene la fecha en la que el sensor fue instalado.
   *
   * @return Objeto LocalDate de la instalación.
   */
  public LocalDate getFechaInstalacion();

  public IUnidad getUnidadDeLectura();

  /**
   * Asigna la fecha de instalación al sensor.
   *
   * @param fecha Objeto LocalDate a asignar.
   */
  public void setFechaInstalacion(LocalDate fecha);

  /**
   * Obliga al sensor a registrar una nueva medición.
   *
   * @param fechaMedicion Fecha y hora de la lectura.
   */
  public void medir(LocalDateTime fechaMedicion) throws ConversionNoCompatibleException;

  /**
   * Aplica un valor de corrección al sensor.
   *
   * @param offset Valor numérico a ajustar.
   */
  public void calibrar(double offset);

  /**
   * Comprueba el estado operativo del sensor.
   *
   * @return true si está calibrado, false si no lo está.
   */
  public boolean estaCalibrado();

  public void setEstrategiaGeneracion(IEstrategia nuevaEstrategia);

  public void cambiarConversor(IConversor conversor) throws ConversionNoCompatibleException;
}
