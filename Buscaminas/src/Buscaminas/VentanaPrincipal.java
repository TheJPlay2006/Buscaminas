package Buscaminas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPrincipal extends JFrame {
    private int juegosJugados = 0;
    private int juegosGanados = 0;
    private int juegosPerdidos = 0;

    public VentanaPrincipal() {
        // Configuración básica de la ventana
        setTitle("Buscaminas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Título llamativo
        JLabel titulo = new JLabel("BUSCAMINAS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setForeground(Color.BLUE);
        add(titulo, BorderLayout.NORTH);

        // Panel para los botones
        JPanel panelBotones = new JPanel();
        JButton btnNuevoJuego = new JButton("Nuevo Juego");
        JButton btnSalir = new JButton("Salir");

        // Acción para "Nuevo Juego"
        btnNuevoJuego.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarNuevoJuego();
            }
        });

        // Acción para "Salir"
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panelBotones.add(btnNuevoJuego);
        panelBotones.add(btnSalir);
        add(panelBotones, BorderLayout.CENTER);

        // Panel para las estadísticas
        JPanel panelEstadisticas = new JPanel();
        JLabel lblEstadisticas = new JLabel("Juegos Jugados: 0 | Ganados: 0 | Perdidos: 0");
        panelEstadisticas.add(lblEstadisticas);
        add(panelEstadisticas, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void iniciarNuevoJuego() {
        // Solicitar el tamaño del tablero
        String input = JOptionPane.showInputDialog(this, "Ingrese el tamaño del lado del tablero (mayor a 2):");
        try {
            int lado = Integer.parseInt(input);
            if (lado <= 2) {
                JOptionPane.showMessageDialog(this, "El tamaño del tablero debe ser mayor a 2.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Incrementar el contador de juegos jugados
            juegosJugados++;

            // Abrir la ventana del tablero
            new VentanaTablero(lado, this);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizarEstadisticas(boolean ganado) {
        if (ganado) {
            juegosGanados++;
        } else {
            juegosPerdidos++;
        }
        System.out.println("Juegos Jugados: " + juegosJugados + " | Ganados: " + juegosGanados + " | Perdidos: " + juegosPerdidos);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VentanaPrincipal::new);
    }
}