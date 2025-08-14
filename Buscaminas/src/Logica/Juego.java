/*
 * Clase Juego: representa la lógica interna del Buscaminas
 * Se encarga de inicializar el tablero, colocar minas, contar minas vecinas,
 * destapar casillas, marcar/desmarcar, expandir áreas vacías y verificar victoria.
 */
package Logica;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.LinkedList;
import java.util.Queue;
import Modelo.Casilla;
import Modelo.Coordenada;

public class Juego {

    private final int tamaño;               // Tamaño del tablero
    private final int numeroMinas;          // Total de minas
    private final Casilla[][] tablero;      // Matriz de casillas
    private boolean juegoTerminado;         // Si terminó el juego
    private boolean juegoGanado;            // Si se ganó
    private int casillasDestapadas;         // Contador de casillas destapadas
    private int marcasUsadas;               // Contador de casillas marcadas

    public Juego(int tamaño) {
        if (tamaño <= 2) throw new IllegalArgumentException("El tamaño debe ser > 2");

        this.tamaño = tamaño;
        this.numeroMinas = 2 * tamaño;
        this.tablero = new Casilla[tamaño][tamaño];
        this.juegoTerminado = false;
        this.juegoGanado = false;
        this.casillasDestapadas = 0;
        this.marcasUsadas = 0;

        inicializarJuego();
    }

    private void inicializarJuego() {
        crearCasillas();
        colocarMinas();
        calcularNumeros();
    }

    private void crearCasillas() {
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                tablero[i][j] = new Casilla(new Coordenada(i, j));
            }
        }
    }

    private void colocarMinas() {
        Random random = new Random();
        int colocadas = 0;
        while (colocadas < numeroMinas) {
            int fila = random.nextInt(tamaño);
            int col = random.nextInt(tamaño);
            Casilla c = tablero[fila][col];
            if (!c.esMina()) {
                c.ponerMina();
                colocadas++;
            }
        }
    }

    private void calcularNumeros() {
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                Casilla c = tablero[i][j];
                if (!c.esMina()) {
                    int vecinas = contarMinasVecinas(c.getPosicion());
                    c.setMinasVecinas(vecinas);
                }
            }
        }
    }

    private int contarMinasVecinas(Coordenada coord) {
        int contador = 0;
        Coordenada[] vecinos = coord.getVecinos(tamaño);
        for (Coordenada vecino : vecinos) {
            if (vecino != null && tablero[vecino.getFila()][vecino.getColumna()].esMina()) {
                contador++;
            }
        }
        return contador;
    }

    // ACCIONES DEL JUGADOR

    /**
     * Marca o desmarca una casilla
     */
  public boolean marcarCasilla(int fila, int columna) {
    if (!coordenadasValidas(fila, columna) || juegoTerminado) return false;

    Casilla c = tablero[fila][columna];

    if (c.estaDestapada()) return false;

    if (c.estaMarcada()) {
        c.marcar(); // desmarca
        marcasUsadas--;
        return true;
    } else {
        if (marcasUsadas >= numeroMinas) return false;
        c.marcar(); // marca
        marcasUsadas++;
        return true;
    }
}

    /**
     * Destapa una casilla y expande si es vacía (valor 0)
     */
 public boolean destaparCasilla(int fila, int columna) {
    if (!coordenadasValidas(fila, columna) || juegoTerminado) return false;

    Casilla c = tablero[fila][columna];

    if (c.estaDestapada()) return false;
    if (c.estaMarcada()) return false; // no se puede destapar si está marcada

    c.destapar();
    casillasDestapadas++;

    if (c.esMina()) {
        juegoTerminado = true;
        juegoGanado = false;
        revelarTodasLasMinas();
        return true;
    }

    if (c.getMinasVecinas() == 0) {
        expandirAreaVacia(c.getPosicion());
    }

    verificarVictoria();
    return true;
}

