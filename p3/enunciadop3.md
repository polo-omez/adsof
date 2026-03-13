Análisis y Diseño de Software 

**Curso 2025-2026** **Práctica 3: Introducción a la Programación Orientada a Objetos con Java** 

* 
**Inicio:** A partir del 23 de febrero. 


* 
**Duración:** 3 semanas. 


* 
**Entrega:** En Moodle, una hora antes del comienzo de la siguiente práctica según grupos (semana del 16 de marzo). 


* 
**Peso de la práctica:** 20% 



El objetivo de esta práctica es introducir al alumno en la programación orientada a objetos con el lenguaje Java. Se pide desarrollar de forma incremental varias clases en Java, incluyendo sus pruebas y documentación. Dichas clases implementarán diversos componentes software (como enlaces, usuarios, mensajes) que sirvan de base para aplicaciones que simulen la propagación de información en una red social. 

En el desarrollo de esta práctica se utilizarán principalmente los siguientes conceptos de Java: 

* Tipos de datos primitivos, `String`, `Array` y `ArrayList` y tipos referencia definidos por el programador. 


* Clases sencillas definidas por el programador para implementar objetos con atributos de instancia y de clase, y con métodos de instancia, métodos de clase, y constructores. 


* Entrada/salida elemental para lectura de archivos en formato texto y visualización de texto en la consola, herencia, sobrescritura de métodos. 


* Comentarios para documentación automática mediante javadoc. 



---

Apartado 0. Introducción 

En esta práctica desarrollarás progresivamente una aplicación sencilla cuyo objetivo es simular la propagación de información dentro de una red social. El sistema estará formado por usuarios conectados entre sí mediante enlaces dirigidos que representan relaciones de seguimiento, y cada usuario tendrá un nivel de "influencia" que afectará a su capacidad para difundir mensajes. 

La aplicación permitirá modelar cómo un mensaje publicado por un usuario se transmite a otros usuarios a través de sus conexiones, consumiendo recursos asociados a esa difusión y pudiendo regenerarse cuando alcanza usuarios especialmente receptivos. Aunque la simulación final será sencilla, deberá diseñarse con vistas a futuras extensiones, como nuevos tipos de usuarios, enlaces con comportamientos especiales o estrategias avanzadas de propagación. 

---

Apartado 1. La clase básica Enlace (1.5 puntos) 

Un enlace conecta unidireccionalmente a dos usuarios (origen y destino). Difundir un mensaje a través de él conlleva un coste de propagación. Debes implementar un constructor con esos tres parámetros (`usuarioOrigen`, `usuarioDestino`, `coste`) y tres getters; no habrá setters para modificar los datos con los que se creó un enlace. 

Si se intentase dar a un enlace un coste menor o igual que cero, se tratará como si el coste fuese 1. Añade la posibilidad de crear un `Enlace` sin coste, y en ese caso se considera 1. Los enlaces solo podrán modificarse con un método `cambiarDestino(Usuario, int)` que cambia el usuario destino y el coste del enlace, pero no cambia el origen. 

Además, implementa un `toString()` que muestre de forma concisa los valores del enlace; por ejemplo, `(@ana--68-->@luis)` indica un enlace desde `@ana` a `@luis` con coste 68. Finalmente, por requisitos futuros, esta clase debe acumular en una variable estática la suma de costes de todos los enlaces creados y ofrecer un método para obtener, en cualquier momento, el total acumulado hasta ese instante. 

En previsión de ampliaciones (apartado 6), añade dos métodos simples:

* 
`int costeEspecial()` que devolverá 0 (solo los enlaces especiales definidos en el futuro tendrán coste especial distinto de cero). 


* 
`int costeReal()` que siempre será la suma del coste del enlace más su coste especial. 



No olvides seguir la guía de estilo de programación Java disponible en Moodle, incluyendo comentarios, especialmente los que se usan para Javadoc, en el código de esta clase y todas las demás clases que escribas. 

---

Apartado 2. La clase básica Usuario (1.5 puntos) 

Un usuario queda definido por su nombre, su capacidad de amplificación (el número de unidades de influencia que puede otorgar a un mensaje cuando éste llega a él) y una colección de enlaces salientes que representan a aquellos usuarios a los que puede difundir directamente la información. La colección de enlaces debe respetar estrictamente el orden de creación, ya que algunos modelos de propagación podrían utilizar ese orden como estrategia para seleccionar la siguiente difusión. 

Se deben ofrecer dos constructores de usuarios:

* Un constructor que recibe solo el nombre, asignando por defecto una capacidad de amplificación igual a 2. 


