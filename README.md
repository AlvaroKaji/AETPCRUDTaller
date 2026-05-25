# CRUD AETP - Taller Mecánico

Reconstrucción progresiva del proyecto original **AETP-CRUD-Taller** en una nueva carpeta, manteniendo compatibilidad funcional con la versión fuente.

## Estado del proyecto

### Módulo base
- Estructura Ant del proyecto.
- Punto de entrada de la aplicación.
- Menú principal Swing.
- Base de documentación técnica.

### Módulo de modelo y persistencia
- Entidad `clsArticulo`.
- Persistencia en `InventarioMecanica.txt`.
- Importación desde CSV.
- Lógica CRUD y métricas de inventario.

## Arquitectura general identificada
- **Presentación:** Java Swing (`JFrame`, formularios y tabla).
- **Dominio:** entidad `clsArticulo`.
- **Lógica de negocio:** gestor `mArticulo`.
- **Persistencia:** archivo plano `InventarioMecanica.txt` e importación CSV.
- **Reportería:** generador PDF propio.
- **Build:** Apache Ant.

## Flujo general del sistema
1. La aplicación inicia en `AETPTaller`.
2. Se abre el menú principal.
3. El usuario accede al módulo de artículos.
4. El sistema carga inventario desde TXT.
5. El usuario realiza operaciones CRUD, importación CSV y generación de PDF.

## Hallazgos del proyecto original
- Se detectaron textos con problemas de codificación visibles en la interfaz.
- El proyecto original usa persistencia local en TXT; no utiliza base de datos relacional.
- El directorio `test/` existe pero no contiene pruebas automatizadas.

## Estructura de documentación
- `docs/modulo-base.md`
- `docs/modulo-modelo-persistencia.md`

## Formato de persistencia
Cada registro se almacena así:

```text
id|nombre|categoria|cantidad|ubicacion|precioUnitario|fechaIngreso
```

## CSV esperado
```csv
id,nombre,categoria,cantidad,ubicacion,precioUnitario,fechaIngreso
```

## Compilación
```bash
ant jar
```
