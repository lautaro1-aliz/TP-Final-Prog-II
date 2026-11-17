package dao;

import ConexionBD.ConexionBD;
import Entidades.Habitacion;
import Entidades.Reserva;
import Entidades.Usuario;
import Enums.EstadoReserva;
import Interfaces.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ReservaDAO implements DAO<Reserva> {

    private static final String SQL_INSERTAR = "INSERT INTO reservas (fecha_I,fecha_F,id_usuario,id_habitacion,estado) VALUES (?, ?, ?, ?,?)";
    private static final String SQL_LISTAR = "SELECT * FROM reservas ORDER BY id";
    private static final String SQL_BUSCAR = "SELECT * FROM reservas WHERE id = ?";
    private static final String SQL_ACTUALIZAR = "UPDATE reservas SET precio_por_noche = ?, estado = ? WHERE id = ?";
    private static final String SQL_ELIMINAR = "DELETE FROM reservas WHERE id = ?";

    @Override
    public void actualizar(Reserva elemento) {
        int filas = 0;
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_ACTUALIZAR)) {
            //TODO ver que queremos actualizar de una habitacion
            filas = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (filas == 0) {
            //TODO añadir excepciones
            System.out.println("No hay coincidencias");
        }
    }

    @Override
    public void insertar(Reserva r) {
        int filas = 0;
        try(Connection con = ConexionBD.getConexion();
            PreparedStatement ps = con.prepareStatement(SQL_INSERTAR)){
            //paso el tipo de dato de LocalDate a Date para poder guardar en BD
            ps.setDate(1,java.sql.Date.valueOf(r.getFechaInicio()));
            ps.setDate(2,java.sql.Date.valueOf(r.getFechaFin()));
            ps.setInt(3,r.getUsuario().getId());
            ps.setInt(4,r.getHabitacion().getId());
            ps.setString(5,r.getEstado().name());
            if(filas == 0){
                throw new RuntimeException("Error al registrar la reserva");
            }
            System.out.println("Reserva registrada exitosamente! ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Reserva> listarTodos() {
        List<Reserva> resultado = new ArrayList<>();
        String listar = "SELECT * FROM reservas";

        try(Connection con = ConexionBD.getConexion();
            PreparedStatement ps = con.prepareStatement(listar)){

            try (ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    resultado.add(mapear(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }

    @Override
    public Reserva buscarPorId(int id) {
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_BUSCAR)){
            ps.setInt(1,id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return mapear(rs);
                }
                //todo añadir excepcion
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void eliminar(int id) {
        int filas = 0;
        try(Connection con = ConexionBD.getConexion();
            PreparedStatement ps = con.prepareStatement(SQL_ELIMINAR)){
            ps.setInt(1,id);

            filas = ps.executeUpdate();
            if (filas ==0){
                throw new SQLException("Reserva no encontrada");
            }
            System.out.println("Reserva cancelada correctamentw");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Reserva mapear(ResultSet rs) throws SQLException {

        //busco el usuario segun el id en la base de datos
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.buscarPorId(rs.getInt("usuario_id"));

        //busco la habitacion segun id en base de datos
        HabitacionDAO habitacionDAO = new HabitacionDAO();
        Habitacion habitacion = habitacionDAO.buscarPorId(rs.getInt("habitacion_id"));

        //mapeo las columnas faltantes y paso como parametro los objetos obtenidos
        return new Reserva(rs.getInt("id"),
                            rs.getDate("fecha_inicio").toLocalDate(),
                            rs.getDate("Fecha_fin").toLocalDate(),
                            EstadoReserva.valueOf(rs.getString("estado").toUpperCase()),
                            usuario,habitacion);
    }
}
