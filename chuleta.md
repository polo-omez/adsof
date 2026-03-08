
### ENTORNO VIRTUAL Y PREPARACIÓN GENERAL

* Crear entorno virtual: `python3.11 -m venv env`.
* Activar entorno virtual: `source env/bin/activate`.
* Instalar dependencias dadas en el examen: `pip install -r requirements.txt`.
*(Asegúrate de que este archivo incluya `dj-database-url`, `djangorestframework` y `django-cors-headers`)*.

### TAREAS 1 Y 2: CREACIÓN DEL PROYECTO Y BASE DE DATOS

* Crear proyecto de Django: `django-admin startproject project .`
* Crear aplicación principal: `python3 manage.py startapp application`.
* Borrar base de datos antigua (por si acaso): `dropdb -U alumnodb -h localhost examen`.
* Crear base de datos para el examen: `createdb -U alumnodb -h localhost examen`.

**CONFIGURACIÓN BASE (`project/settings.py`):**

* Registrar la aplicación: En `INSTALLED_APPS`, añadir `'application.apps.ApplicationConfig'`.
* Configurar la BBDD con la cadena del enunciado:

```python
[cite_start]import dj_database_url # [cite: 1546]
# Sustituir la seccion DATABASES por esto:
DATABASES = {
    'default': dj_database_url.config(
        [cite_start]default='postgres://alumnodb:alumnodb@localhost:5432/examen', # [cite: 1548]
        [cite_start]conn_max_age=500 # [cite: 1549]
    )
}

```

### TAREA 7: CREACIÓN DEL API REST CON DJANGO

* Crear aplicación para la API: `python3 manage.py startapp api`.
* En `project/settings.py`, añadir a `INSTALLED_APPS`: `'api.apps.ApiConfig'`, `'rest_framework'` y `'corsheaders'`.
* Añadir a `MIDDLEWARE` (lo más arriba posible): `'corsheaders.middleware.CorsMiddleware'`.
* Añadir al final de `settings.py` para permitir peticiones desde Vue: `CORS_ALLOW_ALL_ORIGINS = True`.

**SERIALIZADORES (`api/serializers.py`):**

* Crear este archivo manualmente. Convierte los modelos en formato JSON.

```python
from rest_framework import serializers
from application.models import Usuario, Canal, Suscripcion # Importar modelos reales

class UsuarioSerializer(serializers.ModelSerializer):
    [cite_start]class Meta: # [cite: 1526]
        [cite_start]model = Usuario # [cite: 1720]
        [cite_start]fields = '__all__' # Coge todos los atributos [cite: 1722]

# Repetir exactamente igual creando CanalSerializer y SuscripcionSerializer

```

**VISTAS DE LA API (`api/views.py`):**

* Usaremos ModelViewSet para que genere GET, POST, PUT y DELETE automáticamente.

```python
from rest_framework import viewsets
from application.models import Usuario, Canal, Suscripcion
from .serializers import UsuarioSerializer, CanalSerializer, SuscripcionSerializer

class UsuarioViewSet(viewsets.ModelViewSet):
    [cite_start]queryset = Usuario.objects.all() # Obtener todos los registros [cite: 1535]
    serializer_class = UsuarioSerializer

# Repetir exactamente igual creando CanalViewSet y SuscripcionViewSet

```

**URLS DE LA API (`project/urls.py`):**

* Conectar los ViewSets a la ruta `/api/v1/` que pide el enunciado.

```python
from django.contrib import admin
[cite_start]from django.urls import path, include # [cite: 1509]
from rest_framework.routers import DefaultRouter
from api import views

# Registrar rutas del API
router = DefaultRouter()
router.register(r'usuarios', views.UsuarioViewSet)
router.register(r'canales', views.CanalViewSet)
router.register(r'suscripciones', views.SuscripcionViewSet)

urlpatterns = [
    [cite_start]path('admin/', admin.site.urls), # [cite: 1514]
    path('application/', include('application.urls')), # Rutas normales de la app 
    path('api/v1/', include(router.urls)), # Rutas del API REST
]

```

### TAREA 8: PROYECTO VUE.JS (FRONTEND)

* Crear proyecto Vue en otra terminal: `npm create vue@latest project_vue`.
* Responder SÍ a incluir Cypress para testing.
* Entrar a la carpeta: `cd project_vue` y ejecutar `npm install`.

**COMPONENTE PRINCIPAL (`src/App.vue`):**

* Estructura básica para consumir el API REST (Listar, Añadir y Borrar).

```html
<script setup>
import { ref, onMounted } from 'vue'

// Variables reactivas
const usuarios = ref([])
const nuevoUsuario = ref({ id: '', nombreUsuario: '' }) // Adaptar a tu modelo

// GET: Obtener datos
const fetchUsuarios = async () => {
  const response = await fetch('http://localhost:8000/api/v1/usuarios/')
  usuarios.value = await response.json()
}

// POST: Crear datos
const addUsuario = async () => {
  await fetch('http://localhost:8000/api/v1/usuarios/', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(nuevoUsuario.value)
  })
  nuevoUsuario.value = { id: '', nombreUsuario: '' } // Limpiar formulario
  fetchUsuarios() // Actualizar lista
}

// DELETE: Borrar datos
const deleteUsuario = async (id) => {
  await fetch(`http://localhost:8000/api/v1/usuarios/${id}/`, { method: 'DELETE' })
  fetchUsuarios() // Actualizar lista
}

