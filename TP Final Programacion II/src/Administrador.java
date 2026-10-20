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
    public void crearUsuario(){}
    public void realizarBackup(){}
    public void asignarPermisos(Usuario u,String permiso){}


    @Override
    public void menu() {
        MenuAdministrador m = new MenuAdministrador();
        m.mostrarMenuAdministrador();
    }
}
