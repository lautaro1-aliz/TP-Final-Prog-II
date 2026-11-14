package Gestores;

import Entidades.*;
import Enums.EstadoHabitacion;
import dao.UsuarioDAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestionHotel {

    //el gestor hotel o sistema hotelero deberia gestionar pasajeros, reservas y habitaciones
    //para que el usuario no interactue directamente con los datos
    //los gestores que estan dentro de esta clase heredan de Gestor<T> y por ende los metodos CRUD
    //a parte cada uno deberia implementar metodos propios segun la clase
    //dentro de esta clase deberian ir metodos que necesiten llamar a dos o mas gestores
    private GestorPasajeros pasajeros;
    private GestorReservas reservas;
    private GestorHabitaciones habitaciones;
    private GestorEmpleados empleados;

    public GestionHotel() {
        pasajeros = new GestorPasajeros();
        habitaciones = new GestorHabitaciones();
        reservas = new GestorReservas();
        empleados = new GestorEmpleados();
    }
    ///llamada para inicio de sesion, devuelve un usuario
    public Usuario iniciarSesion(String username,String password) {
        return UsuarioDAO.buscarPorCredenciales(username, password);
    }

    /// Metodos correspondientes al menu Administrador

    public void agregarUsuario(Usuario u){
        UsuarioDAO uDAO = new UsuarioDAO();
        // inserta en la base de datos
        uDAO.insertar(u);

        //guarda en memoria

        if(u instanceof Pasajero){
            Pasajero p = (Pasajero) u;
            pasajeros.agregar(p);
        }else if(u instanceof Empleado){
            Empleado e = (Empleado) u;
            empleados.agregar(e);
        }
    }

    public void listarEmpleados(){
        //devuelve una lista de los empleados
        List<Usuario> resultado = empleados.mostrarEmpleados();
        for(Usuario u: resultado){
            System.out.println(u.toString());
        }
    }

    public void eliminarUsuario(int legajo) {

        Empleado e = empleados.buscarPorLegajo(legajo);
        if (e != null) {
            empleados.eliminar(e);
        }
    }

    public void actualizarDatosEmpleado(Empleado e){
        empleados.actualizarDatos(e);

    }



    public boolean altaHabitacion(Habitacion h){
        return habitaciones.agregarHabitacion(h);
    }

    public void backup(){
        habitaciones.guardarBD();
        pasajeros.guardarBD();
        reservas.guardarBD();
        empleados.guardarBD();
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
            if (reservas.tieneReservaEnPeriodo(habitacion, fechaInicio, fechaFin)) {
                continue;
            }

            disponibles.add(habitacion);
        }

        return disponibles;
    }


    public boolean eliminarHabitacion(int id) {

        Habitacion habitacion =habitaciones.buscarHabitacion(id);
        GestorReservas reservas = new GestorReservas();
        if (habitacion == null || reservas.tieneReservasActivas(id)) {
            return false;
        }

        return habitaciones.eliminar(habitacion);
    }

    public boolean registrarReserva(int idHabitacion, String dniUsuario, LocalDate fechaInicio, LocalDate fechaFin) {


        Habitacion habitacion = habitaciones.buscarHabitacion(idHabitacion);
        Pasajero pasajero  =  pasajeros.buscarUsuario(dniUsuario);

        if (habitacion == null || pasajero == null) {
            return false;
        }

        if (fechaInicio == null || fechaFin == null || !fechaInicio.isBefore(fechaFin)) {
            return false;
        }

        if (habitacion.getEstado() != EstadoHabitacion.LIBRE) {
            return false;
        }

        if (reservas.tieneReservaEnPeriodo(habitacion, fechaInicio, fechaFin)) {
            return false;
        }

        Reserva reserva = new Reserva(fechaInicio, fechaFin, habitacion, pasajero);

        return reservas.agregar(reserva);
    }

    public boolean modificarReserva(int idReserva, int idNuevaHabitacion, LocalDate nuevaFechaInicio, LocalDate nuevaFechaFin) {

        // Buscamos la reserva original
        Reserva reserva = reservas.buscarReserva(idReserva);

        if (reserva == null || !reserva.estaActiva()) {
            return false;
        }

        // Validamos las fechas
        if (nuevaFechaInicio == null || nuevaFechaFin == null || !nuevaFechaInicio.isBefore(nuevaFechaFin)) {
            return false;
        }

        // Buscamos la nueva habitación
        Habitacion nuevaHabitacion = habitaciones.buscarHabitacion(idNuevaHabitacion);

        if (nuevaHabitacion == null || nuevaHabitacion.getEstado() != EstadoHabitacion.LIBRE) {
            return false;
        }

        // Comprobamos que no existan reservas superpuestas
        if (reservas.tieneReservaEnPeriodo(nuevaHabitacion, nuevaFechaInicio, nuevaFechaFin, idReserva)) {
            return false;
        }

        // Actualizamos los datos
        reserva.setHabitacion(nuevaHabitacion);
        reserva.setFechaInicio(nuevaFechaInicio);
        reserva.setFechaFin(nuevaFechaFin);

        return true;
    }


}
