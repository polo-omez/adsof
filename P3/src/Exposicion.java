/**
 * Niveles de exposición de un usuario.
 */
public enum Exposicion {
    OCULTA(0), BAJA(5), MEDIA(10), ALTA(20), VIRAL(50);

    private final int minRigidez;

    Exposicion(int min) {
        this.minRigidez = min;
    }

    /**
     * Obtiene la rigidez mínima asociada a este nivel de exposición.
     * @return La rigidez mínima para este nivel de exposición.
     */
    public int getMinRigidez() { 
        return minRigidez; 
    }
}