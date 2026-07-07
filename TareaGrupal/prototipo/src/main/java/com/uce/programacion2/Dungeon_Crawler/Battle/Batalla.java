package com.uce.programacion2.Dungeon_Crawler.Battle;

import com.uce.programacion2.Dungeon_Crawler.Criaturas.Criatura;
import com.uce.programacion2.Dungeon_Crawler.Objetos.Esfera;
import com.uce.programacion2.Dungeon_Crawler.Objetos.Pocion;
import com.uce.programacion2.Dungeon_Crawler.Personajes.Jugador;

public class Batalla {

    private Jugador jugador;
    private Criatura aliado;
    private Criatura salvaje;

    private boolean terminada;
    private boolean victoria;

    public Batalla(Jugador jugador, Criatura salvaje) {

        this.jugador = jugador;
        this.salvaje = salvaje;
        this.aliado = jugador.obtenerPrimeraCriatura();

        terminada = false;
        victoria = false;

    }

    //===========================
    // GETTERS
    //===========================

    public Jugador getJugador() {
        return jugador;
    }

    public Criatura getAliado() {
        return aliado;
    }

    public Criatura getSalvaje() {
        return salvaje;
    }

    public boolean batallaTerminada() {
        return terminada;
    }

    public boolean jugadorGano() {
        return victoria;
    }

    //===========================
    // MÉTODOS AUXILIARES
    //===========================

    private void verificarEstado() {

        if (!salvaje.estaVivo()) {

            victoria = true;
            terminada = true;

            aliado.ganarExperiencia(40);

            jugador.agregarDinero(25);

            jugador.sumarVictoria();

        }

        if (!aliado.estaVivo()) {

            victoria = false;
            terminada = true;

        }

    }

    protected void turnoEnemigo() {

        if (salvaje.estaVivo() && aliado.estaVivo()) {

            salvaje.atacar(aliado);

        }

    }

        //===========================
    // ATAQUE NORMAL
    //===========================

    public String atacar() {

        if (terminada) {
            return "La batalla ya terminó.";
        }

        StringBuilder resultado = new StringBuilder();

        // Ataque del jugador
        resultado.append(aliado.getNombre())
                 .append(" atacó a ")
                 .append(salvaje.getNombre())
                 .append(".\n");

        aliado.atacar(salvaje);

        // Si el enemigo sigue vivo, contraataca
        if (salvaje.estaVivo()) {

            resultado.append(salvaje.getNombre())
                     .append(" contraatacó.\n");

            salvaje.atacar(aliado);

        }

        verificarEstado();

        if (terminada) {

            if (victoria) {

                resultado.append("\n¡Has ganado la batalla!");

            } else {

                resultado.append("\nHas sido derrotado.");

            }

        }

        return resultado.toString();

    }

    //===========================
    // ATAQUE ESPECIAL
    //===========================

    public String usarAtaqueEspecial() {

        if (terminada) {
            return "La batalla ya terminó.";
        }

        StringBuilder resultado = new StringBuilder();

        int danio = aliado.getAtaque() + 20;

        resultado.append(aliado.getNombre())
                 .append(" lanzó un ataque especial.\n");

        salvaje.recibirDanio(danio);

        if (salvaje.estaVivo()) {

            resultado.append(salvaje.getNombre())
                     .append(" contraatacó.\n");

            salvaje.atacar(aliado);

        }

        verificarEstado();

        if (terminada) {

            if (victoria) {

                resultado.append("\n¡Has ganado la batalla!");

            } else {

                resultado.append("\nHas sido derrotado.");

            }

        }

        return resultado.toString();

    }

        //===========================
    // USAR POCIÓN
    //===========================

    public String usarPocion() {

        if (terminada) {
            return "La batalla ya terminó.";
        }

        Pocion pocion = new Pocion();

        pocion.usar(aliado);

        StringBuilder resultado = new StringBuilder();

        resultado.append(aliado.getNombre())
                 .append(" recuperó vida.\n");

        if (salvaje.estaVivo()) {

            resultado.append(salvaje.getNombre())
                     .append(" atacó.\n");

            salvaje.atacar(aliado);

        }

        verificarEstado();

        if (terminada) {

            if (victoria) {

                resultado.append("\n¡Has ganado la batalla!");

            } else {

                resultado.append("\nHas sido derrotado.");

            }

        }

        return resultado.toString();

    }

    //===========================
    // LANZAR ESFERA
    //===========================

    public String lanzarEsfera() {

        if (terminada) {
            return "La batalla ya terminó.";
        }

        Esfera esfera = new Esfera();

        if (esfera.usar(salvaje)) {

            jugador.agregarCriatura(salvaje);

            salvaje.setVida(0);

            victoria = true;

            terminada = true;

            return "¡Capturaste a " + salvaje.getNombre() + "!";

        }

        StringBuilder resultado = new StringBuilder();

        resultado.append("La criatura escapó de la esfera.\n");

        if (salvaje.estaVivo()) {

            salvaje.atacar(aliado);

            resultado.append(salvaje.getNombre())
                     .append(" contraatacó.");

        }

        verificarEstado();

        return resultado.toString();

    }

    //===========================
    // HUIR
    //===========================

    public String huir() {

        terminada = true;

        victoria = false;

        return "Has huido del combate.";

    }

    

}