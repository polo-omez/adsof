package meteorologia.excepciones;

import meteorologia.sensores.IUnidad;

/**
 * Excepción lanzada cuando se intenta convertir entre unidades incompatibles.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: ConversionNoCompatibleException.java
 */
public class ConversionNoCompatibleException extends Exception {
  /** Unidad de origen de la conversión fallida. */
  private IUnidad unidadOrigen;
  /** Unidad de destino de la conversión fallida. */
  private IUnidad unidadDestino;

  /**
   * Construye la excepción especificando las unidades involucradas.
   *
   * @param origen  La unidad de origen.
   * @param destino La unidad de destino.
   */
  public ConversionNoCompatibleException(IUnidad origen, IUnidad destino) {
    super("Error: conversion directa entre " + origen.getSimbolo() + " y " + destino.getSimbolo() + " no compatible");
    this.unidadOrigen = origen;
    this.unidadDestino = destino;
  }

  /**
   * Obtiene la unidad de destino involucrada en el error.
   *
   * @return La unidad de destino.
   */
  public IUnidad getUnidadDestino() {
    return unidadDestino;
  }

  /**
   * Obtiene la unidad de origen involucrada en el error.
   *
   * @return La unidad de origen.
   */
  public IUnidad getUnidadOrigen() {
    return unidadOrigen;
  }
}
