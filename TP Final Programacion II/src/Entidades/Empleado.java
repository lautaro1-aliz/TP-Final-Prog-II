package Entidades;

import Enums.Turno;

import java.time.LocalDate;

public class Empleado extends Usuario {
    private static int contador = 1;
    private int legajo;
    private Turno turno;

    public Empleado(String DNI, String nombre, String apellido, LocalDate fechaNacimiento, String telefono, String mail, String password) {
        super(DNI, nombre, apellido, fechaNacimiento, telefono, mail, password);
        this.legajo = contador;
        contador++;
        this.turno = Turno.MANANA;
    }
    public Empleado() {
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
    public String rol() {
        return "Empleado";
    }


}
