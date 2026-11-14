package Gestores;

import Entidades.Empleado;
import Entidades.Usuario;
import Enums.Rol;
import Interfaces.DAO;
import dao.UsuarioDAO;

import java.util.List;

public class GestorEmpleados extends GestorGenerico<Empleado> {

    private UsuarioDAO dao  = new UsuarioDAO();

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

    public void guardarEnBD(Empleado e)  {
        dao.insertar(e);
    }

    public List<Usuario> mostrarEmpleados(){
        return dao.listarPorRol(Rol.EMPLEADO);
    }

    public void actualizarDatos(Empleado e){

        dao.actualizar(e);
    }
}
