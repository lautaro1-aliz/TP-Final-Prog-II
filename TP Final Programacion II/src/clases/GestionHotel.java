package clases;

import enums.EstadoHabitacion;

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

    public boolean eliminarHabitacion(int id) {
        for (int i = 0; i < habitaciones.cantidad(); i++) {
            Habitacion habitacion = habitaciones.buscarPorIndice(i);

            if (habitacion != null && habitacion.getId() == id) {
                habitaciones.eliminar(i);
                return true;
            }
        }
        return false;
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

    public boolean tieneReservaEnPeriodo(Habitacion habitacion, LocalDate fechaInicio, LocalDate fechaFin) {
        for (Reserva reserva : reservas.listar()) {

            if (reserva.getHabitacion().getId() == habitacion.getId() && reserva.seSuperpone(fechaInicio, fechaFin)) {
                return true;
            }
        }

        return false;
    }

    ///Metodo pensado para listar habitaciones disponibles, con la capacidad solicitadad y a su vez que no se superponga a otra reserva

    public List<Habitacion> listarHabitacionesDisponibles(
            LocalDate fechaInicio, LocalDate fechaFin, int capacidad) {

        List<Habitacion> disponibles = new ArrayList<>();

        // Obtenemos solamente las habitaciones libres
        List<Habitacion> habitacionesLibres =
                listarHabitacionesPorEstado(EstadoHabitacion.LIBRE);

        for (Habitacion habitacion : habitacionesLibres) {

            // Comprobamos que tenga capacidad suficiente
            if (habitacion.getCapacidad() < capacidad) {
                continue;
            }

            // Comprobamos que no tenga reservas en ese período
            if (tieneReservaEnPeriodo(habitacion, fechaInicio, fechaFin)) {
                continue;
            }

            // Si supera ambos filtros, la agregamos
            disponibles.add(habitacion);
        }

        return disponibles;
    }




    public Usuario buscarUsuario(String dni) {
        return usuarios.buscar(dni);
    }


    public Reserva buscarReserva(int id) {
        return reservas.buscar(String.valueOf(id));
    }

}
