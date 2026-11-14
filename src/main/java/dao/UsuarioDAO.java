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

    private static final String SQL_INSERTAR = " INSERT INTO usuarios (codigo, tipo, nombre, piso, precio_por_noche, vista,estado, cantidad_camas, jacuzzi, capacidad_personas) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
    private static final String SQL_LISTAR = "SELECT * FROM usuarios ORDER BY codigo";
    private static final String SQL_BUSCAR = "SELECT * FROM usuarios WHERE codigo = ?";
    private static final String SQL_ACTUALIZAR = "UPDATE usuarios SET precio_por_noche = ?, estado = ? WHERE codigo = ?";
    private static final String SQL_ELIMINAR = "DELETE FROM usuarios WHERE codigo = ?";
    @Override
    public void insertar(Usuario elemento) {
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_INSERTAR)) {

            ps.executeUpdate();
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
    public Usuario buscarPorId(String codigo) {
        return null;
    }

    @Override
    public void actualizar(Usuario elemento) {

    }

    @Override
    public void eliminar(String codigo) {

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
