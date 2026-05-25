package aetp.taller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;

public class clsCSV {

    private static final String RUTA_ARCHIVO = "InventarioMecanica.txt";

    public void guardarDatos(ArrayList<clsArticulo> lista) {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(RUTA_ARCHIVO))) {
            for (clsArticulo articulo : lista) {
                escritor.println(escapar(articulo.getId()) + "|"
                        + escapar(articulo.getNombre()) + "|"
                        + escapar(articulo.getCategoria()) + "|"
                        + articulo.getCantidad() + "|"
                        + escapar(articulo.getUbicacion()) + "|"
                        + articulo.getPrecioUnitario() + "|"
                        + articulo.getFechaIngreso());
            }
        } catch (Exception e) {
            System.out.println("Error al guardar TXT: " + e.getMessage());
        }
    }

    public ArrayList<clsArticulo> leerDatos() {
        ArrayList<clsArticulo> listaRecuperada = new ArrayList<>();
        File archivo = new File(RUTA_ARCHIVO);

        if (!archivo.exists()) {
            return listaRecuperada;
        }

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                clsArticulo articulo = convertirLineaTXT(linea);
                if (articulo != null) {
                    listaRecuperada.add(articulo);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al leer TXT: " + e.getMessage());
        }

        return listaRecuperada;
    }

    public ArrayList<clsArticulo> importarCSV(File archivoCSV) throws Exception {
        ArrayList<clsArticulo> importados = new ArrayList<>();

        try (BufferedReader lector = new BufferedReader(new FileReader(archivoCSV))) {
            String linea;
            boolean primeraLinea = true;

            while ((linea = lector.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] datos = linea.split(",", -1);
                if (primeraLinea && datos[0].toLowerCase().contains("id")) {
                    primeraLinea = false;
                    continue;
                }
                primeraLinea = false;

                if (datos.length >= 7) {
                    clsArticulo articulo = new clsArticulo(
                            datos[0].trim(),
                            datos[1].trim(),
                            datos[2].trim(),
                            Integer.parseInt(datos[3].trim()),
                            datos[4].trim(),
                            Double.parseDouble(datos[5].trim()),
                            LocalDate.parse(datos[6].trim())
                    );
                    importados.add(articulo);
                }
            }
        }

        return importados;
    }

    private clsArticulo convertirLineaTXT(String linea) {
        try {
            String[] datos = linea.split("\\|", -1);
            if (datos.length == 7) {
                return new clsArticulo(
                        datos[0],
                        datos[1],
                        datos[2],
                        Integer.parseInt(datos[3]),
                        datos[4],
                        Double.parseDouble(datos[5]),
                        LocalDate.parse(datos[6])
                );
            }
        } catch (Exception e) {
            System.out.println("Registro inválido ignorado: " + linea);
        }
        return null;
    }

    private String escapar(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.replace("|", " ").replace("\n", " ").replace("\r", " ").trim();
    }
}
