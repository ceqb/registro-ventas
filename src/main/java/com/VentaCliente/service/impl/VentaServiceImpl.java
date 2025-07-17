package com.VentaCliente.service.impl;



import com.VentaCliente.dto.ClienteDTO;
import com.VentaCliente.dto.VentaDTO;
import com.VentaCliente.exception.ResourceNotFoundException;
import com.VentaCliente.model.EstadoVenta;
import com.VentaCliente.model.Venta;
import com.VentaCliente.repository.VentaRepository;
import com.VentaCliente.service.AuditoriaClientService;
import com.VentaCliente.service.VentaService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor

public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;
    private final AuditoriaClientService auditoriaClient;



    @Override
    public List<VentaDTO> listVenta() {
        List<Venta> ventas = ventaRepository.findAll();
        return VentaDTO.toDto(ventas);
    }



    @Override
    public VentaDTO save(VentaDTO ventaDTO) {
        Venta venta = Venta.toModel(ventaDTO);
        venta.setFechaVenta(LocalDateTime.now());
        Venta ventaGuardada = ventaRepository.save(venta);
        auditoriaClient.registrar(
                "Venta",
                "CREACIÓN",
                "Se registró una nueva venta con ID: " + ventaGuardada.getId_ventas()
        );

        return VentaDTO.toDto(ventaGuardada);
    }

    @Override
    public VentaDTO actualizarEstado(Long idVenta, String nuevoEstado) {
        System.out.println("ID recibido: " + idVenta);
        Venta venta = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        try {
            EstadoVenta estadoEnum = EstadoVenta.valueOf(nuevoEstado);
            venta.setEstado(estadoEnum);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Estado inválido: " + nuevoEstado);
        }

        Venta ventaActualizada = ventaRepository.save(venta);
        return VentaDTO.toDto(ventaActualizada);
    }

    @Override
    public List<VentaDTO> findByEstado(EstadoVenta estado) {
    List<Venta>ventas=ventaRepository.findByEstado(estado);
    return VentaDTO.toDto(ventas);

    }


}