* Un segundo constructor que recibe como parámetros el nombre y la capacidad de amplificación. 



En ambos casos, el usuario se creará inicialmente con una colección vacía de enlaces salientes. 

Una vez creado el usuario, se le podrán añadir enlaces mediante sucesivas llamadas al método `boolean addEnlace(Enlace e)` que añadirá el enlace recibido siempre y cuando el origen del enlace coincida exactamente con el usuario, y el destino no sea él mismo (para evitar autorreferencias). También se debe evitar añadir un enlace a un destino, si el usuario ya tiene otro enlace a ese destino. El método devolverá `true` si el enlace se ha añadido correctamente, o `false` en caso contrario. Sobrecarga el método para que acepte un usuario destino y un coste, y cree internamente el enlace y lo añada si cumple las condiciones. 

Como en otras clases, no se dispondrá de setters, pero sí de getters y de un método `toString()`.  Además de los getters para nombre y capacidad de amplificación, deberá implementarse:

* 
`getEnlace(int i)` para acceder al i-ésimo enlace de la secuencia de enlaces salientes del usuario. 


* 
`int getNumEnlaces()` para obtener el número total de enlaces salientes. 


* 
`Enlace getEnlace(Usuario destino)` para obtener el enlace directo desde este usuario hacia otro dado, o `null` si no existe tal conexión. 



El método `toString()` devolverá una representación como: 

> 
> `@ana (73) [(@ana--68-->@luis), (@ana--33-->@carmen)]` 
> 
> 

lo que indica que el usuario `@ana` tiene una capacidad de amplificación de 73 y dos enlaces, hacia `@luis` y hacia `@carmen`. 

---

Apartado 3. La clase básica Mensaje (1.5 puntos) 

Un mensaje se caracteriza por un texto, un valor inicial de alcance disponible (que representa su capacidad para seguir difundiéndose) y el usuario en el que se encuentra actualmente. Estos dos últimos atributos irán variando conforme el mensaje avance a través de la red siguiendo los enlaces entre usuarios. 

Además del constructor, los getters y un `toString()` que devuelva `"Mensaje (m:50) en @ana"` para indicar que el mensaje con texto "m" tiene un alcance de 50 unidades y se encuentra actualmente en el usuario `@ana`, debe implementarse un método `boolean difunde(Enlace e)`.  Este método intentará difundir el mensaje siguiendo el enlace dado. La difusión será posible solo si: existe realmente el enlace desde el usuario actual del mensaje; el mensaje dispone de más alcance (o igual) que el coste real del enlace (ver Apartado 1); y el usuario destino puede aceptar el mensaje según restricciones especiales que aparecerán en extensiones posteriores. 

Para facilitar estas comprobaciones se implementarán dos métodos:

* 
`boolean puedeDifundirPor(Enlace e)`, que devolverá `true` si y solo si el alcance del mensaje es mayor o igual que el coste real del enlace. 


* 
`boolean aceptadoPor(Usuario u)`, que por ahora devolverá siempre `true` y se utilizará para futuras extensiones. 



Si la difusión no es posible por alguna de estas razones, el método devolverá `false`. Si la difusión sí es posible: el usuario actual del mensaje pasa a ser el destino del enlace, el alcance del mensaje disminuye en el coste real del enlace, el alcance vuelve a incrementarse en la cantidad correspondiente a la capacidad de amplificación del usuario destino, y entonces el método difunde devolverá `true`. 

Debe sobrecargar el método difunde para que reciba como argumentos un número variable de usuarios que el mensaje tiene previsto visitar en ese orden. El método aplicará iterativamente `difunde(Enlace)`: si el enlace hacia el siguiente usuario existe y puede difundirse, el mensaje se traslada (difunde) allí, pero si no existe el enlace, o el alcance no es suficiente, o el destino no lo acepta, se intenta directamente con el siguiente usuario de la lista, sin detener la difusión por completo. 

El método devolverá `true` solo si el mensaje ha podido difundirse correctamente en todos los saltos en los que realmente se haya realizado una transmisión. Si al menos una vez se ha tenido que "saltar" un usuario porque no había camino posible o no había alcance suficiente, el retorno será `false`, incluso si el mensaje logra llegar a otros usuarios posteriores. 

Por ejemplo, una llamada `difunde(uA, uB, uC, uD, uE, uF)` podría provocar que el mensaje solo visite `uA`, `uC` y `uE`, si, por ejemplo: no existía enlace de `uA` a `uB`, sí existía de `uA` a `uC`, en `uC` el mensaje no tenía alcance suficiente para llegar a `uD`, sí tenía para llegar a `uE`, pero no para llegar a `uF`. En ese caso, el retorno sería `false`. 

