package aetp.taller;

import java.io.File;
import java.util.ArrayList;

public class mArticulo {

    private final ArrayList<clsArticulo> listaArticulos;
    private final clsCSV archivoDatos;

    public mArticulo() {
        archivoDatos = new clsCSV();
        listaArticulos = archivoDatos.leerDatos();
    }

    public boolean agregarArticulo(clsArticulo articulo) {
        if (buscarPorId(articulo.getId()) != null) {
            return false;
        }
        listaArticulos.add(articulo);
        archivoDatos.guardarDatos(listaArticulos);
        return true;
    }

    public ArrayList<clsArticulo> obtenerTodos() {
        return listaArticulos;
    }

    public clsArticulo buscarPorId(String id) {
        for (clsArticulo articulo : listaArticulos) {
            if (articulo.getId().equalsIgnoreCase(id)) {
                return articulo;
            }
        }
        return null;
    }

    public boolean actualizarArticulo(String id, clsArticulo articuloActualizado) {
        for (int i = 0; i < listaArticulos.size(); i++) {
            if (listaArticulos.get(i).getId().equalsIgnoreCase(id)) {
                listaArticulos.set(i, articuloActualizado);
                archivoDatos.guardarDatos(listaArticulos);
                return true;
            }
        }
        return false;
    }

    public boolean eliminarArticulo(String id) {
        for (int i = 0; i < listaArticulos.size(); i++) {
            if (listaArticulos.get(i).getId().equalsIgnoreCase(id)) {
                listaArticulos.remove(i);
                archivoDatos.guardarDatos(listaArticulos);
                return true;
            }
        }
        return false;
    }

    public int importarDesdeCSV(File archivo) throws Exception {
        ArrayList<clsArticulo> importados = archivoDatos.importarCSV(archivo);
        int contador = 0;

        for (clsArticulo articulo : importados) {
            clsArticulo existente = buscarPorId(articulo.getId());
            if (existente == null) {
                listaArticulos.add(articulo);
            } else {
                actualizarArticulo(articulo.getId(), articulo);
            }
            contador++;
        }

        archivoDatos.guardarDatos(listaArticulos);
        return contador;
    }

    public double obtenerValorTotalInventario() {
        double total = 0;
        for (clsArticulo articulo : listaArticulos) {
            total += articulo.getValorInventario();
        }
        return total;
    }

    public int obtenerTotalPiezas() {
        int total = 0;
        for (clsArticulo articulo : listaArticulos) {
            total += articulo.getCantidad();
        }
        return total;
    }

    public int obtenerArticulosSinStock() {
        int total = 0;
        for (clsArticulo articulo : listaArticulos) {
            if (articulo.getCantidad() == 0) {
                total++;
            }
        }
        return total;
    }

    public int obtenerArticulosConStock() {
        return listaArticulos.size() - obtenerArticulosSinStock();
    }
}
