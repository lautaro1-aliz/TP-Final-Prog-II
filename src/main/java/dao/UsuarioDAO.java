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
import java.util.List;

public class UsuarioDAO implements DAO<Usuario> {

    @Override
    public void insertar(Usuario elemento) {

    }

    @Override
    public List<Usuario> listarTodos() {
        return List.of();
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

    public static Usuario buscarPorCredenciales(
            String email,
            String password) {

        String sql =
                "SELECT * FROM usuarios " +
                        "WHERE mail = ? " +
                        "AND password = ?";

        try (Connection conn = ConexionBD.getConexion() ;
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

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

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
