package Gestores;

import Entidades.Pasajero;
import Entidades.Usuario;
import Interfaces.DAO;
import dao.UsuarioDAO;

public class GestorPasajeros extends GestorGenerico<Pasajero> {

    final DAO<Usuario> dao = new UsuarioDAO();


    public Pasajero buscarPorDni(String dni) {

        ///Validacion momentanea hasta utilizar excepcion
        if (dni == null) {
            return null;
        }

        for (Pasajero p : elementos) {
            if (p.getDNI().equalsIgnoreCase(dni)) {
                return p;
            }
        }
        return null;
    }


    public Pasajero buscarPorCredenciales(String email, String password) {
        Usuario usuario = UsuarioDAO.buscarPorCredenciales(email, password);

        ///casteo momentaneo para evitar que un error si las credenciales son un empleado por ejemplo
        if (usuario instanceof Pasajero) {
            return (Pasajero) usuario;
        }

        return null;
    }

    public  void guardarBD(){
        int guardadas= 0;
        for(Pasajero p: elementos){
            guardarEnBD(p);
            guardadas++;
        }
        System.out.println("Pasajeros guardados: "+guardadas);
    }

    public void actualizarPass(Usuario u){
        dao.actualizar(u);
    }

    public void guardarEnBD(Pasajero p)  {
        dao.insertar(p);
    }
}
