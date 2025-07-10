package com.VentaCliente.controller;




import com.VentaCliente.dto.ClienteDTO;
import com.VentaCliente.dto.VentaDTO;
import com.VentaCliente.exception.ResourceNotFoundException;
import com.VentaCliente.model.Cliente;
import com.VentaCliente.model.EstadoVenta;
import com.VentaCliente.model.Venta;
import com.VentaCliente.payload.MensajeResponse;
import com.VentaCliente.service.ClienteService;
import com.VentaCliente.service.VentaReportService;
import com.VentaCliente.service.VentaService;
import com.itextpdf.text.DocumentException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

@Controller
@RequestMapping("/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;
    private final ClienteService clienteService;
    private final VentaReportService ventaReportService;


    @GetMapping("/ver-reporte-pdf")
    public ResponseEntity<byte[]> generarReporteVentasPdf() {
        try {
            List<VentaDTO> ventas = ventaService.listVenta(); // Asegúrate que este método devuelve List<VentaDTO>
            byte[] pdfBytes = ventaReportService.generateVentasPdfReport(ventas);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.inline().filename("reporte-ventas.pdf").build());


            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(pdfBytes);
        } catch (DocumentException | IOException e) {
            return ResponseEntity
                    .internalServerError()
                    .body(null);
        }
    }
    @GetMapping("/reporte-pdf")
    public ResponseEntity<ByteArrayResource> downloadVentasPdfReport() {
        try {
            // Obtener todas las ventas (o un subconjunto, si lo filtras)
            List<VentaDTO> ventas = ventaService.listVenta();

            // Generar el PDF
            byte[] pdfBytes = ventaReportService.generateVentasPdfReport(ventas);

            // Configurar los encabezados de la respuesta para la descarga del PDF
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_ventas.pdf"); // Nombre del archivo al descargar

            // Devolver el PDF como un recurso de bytes
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(pdfBytes.length)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new ByteArrayResource(pdfBytes));

        } catch (DocumentException | IOException e) {
            // Manejar errores en la generación del PDF
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // O un mensaje de error más específico
        }
    }

    @GetMapping
    public ResponseEntity<MensajeResponse> ventasList() {
        var getList = ventaService.listVenta();
        if (getList == null || getList.isEmpty()) {
            throw new ResourceNotFoundException("ventas no found");
        }
        return new ResponseEntity<>(MensajeResponse.builder()
                .mensaje("to list of ventas")
                .object(getList)
                .build()
                , HttpStatus.OK);
    }
    @GetMapping("/listVenta")
    public String vistaVentas(Model model) {
        var ventas = ventaService.listVenta();

        model.addAttribute("listVenta", ventas);
        model.addAttribute("sinVentas", ventas == null || ventas.isEmpty());

        return "listVenta";
    }

    //GUARDAR
    @GetMapping("/register")
    public String CreateUser(Model modelo){
        VentaDTO registro = new VentaDTO();
        registro.setClienteDTO(new ClienteDTO());

        // Formulario con objeto vacío
        modelo.addAttribute("register", registro);

        // Cargar estados de venta (enum)
        modelo.addAttribute("estados", EstadoVenta.values());

        // Enviar lista de clientes
        List<ClienteDTO> clientes = clienteService.listCliente(); // Asumiendo que tienes este método
        modelo.addAttribute("clientes", clientes);


        return "createVenta";
    }
    @PostMapping("/createVenta")
    public String saveVenta(@ModelAttribute("register") VentaDTO ventaDTO) {

        // Calcular el total automáticamente
        double total = ventaDTO.getCantidad() * ventaDTO.getPrecioUnitario();
        ventaDTO.setTotal(total);

        ventaService.save(ventaDTO);
        return "redirect:/ventas/listVenta";
    }

    @PostMapping("/actualizar-estado")
    public String actualizarEstado(
            HttpServletRequest request, // Inyectar HttpServletRequest para depuración
            @RequestParam("id_ventas") Long id,
            @RequestParam("nuevoEstado") String nuevoEstado) {

        // --- INICIO DE LA SECCIÓN DE DEPURACIÓN ---
        System.out.println("--- Depuración de Parámetros de Solicitud ---");
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = request.getParameter(paramName);
            System.out.println("Parámetro recibido: '" + paramName + "' = '" + paramValue + "'");
        }
        System.out.println("------------------------------------------");

        String idStrFromRequest = request.getParameter("id_ventas");
        System.out.println("Valor de 'id_ventas' directamente del Request: '" + idStrFromRequest + "'");
        // --- FIN DE LA SECCIÓN DE DEPURACIÓN ---

        System.out.println("🟢 ID recibido por @RequestParam: " + id); // Este será null si falla la conversión
        System.out.println("🟢 Estado recibido por @RequestParam: " + nuevoEstado);

        // Si 'id' es null aquí, la conversión falló.
        // Puedes añadir una comprobación para evitar NullPointerException si la lógica de negocio lo permite
        if (id == null) {
            System.err.println("Error: 'id_ventas' no pudo ser convertido a Long. Valor recibido: '" + idStrFromRequest + "'");
            // Aquí podrías redirigir a una página de error o mostrar un mensaje al usuario
            return "redirect:/ventas/errorPage"; // Ejemplo de redirección a error
        }

        ventaService.actualizarEstado(id, nuevoEstado);
        return "redirect:/ventas/listVenta";
    }

}
