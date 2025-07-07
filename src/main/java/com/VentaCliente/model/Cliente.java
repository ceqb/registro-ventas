package com.VentaCliente.model;



import com.VentaCliente.dto.ClienteDTO;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clientes")

public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "telefono")
    private String telefono;

    // Relación con ventas
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Venta> ventas = new ArrayList<>();


    public static Cliente toModel(ClienteDTO dto) {
        if (dto == null) return null;

        return Cliente.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .telefono(dto.getTelefono())
                .ventas(Venta.toModel(dto.getVentas()))
                .build();
    }
    public static List<Cliente> toModel(List<ClienteDTO> dtos) {
        if (dtos == null) return Collections.emptyList();
        return dtos.stream()
                .map(Cliente::toModel).collect(Collectors.toList());
    }
}

