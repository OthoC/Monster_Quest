package com.uce.programacion2.Dungeon_Crawler.Personajes;

import com.uce.programacion2.Dungeon_Crawler.Criaturas.Criatura;
import com.uce.programacion2.Dungeon_Crawler.Objetos.Inventario;
import com.uce.programacion2.Dungeon_Crawler.Objetos.Pocion;
import com.uce.programacion2.Dungeon_Crawler.Objetos.Esfera;
public class Jugador {

    private String nombre;
    private Criatura[] equipo;
    private int cantidadCriaturas;
    private Inventario inventario;
    private int dinero;
    private int victorias;



    // Bloque de instancia
    {
        equipo = new Criatura[6];
        cantidadCriaturas = 0;
        inventario = new Inventario();
        dinero = 100;
        victorias = 0;

        // Ahora que las pociones y esferas se consumen de verdad al usarlas
        // (ver Batalla.usarPocion/lanzarEsfera), el jugador necesita algunos
        // objetos iniciales para poder jugar sin pasar primero por la tienda.
        inventario.agregarObjeto(new Pocion());
        inventario.agregarObjeto(new Pocion());
        inventario.agregarObjeto(new Esfera());
        inventario.agregarObjeto(new Esfera());
        inventario.agregarObjeto(new Esfera());
    }

    public Jugador(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public int getDinero() {
        return dinero;
    }

    public int getVictorias() {
        return victorias;
    }

    public int getCantidadCriaturas() {
        return cantidadCriaturas;
    }

    public void agregarDinero(int cantidad) {
        dinero += cantidad;
    }

    public void sumarVictoria() {
        victorias++;
    }

    public boolean equipoLleno() {
        return cantidadCriaturas == equipo.length;
    }


    public boolean gastarDinero(int cantidad) {

    if (dinero >= cantidad) {

        dinero -= cantidad;
        return true;

    }

    return false;

}


    public void agregarCriatura(Criatura criatura) {

        if (!equipoLleno()) {

            equipo[cantidadCriaturas] = criatura;
            cantidadCriaturas++;

        } else {

            System.out.println("El equipo está completo.");

        }

    }

    public Criatura getCriatura(int posicion) {

        if (posicion >= 0 && posicion < cantidadCriaturas) {
            return equipo[posicion];
        }

        return null;

    }

    public Criatura obtenerPrimeraCriatura() {

        if (cantidadCriaturas > 0) {
            return equipo[0];
        }

        return null;

    }

    /**
     * Cura por completo a todas las criaturas del equipo. Se llama al
     * terminar un combate (ganado, perdido o huido) para que el jugador
     * nunca quede "atascado" con criaturas debilitadas para siempre:
     * antes el daño de un combate se acarreaba indefinidamente al
     * siguiente porque Criatura.recibirDanio() nunca se revertía.
     */
    public void curarEquipo() {

        for (int i = 0; i < cantidadCriaturas; i++) {

            equipo[i].curarCompleto();

        }

    }

    public void mostrarEquipo() {

        if (cantidadCriaturas == 0) {

            System.out.println("No tienes criaturas.");

            return;

        }

        

        System.out.println("\n===== EQUIPO =====");

        for (int i = 0; i < cantidadCriaturas; i++) {

            System.out.println((i + 1) + ". " + equipo[i].getNombre()
                    + " | Nivel " + equipo[i].getNivel()
                    + " | Vida " + equipo[i].getVida()
                    + "/" + equipo[i].getVidaMaxima());

        }

    }

}