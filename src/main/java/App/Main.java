package App;

import Gestores.GestionHotel;
import Vistas.MenuPrincipal;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        GestionHotel sistema = new GestionHotel();
        MenuPrincipal menu = new MenuPrincipal(sistema);
        menu.iniciarSesion();
    }
}