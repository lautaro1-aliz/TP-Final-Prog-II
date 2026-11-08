package Vistas;

import Entidades.Usuario;
import Gestores.GestionHotel;
import Vistas.*;
import java.util.Scanner;


public class MenuPrincipal {

    private static Scanner in;
    private GestionHotel sistema;

    public MenuPrincipal(GestionHotel sistema) {
        this.sistema = sistema;
        in = new Scanner(System.in);
    }

    public void iniciarSesion(){
        System.out.println("Ingrese su mail: ");
        String mail = in.nextLine();
        System.out.println("Ingrese su contraseña: ");
        String password = in.nextLine();

        Usuario user = sistema.iniciarSesion(mail,password);

        if(user == null){
            System.out.println("Credenciales incorrectas");

        }else{
            switch(user.getRol()){
                case ADMINISTRADOR:
                    MenuAdminstrador.mostrar(sistema);
                    break;
                case RECEPCIONISTA:
                    MenuRecepcionista.mostrar();
                    break;
                case PASAJERO:
                    MenuPasajero.mostrar();
                    break;
            }
        }
    }
}
