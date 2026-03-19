# [cite_start]Análisis y Diseño de Software [cite: 1]
## [cite_start]Curso 2025-2026 [cite: 2]
### [cite_start]Práctica 4: Herencia, Interfaces y Excepciones [cite: 3]

* [cite_start]**Inicio:** A partir del 16 de Marzo. [cite: 4]
* [cite_start]**Duración:** 3 semanas. [cite: 5]
* [cite_start]**Entrega:** En Moodle, una hora antes del comienzo de la siguiente práctica según grupos (semana del 13 de Abril) Peso de la práctica: 30% [cite: 6]

[cite_start]El objetivo de la práctica es aprender técnicas de orientación a objetos más avanzadas que en las prácticas anteriores. [cite: 7] [cite_start]En concreto, se hará énfasis en conceptos de orientación a objetos como herencia, interfaces, excepciones, y colecciones. [cite: 8] [cite_start]En esta práctica diseñaremos e implementaremos un panel de control de una estación meteorológica. [cite: 9] [cite_start]Esta aplicación debe permitir gestionar diferentes tipos de sensores (temperatura, humedad, presión), procesar sus datos, y visualizar la información de distintas formas (HTML para una interfaz web y Markdown). [cite: 10]

---

### [cite_start]Apartado 1. Sensores meteorológicos (2.5 puntos) [cite: 11]

[cite_start]Una estación meteorológica se compone de un conjunto de sensores que miden diferentes variables atmosféricas. [cite: 12] [cite_start]Todos los sensores tienen un identificador único, un número real llamado offset de calibración, una unidad de lectura, una fecha y hora de la última lectura, y un valor de la última lectura. [cite: 13] [cite_start]Además de permitir medir, deberán gestionar su calibración, de forma que se pueda consultar si están o no correctamente calibrados. [cite: 14] [cite_start]La calibración caducará pasado un tiempo (configurable) desde la última calibración, y el sensor se considera no calibrado cuando se realice una lectura fuera de rango. [cite: 15] [cite_start]Inicialmente, los tipos de sensores que puede tener nuestra estación meteorológica son los siguientes: [cite: 16]

* [cite_start]**Sensor de temperatura:** mide la temperatura en grados Celsius, Fahrenheit o Kelvin. [cite: 17] [cite_start]Su unidad de medida por defecto es grados Celsius, y el rango de valores válidos es desde -273.15°C (cero absoluto) hasta 1000°C. [cite: 18]
* [cite_start]**Sensor de humedad:** mide la humedad relativa en porcentaje, con un rango de valores válidos entre 0% y 100%. [cite: 19]
* [cite_start]**Sensor de presión atmosférica:** mide la presión en hectopascales (hPa), con un rango de valores válidos entre 300 hPa y 1100 hPa. [cite: 20]

[cite_start]En todos los casos, el valor de la lectura se obtiene restando el offset de calibración al valor medido por el sensor. [cite: 21] [cite_start]Al crear un nuevo sensor, se le asignará automáticamente un identificador único generado por el sistema siguiendo el formato "TIPO-NNNN" (donde TIPO es TEMP. HUM PRES, y NNNN es un número secuencial de 4 digitos. p.ej. TEMP-0001. HUM-0002). [cite: 22] La estación meteorológica coordina todos los sensores. [cite_start]Debe tener un nombre y una ubicación geográfica (latitud y longitud). [cite: 23] [cite_start]También se debe almacenar la fecha de instalación de cada sensor. [cite: 24] [cite_start]Al añadir un sensor, la estación debe controlar que no exista otro con el mismo identificador. [cite: 25] [cite_start]En ese caso, deberá lanzar excepciones específicas que permitan identificar el error y contengan los objetos en conflicto para su tratamiento. [cite: 26] [cite_start]La funcionalidad principal de la estación meteorológica es orquestar la obtención de datos desde los sensores, permitiendo una lectura simultánea en todos ellos. [cite: 27] [cite_start]Esta lectura se debe poder lanzar de manera puntual o configurarse para que se realice de manera periódica (estableciendo un periodo determinado, y un número máximo de lecturas). [cite: 28] [cite_start]Por último, la estación debe permitir obtener la lista de sensores registrados, recuperar un sensor por su identificador, u obtener todos los sensores de un tipo específico. [cite: 29]

