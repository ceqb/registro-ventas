package com.VentaCliente.service.impl;


import com.VentaCliente.dto.ClienteDTO;
import com.VentaCliente.dto.VentaDTO;
import com.VentaCliente.model.Cliente;

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


    }

