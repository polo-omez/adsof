package meteorologia.sensores;

import java.time.LocalDate;
import java.time.LocalDateTime;

import meteorologia.estrategias.IEstrategia;
import meteorologia.excepciones.*;
import meteorologia.alertas.*;
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

  /**
   * Obtiene la unidad de lectura actual configurada en el sensor.
   * * @return Objeto IUnidad.
   */
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
   * @throws AlertaMeteorologicaException    Si surge alguna alerta durante la
   *                                         medición.
   * @throws ConversionNoCompatibleException Si hay errores con las conversiones
   *                                         de unidades.
   */
  public void medir(LocalDateTime fechaMedicion) throws AlertaMeteorologicaException, ConversionNoCompatibleException;

  /**
   * Aplica un valor de corrección al sensor.
   *
   * @param offset Valor numérico a ajustar.
   */
  public void calibrar(double offset);

  /**
   * Aplica un valor de corrección al sensor por un tiempo determinado.
   *
   * @param offset       Valor numérico a ajustar.
   * @param diasDuracion Duración de la calibración en días.
   */
  public void calibrar(double offset, int diasDuracion);

  /**
   * Comprueba el estado operativo del sensor.
   *
   * @return true si está calibrado, false si no lo está.
   */
  public boolean estaCalibrado();

  /**
   * Modifica la estrategia de generación de valores del sensor.
   * * @param nuevaEstrategia La estrategia a emplear.
   */
  public void setEstrategiaGeneracion(IEstrategia nuevaEstrategia);

  /**
   * Cambia el conversor de datos del sensor.
   * * @param conversor El nuevo conversor a utilizar.
   * 
   * @throws ConversionNoCompatibleException Si el conversor es incompatible con
   *                                         la unidad.
   */
  public void cambiarConversor(IConversor conversor) throws ConversionNoCompatibleException;

  /**
   * Establece el porcentaje a partir del cual se considera que hay un cambio
   * brusco.
   * * @param porcentaje Valor porcentual (ej. 0.50 para 50%).
   */
  public void setUmbralCambioBrusco(double porcentaje);
}
