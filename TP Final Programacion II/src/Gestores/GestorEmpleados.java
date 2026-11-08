package Gestores;

import Entidades.Empleado;
import Entidades.Reserva;

public class GestorEmpleados extends GestorGenerico<Empleado> {

    public void realizarCheckIn(){}

    public void realizarCheckOut(){}

    public void administrarHabitaciones(){}


    public Empleado buscarPorLegajo(int legajo) {

        for (Empleado e : elementos) {

            if (e.getLegajo() == legajo) {
                return e;
            }

        }

        return null;
    }
}
