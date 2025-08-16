package gui;

/**
 * @author Jairo 
 */

import Logica.Controlador;
import Logica.EstadoJuego;
import Modelo.Casilla;
import Modelo.Coordenada;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.Timer;

public class TableroJuego extends JFrame {
    private Controlador controlador;
    private JButton[][] botones;
    private guiPrincipal ventanaPrincipal;
    private int lado;
    private Timer pulseTimer;
    private Color[] numberColors = {
        new Color(0, 0, 255),     // 1 - Azul
        new Color(0, 128, 0),     // 2 - Verde
        new Color(255, 0, 0),     // 3 - Rojo
        new Color(128, 0, 128),   // 4 - Púrpura
        new Color(128, 0, 0),     // 5 - Marrón
        new Color(64, 224, 208),  // 6 - Turquesa
        new Color(0, 0, 0),       // 7 - Negro
        new Color(128, 128, 128)  // 8 - Gris
    };

    public TableroJuego(int L, guiPrincipal principal) {
        this.lado = L;
        this.ventanaPrincipal = principal;
        this.controlador = new Controlador(L);
        this.botones = new JButton[L][L];

        setupWindow();
        createGameBoard();
        createMenuBar();
        actualizarTablero();
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void setupWindow() {
        setTitle("Buscaminas " + lado + "x" + lado);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
    }
    
    private void createGameBoard() {
        JPanel panel = new JPanel(new GridLayout(lado, lado, 1, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(100, 149, 237)); // Color azul de fondo
        
        createButtons(panel);
        
        add(panel, BorderLayout.CENTER);
    }
    
    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        
        JLabel titleLabel = new JLabel("💣 BUSCAMINAS ELITE 💎", JLabel.CENTER);
        titleLabel.setFont(new Font("Orbitron", Font.BOLD, 24));
        titleLabel.setForeground(new Color(255, 215, 0));
        
        // Efecto de sombra para el título
        titleLabel.setBorder(new AbstractBorder() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Sombra del texto
                g2d.setColor(new Color(0, 0, 0, 100));
                g2d.drawString(((JLabel)c).getText(), x + 2, y + height - 8);
            }
        });
        
