package com.uce.programacion2.Dungeon_Crawler.Personajes;

import com.uce.programacion2.Dungeon_Crawler.Criaturas.Criatura;

public class Entrenador {

    private String nombre;
    private Criatura[] equipo;
    private int cantidad;

    {
        equipo = new Criatura[3];
        cantidad = 0;
    }

    public Entrenador(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void agregarCriatura(Criatura criatura) {

        if (cantidad < equipo.length) {

            equipo[cantidad] = criatura;
            cantidad++;

        }

    }

    public Criatura getCriatura(int posicion) {

        if (posicion >= 0 && posicion < cantidad) {
            return equipo[posicion];
        }

        return null;

    }

    public int getCantidad() {
        return cantidad;
    }

    public void mostrarEquipo() {

        System.out.println("\nEntrenador: " + nombre);

        for (int i = 0; i < cantidad; i++) {

            System.out.println((i + 1) + ". " + equipo[i].getNombre());

        }

    }

}