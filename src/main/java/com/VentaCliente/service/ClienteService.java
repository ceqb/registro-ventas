package com.VentaCliente.service;

import com.VentaCliente.dto.ClienteDTO;



import java.util.List;
import java.util.Optional;

public interface ClienteService {

    List<ClienteDTO> listCliente();
    ClienteDTO save(ClienteDTO clienteDTO);


}
