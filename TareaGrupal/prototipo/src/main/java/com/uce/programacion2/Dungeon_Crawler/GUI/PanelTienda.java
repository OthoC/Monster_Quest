package com.uce.programacion2.Dungeon_Crawler.GUI;

import java.awt.*;
import javax.swing.*;
import com.uce.programacion2.Dungeon_Crawler.Objetos.Esfera;
import com.uce.programacion2.Dungeon_Crawler.Objetos.Pocion;
import com.uce.programacion2.Dungeon_Crawler.Personajes.Jugador;

public class PanelTienda extends JPanel {

    private final VentanaPrincipal ventana;
    private final JLabel lblDinero;

    public PanelTienda(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setOpaque(true);
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 30, 12, 30));

        // ── Header ──────────────────────────────────────────────────────
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.add(Tema.titulo("★  TIENDA  ★"));
        header.add(Box.createVerticalStrut(2));
        header.add(Tema.subtitulo("Compra pociones y esferas para tus aventuras"));
        lblDinero = new JLabel(" ", SwingConstants.CENTER);
        lblDinero.setFont(new Font("Serif", Font.BOLD, 18));
        lblDinero.setForeground(Tema.GOLD);
        lblDinero.setAlignmentX(CENTER_ALIGNMENT);
        header.add(Box.createVerticalStrut(4));
        header.add(lblDinero);
        add(header, BorderLayout.NORTH);

        // ── Productos ────────────────────────────────────────────────────
        JPanel productos = new JPanel(new GridLayout(1, 2, 30, 0));
        productos.setOpaque(false);
        productos.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        productos.add(crearProducto(
                "Poción",
                "Restaura 30 puntos de vida\na una de tus criaturas\ndurante el combate.",
                20,
                new Color(40, 160, 80)));

        productos.add(crearProducto(
                "Esfera",
                "Lanza esta esfera para\nintentar capturar una criatura\nsalvaje en batalla.",
                15,
                new Color(220, 80, 30)));

        add(productos, BorderLayout.CENTER);

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
        // Línea dorada bajo header
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(Tema.GOLD.getRed(), Tema.GOLD.getGreen(), Tema.GOLD.getBlue(), 60));
        g2.setStroke(new BasicStroke(1f));
        g2.drawLine(40, 148, getWidth() - 40, 148);
    }

    public void actualizar() {
        Jugador j = ventana.getJuego().getJugador();
        lblDinero.setText("Dinero disponible:  $ " + j.getDinero());
    }

    private JPanel crearProducto(String nombre, String descripcion, int precio, Color color) {
        JPanel card = Tema.crearTarjeta();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Imagen del objeto
        JLabel imgLbl = new JLabel(crearIconoProducto(nombre, color, 90, 90));
        imgLbl.setAlignmentX(CENTER_ALIGNMENT);
        imgLbl.setHorizontalAlignment(SwingConstants.CENTER);

        // Nombre
        JLabel lblN = new JLabel(nombre, SwingConstants.CENTER);
        lblN.setFont(new Font("Serif", Font.BOLD, 22));
        lblN.setForeground(Tema.GOLD);
        lblN.setAlignmentX(CENTER_ALIGNMENT);

        // Descripción
        JLabel lblD = new JLabel(
                "<html><center>" + descripcion.replace("\n", "<br>") + "</center></html>",
                SwingConstants.CENTER);
        lblD.setFont(new Font("SansSerif", Font.ITALIC, 13));
        lblD.setForeground(new Color(180, 165, 220));
        lblD.setAlignmentX(CENTER_ALIGNMENT);

        // Precio
        JLabel lblP = new JLabel("$ " + precio, SwingConstants.CENTER);
        lblP.setFont(new Font("Serif", Font.BOLD, 20));
        lblP.setForeground(new Color(100, 220, 100));
        lblP.setAlignmentX(CENTER_ALIGNMENT);

        // Botón comprar
        JButton btnComprar = Tema.crearBoton("Comprar  →", false);
        btnComprar.setAlignmentX(CENTER_ALIGNMENT);
        btnComprar.setMaximumSize(new Dimension(160, 38));
        btnComprar.addActionListener(e -> comprar(nombre, precio));

        card.add(imgLbl);
        card.add(Box.createVerticalStrut(10));
        card.add(lblN);
        card.add(Box.createVerticalStrut(8));
        card.add(lblD);
        card.add(Box.createVerticalStrut(12));
        card.add(lblP);
        card.add(Box.createVerticalStrut(12));
        card.add(btnComprar);

        return card;
    }

    private void comprar(String nombre, int precio) {
        Jugador jugador = ventana.getJuego().getJugador();
        if (!jugador.gastarDinero(precio)) {
            JOptionPane.showMessageDialog(this, "No tienes suficiente dinero.\nNecesitas $" + precio + ".");
            return;
        }
        switch (nombre) {
            case "Poción" -> jugador.getInventario().agregarObjeto(new Pocion());
            case "Esfera" -> jugador.getInventario().agregarObjeto(new Esfera());
        }
        actualizar();
        JOptionPane.showMessageDialog(this, "¡Compraste una " + nombre + "!\nDinero restante: $" + jugador.getDinero());
    }

    private ImageIcon crearIconoProducto(String nombre, Color color, int w, int h) {
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(w, h,
                java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Glow
        for (int i = 4; i >= 1; i--) {
            g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 20 * i));
            int pad = i * 5;
            g.fillOval(pad, pad, w - pad * 2, h - pad * 2);
        }

        if ("Poción".equals(nombre)) {
            // Frasco
            g.setColor(color.darker());
            g.fillRoundRect(w/4, h/3, w/2, h/2, 12, 12);
            g.setColor(color);
            g.fillRoundRect(w/4, h/3, w/2, h/4, 12, 12);
            g.setColor(new Color(100, 100, 110));
            g.fillRoundRect(w*3/8, h/6, w/4, h/5, 5, 5);
            g.setColor(new Color(255, 255, 255, 140));
            g.fillOval(w/3, h*4/10, w/6, h/6);
            g.setColor(color.brighter());
            g.setStroke(new BasicStroke(2f));
            g.drawRoundRect(w/4, h/3, w/2, h/2, 12, 12);
        } else {
            // Esfera
            GradientPaint gp = new GradientPaint(w/4, h/4, color.brighter(), 3*w/4, 3*h/4, color.darker());
            g.setPaint(gp);
            g.fillOval(w/8, h/8, 3*w/4, h*3/5);
            g.setColor(new Color(240, 240, 240));
            g.fillOval(w/8, h/8 + h*3/10, 3*w/4, h*3/10);
            g.setColor(Color.DARK_GRAY);
            g.setStroke(new BasicStroke(2.5f));
            g.drawLine(w/8 + 4, h/8 + h*3/10, w*7/8 - 4, h/8 + h*3/10);
            g.drawOval(w/8, h/8, 3*w/4, h*3/5);
            g.setColor(Color.DARK_GRAY);
            g.fillOval(w/2 - 8, h/8 + h*3/10 - 8, 16, 16);
            g.setColor(Color.WHITE);
            g.fillOval(w/2 - 6, h/8 + h*3/10 - 6, 12, 12);
            g.setColor(new Color(255, 255, 255, 180));
            g.fillOval(w/4, h/5, w/5, h/6);
        }
        g.dispose();
        return new ImageIcon(img);
    }

}
