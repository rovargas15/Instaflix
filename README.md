# Instaflix

Instaflix es una prueba para Instaleap que presenta un listado de películas y series, divididas en tres categorías cada una.

A continuación se detallan las especificaciones técnicas del proyecto.

## Prerrequisitos

Antes de ejecutar el proyecto, asegúrate de configurar tu entorno adecuadamente. Tienes dos opciones para hacerlo:

### Opción 1: Uso del script `SecretCreate.sh`
1. Clona el repositorio.
2. Después de clonar el repositorio, localiza el archivo **SecretCreate.sh** en la raíz del proyecto.
3. Ábrelo y sigue las instrucciones para agregar tu `API_KEY` de [The Movie Database (TMDB)](https://developers.themoviedb.org/3/getting-started/introduction).
4. Al ejecutar el script, se generará un archivo `secrets.properties` que contendrá la `API_KEY` requerida por el módulo `data` en Gradle.

### Opción 2: Creación manual de `secrets.properties`
1. Clona el repositorio.
2. Crea un archivo `secrets.properties` en la raíz del proyecto.
3. Añade la siguiente línea al archivo, reemplazando `TU_API_KEY` con tu clave API obtenida de [The Movie Database (TMDB)](https://developers.themoviedb.org/3/getting-started/introduction):

   ```properties 
   API_KEY=TU_API_KEY

Una vez configurado el archivo `secrets.properties` con la `API_KEY`, sincroniza el proyecto y ejecuta la compilación.

## Tecnologías

Este proyecto fue escrito puramente con Jetpack Compose :), este es el nuevo framework declarativo
para crear Ui, y de ahora en adelante el que tiene la responsabilidad de convertirse en la opción
predeterminada para este propósito.

## Arquitectura

El proyecto sigue el enfoque de Clean Architecture, garantizando alta cohesión y bajo acoplamiento mediante la separación en capas:

- **Capa de Domain**: Contiene las especificaciones del negocio.
- **Capa de Data**: Agrupa los componentes asociados a diferentes fuentes de datos, como mappers, modelos de respuesta, entidades y repositorios.
- **Capa de Presentation**: Contiene los módulos de funcionalidad (`movie`, `tv`, `favorite`) y el NavigatorController, que gestiona el flujo de la aplicación.
- **Feature:**(`movie`, `tv`, `favorite`) Contiene las funcionalidades de la app.

## Inyección de Dependencias

El proyecto utiliza la *Inyección de Dependencias (DI)* para facilitar la separación de capas, siguiendo el principio de inversión de dependencias. Esto permite evitar que la capa de dominio tenga referencias directas a la capa de datos, haciendo que el código sea más fácil de testear y mantener. El framework utilizado para DI es *Hilt*, que se basa en Dagger para construir el árbol de dependencias de manera eficiente.

## Pruebas Unitarias

Se han implementado pruebas unitarias en los viewModels, casos de uso y repositorios, ya que en estos componentes se concentra la mayor parte de la lógica del proyecto.

## API

- **URL base**: `https://api.themoviedb.org/3`
- **Servicios disponibles**:
    - Listado de películas: `GET /movie/{popular}`
    - Listado de series: `GET /tv/{popular}`
    - Imágenes: `GET /{id}/{path}/images?language=es`

## Integración Continua (CI) con GitHub Actions

Se ha integrado un pipeline de CI utilizando GitHub Actions para validar el proyecto de manera automática. Este pipeline realiza las siguientes tareas:

- **Ejecuta pruebas unitarias**: Verifica que todas las pruebas pasen correctamente.
- **Valida el código con Lint**: Asegura que el código cumple con las reglas de estilo definidas.
- **Ejecuta Detekt**: Realiza un análisis estático del código para identificar posibles errores y mantener la calidad.
- **Verifica la cobertura del código con JaCoCo**: Genera un reporte de cobertura de pruebas y asegura que se cumplen los umbrales definidos.
- **Genera una APK de debug**: Al final del pipeline, se genera una APK en modo debug y se sube como un artefacto en GitHub, lo que permite su descarga y prueba directa desde la plataforma.


## Librerías Utilizadas

### Jetpack Compose
- [Material Design](https://material.io/blog/jetpack-compose)
- [UI Tooling](https://developer.android.com/jetpack/compose/layouts/material?hl=es-419)
- [Activity Compose](https://developer.android.com/jetpack/compose/layouts/material?hl=es-419)
- [ViewModel Compose](https://developer.android.com/jetpack/compose/layouts/material?hl=es-419)
- [Compose UI Test](https://developer.android.com/jetpack/compose/testing?hl=es-419)
- [Navigation](https://developer.android.com/jetpack/compose/navigation?hl=es-419)

### Test
- [Mockk](https://github.com/mirtizakh/Android-Mockk)
- [Compose Test](https://developer.android.com/jetpack/compose/testing?hl=es-419)

### Otras
- [Coroutines Core](https://github.com/Kotlin/kotlinx.coroutines)
- [Room](https://developer.android.com/training/data-storage/room)
- [Lifecycle ViewModel](https://github.com/androidx/androidx)
- [ktor](https://ktor.io/docs/client-create-new-application.html#create-client)
- [Hilt](https://github.com/googlecodelabs/android-hilt)