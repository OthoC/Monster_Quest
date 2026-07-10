package com.uce.programacion2.Dungeon_Crawler.GUI;

import java.awt.*;
import javax.swing.*;

/**
 * Panel de estadísticas reutilizable: muestra filas "Nombre: Valor"
 * sobre el fondo oscuro del tema. Puede llenarse con addStat() y
 * resetearse con limpiar() sin reconstruir el panel.
 */
public class StatPanel extends JPanel {

    private final JPanel filas;

    public StatPanel() {
        setOpaque(false);
        setLayout(new BorderLayout());

        filas = new JPanel();
        filas.setOpaque(false);
        filas.setLayout(new BoxLayout(filas, BoxLayout.Y_AXIS));
        add(filas, BorderLayout.NORTH);
    }

    /** Añade una fila "nombre : valor" con el color dorado del tema. */
    public void addStat(String nombre, String valor) {
        addStat(nombre, valor, Tema.TEXTO_CLARO);
    }

    /** Añade una fila con color personalizado para el valor. */
    public void addStat(String nombre, String valor, Color colorValor) {
        JPanel fila = new JPanel(new BorderLayout(6, 0));
        fila.setOpaque(false);
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 22));

        JLabel lblNombre = new JLabel(nombre);
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblNombre.setForeground(Tema.GOLD_DIM);

        JLabel lblValor = new JLabel(valor, SwingConstants.RIGHT);
        lblValor.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblValor.setForeground(colorValor);

        fila.add(lblNombre, BorderLayout.WEST);
        fila.add(lblValor,  BorderLayout.EAST);

        filas.add(fila);
        filas.add(Box.createVerticalStrut(3));
    }

    /** Añade un separador horizontal dorado. */
    public void addSeparador() {
        JPanel sep = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(Tema.GOLD.getRed(), Tema.GOLD.getGreen(), Tema.GOLD.getBlue(), 60));
                g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
            }
        };
        sep.setOpaque(false);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 8));
        filas.add(sep);
    }

    /** Elimina todas las filas para reutilizar el panel. */
    public void limpiar() {
        filas.removeAll();
        filas.revalidate();
        filas.repaint();
    }

}
