package InputUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputUtils {

    ///clase que sirve para validaciones de datos que ingresa el usuario
    ///Objetivo: no sobrecargar los menus con bloques try-catch repetitivos

    //numeros
    public static int leerEntero(Scanner scanner, String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("ERROR: debe ingresar un número entero.");
            }
        }
    }

    public static double leerDouble(Scanner scanner, String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("ERROR: debe ingresar un número válido.");
            }
        }
    }

    //fechas
    public static LocalDate leerFecha(Scanner scanner, String mensaje) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {
            try {
                System.out.print(mensaje);
                return LocalDate.parse(scanner.nextLine(), formatter);
            } catch (DateTimeParseException e) {
                System.out.println("ERROR: fecha inválida. Formato dd/MM/yyyy.");
            }
        }
    }

    //dni
    public static String leerDni(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String dni = scanner.nextLine().trim();

            if (dni.matches("^\\d{7,8}$")) {
                return dni;
            }

            System.out.println("ERROR: DNI invalido, intente nuevamente");
        }
    }

    //mail
    public static String leerMail(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String mail = scanner.nextLine().trim();

            if (mail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}(\\.[A-Za-z]{2,})*$")) {
                return mail.toLowerCase();
            }

            System.out.println("ERROR: mail invalido, intente nuevamente.");
        }
    }

    public static String leerNombre(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String nombre = scanner.nextLine().trim();
            if (nombre.matches("^[A-Za-z]{2,}$")) {
                return nombre;
            }
            System.out.println("ERROR: nombre invalido, intente nuevamente.");
        }
    }

    public static String leerTelefono(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String telefono = scanner.nextLine().trim();
            if (telefono.matches("^[0-9]{10,}$")) {
                return telefono;
            }
            System.out.println("ERROR: telefono invalido, intente nuevamente.");
        }
    }
}
