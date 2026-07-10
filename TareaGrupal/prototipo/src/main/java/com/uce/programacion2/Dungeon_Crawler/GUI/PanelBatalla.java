package com.uce.programacion2.Dungeon_Crawler.GUI;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import com.uce.programacion2.Dungeon_Crawler.Battle.Batalla;
import com.uce.programacion2.Dungeon_Crawler.Criaturas.Criatura;

public class PanelBatalla extends JPanel {

    private final VentanaPrincipal ventana;
    private Batalla batalla;

    // ── Campo de batalla (parte superior) ───────────────────────────────
    private final CampoBatalla campo;

    // ── Registro de combate ──────────────────────────────────────────────
    private final JTextArea txtRegistro;

    // ── Botones de acción ────────────────────────────────────────────────
    private final JButton btnAtacar;
    private final JButton btnEspecial;
    private final JButton btnPocion;
    private final JButton btnCapturar;
    private final JButton btnCambiar;
    private final JButton btnHuir;

    public PanelBatalla(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setOpaque(true);
        setLayout(new BorderLayout(0, 0));

        // ── Campo de batalla ─────────────────────────────────────────────
        campo = new CampoBatalla();
        campo.setPreferredSize(new Dimension(0, 360));
        add(campo, BorderLayout.CENTER);

        // ── Zona inferior (registro + botones) ───────────────────────────
        JPanel zonaBaja = new JPanel(new BorderLayout(0, 0));
        zonaBaja.setOpaque(false);

        // Registro
        txtRegistro = new JTextArea(4, 30);
        txtRegistro.setEditable(false);
        txtRegistro.setLineWrap(true);
        txtRegistro.setWrapStyleWord(true);
        txtRegistro.setBackground(new Color(10, 5, 28));
        txtRegistro.setForeground(new Color(200, 185, 255));
        txtRegistro.setCaretColor(Tema.GOLD);
        txtRegistro.setFont(Tema.FUENTE_REGISTRO);
        txtRegistro.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        JScrollPane scrollReg = new JScrollPane(txtRegistro);
        scrollReg.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Tema.BTN_BORDER));
        scrollReg.setPreferredSize(new Dimension(0, 100));
        scrollReg.getViewport().setBackground(new Color(10, 5, 28));

        // Panel de botones (2 filas × 3 columnas)
        JPanel panelBotones = new JPanel(new GridLayout(2, 3, 6, 6));
        panelBotones.setOpaque(false);
        panelBotones.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(2, 0, 0, 0, Tema.BTN_BORDER),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));

        btnAtacar   = Tema.crearBoton("⚔  Atacar",    false);
        btnEspecial = Tema.crearBoton("✦  Especial",   false);
        btnPocion   = Tema.crearBoton("✚  Poción",     false);
        btnCapturar = Tema.crearBoton("○  Capturar",   false);
        btnCambiar  = Tema.crearBoton("↔  Cambiar",    false);
        btnHuir     = Tema.crearBoton("↩  Huir",       true);

        panelBotones.add(btnAtacar);
        panelBotones.add(btnEspecial);
        panelBotones.add(btnPocion);
        panelBotones.add(btnCapturar);
        panelBotones.add(btnCambiar);
        panelBotones.add(btnHuir);

        zonaBaja.add(scrollReg,    BorderLayout.CENTER);
        zonaBaja.add(panelBotones, BorderLayout.SOUTH);
        add(zonaBaja, BorderLayout.SOUTH);

        registrarEventos();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Tema.pintarFondo(g, this);
    }

    // ====================================================================
    // API PÚBLICA
    // ====================================================================

    public void iniciarBatalla(Batalla batalla) {
        this.batalla = batalla;
        campo.actualizar(batalla.getAliado(), batalla.getSalvaje());
        txtRegistro.setText("");
        log("═══════════════════════════════════");
        log("  ¡Ha comenzado el combate!");
        log("  " + batalla.getAliado().getNombre() + "  vs  " + batalla.getSalvaje().getNombre());
        log("═══════════════════════════════════");
    }

    // ====================================================================
    // ACTUALIZACIÓN VISUAL
    // ====================================================================

    private void refrescar() {
        if (batalla != null) campo.actualizar(batalla.getAliado(), batalla.getSalvaje());
    }

    private void log(String msg) {
        txtRegistro.append(msg + "\n");
        txtRegistro.setCaretPosition(txtRegistro.getDocument().getLength());
    }

    // ====================================================================
    // EVENTOS DE BOTONES
    // ====================================================================

    private void registrarEventos() {

        btnAtacar.addActionListener(e -> {
            if (batalla == null) return;
            log(batalla.atacar());
            refrescar();
            verificarFin();
        });

        btnEspecial.addActionListener(e -> {
            if (batalla == null) return;
            log(batalla.usarAtaqueEspecial());
            refrescar();
            verificarFin();
        });

        btnPocion.addActionListener(e -> {
            if (batalla == null) return;
            log(batalla.usarPocion());
            refrescar();
            verificarFin();
        });

        btnCapturar.addActionListener(e -> {
            if (batalla == null) return;
            log(batalla.lanzarEsfera());
            refrescar();
            verificarFin();
        });

        btnCambiar.addActionListener(e -> {
            if (batalla == null) return;
            var jugador = batalla.getJugador();
            String[] opciones = new String[jugador.getCantidadCriaturas()];
            for (int i = 0; i < opciones.length; i++) {
                Criatura c = jugador.getCriatura(i);
                opciones[i] = c.getNombre() + "  HP " + c.getVida() + "/" + c.getVidaMaxima()
                        + (c.estaVivo() ? "" : "  [KO]");
            }
            String elegido = (String) JOptionPane.showInputDialog(
                    this, "Elige la criatura activa:", "Cambiar criatura",
                    JOptionPane.PLAIN_MESSAGE, null, opciones,
                    opciones.length > 0 ? opciones[0] : null);
            if (elegido == null) return;
            int idx = java.util.Arrays.asList(opciones).indexOf(elegido);
            log(batalla.cambiarCriatura(idx));
            refrescar();
            verificarFin();
        });

        btnHuir.addActionListener(e -> {
            if (batalla == null) return;
            log(batalla.huir());
            batalla.getJugador().curarEquipo();
            JOptionPane.showMessageDialog(this, "Has escapado del combate.");
            ventana.mostrar("MENU");
        });
    }

    private void verificarFin() {
        if (batalla == null) return;

        if (!batalla.getAliado().estaVivo()) {
            batalla.getJugador().curarEquipo();
            JOptionPane.showMessageDialog(this, "☠  Has perdido el combate.\nTu equipo ha sido curado.");
            ventana.mostrar("MENU");
            return;
        }

        if (!batalla.getSalvaje().estaVivo()) {
            batalla.getJugador().curarEquipo();
            JOptionPane.showMessageDialog(this, "★  ¡Has ganado el combate!\nGanaste experiencia y dinero.");
            ventana.mostrar("MENU");
        }
    }

    // ====================================================================
    // CAMPO DE BATALLA PERSONALIZADO
    // ====================================================================

    /**
     * Panel que pinta el campo de batalla estilo Pokémon:
     *  - Fondo degradado de hierba/cielo
     *  - Enemigo arriba a la derecha con su info y HP bar
     *  - Aliado abajo a la izquierda con su info y HP bar
     */
    private static class CampoBatalla extends JPanel {

        private Criatura aliado;
        private Criatura salvaje;

        // Imágenes cacheadas
        private ImageIcon iconAliado;
        private ImageIcon iconSalvaje;

        CampoBatalla() {
            setOpaque(false);
        }

        void actualizar(Criatura aliado, Criatura salvaje) {
            this.aliado  = aliado;
            this.salvaje = salvaje;
            // Regenerar iconos si cambió la criatura
            iconAliado  = ImageUtils.iconoMonstruo(aliado.getNombre(),  150, 150);
            iconSalvaje = ImageUtils.iconoMonstruo(salvaje.getNombre(), 130, 130);
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (aliado == null || salvaje == null) return;

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            int w = getWidth(), h = getHeight();

            // ── Fondo del campo ───────────────────────────────────────
            pintarFondoBatalla(g2, w, h);

            // ── Enemigo (arriba-derecha) ──────────────────────────────
            int exImg = w - 525, eyImg = 20;
            if (iconSalvaje != null) iconSalvaje.paintIcon(this, g2, exImg, eyImg);

            pintarInfoCriatura(g2, salvaje,
                    w - 380, 14,
                    360, false);

            // ── Aliado (abajo-izquierda) ──────────────────────────────
            int axImg = 40, ayImg = h - 195;
            if (iconAliado != null) iconAliado.paintIcon(this, g2, axImg, ayImg);

            pintarInfoCriatura(g2, aliado,
                    axImg + 160, h - 100,
                    360, true);

            g2.dispose();
        }

        private void pintarFondoBatalla(Graphics2D g2, int w, int h) {
            // Cielo
            g2.setPaint(new GradientPaint(0, 0, new Color(12, 6, 38), 0, h * 3 / 5, new Color(30, 12, 70)));
            g2.fillRect(0, 0, w, h * 3 / 5);

            // Plataforma enemiga (elipse trasera)
            g2.setColor(new Color(50, 35, 90, 180));
            g2.fillOval(w - 310, h * 3 / 5 - 22, 280, 30);

            // Suelo
            g2.setPaint(new GradientPaint(0, h * 3 / 5, new Color(18, 8, 48), 0, h, new Color(10, 4, 28)));
            g2.fillRect(0, h * 3 / 5, w, h - h * 3 / 5);

            // Línea de horizonte dorada
            g2.setColor(new Color(Tema.GOLD.getRed(), Tema.GOLD.getGreen(), Tema.GOLD.getBlue(), 50));
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawLine(0, h * 3 / 5, w, h * 3 / 5);

            // Plataforma aliado (elipse delantera)
            g2.setColor(new Color(70, 45, 120, 200));
            g2.fillOval(20, h - 50, 320, 32);

            // Estrellas de fondo
            g2.setColor(new Color(255, 255, 255, 90));
            int[] sx = { 80, 200, 350, 480, 600, 750, 900, 150, 420, 680 };
            int[] sy = { 40,  20,  60,  30,  50,  25,  45,  70,  15,  55 };
            for (int i = 0; i < sx.length && i < w / 100; i++) {
                int ss = (i % 2 == 0) ? 3 : 2;
                g2.fillOval(sx[i] % w, sy[i % sy.length], ss, ss);
            }
        }

        private void pintarInfoCriatura(Graphics2D g2, Criatura c,
                                         int x, int y, int panelW, boolean esAliado) {
            int panelH = 80;

            // Caja semitransparente redondeada
            g2.setColor(new Color(0, 0, 0, 130));
            g2.fill(new RoundRectangle2D.Float(x, y, panelW, panelH, 12, 12));
            g2.setColor(esAliado ? new Color(80, 200, 120, 120) : new Color(200, 80, 80, 120));
            g2.setStroke(new BasicStroke(1.5f));
            g2.draw(new RoundRectangle2D.Float(x, y, panelW, panelH, 12, 12));

            // Nombre + nivel
            g2.setFont(new Font("Serif", Font.BOLD, 16));
            g2.setColor(Tema.GOLD);
            g2.drawString(c.getNombre(), x + 10, y + 22);

            g2.setFont(new Font("SansSerif", Font.PLAIN, 12));
            g2.setColor(Tema.TEXTO_CLARO);
            g2.drawString("Nv. " + c.getNivel() + "   " + c.getTipo(), x + 10, y + 38);

            // Barra HP
            int barX = x + 10, barY = y + 46, barW = panelW - 20, barH = 14;
            double pct = c.getVidaMaxima() > 0 ? (double) c.getVida() / c.getVidaMaxima() : 0;
            int relleno = (int) (pct * barW);

            g2.setColor(new Color(20, 10, 40));
            g2.fillRoundRect(barX, barY, barW, barH, barH, barH);

            Color barColor = pct > 0.5 ? Tema.VIDA_ALTA : pct > 0.25 ? Tema.VIDA_MEDIA : Tema.VIDA_BAJA;
            GradientPaint barGrad = new GradientPaint(barX, barY, barColor.brighter(),
                    barX, barY + barH, barColor.darker());
            g2.setPaint(barGrad);
            if (relleno > barH) g2.fillRoundRect(barX + 1, barY + 1, relleno - 2, barH - 2, barH - 2, barH - 2);
            else if (relleno > 0) g2.fillRect(barX + 1, barY + 1, relleno - 2, barH - 2);

            g2.setColor(Tema.BTN_BORDER);
            g2.setStroke(new BasicStroke(1f));
            g2.drawRoundRect(barX, barY, barW, barH, barH, barH);

            // HP texto
            g2.setFont(new Font("SansSerif", Font.BOLD, 10));
            g2.setColor(Color.WHITE);
            String hpTxt = "HP  " + c.getVida() + " / " + c.getVidaMaxima();
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(hpTxt, barX + (barW - fm.stringWidth(hpTxt)) / 2, barY + 10);

            // Experiencia (solo para el aliado)
            if (esAliado) {
                g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
                g2.setColor(new Color(150, 140, 200));
                g2.drawString("EXP  " + c.getExperiencia() + " / 100", x + 10, y + 74);
            }
        }
    }

}
