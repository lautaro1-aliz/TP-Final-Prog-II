package Gestores;

import Entidades.Pasajero;
import Entidades.Usuario;
import Interfaces.DAO;
import dao.UsuarioDAO;

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

    public Pasajero buscarPorCredenciales(String email,String password){

        return (Pasajero) UsuarioDAO.buscarPorCredenciales(email,password);
    }

    public  void guardarBD(){
        int guardadas= 0;
        for(Pasajero p: elementos){
            guardarEnBD(p);
            guardadas++;
        }
        System.out.println("Habitaciones guardadas: "+guardadas);
    }



    public void guardarEnBD(Pasajero p)  {
        dao.insertar(p);
    }
}
