package Vistas;

import Entidades.Empleado;
import Entidades.Habitacion;
import Entidades.Usuario;
import Enums.EstadoHabitacion;
import Gestores.GestionHotel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import InputUtils.*;

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
            System.out.println("8. Listar empleados ");

            System.out.println("0. Salir ");
            op = in.nextInt();
            in.nextLine(); //limpio buffer
            switch (op) {
                case 1:
                    sistema.agregarUsuario(solicitarDatosEmpleado());
                    break;
                case 2:
                    int legajo = InputUtils.leerEntero(in,"Ingrese el nro de legajo del empleado: ");
                    sistema.eliminarUsuario(legajo);
                    break;
                case 3:
                    //todo no inserta la habitacion
                    sistema.altaHabitacion(solicitarDatosHabitacion(sistema));
                    break;
                case 4:
                    int id = InputUtils.leerEntero(in,"Ingrese el ID de la habitacion a eliminar: ");
                    sistema.eliminarHabitacion(id);
                    break;
                case 5:
                    sistema.backup();
                    break;
                case 6:
                    LocalDate fechaI = InputUtils.leerFecha(in, "Ingrese la fecha de inicio: dd/MM/yyyy");
                    LocalDate fechaF = InputUtils.leerFecha(in,"Ingrese la fecha de fin:  dd/MM/yyyy");
                    int cantidad = InputUtils.leerEntero(in, "Ingrese la cantidad de personas permitida: ");
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

        String dni = InputUtils.leerDni(in,"DNI: ");

        String nombre = InputUtils.leerNombre(in,"Nombre: ");

        String apellido = InputUtils.leerNombre(in,"Apellido: ");

        String telefono = InputUtils.leerTelefono(in, "Telefono: ");

        String mail = InputUtils.leerMail(in,"Email: ");

        System.out.print("Password: ");
        String password = in.nextLine();

        return new Empleado(dni,nombre,apellido,telefono, mail,password);
    }

    public static Habitacion solicitarDatosHabitacion(GestionHotel sistema){
        Scanner in = new Scanner(System.in);


        int piso = InputUtils.leerEntero(in,"Piso: ");

        int capacidad = InputUtils.leerEntero(in,"Capacidad: ");

        double precio = InputUtils.leerDouble(in,"Precio por noche: ");

        System.out.println("Tipo: ");
        String tipo = in.nextLine();

        String motivo = null;

        return new Habitacion(piso,capacidad,precio,tipo,motivo,EstadoHabitacion.LIBRE);
    }
}

