package com.uce.programacion2.Dungeon_Crawler.Criaturas;

public final class Pyron extends Criatura {

    public Pyron() {
        super("Pyron", "Fuego", 100, 25);
    }

    @Override
    public void atacar(Criatura enemigo) {

        realizarAtaque(enemigo, "Llama Ardiente");

    }

    public void llamaFinal(Criatura enemigo) {

        System.out.println(getNombre() + " usa LLAMA FINAL!");

        enemigo.recibirDanio(getAtaque() + 20);

    }

    @Override
    public void evolucionar() {

        System.out.println(getNombre() + " evolucionó a Pyron X.");

    }

}