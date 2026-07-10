package com.uce.programacion2.Dungeon_Crawler.GUI;

import java.awt.*;
import javax.swing.JPanel;

/**
 * Barra de HP personalizada estilo RPG.
 * Dibuja un rectángulo redondeado con relleno de color dinámico
 * (verde / amarillo / rojo según el porcentaje) y texto "HP: X / Y".
 */
public class HPBar extends JPanel {

    private int valor;
    private int maximo;

    public HPBar(int valor, int maximo) {
        this.valor   = valor;
        this.maximo  = maximo;
        setOpaque(false);
        setPreferredSize(new Dimension(200, 22));
    }

    public void setValores(int valor, int maximo) {
        this.valor   = valor;
        this.maximo  = maximo;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int arc = h;

        // Fondo de la barra
        g2.setColor(new Color(20, 10, 40));
        g2.fillRoundRect(0, 0, w, h, arc, arc);
        g2.setColor(new Color(60, 40, 90));
        g2.setStroke(new BasicStroke(1f));
        g2.drawRoundRect(0, 0, w - 1, h - 1, arc, arc);

        // Relleno proporcional
        if (maximo > 0) {
            double pct = Math.max(0, Math.min(1.0, (double) valor / maximo));
            int relleno = (int) (pct * (w - 4));
            Color c = colorSegunPct(pct);
            Color c2 = c.brighter();

            GradientPaint grad = new GradientPaint(2, 2, c2, 2, h - 2, c.darker());
            g2.setPaint(grad);
            if (relleno > arc) {
                g2.fillRoundRect(2, 2, relleno, h - 4, arc - 2, arc - 2);
            } else if (relleno > 0) {
                g2.fillRect(2, 2, relleno, h - 4);
            }

            // Brillo superior
            g2.setColor(new Color(255, 255, 255, 50));
            g2.fillRoundRect(4, 3, Math.max(0, relleno - 4), (h - 4) / 2, arc / 2, arc / 2);
        }

        // Texto HP
        String txt = "HP  " + valor + " / " + maximo;
        g2.setFont(new Font("SansSerif", Font.BOLD, 11));
        FontMetrics fm = g2.getFontMetrics();
        int tx = (w - fm.stringWidth(txt)) / 2;
        int ty = (h + fm.getAscent() - fm.getDescent()) / 2;
        g2.setColor(new Color(0, 0, 0, 130));
        g2.drawString(txt, tx + 1, ty + 1);
        g2.setColor(Color.WHITE);
        g2.drawString(txt, tx, ty);

        g2.dispose();
    }

    private Color colorSegunPct(double pct) {
        if (pct <= 0.25) return Tema.VIDA_BAJA;
        if (pct <= 0.55) return Tema.VIDA_MEDIA;
        return Tema.VIDA_ALTA;
    }

}
