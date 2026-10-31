package clases;
import enums.EstadoHabitacion;

public abstract class Habitacion {

    private int id;
    private int piso;
    private int capacidad;
    private EstadoHabitacion estado;
    private double precioXnoche;
    private String tipo;

    //Constructor
    public Habitacion(int capacidad, int id, int piso, double precioXnoche,String tipo) {
        this.capacidad = capacidad;
        this.estado = EstadoHabitacion.LIBRE;
        this.id = id;
        this.piso = piso;
        this.precioXnoche = precioXnoche;
        this.tipo = tipo;
    }

    public Habitacion() {
    }

    //Getter y Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public EstadoHabitacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoHabitacion estado) {
        this.estado = estado;
    }

    public double getPrecioXnoche() {
        return precioXnoche;
    }

    public void setPrecioXnoche(double precioXnoche) {
        this.precioXnoche = precioXnoche;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    //Calcular costo estadia
    public abstract double calcularCostoEstadia(int noches);

    @Override
    public boolean equals(Object obj) {
        if(this ==obj) return true;

        if(obj == null || this.getClass()!=obj.getClass())return false;

        //casteo
        Habitacion otra = (Habitacion) obj;

        // ver despues que criterio usamos para considerar igualdad de habitacion
        return id == otra.id && piso == otra.piso && capacidad == otra.capacidad;
    }

    @Override
    public String toString() {
        return "Habitacion{" +
                "capacidad=" + capacidad +
                ", id=" + id +
                ", piso=" + piso +
                ", estado=" + estado +
                ", precioXnoche=" + precioXnoche +
                '}';
    }
}
