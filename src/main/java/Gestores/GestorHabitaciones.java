package Gestores;

import Entidades.Habitacion;
import Enums.EstadoHabitacion;
import Interfaces.DAO;
import dao.HabitacionDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestorHabitaciones extends GestorGenerico<Habitacion> {

    private final HabitacionDAO dao = new HabitacionDAO();

    public List<Habitacion> listarPorTipo(String tipo) {
        List<Habitacion> resultado = new ArrayList<>();

        ///Validacion para que no introduzcan un valor nulo mas adelante se puede cambiar por una excepcion
        if (tipo == null || tipo.isBlank()) {
            return resultado;
        }

        for (Habitacion h : elementos) {
            if (h.getTipo().equalsIgnoreCase(tipo)) {
                resultado.add(h);
            }
        }
        return resultado;
    }

    public double calcularRecaudacionEstimada(int noches) {
        double total = 0;
        for (Habitacion elemento : elementos) {
            total += elemento.calcularCostoEstadia(noches);
        }
        return total;
    }


    public Habitacion buscarHabitacion(int numero) {
        return dao.buscarPorId(numero);
    }

    public boolean agregarHabitacion(Habitacion habitacion) {
        dao.insertar(habitacion);
        return agregar(habitacion);
    }

    public List<Habitacion> listarHabitaciones() {
        return listar();
    }

    public List<Habitacion> listarHabitacionesPorEstado(EstadoHabitacion estado) {
        return dao.listarPorEstado(estado);
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
        if (habitacion == null ||
                habitacion.getEstado() != EstadoHabitacion.LIBRE) {
            return false;
        }

        if (!dao.actualizarEstado(idHabitacion, EstadoHabitacion.OCUPADA)) {
            return false;
        }

        habitacion.setEstado(EstadoHabitacion.OCUPADA);

        return true;
    }

    public boolean checkOut(int idHabitacion) {

        Habitacion habitacion = buscarHabitacion(idHabitacion);
        if (habitacion == null ||
                habitacion.getEstado() != EstadoHabitacion.OCUPADA) {
            return false;
        }

        if (!dao.actualizarEstado(idHabitacion, EstadoHabitacion.LIBRE)) {
            return false;
        }

        habitacion.setEstado(EstadoHabitacion.LIBRE);

        return true;
    }

    public void guardarBD() throws SQLException {
        int guardadas = 0;
        for (Habitacion h : elementos) {
            guardarEnBD(h);
            guardadas++;
        }
        System.out.println("Habitaciones guardadas: " + guardadas);
    }

    public boolean marcarHabitacionNoDisponible(int id, String motivo) {
        Habitacion habitacion = buscarHabitacion(id);

        if (habitacion == null || motivo == null || motivo.isBlank()) {
            return false;
        }

        habitacion.marcarNoDisponible(motivo);
        return true;
    }

    public void guardarEnBD(Habitacion h) throws SQLException {
        dao.insertar(h);
    }

    public void actualizar(Habitacion h){
        dao.actualizar(h);
    }
}
