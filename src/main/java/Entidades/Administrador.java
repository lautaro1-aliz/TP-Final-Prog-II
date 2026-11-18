package Entidades;

import Enums.Rol;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Administrador extends Empleado {
    private List<String> permisos;
    private String nivelAcceso;

    //Constructor
    public Administrador(List<String> permisos, String nivelAcceso) {
        this.permisos = permisos;
        this.nivelAcceso = nivelAcceso;
    }

    public Administrador(String DNI, String nombre, String apellido, String telefono, String mail, String password) {
        super(DNI, nombre, apellido, telefono, mail, password);
        this.permisos = new ArrayList<>();
        setRol(Rol.ADMINISTRADOR);
        this.nivelAcceso = "Todos los permisos";
    }

    public Administrador(int legajo, String nombre, String apellido, String dni, String mail, String password, String telefono) {
        super(dni, nombre, apellido, telefono, mail, password);
        setRol(Rol.ADMINISTRADOR);
    }

    //Getters y Setters
    public String getNivelAcceso() {
        return nivelAcceso;
    }

    public void setNivelAcceso(String nivelAcceso) {
        this.nivelAcceso = nivelAcceso;
    }

    public List<String> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<String> permisos) {
        this.permisos = permisos;
    }


    @Override
    public String rol() {
        return "Administrador";
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
