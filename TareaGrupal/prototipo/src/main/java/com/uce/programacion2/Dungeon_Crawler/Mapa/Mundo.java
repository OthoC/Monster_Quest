package com.uce.programacion2.Dungeon_Crawler.Mapa;

import com.uce.programacion2.Dungeon_Crawler.Criaturas.Aquos;
import com.uce.programacion2.Dungeon_Crawler.Criaturas.Floran;
import com.uce.programacion2.Dungeon_Crawler.Criaturas.Pyron;
import com.uce.programacion2.Dungeon_Crawler.Criaturas.Umbrix;
import com.uce.programacion2.Dungeon_Crawler.Criaturas.Voltix;

public class Mundo {

    private Ruta[][] regiones;
    private String[] nombresRegiones;

    {
        nombresRegiones = new String[]{ "Región 1 — Naturaleza", "Región 2 — Oscuridad", "Región 3 — Extremos" };

        regiones = new Ruta[3][];

        // REGIÓN 1
        regiones[0] = new Ruta[2];

        regiones[0][0] = new Ruta(
                "Bosque Verde",
                "Un frondoso bosque lleno de vida vegetal y criaturas de fuego que buscan calor.",
                1,
                new com.uce.programacion2.Dungeon_Crawler.Criaturas.Criatura[]{
                        new Floran(), new Floran(), new Pyron()
                });

        regiones[0][1] = new Ruta(
                "Lago Cristal",
                "Aguas cristalinas donde habitan criaturas acuáticas y misteriosas energías eléctricas.",
                2,
                new com.uce.programacion2.Dungeon_Crawler.Criaturas.Criatura[]{
                        new Aquos(), new Aquos(), new Voltix()
                });

        // REGIÓN 2
        regiones[1] = new Ruta[1];

        regiones[1][0] = new Ruta(
                "Cueva Oscura",
                "Una cueva sin luz donde entidades oscuras y eléctricas acechen en las sombras.",
                4,
                new com.uce.programacion2.Dungeon_Crawler.Criaturas.Criatura[]{
                        new Umbrix(), new Voltix(), new Pyron()
                });

        // REGIÓN 3
        regiones[2] = new Ruta[3];

        regiones[2][0] = new Ruta(
                "Volcán Rojo",
                "Un volcán activo con temperaturas extremas. Solo las criaturas de fuego y oscuras sobreviven.",
                6,
                new com.uce.programacion2.Dungeon_Crawler.Criaturas.Criatura[]{
                        new Pyron(), new Pyron(), new Umbrix()
                });

        regiones[2][1] = new Ruta(
                "Ruinas Antiguas",
                "Restos de una civilización perdida. Criaturas oscuras y eléctricas la consideran su hogar.",
                7,
                new com.uce.programacion2.Dungeon_Crawler.Criaturas.Criatura[]{
                        new Umbrix(), new Floran(), new Voltix()
                });

        regiones[2][2] = new Ruta(
                "Pantano Tóxico",
                "Aguas oscuras y vegetación putrefacta. Un lugar peligroso lleno de criaturas acuáticas y plantas mutadas.",
                8,
                new com.uce.programacion2.Dungeon_Crawler.Criaturas.Criatura[]{
                        new Aquos(), new Umbrix(), new Floran()
                });
    }

    public Ruta obtenerRuta(int region, int ruta) {
        if (region < 0 || region >= regiones.length) return null;
        if (ruta < 0 || ruta >= regiones[region].length) return null;
        return regiones[region][ruta];
    }

    public int getCantidadRegiones()            { return regiones.length; }
    public String getNombreRegion(int i)        { return nombresRegiones[i]; }
    public Ruta[][] getRegiones()               { return regiones; }

    public int getCantidadRutas(int region) {
        if (region < 0 || region >= regiones.length) return 0;
        return regiones[region].length;
    }

    public void mostrarMapa() {
        System.out.println("\n========== MAPA ==========");
        for (int i = 0; i < regiones.length; i++) {
            System.out.println("\n" + nombresRegiones[i]);
            for (int j = 0; j < regiones[i].length; j++) {
                System.out.println((j + 1) + ". " + regiones[i][j].getNombre());
            }
        }
    }

}
