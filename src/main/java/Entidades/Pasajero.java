package Entidades;

import Enums.Rol;

import java.time.LocalDate;

public class Pasajero extends Usuario{

    public Pasajero() {
    }

    public Pasajero(String nombre, String apellido, String dni, String mail, String password, String telefono) {
        super(dni, nombre, apellido, telefono, mail, password);
        setRol(Rol.PASAJERO);
    }

    @Override
    public String rol() {
        return "Pasajero";
    }


}

