package Vistas;

import Entidades.Empleado;
import Entidades.Habitacion;
import Entidades.Usuario;
import Enums.EstadoHabitacion;
import Gestores.GestionHotel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MenuAdminstrador {

    public static void mostrar(GestionHotel sistema) throws SQLException {
        Scanner in = new Scanner(System.in);

        int op;
        do {
            System.out.println("===== MENU ADMINISTRADOR =====");
            System.out.println("1. Alta empleado ");
            System.out.println("2. Baja empleado ");
            System.out.println("3. Alta habitacion ");
            System.out.println("4. Baja habitacion ");
            System.out.println("5. Realizar Backup de los datos ");
            System.out.println("6. Listar habitaciones disponibles por fecha ");
            System.out.println("7. Actualizar credenciales empleado "); //esta opcion podria  desglosar un menu nuevo y actualizar datos de empleados o habitaciones
            System.out.println("8. Ver maestro de empleados ");
            System.out.println("0. Salir ");
            op = in.nextInt();
            in.nextLine(); //limpio buffer
            switch (op) {
                case 1:
                    sistema.agregarUsuario(solicitarDatosEmpleado());
                    break;
                case 2:
                    System.out.println("Ingrese el nro de legajo del empleado: ");
                    int legajo = in.nextInt();
                    sistema.eliminarUsuario(legajo);
                    break;
                case 3:
                    sistema.altaHabitacion(solicitarDatosHabitacion(sistema));
                    break;
                case 4:
                    System.out.println("Ingrese el ID de la habitacion a eliminar: ");
                    int id = in.nextInt();
                    sistema.eliminarHabitacion(id);
                    break;
                case 5:
                    sistema.backup();
                    break;
                case 6:
                    System.out.print("Ingrese la fecha de inicio (dd/MM/yyyy): ");
                    String fechaTexto = in.nextLine();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate fechaI = LocalDate.parse(fechaTexto, formatter);

                    System.out.print("Ingrese la fecha de fin (dd/MM/yyyy): ");
                    fechaTexto = in.nextLine();
                    formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate fechaF = LocalDate.parse(fechaTexto, formatter);

                    System.out.println("Ingrese la capacidad maxima de la habitacion: ");
                    int cantidad = in.nextInt();
                    //buscamos las habitaciones disponibles, las guardamos en un arreglo temporal
                    List<Habitacion>habitacionesDisponibles = sistema.listarHabitacionesDisponibles(fechaI,fechaF,cantidad);

                    //recorremos el arreglo y mostramos
                    for(Habitacion h : habitacionesDisponibles){
                        System.out.println(h.toString());
                    }
                    break;
                case 7:
                    sistema.actualizarDatosEmpleado(solicitarDatosEmpleado());
                    break;
                case 8:
                    sistema.listarEmpleados();
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

    public static Empleado solicitarDatosEmpleado() {

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

        return new Empleado(dni,nombre,apellido,telefono, mail,password);
    }

    public static Habitacion solicitarDatosHabitacion(GestionHotel sistema){
        Scanner in = new Scanner(System.in);

        //todo metodo que devuelva el ultimo id
        int id = 0;

        System.out.println("Piso: ");
        int piso = in.nextInt();

        System.out.print("Capacidad: ");
        int capacidad = in.nextInt();

        System.out.println("Precio por noche: ");
        double precio = in.nextDouble();

        System.out.println("Tipo: ");
        String tipo = in.nextLine();

        String motivo = null;

        return new Habitacion(id,piso,capacidad,precio,tipo,motivo,EstadoHabitacion.LIBRE);
    }
}

