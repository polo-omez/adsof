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

  private ProcesadorDatos procesadorDatos;
  private int diasDuracionCalibracion;
  private double umbralCambioBrusco;
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
    this.umbralCambioBrusco = 0.50; // 50% por defecto
    this.operativo = true; // El sensor nace funcionando
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

  /**
   * Obtiene el identificador único del sensor.
   *
   * @return La cadena de texto con el ID.
   */
  @Override
  public String getIdentificador() {
    return this.id;
  }

  /**
   * Obtiene la fecha de instalación del sensor.
   *
   * @return Objeto LocalDate indicando cuándo se instaló.
   */
  @Override
  public LocalDate getFechaInstalacion() {
    return fechaInstalacion;
  }

  @Override
  /**
   * Obtiene la unidad de medida actual del sensor.
   *
   * @return Objeto que representa la unidad configurada.
   */
  public IUnidad getUnidadDeLectura() {
    return unidadDeLectura;
  }

  @Override
  public void calibrar(double offset) {
    this.calibrar(offset, 365); // 365 días por defecto
  }

  @Override
  public void calibrar(double offset, int diasDuracion) {
    this.offsetCalibracion = offset;
    this.diasDuracionCalibracion = diasDuracion;
    this.fechaUltimaCalibracion = LocalDate.now();
    this.operativo = true; // "En los casos en que la toma estaba detenida, deberá retomarse"
  }

  /**
   * Asigna la fecha en la que el sensor es instalado.
   *
   * @param fecha La fecha de instalación.
   */
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

  /**
   * Verifica si el sensor se encuentra calibrado comprobando si la última lectura
   * está en rango.
   *
   * @return true si el sensor está operativo y calibrado, false en caso
   *         contrario.
   */
  @Override
  public boolean estaCalibrado() {
    LocalDate fechaCaducidad = this.fechaUltimaCalibracion.plusDays(this.diasDuracionCalibracion);
    // Si HOY es posterior o igual a la fecha de caducidad, ya no está calibrado
    if (LocalDate.now().isAfter(fechaCaducidad) || LocalDate.now().isEqual(fechaCaducidad)) {
      this.operativo = false; // Se detiene por seguridad
      return false;
    }
    return true;
  }

  /**
   * Ejecuta una nueva medición utilizando la estrategia asignada y aplicando el
   * offset de calibración.
   *
   * @param fechaMedicion La fecha y hora exactas en la que se registra la
   *                      medición.
   */
  @Override
  public void medir(LocalDateTime fechaMedicion) throws AlertaMeteorologicaException {

    // 1. Validar operatividad y calibración ANTES de hacer nada
    if (!this.operativo || !this.estaCalibrado()) {
      this.operativo = false;
      LocalDate fechaCaducidad = this.fechaUltimaCalibracion.plusDays(this.diasDuracionCalibracion);
      throw new CalibracionCaducadaException(this, fechaCaducidad);
    }

    // 2. Generar el valor
    double valorAnterior = this.ultimaLectura;
    double valorGenerado = this.estrategiaGeneracion.generarValor() - this.offsetCalibracion;

    // 3. Validar Rango (Fallo Fatal)
    if (!this.rangoValores.enRango(valorGenerado)) {
      this.operativo = false; // "Evitar medir en sensores fuera de rango"
      throw new LecturaFueraDeRangoException(this, valorGenerado);
    }

    // 4. Calcular Cambio Brusco (Warning)
    boolean hayCambioBrusco = false;
    // Evitamos división por cero al calcular el porcentaje
    if (Math.abs(valorAnterior) > 0.0001) {
      double diferenciaPorcentual = Math.abs(valorGenerado - valorAnterior) / Math.abs(valorAnterior);
      if (diferenciaPorcentual > this.umbralCambioBrusco) {
        hayCambioBrusco = true;
      }
    }

    // 5. Consolidar el dato (Llegamos aquí porque no hubo fallos fatales)
    this.ultimaLectura = valorGenerado;
    this.fechaUltimaLecutra = fechaMedicion;

    try {
      double valorConvertido = this.procesadorDatos.getConversor().convertir(this.ultimaLectura);
      this.procesadorDatos.addLectura(valorConvertido, fechaMedicion);
    } catch (ConversionNoCompatibleException e) {
      System.err.println("Error de conversión en sensor " + this.id + ": " + e.getMessage());
    }

    // 6. Lanzar la alerta (si aplica) DESPUÉS de guardar el dato válido
    if (hayCambioBrusco) {
      throw new CambioBruscoException(this, valorAnterior, valorGenerado);
    }
  }

  /**
   * Devuelve una representación en cadena con los datos comunes de la lectura.
   *
   * @return Cadena con el formato de lectura y fecha truncada a segundos.
   */
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
