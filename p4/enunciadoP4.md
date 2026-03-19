# Análisis y Diseño de Software
## Curso 2025-2026
### Práctica 4: Herencia, Interfaces y Excepciones

* **Inicio:** A partir del 16 de Marzo.
* **Duración:** 3 semanas.
* **Entrega:** En Moodle, una hora antes del comienzo de la siguiente práctica según grupos (semana del 13 de Abril). Peso de la práctica: 30%

El objetivo de la práctica es aprender técnicas de orientación a objetos más avanzadas que en las prácticas anteriores. En concreto, se hará énfasis en conceptos de orientación a objetos como herencia, interfaces, excepciones, y colecciones. En esta práctica diseñaremos e implementaremos un panel de control de una estación meteorológica. Esta aplicación debe permitir gestionar diferentes tipos de sensores (temperatura, humedad, presión), procesar sus datos, y visualizar la información de distintas formas (HTML para una interfaz web y Markdown).

---

### Apartado 1. Sensores meteorológicos (2.5 puntos)

Una estación meteorológica se compone de un conjunto de sensores que miden diferentes variables atmosféricas. Todos los sensores tienen un identificador único, un número real llamado offset de calibración, una unidad de lectura, una fecha y hora de la última lectura, y un valor de la última lectura. Además de permitir medir, deberán gestionar su calibración, de forma que se pueda consultar si están o no correctamente calibrados. La calibración caducará pasado un tiempo (configurable) desde la última calibración, y el sensor se considera no calibrado cuando se realice una lectura fuera de rango. Inicialmente, los tipos de sensores que puede tener nuestra estación meteorológica son los siguientes:

* **Sensor de temperatura:** mide la temperatura en grados Celsius, Fahrenheit o Kelvin. Su unidad de medida por defecto es grados Celsius, y el rango de valores válidos es desde -273.15°C (cero absoluto) hasta 1000°C.
* **Sensor de humedad:** mide la humedad relativa en porcentaje, con un rango de valores válidos entre 0% y 100%.
* **Sensor de presión atmosférica:** mide la presión en hectopascales (hPa), con un rango de valores válidos entre 300 hPa y 1100 hPa.

En todos los casos, el valor de la lectura se obtiene restando el offset de calibración al valor medido por el sensor. 

Al crear un nuevo sensor, se le asignará automáticamente un identificador único generado por el sistema siguiendo el formato "TIPO-NNNN" (donde TIPO es TEMP, HUM, PRES, y NNNN es un número secuencial de 4 dígitos. p.ej. TEMP-0001, HUM-0002). 

La estación meteorológica coordina todos los sensores. Debe tener un nombre y una ubicación geográfica (latitud y longitud). También se debe almacenar la fecha de instalación de cada sensor. Al añadir un sensor, la estación debe controlar que no exista otro con el mismo identificador. En ese caso, deberá lanzar excepciones específicas que permitan identificar el error y contengan los objetos en conflicto para su tratamiento. La funcionalidad principal de la estación meteorológica es orquestar la obtención de datos desde los sensores, permitiendo una lectura simultánea en todos ellos. Esta lectura se debe poder lanzar de manera puntual o configurarse para que se realice de manera periódica (estableciendo un periodo determinado, y un número máximo de lecturas). Por último, la estación debe permitir obtener la lista de sensores registrados, recuperar un sensor por su identificador, u obtener todos los sensores de un tipo específico.

**Se pide:** Usando principios de orientación a objetos, desarrolla todo el código necesario para cumplir con la funcionalidad descrita en este apartado (estación meteorológica y su jerarquía de sensores) con un buen diseño que asegure la mantenibilidad y extensibilidad. Crea testers (programas con un main, o puedes usar JUnit) que prueben la funcionalidad implementada. Como ejemplo, la siguiente salida muestra la lista de sensores registrados en una estación (con un sensor de cada tipo) con sus últimas lecturas y fechas de lectura.

