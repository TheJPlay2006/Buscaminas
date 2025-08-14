/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Emesis
 */
    public class Casilla {
    // Posibles estados de las casilla
    public enum Estado { Tapada, Destapada, Marcada }
    // Tipo de contenido de la casilla
    public enum Tipo { Vacia, Mina }

    private Coordenada posicion;  // Ubicación en el tablero
    private Tipo tipo;            // Si tiene mina o está vacía
    private Estado estado;        // Tapada, destapada o marcada
    private int minasVecinas;     // Número de minas alrededor
    private boolean revelada;     // Para mostrar minas al final del juego
   // Constructor principal
    public Casilla(Coordenada posicion) {
        this.posicion = posicion;
        this.tipo = Tipo.Vacia;
        this.estado = Estado.Tapada;
        this.minasVecinas = 0;
        this.revelada = false;
    }
    // Coloca una mina en la casilla
    public void ponerMina() { tipo = Tipo.Mina; }
    // Comprueba si la casilla tiene mina
    public boolean esMina() { return tipo == Tipo.Mina; }
    // Destapa la casilla si es posible
    public boolean destapar() {
        if (estado == Estado.Destapada || estado == Estado.Marcada) return false;
        estado = Estado.Destapada;
        return true;
    }
    // Marca o desmarca la casilla 
    public boolean marcar() {
        if (estado == Estado.Destapada) return false;
        estado = (estado == Estado.Marcada) ? Estado.Tapada : Estado.Marcada;
        return true;
    }
    // Comprueba si es una casilla vacía (sin minas alrededor)
    public boolean esVacia() { return tipo == Tipo.Vacia && minasVecinas == 0; }
    // Consultas rápidas sobre el estado
    public boolean estaDestapada() { return estado == Estado.Destapada; }
    public boolean estaMarcada() { return estado == Estado.Marcada; }
    // Obtiene el valor que se muestra en la interfaz
    public String valorMostrar() {
          if (revelada && esMina()) return "*"; // mostrar mina al final del juego
        switch (estado) {
            case Marcada: return "🚩"; 
            case Destapada: return esMina() ? "*" : (minasVecinas == 0 ? "" : String.valueOf(minasVecinas));
            default: return ""; // Tapada
        }
    }
    // Setters y getters de minas vecinas
    public void setMinasVecinas(int n) { minasVecinas = n; }
    public int getMinasVecinas() { return minasVecinas; }
    // Revela la casilla (para mostrar todas las minas al perder)
    public void revelar() { revelada = true; }

     // Crea una copia de la casilla (para seguridad o lógica interna)
    public Casilla copia() {
        Casilla c = new Casilla(new Coordenada(posicion.getFila(), posicion.getColumna()));
        c.tipo = this.tipo;
        c.estado = this.estado;
        c.minasVecinas = this.minasVecinas;
        c.revelada = this.revelada;
        return c;
    }
    public Coordenada getPosicion() {
    return posicion;
}

    @Override
    public String toString() {
        return "Casilla[" + posicion + ", tipo=" + tipo + ", estado=" + estado + ", vecinas=" + minasVecinas + "]";
    }
}