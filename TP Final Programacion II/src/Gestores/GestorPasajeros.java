package Gestores;

import Entidades.Pasajero;
import Entidades.Usuario;
import Interfaces.DAO;
import dao.UsuarioDAO;

import java.util.ArrayList;
import java.util.List;

public class GestorPasajeros extends GestorGenerico<Pasajero> {

    final DAO<Usuario> dao = new UsuarioDAO();


    public Pasajero buscarPorDni(String dni) {
        for (Pasajero p : elementos) {
            if (p.getDNI().equalsIgnoreCase(dni)) {
                return p;
            }
        }
        return null;
    }

    public Pasajero buscarUsuario(String dni) {
        return buscar(dni);
    }

    public Usuario validarCredenciales(String username, String password) {

        return dao.login(username, password);
    }

}
