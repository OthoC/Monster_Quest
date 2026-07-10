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
    // CAMBIAR CRIATURA ACTIVA
    //===========================

    /**
     * Cambia la criatura que pelea actualmente por otra del equipo del
     * jugador (debe estar viva). Al igual que las demás acciones de
     * combate, cambiar de criatura consume el turno: el enemigo ataca
     * después del cambio.
     *
     * Antes la batalla solo usaba jugador.obtenerPrimeraCriatura() y no
     * había forma de rotar de criatura durante el combate.
     */
    public String cambiarCriatura(int indice) {

        if (terminada) {
            return "La batalla ya terminó.";
        }

        Criatura nueva = jugador.getCriatura(indice);

        if (nueva == null) {
            return "Esa criatura no existe.";
        }

        if (!nueva.estaVivo()) {
            return nueva.getNombre() + " está debilitado y no puede pelear.";
        }

        aliado = nueva;

        StringBuilder resultado = new StringBuilder();

        resultado.append("Ahora peleas con ")
                 .append(aliado.getNombre())
                 .append(".\n");

        if (salvaje.estaVivo()) {

            salvaje.atacar(aliado);

            resultado.append(salvaje.getNombre())
                     .append(" atacó.");

        }

        verificarEstado();

        if (terminada) {

            if (victoria) {
                resultado.append("\n\n¡Has ganado la batalla!");
            } else {
                resultado.append("\n\nHas sido derrotado.");
            }

        }

        return resultado.toString();

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

        // Mensaje de ventaja/desventaja de tipo del ataque que se acaba
        // de realizar (ver Criatura.realizarAtaque / Util.Tipos).
        if (!aliado.getUltimoMensajeEfectividad().isEmpty()) {
            resultado.append(aliado.getUltimoMensajeEfectividad()).append("\n");
        }

        // Si el enemigo sigue vivo, contraataca
        if (salvaje.estaVivo()) {

            resultado.append(salvaje.getNombre())
                     .append(" contraatacó.\n");

            salvaje.atacar(aliado);

            if (!salvaje.getUltimoMensajeEfectividad().isEmpty()) {
                resultado.append(salvaje.getUltimoMensajeEfectividad()).append("\n");
            }

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

        // Antes el ataque especial era genérico (mismo bono +20 de daño
        // para cualquier criatura) y las habilidades propias de cada una
        // (llamaFinal, tsunami, hojasAfiladas...) nunca se llamaban.
        // int danio = aliado.getAtaque() + 20;
        // resultado.append(aliado.getNombre())
        //          .append(" lanzó un ataque especial.\n");
        // salvaje.recibirDanio(danio);

        // Ahora cada criatura ejecuta su propia habilidad especial
        // (polimorfismo: ver Criatura.ataqueEspecial y su implementación
        // en cada subclase, que delega en el método ya existente).
        resultado.append(aliado.getNombre())
                 .append(" lanzó un ataque especial.\n");

        aliado.ataqueEspecial(salvaje);

        if (!aliado.getUltimoMensajeEfectividad().isEmpty()) {
            resultado.append(aliado.getUltimoMensajeEfectividad()).append("\n");
        }

        if (salvaje.estaVivo()) {

            resultado.append(salvaje.getNombre())
                     .append(" contraatacó.\n");

            salvaje.atacar(aliado);

            if (!salvaje.getUltimoMensajeEfectividad().isEmpty()) {
                resultado.append(salvaje.getUltimoMensajeEfectividad()).append("\n");
            }

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

        // Antes se creaba una Poción de la nada en cada uso, sin
        // descontarla del inventario: pociones infinitas y gratis.
        // Pocion pocion = new Pocion();
        // pocion.usar(aliado);

        // Ahora se exige y se consume una Poción real del inventario.
        int posicion = jugador.getInventario().buscarPrimero("Poción");

        if (posicion == -1) {
            return "No tienes pociones.";
        }

        Pocion pocion = (Pocion) jugador.getInventario().obtenerObjeto(posicion);

        pocion.usar(aliado);

        jugador.getInventario().eliminarObjeto(posicion);

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

        // Antes se creaba una Esfera de la nada en cada lanzamiento, sin
        // descontarla del inventario: esferas infinitas y gratis.
        // Esfera esfera = new Esfera();

        // Ahora se exige y se consume una Esfera real del inventario.
        int posicion = jugador.getInventario().buscarPrimero("Esfera");

        if (posicion == -1) {
            return "No tienes esferas.";
        }

        Esfera esfera = (Esfera) jugador.getInventario().obtenerObjeto(posicion);

        jugador.getInventario().eliminarObjeto(posicion);

        if (esfera.usar(salvaje)) {

            jugador.agregarCriatura(salvaje);

            // "salvaje" ahora es una copia independiente (ver Ruta.copiar()),
            // ya no es la instancia compartida del mundo, así que no hace
            // falta "matarla" al capturarla: se queda con la vida que tenía.
            // salvaje.setVida(0);

            // Recompensa por captura: misma experiencia, dinero y victoria
            // que se otorgan al ganar un combate normal (ver verificarEstado).
            aliado.ganarExperiencia(40);

            jugador.agregarDinero(25);

            jugador.sumarVictoria();

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