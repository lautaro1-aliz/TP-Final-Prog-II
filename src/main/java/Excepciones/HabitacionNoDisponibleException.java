package Excepciones;

public class HabitacionNoDisponibleException extends RuntimeException {
    String id;

    public HabitacionNoDisponibleException(String id, String mensaje) {
        super("La habitacion con identificador: " + id + " no esta disponible por: " + mensaje);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
