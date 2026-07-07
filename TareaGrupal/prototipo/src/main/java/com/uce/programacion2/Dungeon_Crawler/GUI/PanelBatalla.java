package com.uce.programacion2.Dungeon_Crawler.GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.uce.programacion2.Dungeon_Crawler.Battle.Batalla;
import javax.swing.JOptionPane;

import com.uce.programacion2.Dungeon_Crawler.Criaturas.Aquos;
import com.uce.programacion2.Dungeon_Crawler.Criaturas.Floran;
import com.uce.programacion2.Dungeon_Crawler.Criaturas.Pyron;
import com.uce.programacion2.Dungeon_Crawler.Criaturas.Umbrix;
import com.uce.programacion2.Dungeon_Crawler.Criaturas.Voltix;
import com.uce.programacion2.Dungeon_Crawler.Criaturas.Criatura;

public class PanelBatalla extends JPanel {

    private VentanaPrincipal ventana;

    private Batalla batalla;

    // Criatura del jugador
    private JLabel lblJugador;
    private JLabel lblNivelJugador;
    private JProgressBar vidaJugador;

    // Criatura enemiga
    private JLabel lblEnemigo;
    private JLabel lblNivelEnemigo;
    private JProgressBar vidaEnemigo;

    // Registro del combate
    private JTextArea txtRegistro;

    // Botones
    private JButton btnAtacar;
    private JButton btnEspecial;
    private JButton btnPocion;
    private JButton btnCapturar;
    private JButton btnHuir;

    public PanelBatalla(VentanaPrincipal ventana) {

        this.ventana = ventana;

        construir();

    }

    private void construir() {

        setLayout(new BorderLayout(15, 15));

        // ----------------------------
        // Título
        // ----------------------------

        JLabel titulo = new JLabel("COMBATE");

        titulo.setHorizontalAlignment(JLabel.CENTER);

        titulo.setFont(new Font("Arial", Font.BOLD, 28));

        add(titulo, BorderLayout.NORTH);

        // ----------------------------
        // Centro
        // ----------------------------

        JPanel centro = new JPanel(new GridLayout(1, 2, 20, 20));

        JPanel panelJugador = new JPanel(new GridLayout(4, 1));

        lblJugador = new JLabel("Jugador");

        lblNivelJugador = new JLabel("Nivel");

        vidaJugador = new JProgressBar();

        vidaJugador.setStringPainted(true);

        panelJugador.add(lblJugador);

        panelJugador.add(lblNivelJugador);

        panelJugador.add(new JLabel("Vida"));

        panelJugador.add(vidaJugador);

        JPanel panelEnemigo = new JPanel(new GridLayout(4, 1));

        lblEnemigo = new JLabel("Enemigo");

        lblNivelEnemigo = new JLabel("Nivel");

        vidaEnemigo = new JProgressBar();

        vidaEnemigo.setStringPainted(true);

        panelEnemigo.add(lblEnemigo);

        panelEnemigo.add(lblNivelEnemigo);

        panelEnemigo.add(new JLabel("Vida"));

        panelEnemigo.add(vidaEnemigo);

        centro.add(panelJugador);

        centro.add(panelEnemigo);

        add(centro, BorderLayout.CENTER);

        // ----------------------------
        // Registro
        // ----------------------------

        txtRegistro = new JTextArea();

        txtRegistro.setEditable(false);

        txtRegistro.setLineWrap(true);

        txtRegistro.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(txtRegistro);

        scroll.setPreferredSize(new java.awt.Dimension(100, 180));

        add(scroll, BorderLayout.EAST);

        // ----------------------------
        // Botones
        // ----------------------------

        JPanel botones = new JPanel(new GridLayout(1, 5, 10, 10));

        btnAtacar = new JButton("Atacar");

        btnEspecial = new JButton("Especial");

        btnPocion = new JButton("Poción");

        btnCapturar = new JButton("Capturar");

        btnHuir = new JButton("Huir");

        botones.add(btnAtacar);

        botones.add(btnEspecial);

        botones.add(btnPocion);

        botones.add(btnCapturar);

        botones.add(btnHuir);

        add(botones, BorderLayout.SOUTH);

        registrarEventos();

    }

    /**
     * Inicia una batalla.
     */
    public void iniciarBatalla(Batalla batalla) {

        this.batalla = batalla;

        actualizarPantalla();

        txtRegistro.setText("");

        txtRegistro.append("¡Ha comenzado el combate!\n");

    }

