package com.VentaCliente.login;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioDTO implements Serializable {
    private Long id;
    private String usuario;
    private String clave;
    private String nombre;

    private String correo;
    private Integer intentosFallidos;
    private boolean bloqueado;

    private LocalDateTime expiracionCodigo;
    private String codigoDesbloqueo;

    public static UsuarioDTO toDto(Usuario model) {
        if (model == null) return null;

        return UsuarioDTO.builder()
                .id(model.getId())
                .nombre(model.getNombre())
                .usuario(model.getUsuario())
                .clave(null)
                .correo(model.getCorreo())
                .expiracionCodigo(model.getExpiracionCodigo())
                .intentosFallidos(model.getIntentosFallidos())
                .bloqueado(model.isBloqueado())
                .codigoDesbloqueo(model.getCodigoDesbloqueo())
                .build();
    }
    public static List<UsuarioDTO> toDto(List<Usuario> models) {
        if (models == null) return Collections.emptyList();
        return models.stream()
                .map(UsuarioDTO::toDto).collect(Collectors.toList());
    }

}
