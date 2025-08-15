/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Emesis
 */

public class Casilla {
    private boolean tieneMina;
    private boolean estaMarcada;
    private boolean estaDestapada;
    private int minasVecinas;

    public Casilla() {
        this.tieneMina = false;
        this.estaMarcada = false;
        this.estaDestapada = false;
        this.minasVecinas = 0;
    }

    public boolean tieneMina() { return tieneMina; }
    public void setTieneMina(boolean tieneMina) { this.tieneMina = tieneMina; }

    public boolean isEstaMarcada() { return estaMarcada; }
    public void setEstaMarcada(boolean estaMarcada) { this.estaMarcada = estaMarcada; }

    public boolean isEstaDestapada() { return estaDestapada; }
    public void setEstaDestapada(boolean estaDestapada) { this.estaDestapada = estaDestapada; }

    public int getMinasVecinas() { return minasVecinas; }
    public void setMinasVecinas(int minasVecinas) { this.minasVecinas = minasVecinas; }

    public void revelar() { this.estaDestapada = true; }
}