package Vistas;

import Entidades.Empleado;
import Entidades.Habitacion;
import Entidades.Pasajero;
import Gestores.GestionHotel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MenuRecepcionista {


    public static void mostrar(GestionHotel sistema, Empleado empleado) throws SQLException{
            Scanner in = new Scanner(System.in);

            int op;
            do {
                System.out.println("===== MENU EMPLEADO =====");
                System.out.println("1. Alta pasajero ");
                System.out.println("2. Realizar check-in ");
                System.out.println("3. Realizar check-out ");
                System.out.println("4. Modificar reserva ");
                System.out.println("5. Listar habitaciones disponibles por fecha ");
                System.out.println("0. Salir ");
                op = in.nextInt();
                in.nextLine(); //limpio buffer
                switch (op) {
                    case 1:
                        sistema.agregarUsuario(solicitarDatosPasajero());
                        break;
                    case 2:
                        System.out.println("Ingrese el nro de reserva: ");
                        int id = in.nextInt();
                        sistema.realizarCheckIn(empleado, id);
                        break;
                    case 3:
                        System.out.println("Ingrese el nro de habitacion: ");
                        int nro = in.nextInt();
                        sistema.realizarCheckOut(empleado, nro);
                        break;
                    case 4:
                        System.out.println("Ingrese el nro de la reserva: ");
                        id = in.nextInt();
                        System.out.println("Ingrese el nro de la nueva habitacion: ");
                        nro = in.nextInt();
                        System.out.print("Ingrese la fecha de inicio (dd/MM/yyyy): ");
                        String fechaTexto = in.nextLine();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate fechaI = LocalDate.parse(fechaTexto, formatter);

                        System.out.print("Ingrese la fecha de fin (dd/MM/yyyy): ");
                        fechaTexto = in.nextLine();
                        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate fechaF = LocalDate.parse(fechaTexto, formatter);
                        sistema.modificarReserva(id,nro,fechaI,fechaF);

                        break;
                    case 5:
                        System.out.print("Ingrese la fecha de inicio (dd/MM/yyyy): ");
                        fechaTexto = in.nextLine();
                        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        fechaI = LocalDate.parse(fechaTexto, formatter);

                        System.out.print("Ingrese la fecha de fin (dd/MM/yyyy): ");
                        fechaTexto = in.nextLine();
                        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        fechaF = LocalDate.parse(fechaTexto, formatter);

                        System.out.println("Ingrese la capacidad maxima de la habitacion: ");
                        int cantidad = in.nextInt();
                        //buscamos las habitaciones disponibles, las guardamos en un arreglo temporal
                        List<Habitacion> habitacionesDisponibles = sistema.listarHabitacionesDisponibles(fechaI,fechaF,cantidad);

                        //recorremos el arreglo y mostramos
                        for(Habitacion h : habitacionesDisponibles){
                            System.out.println(h.toString());
                        }
                        break;
                    case 7:

                        break;
                    case 8:

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

    public static Pasajero solicitarDatosPasajero() {

        Scanner in = new Scanner(System.in);
        System.out.println("DNI: ");
        String dni = in.nextLine();

        System.out.print("Nombre: ");
        String nombre = in.nextLine();

        System.out.println("Apellido: ");
        String apellido = in.nextLine();

        System.out.println("Telefono: ");
        String telefono = in.nextLine();

        System.out.print("Email: ");
        String mail = in.nextLine();

        System.out.print("Password: ");
        String password = in.nextLine();

        System.out.println("Nacionalidad: ");
        String nacionalidad = in.nextLine();

        System.out.println("Direccion: ");
        String direccion = in.nextLine();

        return new Pasajero(dni,nombre,apellido,telefono, mail,password,nacionalidad,direccion);
    }
    }

