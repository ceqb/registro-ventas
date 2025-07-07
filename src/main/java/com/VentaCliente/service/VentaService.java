package com.VentaCliente.service;

import com.VentaCliente.dto.VentaDTO;


import java.util.List;

public interface VentaService {

    List<VentaDTO> listVenta(); //LISTAR CATEGORIA
    VentaDTO  save(VentaDTO ventaDTO); //GUARDAR CATEGORIA


}