[cite_start]**Se pide:** Usando principios de orientación a objetos, desarrolla todo el código necesario para cumplir con la funcionalidad descrita en este apartado (estación meteorológica y su jerarquía de sensores) con un buen diseño que asegure la mantenibilidad y extensibilidad. [cite: 30] [cite_start]Crea testers (programas con un main, o puedes usar JUnit) que prueben la funcionalidad implementada. [cite: 31] [cite_start]Como ejemplo, la siguiente salida muestra la lista de sensores registrados en una estación (con un sensor de cada tipo) con sus últimas lecturas y fechas de lectura. [cite: 32, 33]

```text
[TEMP-0001 (desde: 2023-09-01): Sensor Temperatura (20.5°C) última lectura: 2026-01-15T10:30:00,
 HUM-0001 (desde: 2024-09-01): Sensor Humedad (65.0%) última lectura: 2026-01-15T10:30:00,
 PRES-0001 (desde: 2025-11-01): Sensor Presión (1013.25 hPa) última lectura: 2026-01-15T10:30:00]
```

---

### [cite_start]Apartado 2. Estrategias de simulación de lectura de sensores (1.5 puntos) [cite: 34]

[cite_start]En este apartado añadiremos soporte para simular distintos escenarios de lectura de los sensores. [cite: 35] [cite_start]Para ello, un sensor podrá parametrizarse con una estrategia de generación de valores simulados de lectura. [cite: 36] Todas las estrategias serán conformes a una misma interfaz, que deberás diseñar. [cite_start]Además, debes crear estrategias para: [cite: 37]

* [cite_start]Generar un valor aleatorio, que estará entre el mínimo y el máximo permitidos, o bien fuera de rango con una probabilidad configurable. [cite: 38]
* [cite_start]Generar un valor aleatorio cercano al valor generado anterior en un rango de X%, con X configurable. [cite: 39]
* [cite_start]Generar un valor aleatorio cercano a la media histórica de todos los valores generados, en un rango de ± X%, con X configurable. [cite: 40]

[cite_start]Cada sensor usará una estrategia por defecto, que se utilizará si no se especifica explícitamente una estrategia al construir un sensor.. Se pide: Usando principios de orientación a objetos, crea el código necesario para implementar la funcionalidad descrita en este apartado, creando testers que prueben esta funcionalidad. [cite: 41] [cite_start]En este apartado, puedes crear además otras estrategias distintas para probar mejor tu diseño, y demostrar su extensibilidad. [cite: 42]

---

### [cite_start]Apartado 3. Procesamiento de datos (2 puntos) [cite: 43]

[cite_start]En este apartado crearemos procesadores de datos asociados a los sensores. [cite: 44] [cite_start]Los procesadores realizan dos funciones: almacenan los datos de lectura en un histórico (con su fecha/hora y valor de lectura), y pueden configurarse con conversores que cambian la unidad de las lecturas obtenidas desde el sensor asociado. [cite: 45] [cite_start]El conversor debe convertir desde la unidad de lectura del sensor, hacia otra unidad permitida para los sensores de ese tipo. [cite: 46] [cite_start]Por ejemplo, para los sensores de temperatura, podemos tener procesadores de datos configurados para convertir valores entre Celsius, Kelvin y Fahrenheit. [cite: 47] [cite_start]Si un procesador se configura con un conversor de Celsius a Kelvin, sólo puede asociarse a un sensor cuya unidad de lectura sea Celsius, y el procesador almacenará la lectura en Kelvin. [cite: 48] [cite_start]Si existe un error en la compatibilidad de las unidades, deben lanzarse excepciones específicas. [cite: 49] [cite_start]Debes realizar un diseño flexible y extensible que permita crear conversores entre distintas unidades, y concatenarlos. [cite: 50] [cite_start]Por ejemplo, si se tiene un conversor de Celsius a Kelvin, y otro de Kelvin a Fahrenheit, se debe poder obtener un conversor de Celsius a Fahrenheit, concatenado los dos conversores previos. [cite: 51] Para unidades de presión debes considerar hPa (HectoPascales), Pa (Pascales) y mbar (milibares). [cite_start]Para humedad relativa, sólo el porcentaje. [cite: 52] 

