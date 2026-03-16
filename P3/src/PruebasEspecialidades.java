/**
 * Prueba específica para las funcionalidades del Apartado 6.
 * 
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *      Nombre del fichero: PruebasEspecialidades.java
 */
public class PruebasEspecialidades {
    /**
     * Método principal para ejecutar las pruebas específicas del Apartado 6.
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        Usuario ana = new Usuario("Ana", 5, Exposicion.BAJA); 
        UsuarioInteresado pablo = new UsuarioInteresado("Pablo", 10); 
        Usuario lucia = new Usuario("Lucia", 2, Exposicion.VIRAL); 

        MensajeControlado mc = new MensajeControlado("Secreto", 100, ana, 25);

        ana.addEnlace(pablo, 10);
        // Señuelo de Pablo a Lucia: coste extra 0.5 y 100% de retorno (1.0)
        EnlaceSenuelo es = new EnlaceSenuelo(pablo, lucia, 5, 0.5, 1.0);
        pablo.addEnlace(es);

        System.out.println("Estado inicial: " + mc);

        System.out.println("\nIntentando difundir a Pablo");
        mc.difunde(pablo); 
        
        if (mc.getUsuarioActual().equals(pablo)) {
            System.out.println("Éxito: El mensaje ha llegado a " + mc.getUsuarioActual().getNombre());
            System.out.println("Estado actual: " + mc);
        }

        System.out.println("\nIntentando difundir a Lucia (Falla por rigidez)");
        mc.difunde(lucia);
        
        if (mc.getUsuarioActual().equals(pablo)) {
            System.out.println("Correcto: El mensaje NO se movió a Lucia (Rigidez insuficiente)");
        }

        System.out.println("\nComprobando restricciones de MensajeControlado");
        boolean puedePorSenuelo = mc.puedeDifundirPor(es);
        System.out.println("¿MensajeControlado puede usar EnlaceSenuelo?: " + puedePorSenuelo); // Debe ser false

        System.out.println("\nTest de memoria y exposición en Ana");
        System.out.println("Exposición inicial de Ana: " + ana.getExposicion());
        
        // Registramos un mensaje con alcance 100. Como el historial estaba vacío, para que subia de BAJA al siguiente nivel.
        ana.registrarMensaje(mc); 
        System.out.println("Exposición de Ana tras recibir mensaje: " + ana.getExposicion());
    }
}