package Vistas;

import Entidades.Empleado;
import Entidades.Usuario;
import Gestores.GestionHotel;

import java.sql.SQLException;
import java.util.Scanner;


public class MenuPrincipal {

    private static Scanner in;
    private GestionHotel sistema;

    public MenuPrincipal(GestionHotel sistema) {
        this.sistema = sistema;
        in = new Scanner(System.in);
    }

    public void iniciarSesion() throws SQLException {

        Usuario user;
        do {
            System.out.println("===Sistema Hotelero===");
            System.out.println("Ingrese su mail: ");
            String mail = in.nextLine();

            System.out.println("Ingrese su contraseña: ");
            String password = in.nextLine();

            user = sistema.iniciarSesion(mail, password);

            if (user == null) {
                System.out.println("Credenciales incorrectas");
            }

        } while (user == null);

            switch(user.getRol()){
                case ADMINISTRADOR:
                   MenuAdminstrador.mostrar(sistema);
                    break;
                case EMPLEADO:
                    MenuRecepcionista.mostrar(sistema, (Empleado) user);
                    break;
            case PASAJERO:
                  MenuPasajero.mostrar(sistema, user);
                   break;
            }
        }
    }

