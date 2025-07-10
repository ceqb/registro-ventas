package com.VentaCliente.service;

import com.VentaCliente.dto.ClienteDTO;
import com.VentaCliente.dto.VentaDTO;
import com.VentaCliente.model.Venta;


import java.util.List;
import java.util.Optional;

public interface VentaService {

    List<VentaDTO> listVenta();
    VentaDTO  save(VentaDTO ventaDTO);
    VentaDTO actualizarEstado(Long idVenta, String nuevoEstado);

}
