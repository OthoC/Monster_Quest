package com.uce.programacion2.Dungeon_Crawler.Mapa;

import com.uce.programacion2.Dungeon_Crawler.Criaturas.Criatura;

public class Ruta {

    private String nombre;
    private Criatura[] criaturas;

    public Ruta(String nombre, Criatura[] criaturas) {

        this.nombre = nombre;
        this.criaturas = criaturas;

    }

    public String getNombre() {
        return nombre;
    }

    public Criatura[] getCriaturas() {
        return criaturas;
    }

    public Criatura obtenerCriaturaAleatoria() {

        int indice = (int) (Math.random() * criaturas.length);

        return criaturas[indice];

    }

    public void mostrarRuta() {

        System.out.println("\n===== " + nombre + " =====");

        for (Criatura criatura : criaturas) {

            System.out.println("- " + criatura.getNombre());

        }

    }

}