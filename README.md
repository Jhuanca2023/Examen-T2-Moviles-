# Padrón de Personas - Evaluación T2

Este proyecto es una aplicación Android desarrollada como parte de la Evaluación T2 del curso **Desarrollo de Aplicaciones Móviles I**. Permite registrar, listar, editar y eliminar personas en un padrón, usando una base de datos local.

## Tecnologías utilizadas
- **Android (Kotlin)**
- **Jetpack Compose** (UI declarativa)
- **Room** (persistencia local)
- **SQLite** (base de datos)
- **Material 3** (diseño visual)

## Estructura del proyecto

```
Examen T2/
├── app/
│   ├── build.gradle.kts
│   ├── proguard-rules.pro
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   ├── java/
│       │   │   └── com/example/exament2/
│       │   │       ├── MainActivity.kt
│       │   │       ├── PadronDatabase.kt
│       │   │       ├── PantallaListadoPersonas.kt
│       │   │       ├── PantallaRegistroPersona.kt
│       │   │       ├── Persona.kt
│       │   │       ├── PersonaDao.kt
│       │   │       ├── PersonaViewModel.kt
│       │   │       └── ui/theme/
│       │   │           ├── Color.kt
│       │   │           ├── Theme.kt
│       │   │           └── Type.kt
│       │   └── res/
│       │       └── ... (recursos gráficos y layouts)
│       └── test/
│           └── java/com/example/exament2/ExampleUnitTest.kt
├── build.gradle.kts
├── gradle/
│   ├── libs.versions.toml
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradle.properties
├── gradlew
├── gradlew.bat
├── settings.gradle.kts
```

## Capturas de pantalla

### Listado de Personas
![Listado de Personas](./captura_listado.png)

### Registro/Edición de Persona
![Registro de Persona](./captura_registro.png)

### Detalle de Persona
![Detalle de Persona](./captura_detalle.png)

> **Nota:** Las imágenes corresponden a las capturas proporcionadas durante el desarrollo.

## Instrucciones de compilación y ejecución
1. Clona el repositorio o descarga el código fuente.
2. Abre el proyecto en **Android Studio**.
3. Sincroniza el proyecto con Gradle.
4. Ejecuta la app en un emulador o dispositivo físico con Android 8.0+.

## Autor
- **Jose Huanca Otnianco**
- Proyecto académico para la Evaluación T2
- Contacto: [tu-email@ejemplo.com]

---

> Este proyecto es de uso académico y fue desarrollado íntegramente por Jose Huanca Otnianco para fines de evaluación universitaria. 