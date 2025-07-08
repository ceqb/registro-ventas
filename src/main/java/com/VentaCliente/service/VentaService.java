package com.VentaCliente.service;

import com.VentaCliente.dto.VentaDTO;


import java.util.List;

public interface VentaService {

    List<VentaDTO> listVenta();
    VentaDTO  save(VentaDTO ventaDTO);


}
