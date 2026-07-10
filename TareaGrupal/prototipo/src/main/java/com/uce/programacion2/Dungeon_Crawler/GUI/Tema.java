package com.uce.programacion2.Dungeon_Crawler.GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * Paleta de colores, fuentes y componentes reutilizables del tema visual
 * "oscuro fantasy" (fondo degradado violeta, títulos dorados, botones
 * redondeados con efecto hover, badges de tipo de criatura).
 */
public final class Tema {

    // ── Paleta base ────────────────────────────────────────────────────────
    public static final Color BG_TOP      = new Color(8,   4,  25);
    public static final Color BG_BOT      = new Color(22,  9,  58);
    public static final Color GOLD        = new Color(255, 200, 50);
    public static final Color GOLD_DIM    = new Color(165, 115, 18);
    public static final Color BTN_NORMAL  = new Color(18,  10,  48);
    public static final Color BTN_HOVER   = new Color(46,  22,  98);
    public static final Color BTN_BORDER  = new Color(108, 58, 210);
    public static final Color BTN_TEXT    = new Color(215, 195, 255);
    public static final Color RED_NORMAL  = new Color(95,  14,  14);
    public static final Color RED_HOVER   = new Color(155, 32,  32);
    public static final Color RED_BORDER  = new Color(185, 48,  48);
    public static final Color TEXTO_CLARO = new Color(215, 195, 255);
    public static final Color PANEL_OSCURO = new Color(18,  10,  48);
    public static final Color CARD_BG     = new Color(255, 255, 255, 14);
    public static final Color CARD_BORDER = BTN_BORDER;

    // ── Barras de vida ─────────────────────────────────────────────────────
    public static final Color VIDA_ALTA   = new Color(70,  200, 110);
    public static final Color VIDA_MEDIA  = new Color(230, 190,  50);
    public static final Color VIDA_BAJA   = new Color(210,  60,  60);

    // ── Fuentes ────────────────────────────────────────────────────────────
    public static final Font FUENTE_TITULO    = new Font("Serif",    Font.BOLD,   30);
    public static final Font FUENTE_SUBTITULO = new Font("Serif",    Font.ITALIC, 16);
    public static final Font FUENTE_NOMBRE    = new Font("Serif",    Font.BOLD,   20);
    public static final Font FUENTE_STAT      = new Font("SansSerif",Font.PLAIN,  13);
    public static final Font FUENTE_BOTON     = new Font("Serif",    Font.BOLD,   15);
    public static final Font FUENTE_REGISTRO  = new Font("Consolas", Font.PLAIN,  13);

    private Tema() {}

    // ── Fondos ─────────────────────────────────────────────────────────────

    /**
     * Pinta el fondo degradado violeta + cuadrícula sutil de todos los paneles.
     * Llamar desde paintComponent(Graphics) tras super.paintComponent(g).
     */
    public static void pintarFondo(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(new GradientPaint(0, 0, BG_TOP, 0, c.getHeight(), BG_BOT));
        g2.fillRect(0, 0, c.getWidth(), c.getHeight());
        g2.setColor(new Color(90, 50, 190, 22));
        for (int y = 0; y < c.getHeight(); y += 50) g2.drawLine(0, y, c.getWidth(), y);
        for (int x = 0; x < c.getWidth();  x += 80) g2.drawLine(x, 0, x, c.getHeight());
    }

    // ── Botones ────────────────────────────────────────────────────────────

