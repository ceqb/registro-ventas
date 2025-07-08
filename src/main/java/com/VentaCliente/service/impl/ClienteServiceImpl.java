package com.VentaCliente.service.impl;


import com.VentaCliente.dto.ClienteDTO;
import com.VentaCliente.dto.VentaDTO;
import com.VentaCliente.model.Cliente;

import com.VentaCliente.model.Venta;
import com.VentaCliente.repository.ClienteRepository;

import com.VentaCliente.service.ClienteService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor

public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;



    @Override
    public List<ClienteDTO> listCliente() {
        List<Cliente> clientes = clienteRepository.findAll();
        return ClienteDTO.toDto(clientes);
    }

    @Override
    public ClienteDTO save(ClienteDTO clienteDTO) {
        Cliente cliente = Cliente.toModel(clienteDTO);
        Cliente clienteGuardado = clienteRepository.save(cliente);
        return ClienteDTO.toDto(clienteGuardado);
    }


}

