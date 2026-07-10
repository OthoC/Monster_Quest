package com.uce.programacion2.Dungeon_Crawler.GUI;

import java.awt.*;
import javax.swing.*;
import com.uce.programacion2.Dungeon_Crawler.Battle.Batalla;
import com.uce.programacion2.Dungeon_Crawler.Mapa.Ruta;

public class PanelMapa extends JPanel {

    private final VentanaPrincipal ventana;

    // Panel de rutas/botones izquierdo
    private final JPanel panelRutas;

    // Panel de detalle de la región (derecha)
    private final JLabel   imgRegion;
    private final JLabel   lblNombreRuta;
    private final JLabel   lblDescripcion;
    private final JLabel   lblNivel;
    private final JPanel   panelBotonesRuta;

    public PanelMapa(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setOpaque(true);
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(14, 16, 10, 16));

        // ── Header ──────────────────────────────────────────────────────
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.add(Tema.titulo("EXPLORAR MUNDO"));
        header.add(Tema.subtitulo("Selecciona una región y luego una ruta para explorar"));
        add(header, BorderLayout.NORTH);

        // ── Izquierda: lista de rutas ────────────────────────────────────
        panelRutas = new JPanel();
        panelRutas.setOpaque(false);
        panelRutas.setLayout(new BoxLayout(panelRutas, BoxLayout.Y_AXIS));
        panelRutas.setBorder(Tema.bordeTitulado("Regiones y Rutas"));

        JScrollPane scrollRutas = Tema.scrollOscuro(panelRutas);
        scrollRutas.setPreferredSize(new Dimension(240, 0));
        add(scrollRutas, BorderLayout.WEST);

        // ── Derecha: detalle de la ruta seleccionada ─────────────────────
        JPanel detalle = new JPanel();
        detalle.setOpaque(false);
        detalle.setLayout(new BoxLayout(detalle, BoxLayout.Y_AXIS));

        // Imagen grande de la región
        imgRegion = new JLabel();
        imgRegion.setHorizontalAlignment(SwingConstants.CENTER);
        imgRegion.setAlignmentX(CENTER_ALIGNMENT);
        imgRegion.setPreferredSize(new Dimension(560, 220));
        imgRegion.setMinimumSize  (new Dimension(400, 180));
        imgRegion.setText("<html><center><font color='#6030a0'>Selecciona una ruta para ver la región</font></center></html>");

        // Tarjeta de info
        JPanel cardInfo = Tema.crearTarjeta();
        cardInfo.setLayout(new BoxLayout(cardInfo, BoxLayout.Y_AXIS));
        cardInfo.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
        cardInfo.setAlignmentX(CENTER_ALIGNMENT);

        lblNombreRuta = new JLabel(" ", SwingConstants.LEFT);
        lblNombreRuta.setFont(new Font("Serif", Font.BOLD, 20));
        lblNombreRuta.setForeground(Tema.GOLD);
        lblNombreRuta.setAlignmentX(LEFT_ALIGNMENT);

        lblDescripcion = new JLabel(" ", SwingConstants.LEFT);
        lblDescripcion.setFont(new Font("SansSerif", Font.ITALIC, 13));
        lblDescripcion.setForeground(new Color(180, 165, 220));
        lblDescripcion.setAlignmentX(LEFT_ALIGNMENT);

        lblNivel = new JLabel(" ", SwingConstants.LEFT);
        lblNivel.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblNivel.setForeground(Tema.VIDA_MEDIA);
        lblNivel.setAlignmentX(LEFT_ALIGNMENT);

        cardInfo.add(lblNombreRuta);
        cardInfo.add(Box.createVerticalStrut(4));
        cardInfo.add(lblDescripcion);
        cardInfo.add(Box.createVerticalStrut(6));
        cardInfo.add(lblNivel);