```text
[TEMP-0001 (desde: 2023-09-01): Sensor Temperatura (20.5°C) última lectura: 2026-01-15T10:30:00,
 HUM-0001 (desde: 2024-09-01): Sensor Humedad (65.0%) última lectura: 2026-01-15T10:30:00,
 PRES-0001 (desde: 2025-11-01): Sensor Presión (1013.25 hPa) última lectura: 2026-01-15T10:30:00]
```

---

### Apartado 2. Estrategias de simulación de lectura de sensores (1.5 puntos)

En este apartado añadiremos soporte para simular distintos escenarios de lectura de los sensores. Para ello, un sensor podrá parametrizarse con una estrategia de generación de valores simulados de lectura. Todas las estrategias serán conformes a una misma interfaz, que deberás diseñar. Además, debes crear estrategias para:

* Generar un valor aleatorio, que estará entre el mínimo y el máximo permitidos, o bien fuera de rango con una probabilidad configurable.
* Generar un valor aleatorio cercano al valor generado anterior en un rango de X%, con X configurable.
* Generar un valor aleatorio cercano a la media histórica de todos los valores generados, en un rango de ± X%, con X configurable.

Cada sensor usará una estrategia por defecto, que se utilizará si no se especifica explícitamente una estrategia al construir un sensor. Se pide: Usando principios de orientación a objetos, crea el código necesario para implementar la funcionalidad descrita en este apartado, creando testers que prueben esta funcionalidad. En este apartado, puedes crear además otras estrategias distintas para probar mejor tu diseño, y demostrar su extensibilidad.

---

### Apartado 3. Procesamiento de datos (2 puntos)

En este apartado crearemos procesadores de datos asociados a los sensores. Los procesadores realizan dos funciones: almacenan los datos de lectura en un histórico (con su fecha/hora y valor de lectura), y pueden configurarse con conversores que cambian la unidad de las lecturas obtenidas desde el sensor asociado. El conversor debe convertir desde la unidad de lectura del sensor, hacia otra unidad permitida para los sensores de ese tipo. Por ejemplo, para los sensores de temperatura, podemos tener procesadores de datos configurados para convertir valores entre Celsius, Kelvin y Fahrenheit. Si un procesador se configura con un conversor de Celsius a Kelvin, sólo puede asociarse a un sensor cuya unidad de lectura sea Celsius, y el procesador almacenará la lectura en Kelvin. Si existe un error en la compatibilidad de las unidades, deben lanzarse excepciones específicas. 

Debes realizar un diseño flexible y extensible que permita crear conversores entre distintas unidades, y concatenarlos. Por ejemplo, si se tiene un conversor de Celsius a Kelvin, y otro de Kelvin a Fahrenheit, se debe poder obtener un conversor de Celsius a Fahrenheit, concatenando los dos conversores previos. Para unidades de presión debes considerar hPa (HectoPascales), Pa (Pascales) y mbar (milibares). Para humedad relativa, sólo el porcentaje.

La estación meteorológica incorporará la lógica necesaria para crear un procesador de datos cada vez que se cree un sensor. Por defecto, este será un procesador que no realiza conversión entre unidades (o, dicho de otro modo, que esté configurado con un conversor identidad), pero debe ser posible asociar explícitamente un conversor para cada sensor que se añade a la estación. Finalmente, los procesadores deben soportar obtener estadísticas sobre las lecturas procesadas (mínimo, máximo, media).

**Se pide:** Usando principios de orientación a objetos, desarrolla el código necesario para implementar la funcionalidad descrita en este apartado. Crea testers que prueben esta funcionalidad. A modo de ejemplo, la siguiente salida es el resultado de imprimir la estación meteorológica, y los procesadores de datos de cada sensor. El primer sensor tiene un conversor a Kelvin, y cada procesador imprime su mínimo, máximo, y media de lecturas.

