package com.uce.programacion2.Dungeon_Crawler.GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.uce.programacion2.Dungeon_Crawler.Criaturas.Aquos;
import com.uce.programacion2.Dungeon_Crawler.Criaturas.Floran;
import com.uce.programacion2.Dungeon_Crawler.Criaturas.Pyron;

public class PanelInicial extends JPanel {

    private VentanaPrincipal ventana;

    public PanelInicial(VentanaPrincipal ventana) {

        this.ventana = ventana;

        construir();

    }

    private void construir() {

        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("ELIGE TU CRIATURA");

        titulo.setHorizontalAlignment(JLabel.CENTER);

        titulo.setFont(new Font("Arial", Font.BOLD, 30));

        add(titulo, BorderLayout.NORTH);

        JPanel centro = new JPanel();

        centro.setLayout(new GridLayout(1, 3, 20, 20));

        JButton pyron = new JButton("Pyron");

        JButton aquos = new JButton("Aquos");

        JButton floran = new JButton("Floran");

        centro.add(pyron);

        centro.add(aquos);

        centro.add(floran);

        add(centro, BorderLayout.CENTER);

        pyron.addActionListener(e -> {

            ventana.getJuego().getJugador().agregarCriatura(new Pyron());

            JOptionPane.showMessageDialog(this,
                    "Elegiste a Pyron.");

            ventana.mostrar("MENU");

        });

        aquos.addActionListener(e -> {

            ventana.getJuego().getJugador().agregarCriatura(new Aquos());

            JOptionPane.showMessageDialog(this,
                    "Elegiste a Aquos.");

            ventana.mostrar("MENU");

        });

        floran.addActionListener(e -> {

            ventana.getJuego().getJugador().agregarCriatura(new Floran());

            JOptionPane.showMessageDialog(this,
                    "Elegiste a Floran.");

            ventana.mostrar("MENU");

        });

    }

}