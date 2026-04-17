package meteorologia.sensores;

import java.time.LocalDateTime;
import java.time.LocalDate;

import meteorologia.estrategias.IEstrategia;
import meteorologia.excepciones.ConversionNoCompatibleException;
import meteorologia.alertas.*;
import meteorologia.procesamiento.ConversorIdentidad;
import meteorologia.procesamiento.IConversor;
import meteorologia.procesamiento.ProcesadorDatos;

/**
 * Clase abstracta que define la estructura y el comportamiento base de todo
 * sensor meteorológico.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: SensorMeteorologico.java
 */
public abstract class SensorMeteorologico implements ISensor {
  /** Identificador único alfanumérico del sensor. */
  private String id;
  /** Valor de corrección aplicado a cada lectura. */
  private double offsetCalibracion;
  /** Último valor numérico registrado por el sensor. */
  private double ultimaLectura;
  /** Fecha y hora de la última medición realizada. */
  private LocalDateTime fechaUltimaLecutra;
  /** Fecha en la que el sensor fue instalado en la estación. */
  private LocalDate fechaInstalacion;
  /** Fecha en la que se realizó la última calibración. */
  private LocalDate fechaUltimaCalibracion;
  /** Objeto que define los valores mínimos y máximos operativos. */
  private Rango rangoValores;
  /** Unidad de medida configurada para el sensor. */
  private IUnidad unidadDeLectura;
  /** Motor que simula la generación de valores para las mediciones. */
  private IEstrategia estrategiaGeneracion;
  /** Procesador de datos que almacena el historial y aplica conversiones. */
  private ProcesadorDatos procesadorDatos;
  /** Duración en días de la validez de la calibración actual. */
  private int diasDuracionCalibracion;
  /** Porcentaje de variación a partir del cual se considera cambio brusco. */
  private double umbralCambioBrusco;
  /** Estado de operatividad del sensor. */
  private boolean operativo;

  /**
   * Constructor base para inicializar los atributos de un sensor.
   *
   * @param id                   Identificador único del sensor.
   * @param unidadDeLectura      Unidad de medida inicial.
   * @param rangoValores         Rango operativo permitido.
   * @param estrategiaGeneracion Motor para simular las lecturas.
   */
  public SensorMeteorologico(String id, IUnidad unidadDeLectura, Rango rangoValores, IEstrategia estrategiaGeneracion) {
    this.id = id;
    this.offsetCalibracion = 0.0;
    this.fechaUltimaLecutra = LocalDateTime.now();
    this.fechaUltimaCalibracion = LocalDate.now();
    this.rangoValores = rangoValores;
    this.unidadDeLectura = unidadDeLectura;
    this.estrategiaGeneracion = estrategiaGeneracion;
    this.procesadorDatos = new ProcesadorDatos(new ConversorIdentidad(this.unidadDeLectura));
    this.diasDuracionCalibracion = 365;
    this.umbralCambioBrusco = 0.50;
    this.operativo = true;
  }

  /**
   * Obtiene el valor de la última medición efectuada.
   *
   * @return El valor numérico de la última lectura.
   */
  public double getUltimaLectura() {
    return ultimaLectura;
  }

  /**
   * Obtiene el valor de ajuste actual.
   *
   * @return El offset de calibración en uso.
   */
  public double getOffsetCalibracion() {
    return offsetCalibracion;
  }

  /**
   * Obtiene la fecha y hora de la última medición.
   *
   * @return Objeto LocalDateTime con el instante de la lectura.
   */
  public LocalDateTime getFechaUltimaLecutra() {
    return fechaUltimaLecutra;
  }

  /**
   * Obtiene el procesador de datos asociado al sensor.
   *
   * @return El procesador de datos.
   */
  public ProcesadorDatos getProcesadorDatos() {
    return procesadorDatos;
  }

  /**
   * Obtiene el rango de valores operativos del sensor.
   *
   * @return Objeto Rango con los límites permitidos.
   */
  public Rango getRangoValores() {
    return rangoValores;
  }

