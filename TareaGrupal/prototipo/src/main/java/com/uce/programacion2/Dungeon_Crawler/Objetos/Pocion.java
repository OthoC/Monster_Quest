package com.uce.programacion2.Dungeon_Crawler.Objetos;

import com.uce.programacion2.Dungeon_Crawler.Criaturas.Criatura;


public class Pocion extends Objeto {

    private int curacion;

    public Pocion() {
        super("Poción");
        curacion = 30;
    }

    public int getCuracion() {
        return curacion;
    }

    public void setCuracion(int curacion) {
        this.curacion = curacion;
    }

    public void usar(Criatura criatura) {

        if (!criatura.estaVivo()) {
            System.out.println("No puedes curar una criatura derrotada.");
            return;
        }

        if (criatura.getVida() == criatura.getVidaMaxima()) {
            System.out.println(criatura.getNombre() + " ya tiene la vida completa.");
            return;
        }

        int nuevaVida = criatura.getVida() + curacion;

        if (nuevaVida > criatura.getVidaMaxima()) {
            nuevaVida = criatura.getVidaMaxima();
        }

        criatura.setVida(nuevaVida);

        System.out.println(getNombre() + " utilizada.");
        System.out.println(criatura.getNombre() + " recuperó " + curacion + " puntos de vida.");
        System.out.println("Vida actual: " + criatura.getVida() + "/" + criatura.getVidaMaxima());

    }

    @Override
    public void usar() {
        System.out.println("Debes seleccionar una criatura para usar la poción.");
    }

}