package redsocial;

public class EjemploUsoMensajesBasicos { // [cite: 66]
  public static void main(String[] args) { // [cite: 67]
    Usuario ana = new Usuario("ana", 1); // capacidad de amplificación 1 [cite: 70, 72, 73]
    Usuario luis = new Usuario("luis", 5); // [cite: 71, 74]
    Usuario carmen = new Usuario("carmen"); // por defecto capacidad 2 [cite: 75, 77]
    Mensaje m = new Mensaje("Hi!", 50, ana); // texto (Hi!) 50 unid. alcance inicial, msj en ana [cite: 76, 78]

    ana.addEnlace(new Enlace(ana, luis, 68)); // [cite: 79]
    ana.addEnlace(carmen, 33); // [cite: 79]
    System.out.println(m); // [cite: 80]

    m.difunde(luis, carmen); // irá directamente a @carmen [cite: 81]
    System.out.println(m); // alcance 19 = 50 - 33 + 2 [cite: 82]

    carmen.addEnlace(new Enlace(carmen, luis, 11)); // [cite: 83]
    m.difunde(carmen.getEnlace(luis)); // [cite: 84]
    System.out.println(m); // en @luis con alcance 13 = 19 - 11 + 5 [cite: 85, 86]
  } // [cite: 68]
} // [cite: 69]
