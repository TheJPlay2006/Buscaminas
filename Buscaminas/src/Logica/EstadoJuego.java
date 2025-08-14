package Logica;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author TheJPlay2006
 */

public enum EstadoJuego {
    SIN_JUEGO("No hay juego activo"),
    EN_CURSO("Juego en progreso"),
    GANADO("Juego ganado"),
    PERDIDO("Juego perdido");

    private final String descripcion;

    EstadoJuego(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}