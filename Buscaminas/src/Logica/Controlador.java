/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

/**
 *
 * @author Emesis
 */

import Modelo.Casilla;
import Modelo.Coordenada;
import java.util.*;

public class Controlador {
    private int ladoTablero;
    private Casilla[][] tablero;
    private List<Coordenada> minas;
    private EstadoJuego estadoJuego;
    private boolean primeraVez;
    private Estadistica estadistica;

    public Controlador(int ladoTablero) {
        this.ladoTablero = ladoTablero;
        this.tablero = new Casilla[ladoTablero][ladoTablero];
        this.minas = new ArrayList<>();
        this.estadoJuego = EstadoJuego.JUGANDO;
        this.primeraVez = true;
        this.estadistica = new Estadistica();
        inicializarTablero();
    }

    private void inicializarTablero() {
        for (int i = 0; i < ladoTablero; i++) {
            for (int j = 0; j < ladoTablero; j++) {
                tablero[i][j] = new Casilla();
            }
        }
    }

    public boolean destapar(Coordenada coord) {
        int fila = coord.getFila();
        int columna = coord.getColumna();

        if (fila < 0 || fila >= ladoTablero || columna < 0 || columna >= ladoTablero) return false;
        Casilla c = tablero[fila][columna];
        if (c.isEstaDestapada() || c.isEstaMarcada()) return false;

        if (primeraVez) {
            generarMinasSinCoordenada(fila, columna);
            primeraVez = false;
        }

        c.setEstaDestapada(true);

        if (c.tieneMina()) {
            estadoJuego = EstadoJuego.PERDIDO;
            return true;
        }

        if (c.getMinasVecinas() == 0) {
            destaparVecindad(coord);
        }

        return true;
    }

    private void generarMinasSinCoordenada(int filaExcluida, int columnaExcluida) {
        Random random = new Random();
        int numMinas = 2 * ladoTablero;
        int generadas = 0;

        while (generadas < numMinas) {
            int fila = random.nextInt(ladoTablero);
            int columna = random.nextInt(ladoTablero);
            if (fila == filaExcluida && columna == columnaExcluida) continue;

            Coordenada coord = new Coordenada(fila, columna);
            if (!minas.contains(coord)) {
                minas.add(coord);
                tablero[fila][columna].setTieneMina(true);
                generadas++;
            }
        }

        calcularMinasVecinas();
    }

    private void calcularMinasVecinas() {
        int[][] direcciones = {{-1,-1}, {-1,0}, {-1,1}, {0,-1}, {0,1}, {1,-1}, {1,0}, {1,1}};
        for (int i = 0; i < ladoTablero; i++) {
            for (int j = 0; j < ladoTablero; j++) {
                if (!tablero[i][j].tieneMina()) {
                    int contador = 0;
                    for (int[] dir : direcciones) {
                        int ni = i + dir[0], nj = j + dir[1];
                        if (ni >= 0 && ni < ladoTablero && nj >= 0 && nj < ladoTablero) {
                            if (tablero[ni][nj].tieneMina()) contador++;
                        }
                    }
                    tablero[i][j].setMinasVecinas(contador);
                }
            }
        }
    }

    private void destaparVecindad(Coordenada coord) {
        int[][] direcciones = {{-1,-1}, {-1,0}, {-1,1}, {0,-1}, {0,1}, {1,-1}, {1,0}, {1,1}};
        for (int[] dir : direcciones) {
            int ni = coord.getFila() + dir[0];
            int nj = coord.getColumna() + dir[1];
            if (ni >= 0 && ni < ladoTablero && nj >= 0 && nj < ladoTablero) {
                Casilla vecina = tablero[ni][nj];
                if (!vecina.isEstaDestapada() && !vecina.isEstaMarcada()) {
                    vecina.setEstaDestapada(true);
                    if (vecina.getMinasVecinas() == 0) {
                        destaparVecindad(new Coordenada(ni, nj));
                    }
                }
            }
        }
    }

    public void marcar(Coordenada coord) {
        int fila = coord.getFila();
        int columna = coord.getColumna();
        if (fila < 0 || fila >= ladoTablero || columna < 0 || columna >= ladoTablero) return;
        Casilla c = tablero[fila][columna];
        if (!c.isEstaDestapada()) {
            c.setEstaMarcada(!c.isEstaMarcada());
        }
    }

    public Casilla getCasilla(Coordenada coord) {
        return tablero[coord.getFila()][coord.getColumna()];
    }

    public List<Coordenada> getMinas() {
        return new ArrayList<>(minas);
    }

    public EstadoJuego getEstadoJuego() {
        return estadoJuego;
    }

    public int getLadoTablero() {
        return ladoTablero;
    }

    public Estadistica getEstadistica() {
        return estadistica;
    }

    public void revelarMinas() {
        for (Coordenada m : minas) {
            tablero[m.getFila()][m.getColumna()].revelar();
        }
    }
}