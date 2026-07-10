package com.uce.programacion2.Dungeon_Crawler.GUI;

import java.awt.*;
import javax.swing.*;
import com.uce.programacion2.Dungeon_Crawler.Objetos.Inventario;
import com.uce.programacion2.Dungeon_Crawler.Objetos.Objeto;
import com.uce.programacion2.Dungeon_Crawler.Personajes.Jugador;

public class PanelEntrenador extends JPanel {

    private final VentanaPrincipal ventana;

    // Imagen del entrenador — permanece visible siempre
    private final JLabel imgEntrenador;

    // Datos dinámicos
    private final JLabel lblNombre;
    private final JLabel lblDinero;
    private final JLabel lblVictorias;
    private final JLabel lblCriaturas;
    private final StatPanel statsInventario;

    public PanelEntrenador(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setOpaque(true);
        setLayout(new BorderLayout(16, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 20, 12, 20));

        // ── Header ──────────────────────────────────────────────────────
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.add(Tema.titulo("ENTRENADOR"));
        header.add(Box.createVerticalStrut(2));
        header.add(Tema.subtitulo("Perfil del entrenador y estado de su inventario"));
        add(header, BorderLayout.NORTH);

        // ── Imagen entrenador (izquierda) ────────────────────────────────
        imgEntrenador = new JLabel(ImageUtils.iconoEntrenador(170, 280));
        imgEntrenador.setHorizontalAlignment(SwingConstants.CENTER);
        imgEntrenador.setVerticalAlignment(SwingConstants.CENTER);
        imgEntrenador.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 20));

        // ── Datos entrenador (derecha) ───────────────────────────────────
        JPanel datosPanel = new JPanel();
        datosPanel.setOpaque(false);
        datosPanel.setLayout(new BoxLayout(datosPanel, BoxLayout.Y_AXIS));

        // Tarjeta perfil
        JPanel cardPerfil = Tema.crearTarjeta();
        cardPerfil.setLayout(new BoxLayout(cardPerfil, BoxLayout.Y_AXIS));
        cardPerfil.setBorder(BorderFactory.createCompoundBorder(
                Tema.bordeTitulado("Perfil"),
                BorderFactory.createEmptyBorder(10, 12, 12, 12)));
        cardPerfil.setAlignmentX(LEFT_ALIGNMENT);

        lblNombre    = crearLblDato("Nombre", "");
        lblDinero    = crearLblDato("Dinero", "");
        lblVictorias = crearLblDato("Victorias", "");
        lblCriaturas = crearLblDato("Criaturas", "");

        cardPerfil.add(lblNombre);
        cardPerfil.add(Box.createVerticalStrut(6));
        cardPerfil.add(lblDinero);
        cardPerfil.add(Box.createVerticalStrut(6));
        cardPerfil.add(lblVictorias);
        cardPerfil.add(Box.createVerticalStrut(6));
        cardPerfil.add(lblCriaturas);

        // Tarjeta inventario
        JPanel cardInv = Tema.crearTarjeta();
        cardInv.setLayout(new BoxLayout(cardInv, BoxLayout.Y_AXIS));
        cardInv.setBorder(BorderFactory.createCompoundBorder(
                Tema.bordeTitulado("Inventario"),
                BorderFactory.createEmptyBorder(10, 12, 12, 12)));
        cardInv.setAlignmentX(LEFT_ALIGNMENT);

        statsInventario = new StatPanel();
        statsInventario.setAlignmentX(LEFT_ALIGNMENT);
        cardInv.add(statsInventario);

        datosPanel.add(cardPerfil);
        datosPanel.add(Box.createVerticalStrut(14));
        datosPanel.add(cardInv);

        // ── Centro: imagen + datos ───────────────────────────────────────
        JPanel centro = new JPanel(new BorderLayout(10, 0));
        centro.setOpaque(false);
        centro.add(imgEntrenador, BorderLayout.WEST);
        centro.add(datosPanel,    BorderLayout.CENTER);
        add(centro, BorderLayout.CENTER);

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
        Jugador jugador = ventana.getJuego().getJugador();
        if (jugador == null) return;

        setTexto(lblNombre,    "Nombre",    jugador.getNombre());
        setTexto(lblDinero,    "Dinero",    "$ " + jugador.getDinero());
        setTexto(lblVictorias, "Victorias", String.valueOf(jugador.getVictorias()));
        setTexto(lblCriaturas, "Criaturas", jugador.getCantidadCriaturas() + " / 6");

        // Inventario
        statsInventario.limpiar();
        Inventario inv = jugador.getInventario();
        if (inv.getCantidad() == 0) {
            statsInventario.addStat("Estado", "Vacío", new Color(160, 100, 100));
        } else {
            // Contar por tipo de objeto
            int pociones = 0, esferas = 0, otros = 0;
            for (int i = 0; i < inv.getCantidad(); i++) {
                Objeto o = inv.obtenerObjeto(i);
                switch (o.getNombre()) {
                    case "Poción" -> pociones++;
                    case "Esfera" -> esferas++;
                    default       -> otros++;
                }
            }
            statsInventario.addStat("Pociones",       String.valueOf(pociones),
                    new Color(120, 200, 120));
            statsInventario.addStat("Esferas",        String.valueOf(esferas),
                    new Color(120, 160, 240));
            if (otros > 0)
                statsInventario.addStat("Otros objetos", String.valueOf(otros));
            statsInventario.addSeparador();
            statsInventario.addStat("Total objetos",  String.valueOf(inv.getCantidad()),
                    Tema.GOLD);
        }
    }

    private JLabel crearLblDato(String nombre, String valor) {
        JLabel lbl = new JLabel();
        lbl.setFont(new Font("Serif", Font.BOLD, 16));
        lbl.setForeground(Tema.TEXTO_CLARO);
        lbl.setAlignmentX(LEFT_ALIGNMENT);
        setTexto(lbl, nombre, valor);
        return lbl;
    }

    private void setTexto(JLabel lbl, String nombre, String valor) {
        lbl.setText("<html><font color='#a37316'>" + nombre + ": </font>"
                + "<font color='#d7c3ff'>" + valor + "</font></html>");
    }

}
