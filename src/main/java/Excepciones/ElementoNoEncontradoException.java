package Excepciones;

public class ElementoNoEncontradoException extends RuntimeException {
    private final int id;

    public ElementoNoEncontradoException(String mensaje, int id) {
        super("No se encontró " + mensaje + " con el identificador: " + id);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
