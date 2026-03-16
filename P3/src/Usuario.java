import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa a un usuario en el sistema.
 * Actualizada para el Apartado 6 con niveles de exposición e historial.
 * 
 * @author Pablo Gómez
 * @author Jose Antonio Gómez
 * @version 1.1
 *       Nombre del fichero: Usuario.java
 */
public class Usuario {
    private String nombre;
    private int capacidadAmplificacion;
    private List<Enlace> enlacesSalientes;

    private Exposicion exposicion;
    private List<Integer> historialAlcances;

    /**
     * Constructor por defecto con capacidad 2 y exposición ALTA.
     * 
     * @param nombre Nombre del usuario.
     */
    public Usuario(String nombre) {
        this(nombre, 2);
    }

    /**
    * Constructor original: mantiene compatibilidad asignando exposición ALTA.
    * 
    * @param nombre Nombre del usuario.
    * @param capacidadAmplificacion Capacidad de amplificación del usuario.
    */
    public Usuario(String nombre, int capacidadAmplificacion) {
        this(nombre, capacidadAmplificacion, Exposicion.ALTA);
    }

    /**
     * Nuevo constructor que permite especificar la exposición explícitamente.
     * 
     * @param nombre Nombre del usuario.
     * @param capacidadAmplificacion Capacidad de amplificación del usuario.
     * @param exposicion Nivel de exposición del usuario.
     */
    public Usuario(String nombre, int capacidadAmplificacion, Exposicion exposicion) {
        this.nombre = nombre;
        this.capacidadAmplificacion = capacidadAmplificacion;
        this.exposicion = exposicion;
        this.enlacesSalientes = new ArrayList<>();
        this.historialAlcances = new ArrayList<>();
    }

    /**
     * Cambia la exposición manualmente.
     * 
     * @param e Nuevo nivel de exposición para el usuario.
     */
    public void cambiarExposicion(Exposicion e) {
        this.exposicion = e;
    }

    /**
     * Obtiene el nivel de exposición actual del usuario.
     * 
     * @return El nivel de exposición del usuario.
     */
    public Exposicion getExposicion() {
        return exposicion;
    }

    /**
     * Registra un mensaje recibido, guarda su alcance en el historial
     * y ajusta la exposición según el promedio.
     * 
     * @param m Mensaje recibido para registrar.
     */
    public void registrarMensaje(Mensaje m) {
        int alcanceActual = m.getAlcance();
        
        // Calculamos el promedio antes de añadir el nuevo
        if (!historialAlcances.isEmpty()) {
            double suma = 0;
            for (int a : historialAlcances) suma += a;
            double promedio = suma / historialAlcances.size();

            // Ajustamos el nivel
            int nivelActual = this.exposicion.ordinal();
            if (alcanceActual > promedio) {
                // Subimos el nivel si no es VIRAL 
                if (nivelActual < Exposicion.values().length - 1) {
                    this.exposicion = Exposicion.values()[nivelActual + 1];
                }
            } else {
                // Bajamos el nivel si no es OCULTA 
                if (nivelActual > 0) {
                    this.exposicion = Exposicion.values()[nivelActual - 1];
                }
            }
        }
        
        historialAlcances.add(alcanceActual);
    }

    /**
     * Obtiene el nombre del usuario.
     * 
     * @return Nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la capacidad de amplificación del usuario.
     * 
     * @return Capacidad de amplificación del usuario.
     */
    public int getCapacidadAmplificacion() {
        return capacidadAmplificacion;
    }

    /**
     * Obtiene el número total de enlaces salientes desde este usuario.
     * 
     * @return El número de enlaces salientes.
     */
    public int getNumEnlaces() {
        return enlacesSalientes.size();
    }

    /**
     * Accede al i-ésimo enlace de la colección.
     * 
     * @param i Índice del enlace a obtener.
     * @return El enlace en esa posición o null si el índice no es válido.
     */
    public Enlace getEnlace(int i) {
        if (i >= 0 && i < enlacesSalientes.size()) {
            return enlacesSalientes.get(i);
        }
        return null;
    }

    /**
     * Busca un enlace directo hacia un usuario destino.
     * 
     * @param destino Usuario al que se busca la conexión.
     * @return El enlace si existe, null en caso contrario.
     */
    public Enlace getEnlace(Usuario destino) {        
        for (Enlace e : enlacesSalientes) {
            if (e.getDestino().equals(destino)) {
                return e;
            }
        }
        return null;
    }

    /**
     * Agrega un enlace a la coleccion si cumple las reglas de integridad.
     * 
     * @param e Enlace a añadir.
     * @return true si se añadió, false en caso contrario.
     */
    public boolean addEnlace(Enlace e) {
        // El origen debe coincidir con este usuario
        if (!e.getOrigen().equals(this)) {
            return false;
        }

        // Evitar autorreferencias
        if (e.getDestino().equals(this)) {
            return false;
        }

        // Evitar duplicados (un solo enlace por destino)
        if (getEnlace(e.getDestino()) != null) {
            return false;
        }

        return enlacesSalientes.add(e);        
    }

    /**
     * Sobrecarga que crea y añade un enlace.
     * 
     * @param destino Usuario destino.
     * @param coste Coste de la conexión.
     * @return true si se añadió correctamente.
     */
    public boolean addEnlace(Usuario destino, int coste) {
        Enlace nuevo = new Enlace(this, destino, coste);
        return addEnlace(nuevo);
    }

    @Override
    public String toString() {
        String resultado = "@" + nombre + " (" + capacidadAmplificacion + ", " + exposicion + ") [";
        for (int i=0; i<enlacesSalientes.size(); i++) {
            resultado += enlacesSalientes.get(i).toString();
            if (i < enlacesSalientes.size() - 1) resultado += ", ";
        }
        resultado += "]";
        return resultado;
    }
}