// Cargar al inicio
onMounted(() => {
  fetchUsuarios()
  // Añadir fetchCanales() y fetchSuscripciones() aquí
})
</script>

<template>
  <main>
    <h1>Gestión de Usuarios</h1>
    
    <input v-model="nuevoUsuario.id" placeholder="ID" id="input-id">
    <input v-model="nuevoUsuario.nombreUsuario" placeholder="Nombre" id="input-nombre">
    <button @click="addUsuario" id="btn-add">Añadir Usuario</button>

    <table>
      <tr v-for="usuario in usuarios" :key="usuario.id">
        <td>{{ usuario.id }}</td>
        <td>{{ usuario.nombreUsuario }}</td>
        <td><button @click="deleteUsuario(usuario.id)" class="btn-delete">Eliminar</button></td>
      </tr>
    </table>
  </main>
</template>

```

### TAREA 9: TESTS EN DJANGO

* El código debe ir dentro de la carpeta de la aplicación en el archivo `application/tests.py`.
* Se ejecutan con: `python3 manage.py test application.tests`.
* Estructura basada en `TestCase`.

**ESTRUCTURA DEL TEST (`application/tests.py`):**

```python
from django.test import TestCase
from application.models import Usuario, Canal, Suscripcion # Importar modelos

[cite_start]class ExamenViewsTest(TestCase): # [cite: 1792]
    
    [cite_start]def setUp(self): # [cite: 1796] Se ejecuta antes de cada test
        # 1. Borrar todo (aunque Django limpia BD de test, el enunciado lo pide explícito)
        Suscripcion.objects.all().delete()
        Canal.objects.all().delete()
        Usuario.objects.all().delete()
        
        # 2. Crear datos específicos del enunciado
        [cite_start]self.u1 = Usuario.objects.create(id=1001, nombreUsuario='jordi') # [cite: 1767]
        self.u2 = Usuario.objects.create(id=1002, nombreUsuario='nacho')
        self.c1 = Canal.objects.create(id=1001, nombreCanal='wildproject')
        
        Suscripcion.objects.create(id=1001, canal=self.c1, usuario=self.u1, fechaDeSuscripcion='2025-03-08')
        Suscripcion.objects.create(id=1002, canal=self.c1, usuario=self.u2, fechaDeSuscripcion='2025-01-07')

    [cite_start]def test_vista_suscripciones_canal(self): # [cite: 1770]
        # 3. Acceder a la vista
        response = self.client.get('/application/canal/1001')
        
        # [cite_start]4. Comprobaciones [cite: 1789, 1791]
        self.assertEqual(response.status_code, 200) # Si la página existe
        self.assertContains(response, 'jordi') # Comprueba que el HTML incluye el dato
        self.assertContains(response, 'nacho')

```

### TAREA 10: TESTS EN VUE CON CYPRESS

* Se crea un archivo en el proyecto Vue: `project_vue/cypress/e2e/test_vue.cy.js`.
* Se ejecuta con: `npx cypress run` o `npx cypress open` para interfaz gráfica.
* Importante: Tanto el servidor de Django (`runserver`) como el de Vue (`npm run dev`) deben estar encendidos en paralelo.

**CÓDIGO CYPRESS (`test_vue.cy.js`):**

```javascript
describe('Test CRUD de Usuarios en Vue', () => {
  it('Añade y elimina usuarios correctamente', () => {
    // 0. Visitar la web de Vue (asegurar el puerto correcto)
    cy.visit('http://localhost:5173')

    // 1. Crear usuario 'jordi' (usar los IDs definidos en el HTML)
    cy.get('#input-id').type('1001')
    cy.get('#input-nombre').type('jordi')
    cy.get('#btn-add').click()

    // 2. Crear usuario 'nacho'
    cy.get('#input-id').clear().type('1002')
    cy.get('#input-nombre').clear().type('nacho')
    cy.get('#btn-add').click()

    // 3. Comprobar que se han renderizado en la tabla
    cy.contains('jordi').should('exist')
    cy.contains('nacho').should('exist')

    // 4. Eliminar el usuario de nombre 'nacho'
    // Busca la fila (tr) que contiene 'nacho' y hace click en su botón
    cy.contains('tr', 'nacho').find('.btn-delete').click()

    // 5. Comprobar que ya no está en la tabla
    cy.contains('nacho').should('not.exist')
  })
})

```

### COMANDOS FINALES DE CALIFICACIÓN

Antes de entregar, debes ejecutar estos comandos en orden para asegurar que el evaluador puede probar tu código perfectamente:

1. `dropdb -U alumnodb -h localhost examen` (Borra BD actual).
2. `createdb -U alumnodb -h localhost examen` (Crea BD limpia).
3. `python3 manage.py makemigrations` (Detecta cambios en Modelos).
4. `python3 manage.py migrate` (Crea las tablas reales en PostgreSQL).
5. `python3 manage.py createsuperuser` (Crear usuario `alumnodb`, pass `alumnodb`).
6. `python3 populate_models.py` (Ejecutar el script que puebla con los datos del enunciado).

*Recordatorio: Para que Django detecte el script `populate_models.py` desde la raíz, este script debe contener el bloque `os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'project.settings')` y `django.setup()` al principio.*


