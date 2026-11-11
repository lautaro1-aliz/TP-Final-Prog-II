package Gestores;

import Entidades.Habitacion;
import Enums.EstadoHabitacion;
import Interfaces.DAO;
import dao.HabitacionDAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestorHabitaciones extends GestorGenerico<Habitacion>{

    final DAO<Habitacion> dao = new HabitacionDAO();
    public List<Habitacion> listarPorTipo(String tipo) {
        List<Habitacion> resultado = new ArrayList<>();
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
      return buscar(String.valueOf(numero));
   }

    public boolean agregarHabitacion(Habitacion habitacion) {
        return agregar(habitacion);
    }

    public List<Habitacion> listarHabitaciones() {
        return listar();
    }

    public List<Habitacion> listarHabitacionesPorEstado(EstadoHabitacion estado) {
        List<Habitacion> resultado = new ArrayList<>();

        for (Habitacion habitacion : listar()) {
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
    public  void guardarBD(){
        int guardadas= 0;
        for(Habitacion h: elementos){
            guardarEnBD(h);
            guardadas++;
        }
        System.out.println("Habitaciones guardadas: "+guardadas);
    }



    public void guardarEnBD(Habitacion h)  {
        dao.insertar(h);
    }
//
//    public List<Habitacion> listarDesdeBD() {
//        return dao.listarTodos();
//    }
//
//    public Habitacion buscarEnBD(String codigo){
//        return dao.buscarPorId(codigo);
//    }
//
//    public void actualizarEnBD(Habitacion h)  {
//        dao.actualizar(h);
//    }
//
//    public void eliminarDeBD(String codigo)  {
//        dao.eliminar(codigo);
//    }
//    public void ocuparHabitacion(String codigo) {
//    Habitacion h = buscar(codigo);
//    h.ocupar();
//}
//
//    public void desocuparHabitacion(String codigo) {
//        Habitacion h = buscar(codigo);
//        h.desocupar();
//    }

}
