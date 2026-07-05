package com.uce.programacion2.Dungeon_Crawler.Battle;

import java.util.Scanner;
import com.uce.programacion2.Dungeon_Crawler.Criaturas.Criatura;
import com.uce.programacion2.Dungeon_Crawler.Objetos.Esfera;
import com.uce.programacion2.Dungeon_Crawler.Objetos.Pocion;
import com.uce.programacion2.Dungeon_Crawler.Personajes.Jugador;


public class Batalla {

    private Jugador jugador;
    private Criatura salvaje;
    private Scanner teclado;

    {
        teclado = new Scanner(System.in);
    }

    public Batalla(Jugador jugador, Criatura salvaje) {

        this.jugador = jugador;
        this.salvaje = salvaje;

    }

    public void iniciar() {

        Criatura aliado = jugador.obtenerPrimeraCriatura();

        if (aliado == null) {

            System.out.println("No tienes criaturas para combatir.");
            return;

        }

        int opcion;

        do {

            System.out.println("\n==============================");
            System.out.println("        COMBATE");
            System.out.println("==============================");

            mostrarEstado(aliado);
            mostrarEstado(salvaje);

            System.out.println();
            System.out.println("1. Atacar");
            System.out.println("2. Usar poción");
            System.out.println("3. Lanzar esfera");
            System.out.println("4. Huir");

            System.out.print("Opción: ");

            opcion = teclado.nextInt();

            switch (opcion) {

                case 1:

                    turnoAtaque(aliado);

                    break;

                case 2:

                    usarPocion(aliado);

                    break;

                case 3:

                    lanzarEsfera();

                    if (!salvaje.estaVivo()) {
                        return;
                    }

                    break;

                case 4:

                    System.out.println("Has huido.");

                    return;

                default:

                    System.out.println("Opción inválida.");

            }

            if (!salvaje.estaVivo()) {

                System.out.println();

                System.out.println("¡Has ganado el combate!");

                aliado.ganarExperiencia(40);

                jugador.agregarDinero(25);

                jugador.sumarVictoria();

                return;

            }

        } while (aliado.estaVivo());

        System.out.println("Tu criatura fue derrotada.");

    }

    private void turnoAtaque(Criatura aliado) {

        aliado.atacar(salvaje);

        if (salvaje.estaVivo()) {

            salvaje.atacar(aliado);

        }

    }

    private void usarPocion(Criatura aliado) {

        Pocion pocion = new Pocion();

        pocion.usar(aliado);

    }

    private void lanzarEsfera() {

        Esfera esfera = new Esfera();

        if (esfera.usar(salvaje)) {

            System.out.println("¡Capturaste a " + salvaje.getNombre() + "!");

            jugador.agregarCriatura(salvaje);

            salvaje.setVida(0);

        } else {

            System.out.println("La criatura escapó de la esfera.");

        }

    }

    private void mostrarEstado(Criatura criatura) {

        System.out.println();

        System.out.println(criatura.getNombre());

        System.out.println("Nivel : " + criatura.getNivel());

        System.out.println("Vida  : "
                + criatura.getVida()
                + "/"
                + criatura.getVidaMaxima());

    }

}