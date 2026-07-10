package com.uce.programacion2.Dungeon_Crawler.Criaturas;

import com.uce.programacion2.Dungeon_Crawler.Util.Tipos;

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

        // enemigo.recibirDanio(getAtaque() + 20);

        double multiplicador = Tipos.multiplicador(getTipo(), enemigo.getTipo());

        int danio = (int) Math.round((getAtaque() + 20) * multiplicador);

        enemigo.recibirDanio(danio);

        ultimoMensajeEfectividad = Tipos.descripcion(multiplicador);

    }

    @Override
    public void evolucionar() {

        System.out.println(getNombre() + " evolucionó a Pyron X.");

    }

    // Devuelve un Pyron nuevo (nivel 1, vida llena) para usar como
    // enemigo salvaje independiente de la "plantilla" guardada en el mundo.
    @Override
    public Criatura copiar() {

        return new Pyron();

    }

    // Delega en la habilidad especial ya existente de esta criatura.
    @Override
    public void ataqueEspecial(Criatura enemigo) {

        llamaFinal(enemigo);

    }

}