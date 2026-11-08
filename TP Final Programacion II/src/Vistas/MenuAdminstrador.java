package Vistas;

import Entidades.Empleado;
import Entidades.Usuario;
import Gestores.GestionHotel;

import java.time.LocalDate;
import java.util.Scanner;

public class MenuAdminstrador {

    public static void mostrar(GestionHotel sistema){
        System.out.println("Menu administrador");
        Scanner in = new Scanner(System.in);
        int op;
        do {
            System.out.println("===== MENU ADMINISTRADOR =====");
            System.out.println("1. Alta empleado ");
            System.out.println("2. Baja empleado ");
            System.out.println("3. Alta habitacion ");
            System.out.println("4. Baja habitacion ");
            System.out.println("5. Realizar Backup de los datos ");
            System.out.println("6. Listar habitaciones disponibles ");
            System.out.println("7. Actualizar datos "); //esta opcion podria  desglosar un menu nuevo y actualizar datos de empleados o habitaciones
            System.out.println("0. Salir ");
            op = in.nextInt();
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
              //      sistema.altaHabitacion();
                    break;
                case 4:
               //     sistema.eliminarHabitacion();
                    break;
                case 5:
                //    sistema.backup();
                    break;
                case 6:
            //        sistema.listarHabitacionesDisponibles();
                    break;
                case 7:
               //     sistema;
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

    public static Usuario solicitarDatosEmpleado() {

        Scanner in = new Scanner(System.in);
        System.out.println("DNI: ");
        String dni = in.nextLine();

        System.out.print("Nombre: ");
        String nombre = in.nextLine();

        System.out.println("Apellido: ");
        String apellido = in.nextLine();

        System.out.println("Fecha nacimiento: ");
        //TODO ver como formatear la fecha despues
        LocalDate fechaNacimiento = LocalDate.now();

        System.out.println("Telefono: ");
        String telefono = in.nextLine();

        System.out.print("Email: ");
        String mail = in.nextLine();

        System.out.print("Password: ");
        String password = in.nextLine();

        return new Empleado(dni,nombre,apellido,fechaNacimiento,telefono, mail,password);
    }


}
