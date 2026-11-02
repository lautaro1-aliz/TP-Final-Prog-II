package Entidades;

import java.time.LocalDate;

public class Pasajero extends Usuario{


    public Pasajero() {
    }

    public Pasajero(String DNI, String nombre, String apellido, LocalDate fechaNacimiento, String telefono, String mail, String password) {
        super(DNI, nombre, apellido, fechaNacimiento, telefono, mail, password);
    }

    @Override
    public void menu() {

    }

    @Override
    public String rol() {
        return "Pasajero";
    }
}