---

Apartado 4. Ejemplo de uso y pruebas de las clases anteriores (1 puntos) 

Con las clases de los apartados anteriores se pueden ejecutar programas como el siguiente: 

```java
[cite_start]public class EjemploUsoMensajesBasicos { // [cite: 66]
    [cite_start]public static void main(String[] args) { // [cite: 67]
        Usuario ana = new Usuario("ana", 1);    [cite_start]// capacidad de amplificación 1 [cite: 70, 72, 73]
        Usuario luis = new Usuario("luis", 5);   [cite_start]// [cite: 71, 74]
        Usuario carmen = new Usuario("carmen"); [cite_start]// por defecto capacidad 2 [cite: 75, 77]
        Mensaje m = new Mensaje("Hi!", 50, ana); [cite_start]// texto (Hi!) 50 unid. alcance inicial, msj en ana [cite: 76, 78]
        
        ana.addEnlace(new Enlace(ana, luis, 68)); [cite_start]// [cite: 79]
        ana.addEnlace(carmen, 33); [cite_start]// [cite: 79]
        System.out.println(m); [cite_start]// [cite: 80]
        
        m.difunde(luis, carmen); [cite_start]// irá directamente a @carmen [cite: 81]
        System.out.println(m); [cite_start]// alcance 19 = 50 - 33 + 2 [cite: 82]
        
        carmen.addEnlace(new Enlace(carmen, luis, 11)); [cite_start]// [cite: 83]
        m.difunde(carmen.getEnlace(luis)); [cite_start]// [cite: 84]
        System.out.println(m); [cite_start]// en @luis con alcance 13 = 19 - 11 + 5 [cite: 85, 86]
    [cite_start]} // [cite: 68]
[cite_start]} // [cite: 69]

```

Cuya salida debe ser la siguiente: 

```text
[cite_start]Mensaje (Hi!:50) en @ana // [cite: 88]
[cite_start]Mensaje (Hi!:19) en @carmen // [cite: 88]
[cite_start]Mensaje (Hi!:13) en @luis // [cite: 88]

```

Dicho programa es un ejemplo mínimo para probar el funcionamiento correcto de las clases anteriores. En este apartado debes desarrollar otros programas que realicen pruebas más extensas de todos los métodos implementados e invocándolos en situaciones suficientemente variadas como para tener suficiente confianza en que todo ha sido implementado correctamente. 

---

Apartado 5. Clase RedSocial: Simulación y persistencia en ficheros de texto (2 puntos) 

Para facilitar la reutilización de escenarios más complejos, con un número elevado de usuarios y enlaces entre ellos, en este apartado deberás desarrollar una clase encargada de ejecutar automáticamente una simulación de propagación a partir de varios archivos de texto. 

La mayor parte del trabajo de esta clase se realizará en su constructor, el cual recibirá como parámetros los nombres de tres archivos cuyo contenido permitirá construir la red social completa, el mensaje inicial y la secuencia de usuarios que el mensaje tiene previsto intentar visitar durante su difusión. 

1. El primer archivo describe los usuarios y contiene una línea por cada uno de ellos, con su nombre y su capacidad de amplificación separados por espacios o tabuladores. 


2. El segundo archivo detalla los enlaces, también uno por línea, indicando el nombre del usuario origen, el nombre del usuario destino y el coste de propagación asociado a ese enlace. 


3. Finalmente, el tercer archivo especifica el mensaje que se va a difundir: su autor, el alcance inicial y el usuario en el que se encuentra al comenzar la simulación. Las líneas restantes de este tercer archivo contienen, en el orden dado, los nombres de los usuarios que el mensaje intentará visitar, siguiendo el mecanismo explicado anteriormente en la clase Mensaje. 



El constructor de la clase `RedSocial` deberá leer estos tres archivos y crear los objetos correspondientes. En primer lugar, deberá construir todos los usuarios y almacenarlos de forma adecuada para poder acceder a ellos fácilmente. A continuación, deberá leer la lista de enlaces y, para cada uno de ellos, crear un objeto `Enlace` y añadirlo al usuario origen mediante el método `addEnlace`, tal como se ha definido en el apartado 2. Por último, deberá crear el mensaje inicial a partir de los datos especificados en la primera línea del archivo correspondiente y almacenar en una estructura adecuada la secuencia de usuarios que el mensaje intentará visitar. 

