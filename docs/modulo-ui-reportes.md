# Módulo UI y Reportes

## Objetivo
Reconstruir la interfaz Swing operativa y el reporte PDF gerencial del inventario.

## Arquitectura
- `ftmMain`: menú principal.
- `frmArticulo`: formulario CRUD con tabla, búsqueda y acciones.
- `ReportePDF`: generador de PDF sin dependencias externas.

## Clases involucradas
- `aetp.taller.ftmMain`
- `aetp.taller.frmArticulo`
- `aetp.taller.ReportePDF`

## Métodos importantes
- `ftmMain.abrirArticulos`
- `frmArticulo.guardarArticulo`
- `frmArticulo.actualizarTabla`
- `frmArticulo.importarCSV`
- `frmArticulo.generarPDF`
- `ReportePDF.generar`

## Flujo de ejecución
1. El usuario entra al menú principal.
2. Abre el módulo de artículos.
3. Captura, consulta o modifica registros.
4. Puede importar un catálogo CSV.
5. Puede generar un PDF con resumen y tabla.

## Casos de uso
- Alta, edición y eliminación de artículos.
- Búsqueda por ID, nombre o categoría.
- Importación masiva desde CSV.
- Generación de reporte gerencial.

## Recursos gráficos
- `assets/autoexpresstepalogo.png`
- `assets/logo_aetp.svg`
