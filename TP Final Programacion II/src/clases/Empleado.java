package clases;

import enums.Turno;

import java.time.LocalDate;

public class Empleado extends Usuario {
    private int legajo;
    private Turno turno;

    public Empleado(String DNI, String nombre, String apellido, LocalDate fechaNacimiento, String telefono, String mail, String password, int legajo, Turno turno) {
        super(DNI, nombre, apellido, fechaNacimiento, telefono, mail, password);
        this.legajo = legajo;
        this.turno = turno;
    }

    public int getLegajo() {
        return legajo;
    }

    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }



    @Override
    public void menu() {

    }
}
