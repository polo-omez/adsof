package redsocial;

/**
 * Enumeración que define los diferentes niveles de exposición pública
 * que puede tener un usuario dentro de la red social.
 * Están dispuestos en orden creciente de visibilidad (de OCULTA a VIRAL).
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: Exposicion.java
 */
public enum Exposicion {
  OCULTA,
  BAJA,
  MEDIA,
  ALTA,
  VIRAL
}
