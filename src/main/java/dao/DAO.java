package dao;

import java.util.List;

public interface DAO<T> {

    void guardar(T objeto);

    T obtener(int id);

    void actualizar(T objeto);

    void eliminar(int id);

    List<T> listar();

}
