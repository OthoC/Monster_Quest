package com.uce.programacion2.Dungeon_Crawler.GUI;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.uce.programacion2.Dungeon_Crawler.Criaturas.Criatura;

public class PanelEquipo extends JPanel {

    private VentanaPrincipal ventana;

    private DefaultListModel<String> modelo;

    public PanelEquipo(VentanaPrincipal ventana) {

        this.ventana = ventana;

        setLayout(new BorderLayout());

        modelo = new DefaultListModel<>();

        JList<String> lista = new JList<>(modelo);

        add(new JScrollPane(lista), BorderLayout.CENTER);

        JButton volver = new JButton("Volver");

        volver.addActionListener(e -> ventana.mostrar("MENU"));

        add(volver, BorderLayout.SOUTH);

    }

    public void actualizar() {

        modelo.clear();

        if (ventana.getJuego().getJugador() == null)
            return;

        for (int i = 0; i < ventana.getJuego().getJugador().getCantidadCriaturas(); i++) {

            Criatura c = ventana.getJuego().getJugador().getCriatura(i);

            modelo.addElement(
                    c.getNombre() +
                    "  HP:" + c.getVida() +
                    "  Tipo: " + c.getTipo());

        }

    }

}
