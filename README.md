# MatchMaster - Gestor de Ligas FIFA

## Descripción
MatchMaster es una aplicación Android desarrollada en Java para gestionar ligas y torneos de FIFA. La aplicación permite a los usuarios crear, organizar y administrar competiciones de fútbol virtual.

## Características Actuales
- **Pantalla Principal**: Interfaz limpia y moderna con dos secciones principales:
  - **Mis Ligas**: Muestra las ligas creadas por el usuario
  - **Mis Torneos**: Muestra los torneos creados por el usuario
- **Estados Vacíos**: Mensajes informativos cuando no hay ligas o torneos creados
- **Botones de Acción**: Botones para crear nuevas ligas y torneos
- **Diseño Responsivo**: Interfaz adaptada para diferentes tamaños de pantalla

## Estructura del Proyecto

### Clases Java
- `MainActivity.java`: Actividad principal de la aplicación
- `Liga.java`: Modelo de datos para representar una liga
- `Torneo.java`: Modelo de datos para representar un torneo
- `LigaAdapter.java`: Adaptador para mostrar ligas en RecyclerView
- `TorneoAdapter.java`: Adaptador para mostrar torneos en RecyclerView

### Layouts XML
- `activity_main.xml`: Layout principal de la aplicación
- `item_liga.xml`: Layout para cada item de liga en la lista
- `item_torneo.xml`: Layout para cada item de torneo en la lista

### Drawables
- `bg_empty_state.xml`: Fondo para estados vacíos
- `bg_button_primary.xml`: Estilo para botón primario
- `bg_button_secondary.xml`: Estilo para botón secundario
- `bg_tag.xml`: Estilo para etiquetas de información

## Tecnologías Utilizadas
- **Java**: Lenguaje de programación principal
- **Android SDK**: Framework de desarrollo móvil
- **RecyclerView**: Para mostrar listas de ligas y torneos
- **CardView**: Para crear tarjetas visuales atractivas
- **Material Design**: Principios de diseño moderno

## Configuración del Proyecto
1. Abrir el proyecto en Android Studio
2. Sincronizar las dependencias de Gradle
3. Ejecutar la aplicación en un emulador o dispositivo físico

## Versión
- Versión actual: 1.0
- SDK mínimo: 24 (Android 7.0)
- SDK objetivo: 36 (Android 14) 
