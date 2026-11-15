# 💣 BUSCAMINAS UTN 💣

<!-- local-development:start -->
## Desarrollo local

Consulta [RUNNING.md](RUNNING.md) para ubicar cada componente, abrirlo en su IDE, compilarlo y conocer las comprobaciones realizadas y los requisitos pendientes.
<!-- local-development:end -->

<div align="center">

![Buscaminas Banner](https://img.shields.io/badge/💥_BUSCAMINAS-JAVA-orange?style=for-the-badge&logo=java)
![Status](https://img.shields.io/badge/STATUS-🚀_ACTIVO-brightgreen?style=for-the-badge)
![Version](https://img.shields.io/badge/VERSION-1.0-blue?style=for-the-badge)

**🎯 Proyecto Final - Programación I**  
**Universidad Técnica Nacional (UTN) - ITI**

*Un emocionante juego de Buscaminas desarrollado en Java con interfaz gráfica*

---

</div>

## 👥 Colaboradores

<table align="center">
  <tr>
    <td align="center">
      <img src="https://img.shields.io/badge/👨‍💻-DEVELOPER-blue?style=for-the-badge" alt="Developer 1"/><br>
      <b>🔥 Jairo Herrera Romero</b><br>
      <i>Java Developer</i>
    </td>
    <td align="center">
      <img src="https://img.shields.io/badge/👨‍💻-DEVELOPER-green?style=for-the-badge" alt="Developer 2"/><br>
      <b>⚡ Emesis Mairena Sevilla</b><br>
      <i>Java Developer</i>
    </td>
  </tr>
</table>

---

## 🎮 ¿Qué es Buscaminas UTN?

¡Prepárate para la aventura más explosiva! 💥 **Buscaminas UTN** es una implementación moderna del clásico juego de Windows, desarrollado completamente en Java con una interfaz gráfica intuitiva y emocionante.

### 🌟 Características Principales

- 🎯 **Tablero Personalizable**: Define el tamaño de tu campo de batalla
- 💣 **Minas Aleatorias**: Cada partida es única y desafiante
- 🚩 **Sistema de Marcado**: Marca las minas sospechosas
- 🔢 **Contador Inteligente**: Ve cuántas minas rodean cada casilla
- 📊 **Estadísticas**: Lleva el control de tus victorias y derrotas
- 🎨 **Interfaz Gráfica**: Diseño limpio y fácil de usar

---

## 🚀 Instalación y Ejecución

### 📋 Requisitos Previos

- ☕ **Java JDK 8+**
- 🛠️ **NetBeans IDE** (recomendado)
- 🖥️ **Sistema Operativo**: Windows/Linux/MacOS

### 🔧 Pasos de Instalación

1. **Clona el repositorio**
   ```bash
   git clone https://github.com/TheJPlay2006/Buscaminas.git
   cd Buscaminas
   ```

2. **Abre en NetBeans**
   - Abre NetBeans IDE
   - Selecciona "Open Project"
   - Navega hasta la carpeta del proyecto

3. **¡Ejecuta y Juega!** 🎮
   - Presiona F6 o click en "Run Project"
   - ¡Disfruta del juego!

---

## 🎯 Cómo Jugar

### 🕹️ Controles Básicos

| Acción | Descripción | Emoji |
|--------|-------------|-------|
| **Click Izquierdo** | Destapar casilla | 🔍 |
| **Click Derecho** | Marcar/Desmarcar mina | 🚩 |
| **Doble Click** | Destapar casillas adyacentes | ⚡ |

### 🎲 Reglas del Juego

1. 🎯 **Objetivo**: Encuentra todas las minas sin detonarlas
2. 🔢 **Números**: Indican cuántas minas hay alrededor
3. 🚩 **Banderas**: Marca las casillas que crees que tienen minas
4. 💥 **¡Cuidado!**: Si destapas una mina, ¡BOOM! Pierdes
5. 🏆 **Victoria**: Marca correctamente todas las minas

### 📐 Configuración del Tablero

- **Tamaño mínimo**: 3x3 casillas
- **Minas generadas**: 2 × L (donde L = lado del tablero)
- **Distribución**: Completamente aleatoria

---

## 🏗️ Arquitectura del Proyecto

### 📊 Diagrama de Clases (UML)

```
🎮 Buscaminas
├── 📦 Logica/
│   ├── 🎯 Controlador.java
│   ├── 📊 Estadistica.java
│   └── 🎮 TableroJuego.java
├── 📦 Main/
│   ├── 📱 Main.java
│   └── 📦 Modelo/
│       ├── 🔲 Casilla.java
│       └── 📍 Coordenada.java
└── 📦 gui/
    ├── 🎯 TableroJuego.java
    └── 🎨 VentanaPrincipal.java
```

### 🗂️ Estructura de Archivos

```
📦 Buscaminas/
├── 📁 src/
│   ├── 📁 Logica/
│   │   ├── 🎯 Controlador.java
│   │   ├── 📊 Estadistica.java
│   │   └── 🎮 TableroJuego.java
│   ├── 📁 Main/
│   │   ├── 📱 Main.java
│   │   └── 📁 Modelo/
│   │       ├── 🔲 Casilla.java
│   │       └── 📍 Coordenada.java
│   └── 📁 gui/
│       ├── 🎯 TableroJuego.java
│       └── 🎨 VentanaPrincipal.java
├── 📄 README.md
└── 📁 Libraries/
```

---

## 📊 Estadísticas y Progreso

### 🏆 Sistema de Puntuación

- 🎮 **Partidas Jugadas**: Contador total de juegos
- 🏆 **Victorias**: Juegos completados exitosamente  
- 💥 **Derrotas**: Juegos perdidos por mina detonada
- 📈 **Ratio Victoria**: Porcentaje de éxito

### 📋 Funcionalidades Implementadas

- ✅ Generación aleatoria de minas
- ✅ Interfaz gráfica con JTable/JPanel
- ✅ Sistema de marcado con banderas
- ✅ Contador de minas adyacentes
- ✅ Revelado automático de áreas vacías
- ✅ Menú de juego nuevo/salir
- ✅ Estadísticas de juego
- ✅ Validación de entrada de usuario

---

## 🛠️ Tecnologías Utilizadas

<div align="center">

![Java](https://img.shields.io/badge/☕-JAVA-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![NetBeans](https://img.shields.io/badge/🛠️-NETBEANS-1B6AC6?style=for-the-badge&logo=apache-netbeans-ide&logoColor=white)
![Swing](https://img.shields.io/badge/🎨-JAVA_SWING-007396?style=for-the-badge)
![Git](https://img.shields.io/badge/📝-GIT-F05032?style=for-the-badge&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/🐙-GITHUB-181717?style=for-the-badge&logo=github&logoColor=white)

</div>

---

## 📈 Roadmap y Mejoras Futuras

### 🚀 Próximas Versiones

- [ ] 🎵 Efectos de sonido
- [ ] 🎨 Temas visuales personalizables
- [ ] ⏱️ Sistema de cronómetro
- [ ] 🏆 Tabla de mejores tiempos
- [ ] 🌐 Modo multijugador
- [ ] 📱 Versión móvil

### 🐛 Issues Conocidos

- Ninguno por el momento 😎

---

## 📞 Contacto y Soporte

¿Tienes preguntas, sugerencias o encontraste un bug? 🐛

<div align="center">

[![GitHub Issues](https://img.shields.io/badge/🐛-REPORTAR_BUG-red?style=for-the-badge)](https://github.com/TheJPlay2006/Buscaminas/issues)
[![GitHub Discussions](https://img.shields.io/badge/💬-DISCUSIONES-blue?style=for-the-badge)](https://github.com/TheJPlay2006/Buscaminas/discussions)

</div>

---

## 📜 Licencia

Este proyecto fue desarrollado como parte del curso **Programación I** en la **Universidad Técnica Nacional (UTN)** para fines educativos.

---

<div align="center">

### 🎮 ¡Que comience la aventura! 💣

**Desarrollado con ❤️ por Jairo & Emesis**

![Footer](https://img.shields.io/badge/💣_¿LISTO_PARA-EL_DESAFÍO?_💣-orange?style=for-the-badge)

---

*¿Te atreves a desafiar al campo minado? 💀*

</div>
