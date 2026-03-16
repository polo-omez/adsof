/**
 * Clase que representa un mensaje que se propaga por la red social.
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *       Nombre del fichero: Mensaje.java
 */
public class Mensaje {
    private String texto;
    private int alcance;
    private Usuario usuarioActual;

    /**
     * Constructor de la clase Mensaje.
     * @param texto Contenido del mensaje.
     * @param alcance Capacidad inicial de difusión.
     * @param origen Usuario donde se crea el mensaje inicialmente.
     */
    public Mensaje(String texto, int alcance, Usuario origen) {
        this.texto = texto;
        this.alcance = alcance;
        this.usuarioActual = origen;
    }

    /**
     * Obtiene el texto del mensaje.
     * @return El contenido del mensaje.
     */
    public String getTexto() { 
        return texto; 
    }

    /**
     * Obtiene el alcance del mensaje.
     * @return La capacidad actual de difusión del mensaje.
     */
    public int getAlcance() { 
        return alcance; 
    }

    /**
     * Obtiene el usuario actual donde se encuentra el mensaje.
     * @return El usuario que actualmente posee el mensaje.
     */
    public Usuario getUsuarioActual() { 
        return usuarioActual; 
    }

    /**
     * Comprueba si el mensaje tiene alcance suficiente para el enlace.
     * 
     * @param e Enlace por el que se pretende difundir.
     * @return true si el alcance >= coste real del enlace.
     */
    public boolean puedeDifundirPor(Enlace e) {
        return this.alcance >= e.costeReal();
    }

    /**
     * Comprueba si un usuario acepta el mensaje.
     * Por ahora siempre devuelve true (previsión para futuras extensiones).
     * 
     * @param u Usuario destino.
     * @return true siempre en esta versión.
     */
    public boolean aceptadoPor(Usuario u) {
        return true;
    }

    /**
     * Intenta difundir el mensaje a través de un enlace específico.
     * 
     * @param e Enlace de difusión.
     * @return true si la difusión tuvo éxito, false en caso contrario.
     */
    public boolean difunde(Enlace e) {
        if (!e.getOrigen().equals(this.usuarioActual)) {
            return false;
        }

        if (puedeDifundirPor(e) && aceptadoPor(e.getDestino())) {
            this.usuarioActual = e.getDestino();

            this.usuarioActual.registrarMensaje(this);

            this.alcance -= e.costeReal();
            this.alcance += this.usuarioActual.getCapacidadAmplificacion();
            
            return true;
        }

        return false;
    }

    /**
     * Sobrecarga que recibe un array de usuarios que el mensaje tiene previsto visitar.
     * @param destinos Array de usuarios para intentar la difusión secuencial.
     */
    public void difunde(Usuario... destinos) {
        for (int i = 0; i < destinos.length; i++) {
            Usuario siguienteDestino = destinos[i];
            
            Enlace enlaceABuscar = this.usuarioActual.getEnlace(siguienteDestino);
            
            if (enlaceABuscar != null) {
                this.difunde(enlaceABuscar);
            }
        }
    }

    @Override
    public String toString() {
        return "Mensaje (" + texto + ":" + alcance + ") en @" + usuarioActual.getNombre();
    }
}