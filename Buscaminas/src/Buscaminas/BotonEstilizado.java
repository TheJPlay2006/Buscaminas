/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Buscaminas;

import javax.swing.*;
import java.awt.*;

public class BotonEstilizado extends JButton {
    public BotonEstilizado(String texto) {
        super(texto);

        // Estilo del botón
        setFont(new Font("Arial", Font.BOLD, 20));
        setBackground(new Color(51, 153, 255)); // Azul claro
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Efecto al pasar el mouse
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(new Color(255, 102, 204)); // Rosa claro
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(new Color(51, 153, 255)); // Volver al azul claro
            }
        });
    }
}