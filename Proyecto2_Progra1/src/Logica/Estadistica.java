/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Emesis
 */
public class Estadistica {
    
    private int juegosJugados;
    private int juegosGanados;
    private int juegosPerdidos;
    private static final String ARCHIVO_ESTADISTICAS = "estadisticas.properties";
    
    // Instancia única (patrón Singleton)
    private static Estadistica instancia;
    
    /**
     * Constructor privado para implementar patrón Singleton
     */
    private Estadistica() {
        juegosJugados = 0;
        juegosGanados = 0;
        juegosPerdidos = 0;
        cargarEstadisticas();
    }
    
    /**
     * Obtiene la instancia única de EstadisticasJuego
     * @return La instancia única
     */
    public static Estadistica getInstancia() {
        if (instancia == null) {
            instancia = new Estadistica();
        }
        return instancia;
    }
    
    /**
     * Registra un juego ganado
     */
    public void registrarJuegoGanado() {
        juegosJugados++;
        juegosGanados++;
        guardarEstadisticas();
    }
    
    /**
     * Registra un juego perdido
     */
    public void registrarJuegoPerdido() {
        juegosJugados++;
        juegosPerdidos++;
        guardarEstadisticas();
    }
    
    /**
     * Registra el inicio de un nuevo juego
     * (para cuando se reinicia sin terminar)
     */
    public void registrarNuevoJuego() {
        // Este método se puede usar si quieres contar
        // juegos que se iniciaron pero no se terminaron
    }
    
    /**
     * Calcula el porcentaje de victorias
     * @return Porcentaje de victorias (0-100)
     */
    public double getPorcentajeVictorias() {
        if (juegosJugados == 0) {
            return 0.0;
        }
        return (double) juegosGanados / juegosJugados * 100.0;
    }
    
    /**
     * Calcula el porcentaje de derrotas
     * @return Porcentaje de derrotas (0-100)
     */
    public double getPorcentajeDerrotas() {
        if (juegosJugados == 0) {
            return 0.0;
        }
        return (double) juegosPerdidos / juegosJugados * 100.0;
    }
    
    /**
     * Genera un reporte completo de estadísticas
     * @return String con el reporte formateado
     */
    public String generarReporte() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("=== ESTADÍSTICAS BUSCAMINAS ===\n");
        reporte.append("Juegos jugados: ").append(juegosJugados).append("\n");
        reporte.append("Juegos ganados: ").append(juegosGanados).append("\n");
        reporte.append("Juegos perdidos: ").append(juegosPerdidos).append("\n");
        reporte.append("Porcentaje victorias: ").append(String.format("%.1f%%", getPorcentajeVictorias())).append("\n");
        reporte.append("Porcentaje derrotas: ").append(String.format("%.1f%%", getPorcentajeDerrotas())).append("\n");
        
        if (juegosJugados > 0) {
            reporte.append("\nRacha actual: ");
            if (juegosGanados > juegosPerdidos) {
                reporte.append("¡Más victorias que derrotas!");
            } else if (juegosPerdidos > juegosGanados) {
                reporte.append("Más derrotas que victorias");
            } else {
                reporte.append("Empate entre victorias y derrotas");
            }
        } else {
            reporte.append("\n¡Aún no has jugado ninguna partida!");
        }
        
        return reporte.toString();
    }
    
    /**
     * Genera un reporte corto para mostrar en ventanas emergentes
     * @return String con estadísticas resumidas
     */
    public String generarReporteCorto() {
        return String.format("Jugados: %d | Ganados: %d | Perdidos: %d | Victoria: %.1f%%", 
                juegosJugados, juegosGanados, juegosPerdidos, getPorcentajeVictorias());
    }
    
    /**
     * Resetea todas las estadísticas
     */
    public void resetearEstadisticas() {
        juegosJugados = 0;
        juegosGanados = 0;
        juegosPerdidos = 0;
        guardarEstadisticas();
    }
    
    /**
     * Guarda las estadísticas en un archivo
     */
    private void guardarEstadisticas() {
        Properties props = new Properties();
        props.setProperty("juegosJugados", String.valueOf(juegosJugados));
        props.setProperty("juegosGanados", String.valueOf(juegosGanados));
        props.setProperty("juegosPerdidos", String.valueOf(juegosPerdidos));
        
        try (FileOutputStream fos = new FileOutputStream(ARCHIVO_ESTADISTICAS)) {
            props.store(fos, "Estadísticas del Buscaminas");
        } catch (IOException e) {
            System.err.println("Error al guardar estadísticas: " + e.getMessage());
            // No lanzamos excepción para no interrumpir el juego
        }
    }
    
    /**
     * Carga las estadísticas desde el archivo
     */
    private void cargarEstadisticas() {
        Properties props = new Properties();
        
        try (FileInputStream fis = new FileInputStream(ARCHIVO_ESTADISTICAS)) {
            props.load(fis);
            
            juegosJugados = Integer.parseInt(props.getProperty("juegosJugados", "0"));
            juegosGanados = Integer.parseInt(props.getProperty("juegosGanados", "0"));
            juegosPerdidos = Integer.parseInt(props.getProperty("juegosPerdidos", "0"));
            
        } catch (IOException | NumberFormatException e) {
            // Si no existe el archivo o hay error, empezamos con estadísticas en cero
            juegosJugados = 0;
            juegosGanados = 0;
            juegosPerdidos = 0;
        }
    }
    
    /**
     * Verifica si es la primera vez que se juega
     * @return true si nunca se ha jugado
     */
    public boolean esPrimeraVez() {
        return juegosJugados == 0;
    }
    
    /**
     * Obtiene mensaje de bienvenida personalizado
     * @return Mensaje según las estadísticas del jugador
     */
    public String getMensajeBienvenida() {
        if (esPrimeraVez()) {
            return "¡Bienvenido al Buscaminas!\n¡Tu primera aventura te espera!";
        } else if (juegosGanados > juegosPerdidos) {
            return "¡Bienvenido de vuelta, experto!\nSigues con más victorias que derrotas.";
        } else if (juegosPerdidos > juegosGanados) {
            return "¡No te rindas!\nCada juego es una nueva oportunidad.";
        } else {
            return "¡Bienvenido de vuelta!\nEstás en perfecto equilibrio.";
        }
    }
    
    // Getters
    public int getJuegosJugados() {
        return juegosJugados;
    }
    
    public int getJuegosGanados() {
        return juegosGanados;
    }
    
    public int getJuegosPerdidos() {
        return juegosPerdidos;
    }
    
    /**
     * Método toString para debug
     */
    @Override
    public String toString() {
        return String.format("EstadisticasJuego{jugados=%d, ganados=%d, perdidos=%d}", 
                juegosJugados, juegosGanados, juegosPerdidos);
    }
}
