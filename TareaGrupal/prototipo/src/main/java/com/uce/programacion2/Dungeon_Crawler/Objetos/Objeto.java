package com.uce.programacion2.Dungeon_Crawler.Objetos;

public abstract class Objeto {

    private String nombre;

    public Objeto(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public abstract void usar();

}