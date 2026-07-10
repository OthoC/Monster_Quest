package com.uce.programacion2.Dungeon_Crawler.Mapa;

import com.uce.programacion2.Dungeon_Crawler.Criaturas.Criatura;

public class Ruta {

    private String nombre;
    private String descripcion;
    private int nivelRecomendado;
    private Criatura[] criaturas;

    public Ruta(String nombre, Criatura[] criaturas) {
        this(nombre, "", 1, criaturas);
    }

    public Ruta(String nombre, String descripcion, int nivelRecomendado, Criatura[] criaturas) {
        this.nombre            = nombre;
        this.descripcion       = descripcion;
        this.nivelRecomendado  = nivelRecomendado;
        this.criaturas         = criaturas;
    }

    public String getNombre()           { return nombre; }
    public String getDescripcion()      { return descripcion; }
    public int getNivelRecomendado()    { return nivelRecomendado; }
    public Criatura[] getCriaturas()    { return criaturas; }

    public Criatura obtenerCriaturaAleatoria() {
        int indice = (int) (Math.random() * criaturas.length);
        return criaturas[indice].copiar();
    }

    public void mostrarRuta() {
        System.out.println("\n===== " + nombre + " =====");
        for (Criatura criatura : criaturas) {
            System.out.println("- " + criatura.getNombre());
        }
    }

}
