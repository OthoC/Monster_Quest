package com.uce.programacion2.Dungeon_Crawler.Mapa;

import com.uce.programacion2.Dungeon_Crawler.Criaturas.Aquos;
import com.uce.programacion2.Dungeon_Crawler.Criaturas.Criatura;
import com.uce.programacion2.Dungeon_Crawler.Criaturas.Floran;
import com.uce.programacion2.Dungeon_Crawler.Criaturas.Voltix;
import com.uce.programacion2.Dungeon_Crawler.Criaturas.Umbrix;
import com.uce.programacion2.Dungeon_Crawler.Criaturas.Pyron;

public class Mundo {

    private Ruta[][] regiones;

    // Bloque de instancia
    {

        regiones = new Ruta[3][];

        // ==========================
        // REGIÓN 1
        // ==========================

        regiones[0] = new Ruta[2];

        regiones[0][0] = new Ruta(
                "Bosque Verde",
                new Criatura[] {
                        new Floran(),
                        new Floran(),
                        new Pyron()
                });

        regiones[0][1] = new Ruta(
                "Lago Cristal",
                new Criatura[] {
                        new Aquos(),
                        new Aquos(),
                        new Voltix()
                });

        // ==========================
        // REGIÓN 2
        // ==========================

        regiones[1] = new Ruta[1];

        regiones[1][0] = new Ruta(
                "Cueva Oscura",
                new Criatura[] {
                        new Umbrix(),
                        new Voltix(),
                        new Pyron()
                });

        // ==========================
        // REGIÓN 3
        // ==========================

        regiones[2] = new Ruta[3];

        regiones[2][0] = new Ruta(
                "Volcán Rojo",
                new Criatura[] {
                        new Pyron(),
                        new Pyron(),
                        new Umbrix()
                });

        regiones[2][1] = new Ruta(
                "Ruinas Antiguas",
                new Criatura[] {
                        new Umbrix(),
                        new Floran(),
                        new Voltix()
                });

        regiones[2][2] = new Ruta(
                "Pantano Tóxico",
                new Criatura[] {
                        new Aquos(),
                        new Umbrix(),
                        new Floran()
                });

    }

    public Ruta obtenerRuta(int region, int ruta) {

        if (region < 0 || region >= regiones.length) {
            return null;
        }

        if (ruta < 0 || ruta >= regiones[region].length) {
            return null;
        }

        return regiones[region][ruta];

    }

    public int getCantidadRegiones() {
        return regiones.length;
    }

    public int getCantidadRutas(int region) {

        if (region < 0 || region >= regiones.length) {
            return 0;
        }

        return regiones[region].length;

    }

    public void mostrarMapa() {

        System.out.println("\n========== MAPA ==========");

        for (int i = 0; i < regiones.length; i++) {

            System.out.println("\nRegión " + (i + 1));

            for (int j = 0; j < regiones[i].length; j++) {

                System.out.println((j + 1) + ". "
                        + regiones[i][j].getNombre());

            }

        }

    }

}