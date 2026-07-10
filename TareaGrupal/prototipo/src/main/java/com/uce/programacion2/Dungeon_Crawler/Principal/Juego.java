package com.uce.programacion2.Dungeon_Crawler.Principal;

// import java.util.Scanner;

import com.uce.programacion2.Dungeon_Crawler.Battle.Batalla;
import com.uce.programacion2.Dungeon_Crawler.Criaturas.Aquos;
import com.uce.programacion2.Dungeon_Crawler.Criaturas.Criatura;
import com.uce.programacion2.Dungeon_Crawler.Criaturas.Floran;
import com.uce.programacion2.Dungeon_Crawler.Criaturas.Pyron;
import com.uce.programacion2.Dungeon_Crawler.Criaturas.Umbrix;
import com.uce.programacion2.Dungeon_Crawler.Mapa.Mundo;
import com.uce.programacion2.Dungeon_Crawler.Mapa.Ruta;
import com.uce.programacion2.Dungeon_Crawler.Personajes.Jugador;

/**
 * Estado central de una partida (jugador + mundo). El juego original se
 * jugaba por consola (Scanner) con iniciar()/menuPrincipal()/etc. Desde
 * que existe la interfaz gráfica (VentanaPrincipal y los PanelXxx), esos
 * métodos de consola ya no se usan: la GUI llama directamente a los
 * métodos de la sección "MÉTODOS PARA LA GUI". Se dejan comentados abajo
 * como referencia de la versión anterior en vez de borrarlos.
 */
public class Juego {

    // private Scanner teclado;
    private Jugador jugador;
    private Mundo mundo;

    // Bloque de instancia
    {
        // teclado = new Scanner(System.in);
        mundo = new Mundo();
    }

    // ==========================================================
    // FLUJO DE CONSOLA (reemplazado por la interfaz gráfica Swing)
    // ==========================================================

