package Interfaces;

import Entidades.Usuario;

import java.util.List;

public interface DAO <T>{

    //dar de alta,
    void insertar(T elemento);

    //listar todos
    List<T> listarTodos();

    //buscar por ID
    T buscarPorId(String codigo);

    //modificar
    void actualizar(T elemento);

    //eliminar
    void eliminar(String codigo);

    T login(String username, String password);
}
