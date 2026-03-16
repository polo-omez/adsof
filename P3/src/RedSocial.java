import java.io.*;
import java.util.*;

/**
 * Clase que representa la red social a partir de ficheros de texto.
 * Actua como fachada para el sistema de usuarios, enlaces y mensajes.
 * 
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *       Nombre del fichero: RedSocial.java
 */
public class RedSocial {
    private Map<String, Usuario> usuarios;
    private List<Enlace> enlaces;
    private Mensaje mensajeInicial;
    private List<Usuario> rutaDifusion;

    /**
     * Constructor de la clase RedSocial.
     * 
     * @param fUsuarios  Ruta del fichero de usuarios
     * @param fEnlaces   Ruta del fichero de enlaces
     * @param fMensaje   Ruta del fichero de mensaje
     * @throws IOException Si ocurre un error al leer los ficheros
     */
    public RedSocial(String fUsuarios, String fEnlaces, String fMensaje) throws IOException {
        this.usuarios = new HashMap<>();
        this.enlaces = new ArrayList<>();
        this.rutaDifusion = new ArrayList<>();

        leerUsuarios(fUsuarios);
        leerEnlaces(fEnlaces);
        leerMensajeInicial(fMensaje);

        ejecutarSimulacion();
    }

    /**
     * Lee el fichero de usuarios y crea los objetos Usuario correspondientes.
     * El formato esperado es: "nombre capacidadAmplificacion"
     * 
     * @param f Ruta del fichero de usuarios
     * @throws IOException Si ocurre un error al leer el fichero
     */
    private void leerUsuarios(String f) throws IOException {
        try (Scanner sc = new Scanner(new File(f))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine().trim();
                if (linea.isEmpty()) continue; 
                
                String[] partes = linea.split("\\s+");
                if (partes.length >= 2) { 
                    addUsuario(partes[0], Integer.parseInt(partes[1]));
                }
            }
        }
    }

    /**
     * Lee el fichero de enlaces y crea los objetos Enlace correspondientes.
     * El formato esperado es: "origen destino coste"
     * 
     * @param f Ruta del fichero de enlaces
     * @throws IOException Si ocurre un error al leer el fichero
     */
    private void leerEnlaces(String f) throws IOException {
        try (Scanner sc = new Scanner(new File(f))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine().trim();
                if (linea.isEmpty()) continue;

                String[] partes = linea.split("\\s+");
                if (partes.length >= 3) {
                    addEnlace(partes[0], partes[1], Integer.parseInt(partes[2]));
                }
            }
        }
    }

    /**
     * Lee el fichero de mensaje inicial y la ruta de difusión.
     * El formato esperado es:
     * Primera línea: "texto alcance autor"
     * Líneas siguientes: "usuarioDestino"
     * 
     * @param f Ruta del fichero de mensaje
     * @throws IOException Si ocurre un error al leer el fichero
     */
    private void leerMensajeInicial(String f) throws IOException {
        try (Scanner sc = new Scanner(new File(f))) {
            // Primero leemos los datos del mensaje (primera línea)
            if (sc.hasNextLine()) {
                String lineaMensaje = sc.nextLine();
                String[] partes = lineaMensaje.split("\\s+");
                if (partes.length >= 3) {
                    setMensajeInicial(partes[0].replace("\"", ""), Integer.parseInt(partes[1]), partes[2]);
                }
            }
            
            // Después, leemos la ruta (resto de líneas)
            while (sc.hasNextLine()) {
                String nombre = sc.nextLine().trim();
                if (!nombre.isEmpty() && usuarios.containsKey(nombre)) {
                    rutaDifusion.add(usuarios.get(nombre));
                }
            }
        }
    }

    /**
     * Ejecuta la simulación de difusión del mensaje.
     */
    private void ejecutarSimulacion() {
        if (mensajeInicial == null) return;

        for (Usuario destino : rutaDifusion) {
            Enlace e = mensajeInicial.getUsuarioActual().getEnlace(destino);
            if (e != null && mensajeInicial.difunde(e)) {
                System.out.println(mensajeInicial);
            }
        }
    }

    /**
     * Añade un nuevo usuario a la red social.
     * 
     * @param nombre Nombre del usuario.
     * @param capacidadAmplificacion Capacidad de amplificación del usuario.
     */
    public void addUsuario(String nombre, int capacidadAmplificacion) {
        usuarios.put(nombre, new Usuario(nombre, capacidadAmplificacion));
    }

    /**
     * Añade un nuevo enlace entre dos usuarios.
     * 
     * @param origen Nombre del usuario de origen.
     * @param destino Nombre del usuario de destino.
     * @param coste Coste del enlace.
     * @return true si el enlace se añadió correctamente, false en caso contrario.
     */
    public boolean addEnlace(String origen, String destino, int coste) {
        Usuario uOrigen = usuarios.get(origen);
        Usuario uDestino = usuarios.get(destino);
        if (uOrigen != null && uDestino != null) {
            if (uOrigen.addEnlace(uDestino, coste)) {
                enlaces.add(uOrigen.getEnlace(uDestino));
                return true;
            }
        }
        return false;
    }

    /**
     * Establece el mensaje inicial para la simulación.
     * 
     * @param texto Texto del mensaje.
     * @param alcance Capacidad inicial de difusión.
     * @param autor Nombre del usuario que origina el mensaje.
     */
    public void setMensajeInicial(String texto, int alcance, String autor) {
        Usuario uAutor = usuarios.get(autor);
        if (uAutor != null) {
            mensajeInicial = new Mensaje(texto, alcance, uAutor);
        }
    }

    /**
     * Guarda la información de usuarios y enlaces en ficheros.
     * 
     * @param fUsuarios Ruta del fichero de usuarios.
     * @param fEnlaces Ruta del fichero de enlaces.
     * @throws IOException Si ocurre un error al guardar los ficheros.
     */
    public void salvar(String fUsuarios, String fEnlaces) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fUsuarios))) {
            for (Usuario u : usuarios.values()) {
                pw.println(u.getNombre() + " " + u.getCapacidadAmplificacion());
            }
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter(fEnlaces))) {
            for (Enlace e : enlaces) {
                pw.println(e.getOrigen().getNombre() + " " + 
                           e.getDestino().getNombre() + " " + e.getCoste());
            }
        }
    }
}