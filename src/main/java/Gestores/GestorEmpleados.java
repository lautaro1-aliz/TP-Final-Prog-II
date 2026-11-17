package Gestores;

import Entidades.Empleado;
import Entidades.Usuario;
import Enums.Rol;
import dao.UsuarioDAO;
import Entidades.Reserva;
import Enums.EstadoReserva;

import java.util.ArrayList;
import java.util.List;

public class GestorEmpleados extends GestorGenerico<Empleado> {

    private final UsuarioDAO dao  = new UsuarioDAO();

    public boolean realizarCheckIn(GestorReservas reservas, GestorHabitaciones habitaciones, int idReserva) {

        if (reservas == null || habitaciones == null) return false;

        if (!reservas.validarCheckIn(idReserva)) return false;

        Reserva reserva = reservas.buscarReserva(idReserva);

        if (!habitaciones.checkIn(reserva.getId_hab())) {
            return false;
        }

        return reservas.iniciarEstadia(idReserva);
    }

    public boolean realizarCheckOut(GestorReservas reservas,
                                    GestorHabitaciones habitaciones,
                                    int idHabitacion) {

        if (reservas == null || habitaciones == null) return false;

        if (habitaciones.buscarHabitacion(idHabitacion) == null) return false;

        Reserva reservaEnCurso = null;

        for (Reserva r : reservas.listarReservasPorHabitacion(idHabitacion)) {
            if (r.getEstado() == EstadoReserva.EN_CURSO) {
                reservaEnCurso = r;
                break;
            }
        }

        if (reservaEnCurso == null) return false;

        if (!habitaciones.checkOut(idHabitacion)) return false;

        return reservas.finalizarEstadia(reservaEnCurso.getId());
    }


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
        System.out.println("Empleados guardados: " + guardadas);
    }

    public void guardarEnBD(Empleado e)  {
        dao.insertar(e);
    }

    public List<Empleado> mostrarEmpleados() {
        List<Empleado> empleados = new ArrayList<>();

        for (Usuario usuario : dao.listarPorRol(Rol.EMPLEADO)) {
            if (usuario instanceof Empleado) {
                empleados.add((Empleado) usuario);
            }
        }

        return empleados;
    }
    public void actualizarDatos(Empleado e){

        dao.actualizar(e);
    }
}
