
### ENTORNO VIRTUAL Y PREPARACIÓN GENERAL

* Crear entorno virtual: `python3.11 -m venv env`.
* Activar entorno virtual: `source env/bin/activate`.
* Instalar dependencias del proyecto: `pip install -r requirements.txt`.
*(Asegúrate de que este archivo incluya `dj-database-url`, `djangorestframework` y `django-cors-headers`)*.

### CREACIÓN DEL PROYECTO Y BASE DE DATOS

* Crear proyecto de Django: `django-admin startproject nombre_proyecto .`
* Crear aplicación principal: `python3 manage.py startapp nombre_app`.
* Borrar base de datos antigua (por limpieza): `dropdb -U usuario_bd -h localhost nombre_bd`.
* Crear la base de datos principal: `createdb -U usuario_bd -h localhost nombre_bd`.

**CONFIGURACIÓN BASE (`nombre_proyecto/settings.py`):**

* Registrar la aplicación: En `INSTALLED_APPS`, añadir `'nombre_app.apps.NombreAppConfig'`.
* Configurar la BBDD con la cadena de conexión:

```python
import dj_database_url 

# Sustituir la seccion DATABASES por esto:
DATABASES = {
    'default': dj_database_url.config(
        default='postgres://usuario_bd:password_bd@localhost:5432/nombre_bd', 
        conn_max_age=500 
    )
}

```

### MODELOS DE DATOS Y PANEL DE ADMINISTRACIÓN

* Definir la estructura de la base de datos creando clases en el archivo de modelos.
* Registrar estos modelos para poder gestionarlos cómodamente desde la URL `/admin/`.

**CREACIÓN DE MODELOS (`nombre_app/models.py`):**

```python
from django.db import models

class ModeloA(models.Model):
    id = models.IntegerField(primary_key=True)
    campo_ejemplo = models.CharField(max_length=100)

    def __str__(self):
        return self.campo_ejemplo

class ModeloB(models.Model):
    id = models.IntegerField(primary_key=True)
    clave_foranea = models.ForeignKey(ModeloA, on_delete=models.CASCADE)
    fecha_creacion = models.DateField(auto_now_add=True)

    def __str__(self):
        return f"Registro B - {self.id}"

```

**REGISTRO EN ADMINISTRACIÓN (`nombre_app/admin.py`):**

```python
from django.contrib import admin
from .models import ModeloA, ModeloB

# Registrar modelos básicos
admin.site.register(ModeloA)
admin.site.register(ModeloB)

```

### CREACIÓN DEL API REST CON DJANGO

* Crear aplicación para la API: `python3 manage.py startapp api`.
* En `nombre_proyecto/settings.py`, añadir a `INSTALLED_APPS`: `'api.apps.ApiConfig'`, `'rest_framework'` y `'corsheaders'`.
* Añadir a `MIDDLEWARE` (lo más arriba posible): `'corsheaders.middleware.CorsMiddleware'`.
* Añadir al final de `settings.py` para permitir peticiones externas (CORS): `CORS_ALLOW_ALL_ORIGINS = True`.

**SERIALIZADORES (`api/serializers.py`):**

* Crear este archivo manualmente. Convierte los modelos de Python en formato JSON.

```python
from rest_framework import serializers
from nombre_app.models import ModeloA, ModeloB # Importar los modelos creados en el Paso 3

class ModeloASerializer(serializers.ModelSerializer):
    class Meta: 
        model = ModeloA 
        fields = '__all__' # Expone todos los atributos del modelo

# Repetir la estructura para crear ModeloBSerializer

```

**VISTAS DE LA API (`api/views.py`):**

* Usaremos `ModelViewSet` para que genere los métodos GET, POST, PUT y DELETE de forma automática.

```python
from rest_framework import viewsets
from nombre_app.models import ModeloA, ModeloB
from .serializers import ModeloASerializer, ModeloBSerializer

class ModeloAViewSet(viewsets.ModelViewSet):
    queryset = ModeloA.objects.all() # Obtener todos los registros disponibles
    serializer_class = ModeloASerializer

# Repetir la estructura para crear ModeloBViewSet

```

**URLS DE LA API (`nombre_proyecto/urls.py`):**

* Conectar los ViewSets a la ruta de la API requerida por el proyecto (ej. `/api/v1/`).

```python
from django.contrib import admin
from django.urls import path, include 
from rest_framework.routers import DefaultRouter
from api import views

# Configurar el router del API REST
router = DefaultRouter()
router.register(r'endpoint_modelo_a', views.ModeloAViewSet)
router.register(r'endpoint_modelo_b', views.ModeloBViewSet)

urlpatterns = [
    path('admin/', admin.site.urls), # Acceso al panel de administración del Paso 3
    path('nombre_app/', include('nombre_app.urls')), # Rutas del frontend de Django si las hubiera
    path('api/v1/', include(router.urls)), # Rutas del API REST
]

```

### PROYECTO VUE.JS (FRONTEND)

* Crear proyecto Vue en otra terminal: `npm create vue@latest nombre_proyecto_vue`.
* Responder SÍ a incluir Cypress para testing E2E.
* Entrar a la carpeta: `cd nombre_proyecto_vue` y ejecutar `npm install`.

**COMPONENTE PRINCIPAL (`src/App.vue`):**

* Estructura básica para consumir el API REST (Listar, Añadir y Borrar).

