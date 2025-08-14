package Buscaminas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class VentanaTablero extends JFrame {
    private int lado;
    private JButton[][] botones;
    private boolean[][] minas;
    private VentanaPrincipal ventanaPrincipal;

    public VentanaTablero(int lado, VentanaPrincipal ventanaPrincipal) {
        this.lado = lado;
        this.ventanaPrincipal = ventanaPrincipal;

        // Configuración básica de la ventana
        setTitle("Tablero - Buscaminas");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(lado, lado));

        // Inicializar el tablero
        inicializarTablero();

        setVisible(true);
    }

    private void inicializarTablero() {
        botones = new JButton[lado][lado];
        minas = new boolean[lado][lado];

        // Colocar minas aleatoriamente
        colocarMinas();

        // Crear botones para el tablero
        for (int i = 0; i < lado; i++) {
    for (int j = 0; j < lado; j++) {
        JButton boton = new JButton();
        
        // Variables temporales para capturar los valores actuales de i y j
        final int fila = i;
        final int columna = j;

        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                destaparCasilla(fila, columna); // Usa las variables temporales
            }
        });

        botones[i][j] = boton;
        add(boton);
    }
}
    }

    private void colocarMinas() {
        Random random = new Random();
        int minasColocadas = 0;

        while (minasColocadas < 2 * lado) {
            int fila = random.nextInt(lado);
            int columna = random.nextInt(lado);

            if (!minas[fila][columna]) {
                minas[fila][columna] = true;
                minasColocadas++;
            }
        }
    }

    private void destaparCasilla(int fila, int columna) {
        if (fila < 0 || fila >= lado || columna < 0 || columna >= lado) {
            return; // Fuera de los límites
        }

        JButton boton = botones[fila][columna];
        if (boton.getText().length() > 0 || boton.isEnabled() == false) {
            return; // Casilla ya destapada o marcada
        }

        if (minas[fila][columna]) {
            // El jugador pierde
            mostrarMinas();
            JOptionPane.showMessageDialog(this, "¡Has perdido!");
            ventanaPrincipal.actualizarEstadisticas(false);
            dispose(); // Cerrar la ventana del tablero
        } else {
            // Contar minas vecinas
            int minasVecinas = contarMinasVecinas(fila, columna);
            boton.setText(minasVecinas > 0 ? String.valueOf(minasVecinas) : "");

            if (minasVecinas == 0) {
                // Destapar casillas vecinas recursivamente
                destaparCasilla(fila - 1, columna); // Arriba
                destaparCasilla(fila + 1, columna); // Abajo
                destaparCasilla(fila, columna - 1); // Izquierda
                destaparCasilla(fila, columna + 1); // Derecha
                destaparCasilla(fila - 1, columna - 1); // Esquina superior izquierda
                destaparCasilla(fila - 1, columna + 1); // Esquina superior derecha
                destaparCasilla(fila + 1, columna - 1); // Esquina inferior izquierda
                destaparCasilla(fila + 1, columna + 1); // Esquina inferior derecha
            }
        }
    }

    private int contarMinasVecinas(int fila, int columna) {
        int contador = 0;

        for (int i = fila - 1; i <= fila + 1; i++) {
            for (int j = columna - 1; j <= columna + 1; j++) {
                if (i >= 0 && i < lado && j >= 0 && j < lado && minas[i][j]) {
                    contador++;
                }
            }
        }

        return contador;
    }

    private void mostrarMinas() {
        for (int i = 0; i < lado; i++) {
            for (int j = 0; j < lado; j++) {
                if (minas[i][j]) {
                    botones[i][j].setText("M");
                    botones[i][j].setBackground(Color.RED);
                }
            }
        }
    }
}