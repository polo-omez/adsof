package meteorologia.excepciones;

import meteorologia.sensores.IUnidad;

public class ConversionNoCompatibleException extends Exception {
  private IUnidad unidadOrigen;
  private IUnidad unidadDestino;

  public ConversionNoCompatibleException(IUnidad origen, IUnidad destino) {
    super("Error: conversion directa entre " + origen.getSimbolo() + " y " + destino.getSimbolo() + " no compatible");
    this.unidadOrigen = origen;
    this.unidadDestino = destino;
  }

  public IUnidad getUnidadDestino() {
    return unidadDestino;
  }

  public IUnidad getUnidadOrigen() {
    return unidadOrigen;
  }
}
