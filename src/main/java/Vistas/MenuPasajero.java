package Vistas;

import Entidades.Usuario;
import Gestores.GestionHotel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MenuPasajero {

    public static void mostrar(GestionHotel sistema, Usuario u){
        System.out.println("Menu pasajero");
        //TODO poner un swtich con las opciones que puede realizar el pasajero
        // junto a las llamadas a los metodo
            Scanner in = new Scanner(System.in);

            int op;
            do {
                System.out.println("===== MENU PASAJERO =====");
                System.out.println("1. Cambiar contraseña ");
                System.out.println("2. Reservar habitacion ");
                System.out.println("3. Cancelar reserva ");
                System.out.println("4. Baja habitacion ");
                System.out.println("5. Realizar Backup de los datos ");
                System.out.println("0. Salir ");
                op = in.nextInt();
                switch (op) {
                    case 1:
                        System.out.println("Ingrese la nueva contraseña: ");
                        String pass =in.nextLine();
                        u.setPassword(pass);
                        sistema.cambiarContrasenia(u);
                        break;
                    case 2:
                        break;

                    case 0:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opcion invalida");

                }
            }while (op !=0);
            in.close();
        }
    }
