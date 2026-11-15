package Gestores;

import Entidades.*;
import Enums.EstadoHabitacion;
import Enums.EstadoReserva;
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

    public void listarEmpleados() {
        // Devuelve una lista de los empleados
        List<Empleado> resultado = empleados.mostrarEmpleados();

        for (Empleado e : resultado) {
            System.out.println(e);
        }
    }

    public boolean eliminarUsuario(int legajo) {

        Empleado e = empleados.buscarPorLegajo(legajo);

        if (e == null) {
            return false;
        }

        return empleados.eliminar(e);
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

    ///Metodos correspondientes al Menu Pasajero

    public void cambiarContrasenia(Usuario u){
        pasajeros.actualizarPass(u);
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

        Habitacion habitacion = habitaciones.buscarHabitacion(id);

        if (habitacion == null || reservas.tieneReservasActivas(id)) {
            return false;
        }

        return habitaciones.eliminar(habitacion);
    }

    public boolean registrarReserva(int idHabitacion, String dniUsuario, LocalDate fechaInicio, LocalDate fechaFin) {


        Habitacion habitacion = habitaciones.buscarHabitacion(idHabitacion);
        Pasajero pasajero  =  pasajeros.buscarPorDni(dniUsuario);

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

        if (reserva == null ||
                (reserva.getEstado() != EstadoReserva.PENDIENTE && reserva.getEstado() != EstadoReserva.CONFIRMADA)) {
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

    public boolean realizarCheckIn(int idReserva) {
        return empleados.realizarCheckIn(reservas, habitaciones, idReserva);
    }

    public boolean realizarCheckOut(int idHabitacion) {
        return empleados.realizarCheckOut(reservas, habitaciones, idHabitacion);
    }

    public boolean confirmarReserva(int idReserva) {
        return reservas.confirmarReserva(idReserva);
    }

    public boolean cancelarReserva(int idReserva) {
        return reservas.cancelarReserva(idReserva);
    }

    public List<Reserva> listarReservasPorUsuario(String dni) {
        return reservas.listarReservasPorUsuario(dni);
    }

    public List<Reserva> listarReservasPorHabitacion(int idHabitacion) {
        return reservas.listarReservasPorHabitacion(idHabitacion);
    }

    public List<Habitacion> listarHabitacionesPorEstado(EstadoHabitacion estado) {
        return habitaciones.listarHabitacionesPorEstado(estado);
    }

    public boolean marcarHabitacionNoDisponible(int id, String motivo) {
        return habitaciones.marcarHabitacionNoDisponible(id, motivo);
    }

    public List<Pasajero> listarPasajeros() {
        return pasajeros.listar();
    }

    public void generarInformePasajeros() {
        List<Pasajero> lista = listarPasajeros();

        for (Pasajero p : lista) {
            System.out.println("Nombre: " + p.getNombre() + " " + p.getApellido());
            System.out.println("DNI: " + p.getDNI());
            System.out.println("Nacionalidad: " + p.getNacionalidad());
            System.out.println("Domicilio: " + p.getDomicilio());
            System.out.println("--------------------------------");
        }
    }
}
