/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

/**
 *
 * @author TheJPlay2006
 */
/*
 * TableroJuego.java
 * Ventana principal del juego Buscaminas.
 */

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class TableroJuego extends JFrame {

    private final int L; // Tamaño del tablero
    private final int MINAS;
    private final guiPrincipal ventanaPrincipal;

    private boolean[][] tieneMina;
    private boolean[][] destapada;
    private boolean[][] marcada;
    private int[][] minasAlrededor;

    private JButton[][] botones;
    private boolean juegoTerminado;

    // Estadísticas (serán actualizadas en ventanaPrincipal)
    private int casillasDestapadasSinMina;
    private final int totalCasillasSinMina;

    public TableroJuego(int L, guiPrincipal ventanaPrincipal) {
        this.L = L;
        this.MINAS = 2 * L;
        this.ventanaPrincipal = ventanaPrincipal;
        this.juegoTerminado = false;
        this.casillasDestapadasSinMina = 0;
        this.totalCasillasSinMina = (L * L) - MINAS;

        // Inicializar arreglos
        tieneMina = new boolean[L][L];
        destapada = new boolean[L][L];
        marcada = new boolean[L][L];
        minasAlrededor = new int[L][L];
        botones = new JButton[L][L];

        inicializarTablero();
        colocarMinas();
        calcularMinasAlrededor();

        configurarVentana();
        crearInterfaz();
    }

    private void configurarVentana() {
        setTitle("Buscaminas - " + L + "x" + L);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Evita cierre sin confirmar
        setLayout(new BorderLayout());
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void crearInterfaz() {
        JPanel panelTablero = new JPanel(new GridLayout(L, L));
        panelTablero.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                botones[i][j] = new JButton();
                botones[i][j].setFont(new Font("Arial", Font.BOLD, 16));
                botones[i][j].setFocusPainted(false);
                botones[i][j].setBackground(Color.LIGHT_GRAY);

                final int fila = i;
                final int columna = j;

                // Clic izquierdo: destapar
                botones[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (juegoTerminado) return;

                        if (SwingUtilities.isLeftMouseButton(e)) {
                            destapar(fila, columna);
                        } else if (SwingUtilities.isRightMouseButton(e)) {
                            marcar(fila, columna);
                        }
                    }
                });

                panelTablero.add(botones[i][j]);
            }
        }

        add(panelTablero, BorderLayout.CENTER);

        // Menú: Juego Nuevo y Salir
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menú");

        JMenuItem itemNuevo = new JMenuItem("Juego Nuevo");
        itemNuevo.addActionListener(e -> confirmarNuevoJuego());

        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> confirmarSalir());

        menu.add(itemNuevo);
        menu.add(itemSalir);
        menuBar.add(menu);
        setJMenuBar(menuBar);
        setLocationRelativeTo(null);
        pack();
        setSize(600, 600);
    }

    private void inicializarTablero() {
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                tieneMina[i][j] = false;
                destapada[i][j] = false;
                marcada[i][j] = false;
                minasAlrededor[i][j] = 0;
            }
        }
    }

    private void colocarMinas() {
        Random rand = new Random();
        int colocadas = 0;
        while (colocadas < MINAS) {
            int f = rand.nextInt(L);
            int c = rand.nextInt(L);
            if (!tieneMina[f][c]) {
                tieneMina[f][c] = true;
                colocadas++;
            }
        }
    }

    private void calcularMinasAlrededor() {
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                if (tieneMina[i][j]) continue;

                int contador = 0;
                for (int k = 0; k < 8; k++) {
                    int nx = i + dx[k];
                    int ny = j + dy[k];
                    if (nx >= 0 && nx < L && ny >= 0 && ny < L) {
                        if (tieneMina[nx][ny]) {
                            contador++;
                        }
                    }
                }
                minasAlrededor[i][j] = contador;
            }
        }
    }

    private void destapar(int fila, int columna) {
        if (destapada[fila][columna] || marcada[fila][columna] || juegoTerminado) {
            return;
        }

        destapada[fila][columna] = true;

        if (tieneMina[fila][columna]) {
            perder();
            return;
        }

        casillasDestapadasSinMina++;
        actualizarBoton(fila, columna);

        // Propagar zona de ceros
        if (minasAlrededor[fila][columna] == 0) {
            destaparVecinas(fila, columna);
        }

        // Verificar si ganó
        if (casillasDestapadasSinMina == totalCasillasSinMina) {
            ganar();
        }
    }

    private void marcar(int fila, int columna) {
        if (destapada[fila][columna] || juegoTerminado) {
            return;
        }

        if (marcada[fila][columna]) {
            // Desmarcar
            marcada[fila][columna] = false;
            botones[fila][columna].setText("");
            botones[fila][columna].setBackground(Color.LIGHT_GRAY);
        } else {
            // Marcar
            marcada[fila][columna] = true;
            botones[fila][columna].setText("🚩");
            botones[fila][columna].setForeground(Color.RED);
            botones[fila][columna].setBackground(Color.YELLOW);
        }
        botones[fila][columna].repaint();
    }

    private void actualizarBoton(int fila, int columna) {
        JButton btn = botones[fila][columna];
        int valor = minasAlrededor[fila][columna];

        btn.setText(valor == 0 ? "" : String.valueOf(valor));
        btn.setForeground(getColorPorNumero(valor));
        btn.setBackground(Color.WHITE);
        btn.setEnabled(false);
        btn.repaint();
    }

    private Color getColorPorNumero(int num) {
        switch (num) {
            case 1: return Color.BLUE;
            case 2: return Color.GREEN.darker();
            case 3: return Color.RED;
            case 4: return new Color(0, 0, 139);
            case 5: return Color.MAGENTA.darker();
            case 6: return Color.CYAN.darker();
            case 7: return Color.BLACK;
            case 8: return Color.GRAY;
            default: return Color.BLACK;
        }
    }

    private void destaparVecinas(int fila, int columna) {
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int k = 0; k < 8; k++) {
            int nx = fila + dx[k];
            int ny = columna + dy[k];
            if (nx >= 0 && nx < L && ny >= 0 && ny < L) {
                if (!destapada[nx][ny] && !marcada[nx][ny]) {
                    destapar(nx, ny);
                }
            }
        }
    }

    private void perder() {
        juegoTerminado = true;
        revelarTodasLasMinas();

        JOptionPane.showMessageDialog(this,
            "💥 ¡BOOM! Has pisado una mina.\nPerdiste el juego.",
            "Derrota", JOptionPane.ERROR_MESSAGE);

        preguntarJugarNuevo();
    }

    private void ganar() {
        juegoTerminado = true;
        revelarTodasLasMinasCorrectamente();

        JOptionPane.showMessageDialog(this,
            "🎉 ¡Felicidades!\nHas encontrado todas las minas.",
            "¡Victoria!", JOptionPane.INFORMATION_MESSAGE);

        ventanaPrincipal.incrementarGanados(); // Actualiza estadísticas
        preguntarJugarNuevo();
    }

    private void revelarTodasLasMinas() {
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                if (tieneMina[i][j]) {
                    JButton btn = botones[i][j];
                    if (!marcada[i][j]) {
                        btn.setText("💣");
                        btn.setBackground(Color.PINK);
                        btn.setForeground(Color.BLACK);
                    }
                }
            }
        }
        repaint();
    }

    private void revelarTodasLasMinasCorrectamente() {
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                if (tieneMina[i][j]) {
                    JButton btn = botones[i][j];
                    btn.setText("💣");
                    btn.setBackground(Color.GREEN);
                    btn.setForeground(Color.WHITE);
                }
            }
        }
        repaint();
    }

    private void preguntarJugarNuevo() {
        int opcion = JOptionPane.showConfirmDialog(this,
            "¿Deseas jugar una nueva partida?",
            "Juego Nuevo", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            confirmarNuevoJuego();
        } else {
            confirmarSalir();
        }
    }

    private void confirmarNuevoJuego() {
        dispose();
        SwingUtilities.invokeLater(() -> {
            new TableroJuego(L, ventanaPrincipal).setVisible(true);
        });
    }

    private void confirmarSalir() {
        int opcion = JOptionPane.showConfirmDialog(this,
            "¿Estás seguro de que deseas salir?",
            "Confirmar salida", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            dispose();
            ventanaPrincipal.setVisible(true);
        }
    }

    // Método para que ventanaPrincipal actualice estadísticas
    public void marcarComoGanado() {
        ventanaPrincipal.incrementarGanados();
    }

    public void marcarComoPerdido() {
        ventanaPrincipal.incrementarPerdidos();
    }
}