Una vez leídos y construidos todos los elementos, el constructor ejecutará la difusión del mensaje recorriendo la secuencia de usuarios indicada en el archivo. Tras cada difusión exitosa —esto es, cada vez que el mensaje consiga propagarse a través de un enlace válido y con alcance suficiente— el programa deberá mostrar por consola el estado actual del mensaje, utilizando el método `toString()` definido en la clase correspondiente. Este procedimiento permitirá comprobar paso a paso el resultado de la simulación. 

Si alguno de los archivos proporcionados al constructor no existe, no puede abrirse o presenta cualquier problema de lectura, deberá lanzarse una excepción `IOException`. Será responsabilidad del programa que utilice esta clase capturar dicha excepción y mostrar un mensaje adecuado. 

Por lo tanto, con los siguientes archivos: 

| Archivo USUARIOS.txt 

 | Archivo ENLACES.txt 

 | Archivo MENSAJE.txt 

 |
| --- | --- | --- |
|  | ana 3 

 | ana luis 1 

 |
|  | luis 10 

 | ana carmen 5 

 |
|  | carmen 2 

 | luis diego 7 

 |
|  | diego 1 

 | luis mario 4 

 |
|  | mario 3 

 | carmen diego 6 

 |
|  | fede 0 

 | carmen mario B 

 |
|  | diego fede 3 

 |  |
|  | mario fede 0 

 |  |

El siguiente programa da un ejemplo de uso: 

```java
[cite_start]public class EjemploUsoRedSocial { // [cite: 109]
    [cite_start]public static void main(String[] args) { // [cite: 110]
        [cite_start]try { // [cite: 112]
            RedSocial s; [cite_start]// [cite: 113]
            s = new RedSocial("USUARIOS.txt", "ENLACES.txt", "MENSAJE.txt"); [cite_start]// [cite: 114]
            s = new RedSocial("USUARIOS.txt", "ENLACES.txt", "MENSAJE2.txt"); [cite_start]// [cite: 114]
        [cite_start]} catch (IOException e) { // [cite: 114]
            System.out.println("Error en archivos"); [cite_start]// [cite: 114]
        [cite_start]} // [cite: 114]
    [cite_start]} // [cite: 111]
}

```

Debe producir la siguiente salida: 

```text
[cite_start]Mensaje (Hola!:7) en @carmen // [cite: 116]
[cite_start]Mensaje (Hola!:2) en @diego // [cite: 117]
[cite_start]Error en archivos // [cite: 117]

```

La última línea corresponde al hecho de que no se ha creado el archivo `MENSAJE2.txt` que se intenta utilizar en la segunda `RedSocial` de ese programa. 