private void expandirAreaVacia(Coordenada coordInicial) {
    Queue<Coordenada> cola = new LinkedList<>();
    boolean[][] visitadas = new boolean[tamaño][tamaño];

    cola.offer(coordInicial);
    visitadas[coordInicial.getFila()][coordInicial.getColumna()] = true;

    while (!cola.isEmpty()) {
        Coordenada actual = cola.poll();
        int fila = actual.getFila();
        int col = actual.getColumna();

        if (!coordenadasValidas(fila, col)) continue;

        Casilla c = tablero[fila][col];

        if (c.estaDestapada() || c.estaMarcada() || c.esMina()) {
            continue;
        }

        c.destapar();
        casillasDestapadas++;

        if (c.getMinasVecinas() == 0) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0) continue;
                    int nf = fila + i;
                    int nc = col + j;
                    if (coordenadasValidas(nf, nc) && !visitadas[nf][nc]) {
                        visitadas[nf][nc] = true;
                        cola.offer(new Coordenada(nf, nc));
                    }
                }
            }
        }
    }
}

    private void revelarTodasLasMinas() {
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                Casilla c = tablero[i][j];
                if (c.esMina()) c.revelar();
            }
        }
    }

    private void verificarVictoria() {
        int totalCasillas = tamaño * tamaño;
        int minas = numeroMinas;
        int casillasSinMina = totalCasillas - minas;

        if (casillasDestapadas == casillasSinMina) {
            juegoTerminado = true;
            juegoGanado = true;
        }
    }

    // MÉTODOS DE CONSULTA

    public Casilla getCasilla(int fila, int columna) {
        if (!coordenadasValidas(fila, columna)) return null;
        return tablero[fila][columna];
    }

    public boolean estaMarcada(int fila, int columna) {
        Casilla c = getCasilla(fila, columna);
        return c != null && c.estaMarcada();
    }

    public boolean estaDestapada(int fila, int columna) {
        Casilla c = getCasilla(fila, columna);
        return c != null && c.estaDestapada();
    }

    public String valorMostrar(int fila, int columna) {
        Casilla c = getCasilla(fila, columna);
        return c != null ? c.valorMostrar() : "";
    }

    public List<Coordenada> obtenerMinas() {
        List<Coordenada> minas = new ArrayList<>();
        for (int i = 0; i < tamaño; i++)
            for (int j = 0; j < tamaño; j++)
                if (tablero[i][j].esMina()) minas.add(new Coordenada(i, j));
        return minas;
    }

    public List<Coordenada> verificarSeleccion() {
        List<Coordenada> marcadas = new ArrayList<>();
        for (int i = 0; i < tamaño; i++)
            for (int j = 0; j < tamaño; j++)
                if (tablero[i][j].estaMarcada()) marcadas.add(new Coordenada(i, j));
        return marcadas;
    }

    // GETTERS
    public int getTamaño() { return tamaño; }
    public int getNumeroMinas() { return numeroMinas; }
    public boolean isJuegoTerminado() { return juegoTerminado; }
    public boolean isJuegoGanado() { return juegoGanado; }
    public int getCasillasDestapadas() { return casillasDestapadas; }
    public int getMarcasUsadas() { return marcasUsadas; }

    private boolean coordenadasValidas(int fila, int col) {
        return fila >= 0 && fila < tamaño && col >= 0 && col < tamaño;
    }

    public void reiniciarJuego() {
        juegoTerminado = false;
        juegoGanado = false;
        casillasDestapadas = 0;
        marcasUsadas = 0;
        inicializarJuego();
    }

    public String getEstadoDetallado() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tablero ").append(tamaño).append("x").append(tamaño)
          .append(", Minas: ").append(numeroMinas)
          .append(", Destapadas: ").append(casillasDestapadas)
          .append(", Marcadas: ").append(marcasUsadas)
          .append(", Estado: ").append(juegoTerminado ? (juegoGanado ? "GANADO" : "PERDIDO") : "EN CURSO");
        return sb.toString();
    }
}