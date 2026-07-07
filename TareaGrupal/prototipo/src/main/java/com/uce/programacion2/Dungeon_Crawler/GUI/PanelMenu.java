package com.uce.programacion2.Dungeon_Crawler.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PanelMenu extends JPanel {

    private VentanaPrincipal ventana;

    public PanelMenu(VentanaPrincipal ventana) {

        this.ventana = ventana;

        construir();

    }

    private void construir() {

        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("ECLIPSE REALMS");

        titulo.setHorizontalAlignment(JLabel.CENTER);

        titulo.setFont(new Font("Arial", Font.BOLD, 32));

        add(titulo, BorderLayout.NORTH);

        JPanel botones = new JPanel();

        botones.setLayout(new GridLayout(8, 1, 10, 10));

        botones.setPreferredSize(new Dimension(300, 400));

        JButton iniciar = new JButton("Nuevo Juego");

        JButton equipo = new JButton("Equipo");

        JButton inventario = new JButton("Inventario");

        JButton explorar = new JButton("Explorar");

        JButton tienda = new JButton("Tienda");

        JButton entrenador = new JButton("Entrenador");

        JButton jefe = new JButton("Jefe Final");

        JButton salir = new JButton("Salir");

        botones.add(iniciar);

        botones.add(equipo);

        botones.add(inventario);

        botones.add(explorar);

        botones.add(tienda);

        botones.add(entrenador);

        botones.add(jefe);

        botones.add(salir);

        add(botones, BorderLayout.CENTER);

        // =============================
        // NUEVO JUEGO
        // =============================

        iniciar.addActionListener(e -> {

            String nombre = JOptionPane.showInputDialog(
                    this,
                    "Nombre del entrenador:");

            if (nombre == null || nombre.isBlank()) {
                return;
            }

            ventana.getJuego().crearJugador(nombre);

            ventana.mostrar("INICIAL");

        });

        // =============================
        // EQUIPO
        // =============================

        equipo.addActionListener(e -> {

            if (ventana.getJuego().getJugador() == null) {

                JOptionPane.showMessageDialog(this,
                        "Primero inicia una partida.");

                return;

            }

            ventana.getPanelEquipo().actualizar();

            ventana.mostrar("EQUIPO");

        });

        // =============================
        // INVENTARIO
        // =============================

        inventario.addActionListener(e -> {

            if (ventana.getJuego().getJugador() == null) {

                JOptionPane.showMessageDialog(this,
                        "Primero inicia una partida.");

                return;

            }

            ventana.getPanelInventario().actualizar();

            ventana.mostrar("INVENTARIO");

        });

        // =============================
        // EXPLORAR
        // =============================

        explorar.addActionListener(e -> {

            if (ventana.getJuego().getJugador() == null) {

                JOptionPane.showMessageDialog(this,
                        "Primero crea un entrenador.");

                return;

            }

            if (ventana.getJuego().getJugador().getCantidadCriaturas() == 0) {

                JOptionPane.showMessageDialog(this,
                        "Primero elige una criatura inicial.");

                return;

            }

            ventana.getPanelMapa().actualizarMapa();

            ventana.mostrar("MAPA");

        });

        // =============================
        // TIENDA
        // =============================

        tienda.addActionListener(e -> {

            if (ventana.getJuego().getJugador() == null) {

                JOptionPane.showMessageDialog(this,
                        "Primero inicia una partida.");

                return;

            }

            ventana.getPanelTienda().actualizar();

            ventana.mostrar("TIENDA");

        });

        // =============================
        // ENTRENADOR
        // =============================

        entrenador.addActionListener(e -> {

            if (ventana.getJuego().getJugador() == null) {

                JOptionPane.showMessageDialog(this,
                        "Primero inicia una partida.");

                return;

            }

            ventana.getPanelEntrenador().actualizar();

            ventana.mostrar("ENTRENADOR");

        });

        // =============================
        // JEFE FINAL
        // =============================

        jefe.addActionListener(e -> {

            JOptionPane.showMessageDialog(this,
                    "Esta función se implementará desde el Panel de Batalla.");

        });

        // =============================
        // SALIR
        // =============================

        salir.addActionListener(e -> System.exit(0));

    }

}