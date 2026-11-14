package Interfaces;

import Enums.Rol;

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

}
