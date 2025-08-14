/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;
import java.util.ArrayList;
import java.util.List;
import Modelo.Coordenada;
import Modelo.Casilla;
/**
 *
 * @author Emesis
 */

/**
 * Controlador principal del juego Buscaminas.
 * Se encarga de recibir acciones del usuario, actualizar el juego y consultar estadísticas.
 */

import java.util.ArrayList;
import java.util.List;
import Modelo.Coordenada;
import Modelo.Casilla;

/**
 * Controlador principal del juego Buscaminas.
 * Se encarga de recibir acciones del usuario, actualizar el juego y consultar estadísticas.
 */
public class Controlador {

    private Juego juego;                    // Instancia del juego actual
    private Estadistica estadisticas;       // Registro de estadísticas del jugador
    private boolean juegoEnCurso;           // Indica si hay un juego activo

    /**
     * Constructor del controlador
     * Inicializa estadísticas y marca que no hay juego en curso.
     */
    public Controlador() {
        this.estadisticas = Estadistica.getInstancia();
        this.juegoEnCurso = false;
    }

    /**
     * Inicia un nuevo juego con el tamaño especificado
     * @param tamaño Tamaño del tablero (lado)
     * @return true si se pudo iniciar, false si el tamaño es inválido
     */
    public boolean iniciarNuevoJuego(int tamaño) {
        if (tamaño <= 2) return false; // Tablero demasiado pequeño
        this.juego = new Juego(tamaño);
        this.juegoEnCurso = true;
        return true;
    }

    /**
     * Procesa la acción de un usuario usando coordenadas 1-based
     * @param filaUsuario fila ingresada por usuario (1-base)
     * @param columnaUsuario columna ingresada por usuario (1-base)
     * @param accion "destapar", "marcar" o "desmarcar"
     * @return ResultadoMovimiento indicando éxito, mensaje y estado del juego
     */
    public ResultadoMovimiento manejarEntradaUsuario(int filaUsuario, int columnaUsuario, String accion) {
        Coordenada c = Coordenada.desdeUsuario(filaUsuario, columnaUsuario);
        return ejecutarAccionUsuario(c.getFila(), c.getColumna(), accion);
    }

    /**
     * Procesa la acción del usuario usando índices 0-based
     */
    public ResultadoMovimiento ejecutarAccionUsuario(int fila, int columna, String accion) {
        if (!juegoEnCurso || juego == null) {
            return new ResultadoMovimiento(false, "No hay juego en curso", EstadoJuego.SIN_JUEGO);
        }

        boolean exito = false;
        String mensaje = "";
        EstadoJuego estado = EstadoJuego.EN_CURSO;

        switch (accion.toLowerCase()) {
            case "destapar":
                exito = juego.destaparCasilla(fila, columna);
                if (!exito) {
                    mensaje = "No se puede destapar esta casilla";
                } else {
                    mensaje = "Casilla destapada";
                    if (juego.isJuegoTerminado()) {
                        juegoEnCurso = false;
                        if (juego.isJuegoGanado()) {
                            estado = EstadoJuego.GANADO;
                            mensaje = "¡Felicitaciones! ¡Has ganado!";
                            estadisticas.registrarJuegoGanado();
                        } else {
                            estado = EstadoJuego.PERDIDO;
                            mensaje = "¡Boom! Has perdido.";
                            estadisticas.registrarJuegoPerdido();
                        }
                    }
                }
                break;

            case "marcar":
                exito = juego.marcarCasilla(fila, columna);
                if (!exito) {
                    if (juego.getMarcasUsadas() >= juego.getNumeroMinas()) {
                        mensaje = "No tienes más marcas disponibles";
                    } else {
                        mensaje = "No se puede marcar esta casilla";
                    }
                } else {
                    mensaje = "Casilla marcada";
                }
                break;

            case "desmarcar":
                if (juego.estaMarcada(fila, columna)) {
                    exito = juego.marcarCasilla(fila, columna); // Alterna marca
                    mensaje = exito ? "Casilla desmarcada" : "No se pudo desmarcar";
                } else {
                    mensaje = "La casilla no está marcada";
                }
                break;

            default:
                mensaje = "Acción no válida: use destapar, marcar o desmarcar";
                break;
        }

        return new ResultadoMovimiento(exito, mensaje, estado);
    }

    /**
     * Obtiene información de una casilla usando índices 0-based
     */
    public InfoCasilla getInfoCasilla(int fila, int columna) {
        if (juego == null) return new InfoCasilla("", false, false, false);

        Casilla c = juego.getCasilla(fila, columna);
        if (c == null) return new InfoCasilla("", false, false, false);

        return new InfoCasilla(c.valorMostrar(), c.esMina(), c.estaMarcada(), c.estaDestapada());
    }

    /**
     * Obtiene información de una casilla usando coordenadas 1-based
     */
    public InfoCasilla getInfo(int filaUsuario, int columnaUsuario) {
        Coordenada c = Coordenada.desdeUsuario(filaUsuario, columnaUsuario);
        return getInfoCasilla(c.getFila(), c.getColumna());
    }

    /**
     * Devuelve el estado completo del juego para la interfaz
     */
    public EstadoJuegoCompleto getEstadoJuego() {
        if (juego == null) {
            return new EstadoJuegoCompleto(0, 0, 0, 0, EstadoJuego.SIN_JUEGO, estadisticas.generarReporteCorto());
        }

        EstadoJuego estado = !juegoEnCurso
                ? (juego.isJuegoGanado() ? EstadoJuego.GANADO : EstadoJuego.PERDIDO)
                : EstadoJuego.EN_CURSO;

        return new EstadoJuegoCompleto(
                juego.getTamaño(),
                juego.getNumeroMinas(),
                juego.getMarcasUsadas(),
                juego.getCasillasDestapadas(),
                estado,
                estadisticas.generarReporteCorto()
        );
    }

    // Estadísticas y mensajes
    public String getEstadisticas() { return estadisticas.generarReporte(); }
    public String getEstadisticasCortas() { return estadisticas.generarReporteCorto(); }
    public String getMensajeBienvenida() { return estadisticas.getMensajeBienvenida(); }
    public void reiniciarEstadisticas() { estadisticas.resetearEstadisticas(); }

    // Verificaciones de juego
    public boolean hayJuegoEnCurso() { return juegoEnCurso && juego != null && !juego.isJuegoTerminado(); }

    // Conversión y validación de coordenadas de usuario
    public int cambiarCoordenada(int coordenadasUsuario) { return coordenadasUsuario - 1; }
    public boolean coordenadasBuenas(int filaUsuario, int colUsuario) {
        if (juego == null) return false;
        return Coordenada.desdeUsuario(filaUsuario, colUsuario).esValida(juego.getTamaño());
    }

    // Información de debugging o lista de minas/marcadas
    public String getDetalles() {
        if (juego == null) return "No hay juego activo\n" + estadisticas.generarReporte();
        return juego.getEstadoDetallado() + "\n" + estadisticas.generarReporte();
    }
    public List<Coordenada> obtenerMinas() {
        if (juego == null) return new ArrayList<>();
        return juego.obtenerMinas();
    }
    public List<Coordenada> verificarSeleccion() {
        if (juego == null) return new ArrayList<>();
        return juego.verificarSeleccion();
    }
}