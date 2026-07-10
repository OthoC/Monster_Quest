package com.uce.programacion2.Dungeon_Crawler.Criaturas;

import com.uce.programacion2.Dungeon_Crawler.Util.Tipos;

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

        // enemigo.recibirDanio(getAtaque() + 20);

        double multiplicador = Tipos.multiplicador(getTipo(), enemigo.getTipo());

        int danio = (int) Math.round((getAtaque() + 20) * multiplicador);

        enemigo.recibirDanio(danio);

        ultimoMensajeEfectividad = Tipos.descripcion(multiplicador);

    }

    @Override
    public void evolucionar() {

        System.out.println(getNombre() + " evolucionó a Floran X.");

    }

    // Devuelve un Floran nuevo (nivel 1, vida llena) independiente de la
    // "plantilla" guardada en el mundo.
    @Override
    public Criatura copiar() {

        return new Floran();

    }

    // Delega en la habilidad especial ya existente de esta criatura.
    @Override
    public void ataqueEspecial(Criatura enemigo) {

        hojasAfiladas(enemigo);

    }

}