/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Emesis
 */


/**
 * Clase que representa una coordenada en el tablero del Buscaminas
 */
public class Coordenada {
    
    private int fila;
    private int columna;
    
    /**
     * Constructor con índices de array (0-base)
     * @param fila Índice de fila (0-base)
     * @param columna Índice de columna (0-base)
     */
    public Coordenada(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }
    
    /**
     * Crea una coordenada desde coordenadas de usuario (1-base)
     * @param filaUsuario Fila como la ingresa el usuario (1-base)
     * @param columnaUsuario Columna como la ingresa el usuario (1-base)
     * @return Nueva coordenada con índices convertidos
     */
    public static Coordenada desdeUsuario(int filaUsuario, int columnaUsuario) {
        return new Coordenada(filaUsuario - 1, columnaUsuario - 1);
    }
    
    /**
     * Valida si esta coordenada está dentro del tablero
     * @param tamaño Tamaño del tablero
     * @return true si la coordenada es válida
     */
    public boolean esValida(int tamaño) {
        return fila >= 0 && fila < tamaño && columna >= 0 && columna < tamaño;
    }
    
    /**
     * Obtiene todas las coordenadas vecinas (las 8 casillas adyacentes)
     * @param tamaño Tamaño del tablero para validar límites
     * @return Array con coordenadas vecinas válidas
     */
    public Coordenada[] getVecinos(int tamaño) {
        java.util.List<Coordenada> vecinos = new java.util.ArrayList<>();
        
        // Revisar las 8 direcciones posibles
        int[] deltaFilas = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] deltaColumnas = {-1, 0, 1, -1, 1, -1, 0, 1};
        
        for (int i = 0; i < 8; i++) {
            int nuevaFila = this.fila + deltaFilas[i];
            int nuevaColumna = this.columna + deltaColumnas[i];
            
            Coordenada vecino = new Coordenada(nuevaFila, nuevaColumna);
            if (vecino.esValida(tamaño)) {
                vecinos.add(vecino);
            }
        }
        
        return vecinos.toArray(new Coordenada[0]);
    }
    
    /**
     * Genera una coordenada aleatoria dentro del tablero
     * @param tamaño Tamaño del tablero
     * @return Coordenada aleatoria válida
     */
    public static Coordenada aleatoria(int tamaño) {
        java.util.Random random = new java.util.Random();
        int fila = random.nextInt(tamaño);
        int columna = random.nextInt(tamaño);
        return new Coordenada(fila, columna);
    }
    
    // Getters y Setters
    public int getFila() { return fila; }
    public void setFila(int fila) { this.fila = fila; }
    public int getColumna() { return columna; }
    public void setColumna(int columna) { this.columna = columna; }
    
    /**
     * Obtiene la fila en formato de usuario (1-base)
     * @return Fila para mostrar al usuario
     */
    public int getFilaUsuario() { return fila + 1; }
    
    /**
     * Obtiene la columna en formato de usuario (1-base)
     * @return Columna para mostrar al usuario
     */
    public int getColumnaUsuario() { return columna + 1; }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coordenada that = (Coordenada) obj;
        return fila == that.fila && columna == that.columna;
    }
    
    @Override
    public int hashCode() {
        return java.util.Objects.hash(fila, columna);
    }
    
    @Override
    public String toString() {
        return "Coordenada{fila=" + fila + ", columna=" + columna + "}";
    }
    
    /**
     * Representación para debug mostrando tanto índices como coordenadas de usuario
     * @return String detallado
     */
    public String toStringDetallado() {
        return String.format("Coordenada{índices=[%d,%d], usuario=[%d,%d]}", 
                           fila, columna, getFilaUsuario(), getColumnaUsuario());
    }
}