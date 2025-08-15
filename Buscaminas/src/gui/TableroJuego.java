
package gui;

/**
 *
 * @author Jairo
 */

import Logica.Controlador;
import Logica.EstadoJuego;
import Modelo.Casilla;
import Modelo.Coordenada;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TableroJuego extends JFrame {
    private Controlador controlador;
    private JButton[][] botones;
    private guiPrincipal ventanaPrincipal;
    private int lado;

    public TableroJuego(int L, guiPrincipal principal) {
        this.lado = L;
        this.ventanaPrincipal = principal;
        this.controlador = new Controlador(L);
        this.botones = new JButton[L][L];

        setTitle("Buscaminas " + L + "x" + L);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(L, L, 1, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                JButton btn = new JButton();
                btn.setFont(new Font("Arial", Font.BOLD, 20));
                btn.setFocusable(false);
                btn.setMargin(new Insets(0, 0, 0, 0));
                btn.setPreferredSize(new Dimension(40, 40));
                btn.setHorizontalAlignment(SwingConstants.CENTER);
                btn.setBackground(Color.LIGHT_GRAY);

                botones[i][j] = btn;
                int fila = i, col = j;
                btn.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (controlador.getEstadoJuego() != EstadoJuego.JUGANDO) return;
                        Coordenada c = new Coordenada(fila, col);

                        if (SwingUtilities.isLeftMouseButton(e)) {
                            controlador.destapar(c);
                            actualizarTablero();

                            if (controlador.getEstadoJuego() == EstadoJuego.PERDIDO) {
                                JOptionPane.showMessageDialog(TableroJuego.this, "¡Has perdido!", "Fin del juego", JOptionPane.ERROR_MESSAGE);
                                controlador.revelarMinas();
                                actualizarTablero();

                                ventanaPrincipal.incrementarPerdidos();

                                int opcion = JOptionPane.showConfirmDialog(
                                    TableroJuego.this,
                                    "¿Deseas jugar de nuevo?",
                                    "Jugar de nuevo",
                                    JOptionPane.YES_NO_OPTION
                                );

                                if (opcion == JOptionPane.YES_OPTION) {
                                    dispose();
                                    new TableroJuego(lado, ventanaPrincipal);
                                } else {
                                    cerrarYRegresar();
                                }
                            } else if (juegoGanado()) {
                                JOptionPane.showMessageDialog(TableroJuego.this, "¡Felicidades! ¡Has ganado!", "¡Victoria!", JOptionPane.INFORMATION_MESSAGE);
                                ventanaPrincipal.incrementarGanados();

                                int opcion = JOptionPane.showConfirmDialog(
                                    TableroJuego.this,
                                    "¿Deseas jugar de nuevo?",
                                    "Jugar de nuevo",
                                    JOptionPane.YES_NO_OPTION
                                );

                                if (opcion == JOptionPane.YES_OPTION) {
                                    dispose();
                                    new TableroJuego(lado, ventanaPrincipal);
                                } else {
                                    cerrarYRegresar();
                                }
                            }
                        } else if (SwingUtilities.isRightMouseButton(e)) {
                            controlador.marcar(c);
                            actualizarTablero();
                        }
                    }
                });
                panel.add(btn);
            }
        }

        add(panel, BorderLayout.CENTER);
        crearMenuBar();
        actualizarTablero();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void actualizarTablero() {
        for (int i = 0; i < lado; i++) {
            for (int j = 0; j < lado; j++) {
                Casilla c = controlador.getCasilla(new Coordenada(i, j));
                JButton btn = botones[i][j];

                if (c.isEstaDestapada()) {
                    String text = c.tieneMina() ? "*" : (c.getMinasVecinas() == 0 ? "" : String.valueOf(c.getMinasVecinas()));
                    btn.setText(text);
                    btn.setBackground(Color.WHITE);
                    btn.setEnabled(false);
                } else if (c.isEstaMarcada()) {
                    btn.setText("X");
                    btn.setBackground(Color.YELLOW);
                } else {
                    btn.setText("");
                    btn.setBackground(Color.LIGHT_GRAY);
                }
            }
        }
        revalidate();
        repaint();
    }

    private boolean juegoGanado() {
        for (Coordenada m : controlador.getMinas()) {
            if (!controlador.getCasilla(m).isEstaMarcada()) return false;
        }
        return true;
    }

    private void crearMenuBar() {
        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("Juego");

        JMenuItem nuevo = new JMenuItem("Nuevo Juego");
        nuevo.addActionListener(e -> {
            dispose();
            new TableroJuego(lado, ventanaPrincipal);
        });

        JMenuItem volver = new JMenuItem("Volver al Menú");
        volver.addActionListener(e -> cerrarYRegresar());

        menu.add(nuevo);
        menu.add(volver);
        bar.add(menu);
        setJMenuBar(bar);
    }

    private void cerrarYRegresar() {
        dispose();
        ventanaPrincipal.setVisible(true);
    }
}