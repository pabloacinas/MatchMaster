# Match Master

**Match Master** es una aplicación Android desarrollada en Java que permite simular ligas de fútbol entre dos jugadores, pensada para organizar torneos de videojuegos de fútbol. Actualmente, la opción de torneos no está disponible, pero la funcionalidad de ligas está operativa.

## Características principales

- **Gestión de ligas personalizadas**: Crea ligas eligiendo nombre, número de equipos (par obligatorio), nombres de los jugadores y formato (ida o ida y vuelta).
- **Selección de equipos**: Cada jugador elige sus equipos, asegurando que siempre se enfrenten equipos del usuario 1 contra los del usuario 2.
- **Simulación de partidos**: Introduce resultados, goleadores y gestiona traspasos de jugadores tras cada partido.
- **Clasificación automática**: Visualiza la tabla de posiciones, puntos, victorias, derrotas y diferencia de goles.
- **Historial de traspasos**: Consulta todos los traspasos realizados, indicando la jornada, equipo antiguo y nuevo equipo.
- **Tabla de goleadores**: Revisa el ranking de goleadores ordenado de mayor a menor.

## Pantallas principales

1. **Clasificación**  
  Muestra la tabla de posiciones con puntos, victorias, derrotas y diferencia de goles según la normativa estándar de ligas de fútbol.

2. **Jornadas**  
  Visualiza los enfrentamientos de cada jornada. Al seleccionar un partido, puedes introducir el resultado, los goleadores y gestionar el traspaso de jugadores (el equipo ganador puede quitarle un jugador al rival; en caso de empate no hay traspaso).

3. **Traspasos**  
  Resumen de todos los traspasos realizados, indicando la jornada, el equipo antiguo y el nuevo equipo.

4. **Goleadores**  
  Tabla con los máximos goleadores de la liga, ordenados de mayor a menor.

## Cómo empezar

1. Clona el repositorio:
  ```sh
  git clone https://github.com/pabloacinas/MatchMaster.git
  ```
2. Abre el proyecto en Android Studio.
3. Compila y ejecuta la aplicación en tu dispositivo o emulador Android.

## Requisitos

- Android Studio
- Java (versión compatible con Android)
- Dispositivo o emulador Android

## Estado del proyecto

- **Ligas**: Funcional, pero hay un problema con la pantalla de goleadores: cuando se cierra la aplicación y se vuelve a abrir, los datos de los goleadores se pierden.
- **Torneos**: No disponible actualmente
