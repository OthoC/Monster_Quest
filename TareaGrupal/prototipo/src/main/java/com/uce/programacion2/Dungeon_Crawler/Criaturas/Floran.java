package com.uce.programacion2.Dungeon_Crawler.Criaturas;

public final class Floran extends Criatura {

    public Floran() {
        super("Floran", "Planta", 95, 23);
    }

    @Override
    public void atacar(Criatura enemigo) {

        realizarAtaque(enemigo, "Hoja Cortante");

    }

    public void hojasAfiladas(Criatura enemigo) {

        System.out.println(getNombre() + " usa HOJAS AFILADAS!");

        enemigo.recibirDanio(getAtaque() + 20);

    }

    @Override
    public void evolucionar() {

        System.out.println(getNombre() + " evolucionó a Floran X.");

    }

}