package redsocial;

import java.util.*;

public class Usuario {
  private String nombre;
  private int capacidadAmplificacion;
  private List<Enlace> enlaces;

  public Usuario(String nombre) {
    this(nombre, 2);
  }

  public Usuario(String nombre, int capacidadAmplificacion) {
    this.nombre = nombre;
    this.enlaces = new ArrayList<>();
    this.capacidadAmplificacion = capacidadAmplificacion;
  }

  public String getNombre() {
    return nombre;
  }

  public int getCapacidadAmplificacion() {
    return capacidadAmplificacion;
  }

  public Enlace getEnlace(int i) {
    return enlaces.get(i);
  }

  public int getNumEnlaces() {
    return enlaces.size();
  }

  public Enlace getEnlace(Usuario destino) {

    for (Enlace enlace : this.enlaces) {
      if (enlace.getDestino() == destino) {
        return enlace;
      }
    }

    return null;

  }

  public boolean addEnlace(Enlace e) {
    Usuario destino = e.getDestino();

    if (e.getOrigen() != this || destino == this) {
      return false;
    }

    if (getEnlace(destino) != null) {
      return false;
    }

    this.enlaces.add(e);
    return true;
  }

  public boolean addEnlace(Usuario destino, int coste) {
    Enlace e = new Enlace(this, destino, coste);
    return this.addEnlace(e);

  }

  @Override
  public String toString() {
    StringJoiner sj = new StringJoiner(", ", "[", "]");
    for (Enlace enlace : this.enlaces) {
      sj.add(enlace.toString());
    }

    return "@" + this.nombre + " (" + this.capacidadAmplificacion + ") " + sj.toString();
  }

}
