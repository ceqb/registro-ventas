package com.VentaCliente.controller;

import com.VentaCliente.dto.ClienteDTO;
import com.VentaCliente.dto.VentaDTO;
import com.VentaCliente.exception.ResourceNotFoundException;
import com.VentaCliente.model.EstadoVenta;
import com.VentaCliente.payload.MensajeResponse;
import com.VentaCliente.service.ClienteService;
import com.VentaCliente.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    //GUARDAR
    @GetMapping("/register")
    public String CreateUser(Model modelo){
        ClienteDTO registro = new ClienteDTO();

        // Formulario con objeto vacío
        modelo.addAttribute("register", registro);
        return "createCliente";
    }
    @PostMapping("/createCliente")
    public String saveCliente(@ModelAttribute("register") ClienteDTO clienteDTO) {
        clienteService.save(clienteDTO);
        return "redirect:/clientes/listCliente";
    }

    @GetMapping
    public ResponseEntity<MensajeResponse> ventasList() {
        var getList = clienteService.listCliente();
        if (getList == null || getList.isEmpty()) {
            throw new ResourceNotFoundException("ventas no found");
        }
        return new ResponseEntity<>(MensajeResponse.builder()
                .mensaje("to list of ventas")
                .object(getList)
                .build()
                , HttpStatus.OK);
    }
}
