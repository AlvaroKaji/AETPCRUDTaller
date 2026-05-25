# Módulo Modelo y Persistencia

## Objetivo
Reconstruir la entidad de inventario y la persistencia en archivo plano compatible con la versión original.

## Arquitectura
- `clsArticulo`: modelo de dominio.
- `clsCSV`: acceso a datos TXT y CSV.
- `mArticulo`: capa de negocio para CRUD y métricas.

## Clases involucradas
- `aetp.taller.clsArticulo`
- `aetp.taller.clsCSV`
- `aetp.taller.mArticulo`

## Métodos importantes
- `clsArticulo.getValorInventario`
- `clsArticulo.getEstatusStock`
- `clsCSV.guardarDatos`
- `clsCSV.leerDatos`
- `clsCSV.importarCSV`
- `mArticulo.agregarArticulo`
- `mArticulo.actualizarArticulo`
- `mArticulo.eliminarArticulo`

## Flujo de ejecución
1. `mArticulo` carga el archivo `InventarioMecanica.txt`.
2. La aplicación opera sobre la lista en memoria.
3. Cada cambio se persiste nuevamente en TXT.
4. La importación CSV inserta o actualiza por ID.

## Casos de uso
- Crear artículos.
- Consultar inventario completo.
- Actualizar o eliminar artículos.
- Importar catálogo desde CSV.
- Calcular stock y valor total.

## Compatibilidad preservada
- Persistencia en `InventarioMecanica.txt`.
- Separador `|`.
- Cálculo dinámico de stock.
