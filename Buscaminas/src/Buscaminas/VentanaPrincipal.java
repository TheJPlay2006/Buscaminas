package Buscaminas;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private int juegosJugados = 0;
    private int juegosGanados = 0;
    private int juegosPerdidos = 0;

    public VentanaPrincipal() {
        // Configuración básica de la ventana
        setTitle("Buscaminas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Agregar el título llamativo
        JLabel titulo = new JLabel("BUSCAMINAS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 48));
        titulo.setForeground(Color.WHITE); // Color del texto blanco
        titulo.setOpaque(true); // Hacer el fondo visible
        titulo.setBackground(new Color(51, 153, 255)); // Azul claro como fondo del título
        add(titulo, BorderLayout.NORTH);

        // Agregar el panel animado como fondo
        PanelAnimado panelAnimado = new PanelAnimado();
        add(panelAnimado, BorderLayout.CENTER);

        // Crear un panel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false); // Hacerlo transparente para ver el fondo animado
        panelBotones.setLayout(new GridLayout(2, 1, 10, 10)); // Dos botones con espacio entre ellos

        // Botón "Nuevo Juego"
        BotonEstilizado botonNuevoJuego = new BotonEstilizado("Nuevo Juego");
        botonNuevoJuego.addActionListener(e -> iniciarNuevoJuego());
        panelBotones.add(botonNuevoJuego);

        // Botón "Salir"
        BotonEstilizado botonSalir = new BotonEstilizado("Salir");
        botonSalir.addActionListener(e -> System.exit(0));
        panelBotones.add(botonSalir);

        // Agregar el panel de botones al centro de la ventana
        add(panelBotones, BorderLayout.CENTER);

        // Mostrar la ventana
        setVisible(true);
    }

    private void iniciarNuevoJuego() {
        juegosJugados++;
        JOptionPane.showMessageDialog(this, "Iniciando nuevo juego...");
        mostrarEstadisticas();
    }

    private void mostrarEstadisticas() {
        JOptionPane.showMessageDialog(this,
                "Juegos Jugados: " + juegosJugados +
                        "\nGanados: " + juegosGanados +
                        "\nPerdidos: " + juegosPerdidos,
                "Estadísticas", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VentanaPrincipal::new);
    }
}