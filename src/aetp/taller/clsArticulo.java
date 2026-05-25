package aetp.taller;

import java.time.LocalDate;

public class clsArticulo {

    private String id;
    private String nombre;
    private String categoria;
    private int cantidad;
    private String ubicacion;
    private double precioUnitario;
    private LocalDate fechaIngreso;

    public clsArticulo() {
    }

    public clsArticulo(String id, String nombre, String categoria, int cantidad, String ubicacion,
            double precioUnitario, LocalDate fechaIngreso) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.ubicacion = ubicacion;
        this.precioUnitario = precioUnitario;
        this.fechaIngreso = fechaIngreso;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public double getValorInventario() {
        return cantidad * precioUnitario;
    }

    public String getEstatusStock() {
        return cantidad == 0 ? "Sin stock" : "Con stock";
    }
}