[cite_start]La estación meteorológica incorporará la lógica necesaria para crear un procesador de datos cada vez que se cree un sensor. [cite: 53] [cite_start]Por defecto, este será un procesador que no realiza conversión entre unidades (o, dicho de otro modo, que esté configurado con un conversor identidad), pero debe ser posible asociar explicitamente un conversor para cada sensor que se añade a la estación. [cite: 54] [cite_start]Finalmente, los procesadores deben soportar obtener estadisticas sobre las lecturas procesadas (minimo, máximo, media). [cite: 55]

[cite_start]**Se pide:** Usando principios de orientación a objetos, desarrolla el código necesario para implementar la funcionalidad descrita en este apartado. [cite: 56] Crea testers que prueben esta funcionalidad. [cite_start]A modo de ejemplo, la siguiente salida es el resultado de imprimir la estación meteorológica, y los procesadores de datos de cada sensor. [cite: 57] [cite_start]El primer sensor tiene un conversor a Kelvin, y cada procesador imprime su minimo, máximo, y media de lecturas. [cite: 58, 59, 60, 61, 62]

```text
Estación meteorológica: Madrid Centro
Ubicación: 40.4168, -3.7038
Sensores instalados: 2
Última lectura: 2026-03-12T19:12:17
TEMP-0001 (°C) con conversor a °K: [293.39, 293.47, 294.03] ΜΙΝ: 293.39 MAX: 294.03 AVG: 293.63
TEMP-0002 (°C): [20.35, 20.47, 20.35] MIN: 20.35 MAX: 20.47 AVG: 20.39
```

---

### [cite_start]Apartado 4. Alertas (2.5 puntos) [cite: 63]

[cite_start]Se debe incorporar al procesamiento de datos un sistema de alertas gestionado mediante excepciones. [cite: 64] [cite_start]Las siguientes situaciones deberán lanzar una excepción: [cite: 65]

* [cite_start]Sensor sin calibrar (lectura fuera de rango o calibración caducada). [cite: 66] [cite_start]La duración de una calibración se establece en el constructor del Sensor o en el método calibrar (con una fecha de fin o una duración en días). [cite: 67] [cite_start]Por defecto, la calibración durará 365 días. [cite: 68]
* [cite_start]Cambios bruscos en las lecturas (p.ej., un cambio de temperatura superior al 50% entre dos lecturas consecutivas). [cite: 69] [cite_start]Se debe permitir configurar el porcentaje de cambio que se considera brusco (por defecto, un 50%). [cite: 70]

[cite_start]La estación meteorológica debe gestionar estas excepciones, por un lado, almacenando un histórico de alertas generadas para su consulta posterior; [cite: 71] [cite_start]por otro lado, debe evitar medir en los sensores sin calibrar, con calibración caducada, o con lectura fuera de los rangos válidos. [cite: 72] [cite_start]En los casos de cambios bruscos, se debe permitir seguir midiendo, pero se debe generar una alerta específica. [cite: 73] [cite_start]La estación meteorológica también debe contar con métodos para calibrar un determinado sensor. [cite: 74] La calibración consiste en establecer un offset de lectura. [cite_start]Además, la calibración de un sensor eliminará las alertas asociadas con dicho sensor y, en los casos en que la toma de datos estaba detenida, deberá retomarse. [cite: 75]

[cite_start]**Se pide:** Usando principios de orientación a objetos, desarrolla el código necesario para implementar la funcionalidad descrita en este apartado. [cite: 76] [cite_start]Crea testers que prueben esta funcionalidad, incluyendo casos de prueba para las excepciones de alerta. [cite: 77] [cite_start]A modo de ejemplo, la siguiente salida es el resultado de imprimir la estación meteorológica, los procesadores de datos de cada sensor, y el sistema de alertas. [cite: 78] [cite_start]Las alertas se han generado desde el tester: (i) forzando un cambio de offset, (ii) leyendo un sensor sin calibrar, y (iii) calibrando con un offset que saca la lectura fuera de rango. [cite: 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89]

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

### [cite_start]Apartado 5. Visualización de datos (1.5 puntos) [cite: 90]

