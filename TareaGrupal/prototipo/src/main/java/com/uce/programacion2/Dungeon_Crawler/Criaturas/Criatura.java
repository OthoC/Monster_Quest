package com.uce.programacion2.Dungeon_Crawler.Criaturas;

import com.uce.programacion2.Dungeon_Crawler.Interfaces.Atacante;
import com.uce.programacion2.Dungeon_Crawler.Interfaces.Capturable;
import com.uce.programacion2.Dungeon_Crawler.Interfaces.Evolucionable;
import com.uce.programacion2.Dungeon_Crawler.Util.Tipos;

public sealed abstract class Criatura
        implements Atacante, Capturable, Evolucionable
        permits Pyron, Aquos, Floran, Voltix, Umbrix {

    private String nombre;
    private String tipo;

    private int nivel;
    private int experiencia;

    private int vida;
    private int vidaMaxima;

    private int ataque;

    // Guarda el resultado de la ventaja/desventaja de tipo del último
    // ataque realizado ("¡Es muy eficaz!", "No es muy eficaz..." o "").
    // Batalla lo lee después de pedirle a la criatura que ataque para
    // agregarlo al registro de combate.
    protected String ultimoMensajeEfectividad = "";

    // Bloque de instancia
    {
        nivel = 1;
        experiencia = 0;
    }

    public Criatura(String nombre, String tipo, int vidaMaxima, int ataque) {

        this.nombre = nombre;
        this.tipo = tipo;
        this.vidaMaxima = vidaMaxima;
        this.vida = vidaMaxima;
        this.ataque = ataque;

    }

    // ==========================
    // GETTERS
    // ==========================

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public int getNivel() {
        return nivel;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public int getVida() {
        return vida;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getAtaque() {
        return ataque;
    }

    public String getUltimoMensajeEfectividad() {
        return ultimoMensajeEfectividad;
    }

    // ==========================
    // SETTERS
    // ==========================

    public void setVida(int vida) {

        if (vida > vidaMaxima) {
            this.vida = vidaMaxima;
        } else if (vida < 0) {
            this.vida = 0;
        } else {
            this.vida = vida;
        }

    }

    // ==========================
    // MÉTODOS GENERALES
    // ==========================

    public void recibirDanio(int danio) {

        setVida(vida - danio);

    }

    public void curarCompleto() {

        vida = vidaMaxima;

    }

    public boolean estaVivo() {

        return vida > 0;

    }

    public void ganarExperiencia(int exp) {

        experiencia += exp;

        while (experiencia >= 100) {

            experiencia -= 100;
            subirNivel();

        }

    }

    private void subirNivel() {

        nivel++;

        vidaMaxima += 15;
        vida = vidaMaxima;
        ataque += 5;

        System.out.println();
        System.out.println("¡" + nombre + " subió al nivel " + nivel + "!");
        System.out.println("Vida máxima: " + vidaMaxima);
        System.out.println("Ataque: " + ataque);
        System.out.println();

    }

    protected void realizarAtaque(Criatura enemigo, String habilidad) {

    System.out.println(getNombre() + " usa " + habilidad + ".");

    // Antes el daño era siempre igual a getAtaque(), sin importar el
    // tipo de la criatura atacante ni el de la defensora.
    // enemigo.recibirDanio(getAtaque());

    // Ahora se aplica el multiplicador de ventaja/desventaja de tipo
    // (ver Util.Tipos) y se guarda el mensaje de efectividad para que
    // Batalla lo muestre en el registro de combate.
    double multiplicador = Tipos.multiplicador(getTipo(), enemigo.getTipo());

    int danio = (int) Math.round(getAtaque() * multiplicador);

    enemigo.recibirDanio(danio);

    ultimoMensajeEfectividad = Tipos.descripcion(multiplicador);

}

    public void mostrarInformacion() {

        System.out.println("----------------------------");
        System.out.println("Nombre : " + nombre);
        System.out.println("Tipo   : " + tipo);
        System.out.println("Nivel  : " + nivel);
        System.out.println("Vida   : " + vida + "/" + vidaMaxima);
        System.out.println("Ataque : " + ataque);
        System.out.println("EXP    : " + experiencia + "/100");
        System.out.println("----------------------------");

    }

    // ==========================
    // CAPTURA
    // ==========================

    @Override
    public boolean capturar() {

        double probabilidad;

        if (vida <= vidaMaxima * 0.25) {

            probabilidad = 0.90;

        } else if (vida <= vidaMaxima * 0.50) {

            probabilidad = 0.60;

        } else {

            probabilidad = 0.30;

        }

        return Math.random() < probabilidad;

    }

    // ==========================
    // MÉTODOS ABSTRACTOS
    // ==========================

    @Override
    public abstract void atacar(Criatura enemigo);

    @Override
    public abstract void evolucionar();

    /**
     * Crea una criatura nueva del mismo tipo, con las estadísticas base
     * de fábrica (nivel 1, vida completa). Cada subclase debe devolver
     * "new NombreDeLaClase()".
     *
     * Se usa para que las criaturas guardadas en el mundo (Mundo/Ruta)
     * actúen como "plantillas": al entrar en combate se entrega una copia
     * fresca en vez de la instancia original, así el daño de un combate
     * no queda pegado al monstruo salvaje para siempre.
     */
    public abstract Criatura copiar();

    /**
     * Ejecuta la habilidad especial propia de cada criatura (más daño que
     * un ataque normal). Cada subclase delega en su método específico
     * (llamaFinal, tsunami, hojasAfiladas, etc.) que ya existía pero no
     * se estaba usando desde ningún botón de la interfaz.
     */
    public abstract void ataqueEspecial(Criatura enemigo);

}