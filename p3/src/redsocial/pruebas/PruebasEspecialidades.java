package redsocial.pruebas;

import redsocial.*;

/**
 * Prueba específica para las funcionalidades del Apartado 6.
 * Verifica MensajeControlado, EnlaceSeñuelo y UsuarioInteresado.
 * * @author Pablo Gómez
 * 
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: PruebasEspecialidades.java
 */
public class PruebasEspecialidades {

  public static void main(String[] args) {
    System.out.println("=== PRUEBAS APARTADO 6 ===");

    // 1. TEST DE USUARIO INTERESADO
    System.out.println("\n--- 1. Test Usuario Interesado ---");
    UsuarioInteresado pablo = new UsuarioInteresado("Pablo", 2);
    Usuario juanNormal = new Usuario("Juan", 1, Exposicion.BAJA);
    Usuario luciaFamosa = new Usuario("Lucia", 1, Exposicion.VIRAL);

    // Pablo conoce a Juan y a Lucia
    pablo.addEnlace(juanNormal, 1);
    pablo.addEnlace(luciaFamosa, 2);

    Mensaje mNormal = new Mensaje("Cotilleo", 10, pablo);
    // Le pedimos a Pablo que se lo mande a Juan...
    mNormal.difunde(juanNormal);
    // ...pero como Pablo es 'Interesado', debería ignorar a Juan y mandárselo a
    // Lucia (que es VIRAL)
    System.out.println("Destino real del mensaje (Debería ser @Lucia): " + mNormal.getUsuarioActual().getNombre());

    // 2. TEST DE MENSAJE CONTROLADO Y SEÑUELO
    System.out.println("\n--- 2. Test Mensaje Controlado y Enlace Señuelo ---");
    Usuario admin = new Usuario("Admin", 5, Exposicion.ALTA);
    Usuario hacker = new Usuario("Hacker", 1, Exposicion.MEDIA);

    // El hacker intenta engañar al admin con un señuelo (50% de probabilidad de
    // retorno)
    EnlaceSeñuelo trampa = new EnlaceSeñuelo(admin, hacker, 5, 0.5, 0.5);
    admin.addEnlace(trampa);

    // Mensaje muy estricto: Rigidez 50. Solo viaja a usuarios VIRALES o por enlaces
    // 100% seguros
    MensajeControlado mc = new MensajeControlado("Top Secret", 100, admin, 50);

    System.out.println("Rigidez del mensaje: " + mc.getRigidez());
    System.out.println("¿El mensaje controlado confía en el señuelo?: " + mc.puedeDifundirPor(trampa)); // false

    // Intentamos forzar el envío
    boolean exito = mc.difunde(trampa);
    System.out.println("¿Consiguió el Hacker robar el mensaje?: " + exito); // false
    System.out.println("Estado final: " + mc); // Sigue en Admin
  }
}
