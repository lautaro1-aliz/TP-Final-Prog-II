package clases;

import java.util.ArrayList;
import java.util.List;

public class GestorHabitaciones extends GestorGenerico<Habitacion>{


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

//    public void guardarEnBD(Habitacion h)  {
//        dao.insertar(h);
//    }
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
