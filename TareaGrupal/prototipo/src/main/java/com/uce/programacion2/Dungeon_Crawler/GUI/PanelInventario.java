package com.uce.programacion2.Dungeon_Crawler.GUI;

import java.awt.*;
import javax.swing.*;
import com.uce.programacion2.Dungeon_Crawler.Objetos.Inventario;
import com.uce.programacion2.Dungeon_Crawler.Objetos.Objeto;

public class PanelInventario extends JPanel {

    private final VentanaPrincipal ventana;
    private final JPanel panelItems;
    private final JLabel lblResumen;

    public PanelInventario(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setOpaque(true);
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(14, 20, 10, 20));

        // ── Header ──────────────────────────────────────────────────────
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.add(Tema.titulo("INVENTARIO"));
        header.add(Tema.subtitulo("Objetos disponibles del entrenador"));
        lblResumen = Tema.subtitulo("");
        lblResumen.setForeground(new Color(130, 200, 130));
        header.add(lblResumen);
        add(header, BorderLayout.NORTH);

        // ── Lista de items ───────────────────────────────────────────────
        panelItems = new JPanel();
        panelItems.setOpaque(false);
        panelItems.setLayout(new WrapLayout(FlowLayout.LEFT, 12, 10));

        JScrollPane scroll = Tema.scrollOscuro(panelItems);
        add(scroll, BorderLayout.CENTER);

        // ── Footer ──────────────────────────────────────────────────────
        JButton volver = Tema.crearBoton("← Volver", true);
        volver.setPreferredSize(new Dimension(160, 36));
        volver.addActionListener(e -> ventana.mostrar("MENU"));
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setOpaque(false);
        footer.add(volver);
        add(footer, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Tema.pintarFondo(g, this);
    }

    public void actualizar() {
        panelItems.removeAll();

        Inventario inv = ventana.getJuego().getJugador().getInventario();

        if (inv.getCantidad() == 0) {
            JLabel vacio = Tema.subtitulo("El inventario está vacío.\nCompra objetos en la tienda.");
            vacio.setHorizontalAlignment(SwingConstants.CENTER);
            panelItems.setLayout(new GridBagLayout());
            panelItems.add(vacio);
        } else {
            panelItems.setLayout(new WrapLayout(FlowLayout.LEFT, 12, 10));
            for (int i = 0; i < inv.getCantidad(); i++) {
                panelItems.add(crearItemCard(inv.obtenerObjeto(i), i + 1));
            }
        }

        int pociones = 0, esferas = 0;
        for (int i = 0; i < inv.getCantidad(); i++) {
            String n = inv.obtenerObjeto(i).getNombre();
            if ("Poción".equals(n)) pociones++;
            else if ("Esfera".equals(n)) esferas++;
        }
        lblResumen.setText("  Pociones: " + pociones + "   ·   Esferas: " + esferas
                + "   ·   Total: " + inv.getCantidad());

        panelItems.revalidate();
        panelItems.repaint();
    }

    private JPanel crearItemCard(Objeto obj, int numero) {
        JPanel card = Tema.crearTarjeta();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
        card.setPreferredSize(new Dimension(150, 130));

        // Icono del objeto (dibujado con Java2D)
        JLabel icono = new JLabel(crearIconoObjeto(obj.getNombre(), 48, 48));
        icono.setAlignmentX(CENTER_ALIGNMENT);
        icono.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblNum = new JLabel("#" + numero, SwingConstants.CENTER);
        lblNum.setFont(new Font("SansSerif", Font.PLAIN, 10));
        lblNum.setForeground(Tema.GOLD_DIM);
        lblNum.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblNombre = new JLabel(obj.getNombre(), SwingConstants.CENTER);
        lblNombre.setFont(new Font("Serif", Font.BOLD, 14));
        lblNombre.setForeground(Tema.GOLD);
        lblNombre.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblDesc = new JLabel(descripcionObjeto(obj.getNombre()), SwingConstants.CENTER);
        lblDesc.setFont(new Font("SansSerif", Font.ITALIC, 10));
        lblDesc.setForeground(new Color(160, 150, 200));
        lblDesc.setAlignmentX(CENTER_ALIGNMENT);

        card.add(icono);
        card.add(Box.createVerticalStrut(2));
        card.add(lblNum);
        card.add(Box.createVerticalStrut(3));
        card.add(lblNombre);
        card.add(Box.createVerticalStrut(2));
        card.add(lblDesc);

        return card;
    }

