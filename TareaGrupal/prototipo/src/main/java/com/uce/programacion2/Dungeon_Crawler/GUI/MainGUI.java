package com.uce.programacion2.Dungeon_Crawler.GUI;

import javax.swing.SwingUtilities;

public class MainGUI {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new VentanaPrincipal();

        });

    }

}