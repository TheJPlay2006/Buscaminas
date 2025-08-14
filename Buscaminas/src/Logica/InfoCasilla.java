/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

/**
 *
 * @author TheJPlay2006
 */

/**
 * Información de una casilla para la interfaz
 */
public class InfoCasilla {
    private String valor;
    private boolean estaDestapada;
    private boolean estaMarcada;
    private boolean esMina;

    public InfoCasilla(String valor, boolean estaDestapada, boolean estaMarcada, boolean esMina) {
        this.valor = valor;
        this.estaDestapada = estaDestapada;
        this.estaMarcada = estaMarcada;
        this.esMina = esMina;
    }

    // Getters y setters
    public String getValor() { return valor; }
   public boolean isEstaDestapada() { return estaDestapada; }
    public boolean isEstaMarcada() { return estaMarcada; }
    public boolean isEsMina() { return esMina; }

    public void setValor(String valor) { this.valor = valor; }
    public void setEstaDestapada(boolean estaDestapada) { this.estaDestapada = estaDestapada; }
    public void setEstaMarcada(boolean estaMarcada) { this.estaMarcada = estaMarcada; }
    public void setEsMina(boolean esMina) { this.esMina = esMina; }

    @Override
    public String toString() {
        return "InfoCasilla{" + "valor=" + valor + ", estaDestapada=" + estaDestapada + ", estaMarcada=" + estaMarcada + ", esMina=" + esMina + '}';
    }
 
}