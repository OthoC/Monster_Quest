package com.uce.programacion2.Dungeon_Crawler.GUI;

import java.awt.*;
import javax.swing.*;

public class PanelMenu extends JPanel {

    private final VentanaPrincipal ventana;

    public PanelMenu(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout());
        construir();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Tema.pintarFondo(g, this);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Línea dorada decorativa bajo el header
        g2.setStroke(new BasicStroke(1.2f));
        g2.setColor(new Color(Tema.GOLD.getRed(), Tema.GOLD.getGreen(), Tema.GOLD.getBlue(), 75));
        g2.drawLine(55, 154, getWidth() - 55, 154);
    }

    private void construir() {

        // ── Header ──────────────────────────────────────────────────────
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(BorderFactory.createEmptyBorder(38, 0, 16, 0));

        JLabel titulo = new JLabel("MONSTER QUEST");
        titulo.setFont(new Font("Serif", Font.BOLD, 54));
        titulo.setForeground(Tema.GOLD);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = new JLabel("— Eclipse Realms —");
        sub.setFont(new Font("Serif", Font.ITALIC, 20));
        sub.setForeground(Tema.GOLD_DIM);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(titulo);
        header.add(Box.createVerticalStrut(6));
        header.add(sub);
        add(header, BorderLayout.NORTH);

        // ── Botones ─────────────────────────────────────────────────────
        JPanel centerWrap = new JPanel(new GridBagLayout());
        centerWrap.setOpaque(false);

        JPanel col = new JPanel();
        col.setOpaque(false);
        col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));
        col.setBorder(BorderFactory.createEmptyBorder(8, 0, 28, 0));

        String[] etiquetas = {
            "  ★  Nuevo Juego",
            "  ⚔  Ver Equipo",
            "  ✧  Inventario",
            "  ▶  Explorar",
            "  ✷  Tienda",
            "  ♟  Entrenador",
            "  ✴  Jefe Final",
            "  ✕  Salir"
        };
        boolean[] danger = { false, false, false, false, false, false, false, true };
        JButton[] btns = new JButton[etiquetas.length];

        for (int i = 0; i < etiquetas.length; i++) {
            btns[i] = Tema.crearBoton(etiquetas[i], danger[i]);
            btns[i].setHorizontalAlignment(SwingConstants.LEFT);
            btns[i].setMaximumSize (new Dimension(252, 42));
            btns[i].setPreferredSize(new Dimension(252, 42));
            col.add(btns[i]);
            if (i < etiquetas.length - 1) col.add(Box.createVerticalStrut(8));
        }

        centerWrap.add(col);
        add(centerWrap, BorderLayout.CENTER);

        // ── Footer ──────────────────────────────────────────────────────
        JLabel version = new JLabel("v1.1  •  Programación II  •  UCE  •  2024");
        version.setFont(new Font("SansSerif", Font.PLAIN, 11));
        version.setForeground(new Color(80, 60, 130));
        version.setHorizontalAlignment(SwingConstants.CENTER);
        version.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        add(version, BorderLayout.SOUTH);

        // ── Acciones ────────────────────────────────────────────────────

        btns[0].addActionListener(e -> {
            String nombre = JOptionPane.showInputDialog(this, "Ingresa el nombre del entrenador:");
            if (nombre == null || nombre.isBlank()) return;
            ventana.getJuego().crearJugador(nombre);
            ventana.mostrar("INICIAL");
        });

        btns[1].addActionListener(e -> {
            if (ventana.getJuego().getJugador() == null) {
                JOptionPane.showMessageDialog(this, "Primero inicia una partida."); return;
            }
            ventana.getPanelEquipo().actualizar();
            ventana.mostrar("EQUIPO");
        });

        btns[2].addActionListener(e -> {
            if (ventana.getJuego().getJugador() == null) {
                JOptionPane.showMessageDialog(this, "Primero inicia una partida."); return;
            }
            ventana.getPanelInventario().actualizar();
            ventana.mostrar("INVENTARIO");
        });

        btns[3].addActionListener(e -> {
            if (ventana.getJuego().getJugador() == null) {
                JOptionPane.showMessageDialog(this, "Primero crea un entrenador."); return;
            }
            if (ventana.getJuego().getJugador().getCantidadCriaturas() == 0) {
                JOptionPane.showMessageDialog(this, "Primero elige una criatura inicial."); return;
            }
            ventana.getPanelMapa().actualizarMapa();
            ventana.mostrar("MAPA");
        });

        btns[4].addActionListener(e -> {
            if (ventana.getJuego().getJugador() == null) {
                JOptionPane.showMessageDialog(this, "Primero inicia una partida."); return;
            }
            ventana.getPanelTienda().actualizar();
            ventana.mostrar("TIENDA");
        });

        btns[5].addActionListener(e -> {
            if (ventana.getJuego().getJugador() == null) {
                JOptionPane.showMessageDialog(this, "Primero inicia una partida."); return;
            }
            ventana.getPanelEntrenador().actualizar();
            ventana.mostrar("ENTRENADOR");
        });

        btns[6].addActionListener(e -> {
            if (ventana.getJuego().getJugador() == null) {
                JOptionPane.showMessageDialog(this, "Primero inicia una partida."); return;
            }
            if (ventana.getJuego().getJugador().getCantidadCriaturas() == 0) {
                JOptionPane.showMessageDialog(this, "Primero elige una criatura inicial."); return;
            }
            var batallaJefe = ventana.getJuego().crearBatallaJefe();
            ventana.getPanelBatalla().iniciarBatalla(batallaJefe);
            ventana.mostrar("BATALLA");
        });

        btns[7].addActionListener(e -> System.exit(0));
    }

}
