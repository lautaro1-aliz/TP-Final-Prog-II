package Entidades;

import Enums.Rol;
import Interfaces.Identificable;

import java.time.LocalDate;

public abstract class Usuario implements Identificable {

    //clase abstracta que define atributos de cliente, administrador y empleado
    private String nombre;
    private String apellido;
    private String DNI;
    private String mail;
    private String password;
    private String telefono;
    private Rol rol;

    //Constructor
    public Usuario(String DNI, String nombre, String apellido, String telefono, String mail, String password) {
        this.DNI = DNI;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.mail = mail;
        this.password = password;
    }

    //Constructor
    public Usuario() {
    }

    //Getters y Setters
    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    //Metodos abstractos
    public boolean iniciarSesion(String correo, String contra) {
        if (correo.equals(getMail()) && contra.equals(getPassword())) {
            return true;
        }
        System.out.println("ERROR datos invalidos");
        return false;
    }

    public boolean cerrarSesion() {
        return true;
    }

    public void actualizarDatosContacto(String correo, String telefono) {
        this.mail = correo;
        this.telefono = telefono;
        System.out.println("Datos actualizados correctamente");
    }

    @Override
    public boolean coincideCon(String criterio) {
        return DNI.equals(criterio);
    }


    public abstract String rol();

    @Override
    public String toString() {
        return "Usuario: " +
                "| Nombre: " + nombre  +
                " | Apellido: " + apellido  +
                " | DNI: " + DNI  +
                " | mail: " + mail  +
                " | Telefono" + telefono ;
    }
}
