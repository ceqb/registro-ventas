package com.VentaCliente.service;

import com.VentaCliente.dto.VentaDTO;

import com.itextpdf.text.*;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class VentaReportService {

    public byte[] generateVentasPdfReport(List<VentaDTO> ventas) throws DocumentException, IOException {
        // ByteArrayOutputStream para escribir el PDF en memoria
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Crear un nuevo documento PDF
        Document document = new Document();

        // Asociar PdfWriter al documento y al OutputStream
        PdfWriter.getInstance(document, baos);

        // Abrir el documento para empezar a añadir contenido
        document.open();
        // ↓↓↓ FUENTES CON TAMAÑO AJUSTADO ↓↓↓
        Font tituloFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
        Font encabezadoFont = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);
        Font contenidoFont = new Font(Font.FontFamily.HELVETICA, 8);
        // Añadir un título al documento
        // Título
        Paragraph titulo = new Paragraph("Reporte de Ventas", tituloFont);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);
        document.add(new Paragraph(" ")); // Espacio

        // Crear una tabla con el número de columnas de tu reporte (ej. 6 columnas para los campos de Venta)
        PdfPTable table = new PdfPTable(6); // id_ventas, producto, cantidad, precioUnitario, total, estado
        table.setWidthPercentage(100); // La tabla ocupará el 100% del ancho de la página

        // Encabezados
        String[] headers = {"Cliente","Producto", "Cantidad", "P. Unitario", "Total", "Estado"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, encabezadoFont));
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);
        }
        // Contenido
        for (VentaDTO venta : ventas) {
            table.addCell(new Phrase(
                    venta.getClienteDTO() != null ? venta.getClienteDTO().getNombre() : "N/A",
                    contenidoFont
            ));
            table.addCell(new Phrase(venta.getProducto(), contenidoFont));
            table.addCell(new Phrase(String.valueOf(venta.getCantidad()), contenidoFont));
            table.addCell(new Phrase(String.valueOf(venta.getPrecioUnitario()), contenidoFont));
            table.addCell(new Phrase(String.valueOf(venta.getTotal()), contenidoFont));
            table.addCell(new Phrase(
                    venta.getEstado() != null ? venta.getEstado() : "N/A",
                    contenidoFont
            ));

        }

        // Añadir la tabla al documento
        document.add(table);

        // Cerrar el documento
        document.close();

        // Devolver el contenido del PDF como un array de bytes
        return baos.toByteArray();
    }
}