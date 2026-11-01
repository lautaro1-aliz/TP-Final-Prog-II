package clases;

import interfaces.Identificable;

import java.time.LocalDate;

public class Reserva implements Identificable {

    private static int contador = 1;

    private int id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Habitacion habitacion;
    private Usuario usuario;

    public Reserva(LocalDate fechaInicio, LocalDate fechaFin, Habitacion habitacion, Usuario usuario) {
        this.id = contador++;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.habitacion = habitacion;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    ///Lo dejo por las dudas pero en la logica no haria falta setearlo ya que seria autoincremental
    /**
     * public void setId(int id) {
     * this.id = id;
     * }
     */

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean coincideCon(String criterio) {
        return String.valueOf(id).equals(criterio);
    }

    public boolean seSuperpone(LocalDate inicio, LocalDate fin) {
        return fechaInicio.isBefore(fin) &&
                fechaFin.isAfter(inicio);
    }
}
