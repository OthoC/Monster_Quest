package com.uce.programacion2.Dungeon_Crawler.GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.uce.programacion2.Dungeon_Crawler.Personajes.Jugador;
import com.uce.programacion2.Dungeon_Crawler.Objetos.Pocion;
import com.uce.programacion2.Dungeon_Crawler.Objetos.Esfera;

public class PanelTienda extends JPanel {

    private VentanaPrincipal ventana;

    private JLabel lblDinero;

    public PanelTienda(VentanaPrincipal ventana) {

        this.ventana = ventana;

        construir();

    }

    private void construir() {

        setLayout(new BorderLayout(10,10));

        JLabel titulo = new JLabel("TIENDA");

        titulo.setHorizontalAlignment(JLabel.CENTER);

        titulo.setFont(new Font("Arial", Font.BOLD, 28));

        add(titulo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridLayout(3,1,10,10));

        lblDinero = new JLabel();

        JButton btnPocion = new JButton("Comprar Poción ($20)");

        JButton btnEsfera = new JButton("Comprar Esfera ($15)");

        centro.add(lblDinero);
        centro.add(btnPocion);
        centro.add(btnEsfera);

        add(centro, BorderLayout.CENTER);

        JButton volver = new JButton("Volver");

        volver.addActionListener(e -> ventana.mostrar("MENU"));

        add(volver, BorderLayout.SOUTH);

        btnPocion.addActionListener(e -> comprarPocion());

        btnEsfera.addActionListener(e -> comprarEsfera());

    }

    public void actualizar() {

        Jugador jugador = ventana.getJuego().getJugador();

        lblDinero.setText("Dinero disponible: $" + jugador.getDinero());

    }

    private void comprarPocion() {

        Jugador jugador = ventana.getJuego().getJugador();

        if (jugador.gastarDinero(20)) {

            jugador.getInventario().agregarObjeto(new Pocion());

            JOptionPane.showMessageDialog(this,
                    "Compraste una Poción.");

        } else {

            JOptionPane.showMessageDialog(this,
                    "No tienes suficiente dinero.");

        }

        actualizar();

    }

    private void comprarEsfera() {

        Jugador jugador = ventana.getJuego().getJugador();

        if (jugador.gastarDinero(15)) {

            jugador.getInventario().agregarObjeto(new Esfera());

            JOptionPane.showMessageDialog(this,
                    "Compraste una Esfera.");

        } else {

            JOptionPane.showMessageDialog(this,
                    "No tienes suficiente dinero.");

        }

        actualizar();

    }

}