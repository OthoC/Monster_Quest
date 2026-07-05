package com.uce.programacion2.Dungeon_Crawler.Objetos;

import com.uce.programacion2.Dungeon_Crawler.Criaturas.Criatura;

public class Esfera extends Objeto {

    public Esfera() {
        super("Esfera");
    }

    public boolean usar(Criatura criatura) {

        if (!criatura.estaVivo()) {

            System.out.println("No puedes capturar una criatura derrotada.");
            return false;

        }

        boolean capturada = criatura.capturar();

        if (capturada) {

            System.out.println("¡Has capturado a " + criatura.getNombre() + "!");

        } else {

            System.out.println(criatura.getNombre() + " escapó de la esfera.");

        }

        return capturada;

    }

    @Override
    public void usar() {
        System.out.println("Debes seleccionar una criatura para capturar.");
    }

}