package com.VentaCliente.service;

import com.VentaCliente.dto.ClienteDTO;
import com.VentaCliente.dto.VentaDTO;


import java.util.List;

public interface ClienteService {

    List<ClienteDTO> listCliente();
    ClienteDTO save(ClienteDTO clienteDTO);


}
