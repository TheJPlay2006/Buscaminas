/*
 * TableroJuego.java
 * Universidad Técnica Nacional - ITI Programación I
 * Proyecto Buscaminas
 */
package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

/**
 * Clase que representa la ventana del juego Buscaminas.
 * Permite al usuario jugar en un tablero LxL, destapando casillas y marcando minas.
 * 
 * @author [Tu Nombre]
 */
public class TableroJuego extends JFrame {

    private final int L;                   // Tamaño del tablero
    private final int MINAS;               // Cantidad de minas: 2 * L
    private final guiPrincipal ventanaPrincipal; // Para actualizar estadísticas

    // Matrices del juego
    private boolean[][] tieneMina;
    private boolean[][] destapada;
    private boolean[][] marcada;
    private int[][] minasAlrededor;

    // Botones del tablero
    private JButton[][] botones;

    // Estado del juego
    private boolean juegoTerminado;
    private int casillasDestapadasSinMina;
    private final int totalCasillasSinMina;

    /**
     * Constructor del tablero de juego.
     * Inicializa el juego y muestra la ventana correctamente centrada.
     * 
     * @param L tamaño del lado del tablero
     * @param ventanaPrincipal referencia a la ventana principal para estadísticas
     */
    public TableroJuego(int L, guiPrincipal ventanaPrincipal) {
        this.L = L;
        this.MINAS = 2 * L;
        this.ventanaPrincipal = ventanaPrincipal;
        this.juegoTerminado = false;
        this.casillasDestapadasSinMina = 0;
        this.totalCasillasSinMina = (L * L) - MINAS;

        // Inicializar matrices
        inicializarMatrices();

        // Configurar ventana
        configurarVentana();
        crearInterfaz();

        // Generar minas
        colocarMinas();
        calcularMinasAlrededor();

        // === PASOS FINALES (orden correcto) ===
        pack(); // Ajusta el tamaño al contenido
        setSize(Math.min(800, L * 60), Math.min(600, L * 60)); // Tamaño adaptable
        setLocationRelativeTo(null); // ✅ Centra en la pantalla
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setVisible(true); // Último paso: hacer visible
    }

    /**
     * Inicializa todas las matrices del juego.
     */
    private void inicializarMatrices() {
        tieneMina = new boolean[L][L];
        destapada = new boolean[L][L];
        marcada = new boolean[L][L];
        minasAlrededor = new int[L][L];
        botones = new JButton[L][L];

        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                tieneMina[i][j] = false;
                destapada[i][j] = false;
                marcada[i][j] = false;
                minasAlrededor[i][j] = 0;
            }
        }
    }

    /**
     * Configura las propiedades básicas de la ventana.
     */
    private void configurarVentana() {
        setTitle("Buscaminas - " + L + "x" + L);
        setLayout(new BorderLayout());
    }

    /**
     * Crea la interfaz gráfica del tablero.
     */
    private void crearInterfaz() {
        // Panel del tablero con GridLayout
        JPanel panelTablero = new JPanel(new GridLayout(L, L));
        panelTablero.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Crear botones
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                botones[i][j] = new JButton();
                botones[i][j].setFont(new Font("Arial", Font.BOLD, 16));
                botones[i][j].setFocusPainted(false);
                botones[i][j].setBackground(Color.LIGHT_GRAY);

                final int fila = i;
                final int columna = j;

                // Clic izquierdo: destapar
                // Clic derecho: marcar/desmarcar
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

        // Menú superior
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
    }

    /**
     * Coloca aleatoriamente 2*L minas en el tablero.
     */
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

    /**
     * Calcula cuántas minas hay alrededor de cada casilla.
     */
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

    /**
     * Destapa una casilla.
     * Si tiene mina → pierde.
     * Si no tiene mina → muestra número o propaga ceros.
     */
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

        // Propagar si es zona de ceros
        if (minasAlrededor[fila][columna] == 0) {
            destaparVecinas(fila, columna);
        }

        // Verificar victoria
        if (casillasDestapadasSinMina == totalCasillasSinMina) {
            ganar();
        }
    }

    /**
     * Marca o desmarca una casilla con una bandera.
     */
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

    /**
     * Actualiza visualmente un botón destapado.
     */
    private void actualizarBoton(int fila, int columna) {
        JButton btn = botones[fila][columna];
        int valor = minasAlrededor[fila][columna];

        btn.setText(valor == 0 ? "" : String.valueOf(valor));
        btn.setForeground(getColorPorNumero(valor));
        btn.setBackground(Color.WHITE);
        btn.setEnabled(false);
        btn.repaint();
    }

    /**
     * Devuelve un color según el número de minas alrededor.
     */
    private Color getColorPorNumero(int num) {
        return switch (num) {
            case 1 -> Color.BLUE;
            case 2 -> Color.GREEN.darker();
            case 3 -> Color.RED;
            case 4 -> new Color(0, 0, 139);
            case 5 -> Color.MAGENTA.darker();
            case 6 -> Color.CYAN.darker();
            case 7 -> Color.BLACK;
            case 8 -> Color.GRAY;
            default -> Color.BLACK;
        };
    }

    /**
     * Destapa recursivamente las casillas vecinas si están en zona de ceros.
     */
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

    /**
     * Acción al perder: muestra todas las minas.
     */
    private void perder() {
        juegoTerminado = true;
        revelarTodasLasMinas();

        // Mensaje de error
        JOptionPane.showMessageDialog(this,
            "<html><div style='text-align:center; font-family: Arial;'>"
            + "<b><font color='red' size='5'>💥 ¡BOOM! Has pisado una mina.</font></b><br><br>"
            + "El juego ha terminado.<br>"
            + "Se mostraron todas las minas.</div></html>",
            "Derrota", JOptionPane.ERROR_MESSAGE);

        // Preguntar si quiere jugar de nuevo
        preguntarJugarNuevo();
    }

    /**
     * Acción al ganar: verifica que todas las minas estén marcadas.
     */
    private void ganar() {
        juegoTerminado = true;
        revelarTodasLasMinasCorrectamente();

        JOptionPane.showMessageDialog(this,
            "<html><div style='text-align:center; font-family: Arial;'>"
            + "<b><font color='green' size='5'>🎉 ¡Felicidades!</font></b><br><br>"
            + "Has identificado todas las minas correctamente.<br>"
            + "¡Ganaste el juego!</div></html>",
            "¡Victoria!", JOptionPane.INFORMATION_MESSAGE);

        ventanaPrincipal.incrementarGanados();
        preguntarJugarNuevo();
    }

    /**
     * Muestra todas las minas (en rojo/pink si no estaban marcadas).
     */
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

    /**
     * Muestra todas las minas con color de victoria.
     */
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

    /**
     * Pregunta al usuario si desea jugar de nuevo.
     */
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

    /**
     * Reinicia el juego con el mismo tamaño.
     */
    private void confirmarNuevoJuego() {
        dispose();
        SwingUtilities.invokeLater(() -> {
            new TableroJuego(L, ventanaPrincipal);
        });
    }

    /**
     * Cierra el juego y regresa a la ventana principal.
     */
    private void confirmarSalir() {
        int opcion = JOptionPane.showConfirmDialog(this,
            "¿Estás seguro de que deseas salir?",
            "Confirmar salida", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            dispose();
            ventanaPrincipal.setVisible(true);
        }
    }
}