  /**
   * Modifica la unidad de lectura del sensor.
   *
   * @param unidadDeLectura La nueva unidad a configurar.
   */
  public void setUnidadDeLectura(IUnidad unidadDeLectura) {
    this.unidadDeLectura = unidadDeLectura;
  }

  @Override
  public void setEstrategiaGeneracion(IEstrategia nuevaEstrategia) {
    this.estrategiaGeneracion = nuevaEstrategia;
  }

  @Override
  public void setUmbralCambioBrusco(double porcentaje) {
    this.umbralCambioBrusco = porcentaje;
  }

  @Override
  public String getIdentificador() {
    return this.id;
  }

  @Override
  public LocalDate getFechaInstalacion() {
    return fechaInstalacion;
  }

  @Override
  public IUnidad getUnidadDeLectura() {
    return unidadDeLectura;
  }

  @Override
  public void calibrar(double offset) {
    this.calibrar(offset, 365);
  }

  @Override
  public void calibrar(double offset, int diasDuracion) {
    this.offsetCalibracion = offset;
    this.diasDuracionCalibracion = diasDuracion;
    this.fechaUltimaCalibracion = LocalDate.now();
    this.operativo = true;
  }

  @Override
  public void setFechaInstalacion(LocalDate fecha) {
    this.fechaInstalacion = fecha;
  }

  @Override
  public void cambiarConversor(IConversor nuevoConversor) throws ConversionNoCompatibleException {
    IUnidad unidadLectura = this.getUnidadDeLectura();
    if (nuevoConversor.getUnidadOrigen() != unidadLectura) {
      throw new ConversionNoCompatibleException(unidadLectura, nuevoConversor.getUnidadDestino());
    }
    this.procesadorDatos.setConversor(nuevoConversor);
  }

  @Override
  public boolean estaCalibrado() {
    if (!this.operativo) {
      return false;
    }

    LocalDate fechaCaducidad = this.fechaUltimaCalibracion.plusDays(this.diasDuracionCalibracion);
    if (LocalDate.now().isAfter(fechaCaducidad) || LocalDate.now().isEqual(fechaCaducidad)) {
      this.operativo = false;
      return false;
    }

    return true;
  }

  @Override
  public void medir(LocalDateTime fechaMedicion) throws AlertaMeteorologicaException {
    if (!this.operativo || !this.estaCalibrado()) {
      this.operativo = false;
      LocalDate fechaCaducidad = this.fechaUltimaCalibracion.plusDays(this.diasDuracionCalibracion);
      throw new CalibracionCaducadaException(this, fechaCaducidad);
    }

    double valorAnterior = this.ultimaLectura;
    double valorGenerado = this.estrategiaGeneracion.generarValor() - this.offsetCalibracion;

    if (!this.rangoValores.enRango(valorGenerado)) {
      this.operativo = false;
      throw new LecturaFueraDeRangoException(this, valorGenerado);
    }

    boolean hayCambioBrusco = false;
    if (Math.abs(valorAnterior) > 0.0001) {
      double diferenciaPorcentual = Math.abs(valorGenerado - valorAnterior) / Math.abs(valorAnterior);
      if (diferenciaPorcentual > this.umbralCambioBrusco) {
        hayCambioBrusco = true;
      }
    }

    this.ultimaLectura = valorGenerado;
    this.fechaUltimaLecutra = fechaMedicion;

    try {
      double valorConvertido = this.procesadorDatos.getConversor().convertir(this.ultimaLectura);
      this.procesadorDatos.addLectura(valorConvertido, fechaMedicion);
    } catch (ConversionNoCompatibleException e) {
      System.err.println("Error de conversión en sensor " + this.id + ": " + e.getMessage());
    }

    if (hayCambioBrusco) {
      throw new CambioBruscoException(this, valorAnterior, valorGenerado);
    }
  }

  @Override
  public String toString() {
    String string = this.getIdentificador() + " (" + this.unidadDeLectura.getSimbolo() + ")";
    IUnidad destinoConversor = this.procesadorDatos.getConversor().getUnidadDestino();
    if (destinoConversor != this.unidadDeLectura) {
      string += " con conversor a " + destinoConversor.getSimbolo();
    }
    string += ": " + this.procesadorDatos;
    return string;
  }
}
