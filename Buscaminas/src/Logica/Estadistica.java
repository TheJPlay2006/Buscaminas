/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Emesis
 */

public class Estadistica {
    private int juegosJugados;
    private int juegosGanados;
    private int juegosPerdidos;

    public Estadistica() {
        this.juegosJugados = 0;
        this.juegosGanados = 0;
        this.juegosPerdidos = 0;
    }

    public void incrementarJugados() { juegosJugados++; }
    public void incrementarGanados() { juegosGanados++; }
    public void incrementarPerdidos() { juegosPerdidos++; }

    public int getJuegosJugados() { return juegosJugados; }
    public int getJuegosGanados() { return juegosGanados; }
    public int getJuegosPerdidos() { return juegosPerdidos; }
}