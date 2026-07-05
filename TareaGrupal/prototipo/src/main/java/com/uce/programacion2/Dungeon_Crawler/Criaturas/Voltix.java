package com.uce.programacion2.Dungeon_Crawler.Criaturas;

public final class Voltix extends Criatura {

    public Voltix() {
        super("Voltix", "Eléctrico", 90, 28);
    }

    @Override
    public void atacar(Criatura enemigo) {

        realizarAtaque(enemigo, "Rayo Eléctrico");

    }

    public void tormentaElectrica(Criatura enemigo) {

        System.out.println(getNombre() + " usa TORMENTA ELÉCTRICA!");

        enemigo.recibirDanio(getAtaque() + 20);

    }

    @Override
    public void evolucionar() {

        System.out.println(getNombre() + " evolucionó a Voltix X.");

    }

}