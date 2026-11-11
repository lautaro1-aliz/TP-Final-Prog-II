package Entidades;

import Enums.Rol;
import Enums.Turno;

import java.time.LocalDate;

public class Empleado extends Usuario {
    private static int contador = 1;
    private int legajo;
    private Turno turno;

    public Empleado() {
    }

    public Empleado(int legajo, String nombre, String apellido, String dni, String mail, String password, String telefono) {
        super(dni, nombre, apellido, telefono, mail, password);
        this.legajo = legajo;
        contador++;
        setRol(Rol.EMPLEADO);
        this.turno = Turno.MANANA;
    }

    public Empleado(String dni, String nombre, String apellido, String telefono, String mail, String password) {
        super(dni, nombre, apellido, telefono, mail, password);
        contador++;
        setRol(Rol.EMPLEADO);
        this.turno = Turno.MANANA;
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