```text
Estación meteorológica: Madrid Centro
Ubicación: 40.4168, -3.7038
Sensores instalados: 2
Última lectura: 2026-03-12T19:12:17
TEMP-0001 (°C) con conversor a °K: [293.39, 293.47, 294.03] ΜΙΝ: 293.39 MAX: 294.03 AVG: 293.63
TEMP-0002 (°C): [20.35, 20.47, 20.35] MIN: 20.35 MAX: 20.47 AVG: 20.39
```

---

### Apartado 4. Alertas (2.5 puntos)

Se debe incorporar al procesamiento de datos un sistema de alertas gestionado mediante excepciones. Las siguientes situaciones deberán lanzar una excepción:

* Sensor sin calibrar (lectura fuera de rango o calibración caducada). La duración de una calibración se establece en el constructor del Sensor o en el método calibrar (con una fecha de fin o una duración en días). Por defecto, la calibración durará 365 días.
* Cambios bruscos en las lecturas (p.ej., un cambio de temperatura superior al 50% entre dos lecturas consecutivas). Se debe permitir configurar el porcentaje de cambio que se considera brusco (por defecto, un 50%).

La estación meteorológica debe gestionar estas excepciones, por un lado, almacenando un histórico de alertas generadas para su consulta posterior; por otro lado, debe evitar medir en los sensores sin calibrar, con calibración caducada, o con lectura fuera de los rangos válidos. En los casos de cambios bruscos, se debe permitir seguir midiendo, pero se debe generar una alerta específica. La estación meteorológica también debe contar con métodos para calibrar un determinado sensor. La calibración consiste en establecer un offset de lectura. Además, la calibración de un sensor eliminará las alertas asociadas con dicho sensor y, en los casos en que la toma de datos estaba detenida, deberá retomarse.

**Se pide:** Usando principios de orientación a objetos, desarrolla el código necesario para implementar la funcionalidad descrita en este apartado. Crea testers que prueben esta funcionalidad, incluyendo casos de prueba para las excepciones de alerta. A modo de ejemplo, la siguiente salida es el resultado de imprimir la estación meteorológica, los procesadores de datos de cada sensor, y el sistema de alertas. Las alertas se han generado desde el tester: (i) forzando un cambio de offset, (ii) leyendo un sensor sin calibrar, y (iii) calibrando con un offset que saca la lectura fuera de rango.

```text
Estación Meteorológica: Madrid Centro
Ubicación: 40.4168, -3.7038
Sensores instalados: 7
Última lectura: 2026-01-15T10:30:00
TEMP-0001 (°C): [20.50, 20.50, 20.50] MIN: 20.50 MAX: 20.50 AVG: 20.50
HUM-0001 (%): [65.00, 65.10, 65.00] MIN: 65.00 MAX: 65.10 AVG: 65.03
PRES-0001 (hPa): [1013.25, 1013.30, 1013.25] MIN: 1013.25 MAX: 1013.30 AVG: 1013.26
HUM-0003 (%): [68.00, 68.00, 67.90]
MIN: 67.90 MAX: 68.00 AVG: 67.96

Alertas activas: 3
[2026-01-12T08:03:00] Cambio brusco en TEMP-0002: 27.0°C (anterior: 10.8°C)
[2026-01-13T11:27:00] Sensor TEMP-0003 sin calibrar (calibración caducada desde 2026-01-01T00:00:00)
- [2026-01-14T17:43:00] Lectura fuera de rango en HUM-0002: 105.0%
```

---

### Apartado 5. Visualización de datos (1.5 puntos)

