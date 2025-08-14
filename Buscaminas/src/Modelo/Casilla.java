/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Emesis
 */
/*
 * Clase Casilla: representa una celda del tablero
 */
public class Casilla {
    public enum Estado { Tapada, Destapada, Marcada }
    public enum Tipo { Vacia, Mina }

    private Coordenada posicion;
    private Tipo tipo;
    private Estado estado;
    private int minasVecinas;
    private boolean revelada;

    public Casilla(Coordenada posicion) {
        this.posicion = posicion;
        this.tipo = Tipo.Vacia;
        this.estado = Estado.Tapada;
        this.minasVecinas = 0;
        this.revelada = false;
    }

    public void ponerMina() { tipo = Tipo.Mina; }
    public boolean esMina() { return tipo == Tipo.Mina; }
    public boolean destapar() {
        if (estado == Estado.Destapada || estado == Estado.Marcada) return false;
        estado = Estado.Destapada;
        return true;
    }
    public boolean marcar() {
        if (estado == Estado.Destapada) return false;
        estado = (estado == Estado.Marcada) ? Estado.Tapada : Estado.Marcada;
        return true;
    }
    public boolean esVacia() { return tipo == Tipo.Vacia && minasVecinas == 0; }
    public boolean estaDestapada() { return estado == Estado.Destapada; }
    public boolean estaMarcada() { return estado == Estado.Marcada; }
    public String valorMostrar() {
        if (revelada && esMina()) return "*";
        switch (estado) {
            case Marcada: return "🚩";
            case Destapada: return esMina() ? "*" : (minasVecinas == 0 ? "" : String.valueOf(minasVecinas));
            default: return "";
        }
    }
    public void setMinasVecinas(int n) { minasVecinas = n; }
    public int getMinasVecinas() { return minasVecinas; }
    public void revelar() { revelada = true; }
    public Casilla copia() {
        Casilla c = new Casilla(new Coordenada(posicion.getFila(), posicion.getColumna()));
        c.tipo = this.tipo;
        c.estado = this.estado;
        c.minasVecinas = this.minasVecinas;
        c.revelada = this.revelada;
        return c;
    }
    public Coordenada getPosicion() { return posicion; }
    @Override
    public String toString() {
        return "Casilla[" + posicion + ", tipo=" + tipo + ", estado=" + estado + ", vecinas=" + minasVecinas + "]";
    }
}