    // public void iniciar() {
    //
    //     System.out.println("=================================");
    //     System.out.println("         MONSTER QUEST");
    //     System.out.println("=================================");
    //
    //     System.out.print("Ingrese el nombre del entrenador: ");
    //     String nombre = teclado.nextLine();
    //
    //     jugador = new Jugador(nombre);
    //
    //     menuPrincipal();
    //
    // }
    //
    // private void menuPrincipal() {
    //
    //     int opcion;
    //
    //     do {
    //
    //         System.out.println("\n========== MENÚ ==========");
    //         System.out.println("1. Elegir criatura inicial");
    //         System.out.println("2. Ver equipo");
    //         System.out.println("3. Explorar");
    //         System.out.println("4. Ver inventario");
    //         System.out.println("5. Ver entrenador");
    //         System.out.println("6. Desafiar Jefe Final");
    //         System.out.println("7. Salir");
    //
    //         System.out.print("Opción: ");
    //         opcion = teclado.nextInt();
    //
    //         switch (opcion) {
    //
    //             case 1:
    //                 elegirInicial();
    //                 break;
    //
    //             case 2:
    //                 jugador.mostrarEquipo();
    //                 break;
    //
    //             case 3:
    //                 explorar();
    //                 break;
    //
    //             case 4:
    //                 jugador.getInventario().mostrarInventario();
    //                 break;
    //
    //             case 5:
    //                 mostrarJugador();
    //                 break;
    //
    //             case 6:
    //                 desafiarJefe();
    //                 break;
    //
    //             case 7:
    //                 System.out.println("¡Gracias por jugar!");
    //                 break;
    //
    //             default:
    //                 System.out.println("Opción inválida.");
    //
    //         }
    //
    //     } while (opcion != 7);
    //
    // }
    //
    // private void elegirInicial() {
    //
    //     if (jugador.getCantidadCriaturas() > 0) {
    //
    //         System.out.println("Ya elegiste tu criatura inicial.");
    //         return;
    //
    //     }
    //
    //     System.out.println("\n===== ELIGE TU CRIATURA =====");
    //     System.out.println("1. Pyron");
    //     System.out.println("2. Aquos");
    //     System.out.println("3. Floran");
    //
    //     System.out.print("Opción: ");
    //
    //     int opcion = teclado.nextInt();
    //
    //     Criatura inicial;
    //
    //     switch (opcion) {
    //
    //         case 1:
    //             inicial = new Pyron();
    //             break;
    //
    //         case 2:
    //             inicial = new Aquos();
    //             break;
    //
    //         case 3:
    //             inicial = new Floran();
    //             break;
    //
    //         default:
    //             System.out.println("Opción inválida.");
    //             return;
    //
    //     }
    //
    //     jugador.agregarCriatura(inicial);
    //
    //     System.out.println("\nHas elegido a " + inicial.getNombre());
    //
    // }
    //
    // private void explorar() {
    //
    //     if (jugador.getCantidadCriaturas() == 0) {
    //
    //         System.out.println("Primero debes elegir una criatura.");
    //         return;
    //
    //     }
    //
    //     System.out.println("\n========== MAPA ==========");
    //
    //     mundo.mostrarMapa();
    //
    //     System.out.print("\nSeleccione una región: ");
    //     int region = teclado.nextInt() - 1;
    //
    //     if (region < 0 || region >= mundo.getCantidadRegiones()) {
    //
    //         System.out.println("Región inválida.");
    //         return;
    //
    //     }
    //
    //     System.out.print("Seleccione una ruta: ");
    //     int ruta = teclado.nextInt() - 1;
    //
    //     if (ruta < 0 || ruta >= mundo.getCantidadRutas(region)) {
    //
    //         System.out.println("Ruta inválida.");
    //         return;
    //
    //     }
    //
    //     Ruta rutaSeleccionada = mundo.obtenerRuta(region, ruta);
    //
    //     System.out.println("\nEntraste a " + rutaSeleccionada.getNombre());
    //
    //     Criatura salvaje = rutaSeleccionada.obtenerCriaturaAleatoria();
    //
    //     System.out.println("¡Ha aparecido un " + salvaje.getNombre() + " salvaje!");
    //
    //     Batalla batalla = new Batalla(jugador, salvaje);
    //
    //     // En la versión Swing este objeto será enviado al PanelBatalla.
    //     System.out.println("La batalla ha sido creada.");
    //
    // }
    //
    // private void desafiarJefe() {
    //
    //     if (jugador.getCantidadCriaturas() == 0) {
    //
    //         System.out.println("Primero debes elegir una criatura.");
    //         return;
    //
    //     }
    //
    //     System.out.println("\n==================================");
    //     System.out.println("          JEFE FINAL");
    //     System.out.println("==================================");
    //
    //     Criatura jefe = new Umbrix() {
    //
    //         @Override
    //         public void atacar(Criatura enemigo) {
    //
    //             System.out.println(getNombre() + " usa ECLIPSE SUPREMO.");
    //
    //             enemigo.recibirDanio(getAtaque() + 40);
    //
    //         }
    //
    //         @Override
    //         public void evolucionar() {
    //
    //             System.out.println(getNombre() + " ya alcanzó su forma definitiva.");
    //
    //         }
    //
    //     };
    //
    //     System.out.println("¡Ha aparecido el Jefe Final!");
    //
    //     Batalla batalla = new Batalla(jugador, jefe);
    //
    //     // En Swing este objeto será enviado al PanelBatalla.
    //     System.out.println("La batalla contra el jefe ha sido creada.");
    //
    // }
    //
    // private void mostrarJugador() {
    //
    //     System.out.println("\n===== ENTRENADOR =====");
    //     System.out.println("Nombre      : " + jugador.getNombre());
    //     System.out.println("Dinero      : $" + jugador.getDinero());
    //     System.out.println("Victorias   : " + jugador.getVictorias());
    //     System.out.println("Criaturas   : " + jugador.getCantidadCriaturas());
    //
    // }

    // =========================
    // MÉTODOS PARA LA GUI
    // =========================

    public void crearJugador(String nombre) {

        jugador = new Jugador(nombre);

    }

    public Jugador getJugador() {

        return jugador;

    }

    public Mundo getMundo() {

        return mundo;

    }

    public boolean elegirInicialGUI(int opcion) {

        if (jugador == null)
            return false;

        if (jugador.getCantidadCriaturas() > 0)
            return false;

        switch (opcion) {

            case 1:
                jugador.agregarCriatura(new Pyron());
                break;

            case 2:
                jugador.agregarCriatura(new Aquos());
                break;

            case 3:
                jugador.agregarCriatura(new Floran());
                break;

            default:
                return false;

        }

        return true;

    }

    public Batalla crearBatalla(Ruta ruta) {

        if (jugador == null || ruta == null)
            return null;

        return new Batalla(jugador, ruta.obtenerCriaturaAleatoria());

    }

    public Batalla crearBatallaJefe() {

        if (jugador == null)
            return null;

        Criatura jefe = new Umbrix() {

            @Override
            public void atacar(Criatura enemigo) {

                System.out.println(getNombre() + " usa ECLIPSE SUPREMO.");

                enemigo.recibirDanio(getAtaque() + 40);

            }

            @Override
            public void evolucionar() {

                System.out.println(getNombre() + " ya alcanzó su forma definitiva.");

            }

        };

        return new Batalla(jugador, jefe);

    }

}
