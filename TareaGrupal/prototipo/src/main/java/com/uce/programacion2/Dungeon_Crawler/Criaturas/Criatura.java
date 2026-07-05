package com.uce.programacion2.Dungeon_Crawler.Criaturas;

import com.uce.programacion2.Dungeon_Crawler.Interfaces.Atacante;
import com.uce.programacion2.Dungeon_Crawler.Interfaces.Capturable;
import com.uce.programacion2.Dungeon_Crawler.Interfaces.Evolucionable;

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

    enemigo.recibirDanio(getAtaque());

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

}