Extiende la clase `RedSocial` para convertirla en una Fachada ([https://refactoring.guru/design-patterns/facade](https://refactoring.guru/design-patterns/facade)) para las clases que has creado. La idea es poder crear usuarios, enlaces y mensajes a través de métodos de la clase `RedSocial` (y no sólo mediante la lectura de ficheros). La clase debe almacenar los usuarios, enlaces y mensajes existentes, y además, debe ser posible guardar el contenido de la red en ficheros de texto (de tal manera que escritura y lectura sean compatibles). 

---

Apartado 6. Otras clases de enlaces, usuarios y mensajes (2.5 puntos) 

Vamos a extender las clases descritas arriba de forma que existan enlaces, usuarios y mensajes con atributos o comportamientos especializados según describimos a continuación. Estas extensiones se diseñan para introducir variaciones en la dinámica de propagación que permitan simular fenómenos más ricos y realistas. La idea es que el código desarrollado en los apartados anteriores se mantenga válido y operativo, de modo que estas nuevas clases amplíen el sistema sin romper su funcionamiento básico. 

* 
**Enlace Señuelo:** Tal y como se mencionó en el Apartado 1, extenderemos la clase `Enlace` para introducir un nuevo tipo de enlace especial que denominaremos enlace señuelo. Un enlace señuelo se crea igual que un enlace normal, pero recibe dos parámetros adicionales: un factor de coste extra y una probabilidad de retorno obligado. Así, el coste especial (ver Apartado 1) asociado al enlace señuelo se calculará como el producto del coste original por dicho factor extra, de forma que el coste real (el que se descuenta del alcance del mensaje) se calculará sumando el coste de propagación y el coste especial. Por otro lado, la probabilidad de retorno obligado indica con qué probabilidad la difusión no llevará al mensaje al usuario destino especificado, sino que lo devolverá al usuario origen, simulando un comportamiento engañoso o de retroceso en la propagación. 


* 
**Exposición Pública de Usuarios:** Para ampliar el comportamiento de los usuarios modificaremos la clase `Usuario` (del Apartado 2) añadiendo un nuevo atributo que represente su nivel de exposición pública, es decir, cuán visible o accesible es para la recepción de mensajes. Esta exposición tomará uno de los valores: `OCULTA`, `BAJA`, `MEDIA`, `ALTA`, `VIRAL`, dispuestos en orden creciente. Para mantener compatibilidad con el código ya implementado, los constructores existentes conservarán su comportamiento actual, asignando por defecto una exposición `ALTA`. Añadiremos también un nuevo constructor que permita especificar este valor explícitamente, así como un método `void cambiarExposicion(Exposicion e)` para modificarlo durante la ejecución. 


* 
**Historial de Usuarios:** Añade la posibilidad de que cada usuario memorice qué mensajes le han llegado. Cada vez que un usuario reciba un mensaje, deberá registrar dicho mensaje en su historial y ajustar su exposición: si el mensaje llega con un alcance mayor que el promedio de alcances de los mensajes que ha recibido hasta ese momento, aumentará su exposición en un nivel;  en caso contrario, la disminuirá en un nivel. Realiza un programa que pruebe esta nueva funcionalidad. 


* 
**Usuario Interesado:** Introduce un `UsuarioInteresado` que preserve el orden de creación de enlaces, pero está interesado en difundir los mensajes por el primer enlace de su lista cuyo usuario destino tenga alta exposición. En concreto, el `UsuarioInteresado` seleccionará el enlace de forma diferente a la estándar: elegirá el primer enlace de su lista cuyo destino tenga exposición `ALTA` o `VIRAL` (independientemente del destino de dicho enlace), si no lo hay, realizará el comportamiento estándar (búsqueda del enlace directo respetando el orden y el destino). 


* **Mensaje Controlado:** Añadiremos un tipo especial de mensaje denominado `MensajeControlado`. Mientras que un mensaje básico utiliza solamente su alcance para decidir si puede o no propagarse, un mensaje controlado incorpora un atributo numérico adicional llamado rigidez, que representa cuán estricta es su política de difusión. Un mensaje controlado no podrá propagarse nunca por enlaces señuelo, independientemente de que su alcance sea suficiente. Además, la posibilidad de llegar a un usuario dependerá tanto del alcance como del nivel de exposición del usuario destino: en términos generales, un mensaje controlado solo podrá llegar a usuarios con exposición suficiente (determinada por su rigidez). Si la exposición es `OCULTA`, la rigidez puede ser cualquiera; si es `BAJA`, la rigidez debe ser 5 o más; para `MEDIA` debe ser 10 o más; para `ALTA` 20 o más; y para `VIRAL` 50 o más. La lógica concreta de estas restricciones deberá estar integrada en los métodos `puedeDifundirPor` y `aceptadoPor`. 



Ten en cuenta que, intencionadamente, este apartado describe los requisitos anteriores de manera algo menos precisa y detallada que en los apartados anteriores, con el fin de que ejercites tus cualidades de buen diseñador de software orientado a objetos. Asegúrate de que tu diseño recoge bien todos esos requisitos y además no dificulta futuras posibles extensiones de estas clases. Si lo has hecho bien, tanto los programas de prueba (Apartado 4) como el simulador (Apartado 5) deberán seguir funcionando igual que antes. En este apartado debes desarrollar nuevos programas de pruebas para probar toda la funcionalidad añadida a los nuevos tipos de enlaces, usuarios y mensajes. 

---

Normas de Entrega 

Se deberá entregar: 

* Un directorio `src` con todo el código Java. 


* Un directorio `doc` con la documentación generada. 


* Un directorio `txt` con todos los archivos utilizados y generados en las pruebas. 


* Un archivo PDF con una breve justificación de las decisiones que se hayan tomado en el desarrollo de la práctica, los problemas principales que se han abordado y cómo se han resuelto, así como los problemas pendientes de resolver; además se debe incluir el diagrama de clases y una explicación de las pruebas realizadas, cuyos ficheros de entrada y código Java deben de estar incluidos en los directorios antes descritos. 



Se debe entregar un único fichero ZIP con todo lo solicitado, que deberá llamarse de la siguiente manera: `GR<numero_grupo>_<nombre_estudiantes>.zip`. Por ejemplo Marisa López y Pedro Pérez, del grupo 2261, entregarían el fichero: `GR2261_Marisa Lopez Pedro Perez.zip`. 

---

¿Te gustaría que te ayude a redactar el código base en Java de alguna de las clases mencionadas (como `Enlace` o `Usuario`) para empezar con la práctica?
