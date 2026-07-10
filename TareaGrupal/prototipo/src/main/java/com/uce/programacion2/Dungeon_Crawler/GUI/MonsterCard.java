package com.uce.programacion2.Dungeon_Crawler.GUI;

import java.awt.*;
import javax.swing.*;
import com.uce.programacion2.Dungeon_Crawler.Criaturas.Criatura;

/**
 * Tarjeta visual de una criatura: imagen grande, nombre, tipo (badge),
 * barra de HP y panel de estadísticas detalladas.
 *
 * Uso:
 *   MonsterCard card = new MonsterCard(imgW, imgH, mostrarBoton);
 *   card.setCriatura(c);   // para actualizar
 *   card.limpiar();        // para resetear
 */
public class MonsterCard extends JPanel {

    private final int imgW;
    private final int imgH;

    private final JLabel  imgLabel;
    private final JLabel  lblNombre;
    private final JLabel  lblTipo;
    private final HPBar   hpBar;
    private final StatPanel stats;

    // Botón opcional de acción (p.ej. "Elegir")
    private final JButton btnAccion;
    private final boolean mostrarBoton;

    public MonsterCard(int imgW, int imgH, boolean mostrarBoton) {
        this.imgW         = imgW;
        this.imgH         = imgH;
        this.mostrarBoton = mostrarBoton;

        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));

        // Imagen
        imgLabel = new JLabel();
        imgLabel.setAlignmentX(CENTER_ALIGNMENT);
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imgLabel.setPreferredSize(new Dimension(imgW, imgH));

        // Nombre
        lblNombre = new JLabel(" ", SwingConstants.CENTER);
        lblNombre.setFont(new Font("Serif", Font.BOLD, 20));
        lblNombre.setForeground(Tema.GOLD);
        lblNombre.setAlignmentX(CENTER_ALIGNMENT);

        // Badge tipo
        lblTipo = new JLabel(" ", SwingConstants.CENTER);
        lblTipo.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblTipo.setForeground(Color.WHITE);
        lblTipo.setOpaque(false);
        lblTipo.setAlignmentX(CENTER_ALIGNMENT);

        // HP Bar
        hpBar = new HPBar(0, 1);
        hpBar.setAlignmentX(CENTER_ALIGNMENT);
        hpBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 22));

        // Stats
        stats = new StatPanel();
        stats.setAlignmentX(CENTER_ALIGNMENT);

        // Botón acción
        btnAccion = Tema.crearBoton("Elegir", false);
        btnAccion.setAlignmentX(CENTER_ALIGNMENT);
        btnAccion.setMaximumSize(new Dimension(140, 36));

        // Construcción
        add(imgLabel);
        add(Box.createVerticalStrut(8));
        add(lblNombre);
        add(Box.createVerticalStrut(4));

        // Wrapper del badge de tipo (para poder remplazarlo)
        JPanel badgeWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        badgeWrapper.setOpaque(false);
        badgeWrapper.setAlignmentX(CENTER_ALIGNMENT);
        badgeWrapper.add(lblTipo);
        add(badgeWrapper);

        add(Box.createVerticalStrut(10));
        add(hpBar);
        add(Box.createVerticalStrut(8));
        add(stats);

        if (mostrarBoton) {
            add(Box.createVerticalStrut(10));
            add(btnAccion);
        }
    }

    /** Actualiza la tarjeta con los datos de la criatura dada. */
    public void setCriatura(Criatura c) {
        if (c == null) { limpiar(); return; }

        imgLabel.setIcon(ImageUtils.iconoMonstruo(c.getNombre(), imgW, imgH));

        lblNombre.setText(c.getNombre());

        // Reconstruir badge de tipo
        lblTipo.setText("  " + c.getTipo() + "  ");
        Color tc = ImageUtils.colorDeTipo(c.getTipo());
        lblTipo.setForeground(Color.WHITE);
        lblTipo.setBackground(new Color(tc.getRed(), tc.getGreen(), tc.getBlue(), 180));
        lblTipo.setOpaque(true);
        lblTipo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(tc.brighter(), 1, true),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)));

        hpBar.setValores(c.getVida(), c.getVidaMaxima());

        stats.limpiar();
        stats.addStat("Nivel",       String.valueOf(c.getNivel()));
        stats.addStat("Ataque",      String.valueOf(c.getAtaque()));
        stats.addStat("Experiencia", c.getExperiencia() + " / 100");
        stats.addSeparador();
        stats.addStat("Vida máx.",   String.valueOf(c.getVidaMaxima()));

        revalidate();
        repaint();
    }

    /** Borra todos los datos visuales de la tarjeta. */
    public void limpiar() {
        imgLabel.setIcon(null);
        lblNombre.setText(" ");
        lblTipo.setText(" ");
        lblTipo.setOpaque(false);
        lblTipo.setBorder(null);
        hpBar.setValores(0, 1);
        stats.limpiar();
        revalidate();
        repaint();
    }

    /** Añade un listener al botón de acción (solo si mostrarBoton=true). */
    public void setAccionListener(java.awt.event.ActionListener l) {
        btnAccion.addActionListener(l);
    }

    /** Cambia el texto del botón de acción. */
    public void setTextoBoton(String texto) {
        btnAccion.setText(texto);
    }

}
