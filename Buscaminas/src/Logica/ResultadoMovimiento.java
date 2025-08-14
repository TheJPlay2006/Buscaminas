/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

/**
 *
 * @author TheJPlay2006
 */
/*
 * ResultadoMovimiento.java
 * Resultado de una acción del jugador (éxito, mensaje, estado del juego)
 */
/**
 * Representa el resultado de un movimiento realizado por el usuario.
 */
public class ResultadoMovimiento {
    private final boolean exito;
    private final String mensaje;
    private final EstadoJuego estadoJuego;

    public ResultadoMovimiento(boolean exito, String mensaje, EstadoJuego estadoJuego) {
        this.exito = exito;
        this.mensaje = mensaje;
        this.estadoJuego = estadoJuego;
    }

    public boolean isExito() {
        return exito;
    }

    public String getMensaje() {
        return mensaje;
    }

    public EstadoJuego getEstadoJuego() {
        return estadoJuego;
    }
}