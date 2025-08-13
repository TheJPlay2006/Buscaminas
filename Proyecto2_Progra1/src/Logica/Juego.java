/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import modelo.Casilla;
import modelo.Coordenada;

/**
 *
 * @author Emesis
 */
/**
 
/**
 * Clase Juego: representa la lógica interna del Buscaminas
 * Se encarga de inicializar el tablero, colocar minas, contar minas vecinas,
 * destapar casillas, marcar/desmarcar, expandir áreas vacías y verificar victoria.
 */
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
        this.numeroMinas = 2 * tamaño;          // Definimos minas como 2*lado
        this.tablero = new Casilla[tamaño][tamaño];
        this.juegoTerminado = false;
        this.juegoGanado = false;
        this.casillasDestapadas = 0;
        this.marcasUsadas = 0;

        inicializarJuego();
    }

    // ==================== INICIALIZACIÓN ====================

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
        for (Coordenada vecino : coord.getVecinos(tamaño)) {
            if (tablero[vecino.getFila()][vecino.getColumna()].esMina()) contador++;
        }
        return contador;
    }

    // ==================== ACCIONES DEL JUGADOR ====================

    /**
     * Marca o desmarca una casilla
     */
    public boolean marcarCasilla(int fila, int columna) {
        if (!coordenadasValidas(fila, columna) || juegoTerminado) return false;

        Casilla c = tablero[fila][columna];

        if (!c.marcar()) return false; // No se puede marcar destapada

        boolean estabaMarcada = c.estaMarcada();
        if (!c.marcar()) return false;

        if (estabaMarcada) {
            marcasUsadas--;
        } else {
            if (marcasUsadas >= numeroMinas) {
                c.marcar(); // revertir
                return false;
            }
            marcasUsadas++;
        }
        return true;
    }

    /**
     * Destapa una casilla y expande si es vacía
     */
    public boolean destaparCasilla(int fila, int columna) {
        if (!coordenadasValidas(fila, columna) || juegoTerminado) return false;

        Casilla c = tablero[fila][columna];
        if (!c.destapar()) return false;

        c.destapar();
        casillasDestapadas++;

        if (c.esMina()) {
            juegoTerminado = true;
            juegoGanado = false;
            revelarTodasLasMinas();
            return true;
        }

        if (c.esVacia()) expandirAreaVacia(c.getPosicion());

        verificarVictoria();
        return true;
    }

    private void expandirAreaVacia(Coordenada coord) {
        for (Coordenada vecino : coord.getVecinos(tamaño)) {
            Casilla c = tablero[vecino.getFila()][vecino.getColumna()];
            if (c.estaDestapada()) {
                c.destapar();
                casillasDestapadas++;
                if (c.esVacia()) expandirAreaVacia(vecino);
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
        if (casillasDestapadas == (tamaño * tamaño - numeroMinas)) {
            juegoTerminado = true;
            juegoGanado = true;
        }
    }

    // ==================== MÉTODOS DE CONSULTA ====================

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

    /**
     * Reinicia el juego
     */
    public void reiniciarJuego() {
        juegoTerminado = false;
        juegoGanado = false;
        casillasDestapadas = 0;
        marcasUsadas = 0;
        inicializarJuego();
    }

    /**
     * Información detallada (para debugging o GUI)
     */
    public String getEstadoDetallado() {
        StringBuilder stringB = new StringBuilder();
        stringB.append("Tablero ").append(tamaño).append("x").append(tamaño)
          .append(", Minas: ").append(numeroMinas)
          .append(", Destapadas: ").append(casillasDestapadas)
          .append(", Marcadas: ").append(marcasUsadas)
          .append(", Estado: ").append(juegoTerminado ? (juegoGanado ? "GANADO" : "PERDIDO") : "EN CURSO");
        return stringB.toString();
    }
}
