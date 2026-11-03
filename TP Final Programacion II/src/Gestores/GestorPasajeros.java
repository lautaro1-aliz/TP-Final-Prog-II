package Gestores;

import Entidades.Pasajero;
import Entidades.Usuario;

import java.util.ArrayList;
import java.util.List;

public class GestorPasajeros extends GestorGenerico<Pasajero>{


    public Pasajero buscarPorDni(String dni) {
        for (Pasajero p : elementos) {
            if (p.getDNI().equals(dni)) {
                return p;
            }
        }
        return null;
    }

    public Pasajero buscarUsuario(String dni) {
        return buscar(dni);
    }

}
