package com.uce.programacion2.Dungeon_Crawler.Criaturas;

import com.uce.programacion2.Dungeon_Crawler.Util.Tipos;

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

        // enemigo.recibirDanio(getAtaque() + 20);

        double multiplicador = Tipos.multiplicador(getTipo(), enemigo.getTipo());

        int danio = (int) Math.round((getAtaque() + 20) * multiplicador);

        enemigo.recibirDanio(danio);

        ultimoMensajeEfectividad = Tipos.descripcion(multiplicador);

    }

    @Override
    public void evolucionar() {

        System.out.println(getNombre() + " evolucionó a Aquos X.");

    }

    // Devuelve un Aquos nuevo (nivel 1, vida llena) independiente de la
    // "plantilla" guardada en el mundo.
    @Override
    public Criatura copiar() {

        return new Aquos();

    }

    // Delega en la habilidad especial ya existente de esta criatura.
    @Override
    public void ataqueEspecial(Criatura enemigo) {

        tsunami(enemigo);

    }

}
