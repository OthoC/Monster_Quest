package com.uce.programacion2.Dungeon_Crawler.GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.uce.programacion2.Dungeon_Crawler.Personajes.Jugador;

public class PanelEntrenador extends JPanel {

    private VentanaPrincipal ventana;

    private JLabel lblNombre;
    private JLabel lblDinero;
    private JLabel lblVictorias;
    private JLabel lblCriaturas;

    public PanelEntrenador(VentanaPrincipal ventana) {

        this.ventana = ventana;

        construir();

    }

    private void construir() {

        setLayout(new BorderLayout(10,10));

        JLabel titulo = new JLabel("ENTRENADOR");

        titulo.setHorizontalAlignment(JLabel.CENTER);

        titulo.setFont(new Font("Arial",Font.BOLD,28));

        add(titulo,BorderLayout.NORTH);

        JPanel datos = new JPanel(new GridLayout(4,1,10,10));

        lblNombre = new JLabel();

        lblDinero = new JLabel();

        lblVictorias = new JLabel();

        lblCriaturas = new JLabel();

        datos.add(lblNombre);

        datos.add(lblDinero);

        datos.add(lblVictorias);

        datos.add(lblCriaturas);

        add(datos,BorderLayout.CENTER);

        JButton volver = new JButton("Volver");

        volver.addActionListener(e->ventana.mostrar("MENU"));

        add(volver,BorderLayout.SOUTH);

    }

    public void actualizar(){

        Jugador jugador = ventana.getJuego().getJugador();

        lblNombre.setText("Nombre: "+jugador.getNombre());

        lblDinero.setText("Dinero: $"+jugador.getDinero());

        lblVictorias.setText("Victorias: "+jugador.getVictorias());

        lblCriaturas.setText("Criaturas: "+jugador.getCantidadCriaturas());

    }

}