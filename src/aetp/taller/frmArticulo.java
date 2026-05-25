package aetp.taller;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;
import java.time.LocalDate;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class frmArticulo extends JFrame {

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtCategoria;
    private JTextField txtCantidad;
    private JTextField txtUbicacion;
    private JTextField txtPrecio;
    private JTextField txtFechaIngreso;
    private JTextField txtBuscar;
    private JLabel lblResumen;
    private JTable tablaArticulos;
    private DefaultTableModel modeloTabla;
    private mArticulo gestorArticulos;

    public frmArticulo() {
        gestorArticulos = new mArticulo();
        initComponents();
        actualizarTabla();
    }

    private void initComponents() {
        setTitle("CRUD de Inventario AETP");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1100, 650);

        txtId = new JTextField();
        txtNombre = new JTextField();
        txtCategoria = new JTextField();
        txtCantidad = new JTextField();
        txtUbicacion = new JTextField();
        txtPrecio = new JTextField();
        txtFechaIngreso = new JTextField(LocalDate.now().toString());
        txtBuscar = new JTextField();
        lblResumen = new JLabel(" ");

        JPanel panelFormulario = new JPanel(new GridLayout(7, 2, 8, 8));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del artículo"));
        panelFormulario.add(new JLabel("ID Artículo:"));
        panelFormulario.add(txtId);
        panelFormulario.add(new JLabel("Nombre de pieza:"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Categoría:"));
        panelFormulario.add(txtCategoria);
        panelFormulario.add(new JLabel("Cantidad (int):"));
        panelFormulario.add(txtCantidad);
        panelFormulario.add(new JLabel("Ubicación:"));
        panelFormulario.add(txtUbicacion);
        panelFormulario.add(new JLabel("Precio unitario (double):"));
        panelFormulario.add(txtPrecio);
        panelFormulario.add(new JLabel("Fecha ingreso yyyy-MM-dd:"));
        panelFormulario.add(txtFechaIngreso);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");
        JButton btnImportarCSV = new JButton("Importar CSV");
        JButton btnReportePDF = new JButton("Generar PDF");
        JButton btnLimpiarBusqueda = new JButton("Mostrar todos");

        btnGuardar.addActionListener(evt -> guardarArticulo());
        btnActualizar.addActionListener(evt -> actualizarArticulo());
        btnEliminar.addActionListener(evt -> eliminarArticulo());
        btnLimpiar.addActionListener(evt -> limpiarCampos());
        btnImportarCSV.addActionListener(evt -> importarCSV());
        btnReportePDF.addActionListener(evt -> generarPDF());
        btnLimpiarBusqueda.addActionListener(evt -> {
            txtBuscar.setText("");
            actualizarTabla();
        });

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnImportarCSV);
        panelBotones.add(btnReportePDF);

        JPanel panelBusqueda = new JPanel(new BorderLayout(8, 8));
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("Buscador"));
        panelBusqueda.add(new JLabel("Buscar por ID, categoría o nombre:"), BorderLayout.WEST);
        panelBusqueda.add(txtBuscar, BorderLayout.CENTER);
        panelBusqueda.add(btnLimpiarBusqueda, BorderLayout.EAST);

        txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                actualizarTabla();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                actualizarTabla();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                actualizarTabla();
            }
        });

        JPanel panelAcciones = new JPanel(new BorderLayout());
        panelAcciones.add(panelBotones, BorderLayout.NORTH);
        panelAcciones.add(panelBusqueda, BorderLayout.CENTER);
        panelAcciones.add(lblResumen, BorderLayout.SOUTH);

        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre", "Categoría", "Cantidad", "Ubicación", "Precio", "Fecha", "Stock", "Valor"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaArticulos = new JTable(modeloTabla);
        tablaArticulos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaArticulos.getSelectionModel().addListSelectionListener(evt -> {
            if (!evt.getValueIsAdjusting()) {
                cargarSeleccion();
            }
        });

        JScrollPane scrollTabla = new JScrollPane(tablaArticulos);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Lista de artículos"));

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelFormulario, BorderLayout.CENTER);
        panelSuperior.add(panelAcciones, BorderLayout.SOUTH);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);
        setContentPane(panelPrincipal);
    }

    private void guardarArticulo() {
        clsArticulo articulo = leerFormulario();
        if (articulo == null) {
            return;
        }

        if (!gestorArticulos.agregarArticulo(articulo)) {
            JOptionPane.showMessageDialog(this, "Ya existe un artículo con ese ID. Usa Actualizar.");
            return;
        }
        actualizarTabla();
        limpiarCampos();
        JOptionPane.showMessageDialog(this, "Artículo guardado en InventarioMecanica.txt.");
    }

    private void actualizarArticulo() {
        clsArticulo articulo = leerFormulario();
        if (articulo == null) {
            return;
        }

        if (gestorArticulos.actualizarArticulo(articulo.getId(), articulo)) {
            actualizarTabla();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Artículo actualizado.");
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró un artículo con ese ID.");
        }
    }

    private void eliminarArticulo() {
        String id = txtId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escribe o selecciona un ID para eliminar.");
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(this, "¿Eliminar físicamente el registro " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        if (gestorArticulos.eliminarArticulo(id)) {
            actualizarTabla();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Artículo eliminado.");
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró un artículo con ese ID.");
        }
    }

    private clsArticulo leerFormulario() {
        String id = txtId.getText().trim();
        String nombre = txtNombre.getText().trim();
        String categoria = txtCategoria.getText().trim();
        String ubicacion = txtUbicacion.getText().trim();
        String cantidadTexto = txtCantidad.getText().trim();
        String precioTexto = txtPrecio.getText().trim();
        String fechaTexto = txtFechaIngreso.getText().trim();

        if (id.isEmpty() || nombre.isEmpty() || categoria.isEmpty() || cantidadTexto.isEmpty()
                || ubicacion.isEmpty() || precioTexto.isEmpty() || fechaTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Llena todos los campos obligatorios.");
            return null;
        }

        try {
            int cantidad = Integer.parseInt(cantidadTexto);
            double precio = Double.parseDouble(precioTexto);
            LocalDate fecha = LocalDate.parse(fechaTexto);

            if (cantidad < 0) {
                JOptionPane.showMessageDialog(this, "La cantidad no puede ser negativa.");
                return null;
            }
            if (precio < 0) {
                JOptionPane.showMessageDialog(this, "El precio no puede ser negativo.");
                return null;
            }
            return new clsArticulo(id, nombre, categoria, cantidad, ubicacion, precio, fecha);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cantidad debe ser int y precio debe ser double.");
            return null;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "La fecha debe tener formato yyyy-MM-dd. Ejemplo: " + LocalDate.now());
            return null;
        }
    }

    private void actualizarTabla() {
        String busqueda = txtBuscar == null ? "" : txtBuscar.getText().trim().toLowerCase();
        modeloTabla.setRowCount(0);

        for (clsArticulo articulo : gestorArticulos.obtenerTodos()) {
            if (coincideBusqueda(articulo, busqueda)) {
                modeloTabla.addRow(new Object[]{
                    articulo.getId(), articulo.getNombre(), articulo.getCategoria(), articulo.getCantidad(),
                    articulo.getUbicacion(), String.format("%.2f", articulo.getPrecioUnitario()),
                    articulo.getFechaIngreso(), articulo.getEstatusStock(),
                    String.format("%.2f", articulo.getValorInventario())
                });
            }
        }
        lblResumen.setText(String.format("Registros: %d | Piezas: %d | Con stock: %d | Sin stock: %d | Valor inventario: $%.2f",
                gestorArticulos.obtenerTodos().size(), gestorArticulos.obtenerTotalPiezas(),
                gestorArticulos.obtenerArticulosConStock(), gestorArticulos.obtenerArticulosSinStock(),
                gestorArticulos.obtenerValorTotalInventario()));
    }

    private boolean coincideBusqueda(clsArticulo articulo, String busqueda) {
        if (busqueda.isEmpty()) {
            return true;
        }
        return articulo.getId().toLowerCase().contains(busqueda)
                || articulo.getNombre().toLowerCase().contains(busqueda)
                || articulo.getCategoria().toLowerCase().contains(busqueda);
    }

    private void cargarSeleccion() {
        int fila = tablaArticulos.getSelectedRow();
        if (fila >= 0) {
            txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
            txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtCategoria.setText(modeloTabla.getValueAt(fila, 2).toString());
            txtCantidad.setText(modeloTabla.getValueAt(fila, 3).toString());
            txtUbicacion.setText(modeloTabla.getValueAt(fila, 4).toString());
            txtPrecio.setText(modeloTabla.getValueAt(fila, 5).toString());
            txtFechaIngreso.setText(modeloTabla.getValueAt(fila, 6).toString());
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtCategoria.setText("");
        txtCantidad.setText("");
        txtUbicacion.setText("");
        txtPrecio.setText("");
        txtFechaIngreso.setText(LocalDate.now().toString());
        tablaArticulos.clearSelection();
    }

    private void importarCSV() {
        JFileChooser selector = new JFileChooser();
        int resultado = selector.showOpenDialog(this);
        if (resultado != JFileChooser.APPROVE_OPTION) {
            return;
        }
        try {
            int cantidad = gestorArticulos.importarDesdeCSV(selector.getSelectedFile());
            actualizarTabla();
            JOptionPane.showMessageDialog(this, "Registros importados/actualizados: " + cantidad);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No se pudo importar el CSV. Formato esperado:\nid,nombre,categoria,cantidad,ubicacion,precioUnitario,fechaIngreso");
        }
    }

    private void generarPDF() {
        JFileChooser selector = new JFileChooser();
        selector.setSelectedFile(new File("ReporteInventarioAETP.pdf"));
        int resultado = selector.showSaveDialog(this);
        if (resultado != JFileChooser.APPROVE_OPTION) {
            return;
        }
        try {
            File archivo = selector.getSelectedFile();
            ReportePDF.generar(archivo, gestorArticulos.obtenerTodos(), gestorArticulos.obtenerValorTotalInventario(),
                    gestorArticulos.obtenerTotalPiezas(), gestorArticulos.obtenerArticulosConStock(),
                    gestorArticulos.obtenerArticulosSinStock());
            JOptionPane.showMessageDialog(this, "Reporte PDF generado:\n" + archivo.getAbsolutePath());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al generar PDF: " + e.getMessage());
        }
    }
}
