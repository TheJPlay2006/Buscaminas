/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Buscaminas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelAnimado extends JPanel implements ActionListener {
    private int offset = 0; // Variable para controlar la animación

    public PanelAnimado() {
        Timer timer = new Timer(50, this); // Actualiza cada 50ms
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Dibujar un gradiente animado
        int width = getWidth();
        int height = getHeight();
        GradientPaint gradient = new GradientPaint(
                offset, 0, new Color(255, 102, 204), // Rosa claro
                offset + width, height, new Color(51, 153, 255) // Azul claro
        );
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        offset += 5; // Mover el gradiente
        if (offset > getWidth()) {
            offset = -getWidth(); // Reiniciar el desplazamiento
        }
        repaint(); // Redibujar el panel
    }
}