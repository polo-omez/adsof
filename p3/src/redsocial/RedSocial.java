package redsocial;

import java.util.*;
import java.io.*;

/**
 * Clase que representa la Red Social completa y actúa como Fachada (Facade).
 * Gestiona el almacenamiento global de usuarios, enlaces y mensajes.
 * Permite inicializar la red mediante lectura de ficheros de texto, simular
 * la propagación de un mensaje inicial, y persistir el estado de la red
 * guardándolo de nuevo en ficheros.
 *
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.0
 *          Nombre del fichero: RedSocial.java
 */
public class RedSocial {

  /**
   * Mapa que almacena los usuarios registrados indexados por su nombre para
   * búsquedas rápidas.
   */
  private Map<String, Usuario> usuarios;
  /** Lista global que almacena todos los enlaces creados en la red. */
  private List<Enlace> enlaces;
  /** Lista global que almacena todos los mensajes generados en la red. */
  private List<Mensaje> mensajes;

  /**
   * Referencia al mensaje inicial que se utilizará para la simulación automática.
   */
  private Mensaje mensajeInicial;
  /**
   * Secuencia de usuarios previstos que el mensaje inicial intentará visitar
   * durante la simulación.
   */
  private List<Usuario> secuenciaInicial;

  /**
   * Construye una red social vacía, inicializando sus colecciones internas.
   * Utilizado para el patrón Fachada cuando se desea crear la red manualmente.
   */
  public RedSocial() {
    // Se usa LinkedHashMap para mantener el orden de inserción al guardar en
    // fichero
    this.usuarios = new LinkedHashMap<>();
    this.enlaces = new ArrayList<>();
    this.mensajes = new ArrayList<>();
    this.secuenciaInicial = new ArrayList<>();
  }

  /**
   * Construye la red social leyendo su estado desde ficheros de texto e inicia
   * la simulación de propagación del mensaje.
   *
   * @param ficheroUsuarios Ruta del fichero que contiene los usuarios.
   * @param ficheroEnlaces  Ruta del fichero que contiene los enlaces.
   * @param ficheroMensaje  Ruta del fichero que contiene el mensaje inicial y su
   *                        recorrido.
   * @throws IOException Si ocurre un error al abrir o leer los ficheros (ej. no
   *                     existen).
   */
  public RedSocial(String ficheroUsuarios, String ficheroEnlaces, String ficheroMensaje) throws IOException {
    this();

    this.leerUsuarios(ficheroUsuarios);
    this.leerEnlaces(ficheroEnlaces);
    this.leerMensaje(ficheroMensaje);

    this.simular();
  }

  /**
   * Registra un nuevo usuario en la red social si no existe previamente.
   *
   * @param nombre        Nombre identificativo del usuario.
   * @param amplificacion Capacidad de amplificación que aportará a los mensajes.
   */
  public void registrarUsuario(String nombre, int amplificacion) {
    if (!this.usuarios.containsKey(nombre)) {
      Usuario u = new Usuario(nombre, amplificacion);
      this.usuarios.put(nombre, u);
    }
  }

  /**
   * Crea un nuevo enlace dirigido entre dos usuarios existentes en la red.
   *
   * @param nombreOrigen  Nombre del usuario desde donde parte el enlace.
   * @param nombreDestino Nombre del usuario al que apunta el enlace.
   * @param coste         Coste que restará al alcance del mensaje al cruzar.
   */
  public void crearEnlace(String nombreOrigen, String nombreDestino, int coste) {
    Usuario origen = this.usuarios.get(nombreOrigen);
    Usuario destino = this.usuarios.get(nombreDestino);

    if (origen != null && destino != null) {
      Enlace e = new Enlace(origen, destino, coste);
      if (origen.addEnlace(e)) {
        this.enlaces.add(e);
      }
    }
  }

  /**
   * Crea un nuevo mensaje y lo asocia a un usuario inicial dentro de la red.
   *
   * @param texto        Contenido textual del mensaje.
   * @param alcance      Alcance disponible inicial del mensaje.
   * @param nombreOrigen Nombre del usuario en el que se ubicará el mensaje
   *                     inicialmente.
   */
  public void crearMensaje(String texto, int alcance, String nombreOrigen) {
    Usuario origen = this.usuarios.get(nombreOrigen);
    if (origen != null) {
      Mensaje m = new Mensaje(texto, alcance, origen);
      this.mensajes.add(m);
    }
  }

  /**
   * Método interno para poblar la red de usuarios leyendo desde un fichero.
   *
   * @param ficheroUsuarios Ruta del archivo de texto.
   * @throws IOException Si el archivo no se puede leer.
   */
  private void leerUsuarios(String ficheroUsuarios) throws IOException {
    try (BufferedReader br = new BufferedReader(new FileReader(ficheroUsuarios))) {
      String linea;
      while ((linea = br.readLine()) != null) {
        String[] atributos = linea.trim().split("\\s+", 2);
        if (atributos.length == 2) {
          this.registrarUsuario(atributos[0], Integer.parseInt(atributos[1]));
        }
      }
    }
  }

