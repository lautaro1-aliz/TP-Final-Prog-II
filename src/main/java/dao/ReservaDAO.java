package dao;

import ConexionBD.ConexionBD;
import Entidades.Reserva;
import Interfaces.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class ReservaDAO implements DAO<Reserva> {

    private static final String SQL_INSERTAR = "INSERT INTO reservas (fecha_I,fecha_F,id_usuario,id_habitacion,estado) VALUES (?, ?, ?, ?,?)";
    private static final String SQL_LISTAR = "SELECT * FROM reservas ORDER BY codigo";
    private static final String SQL_BUSCAR = "SELECT * FROM reservas WHERE codigo = ?";
    private static final String SQL_ACTUALIZAR = "UPDATE reservas SET precio_por_noche = ?, estado = ? WHERE codigo = ?";
    private static final String SQL_ELIMINAR = "DELETE FROM reservas WHERE codigo = ?";

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
            ps.setInt(3,r.getId_user());
            ps.setInt(4,r.getId_hab());
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
        return List.of();
    }

    @Override
    public Reserva buscarPorId(int id) {
        return null;
    }

    @Override
    public void eliminar(int id) {

    }
}
