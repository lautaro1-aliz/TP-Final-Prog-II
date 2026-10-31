package clases;

import java.util.ArrayList;
import java.util.List;

public class GestorGenerico<T> {

    private List<T> elementos;

    public GestorGenerico() {
        elementos = new ArrayList<>();
    }

    public boolean agregar(T elemento) {
        if (elemento == null || elementos.contains(elemento)) {
            return false;
        }
        return elementos.add(elemento);
    }


    public void eliminar(int indice) {
        T elemento = buscarPorIndice(indice);
        elementos.remove(elemento);
    }

    public T buscarPorIndice(int indice) {
        if (indice < 0 || indice >= elementos.size()) {
            return null;
        }
        return elementos.get(indice);
    }

    public List<T> listar() {
        return new ArrayList<>(elementos);
    }

    public int cantidad() {
        return elementos.size();
    }

    public boolean estaVacio() {
        return elementos.isEmpty();
    }
}
