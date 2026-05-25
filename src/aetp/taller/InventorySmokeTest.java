package aetp.taller;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;

public class InventorySmokeTest {

    public static void main(String[] args) throws Exception {
        Files.deleteIfExists(new File("InventarioMecanica.txt").toPath());

        mArticulo gestor = new mArticulo();
        if (!gestor.obtenerTodos().isEmpty()) {
            throw new IllegalStateException("La lista inicial debe estar vacía.");
        }

        clsArticulo articulo = new clsArticulo("T001", "Prueba", "Test", 2, "Rack 1", 100.0, LocalDate.parse("2026-05-24"));
        if (!gestor.agregarArticulo(articulo)) {
            throw new IllegalStateException("No se pudo agregar el artículo.");
        }

        if (gestor.obtenerTodos().size() != 1) {
            throw new IllegalStateException("Se esperaba exactamente un artículo.");
        }

        if (gestor.obtenerValorTotalInventario() != 200.0) {
            throw new IllegalStateException("Valor de inventario incorrecto.");
        }

        int importados = gestor.importarDesdeCSV(new File("catalogo_ejemplo.csv"));
        if (importados != 5) {
            throw new IllegalStateException("Se esperaban 5 registros importados.");
        }

        if (gestor.buscarPorId("A002") == null) {
            throw new IllegalStateException("No se importó el artículo A002.");
        }

        System.out.println("InventorySmokeTest OK");
    }
}
