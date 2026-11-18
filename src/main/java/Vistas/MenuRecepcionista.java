package Vistas;

import Entidades.Empleado;
import Entidades.Habitacion;
import Entidades.Pasajero;
import Excepciones.ElementoNoEncontradoException;
import Gestores.GestionHotel;
import InputUtils.InputUtils;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MenuRecepcionista {


        public static void mostrar(GestionHotel sistema, Empleado user) throws SQLException {
            Scanner in = new Scanner(System.in);

            int op;
            do {
                System.out.println("===== MENU RECEPCIONISTA =====");
                System.out.println("1. Alta pasajero ");
                System.out.println("2. Realizar check-in ");
                System.out.println("3. Realizar check-out ");
                System.out.println("4. Modificar reserva ");
                System.out.println("5. Ver reservas por habitacion ");
                System.out.println("6. Listar habitaciones disponibles por fecha ");
                System.out.println("7. Generar informe de pasajeros ");
                System.out.println("8. Marcar habitacion no disponible ");
                System.out.println("9. Actualizar precio habitacion ");
                System.out.println("0. Salir ");
                op = in.nextInt();
                in.nextLine(); //limpio buffer
                switch (op) {
                    case 1:
                        sistema.agregarUsuario(solicitarDatosPasajero());
                        break;
                    case 2:
                        int id = InputUtils.leerEntero(in,"Ingrese el nro de reserva: ");
                        sistema.realizarCheckIn(user, id);
                        break;
                    case 3:
                        int nro = InputUtils.leerEntero(in,"Ingrese el nro de habitacion: ");
                        sistema.realizarCheckOut(user, nro);
                        break;
                    case 4:

                        id = InputUtils.leerEntero(in,"Ingrese el nro de la reserva: ");
                        nro = InputUtils.leerEntero(in,"Ingrese el nro de la nueva habitacion: ");
                        LocalDate fechaI = InputUtils.leerFecha(in,"Ingrese la fecha de inicio (dd/MM/yyyy): ");
                        LocalDate fechaF = InputUtils.leerFecha(in,"Ingrese la fecha de fin (dd/MM/yyyy): ");

                        //modificamos la reserva
                        sistema.modificarReserva(id,nro,fechaI,fechaF);
                        break;
                    case 5:
                        try{
                        id = InputUtils.leerEntero(in,"Ingrese el nro de la habitacion: ");
                        sistema.listarReservasPorHabitacion(id);
                        }catch (ElementoNoEncontradoException e){
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 6:
                        fechaI = InputUtils.leerFecha(in, "Ingrese la fecha de inicio: dd/MM/yyyy: ");
                        fechaF = InputUtils.leerFecha(in,"Ingrese la fecha de fin:  dd/MM/yyyy: ");
                        int cantidad = InputUtils.leerEntero(in, "Ingrese la cantidad de personas permitida: ");

                        //buscamos las habitaciones disponibles, las guardamos en un arreglo temporal
                        List<Habitacion> habitacionesDisponibles = sistema.listarHabitacionesDisponibles(fechaI,fechaF,cantidad);

                        //recorremos el arreglo y mostramos
                        for(Habitacion h : habitacionesDisponibles){
                            System.out.println(h.toString());
                        }
                        break;
                    case 7:
                        sistema.generarInformePasajeros();
                        break;
                    case 8:
                        id = InputUtils.leerEntero(in,"Ingrese el ID de la habitacion: ");
                        String motivo = in.nextLine();
                        sistema.marcarHabitacionNoDisponible(id,motivo);
                        break;
                    case 9:
                        try{
                        double precio = InputUtils.leerDouble(in,"Ingrese el nuevo precio de la habitacion (por noche): ");
                        id = InputUtils.leerEntero(in,"Ingrese el ID de la habitacion a actualizar: ");
                        sistema.actualizarPrecioHabitacion(precio,id);
                        }catch (ElementoNoEncontradoException e){
                            System.out.println(e.getMessage());
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

