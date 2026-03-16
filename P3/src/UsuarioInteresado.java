/**
 * Clase que representa un usuario interesado, que prioriza enlaces a usuarios con alta exposición.
 * Si no encuentra ninguno, se comporta como un usuario estándar.
 * 
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *       Nombre del fichero: UsuarioInteresado.java
 */
public class UsuarioInteresado extends Usuario {

    /**
     * Constructor de la clase UsuarioInteresado.
     * 
     * @param nombre Nombre del usuario.
     * @param capacidad Capacidad de amplificación del usuario.
     */
    public UsuarioInteresado(String nombre, int capacidad) {
        super(nombre, capacidad);
    }

    @Override
    public Enlace getEnlace(Usuario destino) {
        // Buscamos el primer enlace a alguien ALTA o VIRAL
        for (int i = 0; i < getNumEnlaces(); i++) {
            Enlace e = getEnlace(i);
            Exposicion expDestino = e.getDestino().getExposicion();
            if (expDestino == Exposicion.ALTA || expDestino == Exposicion.VIRAL) {
                return e;
            }
        }

        return super.getEnlace(destino);
    }
}