  /**
   * Método interno para poblar los enlaces leyendo desde un fichero.
   *
   * @param ficheroEnlaces Ruta del archivo de texto.
   * @throws IOException Si el archivo no se puede leer.
   */
  private void leerEnlaces(String ficheroEnlaces) throws IOException {
    try (BufferedReader br = new BufferedReader(new FileReader(ficheroEnlaces))) {
      String linea;
      while ((linea = br.readLine()) != null) {
        String[] atributos = linea.trim().split("\\s+", 3);
        if (atributos.length == 3) {
          this.crearEnlace(atributos[0], atributos[1], Integer.parseInt(atributos[2]));
        }
      }
    }
  }

  /**
   * Método interno para configurar el mensaje inicial y su recorrido a partir de
   * un fichero.
   *
   * @param ficheroMensaje Ruta del archivo de texto.
   * @throws IOException Si el archivo no se puede leer.
   */
  private void leerMensaje(String ficheroMensaje) throws IOException {
    try (BufferedReader br = new BufferedReader(new FileReader(ficheroMensaje))) {
      String linea = br.readLine();

      if (linea != null) {
        String[] atributos = linea.trim().split("\\s+", 3);
        if (atributos.length == 3) {
          String textoSinComillas = atributos[0].replace("\"", "");
          int alcance = Integer.parseInt(atributos[1]);
          String nombreInicial = atributos[2];

          this.crearMensaje(textoSinComillas, alcance, nombreInicial);
          this.mensajeInicial = this.mensajes.get(this.mensajes.size() - 1);
        }

        while ((linea = br.readLine()) != null) {
          linea = linea.trim();
          if (this.usuarios.containsKey(linea)) {
            this.secuenciaInicial.add(this.usuarios.get(linea));
          }
        }
      }
    }
  }

  /**
   * Ejecuta la propagación automática del mensaje inicial a lo largo de la
   * secuencia de usuarios leída. Muestra por consola el estado tras cada salto
   * exitoso.
   */
  public void simular() {
    if (this.mensajeInicial == null)
      return;

    for (Usuario siguiente : this.secuenciaInicial) {
      Enlace e = this.mensajeInicial.getUsuarioActual().getEnlace(siguiente);

      if (e != null && this.mensajeInicial.difunde(e)) {
        System.out.println(this.mensajeInicial);
      }
    }
  }

  /**
   * Exporta todo el contenido actual de la red (usuarios, enlaces y mensajes)
   * guardándolo en ficheros de texto para su posterior recuperación.
   *
   * @param ficheroUsuarios Nombre del archivo donde se guardarán los usuarios.
   * @param ficheroEnlaces  Nombre del archivo donde se guardarán los enlaces.
   * @param ficheroMensajes Nombre del archivo donde se guardarán los mensajes.
   * @throws IOException Si ocurre un error de escritura en los archivos.
   */
  public void guardarRed(String ficheroUsuarios, String ficheroEnlaces, String ficheroMensajes) throws IOException {
    this.guardarUsuarios(ficheroUsuarios);
    this.guardarEnlaces(ficheroEnlaces);
    this.guardarMensajes(ficheroMensajes);
  }

  /**
   * Método interno para persistir el mapa de usuarios en disco.
   *
   * @param ruta Ruta del fichero de destino.
   * @throws IOException Si falla la escritura.
   */
  private void guardarUsuarios(String ruta) throws IOException {
    try (PrintWriter pw = new PrintWriter(new FileWriter(ruta))) {
      for (Usuario u : this.usuarios.values()) {
        pw.println(u.getNombre() + " " + u.getCapacidadAmplificacion());
      }
    }
  }

  /**
   * Método interno para persistir la lista de enlaces en disco.
   *
   * @param ruta Ruta del fichero de destino.
   * @throws IOException Si falla la escritura.
   */
  private void guardarEnlaces(String ruta) throws IOException {
    try (PrintWriter pw = new PrintWriter(new FileWriter(ruta))) {
      for (Enlace e : this.enlaces) {
        pw.println(e.getOrigen().getNombre() + " " + e.getDestino().getNombre() + " " + e.getCoste());
      }
    }
  }

  /**
   * Método interno para persistir la lista de mensajes en disco.
   *
   * @param ruta Ruta del fichero de destino.
   * @throws IOException Si falla la escritura.
   */
  private void guardarMensajes(String ruta) throws IOException {
    try (PrintWriter pw = new PrintWriter(new FileWriter(ruta))) {
      for (Mensaje m : this.mensajes) {
        pw.println("\"" + m.getTexto() + "\" " + m.getAlcanceDisponible() + " " + m.getUsuarioActual().getNombre());
      }
    }
  }
}
