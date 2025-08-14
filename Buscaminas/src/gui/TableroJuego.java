/*
 * TableroJuego.java
 * Universidad Técnica Nacional - ITI Programación I
 * Proyecto Buscaminas - GUI Conectada con Lógica
 */
package gui;

import java.util.List;
import Logica.*;
import Modelo.Coordenada;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Ventana principal del juego Buscaminas.
 * Se conecta con el Controlador para usar la lógica del juego.
 */
public class TableroJuego extends JFrame {

    private final int L;
    private final guiPrincipal ventanaPrincipal;
    private final Controlador controlador;

    private JButton[][] botones;
    private boolean juegoTerminado;

    public TableroJuego(int L, guiPrincipal ventanaPrincipal) {
        this.L = L;
        this.ventanaPrincipal = ventanaPrincipal;
        this.controlador = new Controlador();
        this.juegoTerminado = false;

        // Validar tamaño
        if (L < 1 || L > 50) {
            JOptionPane.showMessageDialog(this,
                "El tamaño debe estar entre 1 y 50.",
                "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            ventanaPrincipal.setVisible(true);
            return;
        }

        // Iniciar juego
        if (!controlador.iniciarNuevoJuego(L)) {
            JOptionPane.showMessageDialog(this,
                "No se pudo iniciar el juego con tamaño: " + L,
                "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            ventanaPrincipal.setVisible(true);
            return;
        }

        configurarVentana();
        crearInterfaz();
        pack();
        setSize(Math.min(800, L * 60), Math.min(600, L * 60));
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void configurarVentana() {
        setTitle("Buscaminas - " + L + "x" + L);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    private void crearInterfaz() {
        JPanel panelTablero = new JPanel(new GridLayout(L, L));
        panelTablero.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        botones = new JButton[L][L];

        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                botones[i][j] = new JButton();
                botones[i][j].setFont(new Font("Arial", Font.BOLD, 16));
                botones[i][j].setFocusPainted(false);
                botones[i][j].setBackground(Color.LIGHT_GRAY);
                botones[i][j].setEnabled(true);
                
                final int fila = i;
                final int columna = j;

                botones[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (juegoTerminado) return;

                        if (e.getButton() == MouseEvent.BUTTON1) {
                            destapar(fila, columna);
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            marcar(fila, columna);
                        }
                    }
                });

                panelTablero.add(botones[i][j]);
            }
        }

        add(panelTablero, BorderLayout.CENTER);

        // Menú
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

   private void destapar(int fila, int columna) {
    ResultadoMovimiento rm = controlador.manejarEntradaUsuario(fila + 1, columna + 1, "destapar");
    actualizarCasilla(fila, columna);

    if (!rm.isExito()) {
        JOptionPane.showMessageDialog(this, rm.getMensaje(), "Error", JOptionPane.WARNING_MESSAGE);
        return;
    }

    EstadoJuego estado = rm.getEstadoJuego();
    if (estado == EstadoJuego.PERDIDO) {
        revelarTodasLasMinas();
        JOptionPane.showMessageDialog(this,
            "<html><b><font color='red' size='5'>💥 ¡BOOM! Has perdido.</font></b><br>" +
            "El juego ha terminado.</html>",
            "Derrota", JOptionPane.ERROR_MESSAGE);
        ventanaPrincipal.incrementarPerdidos();
        preguntarJugarNuevo();
    } else if (estado == EstadoJuego.GANADO) {
        revelarTodasLasMinasCorrectamente();
        JOptionPane.showMessageDialog(this,
            "<html><b><font color='green' size='5'>🎉 ¡Felicidades! Has ganado.</font></b></html>",
            "Victoria", JOptionPane.INFORMATION_MESSAGE);
        ventanaPrincipal.incrementarGanados();
        preguntarJugarNuevo();
    }
}

    private void marcar(int fila, int columna) {
    ResultadoMovimiento rm = controlador.manejarEntradaUsuario(fila + 1, columna + 1, "marcar");
    actualizarCasilla(fila, columna); 

    if (!rm.isExito()) {
        rm = controlador.manejarEntradaUsuario(fila + 1, columna + 1, "desmarcar");
        actualizarCasilla(fila, columna);
    }
}

    private void actualizarCasilla(int fila, int columna) {
    InfoCasilla info = controlador.getInfo(fila + 1, columna + 1);
    JButton btn = botones[fila][columna];

    if (info.isEstaDestapada()) {
        String valor = info.getValor();
        if (valor.isEmpty()) {
            valor = "0";
        }
        btn.setText(valor);
        btn.setForeground(getColorPorNumero(valor));
        btn.setBackground(Color.WHITE);
        btn.setEnabled(false);
    } else if (info.isEstaMarcada()) {
        btn.setText("🚩");
        btn.setForeground(Color.RED);
        btn.setBackground(Color.YELLOW);
        btn.setEnabled(true); 
    } else {
        btn.setText("");
        btn.setBackground(Color.LIGHT_GRAY);
        btn.setEnabled(true); 
    }
    btn.repaint();
}

    private Color getColorPorNumero(String valor) {
        if (valor == null || valor.isEmpty()) {
            return Color.BLACK; // Por si acaso
        }
        return switch (valor) {
            case "1" -> Color.BLUE;
            case "2" -> Color.GREEN.darker();
            case "3" -> Color.RED;
            case "4" -> new Color(0, 0, 139);
            case "5" -> Color.MAGENTA.darker();
            case "6" -> Color.CYAN.darker();
            case "7" -> Color.BLACK;
            case "8" -> Color.GRAY;
            default -> Color.BLACK;
        };
    }

    private void revelarTodasLasMinas() {
        List<Coordenada> minas = controlador.obtenerMinas();
        for (Coordenada c : minas) {
            int i = c.getFila() - 1; 
            int j = c.getColumna() - 1;
            if (i >= 0 && i < L && j >= 0 && j < L) {
                JButton btn = botones[i][j];
                if (!controlador.getInfo(c.getFila(), c.getColumna()).isEstaMarcada()) {
                    btn.setText("💣");
                    btn.setBackground(Color.PINK);
                    btn.setForeground(Color.BLACK);
                }
            }
        }
        repaint();
    }

    private void revelarTodasLasMinasCorrectamente() {
        List<Coordenada> minas = controlador.obtenerMinas();
        for (Coordenada c : minas) {
            int i = c.getFila() - 1; // Convertir a índice 0
            int j = c.getColumna() - 1;
            if (i >= 0 && i < L && j >= 0 && j < L) {
                JButton btn = botones[i][j];
                btn.setText("💣");
                btn.setBackground(Color.GREEN);
                btn.setForeground(Color.WHITE);
            }
        }
        repaint();
    }

    private void preguntarJugarNuevo() {
        juegoTerminado = true;
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
        SwingUtilities.invokeLater(() -> new TableroJuego(L, ventanaPrincipal));
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
}