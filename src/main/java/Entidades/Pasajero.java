package Entidades;

import Enums.Rol;

import java.time.LocalDate;

public class Pasajero extends Usuario{

    private String nacionalidad;
    private String domicilio;
    public Pasajero() {
    }

    public Pasajero(String nombre, String apellido, String DNI, String mail, String telefono, String password, String nacionalidad,String domicilio) {
        super(DNI, nombre, apellido, telefono, mail, password);
        //todo ver como mapear el domicilio

        this.domicilio = domicilio;
        this.nacionalidad = nacionalidad;
        setRol(Rol.PASAJERO);
    }

    public Pasajero(String nombre, String apellido, String dni, String mail, String telefono, String password) {
        super(dni,nombre,apellido,telefono,mail,password);
        setRol(Rol.PASAJERO);
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
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

