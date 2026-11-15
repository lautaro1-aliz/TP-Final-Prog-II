package Entidades;

import Enums.Rol;

import java.time.LocalDate;

public class Pasajero extends Usuario{

    private String nacionalidad;
    private Domicilio domicilio;
    public Pasajero() {
    }

    public Pasajero(String nombre, String apellido, String DNI, String mail, String telefono, String password, String nacionalidad) {
        super(DNI, nombre, apellido, telefono, mail, password);
        //todo ver como mapear el domicilio
    //    this.domicilio = domicilio;
        this.nacionalidad = nacionalidad;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    @Override
    public String rol() {
        return "Pasajero";
    }


}

