package com.VentaCliente.dto;

import com.VentaCliente.model.Cliente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClienteDTO implements Serializable {

    private Long id;
    private String nombre;
    private String telefono;
    private String correo;

    // Solo si necesitas las ventas del cliente (opcional)
    private List<VentaDTO> ventas;


    public static ClienteDTO toDto(Cliente model) {
        if (model == null) return null;

        return ClienteDTO.builder()
                .id(model.getId())
                .nombre(model.getNombre())
                .telefono(model.getTelefono())
                .correo(model.getCorreo())
                //.ventas(VentaDTO.toDto(model.getVentas()))
                .build();
    }
    public static List<ClienteDTO> toDto(List<Cliente> models) {
        if (models == null) return Collections.emptyList();
        return models.stream()
                .map(ClienteDTO::toDto).collect(Collectors.toList());
    }
}