```html
<script setup>
import { ref, onMounted } from 'vue'

// Variables reactivas para el estado del componente
const registros = ref([])
const nuevoRegistro = ref({ id: '', campo_ejemplo: '' }) 

// GET: Obtener lista de datos
const fetchRegistros = async () => {
  const response = await fetch('http://localhost:8000/api/v1/endpoint_modelo_a/')
  registros.value = await response.json()
}

// POST: Crear nuevo dato
const addRegistro = async () => {
  await fetch('http://localhost:8000/api/v1/endpoint_modelo_a/', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(nuevoRegistro.value)
  })
  nuevoRegistro.value = { id: '', campo_ejemplo: '' } // Limpiar formulario tras el envío
  fetchRegistros() // Refrescar lista
}

// DELETE: Borrar un dato existente
const deleteRegistro = async (id) => {
  await fetch(`http://localhost:8000/api/v1/endpoint_modelo_a/${id}/`, { method: 'DELETE' })
  fetchRegistros() // Refrescar lista
}

// Ejecutar carga inicial
onMounted(() => {
  fetchRegistros()
})
</script>

<template>
  <main>
    <h1>Gestión de Registros</h1>
    
    <input v-model="nuevoRegistro.id" placeholder="ID" id="input-id">
    <input v-model="nuevoRegistro.campo_ejemplo" placeholder="Campo de texto" id="input-campo">
    <button @click="addRegistro" id="btn-add">Añadir Registro</button>

    <table>
      <tr v-for="registro in registros" :key="registro.id">
        <td>{{ registro.id }}</td>
        <td>{{ registro.campo_ejemplo }}</td>
        <td><button @click="deleteRegistro(registro.id)" class="btn-delete">Eliminar</button></td>
      </tr>
    </table>
  </main>
</template>

```

### TESTS EN DJANGO (BACKEND)

* El código debe ir dentro de la carpeta de la aplicación principal, en el archivo `nombre_app/tests.py`.
* Se ejecutan con: `python3 manage.py test nombre_app.tests`.

**ESTRUCTURA DEL TEST (`nombre_app/tests.py`):**

```python
from django.test import TestCase
from nombre_app.models import ModeloA, ModeloB # Importar modelos

class IntegracionViewsTest(TestCase): 
    
    def setUp(self): # Se ejecuta antes de lanzar cada test para preparar el entorno
        # 1. Limpiar base de datos (buenas prácticas de testing)
        ModeloB.objects.all().delete()
        ModeloA.objects.all().delete()
        
        # 2. Inyectar datos semilla (mock data)
        self.m1 = ModeloA.objects.create(id=1, campo_ejemplo='valor_prueba_1') 
        self.m2 = ModeloA.objects.create(id=2, campo_ejemplo='valor_prueba_2')
        
        ModeloB.objects.create(id=1, clave_foranea=self.m1, fecha_creacion='2025-03-08')

    def test_vista_renderiza_datos_correctamente(self): 
        # 3. Simular petición HTTP a la vista
        response = self.client.get('/nombre_app/ruta_especifica/1')
        
        # 4. Aserciones (Comprobaciones)
        self.assertEqual(response.status_code, 200) # Verifica que devuelve un HTTP 200 OK
        self.assertContains(response, 'valor_prueba_1') # Verifica el contenido del HTML
        self.assertContains(response, 'valor_prueba_2')

```

### TESTS EN VUE CON CYPRESS (FRONTEND)

* Crear un archivo de especificaciones E2E: `nombre_proyecto_vue/cypress/e2e/test_frontend.cy.js`.
* Se ejecuta con: `npx cypress run` (consola) o `npx cypress open` (interfaz gráfica).
* *Aviso: Para que el test pase, tanto el backend (`runserver`) como el frontend (`npm run dev`) deben estar corriendo simultáneamente.*

**CÓDIGO CYPRESS (`test_frontend.cy.js`):**

```javascript
describe('Test Flujo CRUD en Interfaz de Usuario', () => {
  it('Debe permitir añadir y eliminar registros desde el DOM', () => {
    // 0. Navegar a la aplicación (verificar el puerto en Vite/Vue)
    cy.visit('http://localhost:5173')

    // 1. Simular la creación de un primer registro
    cy.get('#input-id').type('1')
    cy.get('#input-campo').type('valor_prueba_1')
    cy.get('#btn-add').click()

    // 2. Simular la creación de un segundo registro
    cy.get('#input-id').clear().type('2')
    cy.get('#input-campo').clear().type('valor_prueba_2')
    cy.get('#btn-add').click()

    // 3. Verificar reactividad: los datos deben estar en el DOM
    cy.contains('valor_prueba_1').should('exist')
    cy.contains('valor_prueba_2').should('exist')

    // 4. Simular borrado: localizar la fila de 'valor_prueba_2' y hacer click en su botón
    cy.contains('tr', 'valor_prueba_2').find('.btn-delete').click()

    // 5. Verificar que el elemento ha desaparecido del DOM
    cy.contains('valor_prueba_2').should('not.exist')
  })
})

```

### PASO 8: EJECUCIÓN Y COMPROBACIÓN FINAL

Para asegurar un despliegue limpio y funcional, y garantizar que la base de datos está correctamente sincronizada con los modelos, ejecuta esta secuencia de comandos:

1. `dropdb -U usuario_bd -h localhost nombre_bd` (Destruye la BD actual para empezar en limpio).
2. `createdb -U usuario_bd -h localhost nombre_bd` (Crea una BD nueva y vacía).
3. `python3 manage.py makemigrations` (Detecta y empaqueta los cambios en los modelos del Paso 3).
4. `python3 manage.py migrate` (Aplica los cambios, creando las tablas reales en PostgreSQL).
5. `python3 manage.py createsuperuser` (Crea el usuario administrador para poder acceder a `http://localhost:8000/admin`).
6. `python3 script_poblacion.py` (Opcional: Ejecutar un script para cargar datos iniciales si el proyecto lo requiere).

Para usar un script externo de población de datos (test en django), recuerda que el archivo debe incluir la configuración inicial del entorno de Django (`os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'nombre_proyecto.settings')` y `django.setup()`) en sus primeras líneas.*