[cite_start]Queremos visualizar los datos de la estación meteorológica de diferentes formas, en particular como documentos en HTML y en formato Markdown. [cite: 91] [cite_start]Tienes una guía de la sintaxis básica de ambos formatos en: [https://www.markdownguide.org/basic-syntax/](https://www.markdownguide.org/basic-syntax/). [cite: 92] [cite_start]Para mayor generalidad y extensibilidad, crearemos una familia de formateadores, que tomarán como entrada no un objeto de tipo estación meteorológica, sino un objeto conforme a una interfaz IDocumento, que debes desarrollar tú. [cite: 93] [cite_start]Debes diseñar métodos para esta interfaz que permitan obtener: [cite: 94]

* [cite_start]El titulo del documento (p.ej., "Estación Meteorológica: Madrid Centro") [cite: 95]
* [cite_start]El título de la sección principal del documento (p.ej., "Madrid Centro") [cite: 96]
* [cite_start]Cada uno de los párrafos de la sección principal del documento (p.ej., 3 párrafos: "Ubicación: 40.4168, -3.7038", "Sensores instalados: 7" y "Última lectura: 2026-01-15T10:30:00) [cite: 97]
* [cite_start]Colecciones de listas que se incluirán a continuación del contenido sección principal, cada una con su título (p.ej., un título "Sensores activos" y la lista de sensores; otro título "Alertas activas: 3" y la lista de alertas). [cite: 98]

[cite_start]Esta interfaz permitirá a los formateadores "ver" un objeto de tipo estación meteorológica como un objeto conforme a I Documento, desacoplando ambos diseños, y haciendo más reutilizables a los formateadores. [cite: 99] [cite_start]Debes procurar un diseño extensible, que permita añadir nuevos tipos de formateadores para objetos de tipo I Documento (p.ej., para JSON, o YAML) fácilmente. [cite: 100]

[cite_start]**Se pide:** Usando principios de orientación a objetos, desarrolla el código necesario que implemente la funcionalidad descrita. [cite: 101] Crea testers que prueben la funcionalidad implementada. [cite_start]A modo de ejemplo, la siguiente salida muestra el formateo de una estación meteorológica con dos sensores y sin alertas. [cite: 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120]

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

[cite_start]De manera similar, el resultado de formatear a Markdown seria: [cite: 121, 122, 123, 124, 125, 126, 127, 128]

```markdown
#Madrid Centro
## Madrid Centro
Ubicacion: -3.7038, 40.4168
Sensores instalados: 2
Última lectura: 2026-03-12T20:42:29
### Sensores activos
TEMP-0001 (°C) con conversor a °K: [293.89, 293.72, 293.34]MIN: 293.34 MAX: 293.89 AVG: 293.65
TEMP-0002 (°C): [20.02, 20.88, 20.33] MIN: 20.02 MAX: 20.88 AVG: 20.41
```

---

### [cite_start]Comentarios adicionales [cite: 129]

[cite_start]No olvides que, además del correcto funcionamiento de la práctica, un aspecto fundamental en la evaluación será la calidad del diseño. [cite: 130] [cite_start]Tu diseño debe utilizar los principios de orientación a objetos, además de ser claro, fácil de entender, extensible y flexible. [cite: 131] [cite_start]Presta atención a la calidad del código, evitando redundancias. [cite: 132] [cite_start]Organiza el código en paquetes. [cite: 133] [cite_start]Crea programas de prueba para ejercitar el código de cada apartado, y entrégalos con tu código. [cite: 134] [cite_start]Puedes usar programas de prueba con un main, o bien usar JUnit. [cite: 135]

### [cite_start]Normas de entrega [cite: 136]

* [cite_start]Se debe entregar el código Java de los apartados, la documentación generada con javadoc, los programas de prueba creados, y una memoria que contenga el diagrama de diseño de toda la práctica (en PDF) junto con una breve explicación y justificación de las decisiones de diseño tomadas. [cite: 137, 138]
* [cite_start]El nombre de los alumnos debe ir en la cabecera javadoc de todas las clases entregadas. [cite: 139]
* [cite_start]La entrega la realizará uno de los alumnos de la pareja a través de Moodle. [cite: 140]
* [cite_start]Se debe entregar un único fichero ZIP RAR con todo lo solicitado, que debe llamarse de la siguiente manera: `GR<numero_grupo>_<nombre_estudiantes>.zip`. [cite: 141] [cite_start]Por ejemplo, Marisa y Pedro, del grupo 2261, entregarían el fichero: `GR2261_MarisaPedro.zip`. [cite: 142, 143]

