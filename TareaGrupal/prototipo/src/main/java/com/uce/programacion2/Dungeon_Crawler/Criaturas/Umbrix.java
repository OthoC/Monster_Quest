package com.uce.programacion2.Dungeon_Crawler.Criaturas;

import com.uce.programacion2.Dungeon_Crawler.Util.Tipos;

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

        // enemigo.recibirDanio(getAtaque() + 20);

        double multiplicador = Tipos.multiplicador(getTipo(), enemigo.getTipo());

        int danio = (int) Math.round((getAtaque() + 20) * multiplicador);

        enemigo.recibirDanio(danio);

        ultimoMensajeEfectividad = Tipos.descripcion(multiplicador);

    }

    @Override
    public void evolucionar() {

        System.out.println(getNombre() + " evolucionó a Umbrix X.");

    }

    // Devuelve un Umbrix nuevo (nivel 1, vida llena) independiente de la
    // "plantilla" guardada en el mundo. La subclase anónima del Jefe Final
    // (ver Juego.crearBatallaJefe) hereda esta misma implementación.
    @Override
    public Criatura copiar() {

        return new Umbrix();

    }

    // Delega en la habilidad especial ya existente de esta criatura.
    @Override
    public void ataqueEspecial(Criatura enemigo) {

        eclipseOscuro(enemigo);

    }

}