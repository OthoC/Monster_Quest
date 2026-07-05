package com.uce.programacion2.Dungeon_Crawler.Criaturas;

public non-sealed class Umbrix extends Criatura {

    public Umbrix() {
        super("Umbrix", "Oscuro", 120, 18);
    }

    @Override
    public void atacar(Criatura enemigo) {

        realizarAtaque(enemigo, "Sombra Oscura");

    }

    public void eclipseOscuro(Criatura enemigo) {

        System.out.println(getNombre() + " usa ECLIPSE OSCURO!");

        enemigo.recibirDanio(getAtaque() + 20);

    }

    @Override
    public void evolucionar() {

        System.out.println(getNombre() + " evolucionó a Umbrix X.");

    }

}