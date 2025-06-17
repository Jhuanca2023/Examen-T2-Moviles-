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
![image](https://github.com/user-attachments/assets/8c7a761e-3fbe-4e9f-a94e-23b34053427e)


### Registro/Edición de Persona
![image](https://github.com/user-attachments/assets/59f76819-261c-42c5-a4de-8e94cf9b000f)
![image](https://github.com/user-attachments/assets/aee5570a-cf3d-4cae-acd6-cd1bc1743194)

![image](https://github.com/user-attachments/assets/b5dc81ec-5cd0-4901-8757-a4b869697329)





### Detalle de Persona
![image](https://github.com/user-attachments/assets/8de0db43-0b8c-4347-9b7b-4c03fc3b6ef9)


> **Nota:** Las imágenes corresponden a las capturas proporcionadas durante el desarrollo.

## Instrucciones de compilación y ejecución
1. Clona el repositorio o descarga el código fuente.
   
    https://github.com/Jhuanca2023/Examen-T2-Moviles-.git

3. Abre el proyecto en **Android Studio**.
4. Sincroniza el proyecto con Gradle.
5. Ejecuta la app en un emulador o dispositivo físico con Android 8.0+.

## Autor
- **Jose Huanca Otnianco**
- Proyecto académico para la Evaluación T2
- Contacto: [josehuanca612@gmail.com]

---

> Este proyecto es de uso académico y fue desarrollado íntegramente por Jose Huanca Otnianco para fines de evaluación universitaria. 
