/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author Jairo
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private int tamanoTablero; // Tamaño del tablero (L x L)
    private JButton[][] botones; // Matriz de botones para el tablero
    private JLabel lblJugados, lblGanados, lblPerdidos; // Etiquetas para estadísticas

    public Main (int tamano) {
        this.tamanoTablero = tamano;
        setTitle("Buscaminas");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Menú superior
        crearMenu();

        // Tablero
        crearTablero();

        // Estadísticas
        crearEstadisticas();

        setVisible(true);
    }

    private void crearMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuArchivo = new JMenu("Archivo");

        JMenuItem nuevoJuego = new JMenuItem("Nuevo Juego");
        nuevoJuego.addActionListener(e -> reiniciarJuego());
        menuArchivo.add(nuevoJuego);

        JMenuItem salir = new JMenuItem("Salir");
        salir.addActionListener(e -> System.exit(0));
        menuArchivo.add(salir);

        menuBar.add(menuArchivo);
        setJMenuBar(menuBar);
    }

    private void crearTablero() {
        JPanel panelTablero = new JPanel(new GridLayout(tamanoTablero, tamanoTablero));
        botones = new JButton[tamanoTablero][tamanoTablero];

        for (int i = 0; i < tamanoTablero; i++) {
            for (int j = 0; j < tamanoTablero; j++) {
                JButton boton = new JButton();
                boton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        accionBoton((JButton) e.getSource());
                    }
                });
                botones[i][j] = boton;
                panelTablero.add(boton);
            }
        }

        add(panelTablero, BorderLayout.CENTER);
    }

    private void crearEstadisticas() {
        JPanel panelEstadisticas = new JPanel(new FlowLayout());

        lblJugados = new JLabel("Jugados: 0");
        lblGanados = new JLabel("Ganados: 0");
        lblPerdidos = new JLabel("Perdidos: 0");

        panelEstadisticas.add(lblJugados);
        panelEstadisticas.add(lblGanados);
        panelEstadisticas.add(lblPerdidos);

        add(panelEstadisticas, BorderLayout.SOUTH);
    }

    private void reiniciarJuego() {
        // Lógica para reiniciar el juego
        System.out.println("Reiniciando juego...");
        dispose(); // Cierra la ventana actual
        new Main(tamanoTablero); // Crea una nueva instancia
    }

    private void accionBoton(JButton boton) {
        // Lógica para manejar clics en los botones
        System.out.println("Botón presionado");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main(5)); // Ejemplo con tablero de 5x5
    }
}