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
    private EstadoReserva estado;
    private Usuario usuario;
    private Habitacion habitacion;
    private int id_user;
    private int id_hab;


    public Reserva(int id, LocalDate fechaI, LocalDate fechaF, int idUsuario, int idHabitacion, EstadoReserva estado) {
        this.id = id;
        this.fechaInicio = fechaI;
        this.fechaFin = fechaF;
        this.id_hab = idHabitacion;
        this.id_user = idUsuario;
        this.estado = estado;
    }

    public Reserva() {
    }

    public Reserva(LocalDate fechaInicio, LocalDate fechaFin, int id, int id1) {

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
                estado == EstadoReserva.CONFIRMADA ||
                estado == EstadoReserva.EN_CURSO;
    }
}
