package dao;

import ConexionBD.ConexionBD;
import Entidades.Habitacion;
import Enums.EstadoHabitacion;
import Enums.EstadoReserva;
import Interfaces.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HabitacionDAO implements DAO<Habitacion> {

    private static final String SQL_INSERTAR = " INSERT INTO habitaciones (piso, capacidad, estado, precio_x_noche, tipo, motivo_no_disponible) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_LISTAR = " SELECT * FROM habitaciones ORDER BY id";
    private static final String SQL_BUSCAR = "SELECT * FROM habitaciones WHERE id = ?";
    private static final String SQL_ACTUALIZAR = "UPDATE habitaciones SET precio_por_noche = ?, estado = ? WHERE codigo = ?";
    private static final String SQL_ELIMINAR = "DELETE FROM habitaciones WHERE id = ?";


    @Override
    public void actualizar(Habitacion h) {
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
    public void insertar(Habitacion h) {
        int filas = 0;
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_INSERTAR)) {
            ps.setInt(1, h.getId());
            ps.setInt(2,h.getPiso());
            ps.setString(3, h.getTipo());
            ps.setDouble(4, h.getPrecioXnoche());
            ps.setInt(5, h.getCapacidad());
            ps.setString(6, h.getEstado().name());
            ps.executeUpdate();
            if(filas!=0){
                System.out.println(" Habitacion insertada correctamente.");
            }else{
                throw new RuntimeException("Error al insertar la habitacion");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Habitacion> listarTodos() {
        List<Habitacion> lista = new ArrayList<>();
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_LISTAR);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(crearHabitacion(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lista;
    }

    @Override
    public Habitacion buscarPorId(int id) {
        /// ejecuta la query y si encontro lo buscado lo mapea y retorna
        /// añadi excepcion ElementoNoEncontrado para informar al usuario si no existe la habitacion
        try(PreparedStatement ps = ConexionBD.getConexion().prepareStatement(SQL_BUSCAR)) {
            ps.setInt(1,id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) {
                    return crearHabitacion(rs);
                }
               //TODO añadir excepcion
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void eliminar(int id) {
        int filas;
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_ELIMINAR)) {
            ps.setInt(1, id);
            filas = ps.executeUpdate();
            if(filas == 0){
                // TODO añadir excepciones
                System.out.println("No hay coincidencias");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public List<Habitacion> listarPorEstado(EstadoHabitacion estado) {
        List<Habitacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM habitaciones WHERE estado = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setString(1,estado.name());
            try(ResultSet rs = ps.executeQuery()){
            while (rs.next()) {
                lista.add(crearHabitacion(rs));
            }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lista;
    }

    private Habitacion crearHabitacion(ResultSet rs) throws SQLException {
        return new Habitacion(rs.getInt("id"),
                            rs.getInt("piso"),
                            rs.getInt("capacidad"),
                            rs.getDouble("precio_x_noche"),
                            rs.getString("tipo"),
                            rs.getString("motivo_no_disponible"),
                            EstadoHabitacion.valueOf(rs.getString("estado")));
    }
}
