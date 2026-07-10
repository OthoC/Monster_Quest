package com.uce.programacion2.Dungeon_Crawler.Util;

/**
 * Calcula ventajas y desventajas de daño entre los tipos de criatura,
 * al estilo "piedra-papel-tijera" de un RPG de monstruos.
 *
 * Relaciones de ventaja (atacante x1.5 de daño, defensor x0.75):
 *   Fuego      vence a Planta
 *   Agua       vence a Fuego
 *   Planta     vence a Agua
 *   Eléctrico  vence a Agua
 *   Oscuro     no tiene ventajas ni debilidades definidas (siempre neutral)
 *
 * Cualquier combinación no listada arriba es neutral (x1.0).
 */
public class Tipos {

    public static final double VENTAJA = 1.5;
    public static final double DESVENTAJA = 0.75;
    public static final double NEUTRAL = 1.0;

    /**
     * Devuelve el multiplicador de daño que corresponde cuando una
     * criatura de "tipoAtacante" ataca a una de "tipoDefensor".
     */
    public static double multiplicador(String tipoAtacante, String tipoDefensor) {

        if (esVentaja(tipoAtacante, tipoDefensor)) {
            return VENTAJA;
        }

        if (esVentaja(tipoDefensor, tipoAtacante)) {
            return DESVENTAJA;
        }

        return NEUTRAL;

    }

    private static boolean esVentaja(String atacante, String defensor) {

        return (atacante.equals("Fuego") && defensor.equals("Planta"))
            || (atacante.equals("Agua") && defensor.equals("Fuego"))
            || (atacante.equals("Planta") && defensor.equals("Agua"))
            || (atacante.equals("Eléctrico") && defensor.equals("Agua"));

    }

    /**
     * Texto para mostrar en el registro de combate según el multiplicador
     * calculado ("" si el ataque fue neutral).
     */
    public static String descripcion(double multiplicador) {

        if (multiplicador > NEUTRAL) {
            return "¡Es muy eficaz!";
        }

        if (multiplicador < NEUTRAL) {
            return "No es muy eficaz...";
        }

        return "";

    }

}
