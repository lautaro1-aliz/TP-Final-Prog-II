package Entidades;

import Enums.EstadoReserva;
import Interfaces.Identificable;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reserva implements Identificable {

    private static int contador = 1;

    private int id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Habitacion habitacion;
    private Usuario usuario;
    private EstadoReserva estado;
    ///creo que conviene mas almacenaer solo el id del usuario y habitacion
    private int id_user;
    private int id_hab;

    public Reserva(LocalDate fechaInicio, LocalDate fechaFin, Habitacion habitacion, Usuario usuario) {
        this.id = contador++;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.habitacion = habitacion;
        this.usuario = usuario;
        this.estado = EstadoReserva.PENDIENTE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_hab() {
        return id_hab;
    }

    public void setId_hab(int id_hab) {
        this.id_hab = id_hab;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
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

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    @Override
    public boolean coincideCon(String criterio) {
        return String.valueOf(id).equals(criterio);
    }

    public boolean seSuperpone(LocalDate inicio, LocalDate fin) {
        return fechaInicio.isBefore(fin) &&
                fechaFin.isAfter(inicio);
    }

    ///Metodo para calcular noches de la estadia en el hotel
    /// Soy un queso con todo lo relacionado con las fechas asi que aca me ayudo chatgpt jajaja
    public long calcularNoches() {
        return ChronoUnit.DAYS.between(fechaInicio, fechaFin);
    }

    public boolean estaActiva() {
        return estado == EstadoReserva.PENDIENTE ||
                estado == EstadoReserva.CONFIRMADA;
    }
}
