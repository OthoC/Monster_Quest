package com.uce.programacion2.Dungeon_Crawler.Criaturas;

public final class Aquos extends Criatura {

    public Aquos() {
        super("Aquos", "Agua", 110, 20);
    }

    @Override
    public void atacar(Criatura enemigo) {

        realizarAtaque(enemigo, "Ola Marina");

    }

    public void tsunami(Criatura enemigo) {

        System.out.println(getNombre() + " usa TSUNAMI!");

        enemigo.recibirDanio(getAtaque() + 20);

    }

    @Override
    public void evolucionar() {

        System.out.println(getNombre() + " evolucionó a Aquos X.");

    }

}
