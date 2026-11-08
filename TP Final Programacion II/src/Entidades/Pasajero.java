package Entidades;

import java.time.LocalDate;

public class Pasajero extends Usuario{
    private final String origen;
    private final String domiciolioOrigen;

    public Pasajero(String origen, String domiciolioOrigen) {
        this.origen = origen;
        this.domiciolioOrigen = domiciolioOrigen;
    }

    public Pasajero(String DNI, String nombre, String apellido, LocalDate fechaNacimiento, String telefono, String mail, String password, String origen, String domicilioOrigen) {
        super(DNI, nombre, apellido, fechaNacimiento, telefono, mail, password);
        this.origen = origen;
        this.domiciolioOrigen = domicilioOrigen;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDomiciolioOrigen() {
        return domiciolioOrigen;
    }

    @Override
    public String rol() {
        return "Pasajero";
    }


}

