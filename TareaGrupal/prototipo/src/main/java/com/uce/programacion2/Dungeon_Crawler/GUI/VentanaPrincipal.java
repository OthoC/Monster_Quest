package com.uce.programacion2.Dungeon_Crawler.GUI;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.uce.programacion2.Dungeon_Crawler.Principal.Juego;

public class VentanaPrincipal extends JFrame {

    private Juego juego;

    private CardLayout cardLayout;
    private JPanel contenedor;
    private PanelMapa panelMapa;
    private PanelMenu panelMenu;
    private PanelInicial panelInicial;
    private PanelEquipo panelEquipo;
    private PanelBatalla panelBatalla;
    private PanelInventario panelInventario;
    private PanelEntrenador panelEntrenador;
    private PanelTienda panelTienda;

    public VentanaPrincipal() {

        juego = new Juego();

        setTitle("Monster Quest");

        setSize(900, 600);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(false);

        cardLayout = new CardLayout();

        contenedor = new JPanel(cardLayout);

        panelMenu = new PanelMenu(this);

        panelInicial = new PanelInicial(this);

        panelEquipo = new PanelEquipo(this);

        panelMapa = new PanelMapa(this);

        panelBatalla = new PanelBatalla(this);

        panelInventario = new PanelInventario(this);

        panelEntrenador = new PanelEntrenador(this);

        panelTienda = new PanelTienda(this);

        contenedor.add(panelMenu, "MENU");

        contenedor.add(panelInicial, "INICIAL");

        contenedor.add(panelEquipo, "EQUIPO");

        contenedor.add(panelMapa, "MAPA");

        contenedor.add(panelBatalla, "BATALLA");

        contenedor.add(panelInventario, "INVENTARIO");

        contenedor.add(panelEntrenador, "ENTRENADOR");

        contenedor.add(panelTienda, "TIENDA");

        add(contenedor);

        mostrar("MENU");

        setVisible(true);

    }

    public void mostrar(String panel) {

        cardLayout.show(contenedor, panel);

    }

    public Juego getJuego() {

        return juego;

    }

    public JPanel getContenedor() {

        return contenedor;

    }

    public PanelEquipo getPanelEquipo() {

        return panelEquipo;

    }

    public PanelMapa getPanelMapa() {

        return panelMapa;

    }

    public PanelBatalla getPanelBatalla() {
        return panelBatalla;
    }

    public PanelInventario getPanelInventario() {
        return panelInventario;
    }

    public PanelEntrenador getPanelEntrenador() {
        return panelEntrenador;
    }

    public PanelTienda getPanelTienda() {
        return panelTienda;
    }

}