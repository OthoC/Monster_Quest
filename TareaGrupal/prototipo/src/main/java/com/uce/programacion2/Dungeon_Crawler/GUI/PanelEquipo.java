package com.uce.programacion2.Dungeon_Crawler.GUI;

import java.awt.*;
import javax.swing.*;
import com.uce.programacion2.Dungeon_Crawler.Criaturas.Criatura;

public class PanelEquipo extends JPanel {

    private final VentanaPrincipal ventana;
    private final DefaultListModel<String> modelo;
    private final JList<String>  lista;
    private final MonsterCard    card;
    private final JLabel         lblVacio;

    public PanelEquipo(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setOpaque(true);
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 12, 16));

        // ── Header ──────────────────────────────────────────────────────
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.add(Tema.titulo("TU EQUIPO"));
        header.add(Box.createVerticalStrut(2));
        header.add(Tema.subtitulo("Selecciona una criatura para ver sus estadísticas completas"));
        add(header, BorderLayout.NORTH);

        // ── Lista de criaturas (izquierda) ───────────────────────────────
        modelo = new DefaultListModel<>();
        lista  = new JList<>(modelo);
        lista.setOpaque(false);
        lista.setForeground(Tema.TEXTO_CLARO);
        lista.setFont(new Font("Serif", Font.BOLD, 15));
        lista.setFixedCellHeight(44);
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lista.setCellRenderer(new CeldaCriatura());

        JScrollPane scrollLista = Tema.scrollOscuro(lista);
        scrollLista.setPreferredSize(new Dimension(280, 0));

        // ── Tarjeta detallada (derecha) ──────────────────────────────────
        card = new MonsterCard(180, 180, false);

        JPanel cardWrapper = Tema.crearTarjeta();
        cardWrapper.setLayout(new BorderLayout());
        cardWrapper.add(card, BorderLayout.CENTER);

        // Label "equipo vacío"
        lblVacio = Tema.subtitulo("Tu equipo está vacío.\nElige una criatura inicial primero.");
        lblVacio.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel centro = new JPanel(new BorderLayout(12, 0));
        centro.setOpaque(false);
        centro.add(scrollLista, BorderLayout.WEST);
        centro.add(cardWrapper,  BorderLayout.CENTER);
        add(centro, BorderLayout.CENTER);

        // ── Footer ──────────────────────────────────────────────────────
        JButton volver = Tema.crearBoton("← Volver", true);
        volver.setPreferredSize(new Dimension(160, 36));
        volver.addActionListener(e -> ventana.mostrar("MENU"));
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setOpaque(false);
        footer.add(volver);
        add(footer, BorderLayout.SOUTH);

        // ── Evento de selección ──────────────────────────────────────────
        lista.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) mostrarCriaturaSeleccionada();
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Tema.pintarFondo(g, this);
    }

    /** Rellena la lista con las criaturas actuales del jugador. */
    public void actualizar() {
        modelo.clear();
        card.limpiar();

        if (ventana.getJuego().getJugador() == null) return;

        int n = ventana.getJuego().getJugador().getCantidadCriaturas();
        for (int i = 0; i < n; i++) {
            Criatura c = ventana.getJuego().getJugador().getCriatura(i);
            modelo.addElement(c.getNombre() + "|" + c.getNivel() + "|" + c.getVida() + "|" + c.getVidaMaxima() + "|" + c.getTipo());
        }

        if (n > 0) lista.setSelectedIndex(0);
    }

    private void mostrarCriaturaSeleccionada() {
        int idx = lista.getSelectedIndex();
        if (idx < 0 || ventana.getJuego().getJugador() == null) { card.limpiar(); return; }
        Criatura c = ventana.getJuego().getJugador().getCriatura(idx);
        card.setCriatura(c);
    }

    // ── Renderer personalizado para la lista ────────────────────────────
    private static class CeldaCriatura extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {

            String raw = value.toString();
            String[] partes = raw.split("\\|");
            String nombre = partes[0];
            String nivel  = partes.length > 1 ? partes[1] : "?";
            String vidaAct = partes.length > 2 ? partes[2] : "?";
            String vidaMax = partes.length > 3 ? partes[3] : "?";
            String tipo    = partes.length > 4 ? partes[4] : "";

            JPanel cell = new JPanel(new BorderLayout(8, 0));
            cell.setOpaque(true);
            cell.setBackground(isSelected ? Tema.BTN_HOVER : new Color(0, 0, 0, 0));
            cell.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(60, 40, 100)),
                    BorderFactory.createEmptyBorder(4, 10, 4, 10)));

            // Mini icono del monstruo
            JLabel icono = new JLabel(ImageUtils.iconoMonstruo(nombre, 32, 32));
            cell.add(icono, BorderLayout.WEST);

            // Texto central
            JLabel txt = new JLabel("<html><b>" + nombre + "</b>  <font color='#b090e0'>Lv." + nivel
                    + "</font>  <font color='#80d080'>HP " + vidaAct + "/" + vidaMax + "</font></html>");
            txt.setForeground(Tema.TEXTO_CLARO);
            txt.setFont(new Font("SansSerif", Font.PLAIN, 13));
            cell.add(txt, BorderLayout.CENTER);

            // Badge tipo
            Color tc = ImageUtils.colorDeTipo(tipo);
            JLabel badgeLbl = new JLabel(tipo, SwingConstants.CENTER);
            badgeLbl.setFont(new Font("SansSerif", Font.BOLD, 10));
            badgeLbl.setForeground(Color.WHITE);
            badgeLbl.setBackground(new Color(tc.getRed(), tc.getGreen(), tc.getBlue(), 180));
            badgeLbl.setOpaque(true);
            badgeLbl.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));
            cell.add(badgeLbl, BorderLayout.EAST);

            return cell;
        }
    }

}
