package com.uce.programacion2.Dungeon_Crawler.GUI;

import java.awt.*;
import javax.swing.*;
import com.uce.programacion2.Dungeon_Crawler.Criaturas.*;

public class PanelInicial extends JPanel {

    private final VentanaPrincipal ventana;

    public PanelInicial(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setOpaque(true);
        setLayout(new BorderLayout());
        construir();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Tema.pintarFondo(g, this);
        // Línea dorada separadora bajo el header
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(Tema.GOLD.getRed(), Tema.GOLD.getGreen(), Tema.GOLD.getBlue(), 70));
        g2.setStroke(new BasicStroke(1f));
        g2.drawLine(60, 100, getWidth() - 60, 100);
    }

    private void construir() {
        // ── Header ──────────────────────────────────────────────────────
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(BorderFactory.createEmptyBorder(22, 0, 10, 0));
        header.add(Tema.titulo("ELIGE TU CRIATURA INICIAL"));
        header.add(Box.createVerticalStrut(4));
        header.add(Tema.subtitulo("Cada criatura tiene una ventaja de tipo diferente — ¡elige sabiamente!"));
        add(header, BorderLayout.NORTH);

        // ── Tarjetas de criatura ─────────────────────────────────────────
        JPanel centro = new JPanel(new GridLayout(1, 3, 20, 20));
        centro.setOpaque(false);
        centro.setBorder(BorderFactory.createEmptyBorder(16, 36, 24, 36));

        centro.add(construirTarjeta(new Pyron(),  "Fuego  |  Ataque: Alto  |  Velocidad: Alta"));
        centro.add(construirTarjeta(new Aquos(),  "Agua   |  Defensa: Alta |  Resistencia: Alta"));
        centro.add(construirTarjeta(new Floran(), "Planta |  Equilibrio entre ataque y defensa"));

        add(centro, BorderLayout.CENTER);

        // ── Footer ──────────────────────────────────────────────────────
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setOpaque(false);
        JButton volver = Tema.crearBoton("← Volver al menú", true);
        volver.setPreferredSize(new Dimension(200, 36));
        volver.addActionListener(e -> ventana.mostrar("MENU"));
        footer.add(volver);
        add(footer, BorderLayout.SOUTH);
    }

    /**
     * Construye una tarjeta seleccionable para una criatura.
     * "muestra" sirve solo para leer stats; al elegir se crea una copia nueva.
     */
    private JPanel construirTarjeta(Criatura muestra, String descripcion) {
        JPanel tarjeta = Tema.crearTarjeta();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBorder(BorderFactory.createEmptyBorder(16, 14, 16, 14));

        // Imagen grande del monstruo
        JLabel imgLbl = new JLabel(ImageUtils.iconoMonstruo(muestra.getNombre(), 160, 160));
        imgLbl.setAlignmentX(CENTER_ALIGNMENT);
        imgLbl.setHorizontalAlignment(SwingConstants.CENTER);

        // Nombre
        JLabel lblNombre = new JLabel(muestra.getNombre(), SwingConstants.CENTER);
        lblNombre.setFont(new Font("Serif", Font.BOLD, 22));
        lblNombre.setForeground(Tema.GOLD);
        lblNombre.setAlignmentX(CENTER_ALIGNMENT);

        // Badge de tipo
        JLabel badge = Tema.badgeTipo(muestra.getTipo());
        badge.setAlignmentX(CENTER_ALIGNMENT);

        // Descripción de la criatura
        JLabel desc = new JLabel("<html><center>" + descripcion + "</center></html>", SwingConstants.CENTER);
        desc.setFont(new Font("SansSerif", Font.ITALIC, 11));
        desc.setForeground(new Color(160, 150, 200));
        desc.setAlignmentX(CENTER_ALIGNMENT);

        // Stats
        StatPanel sp = new StatPanel();
        sp.setAlignmentX(CENTER_ALIGNMENT);
        sp.addStat("HP máximo", String.valueOf(muestra.getVidaMaxima()),
                Tema.VIDA_ALTA);
        sp.addStat("Ataque base", String.valueOf(muestra.getAtaque()),
                new Color(255, 160, 60));
        sp.addStat("Nivel inicial", "1");

        // Botón elegir
        JButton btnElegir = Tema.crearBoton("★  Elegir  ★", false);
        btnElegir.setAlignmentX(CENTER_ALIGNMENT);
        btnElegir.setMaximumSize(new Dimension(160, 38));

        tarjeta.add(imgLbl);
        tarjeta.add(Box.createVerticalStrut(8));
        tarjeta.add(lblNombre);
        tarjeta.add(Box.createVerticalStrut(4));

        JPanel badgePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        badgePanel.setOpaque(false);
        badgePanel.setAlignmentX(CENTER_ALIGNMENT);
        badgePanel.add(badge);
        tarjeta.add(badgePanel);

        tarjeta.add(Box.createVerticalStrut(6));
        tarjeta.add(desc);
        tarjeta.add(Box.createVerticalStrut(10));
        tarjeta.add(sp);
        tarjeta.add(Box.createVerticalStrut(12));
        tarjeta.add(btnElegir);

        btnElegir.addActionListener(e -> {
            if (ventana.getJuego().getJugador() == null) {
                JOptionPane.showMessageDialog(this, "Primero inicia una partida.");
                return;
            }
            if (ventana.getJuego().getJugador().getCantidadCriaturas() > 0) {
                JOptionPane.showMessageDialog(this, "Ya elegiste tu criatura inicial.");
                return;
            }
            Criatura elegida = muestra.copiar();
            ventana.getJuego().getJugador().agregarCriatura(elegida);
            JOptionPane.showMessageDialog(this,
                    "¡Elegiste a " + elegida.getNombre() + "!\n" +
                    "Tipo: " + elegida.getTipo() + "  |  HP: " + elegida.getVidaMaxima() +
                    "  |  Ataque: " + elegida.getAtaque());
            ventana.mostrar("MENU");
        });

        return tarjeta;
    }

}
