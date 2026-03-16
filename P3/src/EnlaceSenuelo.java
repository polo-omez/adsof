import java.util.Random;

/**
 * Clase que representa un enlace señuelo, que puede engañar al mensaje haciendo que vuelva al origen.
 * El coste real del enlace es mayor que el coste nominal para reflejar la dificultad de propagación.
 * 
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *       Nombre del fichero: EnlaceSenuelo.java
 */
public class EnlaceSenuelo extends Enlace {
    private double factorExtra;
    private double probRetorno;
    private static final Random random = new Random();

    /**
     * Constructor de la clase EnlaceSenuelo.
     * 
     * @param origen Usuario de origen.
     * @param destino Usuario de destino.
     * @param coste Coste del enlace.
     * @param factor Factor extra de coste.
     * @param prob Probabilidad de retorno al origen.
     */
    public EnlaceSenuelo(Usuario origen, Usuario destino, int coste, double factor, double prob) {
        super(origen, destino, coste);
        this.factorExtra = factor;
        this.probRetorno = prob;
    }

    @Override
    public int costeReal() {
        // Coste propagación + (coste original * factor)
        return getCoste() + (int)(getCoste() * factorExtra);
    }

    @Override
    public Usuario getDestino() {
        // Con probabilidad probRetorno, el destino es el origen (engaño)
        if (random.nextDouble() < probRetorno) {
            return getOrigen();
        }
        return super.getDestino();
    }
}