package Entidades;

import Gestores.GestionHotel;
import Gestores.GestorGenerico;
import Gestores.GestorHabitaciones;
import Gestores.MenuAdministrador;

import java.time.LocalDate;
import java.util.List;

public class Administrador extends Usuario {
    private List<String> permisos;
    private String nivelAcceso;

    //Constructor
    public Administrador(List<String> permisos, String nivelAcceso) {
        this.permisos = permisos;
        this.nivelAcceso = nivelAcceso;
    }

    public Administrador(String DNI, String nombre, String apellido, LocalDate fechaNacimiento, String telefono, String mail, String password, List<String> permisos, String nivelAcceso) {
        super(DNI, nombre, apellido, fechaNacimiento, telefono, mail, password);
        this.permisos = permisos;
        this.nivelAcceso = nivelAcceso;
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

    //metodos
    public void crearUsuario(GestionHotel sistema, Usuario user){
        sistema.agregarUsuario(user);
    }

     public void eliminarUsuario(GestionHotel sistema, Usuario user){
        sistema.eliminarUsuario(user);
     }


    public void registrarHabitacion(GestionHotel sistema,Habitacion h){
       // sistema.registrarHabitacion(h);
    }
    public void eliminarHabitacion(GestionHotel sistema, Habitacion h){
        sistema.eliminarHabitacion(h.getId());
    }

    public void realizarBackup(){
        //TODO logica dao

    }

    public void asignarPermisos(Usuario u, String permiso){

    }


    @Override
    public String rol() {
        return "Administrador";
    }


}
