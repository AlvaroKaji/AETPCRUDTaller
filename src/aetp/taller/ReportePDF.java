package aetp.taller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class ReportePDF {

    public static void generar(File archivo, List<clsArticulo> articulos, double valorTotal, int totalPiezas,
            int conStock, int sinStock) throws Exception {
        escribirPDFBonito(archivo, articulos, valorTotal, totalPiezas, conStock, sinStock);
    }

    private static void escribirPDFBonito(File archivo, List<clsArticulo> articulos, double valorTotal,
            int totalPiezas, int conStock, int sinStock) throws Exception {
        StringBuilder c = new StringBuilder();
        LogoPDF logo = cargarLogo();

        rect(c, 0, 0, 612, 792, "1 1 1", true);
        rect(c, 0, 715, 612, 77, "0.02 0.02 0.02", true);
        rect(c, 0, 709, 612, 6, "0.95 0.84 0.00", true);

        if (logo != null) {
            imagen(c, 34, 724, 122, 52);
        } else {
            rect(c, 38, 727, 90, 46, "0.95 0.84 0.00", true);
            text(c, "F2", 14, 48, 744, "AETP", "0 0 0");
        }

        text(c, "F2", 20, 178, 755, "Reporte Gerencial de Inventario", "0.95 0.84 0.00");
        text(c, "F1", 10, 179, 737, "Sistema de control de inventario - Taller mecánico", "0.95 0.95 0.95");
        text(c, "F1", 9, 179, 723, "Generado: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), "0.90 0.90 0.90");

        card(c, 38, 655, 120, 42, "Registros", String.valueOf(articulos.size()));
        card(c, 178, 655, 120, 42, "Piezas", String.valueOf(totalPiezas));
        card(c, 318, 655, 120, 42, "Con stock", String.valueOf(conStock));
        card(c, 458, 655, 120, 42, "Sin stock", String.valueOf(sinStock));

        text(c, "F2", 12, 38, 625, "Valor total del inventario: $" + money(valorTotal), "0.10 0.18 0.30");
        text(c, "F1", 9, 38, 609, "Estatus calculado automáticamente: si la cantidad es 0 = Sin stock; cualquier otro número = Con stock.", "0.30 0.30 0.30");

        double y = 575;
        rect(c, 38, y, 540, 24, "0.02 0.02 0.02", true);
        text(c, "F2", 8, 45, y + 8, "ID", "0.95 0.84 0.00");
        text(c, "F2", 8, 90, y + 8, "Nombre", "0.95 0.84 0.00");
        text(c, "F2", 8, 190, y + 8, "Categoría", "0.95 0.84 0.00");
        text(c, "F2", 8, 275, y + 8, "Cant.", "0.95 0.84 0.00");
        text(c, "F2", 8, 320, y + 8, "Ubicación", "0.95 0.84 0.00");
        text(c, "F2", 8, 405, y + 8, "Precio", "0.95 0.84 0.00");
        text(c, "F2", 8, 465, y + 8, "Stock", "0.95 0.84 0.00");
        text(c, "F2", 8, 525, y + 8, "Valor", "0.95 0.84 0.00");

        y -= 22;
        int filas = 0;
        for (clsArticulo articulo : articulos) {
            if (filas >= 22) {
                break;
            }
            if (filas % 2 == 0) {
                rect(c, 38, y - 3, 540, 20, "0.96 0.97 0.99", true);
            }
            text(c, "F1", 7, 45, y + 4, safe(articulo.getId(), 8), "0.08 0.08 0.08");
            text(c, "F1", 7, 90, y + 4, safe(articulo.getNombre(), 18), "0.08 0.08 0.08");
            text(c, "F1", 7, 190, y + 4, safe(articulo.getCategoria(), 14), "0.08 0.08 0.08");
            text(c, "F1", 7, 275, y + 4, String.valueOf(articulo.getCantidad()), "0.08 0.08 0.08");
            text(c, "F1", 7, 320, y + 4, safe(articulo.getUbicacion(), 14), "0.08 0.08 0.08");
            text(c, "F1", 7, 405, y + 4, "$" + money(articulo.getPrecioUnitario()), "0.08 0.08 0.08");
            text(c, "F1", 7, 465, y + 4, articulo.getEstatusStock(), articulo.getCantidad() == 0 ? "0.75 0.10 0.10" : "0.10 0.45 0.20");
            text(c, "F1", 7, 525, y + 4, "$" + money(articulo.getValorInventario()), "0.08 0.08 0.08");
            line(c, 38, y - 5, 578, y - 5, "0.85 0.85 0.85");
            y -= 20;
            filas++;
        }

        if (articulos.size() > 22) {
            text(c, "F1", 9, 38, 95, "Nota: el reporte muestra los primeros 22 registros para conservar presentación en una página.", "0.45 0.45 0.45");
        }

        text(c, "F1", 8, 38, 45, "Auto Express Tepa | Reporte generado automáticamente desde archivos TXT", "0.40 0.40 0.40");
        line(c, 38, 62, 578, 62, "0.80 0.80 0.80");

        escribirPDF(archivo, c.toString(), logo);
    }

    private static void card(StringBuilder c, double x, double y, double w, double h, String titulo, String valor) {
        rect(c, x, y, w, h, "0.98 0.98 0.92", true);
        rect(c, x, y, w, h, "0.85 0.78 0.25", false);
        text(c, "F1", 8, x + 10, y + 27, titulo, "0.35 0.35 0.35");
        text(c, "F2", 16, x + 10, y + 9, valor, "0.02 0.02 0.02");
    }

    private static void imagen(StringBuilder c, double x, double y, double w, double h) {
        c.append("q\n")
                .append(num(w)).append(" 0 0 ").append(num(h)).append(" ")
                .append(num(x)).append(" ").append(num(y)).append(" cm\n")
                .append("/Im1 Do\nQ\n");
    }

    private static LogoPDF cargarLogo() {
        ImageIO.setUseCache(false);
        String[] rutas = {
            "assets/autoexpresstepalogo.png",
            "assets/autoexpresstepalogo(1).png",
            "src/assets/autoexpresstepalogo.png",
            "logo.png"
        };

        for (String ruta : rutas) {
            try {
                File archivo = new File(ruta);
                if (!archivo.exists()) {
                    continue;
                }

                BufferedImage original = ImageIO.read(archivo);
                if (original == null) {
                    continue;
                }

                BufferedImage rgb = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
                for (int y = 0; y < original.getHeight(); y++) {
                    for (int x = 0; x < original.getWidth(); x++) {
                        int argb = original.getRGB(x, y);
                        int alpha = (argb >> 24) & 0xff;
                        int r = (argb >> 16) & 0xff;
                        int g = (argb >> 8) & 0xff;
                        int b = argb & 0xff;
                        r = (r * alpha) / 255;
                        g = (g * alpha) / 255;
                        b = (b * alpha) / 255;
                        rgb.setRGB(x, y, (r << 16) | (g << 8) | b);
                    }
                }

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(rgb, "jpg", baos);
                return new LogoPDF(rgb.getWidth(), rgb.getHeight(), baos.toByteArray());
            } catch (Exception ex) {
                // Sigue con la siguiente ruta.
            }
        }
        return null;
    }

    private static class LogoPDF {
        int ancho;
        int alto;
        byte[] bytes;

        LogoPDF(int ancho, int alto, byte[] bytes) {
            this.ancho = ancho;
            this.alto = alto;
            this.bytes = bytes;
        }
    }

    private static String safe(String texto, int max) {
        if (texto == null) {
            return "";
        }
        String limpio = texto
                .replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u").replace("ñ", "n")
                .replace("Á", "A").replace("É", "E").replace("Í", "I").replace("Ó", "O").replace("Ú", "U").replace("Ñ", "N");
        if (limpio.length() <= max) {
            return limpio;
        }
        return limpio.substring(0, Math.max(0, max - 3)) + "...";
    }

    private static String money(double value) {
        return String.format(java.util.Locale.US, "%.2f", value);
    }

    private static void text(StringBuilder c, String font, int size, double x, double y, String text, String rgb) {
        c.append("BT\n").append(rgb).append(" rg\n/").append(font).append(" ").append(size).append(" Tf\n")
                .append(num(x)).append(" ").append(num(y)).append(" Td\n(").append(escaparPDF(safe(text, 120))).append(") Tj\nET\n");
    }

    private static void rect(StringBuilder c, double x, double y, double w, double h, String rgb, boolean fill) {
        c.append(rgb).append(fill ? " rg\n" : " RG\n")
                .append(num(x)).append(" ").append(num(y)).append(" ").append(num(w)).append(" ").append(num(h)).append(" re ")
                .append(fill ? "f\n" : "S\n");
    }

    private static void line(StringBuilder c, double x1, double y1, double x2, double y2, String rgb) {
        c.append(rgb).append(" RG\n")
                .append(num(x1)).append(" ").append(num(y1)).append(" m ")
                .append(num(x2)).append(" ").append(num(y2)).append(" l S\n");
    }

    private static String num(double n) {
        return String.format(java.util.Locale.US, "%.2f", n);
    }

    private static void escribirPDF(File archivo, String contenido, LogoPDF logo) throws Exception {
        byte[] stream = contenido.getBytes(StandardCharsets.ISO_8859_1);
        ArrayList<Integer> offsets = new ArrayList<>();
        ByteArrayOutputStream pdf = new ByteArrayOutputStream();

        escribir(pdf, "%PDF-1.4\n");

        offsets.add(pdf.size());
        escribir(pdf, "1 0 obj\n<< /Type /Catalog /Pages 2 0 R >>\nendobj\n");
        offsets.add(pdf.size());
        escribir(pdf, "2 0 obj\n<< /Type /Pages /Kids [3 0 R] /Count 1 >>\nendobj\n");
        offsets.add(pdf.size());
        String recursos = "<< /Font << /F1 4 0 R /F2 5 0 R >>";
        if (logo != null) {
            recursos += " /XObject << /Im1 7 0 R >>";
        }
        recursos += " >>";
        escribir(pdf, "3 0 obj\n<< /Type /Page /Parent 2 0 R /MediaBox [0 0 612 792] /Resources "
                + recursos + " /Contents 6 0 R >>\nendobj\n");
        offsets.add(pdf.size());
        escribir(pdf, "4 0 obj\n<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica >>\nendobj\n");
        offsets.add(pdf.size());
        escribir(pdf, "5 0 obj\n<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica-Bold >>\nendobj\n");
        offsets.add(pdf.size());
        escribir(pdf, "6 0 obj\n<< /Length " + stream.length + " >>\nstream\n");
        pdf.write(stream);
        escribir(pdf, "\nendstream\nendobj\n");

        if (logo != null) {
            offsets.add(pdf.size());
            escribir(pdf, "7 0 obj\n<< /Type /XObject /Subtype /Image /Width " + logo.ancho
                    + " /Height " + logo.alto
                    + " /ColorSpace /DeviceRGB /BitsPerComponent 8 /Filter /DCTDecode /Length "
                    + logo.bytes.length + " >>\nstream\n");
            pdf.write(logo.bytes);
            escribir(pdf, "\nendstream\nendobj\n");
        }

        int xref = pdf.size();
        int totalObjetos = logo == null ? 7 : 8;
        escribir(pdf, "xref\n0 " + totalObjetos + "\n0000000000 65535 f \n");
        for (Integer offset : offsets) {
            escribir(pdf, String.format("%010d 00000 n \n", offset));
        }
        escribir(pdf, "trailer\n<< /Size " + totalObjetos + " /Root 1 0 R >>\nstartxref\n" + xref + "\n%%EOF");

        try (FileOutputStream fos = new FileOutputStream(archivo)) {
            fos.write(pdf.toByteArray());
        }
    }

    private static void escribir(ByteArrayOutputStream out, String texto) throws Exception {
        out.write(texto.getBytes(StandardCharsets.ISO_8859_1));
    }

    private static String escaparPDF(String texto) {
        return texto.replace("\\", "\\\\").replace("(", "\\(").replace(")", "\\)");
    }
}
