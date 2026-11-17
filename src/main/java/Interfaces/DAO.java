package Interfaces;

import Enums.Rol;

import java.sql.SQLException;
import java.util.List;

public interface DAO <T>{

    //dar de alta,
    void insertar(T elemento) throws SQLException;

    //listar todos
    List<T> listarTodos();

    //buscar por ID
    T  buscarPorId(int id);

    //modificar
    void actualizar(T elemento);

    //eliminar
    void eliminar(int id);

}
