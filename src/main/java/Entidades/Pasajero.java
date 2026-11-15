package Entidades;

import Enums.Rol;

import java.time.LocalDate;

public class Pasajero extends Usuario{

    private String nacionalidad;
    private Domicilio domicilio;
    public Pasajero() {
    }

    public Pasajero(String nombre, String apellido, String DNI, String mail, String telefono, String password) {
        super(DNI, nombre, apellido, telefono, mail, password);
        //todo ver como mapear el domicilio
    //    this.domicilio = domicilio;
        this.nacionalidad = nacionalidad;
        setRol(Rol.PASAJERO);
    }

    @Override
    public String rol() {
        return "Pasajero";
    }

    @Override
    public String toString() {
        return super.toString()+"Pasajero{" + getPassword();
    }
}

