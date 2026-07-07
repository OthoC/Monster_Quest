package com.uce.programacion2.Dungeon_Crawler.GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.uce.programacion2.Dungeon_Crawler.Mapa.Ruta;

public class PanelMapa extends JPanel {

    private VentanaPrincipal ventana;

    private JPanel panelRegiones;
    private JPanel panelRutas;

    public PanelMapa(VentanaPrincipal ventana) {

        this.ventana = ventana;

        construir();

    }

    private void construir() {

        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("EXPLORAR MUNDO");

        titulo.setHorizontalAlignment(JLabel.CENTER);

        add(titulo, BorderLayout.NORTH);

        panelRegiones = new JPanel();

        panelRutas = new JPanel();

        add(panelRegiones, BorderLayout.WEST);

        add(panelRutas, BorderLayout.CENTER);

        JButton volver = new JButton("Volver");

        volver.addActionListener(e -> ventana.mostrar("MENU"));

        add(volver, BorderLayout.SOUTH);

    }

    /**
     * Se ejecuta cada vez que se abre el mapa.
     */
    public void actualizarMapa() {

        panelRegiones.removeAll();

        int cantidadRegiones = ventana.getJuego()
                .getMundo()
                .getCantidadRegiones();

        panelRegiones.setLayout(new GridLayout(cantidadRegiones, 1, 10, 10));

        for (int i = 0; i < cantidadRegiones; i++) {

            JButton boton = new JButton("Región " + (i + 1));

            int indice = i;

            boton.addActionListener(e -> mostrarRutas(indice));

            panelRegiones.add(boton);

        }

        panelRegiones.revalidate();

        panelRegiones.repaint();

        panelRutas.removeAll();

        panelRutas.revalidate();

        panelRutas.repaint();

    }

    private void mostrarRutas(int region) {

        panelRutas.removeAll();

        int cantidadRutas = ventana.getJuego()
                .getMundo()
                .getCantidadRutas(region);

        panelRutas.setLayout(new GridLayout(cantidadRutas, 1, 10, 10));

        for (int i = 0; i < cantidadRutas; i++) {

            Ruta ruta = ventana.getJuego()
                    .getMundo()
                    .obtenerRuta(region, i);

            JButton botonRuta = new JButton(ruta.getNombre());

            int indiceRuta = i;

            botonRuta.addActionListener(e -> explorar(region, indiceRuta));

            panelRutas.add(botonRuta);

        }

        panelRutas.revalidate();

        panelRutas.repaint();

    }

    private void explorar(int region, int ruta) {

    Ruta rutaSeleccionada = ventana.getJuego()
            .getMundo()
            .obtenerRuta(region, ruta);

    var criatura = rutaSeleccionada.obtenerCriaturaAleatoria();

    ventana.getPanelBatalla().iniciarBatalla(

            new com.uce.programacion2.Dungeon_Crawler.Battle.Batalla(

                    ventana.getJuego().getJugador(),
                    criatura));

    ventana.mostrar("BATALLA");

}
}