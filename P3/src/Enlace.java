/**
 * Clase que representa un enlace entre dos usuarios.
 * 
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *       Nombre del fichero: Enlace.java
 */
public class Enlace {
    private Usuario origen;
    private Usuario destino;
    private int coste;

    private static int costeTotalAcumulado = 0;

    /**
     * Constructor de la clase Enlace.
     * 
     * @param origen Usuario de origen.
     * @param destino Usuario de destino.
     * @param coste Coste del enlace.
     */
    public Enlace(Usuario origen, Usuario destino, int coste) {
        this.origen = origen;
        this.destino = destino;
        this.coste = (coste <= 0) ? 1 : coste;

        costeTotalAcumulado += this.coste;
    }

    /**
     * Constructor de la clase Enlace con coste por defecto 1.
     * 
     * @param origen Usuario de origen.
     * @param destino Usuario de destino.
     */
    public Enlace(Usuario origen, Usuario destino) {
        this(origen, destino, 1);
    }

    /**
     * Obtiene el usuario de origen del enlace.
     * 
     * @return El usuario de origen.
     */
    public Usuario getOrigen() {
        return origen;
    }

    /**
     * Obtiene el usuario de destino del enlace.
     * 
     * @return El usuario de destino.
     */
    public Usuario getDestino() {
        return destino;
    }

    /**
     * Obtiene el coste del enlace.
     * 
     * @return El coste del enlace.
     */
    public int getCoste() {
        return coste;
    }

    /**
     * Modifica el usuario de destino y el coste del enlace.
     * Actualiza el acumulador global restando el coste anterior y sumando el nuevo coste validado.
     * 
     * @param nuevoDestino El nuevo usuario de destino.
     * @param nuevoCoste El nuevo coste del enlace. Si es negativo, se establece
     */
    public void cambiarDestino(Usuario nuevoDestino, int nuevoCoste) {
        int costeValidado = (nuevoCoste <= 0) ? 1 : nuevoCoste;
        
        costeTotalAcumulado = (costeTotalAcumulado - this.coste) + costeValidado;

        this.destino = nuevoDestino;
        this.coste = costeValidado;
    }   

    /**
     * Devuelve el coste especial del enlace. 
     * Por defecto es 0.
     * 
     * @return coste especial
     */
    public int costeEspecial() {
        return 0;
    }

    /**
     * Devuelve el coste real del enlace (coste base + coste especial).
     * 
     * @return coste real
     */
    public int costeReal() {
        return coste + costeEspecial();
    }

    /**
     * Devuelve el coste total acumulado de todos los enlaces creados.
     * 
     * @return coste total acumulado.
     */
    public static int getCosteTotalAcumulado() {
        return costeTotalAcumulado;
    }

    @Override
    public String toString() {
        return "(@" + origen.getNombre() + "--" + coste + "-->@" + destino.getNombre() + ")";
    }
}