        // Botones de acción sobre la ruta seleccionada
        panelBotonesRuta = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 4));
        panelBotonesRuta.setOpaque(false);
        panelBotonesRuta.setAlignmentX(CENTER_ALIGNMENT);

        detalle.add(imgRegion);
        detalle.add(Box.createVerticalStrut(10));
        detalle.add(cardInfo);
        detalle.add(Box.createVerticalStrut(8));
        detalle.add(panelBotonesRuta);
        add(detalle, BorderLayout.CENTER);

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

    /** Reconstruye la lista de rutas. Llamado cada vez que se abre el mapa. */
    public void actualizarMapa() {
        panelRutas.removeAll();
        limpiarDetalle();

        var mundo = ventana.getJuego().getMundo();

        for (int r = 0; r < mundo.getCantidadRegiones(); r++) {
            // Encabezado de región
            JLabel lblRegion = new JLabel(mundo.getNombreRegion(r));
            lblRegion.setFont(new Font("Serif", Font.BOLD, 13));
            lblRegion.setForeground(Tema.GOLD);
            lblRegion.setBorder(BorderFactory.createEmptyBorder(8, 6, 4, 6));
            lblRegion.setAlignmentX(LEFT_ALIGNMENT);
            panelRutas.add(lblRegion);

            for (int i = 0; i < mundo.getCantidadRutas(r); i++) {
                Ruta ruta = mundo.obtenerRuta(r, i);
                JButton btn = crearBotonRuta(ruta);
                btn.setAlignmentX(LEFT_ALIGNMENT);
                btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
                final Ruta rutaFinal = ruta;
                btn.addActionListener(e -> seleccionarRuta(rutaFinal));
                panelRutas.add(btn);
                panelRutas.add(Box.createVerticalStrut(4));
            }

            // Separador entre regiones
            if (r < mundo.getCantidadRegiones() - 1) {
                JSeparator sep = new JSeparator();
                sep.setForeground(Tema.BTN_BORDER);
                sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));
                panelRutas.add(Box.createVerticalStrut(4));
                panelRutas.add(sep);
                panelRutas.add(Box.createVerticalStrut(4));
            }
        }

        panelRutas.revalidate();
        panelRutas.repaint();
    }

    private JButton crearBotonRuta(Ruta ruta) {
        JButton btn = Tema.crearBoton("  ▶  " + ruta.getNombre() + "  (Nv." + ruta.getNivelRecomendado() + ")", false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        return btn;
    }

    private void seleccionarRuta(Ruta ruta) {
        // Imagen de la región
        imgRegion.setIcon(ImageUtils.iconoRegion(ruta.getNombre(), 560, 220));
        imgRegion.setText(null);

        // Info
        lblNombreRuta.setText(ruta.getNombre());
        lblDescripcion.setText("<html><i>" + ruta.getDescripcion() + "</i></html>");
        lblNivel.setText("⚔  Nivel recomendado: " + ruta.getNivelRecomendado()
                + "   •   Criaturas: " + ruta.getCriaturas().length + " tipos");

        // Botones de acción
        panelBotonesRuta.removeAll();

        JButton btnExplorar = Tema.crearBoton("⚔  ¡Explorar!", false);
        btnExplorar.setPreferredSize(new Dimension(180, 38));
        btnExplorar.addActionListener(e -> explorar(ruta));

        panelBotonesRuta.add(btnExplorar);
        panelBotonesRuta.revalidate();
        panelBotonesRuta.repaint();
    }

    private void explorar(Ruta ruta) {
        var criatura = ruta.obtenerCriaturaAleatoria();
        Batalla batalla = new Batalla(ventana.getJuego().getJugador(), criatura);
        ventana.getPanelBatalla().iniciarBatalla(batalla);
        ventana.mostrar("BATALLA");
    }

    private void limpiarDetalle() {
        imgRegion.setIcon(null);
        imgRegion.setText("<html><center><font color='#6030a0'>Selecciona una ruta para ver la región</font></center></html>");
        lblNombreRuta.setText(" ");
        lblDescripcion.setText(" ");
        lblNivel.setText(" ");
        panelBotonesRuta.removeAll();
    }

}
