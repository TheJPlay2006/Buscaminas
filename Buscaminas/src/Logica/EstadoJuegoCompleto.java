/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

/**
 *
 * @author TheJPlay2006
 */

public class EstadoJuegoCompleto {
    private final int tamaño;
    private final int numeroMinas;
    private final int marcasUsadas;
    private final int casillasDestapadas;
    private final EstadoJuego estado;
    private final String estadisticas;

    public EstadoJuegoCompleto(int tamaño, int numeroMinas, int marcasUsadas, int casillasDestapadas,
                               EstadoJuego estado, String estadisticas) {
        this.tamaño = tamaño;
        this.numeroMinas = numeroMinas;
        this.marcasUsadas = marcasUsadas;
        this.casillasDestapadas = casillasDestapadas;
        this.estado = estado;
        this.estadisticas = estadisticas;
    }

    public int getTamaño() {
        return tamaño;
    }

    public int getNumeroMinas() {
        return numeroMinas;
    }

    public int getMarcasUsadas() {
        return marcasUsadas;
    }

    public int getCasillasDestapadas() {
        return casillasDestapadas;
    }

    public EstadoJuego getEstado() {
        return estado;
    }

    public String getEstadisticas() {
        return estadisticas;
    }

    public double getPorcentajeProgreso() {
        int totalSinMinas = (tamaño * tamaño) - numeroMinas;
        if (totalSinMinas == 0) return 100.0;
        return (double) casillasDestapadas / totalSinMinas * 100.0;
    }

    public int getMarcasRestantes() {
        return numeroMinas - marcasUsadas;
    }
}