    /**
     * Actualiza todos los datos de la pantalla.
     */
    private void actualizarPantalla() {

        if (batalla == null)
            return;

        lblJugador.setText(batalla.getAliado().getNombre());

        lblNivelJugador.setText(
                "Nivel " + batalla.getAliado().getNivel());

        vidaJugador.setMaximum(
                batalla.getAliado().getVidaMaxima());

        vidaJugador.setValue(
                batalla.getAliado().getVida());

        vidaJugador.setString(
                batalla.getAliado().getVida()
                        + "/"
                        + batalla.getAliado().getVidaMaxima());

        lblEnemigo.setText(
                batalla.getSalvaje().getNombre());

        lblNivelEnemigo.setText(
                "Nivel " + batalla.getSalvaje().getNivel());

        vidaEnemigo.setMaximum(
                batalla.getSalvaje().getVidaMaxima());

        vidaEnemigo.setValue(
                batalla.getSalvaje().getVida());

        vidaEnemigo.setString(
                batalla.getSalvaje().getVida()
                        + "/"
                        + batalla.getSalvaje().getVidaMaxima());

    }

    private void registrarEventos() {

        // -----------------------------------------
        // ATACAR
        // -----------------------------------------

        btnAtacar.addActionListener(e -> {

            if (batalla == null)
                return;

            txtRegistro.append("\n" + batalla.atacar());

            actualizarPantalla();

            verificarFin();

        });

        // -----------------------------------------
        // POCION
        // -----------------------------------------

        btnPocion.addActionListener(e -> {

            if (batalla == null)
                return;

            txtRegistro.append("\n" + batalla.usarPocion());

            actualizarPantalla();

            verificarFin();

        });

        // -----------------------------------------
        // CAPTURAR
        // -----------------------------------------

        btnCapturar.addActionListener(e -> {

            if (batalla == null)
                return;

            txtRegistro.append("\n" + batalla.lanzarEsfera());

            actualizarPantalla();

            verificarFin();

        });

        // -----------------------------------------
        // HUIR
        // -----------------------------------------

        btnHuir.addActionListener(e -> {

            if (batalla == null)
                return;

            txtRegistro.append("\n" + batalla.huir());

            JOptionPane.showMessageDialog(this,
                    "Has escapado del combate.");

            ventana.mostrar("MENU");

        });

        // -----------------------------------------
        // ATAQUE ESPECIAL (DOWNCASTING)
        // -----------------------------------------

        btnEspecial.addActionListener(e -> {

            if (batalla == null)
                return;

            txtRegistro.append(batalla.usarAtaqueEspecial());

            actualizarPantalla();

            verificarFin();

        });

    }

    private void usarAtaqueEspecial() {

        Criatura aliado = batalla.getAliado();

        Criatura enemigo = batalla.getSalvaje();

        if (aliado instanceof Pyron) {

            ((Pyron) aliado).llamaFinal(enemigo);

            txtRegistro.append("\nPyron usó LLAMA FINAL.");

        }

        else if (aliado instanceof Aquos) {

            ((Aquos) aliado).tsunami(enemigo);

            txtRegistro.append("\nAquos usó TSUNAMI.");

        }

        else if (aliado instanceof Floran) {

            ((Floran) aliado).hojasAfiladas(enemigo);

            txtRegistro.append("\nFloran usó HOJAS AFILADAS.");

        }

        else if (aliado instanceof Voltix) {

            ((Voltix) aliado).tormentaElectrica(enemigo);

            txtRegistro.append("\nVoltix usó TORMENTA ELÉCTRICA.");

        }

        else if (aliado instanceof Umbrix) {

            ((Umbrix) aliado).eclipseOscuro(enemigo);

            txtRegistro.append("\nUmbrix usó ECLIPSE OSCURO.");

        }

        if (enemigo.estaVivo()) {

            enemigo.atacar(aliado);

            txtRegistro.append("\nEl enemigo contraatacó.");

        }

    }

    private void verificarFin() {

        if (!batalla.getAliado().estaVivo()) {

            JOptionPane.showMessageDialog(this,
                    "Has perdido el combate.");

            ventana.mostrar("MENU");

            return;

        }

        if (!batalla.getSalvaje().estaVivo()) {

            JOptionPane.showMessageDialog(this,
                    "¡Has ganado el combate!");

            ventana.mostrar("MENU");

        }

    }

}