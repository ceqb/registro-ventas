package com.VentaCliente.service.impl;



import com.VentaCliente.dto.VentaDTO;
import com.VentaCliente.model.Venta;
import com.VentaCliente.repository.VentaRepository;
import com.VentaCliente.service.VentaService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor

public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;



    @Override
    public List<VentaDTO> listVenta() {
        List<Venta> ventas = ventaRepository.findAll();
        return VentaDTO.toDto(ventas);
    }



    @Override
    public VentaDTO save(VentaDTO ventaDTO) {
        Venta venta = Venta.toModel(ventaDTO);
        Venta ventaGuardada = ventaRepository.save(venta);
        return VentaDTO.toDto(ventaGuardada);
    }


    }

