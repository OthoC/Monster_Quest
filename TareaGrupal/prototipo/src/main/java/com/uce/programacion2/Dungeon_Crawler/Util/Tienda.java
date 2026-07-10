package com.uce.programacion2.Dungeon_Crawler.Util;

// Tienda de consola (con Scanner) reemplazada por PanelTienda en la
// interfaz gráfica. No tiene referencias en el resto del proyecto; se
// deja comentada en vez de borrarla.
//
// import com.uce.programacion2.Dungeon_Crawler.Objetos.Esfera;
// import com.uce.programacion2.Dungeon_Crawler.Objetos.Pocion;
// import com.uce.programacion2.Dungeon_Crawler.Personajes.Jugador;
//
// import java.util.Scanner;
//
// public class Tienda {
//
//     private Scanner teclado;
//
//     {
//         teclado = new Scanner(System.in);
//     }
//
//     public void abrir(Jugador jugador) {
//
//         int opcion;
//
//         do {
//
//             System.out.println("\n===== TIENDA =====");
//             System.out.println("Dinero: $" + jugador.getDinero());
//
//             System.out.println("1. Comprar Poción ($20)");
//             System.out.println("2. Comprar Esfera ($30)");
//             System.out.println("3. Salir");
//
//             opcion = teclado.nextInt();
//
//             switch (opcion) {
//
//                 case 1:
//
//                     if (jugador.gastarDinero(20)) {
//
//                         jugador.getInventario().agregarObjeto(new Pocion());
//
//                         System.out.println("Compraste una poción.");
//
//                     } else {
//
//                         System.out.println("Dinero insuficiente.");
//
//                     }
//
//                     break;
//
//                 case 2:
//
//                     if (jugador.gastarDinero(30)) {
//
//                         jugador.getInventario().agregarObjeto(new Esfera());
//
//                         System.out.println("Compraste una esfera.");
//
//                     } else {
//
//                         System.out.println("Dinero insuficiente.");
//
//                     }
//
//                     break;
//
//             }
//
//         } while (opcion != 3);
//
//     }
//
// }
