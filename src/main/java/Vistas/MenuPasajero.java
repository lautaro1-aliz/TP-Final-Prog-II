package Vistas;

import Entidades.Habitacion;
import Entidades.Usuario;
import Gestores.GestionHotel;
import InputUtils.InputUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
                System.out.println("4. Ver habitaciones disponibles ");
                System.out.println("0. Salir ");
                op = in.nextInt();
                in.nextLine(); //limpio el buffer
                switch (op) {
                    case 1:
                        System.out.println("Ingrese la nueva contraseña: ");

                        String pass =in.nextLine();
                        u.setPassword(pass);
                        System.out.println(u.toString());
                        sistema.cambiarContrasenia(u);
                        break;
                    case 2:
                        System.out.println("Ingrese el id de la habitacion que desea reservar: ");
                        int id_hab = in.nextInt();
                        in.nextLine(); //limpio buffer

                        System.out.print("Ingrese la fecha de inicio (dd/MM/yyyy): ");
                        String fecha = in.nextLine();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate fechaI = LocalDate.parse(fecha, formatter);

                        System.out.print("Ingrese la fecha de fin (dd/MM/yyyy): ");
                        fecha = in.nextLine();
                        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate fechaF = LocalDate.parse(fecha, formatter);

                        sistema.registrarReserva(id_hab, u.getDNI(),fechaI,fechaF);
                        break;
                    case 3:
                    //    sistema.cancelarReserva();
                        break;
                    case 4:

                        int capacidad = InputUtils.leerEntero(in,"Ingrese la capacidad: ");
                        fechaI = InputUtils.leerFecha(in,"Ingrese la fecha de inicio (dd/MM/yyyy): ");
                        fechaF = InputUtils.leerFecha(in,"Ingrese la fecha de fin (dd/MM/yyyy): ");

                        List<Habitacion> aux = sistema.listarHabitacionesDisponibles(fechaI,fechaF,capacidad);
                        for (Habitacion hab : aux) {
                            System.out.println(hab.toString());
                        }


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
