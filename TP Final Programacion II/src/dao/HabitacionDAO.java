package dao;

import Entidades.Habitacion;
import Interfaces.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import ConexionBD.*;

public class HabitacionDAO implements DAO<Habitacion> {

    private static final String SQL_INSERTAR = "INSERT INTO habitaciones (codigo, tipo, nombre, piso, precio_por_noche, vista, "
            + "estado, cantidad_camas, jacuzzi, capacidad_personas) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_LISTAR = "SELECT * FROM habitaciones ORDER BY codigo";
    private static final String SQL_BUSCAR = "SELECT * FROM habitaciones WHERE codigo = ?";
    private static final String SQL_ACTUALIZAR = "UPDATE habitaciones SET precio_por_noche = ?, estado = ? WHERE codigo = ?";
    private static final String SQL_ELIMINAR = "DELETE FROM habitaciones WHERE codigo = ?";


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
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_INSERTAR)) {
            ps.setInt(1, h.getId());
            ps.setInt(2,h.getPiso());
            ps.setString(3, h.getTipo());
            ps.setDouble(4, h.getPrecioXnoche());
            ps.setInt(5, h.getCapacidad());
            ps.setString(6, h.getEstado().name());
            ps.executeUpdate();
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
    public Habitacion buscarPorId(String codigo) {
        /// ejecuta la query y si encontro lo buscado lo mapea y retorna
        /// añadi excepcion ElementoNoEncontrado para informar al usuario si no existe la habitacion
        try(PreparedStatement ps = ConexionBD.getConexion().prepareStatement(SQL_BUSCAR)) {
            ps.setString(1,codigo);
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
    public void eliminar(String codigo) {
        int filas;
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_ELIMINAR)) {
            ps.setString(1, codigo);
            filas = ps.executeUpdate();
            if(filas == 0){
                // TODO añadir excepciones
                System.out.println("No hay coincidencias");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Habitacion login(String username, String password) {
        return null;
    }

    private Habitacion crearHabitacion(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipo");
        String codigo = rs.getString("codigo");
        String nombre = rs.getString("nombre");
        int piso = rs.getInt("piso");
        double precio = rs.getDouble("precio_por_noche");

        //TODO ver si finalmente dejamos Habitacion como abstracta para definir esta parte
//        switch (tipo) {
//            case "SIMPLE":
//                h = new HabitacionSimple(codigo, nombre, piso, precio, vista, rs.getInt("cantidad_camas"));
//                break;
//            case "SUITE":
//                h = new Suite(codigo, nombre, piso, precio, vista, rs.getBoolean("jacuzzi"));
//                break;
//            case "CABANA":
//                h = new Cabana(codigo, nombre, piso, precio, vista, rs.getInt("capacidad_personas"));
//                break;
//            default:
//                throw new SQLException("Tipo de habitación desconocido: " + tipo);
//        }
//        h.setEstado(EstadoHabitacion.valueOf(rs.getString("estado")));
        return null;
    }
}