        titlePanel.add(titleLabel);
        return titlePanel;
    }
    
    private void createButtons(JPanel gamePanel) {
        for (int i = 0; i < lado; i++) {
            for (int j = 0; j < lado; j++) {
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
                        handleButtonClick(e, fila, col, btn);
                    }
                });
                
                gamePanel.add(btn);
            }
        }
    }
    
    private JButton createStyledButton() {
        JButton btn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Fondo con degradado
                if (isEnabled()) {
                    GradientPaint gradient;
                    if (getBackground() == Color.WHITE) {
                        // Casilla destapada
                        gradient = new GradientPaint(0, 0, Color.WHITE, 0, getHeight(), new Color(245, 245, 245));
                    } else if (getBackground() == Color.YELLOW) {
                        // Casilla marcada
                        gradient = new GradientPaint(0, 0, new Color(255, 223, 0), 0, getHeight(), new Color(255, 193, 7));
                    } else {
                        // Casilla normal
                        gradient = new GradientPaint(0, 0, new Color(189, 195, 199), 0, getHeight(), new Color(149, 165, 166));
                    }
                    g2d.setPaint(gradient);
                    g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                }
                
                // Borde con efecto 3D
                g2d.setColor(new Color(255, 255, 255, 100));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                
                // Texto del botón
                if (getText() != null && !getText().isEmpty()) {
                    g2d.setFont(getFont());
                    FontMetrics fm = g2d.getFontMetrics();
                    int textWidth = fm.stringWidth(getText());
                    int textHeight = fm.getHeight();
                    
                    // Sombra del texto
                    g2d.setColor(new Color(0, 0, 0, 50));
                    g2d.drawString(getText(), 
                        (getWidth() - textWidth) / 2 + 1, 
                        (getHeight() + textHeight) / 2 - 3 + 1);
                    
                    // Texto principal
                    g2d.setColor(getForeground());
                    g2d.drawString(getText(), 
                        (getWidth() - textWidth) / 2, 
                        (getHeight() + textHeight) / 2 - 3);
                }
            }
        };
        
        btn.setFont(new Font("Orbitron", Font.BOLD, 16));
        btn.setFocusable(false);
        btn.setPreferredSize(new Dimension(45, 45));
        btn.setBackground(new Color(189, 195, 199));
        btn.setForeground(Color.BLACK);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        
        return btn;
    }
    
    private void handleButtonClick(MouseEvent e, int fila, int col, JButton btn) {
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
    
    private void handleGameLost() {
        // Efecto de explosión
        showExplosionEffect();
        
        controlador.revelarMinas();
        actualizarTablero();
        ventanaPrincipal.incrementarPerdidos();
        
        Timer delay = new Timer(1000, e -> {
            int opcion = JOptionPane.showConfirmDialog(
                TableroJuego.this,
                "💥 ¡Oh no! ¡Has encontrado una mina! 💥\n¿Deseas intentar de nuevo?",
                "¡Explosión!",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.ERROR_MESSAGE
            );
            
            if (opcion == JOptionPane.YES_OPTION) {
                dispose();
                new TableroJuego(lado, ventanaPrincipal);
            } else {
                cerrarYRegresar();
            }
        });
        delay.setRepeats(false);
        delay.start();
    }
    
    private void handleGameWon() {
        // Efecto de victoria
        showVictoryEffect();
        
        ventanaPrincipal.incrementarGanados();
        
        Timer delay = new Timer(2000, e -> {
            int opcion = JOptionPane.showConfirmDialog(
                TableroJuego.this,
                "🎉 ¡INCREÍBLE! ¡Has ganado! 🎉\n¡Eres un maestro del Buscaminas!\n¿Quieres jugar otra partida?",
                "¡VICTORIA!",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE
            );
            
            if (opcion == JOptionPane.YES_OPTION) {
                dispose();
                new TableroJuego(lado, ventanaPrincipal);
            } else {
                cerrarYRegresar();
            }
        });
        delay.setRepeats(false);
        delay.start();
    }
    
    private void actualizarTablero() {
        for (int i = 0; i < lado; i++) {
            for (int j = 0; j < lado; j++) {
                Casilla c = controlador.getCasilla(new Coordenada(i, j));
                JButton btn = botones[i][j];
                
                if (c.isEstaDestapada()) {
                    if (c.tieneMina()) {
                        btn.setText("*");
                        btn.setBackground(Color.WHITE);
                        btn.setForeground(Color.BLACK);
                    } else {
                        int minas = c.getMinasVecinas();
                        if (minas == 0) {
                            btn.setText("");
                        } else {
                            btn.setText(String.valueOf(minas));
                            btn.setForeground(numberColors[minas - 1]); // Colores distintos para cada número
                        }
                        btn.setBackground(Color.WHITE);
                    }
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
    
    private void animateButtonClick(JButton btn) {
        Timer timer = new Timer(50, null);
        timer.addActionListener(e -> {
            btn.setBackground(btn.getBackground().brighter());
            Timer restore = new Timer(100, evt -> {
                btn.setBackground(btn.getBackground().darker());
                timer.stop();
            });
            restore.setRepeats(false);
            restore.start();
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    private void animateButtonHover(JButton btn, boolean entering) {
        Timer timer = new Timer(30, null);
        final int steps = 5;
        final int[] step = {0};
        
        Color originalColor = btn.getBackground();
        Color targetColor = entering ? originalColor.brighter() : originalColor;
        
        timer.addActionListener(e -> {
            if (step[0] >= steps) {
                timer.stop();
                return;
            }
            
            float ratio = (float) step[0] / steps;
            if (!entering) ratio = 1.0f - ratio;
            
            int r = (int) (originalColor.getRed() + (targetColor.getRed() - originalColor.getRed()) * ratio);
            int g = (int) (originalColor.getGreen() + (targetColor.getGreen() - originalColor.getGreen()) * ratio);
            int b = (int) (originalColor.getBlue() + (targetColor.getBlue() - originalColor.getBlue()) * ratio);
            
            btn.setBackground(new Color(Math.max(0, Math.min(255, r)), 
                                       Math.max(0, Math.min(255, g)), 
                                       Math.max(0, Math.min(255, b))));
            step[0]++;
            repaint();
        });
        timer.start();
    }
    
    private void animateReveal(JButton btn) {
        Timer timer = new Timer(50, null);
        final int[] scale = {0};
        
        timer.addActionListener(e -> {
            if (scale[0] >= 10) {
                timer.stop();
                return;
            }
            
            // Efecto de escala
            double scaleValue = 1.0 + (Math.sin(scale[0] * 0.3) * 0.1);
            btn.setFont(btn.getFont().deriveFont((float)(16 * scaleValue)));
            scale[0]++;
            repaint();
        });
        timer.start();
    }
    
    private void showExplosionEffect() {
        // Efecto visual de explosión con colores rojos
        Timer explosionTimer = new Timer(100, null);
        final int[] intensity = {0};
        
        explosionTimer.addActionListener(e -> {
            if (intensity[0] >= 10) {
                explosionTimer.stop();
                return;
            }
            
            Color explosionColor = new Color(255, intensity[0] * 20, 0, 100);
            getContentPane().setBackground(explosionColor);
            intensity[0]++;
            repaint();
        });
        explosionTimer.start();
    }
    
    private void showVictoryEffect() {
        // Efecto de victoria con colores dorados
        Timer victoryTimer = new Timer(200, null);
        final int[] phase = {0};
        
        victoryTimer.addActionListener(e -> {
            if (phase[0] >= 10) {
                victoryTimer.stop();
                return;
            }
            
            // Parpadeo dorado
            Color goldColor = new Color(255, 215, 0, 50 + (phase[0] % 2) * 50);
            
            // Aplicar efecto a todos los botones
            for (int i = 0; i < lado; i++) {
                for (int j = 0; j < lado; j++) {
                    if (botones[i][j].isEnabled()) {
                        botones[i][j].setBackground(goldColor);
                    }
                }
            }
            
            phase[0]++;
            repaint();
        });
        victoryTimer.start();
    }
    
    private void startPulseAnimation() {
        pulseTimer = new Timer(2000, e -> {
            // Efecto de pulso sutil en el título
            Component titlePanel = ((JPanel)getContentPane().getComponent(0)).getComponent(0);
            titlePanel.repaint();
        });
        pulseTimer.start();
    }
    
    private boolean juegoGanado() {
        for (Coordenada m : controlador.getMinas()) {
            if (!controlador.getCasilla(m).isEstaMarcada()) return false;
        }
        return true;
    }
    
    private void createMenuBar() {
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