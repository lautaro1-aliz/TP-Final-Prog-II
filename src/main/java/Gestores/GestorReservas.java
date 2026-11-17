package Gestores;

import Entidades.Habitacion;
import Entidades.Reserva;
import Enums.EstadoHabitacion;
import Enums.EstadoReserva;
import Excepciones.ElementoNoEncontradoException;
import Interfaces.DAO;
import dao.ReservaDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestorReservas extends GestorGenerico<Reserva> {

    private ReservaDAO dao = new ReservaDAO();

    public boolean tieneReservasActivas(int idHabitacion) {
        for (Reserva reserva : listar()) {
            if (reserva.getHabitacion() != null && reserva.getHabitacion().getId() == idHabitacion && reserva.estaActiva()) {
                return true;
            }
        }
        return false;
    }

    public Reserva buscarReserva(int id) {
        return dao.buscarPorId(id);
    }

    public boolean tieneReservaEnPeriodo(Habitacion habitacion, LocalDate fechaInicio, LocalDate fechaFin) {


        if (habitacion == null || fechaInicio == null || fechaFin == null || !fechaInicio.isBefore(fechaFin)) {
            return false;
        }

        for (Reserva reserva : listar()) {

            ///Ver si hay una manera mas legible para el if
            if (reserva.estaActiva() && reserva.getHabitacion().getId() == habitacion.getId() && reserva.seSuperpone(fechaInicio, fechaFin)) {
                return true;
            }
        }

        return false;
    }

    public boolean tieneReservaEnPeriodo(Habitacion habitacion, LocalDate fechaInicio, LocalDate fechaFin, int idReservaExcluir) {

        if (habitacion == null || fechaInicio == null || fechaFin == null || !fechaInicio.isBefore(fechaFin)) {
            return false;
        }

        for (Reserva reserva : listar()) {

            if (reserva.getId() == idReservaExcluir) {
                continue;
            }

            if (!reserva.estaActiva()) {
                continue;
            }

            if (reserva.getHabitacion().getId() == habitacion.getId() && reserva.seSuperpone(fechaInicio, fechaFin)) {
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

        if (reserva == null || reserva.getEstado() != EstadoReserva.PENDIENTE || reserva.getHabitacion() == null || reserva.getHabitacion().getEstado() != EstadoHabitacion.LIBRE) {
            return false;
        }

        if (tieneReservaEnPeriodo(
                reserva.getHabitacion(),
                reserva.getFechaInicio(),
                reserva.getFechaFin(),
                reserva.getId())) {
            return false;
        }

        reserva.setEstado(EstadoReserva.CONFIRMADA);
        return true;
    }

    public boolean cancelarReserva(int idReserva) {
        Reserva reserva = buscarReserva(idReserva);

        if (reserva == null ||
                reserva.getEstado() == EstadoReserva.CANCELADA ||
                reserva.getEstado() == EstadoReserva.FINALIZADA ||
                reserva.getEstado() == EstadoReserva.EN_CURSO) {
            return false;
        }

        reserva.setEstado(EstadoReserva.CANCELADA);
        return true;
    }

    public List<Reserva> listarReservasPorUsuario(String dni) {

        List<Reserva> resultado = new ArrayList<>();

        if (dni == null) {
            return resultado;
        }

        for (Reserva reserva : listar()) {

            if (reserva.getUsuario() != null &&
                    reserva.getUsuario().getDNI() != null &&
                    reserva.getUsuario().getDNI().equalsIgnoreCase(dni)) {

                resultado.add(reserva);
            }
        }

        return resultado;
    }

    public List<Reserva> listarReservasPorHabitacion(int idHabitacion) {

        List<Reserva> resultado = new ArrayList<>();

        for (Reserva reserva : elementos) {

            if (reserva.getHabitacion() != null && reserva.getHabitacion().getId() == idHabitacion) {
                resultado.add(dao.buscarPorIdHabitacion(idHabitacion));
            }
        }
        if(resultado.isEmpty()){
            throw new ElementoNoEncontradoException("No existen reservas para el ID de habitacion: "+idHabitacion);
        }

        return resultado;
    }

    public boolean validarCheckIn(int idReserva) {
        Reserva reserva = buscarReserva(idReserva);

        if (reserva == null) return false;

        if (reserva.getEstado() != EstadoReserva.CONFIRMADA) {
            return false;
        }

        if (reserva.getHabitacion() == null) return false;

        return reserva.getHabitacion().getEstado() == EstadoHabitacion.LIBRE;
    }

    public boolean iniciarEstadia(int idReserva) {
        Reserva reserva = buscarReserva(idReserva);

        if (reserva == null || reserva.getEstado() != EstadoReserva.CONFIRMADA) {
            return false;
        }

        reserva.setEstado(EstadoReserva.EN_CURSO);
        return true;
    }

    public boolean finalizarEstadia(int idReserva) {
        Reserva reserva = buscarReserva(idReserva);

        if (reserva == null || reserva.getEstado() != EstadoReserva.EN_CURSO) {
            return false;
        }

        reserva.setEstado(EstadoReserva.FINALIZADA);
        return true;
    }

    public void guardarBD() throws SQLException {
        int guardadas = 0;
        for (Reserva r : elementos) {
            guardarEnBD(r);
            guardadas++;
        }
        System.out.println("Reservas guardadas: " + guardadas);
    }


    public void guardarEnBD(Reserva r) throws SQLException {
        dao.insertar(r);
    }

}