    /** Botón redondeado con borde violeta o rojo y efecto hover. */
    public static JButton crearBoton(String texto, boolean danger) {
        Color bgNormal = danger ? RED_NORMAL : BTN_NORMAL;
        Color bgHover  = danger ? RED_HOVER  : BTN_HOVER;
        Color border   = danger ? RED_BORDER : BTN_BORDER;
        boolean[] hovered = { false };

        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hovered[0] ? bgHover : bgNormal);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(border);
                g2.setStroke(new BasicStroke(hovered[0] ? 2f : 1.2f));
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 10, 10);
                if (hovered[0]) {
                    g2.setColor(new Color(border.getRed(), border.getGreen(), border.getBlue(), 55));
                    g2.setStroke(new BasicStroke(5f));
                    g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 10, 10);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { hovered[0] = true;  btn.repaint(); }
            @Override public void mouseExited (MouseEvent e) { hovered[0] = false; btn.repaint(); }
        });

        btn.setForeground(BTN_TEXT);
        btn.setFont(FUENTE_BOTON);
        btn.setHorizontalAlignment(SwingConstants.CENTER);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        return btn;
    }

    // ── Etiquetas ──────────────────────────────────────────────────────────

    public static JLabel titulo(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        lbl.setFont(FUENTE_TITULO);
        lbl.setForeground(GOLD);
        return lbl;
    }

    public static JLabel subtitulo(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        lbl.setFont(FUENTE_SUBTITULO);
        lbl.setForeground(GOLD_DIM);
        return lbl;
    }

    public static JLabel texto(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(TEXTO_CLARO);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 15));
        return lbl;
    }

    public static JLabel stat(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(TEXTO_CLARO);
        lbl.setFont(FUENTE_STAT);
        return lbl;
    }

    // ── Badge de tipo ──────────────────────────────────────────────────────

    /**
     * Crea una etiqueta con fondo coloreado según el tipo de criatura,
     * estilo "badge" redondeado (Fuego = naranja, Agua = azul, etc.).
     */
    public static JLabel badgeTipo(String tipo) {
        Color color = ImageUtils.colorDeTipo(tipo);
        JLabel lbl = new JLabel(tipo, SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 180));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
                g2.setColor(color.brighter());
                g2.setStroke(new BasicStroke(1.2f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getHeight(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        lbl.setOpaque(false);
        lbl.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));
        return lbl;
    }

    // ── Paneles decorativos ────────────────────────────────────────────────

    /**
     * Panel semitransparente con borde violeta redondeado —
     * úsalo como "tarjeta" contenedora de contenido.
     */
    public static JPanel crearTarjeta() {
        JPanel p = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(CARD_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.setColor(CARD_BORDER);
                g2.setStroke(new BasicStroke(1.2f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        p.setOpaque(false);
        return p;
    }

    /** Borde decorativo dorado con texto de sección. */
    public static Border bordeTitulado(String titulo) {
        return BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BTN_BORDER, 1),
                titulo,
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.TOP,
                new Font("Serif", Font.BOLD, 13),
                GOLD_DIM);
    }

    // ── Barras de vida ─────────────────────────────────────────────────────

    public static void estilizarBarraVida(JProgressBar barra) {
        barra.setStringPainted(true);
        barra.setForeground(colorSegunVida(barra));
        barra.setBackground(new Color(30, 15, 50));
        barra.setBorder(BorderFactory.createLineBorder(new Color(60, 40, 90), 1));
        barra.addChangeListener(e -> barra.setForeground(colorSegunVida(barra)));
    }

    private static Color colorSegunVida(JProgressBar b) {
        if (b.getMaximum() <= 0) return VIDA_ALTA;
        double pct = (double) b.getValue() / b.getMaximum();
        if (pct <= 0.25) return VIDA_BAJA;
        if (pct <= 0.5)  return VIDA_MEDIA;
        return VIDA_ALTA;
    }

    // ── Scroll pane oscuro ─────────────────────────────────────────────────

    public static JScrollPane scrollOscuro(JComponent vista) {
        JScrollPane sp = new JScrollPane(vista);
        sp.setOpaque(false);
        sp.getViewport().setOpaque(false);
        sp.setBorder(BorderFactory.createLineBorder(BTN_BORDER, 1));
        sp.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override protected void configureScrollBarColors() {
                thumbColor  = BTN_BORDER;
                trackColor  = PANEL_OSCURO;
            }
        });
        return sp;
    }

}
