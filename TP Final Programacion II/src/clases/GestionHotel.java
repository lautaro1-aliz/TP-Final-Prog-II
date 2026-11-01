package clases;

import enums.EstadoHabitacion;
import enums.EstadoReserva;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestionHotel {
    private GestorGenerico<Habitacion> habitaciones;
    private GestorGenerico<Usuario> usuarios;
    private GestorGenerico<Reserva> reservas;

    public GestionHotel() {
        usuarios = new GestorGenerico<>();
        habitaciones = new GestorGenerico<>();
        reservas = new GestorGenerico<>();
    }

    // GESTIÓN DE HABITACIONES

    public boolean agregarHabitacion(Habitacion habitacion) {
        return habitaciones.agregar(habitacion);
    }

    public Habitacion buscarHabitacion(int numero) {
        return habitaciones.buscar(String.valueOf(numero));
    }

    public boolean tieneReservasActivas(int idHabitacion) {
        for (Reserva reserva : reservas.listar()) {
            if (reserva.getHabitacion().getId() == idHabitacion && reserva.estaActiva()) {
                return true;
            }
        }
        return false;
    }

    public boolean eliminarHabitacion(int id) {

        Habitacion habitacion = buscarHabitacion(id);

        if (habitacion == null || tieneReservasActivas(id)) {
            return false;
        }

        return habitaciones.eliminar(habitacion);
    }


    public List<Habitacion> listarHabitaciones() {
        return habitaciones.listar();
    }


    public List<Habitacion> listarHabitacionesPorEstado(EstadoHabitacion estado) {
        List<Habitacion> resultado = new ArrayList<>();

        for (Habitacion habitacion : habitaciones.listar()) {
            if (habitacion.getEstado() == estado) {
                resultado.add(habitacion);
            }
        }

        return resultado;
    }

    public boolean modificarPrecioHabitacion(int id, double nuevoPrecio) {
        Habitacion habitacion = buscarHabitacion(id);

        if (habitacion == null || nuevoPrecio <= 0) {
            return false;
        }

        habitacion.setPrecioXnoche(nuevoPrecio);
        return true;
    }

    public boolean cambiarEstadoHabitacion(int id, EstadoHabitacion estado) {
        Habitacion habitacion = buscarHabitacion(id);

        if (habitacion == null || estado == null) {
            return false;
        }

        habitacion.setEstado(estado);
        return true;
    }

    public boolean checkIn(int idHabitacion) {
        Habitacion habitacion = buscarHabitacion(idHabitacion);

        if (habitacion == null || habitacion.getEstado() != EstadoHabitacion.LIBRE) {
            return false;
        }

        habitacion.setEstado(EstadoHabitacion.OCUPADA);
        return true;
    }

    public boolean checkOut(int idHabitacion) {
        Habitacion habitacion = buscarHabitacion(idHabitacion);

        if (habitacion == null || habitacion.getEstado() != EstadoHabitacion.OCUPADA) {
            return false;
        }

        habitacion.setEstado(EstadoHabitacion.LIBRE);
        return true;
    }

    /// Metodo pensado para listar habitaciones disponibles, con la capacidad solicitadad y a su vez que no se superponga a otra reserva

    public List<Habitacion> listarHabitacionesDisponibles(
            LocalDate fechaInicio, LocalDate fechaFin, int capacidad) {

        List<Habitacion> disponibles = new ArrayList<>();

        ///TODO Excepcion para implementar mas adelante
        if (fechaInicio == null || fechaFin == null || !fechaInicio.isBefore(fechaFin) || capacidad <= 0) {
            return disponibles;
        }

        for (Habitacion habitacion : habitaciones.listar()) {

            // Comprobamos que esté libre
            if (habitacion.getEstado() != EstadoHabitacion.LIBRE) {
                continue;
            }

            // Comprobamos que tenga capacidad suficiente
            if (habitacion.getCapacidad() < capacidad) {
                continue;
            }

            // Comprobamos que no tenga reservas superpuestas
            if (tieneReservaEnPeriodo(habitacion, fechaInicio, fechaFin)) {
                continue;
            }

            disponibles.add(habitacion);
        }

        return disponibles;
    }


    public Usuario buscarUsuario(String dni) {
        return usuarios.buscar(dni);
    }

    // GESTIÓN DE RESERVAS

    public Reserva buscarReserva(int id) {
        return reservas.buscar(String.valueOf(id));
    }

    public boolean tieneReservaEnPeriodo(Habitacion habitacion, LocalDate fechaInicio, LocalDate fechaFin) {

        for (Reserva reserva : reservas.listar()) {

            ///Ver si hay una manera mas legible para el if
            if (reserva.estaActiva() && reserva.getHabitacion().getId() == habitacion.getId() && reserva.seSuperpone(fechaInicio, fechaFin)) {
                return true;
            }
        }

        return false;
    }

    public boolean tieneReservaEnPeriodo(Habitacion habitacion, LocalDate fechaInicio, LocalDate fechaFin, int idReservaExcluir) {

        for (Reserva reserva : reservas.listar()) {

            if (reserva.getId() == idReservaExcluir) {
                continue;
            }

            if (reserva.estaActiva() && reserva.getHabitacion().getId() == habitacion.getId() && reserva.seSuperpone(fechaInicio, fechaFin)) {
                return true;
            }
        }

        return false;
    }

    public boolean registrarReserva(int idHabitacion, String dniUsuario, LocalDate fechaInicio, LocalDate fechaFin) {

        Habitacion habitacion = buscarHabitacion(idHabitacion);
        Usuario usuario = buscarUsuario(dniUsuario);

        if (habitacion == null || usuario == null) {
            return false;
        }

        if (fechaInicio == null || fechaFin == null || !fechaInicio.isBefore(fechaFin)) {
            return false;
        }

        if (habitacion.getEstado() != EstadoHabitacion.LIBRE) {
            return false;
        }

        if (tieneReservaEnPeriodo(habitacion, fechaInicio, fechaFin)) {
            return false;
        }

        Reserva reserva = new Reserva(fechaInicio, fechaFin, habitacion, usuario);

        return reservas.agregar(reserva);
    }

    public List<Reserva> listarReservas() {
        return reservas.listar();
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

        for (Reserva reserva : reservas.listar()) {

            if (reserva.getUsuario().getDNI().equals(dni)) {
                resultado.add(reserva);
            }
        }

        return resultado;
    }

    public List<Reserva> listarReservasPorHabitacion(int idHabitacion) {

        List<Reserva> resultado = new ArrayList<>();

        for (Reserva reserva : reservas.listar()) {

            if (reserva.getHabitacion().getId() == idHabitacion) {
                resultado.add(reserva);
            }
        }

        return resultado;
    }

    public boolean modificarReserva(int idReserva, int idNuevaHabitacion, LocalDate nuevaFechaInicio, LocalDate nuevaFechaFin) {

        // Buscamos la reserva original
        Reserva reserva = buscarReserva(idReserva);

        if (reserva == null || !reserva.estaActiva()) {
            return false;
        }

        // Validamos las fechas
        if (nuevaFechaInicio == null || nuevaFechaFin == null || !nuevaFechaInicio.isBefore(nuevaFechaFin)) {
            return false;
        }

        // Buscamos la nueva habitación
        Habitacion nuevaHabitacion = buscarHabitacion(idNuevaHabitacion);

        if (nuevaHabitacion == null || nuevaHabitacion.getEstado() != EstadoHabitacion.LIBRE) {
            return false;
        }

        // Comprobamos que no existan reservas superpuestas
        if (tieneReservaEnPeriodo(nuevaHabitacion, nuevaFechaInicio, nuevaFechaFin, idReserva)) {
            return false;
        }

        // Actualizamos los datos
        reserva.setHabitacion(nuevaHabitacion);
        reserva.setFechaInicio(nuevaFechaInicio);
        reserva.setFechaFin(nuevaFechaFin);

        return true;
    }
}
