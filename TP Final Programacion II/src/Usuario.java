import java.time.LocalDate;

public abstract class Usuario {

    //clase abstracta que define atributos de cliente, administrador y empleado
    private String nombre;
    private String apellido;
    private String DNI;
    private String mail;
    private String password;
    private String telefono;
    private LocalDate fechaNacimiento;

    //Constructor
    public Usuario(String DNI, String nombre, String apellido, LocalDate fechaNacimiento,String telefono, String mail, String password) {
        this.DNI = DNI;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
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

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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

    //Metodos abstractos
    public boolean iniciarSesion(String correo, String contra){
        if(correo.equals(getMail()) && contra.equals(getPassword())){
            return true;
        }
        System.out.println("ERROR datos invalidos");
        return false;
    }
    public boolean cerrarSesion(){
        return true;
    }
    public void actualizarDatosContacto(String correo,String telefono){
        this.mail = correo;
        this.telefono = telefono;
        System.out.println("Datos actualizados correctamente");
    }

    public abstract void menu();
}
