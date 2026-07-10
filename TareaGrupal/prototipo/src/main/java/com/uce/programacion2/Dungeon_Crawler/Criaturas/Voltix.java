package com.uce.programacion2.Dungeon_Crawler.Criaturas;

import com.uce.programacion2.Dungeon_Crawler.Util.Tipos;

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

        // enemigo.recibirDanio(getAtaque() + 20);

        double multiplicador = Tipos.multiplicador(getTipo(), enemigo.getTipo());

        int danio = (int) Math.round((getAtaque() + 20) * multiplicador);

        enemigo.recibirDanio(danio);

        ultimoMensajeEfectividad = Tipos.descripcion(multiplicador);

    }

    @Override
    public void evolucionar() {

        System.out.println(getNombre() + " evolucionó a Voltix X.");

    }

    // Devuelve un Voltix nuevo (nivel 1, vida llena) independiente de la
    // "plantilla" guardada en el mundo.
    @Override
    public Criatura copiar() {

        return new Voltix();

    }

    // Delega en la habilidad especial ya existente de esta criatura.
    @Override
    public void ataqueEspecial(Criatura enemigo) {

        tormentaElectrica(enemigo);

    }

}