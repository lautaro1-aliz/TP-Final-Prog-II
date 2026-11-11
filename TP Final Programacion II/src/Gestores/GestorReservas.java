package Gestores;

import Entidades.Habitacion;
import Entidades.Pasajero;
import Entidades.Reserva;
import Entidades.Usuario;
import Enums.EstadoHabitacion;
import Enums.EstadoReserva;
import Interfaces.DAO;
import dao.ReservaDAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestorReservas extends GestorGenerico<Reserva> {

    final DAO<Reserva> dao = new ReservaDAO();
    public boolean tieneReservasActivas(int idHabitacion) {
        for (Reserva reserva : listar()) {
            if (reserva.getHabitacion().getId() == idHabitacion && reserva.estaActiva()) {
                return true;
            }
        }
        return false;
    }

    public Reserva buscarReserva(int id) {
        return buscar(String.valueOf(id));
    }

    public boolean tieneReservaEnPeriodo(Habitacion habitacion, LocalDate fechaInicio, LocalDate fechaFin) {

        for (Reserva reserva : listar()) {

            ///Ver si hay una manera mas legible para el if
            if (reserva.estaActiva() && reserva.getHabitacion().getId() == habitacion.getId() && reserva.seSuperpone(fechaInicio, fechaFin)) {
                return true;
            }
        }

        return false;
    }

    public boolean tieneReservaEnPeriodo(Habitacion habitacion, LocalDate fechaInicio, LocalDate fechaFin, int idReservaExcluir) {

        for (Reserva reserva : listar()) {

            if (reserva.getId() == idReservaExcluir) {
                continue;
            }

            if (reserva.estaActiva() && reserva.getHabitacion().getId() == habitacion.getId() && reserva.seSuperpone(fechaInicio, fechaFin)) {
                return true;
            }
        }

        return false;
    }

    public List<Reserva> listarReservas() {
        return listar();
    }

    public boolean confirmarReserva(int idReserva) {

        Reserva reserva = buscarReserva(idReserva);

        if (reserva == null || reserva.getEstado() != EstadoReserva.PENDIENTE) {
            return false;
        }

        reserva.setEstado(EstadoReserva.CONFIRMADA);
        return true;
    }

    public boolean cancelarReserva(int idReserva) {

        Reserva reserva = buscarReserva(idReserva);

        if (reserva == null || reserva.getEstado() == EstadoReserva.CANCELADA || reserva.getEstado() == EstadoReserva.FINALIZADA) {
            return false;
        }

        reserva.setEstado(EstadoReserva.CANCELADA);
        return true;
    }

    public List<Reserva> listarReservasPorUsuario(String dni) {

        List<Reserva> resultado = new ArrayList<>();

        for (Reserva reserva : listar()) {

            if (reserva.getUsuario().getDNI().equals(dni)) {
                resultado.add(reserva);
            }
        }

        return resultado;
    }

    public List<Reserva> listarReservasPorHabitacion(int idHabitacion) {

        List<Reserva> resultado = new ArrayList<>();

        for (Reserva reserva : listar()) {

            if (reserva.getHabitacion().getId() == idHabitacion) {
                resultado.add(reserva);
            }
        }

        return resultado;
    }

    public  void guardarBD(){
        int guardadas= 0;
        for(Reserva r: elementos){
            guardarEnBD(r);
            guardadas++;
        }
        System.out.println("Habitaciones guardadas: "+guardadas);
    }



    public void guardarEnBD(Reserva r)  {
        dao.insertar(r);
    }

}
