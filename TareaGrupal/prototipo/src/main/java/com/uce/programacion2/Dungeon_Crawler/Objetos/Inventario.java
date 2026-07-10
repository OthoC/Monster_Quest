package com.uce.programacion2.Dungeon_Crawler.Objetos;

public class Inventario {

    private Objeto[] objetos;
    private int cantidad;

    // Bloque de instancia
    {
        objetos = new Objeto[20];
        cantidad = 0;
    }

    public boolean inventarioLleno() {
        return cantidad == objetos.length;
    }

    public void agregarObjeto(Objeto objeto) {

        if (!inventarioLleno()) {

            objetos[cantidad] = objeto;
            cantidad++;

        } else {

            System.out.println("Inventario lleno.");

        }

    }

    public Objeto obtenerObjeto(int posicion) {

        if (posicion >= 0 && posicion < cantidad) {

            return objetos[posicion];

        }

        return null;

    }

    public void eliminarObjeto(int posicion) {

        if (posicion < 0 || posicion >= cantidad) {
            return;
        }

        for (int i = posicion; i < cantidad - 1; i++) {

            objetos[i] = objetos[i + 1];

        }

        objetos[cantidad - 1] = null;
        cantidad--;

    }

    public int getCantidad() {
        return cantidad;
    }

    /**
     * Busca el primer objeto cuyo nombre coincida (por ejemplo "Poción" o
     * "Esfera") y devuelve su posición, o -1 si no hay ninguno.
     * Se usa en Batalla para consumir un objeto real del inventario en
     * vez de crear uno nuevo de la nada cada vez que se usa.
     */
    public int buscarPrimero(String nombre) {

        for (int i = 0; i < cantidad; i++) {

            if (objetos[i].getNombre().equals(nombre)) {
                return i;
            }

        }

        return -1;

    }

    public void mostrarInventario() {

        if (cantidad == 0) {

            System.out.println("Inventario vacío.");

            return;

        }

        System.out.println("\n===== INVENTARIO =====");

        for (int i = 0; i < cantidad; i++) {

            System.out.println((i + 1) + ". " + objetos[i].getNombre());

        }

    }

}