/**
 * Pruebas de difusión de mensajes en la red social 
 * 
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *       Nombre del fichero: Pruebas.java
 */
public class Pruebas {
    
    /**
     * Método principal para ejecutar las pruebas de difusión de mensajes en la red social.
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        Usuario ana = new Usuario("ana", 1);
        Usuario luis = new Usuario("luis", 5);
        Usuario carmen = new Usuario("carmen");

        Mensaje m = new Mensaje("Hi!", 50, ana);
        ana.addEnlace(new Enlace(ana, luis, 68));   
        ana.addEnlace(carmen, 33);
        
        System.out.println(m);              // Mensaje (Hi!: 50) en @ana
        m.difunde(luis, carmen);            // Falla luis va a carmen
        System.out.println(m);              // Mensaje (Hi!: 19) en @carmen

        carmen.addEnlace(new Enlace(carmen, luis, 11));
        m.difunde(carmen.getEnlace(luis));
        System.out.println(m);              // Mensaje (Hi!: 13) en @luis

        // PRUEBAS ADICIONALES
        System.out.println("\nPRUEBAS ADICIONALES:");
        // Intentamos que luis se siga a sí mismo y que ana siga a carmen otra vez
        boolean auto = luis.addEnlace(luis, 5);
        boolean duplicado = ana.addEnlace(carmen, 10);

        System.out.println("Autorreferencia bloqueada: " + !auto);
        System.out.println("Duplicado bloqueado: " + !duplicado);

        // Creamos un mensaje con muy poco alcance y un enlace caro
        Usuario pedro = new Usuario("pedro", 10);
        luis.addEnlace(pedro, 100);
        Mensaje m2 = new Mensaje("SOS", 5, luis);

        System.out.println("Antes de fallar: " + m2);  
        m2.difunde(pedro);  // Deberí fallar porque 5 < 100
        System.out.println("Tras fallo (no debe moverse): " + m2);
        
        // Intentamos difundir a un usuario con el que no hay conexión directa
        m2.difunde(ana); // Luis no tiene enlace directo a ana, debe fallar
        System.out.println("Tras salto inexistente (no debe moverse): " + m2);

        // Esto verifica que la variable static de Enlace funciona bien
        System.out.println("Coste total acumulado de la red: " + Enlace.getCosteTotalAcumulado());
    }
}