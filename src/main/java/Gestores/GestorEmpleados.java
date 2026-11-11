package Gestores;

import Entidades.Empleado;
import Entidades.Usuario;
import Interfaces.DAO;
import dao.UsuarioDAO;

public class GestorEmpleados extends GestorGenerico<Empleado> {

    final DAO<Usuario> dao = new UsuarioDAO();
    public void realizarCheckIn(){}

    public void realizarCheckOut(){}

    public void administrarHabitaciones(){}


    public Empleado buscarPorLegajo(int legajo) {

        for (Empleado e : elementos) {

            if (e.getLegajo() == legajo) {
                return e;
            }

        }

        return null;
    }

    public  void guardarBD(){
        int guardadas= 0;
        for(Empleado e: elementos){
            guardarEnBD(e);
            guardadas++;
        }
        System.out.println("Habitaciones guardadas: "+guardadas);

    }

    public Empleado buscarPorCredenciales(String email,String password){

        return (Empleado) UsuarioDAO.buscarPorCredenciales(email,password);
    }


    public void guardarEnBD(Empleado e)  {
        dao.insertar(e);
    }
}
