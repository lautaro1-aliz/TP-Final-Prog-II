package App;

import Gestores.GestionHotel;
import Vistas.MenuPrincipal;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;

public class Main {
    public static void main(String[] args) {
        GestionHotel sistema = new GestionHotel();
        MenuPrincipal menu = new MenuPrincipal(sistema);
        menu.iniciarSesion();
    }
}