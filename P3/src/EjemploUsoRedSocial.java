import java.io.IOException;

/**
 * Clase de prueba para la RedSocial.
 * Verifica la carga de ficheros, la simulación y la persistencia.
 * 
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *      Nombre del fichero: EjemploUsoRedSocial.java
 */
public class EjemploUsoRedSocial {

    /**
     * Método principal para ejecutar el ejemplo de uso de la red social.
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        try {
            // Creamos la red social a partir de los ficheros iniciales
            RedSocial s = new RedSocial("USUARIOS.txt", "ENLACES.txt", "MENSAJE.txt");
            
            // Probamos la FACHADA, añadiendo algo manualmente
            s.addUsuario("pablo", 10);
            s.addEnlace("diego", "pablo", 2);
            System.out.println("Usuario 'pablo' añadido y conectado desde 'diego'.");

            // Probamos la PERSISTENCIA, guardamos el estado actual
            s.salvar("USUARIOS_BACKUP.txt", "ENLACES_BACKUP.txt");
            System.out.println("Ficheros 'USUARIOS_BACKUP.txt' y 'ENLACES_BACKUP.txt' creados.");

            // Probamos el control de ERRORES con un fichero inexistente
            // Esto lanzará IOException porque MENSAJE2.txt no existe
            RedSocial s2 = new RedSocial("USUARIOS.txt", "ENLACES.txt", "MENSAJE2.txt");

        } catch (IOException e) {
            // Capturamos cualquier error de lectura/escritura y mostramos el mensaje del PDF
            System.out.println("Error en archivos");
        }
    }
}