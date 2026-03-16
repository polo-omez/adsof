/**
 * Clase que representa un mensaje controlado, que tiene una rigidez que limita su propagación.
 * No puede difundirse por enlaces señuelo y solo es aceptado por usuarios con exposición suficiente.
 * 
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *       Nombre del fichero: MensajeControlado.java
 */
public class MensajeControlado extends Mensaje {
    private int rigidez;

    /**
     * Constructor de la clase MensajeControlado.
     * 
     * @param texto Texto del mensaje.
     * @param alcance Alcance del mensaje.
     * @param origen Usuario que origina el mensaje.
     * @param rigidez Rigidez del mensaje.
     */
    public MensajeControlado(String texto, int alcance, Usuario origen, int rigidez) {
        super(texto, alcance, origen);
        this.rigidez = rigidez;
    }

    @Override
    public boolean puedeDifundirPor(Enlace e) {
        // No puede ir por señuelos
        if (e instanceof EnlaceSenuelo) {
            return false;
        }
        return super.puedeDifundirPor(e);
    }

    @Override
    public boolean aceptadoPor(Usuario u) {
        // La rigidez debe ser mayor o igual al mínimo de la exposición del destino
        return this.rigidez >= u.getExposicion().getMinRigidez();
    }
}