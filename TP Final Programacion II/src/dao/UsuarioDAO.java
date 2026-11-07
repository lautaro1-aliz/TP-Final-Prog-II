package dao;

import Entidades.Usuario;
import Interfaces.DAO;

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

    @Override
    public Usuario login(String username, String password) {
            String sql = "SELECT * FROM usuarios " + "WHERE username=? AND password=?";
            return null;
    }
}
