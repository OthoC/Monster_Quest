package com.uce.programacion2.Dungeon_Crawler.GUI;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.uce.programacion2.Dungeon_Crawler.Objetos.Inventario;
import com.uce.programacion2.Dungeon_Crawler.Objetos.Objeto;

public class PanelInventario extends JPanel {

    private VentanaPrincipal ventana;

    private JTextArea txtInventario;

    public PanelInventario(VentanaPrincipal ventana) {

        this.ventana = ventana;

        construir();

    }

    private void construir() {

        setLayout(new BorderLayout(10,10));

        JLabel titulo = new JLabel("INVENTARIO");

        titulo.setHorizontalAlignment(JLabel.CENTER);

        titulo.setFont(new Font("Arial", Font.BOLD, 28));

        add(titulo, BorderLayout.NORTH);

        txtInventario = new JTextArea();

        txtInventario.setEditable(false);

        add(new JScrollPane(txtInventario), BorderLayout.CENTER);

        JButton volver = new JButton("Volver");

        volver.addActionListener(e -> ventana.mostrar("MENU"));

        add(volver, BorderLayout.SOUTH);

    }

    public void actualizar() {

        txtInventario.setText("");

        Inventario inventario =
                ventana.getJuego().getJugador().getInventario();

        if(inventario.getCantidad()==0){

            txtInventario.append("Inventario vacío.");

            return;

        }

        for(int i=0;i<inventario.getCantidad();i++){

            Objeto objeto = inventario.obtenerObjeto(i);

            txtInventario.append((i+1)+". "+objeto.getNombre()+"\n");

        }

    }

}