Queremos visualizar los datos de la estación meteorológica de diferentes formas, en particular como documentos en HTML y en formato Markdown. Tienes una guía de la sintaxis básica de ambos formatos en: [https://www.markdownguide.org/basic-syntax/](https://www.markdownguide.org/basic-syntax/). Para mayor generalidad y extensibilidad, crearemos una familia de formateadores, que tomarán como entrada no un objeto de tipo estación meteorológica, sino un objeto conforme a una interfaz IDocumento, que debes desarrollar tú. Debes diseñar métodos para esta interfaz que permitan obtener:

* El título del documento (p.ej., "Estación Meteorológica: Madrid Centro")
* El título de la sección principal del documento (p.ej., "Madrid Centro")
* Cada uno de los párrafos de la sección principal del documento (p.ej., 3 párrafos: "Ubicación: 40.4168, -3.7038", "Sensores instalados: 7" y "Última lectura: 2026-01-15T10:30:00")
* Colecciones de listas que se incluirán a continuación del contenido sección principal, cada una con su título (p.ej., un título "Sensores activos" y la lista de sensores; otro título "Alertas activas: 3" y la lista de alertas).

Esta interfaz permitirá a los formateadores "ver" un objeto de tipo estación meteorológica como un objeto conforme a IDocumento, desacoplando ambos diseños, y haciendo más reutilizables a los formateadores. Debes procurar un diseño extensible, que permita añadir nuevos tipos de formateadores para objetos de tipo IDocumento (p.ej., para JSON, o YAML) fácilmente.

**Se pide:** Usando principios de orientación a objetos, desarrolla el código necesario que implemente la funcionalidad descrita. Crea testers que prueben la funcionalidad implementada. A modo de ejemplo, la siguiente salida muestra el formateo de una estación meteorológica con dos sensores y sin alertas.

```html
<!DOCTYPE html>
<html lang="es">
<head>
<title>Madrid Centro</title>
</head>
<body>
<h1>Madrid Centro</h1>
<p>Ubicacion: -3.7038, 40.4168</p>
<p>Sensores instalados: 2</p>
<p>Última lectura: 2026-03-12T20:42:29</p>
<p>Sensores activos </p>
<ul>
<li>TEMP-0001 (°C) con conversor a °K: [293.89, 293.72, 293.34]
MIN: 293.34 MAX: 293.89 AVG: 293.65 </li>
<li>TEMP-0002 (°C): [20.02, 20.88, 20.33] MIN: 20.02 MAX: 20.88 AVG: 20.41 </li>
</ul>
</body>
</html>
```

De manera similar, el resultado de formatear a Markdown sería:

```markdown
# Madrid Centro
## Madrid Centro
Ubicacion: -3.7038, 40.4168
Sensores instalados: 2
Última lectura: 2026-03-12T20:42:29
### Sensores activos
TEMP-0001 (°C) con conversor a °K: [293.89, 293.72, 293.34] MIN: 293.34 MAX: 293.89 AVG: 293.65
TEMP-0002 (°C): [20.02, 20.88, 20.33] MIN: 20.02 MAX: 20.88 AVG: 20.41
```

---

### Comentarios adicionales

No olvides que, además del correcto funcionamiento de la práctica, un aspecto fundamental en la evaluación será la calidad del diseño. Tu diseño debe utilizar los principios de orientación a objetos, además de ser claro, fácil de entender, extensible y flexible. Presta atención a la calidad del código, evitando redundancias. Organiza el código en paquetes. Crea programas de prueba para ejercitar el código de cada apartado, y entrégalos con tu código. Puedes usar programas de prueba con un main, o bien usar JUnit.

### Normas de entrega

* Se debe entregar el código Java de los apartados, la documentación generada con javadoc, los programas de prueba creados, y una memoria que contenga el diagrama de diseño de toda la práctica (en PDF) junto con una breve explicación y justificación de las decisiones de diseño tomadas.
* El nombre de los alumnos debe ir en la cabecera javadoc de todas las clases entregadas.
* La entrega la realizará uno de los alumnos de la pareja a través de Moodle.
* Se debe entregar un único fichero ZIP/RAR con todo lo solicitado, que debe llamarse de la siguiente manera: `GR<numero_grupo>_<nombre_estudiantes>.zip`. Por ejemplo, Marisa y Pedro, del grupo 2261, entregarían el fichero: `GR2261_MarisaPedro.zip`.

