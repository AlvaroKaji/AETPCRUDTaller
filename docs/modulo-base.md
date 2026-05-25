# Módulo Base

## Objetivo
Crear la base reconstruida del proyecto con estructura compilable, punto de entrada y menú principal inicial.

## Arquitectura
- `AETPTaller`: clase de arranque.
- `ftmMain`: ventana principal de navegación.
- `build.xml`: automatización de compilación y empaquetado.

## Clases involucradas
- `aetp.taller.AETPTaller`
- `aetp.taller.ftmMain`

## Métodos importantes
- `AETPTaller.main`
- `ftmMain.initComponents`
- `ftmMain.abrirArticulos`

## Flujo de ejecución
1. Se ejecuta `main`.
2. Se crea la ventana principal.
3. El usuario puede abrir el módulo de artículos.

## Casos de uso
- Abrir la aplicación.
- Navegar al módulo de inventario.

## Archivos creados en este módulo
- `build.xml`
- `manifest.mf`
- `README.md`
- `docs/modulo-base.md`
- `src/aetp/taller/AETPTaller.java`
- `src/aetp/taller/ftmMain.java`