    private ImageIcon crearIconoObjeto(String nombre, int w, int h) {
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(w, h,
                java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if ("Poción".equals(nombre)) {
            // Frasco verde
            g.setColor(new Color(40, 160, 80));
            g.fillRoundRect(w/4, h/4, w/2, h/2, 8, 8);
            g.setColor(new Color(80, 210, 120));
            g.fillRoundRect(w/4, h/4, w/2, h/4, 8, 8);
            g.setColor(new Color(80, 80, 80));
            g.fillRoundRect(w*3/8, h/8, w/4, h/6, 4, 4);
            g.setColor(new Color(200, 255, 200, 160));
            g.fillOval(w/3, h/3, w/6, h/6);
        } else if ("Esfera".equals(nombre)) {
            // Esfera naranja/roja (como Pokéball simplificada)
            Color top = new Color(220, 80, 30);
            Color bot = new Color(230, 230, 230);
            g.setColor(top);
            g.fillOval(2, 2, w-4, (h-4)/2);
            g.setColor(bot);
            g.fillOval(2, h/2, w-4, (h-4)/2);
            g.setColor(Color.DARK_GRAY);
            g.setStroke(new BasicStroke(2f));
            g.drawLine(4, h/2, w-4, h/2);
            g.drawOval(2, 2, w-4, h-4);
            g.setColor(Color.DARK_GRAY);
            g.fillOval(w/2-6, h/2-6, 12, 12);
            g.setColor(Color.WHITE);
            g.fillOval(w/2-4, h/2-4, 8, 8);
            g.setColor(new Color(255,255,255,180));
            g.fillOval(w/4, h/5, w/6, h/6);
        } else {
            // Objeto genérico
            g.setColor(Tema.GOLD_DIM);
            g.fillOval(4, 4, w-8, h-8);
            g.setColor(Tema.GOLD);
            g.setFont(new Font("Serif", Font.BOLD, 14));
            g.drawString("?", w/2-4, h/2+5);
        }
        g.dispose();
        return new ImageIcon(img);
    }

    private String descripcionObjeto(String nombre) {
        return switch (nombre) {
            case "Poción" -> "Restaura 30 HP";
            case "Esfera" -> "Captura monstruos";
            default       -> "Objeto especial";
        };
    }

    /** FlowLayout que envuelve componentes en la siguiente línea automáticamente. */
    private static class WrapLayout extends FlowLayout {
        WrapLayout(int align, int hgap, int vgap) { super(align, hgap, vgap); }

        @Override
        public Dimension preferredLayoutSize(Container target) {
            return layoutSize(target, true);
        }

        @Override
        public Dimension minimumLayoutSize(Container target) {
            Dimension min = layoutSize(target, false);
            min.width -= (getHgap() + 1);
            return min;
        }

        private Dimension layoutSize(Container target, boolean preferred) {
            synchronized (target.getTreeLock()) {
                int targetWidth = target.getSize().width;
                if (targetWidth == 0) targetWidth = Integer.MAX_VALUE;

                int hgap = getHgap(), vgap = getVgap();
                Insets insets = target.getInsets();
                int maxWidth = targetWidth - (insets.left + insets.right + hgap * 2);

                int x = 0, y = insets.top + vgap, rowHeight = 0;
                for (int i = 0; i < target.getComponentCount(); i++) {
                    Component m = target.getComponent(i);
                    if (m.isVisible()) {
                        Dimension d = preferred ? m.getPreferredSize() : m.getMinimumSize();
                        if (x == 0 || (x + d.width) <= maxWidth) {
                            if (x > 0) x += hgap;
                            x += d.width;
                            rowHeight = Math.max(rowHeight, d.height);
                        } else {
                            x = d.width;
                            y += vgap + rowHeight;
                            rowHeight = d.height;
                        }
                    }
                }
                y += rowHeight + vgap + insets.bottom;
                return new Dimension(targetWidth, y);
            }
        }
    }

}
