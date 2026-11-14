package dao;

import ConexionBD.ConexionBD;
import Entidades.Administrador;
import Entidades.Empleado;
import Entidades.Pasajero;
import Entidades.Usuario;
import Enums.Rol;
import Interfaces.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements DAO<Usuario> {

    private static final String SQL_INSERTAR = "INSERT INTO usuarios (nombre,apellido,dni,mail,password,telefono,rol) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_LISTAR = "SELECT * FROM usuarios WHERE rol = ? ORDER BY id";
    private static final String SQL_BUSCAR = "SELECT * FROM usuarios WHERE id = ?";
    private static final String SQL_ACTUALIZAR = "UPDATE usuarios SET mail = ?, password = ? WHERE dni =  ?";
    private static final String SQL_ELIMINAR = "DELETE FROM usuarios WHERE id = ?";
    @Override
    public void insertar(Usuario u) {
        int filas = 0;
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_INSERTAR)) {
            ps.setString(1,u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3,u.getDNI());
            ps.setString(4,u.getMail());
            ps.setString(5,u.getPassword());
            ps.setString(6,u.getTelefono());
            ps.setString(7,u.getRol().name());
            filas = ps.executeUpdate();
            if(filas!=0){
                System.out.println(u.getRol()+" insertado correctamente.");
            }else{
                throw new RuntimeException("Error al insertar usuario");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> resultado = new ArrayList<>();
        String listar = "SELECT * FROM usuarios";

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
    public Usuario buscarPorId(int id) {
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
    public void actualizar(Usuario u) {
        int filas = 0;
        try(Connection con = ConexionBD.getConexion();
            PreparedStatement ps = con.prepareStatement(SQL_ACTUALIZAR)){
            ps.setString(1,u.getMail());
            ps.setString(2,u.getPassword());
            ps.setString(1,u.getDNI());
            filas = ps.executeUpdate();
            if(filas!=0){
                System.out.println("Cambios realizados correctamente");
            }else{
                //todo añadir excepcion
                System.out.println("No hay usuario registrado con ese dni");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void eliminar(int id) {
        int filas = 0;
        try(Connection con = ConexionBD.getConexion();
            PreparedStatement ps = con.prepareStatement(SQL_ELIMINAR)){
            ps.setInt(1,id);

            filas = ps.executeUpdate();
            if (filas ==0){
                throw new SQLException("Usuario no encontrado");
            }
            System.out.println("Usuario eliminado correctamente");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Usuario> listarPorRol(Rol rol){
        List<Usuario> resultado = new ArrayList<>();
        try(Connection con = ConexionBD.getConexion();
            PreparedStatement ps = con.prepareStatement(SQL_LISTAR)){
            ps.setString(1, rol.name());

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    resultado.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }
    
    public static Usuario mapear(ResultSet rs) throws SQLException {

        Rol rol = Rol.valueOf(rs.getString("rol"));

        if (rol == Rol.EMPLEADO) {
            return new Empleado(
                    rs.getInt("legajo"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("dni"),
                    rs.getString("mail"),
                    rs.getString("password"),
                    rs.getString("telefono")
            );
        }else if(rol == Rol.ADMINISTRADOR){
            return new Administrador(
                    rs.getInt("legajo"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("dni"),
                    rs.getString("mail"),
                    rs.getString("password"),
                    rs.getString("telefono")
            );
        }
        return new Pasajero(
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("dni"),
                rs.getString("mail"),
                rs.getString("password"),
                rs.getString("telefono")
        );
    }

    public static Usuario buscarPorCredenciales(String email,String password) {

        String sql =
                " SELECT * FROM usuarios " +
                        "WHERE mail = ? " +
                        "AND password = ?";

        try (Connection conn = ConexionBD.getConexion() ;
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return mapear(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
