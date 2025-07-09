package com.VentaCliente.login;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario")
    private String usuario;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "clave")
    private String clave;

    public static Usuario toModel(UsuarioDTO dto) {
        if (dto == null) return null;

        return Usuario.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .usuario(dto.getUsuario())
                .clave(dto.getClave())
                .build();
    }
    public static List<Usuario> toModel(List<UsuarioDTO> dtos) {
        if (dtos == null) return Collections.emptyList();
        return dtos.stream()
                .map(Usuario::toModel).collect(Collectors.toList());
    }
}
